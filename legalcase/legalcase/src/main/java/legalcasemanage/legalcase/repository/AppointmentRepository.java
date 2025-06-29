package legalcasemanage.legalcase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import legalcasemanage.legalcase.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByClientId(Long clientId);
}

