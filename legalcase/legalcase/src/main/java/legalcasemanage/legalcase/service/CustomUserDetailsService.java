package legalcasemanage.legalcase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import legalcasemanage.legalcase.model.LawyerModel;
import legalcasemanage.legalcase.repository.LawyerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final LawyerRepository lawyerRepository;

    @Autowired
    public CustomUserDetailsService(LawyerRepository lawyerRepository) {
        this.lawyerRepository = lawyerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LawyerModel user = lawyerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        
        return new CustomUserDetails(user);
    }
}
