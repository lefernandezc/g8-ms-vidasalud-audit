package vidasalud.ms_vidasalud_audit.controller;

import vidasalud.ms_vidasalud_audit.entity.AuditEvent;
import vidasalud.ms_vidasalud_audit.service.AuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/timeline")
    public ResponseEntity<List<AuditEvent>> obtenerTimeline() {
        return ResponseEntity.ok(auditService.obtenerTimeline());
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<AuditEvent>> obtenerPorUsuario(@PathVariable String userId) {
        return ResponseEntity.ok(auditService.obtenerPorUsuario(userId));
    }
}