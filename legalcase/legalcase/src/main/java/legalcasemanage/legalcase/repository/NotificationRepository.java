package legalcasemanage.legalcase.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import legalcasemanage.legalcase.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findTop5ByOrderByCreatedAtDesc();
    List<Notification> findByIsReadFalse();
}
