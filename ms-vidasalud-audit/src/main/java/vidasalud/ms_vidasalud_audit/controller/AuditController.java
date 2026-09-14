package vidasalud.ms_vidasalud_audit.controller;

import vidasalud.ms_vidasalud_audit.entity.AuditEvent;
import vidasalud.ms_vidasalud_audit.repository.AuditEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")

public class AuditController {
    @Autowired
    private AuditEventRepository auditRepository;

    @GetMapping("/timeline")
    public List<AuditEvent> obtenerTimeline() {
        return auditRepository.findAll();
    }

    @GetMapping("/by-user/{userId}")
    public List<AuditEvent> obtenerPorUsuario(@PathVariable String userId) {
        return auditRepository.findByUserId(userId);
    }
}
