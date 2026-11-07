package vidasalud.ms_vidasalud_audit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditEnvelope {
    private String type;
    private String eventId;
    private LocalDateTime timestamp;
    private String traceId;
    private String correlationId;
    private Object payload; 
}