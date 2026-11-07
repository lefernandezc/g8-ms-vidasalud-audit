package vidasalud.ms_vidasalud_audit.service;

import vidasalud.ms_vidasalud_audit.entity.AuditEvent;
import vidasalud.ms_vidasalud_audit.repository.AuditEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuditService {

    private final AuditEventRepository auditRepository;

    public AuditService(AuditEventRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Transactional
    public void registrarEvento(AuditEvent entity) {
        auditRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<AuditEvent> obtenerTimeline() {
        return auditRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<AuditEvent> obtenerPorUsuario(String userId) {
        return auditRepository.findByUserId(userId);
    }
}