package vidasalud.ms_vidasalud_audit.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import vidasalud.ms_vidasalud_audit.dto.AuditoriaStreamDTO;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditStreamAnalyticsConsumer {

    private final ObjectMapper kafkaObjectMapper;

    @KafkaListener(
            topics = "${vidasalud.kafka.topic.auditoria:vidasalud.auditoria.stream}",
            groupId = "audit-analytics-group"
    )
    public void procesarAnaliticaAuditoria(String payload) throws Exception {
        AuditoriaStreamDTO stream = kafkaObjectMapper.readValue(payload, AuditoriaStreamDTO.class);
        if (stream.getStreamId() == null) {
            throw new IllegalArgumentException("StreamId nulo en analitica de auditoria");
        }
        log.info("[KAFKA-ANALYTICS][audit-analytics-group] Traza auditada -> origen={}, evento={}, entidad={}",
                stream.getOrigenServicio(), stream.getTipoEvento(), stream.getEntidadId());
    }
}
