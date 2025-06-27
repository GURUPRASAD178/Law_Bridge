package legalcasemanage.legalcase.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import legalcasemanage.legalcase.DTO.LawyerDTO;
import legalcasemanage.legalcase.model.LawyerModel;
import legalcasemanage.legalcase.repository.LawyerRepository;

@Service
public class LawyerService {

    private final LawyerRepository lawyerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Autowired
    public LawyerService(LawyerRepository lawyerRepository,
                         PasswordEncoder passwordEncoder,
                         JavaMailSender mailSender) {
        this.lawyerRepository = lawyerRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    public void notifyAdminNewUser(LawyerModel lawyer) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("guruprasad.parvam@gmail.com");
        message.setSubject("New Registration");
        message.setText("A new user has registered: " + lawyer.getFull_name() + " (" + lawyer.getEmail() + ")");
        mailSender.send(message);
    }

    public void registerUser(LawyerDTO dto) {
        LawyerModel user = new LawyerModel();
        user.setFull_name(dto.getFullName());
        user.setEmail(dto.getEmail());
        
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);
        user.setConfirm_password(null);
        user.setRole(dto.getRole());
        user.setCreatedAt(new Date());

        // Save user
        lawyerRepository.save(user);

        // Send welcome email to user
        sendWelcomeEmail(dto.getEmail(), dto.getFullName(), dto.getPassword());

        notifyAdminNewUser(user);
    }

    public List<LawyerModel> findAllLawyers() {
        return lawyerRepository.findByRole("LAWYER");
    }

    public LawyerModel getById(Long id) {
        return lawyerRepository.findById(id).orElseThrow(() -> new RuntimeException("Lawyer not found"));
    }

    public void deleteById(Long id) {
        lawyerRepository.deleteById(id);
    }
    
    private void sendWelcomeEmail(String toEmail, String fullName, String rawPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Welcome to LawBridge!");
        message.setText("Dear " + fullName + ",\n\n"
            + "Thank you for registering with LawBridge.\n"
            + "Here are your login credentials:\n"
            + "Email: " + toEmail + "\n"
            + "Password: " + rawPassword + "\n\n"
            + "Please keep them safe.\n\n"
            + "Regards,\nLawBridge Team");

        mailSender.send(message);
    }

}

