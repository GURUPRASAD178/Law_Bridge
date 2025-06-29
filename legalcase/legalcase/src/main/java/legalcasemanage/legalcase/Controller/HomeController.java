package legalcasemanage.legalcase.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import legalcasemanage.legalcase.DTO.LawyerDTO;
import legalcasemanage.legalcase.model.Appointment;
import legalcasemanage.legalcase.model.LawyerModel;
import legalcasemanage.legalcase.repository.LawyerRepository;
import legalcasemanage.legalcase.service.AppointmentService;
import legalcasemanage.legalcase.service.LawyerService;

@Controller
public class HomeController {

    private final LawyerService lawyerService;
    private LawyerRepository lawyerRepository;
    private AppointmentService appointmentService;



    @Autowired
    public HomeController(LawyerService lawyerService, LawyerRepository lawyerRepository,
			AppointmentService appointmentService) {
		super();
		this.lawyerService = lawyerService;
		this.lawyerRepository = lawyerRepository;
		this.appointmentService = appointmentService;
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

    @GetMapping("/lawyers/edit/{id}")
    public String editLawyer(@PathVariable Long id, Model model) {
        LawyerModel lawyer = lawyerService.getById(id);
        model.addAttribute("lawyer", lawyer);
        return "edit_lawyer";
    }

    @GetMapping("/lawyers/delete/{id}")
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

    @GetMapping("/lawyer/clients")
    public String lawyerYourClients(Model model) {
        // Fetch and add clients data to the model
        return "lawyer-your-clients"; // Corresponds to src/main/resources/templates/lawyer-your-clients.html
    }

    @GetMapping("/lawyer/cases")
    public String lawyerMyCases(Model model) {
        // Fetch and add cases data to the model
        return "lawyer-my-cases"; // Corresponds to src/main/resources/templates/lawyer-my-cases.html
    }
    
    @GetMapping("/lawyer/messages")
    public String lawyerMessages(Model model) {
        // Fetch and add messages data to the model
        return "lawyer-messages"; // Corresponds to src/main/resources/templates/lawyer-messages.html
    }

    @GetMapping("/lawyer/schedule")
    public String lawyerUpcomingSchedule(Model model) {
        // Fetch and add schedule data to the model
        return "lawyer-upcoming-schedule"; // Corresponds to src/main/resources/templates/lawyer-upcoming-schedule.html
    }

    // You might also have a profile edit page for lawyers
    @GetMapping("/lawyer/profile")
    public String lawyerProfile(Model model) {
        // Fetch lawyer profile data
        return "lawyer-profile"; // Assuming you create this HTML
    }
    
    @GetMapping("/client_dashboard")
    public String clientDashboard() {
        return "client_dashboard";
    }
    
    @GetMapping("/client/cases")
    public String clientMyLegalCases(Model model) {
        // Fetch and add client's cases data to the model
        return "client-my-legal-cases"; // Corresponds to src/main/resources/templates/client-my-legal-cases.html
    }

    @GetMapping("/client/messages")
    public String clientYourMessages(Model model) {
        // Fetch and add client's messages data to the model
        return "client-your-messages"; // Corresponds to src/main/resources/templates/client-your-messages.html
    }

    
    @GetMapping("/client/lawyers")
    public String showLawyers(Model model) {
        model.addAttribute("lawyers", lawyerService.findAllLawyers());
        return "client_lawyer_list";
    }

    @PostMapping("/client/book")
    public String book(@RequestParam Long clientId, @RequestParam Long lawyerId,
                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date appointmentDate) {
        Appointment appointment = new Appointment();
        appointment.setClientId(clientId);
        appointment.setLawyerId(lawyerId);
        appointment.setAppointmentDate(appointmentDate);
        appointmentService.bookAppointment(appointment);
        return "redirect:/client/lawyers?success";
    }

}
