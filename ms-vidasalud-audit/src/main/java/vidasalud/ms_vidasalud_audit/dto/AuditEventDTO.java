package vidasalud.ms_vidasalud_audit.dto;

import lombok.Data;

@Data
public class AuditEventDTO {
    private String eventId;
    private String eventType;
    private String userId;
    private String userRole;
    private String details;
    private String timestamp;
}
