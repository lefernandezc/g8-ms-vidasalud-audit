package vidasalud.ms_vidasalud_audit.consumer;

import vidasalud.ms_vidasalud_audit.dto.AuditEventDTO;
import vidasalud.ms_vidasalud_audit.entity.AuditEvent;
import vidasalud.ms_vidasalud_audit.repository.AuditEventRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditKafkaConsumer {

    private final AuditEventRepository auditRepository;

    public AuditKafkaConsumer(AuditEventRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @KafkaListener(topics = {"appointments.events", "audit.timeline"}, groupId = "audit-group")
    public void consumirEvento(AuditEventDTO eventDTO) {
        System.out.println("-> Evento recibido desde Kafka: " + eventDTO.getEventType());

        AuditEvent entity = AuditEvent.builder()
                .eventId(eventDTO.getEventId())
                .eventType(eventDTO.getEventType())
                .userId(eventDTO.getUserId())
                .userRole(eventDTO.getUserRole())
                .details(eventDTO.getDetails())
                .timestamp(LocalDateTime.now())
                .build();

        auditRepository.save(entity);
    }
}