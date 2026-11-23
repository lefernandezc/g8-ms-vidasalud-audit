package vidasalud.ms_vidasalud_audit.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import vidasalud.ms_vidasalud_audit.dto.AuditoriaStreamDTO;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditStreamKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper kafkaObjectMapper;

    @Value("${vidasalud.kafka.topic.auditoria:vidasalud.auditoria.stream}")
    private String auditoriaTopic;

    public void publicarStreamAuditoria(AuditoriaStreamDTO streamDto) {
        try {
            String json = kafkaObjectMapper.writeValueAsString(streamDto);
            kafkaTemplate.send(auditoriaTopic, streamDto.getStreamId(), json);
            log.info("[KAFKA-PRODUCER][audit] Evento publicado en {}: streamId={}", auditoriaTopic, streamDto.getStreamId());
        } catch (Exception e) {
            log.error("[KAFKA-PRODUCER][audit] Error publicando en {}: {}", auditoriaTopic, e.getMessage(), e);
        }
    }
}
