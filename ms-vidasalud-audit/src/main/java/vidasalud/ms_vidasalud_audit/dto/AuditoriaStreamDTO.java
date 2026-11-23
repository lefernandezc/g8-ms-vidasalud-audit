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
public class AuditoriaStreamDTO {
    private String streamId;
    private String origenServicio;
    private String tipoEvento;
    private String entidadId;
    private String resumen;
    private LocalDateTime timestamp;
}
