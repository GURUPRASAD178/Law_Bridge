package legalcasemanage.legalcase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import legalcasemanage.legalcase.DTO.LawyerDTO;
import legalcasemanage.legalcase.model.LawyerModel;
import legalcasemanage.legalcase.repository.LawyerRepository;

@Service
public class LawyerService {

    private final LawyerRepository lawyerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public LawyerService(LawyerRepository lawyerRepository) {
        this.lawyerRepository = lawyerRepository;
    }

    public void registerUser(LawyerDTO dto) {
        LawyerModel user = new LawyerModel();
        user.setFull_name(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setConfirm_password(null); // don't store this in DB
        user.setRole(dto.getRole());
        lawyerRepository.save(user);
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

}
