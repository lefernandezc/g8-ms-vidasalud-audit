package vidasalud.ms_vidasalud_audit.service;

import vidasalud.ms_vidasalud_audit.dto.AuditEventDTO;
import vidasalud.ms_vidasalud_audit.entity.AuditEvent;
import vidasalud.ms_vidasalud_audit.repository.AuditEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditService {

    private final AuditEventRepository auditRepository;

    public AuditService(AuditEventRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public void registrarEvento(AuditEventDTO dto) {
        AuditEvent entity = AuditEvent.builder()
                .eventId(dto.getEventId())
                .eventType(dto.getEventType())
                .userId(dto.getUserId())
                .userRole(dto.getUserRole())
                .details(dto.getDetails())
                .timestamp(LocalDateTime.now())
                .build();

        auditRepository.save(entity);
    }

    public List<AuditEvent> obtenerTimeline() {
        return auditRepository.findAll();
    }

    public List<AuditEvent> obtenerPorUsuario(String userId) {
        return auditRepository.findByUserId(userId);
    }
}