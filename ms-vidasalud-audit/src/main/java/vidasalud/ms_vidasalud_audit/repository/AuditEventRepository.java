package vidasalud.ms_vidasalud_audit.repository;

import vidasalud.ms_vidasalud_audit.entity.AuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditEventRepository extends JpaRepository<AuditEvent, Long> {
    List<AuditEvent> findByUserId(String userId);
    List<AuditEvent> findByEventType(String eventType);
}