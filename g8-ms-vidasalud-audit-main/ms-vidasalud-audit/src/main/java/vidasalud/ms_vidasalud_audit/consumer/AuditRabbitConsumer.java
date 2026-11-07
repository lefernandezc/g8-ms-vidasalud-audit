package vidasalud.ms_vidasalud_audit.consumer;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import vidasalud.ms_vidasalud_audit.dto.AuditEnvelope;
import vidasalud.ms_vidasalud_audit.entity.AuditEvent;
import vidasalud.ms_vidasalud_audit.service.AuditService;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditRabbitConsumer {

    private final AuditService auditService;

    @RabbitListener(queues = "${messaging.queue.audit}")
    public void processAudit(AuditEnvelope envelope, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            log.info("Procesando evento de auditoría ID: {}", envelope.getEventId());

            AuditEvent auditEvent = AuditEvent.builder()
                    .eventId(envelope.getEventId())
                    .eventType(envelope.getType())
                    .details(envelope.getPayload() != null ? envelope.getPayload().toString() : "Sin detalles")
                    .timestamp(envelope.getTimestamp() != null ? envelope.getTimestamp() : LocalDateTime.now())
                    .userId(envelope.getTraceId() != null ? envelope.getTraceId() : "SISTEMA")
                    .userRole("APP_SERVICE")
                    .build();

            auditService.registrarEvento(auditEvent);
            channel.basicAck(tag, false);

        } catch (Exception e) {
            log.error("Error procesando evento de auditoría: {}", e.getMessage(), e);
            channel.basicNack(tag, false, false);
        }
    }
}