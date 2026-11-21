package vidasalud.ms_vidasalud_audit.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import vidasalud.ms_vidasalud_audit.dto.AuditoriaStreamDTO;
import vidasalud.ms_vidasalud_audit.dto.CitaEventoDTO;
import vidasalud.ms_vidasalud_audit.entity.AuditEvent;
import vidasalud.ms_vidasalud_audit.service.AuditService;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CitaKafkaConsumer {

    private final AuditService auditService;
    private final AuditStreamKafkaProducer streamProducer;
    private final ObjectMapper kafkaObjectMapper;

    @KafkaListener(
            topics = "${vidasalud.kafka.topic.citas:vidasalud.citas.eventos}",
            groupId = "audit-citas-group"
    )
    public void consumirEventoCita(String payload) throws Exception {
        if (payload == null || payload.isBlank()) {
            throw new IllegalArgumentException("Payload vacio en topico de citas");
        }

        CitaEventoDTO dto = kafkaObjectMapper.readValue(payload, CitaEventoDTO.class);
        if (dto.getEventId() == null || dto.getTipoEvento() == null) {
            throw new IllegalArgumentException("Evento de cita invalido (faltan campos obligatorios): " + payload);
        }

        String detalle = String.format("Atencion ID=%s | Paciente=%s | Doctor=%s | Fecha=%s",
                dto.getAtencionId(), dto.getPaciente(), dto.getDoctor(), dto.getHoraFecha());

        AuditEvent entidad = AuditEvent.builder()
                .eventId(dto.getEventId())
                .eventType(dto.getTipoEvento())
                .userId(dto.getPaciente() != null ? dto.getPaciente() : "SISTEMA")
                .userRole("KAFKA_STREAM")
                .details(detalle)
                .timestamp(dto.getTimestamp() != null ? dto.getTimestamp() : LocalDateTime.now())
                .build();

        auditService.registrarEvento(entidad);
        log.info("[KAFKA-CONSUMER][audit-citas-group] Auditoria persistida en Oracle para eventId={}", dto.getEventId());

        streamProducer.publicarStreamAuditoria(AuditoriaStreamDTO.builder()
                .streamId(UUID.randomUUID().toString())
                .origenServicio("ms-vidasalud-audit")
                .tipoEvento(dto.getTipoEvento())
                .entidadId(dto.getAtencionId() != null ? dto.getAtencionId().toString() : "N/A")
                .resumen(detalle)
                .timestamp(LocalDateTime.now())
                .build());
    }
}
