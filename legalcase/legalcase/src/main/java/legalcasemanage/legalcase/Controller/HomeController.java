package legalcasemanage.legalcase.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import legalcasemanage.legalcase.DTO.LawyerDTO;
import legalcasemanage.legalcase.model.LawyerModel;
import legalcasemanage.legalcase.repository.LawyerRepository;
import legalcasemanage.legalcase.service.LawyerService;

@Controller
public class HomeController {

    private final LawyerService lawyerService;
    private LawyerRepository lawyerRepository;


    @Autowired
    public HomeController(LawyerService lawyerService, LawyerRepository lawyerRepository) {
        this.lawyerService = lawyerService;
        this.lawyerRepository = lawyerRepository;
    }


    
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/service")
    public String services() {
        return "service";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
    	LawyerDTO lawyerDTO = new LawyerDTO();
        model.addAttribute("lawyerDTO", lawyerDTO);
        return "register";
    }

    @PostMapping("/register")
	public String register(@ModelAttribute LawyerDTO lawyerDTO , RedirectAttributes redirectAttributes, Model model) {
		if (lawyerRepository.existsByEmail(lawyerDTO.getEmail())) {	
			model.addAttribute("emailError", "Email already registered");
    	    return "register";
    	}

    	lawyerService.registerUser(lawyerDTO);
		redirectAttributes.addFlashAttribute("success", "Registered successfully!");
		return "redirect:/login";
	}

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin-dashboard")
    public String adminDashboard() {
        return "admin_dashboard";
    }

    @GetMapping("/ld")
    public String lawyerDashboard() {
        return "lawyer_dashboard";
    }

    @GetMapping("/cd")
    public String clientDashboard() {
        return "client_dashboard";
    }

}
