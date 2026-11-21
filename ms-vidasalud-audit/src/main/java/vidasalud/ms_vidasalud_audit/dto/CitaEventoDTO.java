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
public class CitaEventoDTO {
    private String eventId;
    private String tipoEvento;
    private Long atencionId;
    private String paciente;
    private String doctor;
    private LocalDateTime horaFecha;
    private LocalDateTime timestamp;
}
