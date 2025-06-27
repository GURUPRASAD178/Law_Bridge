package legalcasemanage.legalcase.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    
    
    
    @GetMapping("/admin")
    public String adminDashboard(Model model) {
    	List<LawyerModel> recentUsers = lawyerRepository.findTop5ByOrderByCreatedAtDesc();
    	model.addAttribute("recentUsers", recentUsers);
        return "admin"; // template name
    }
    
    @GetMapping("/lawyers")
    public String listLawyers(Model model) {
        List<LawyerModel> lawyers = lawyerService.findAllLawyers(); // This should return only users with role = LAWYER
        model.addAttribute("lawyers", lawyers);
        return "lawyer_list";
    }

    @GetMapping("/lawyer/edit/{id}")
    public String editLawyer(@PathVariable Long id, Model model) {
        LawyerModel lawyer = lawyerService.getById(id);
        model.addAttribute("lawyer", lawyer);
        return "edit_lawyer";
    }

    @GetMapping("/lawyer/delete/{id}")
    public String deleteLawyer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        lawyerService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Lawyer deleted successfully.");
        return "redirect:/lawyers";
    }

    @GetMapping("/clients")
    public String showClientList(Model model) {
        List<LawyerModel> clients = lawyerRepository.findByRole("client");
        model.addAttribute("clients", clients);
        return "client_list";
    }

    @GetMapping("/clients/edit/{id}")
    public String editClient(@PathVariable Long id, Model model) {
        LawyerModel client = lawyerRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid client ID: " + id));
        model.addAttribute("client", client);
        return "edit_client"; // you’ll create this page next
    }

    @GetMapping("/clients/delete/{id}")
    public String deleteClient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        lawyerRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Client deleted successfully.");
        return "redirect:/clients";
    }

    @PostMapping("/clients/update")
    public String updateClient(@ModelAttribute("client") LawyerModel client, RedirectAttributes redirectAttributes) {
        lawyerRepository.save(client);
        redirectAttributes.addFlashAttribute("success", "Client updated successfully.");
        return "redirect:/clients";
    }

    @GetMapping("/add-lawyer")
    public String showAddLawyerForm(Model model) {
        model.addAttribute("lawyer", new LawyerModel());
        return "add_lawyer";
    }

    @PostMapping("/lawyers/save")
    public String saveLawyer(@ModelAttribute("lawyer") LawyerModel lawyer, RedirectAttributes redirectAttributes) {
        lawyer.setRole("lawyer");
        lawyerRepository.save(lawyer);
        redirectAttributes.addFlashAttribute("success", "Lawyer added successfully!");
        return "redirect:/lawyers";
    }

    @GetMapping("/add-client")
    public String showAddClientForm(Model model) {
        model.addAttribute("client", new LawyerModel());
        return "add_client";
    }

    @PostMapping("/clients/save")
    public String saveClient(@ModelAttribute("client") LawyerModel client, RedirectAttributes redirectAttributes) {
        client.setRole("client");
        lawyerRepository.save(client);
        redirectAttributes.addFlashAttribute("success", "Client added successfully!");
        return "redirect:/clients";
    }

    
    
    
    
    @GetMapping("/lawyer_dashboard")
    public String lawyerDashboard() {
        return "lawyer_dashboard";
    }

    @GetMapping("/client_dashboard")
    public String clientDashboard() {
        return "client_dashboard";
    }

}
