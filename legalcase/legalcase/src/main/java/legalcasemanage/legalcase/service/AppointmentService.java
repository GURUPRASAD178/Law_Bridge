package legalcasemanage.legalcase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import legalcasemanage.legalcase.model.Appointment;
import legalcasemanage.legalcase.model.LawyerModel;
import legalcasemanage.legalcase.repository.AppointmentRepository;

@Service
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository repository;

    public void bookAppointment(Appointment appointment) {
        appointment.setStatus("PENDING");
        repository.save(appointment);
    }

    public List<Appointment> getAppointmentsByClient(Long clientId) {
        return repository.findByClientId(clientId);
    }

    public List<Appointment> getAppointmentsByLawyer(Long lawyerId) {
        return repository.findByLawyerId(lawyerId);  // ✅ Add this
    }

}
