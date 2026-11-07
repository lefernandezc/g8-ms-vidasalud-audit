package vidasalud.ms_vidasalud_audit.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AuditEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventId;
    private String eventType;
    private String userId;
    private String userRole;
    private String details;
    private LocalDateTime timestamp;
}
