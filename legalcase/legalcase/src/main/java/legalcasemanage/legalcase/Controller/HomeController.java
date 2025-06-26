package legalcasemanage.legalcase.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import legalcasemanage.legalcase.DTO.LegalcaseDTO;
import legalcasemanage.legalcase.service.legalservice;

@Controller
public class HomeController {
	private legalservice caseservice;
	
	
	public HomeController(legalservice caseservice) {
		super();
		this.caseservice = caseservice;
	}
	
	@GetMapping({"/"," "})
	 public String home() {
		 return "index";
	 }
	@GetMapping({"/about"})
	 public String about() {
		 return "about";
	 }
	
	@GetMapping({"/contact"})
	 public String contact() {
		 return "contact";
	 }
	
	@GetMapping({"/admin"})
	 public String admin() {
		 return "Admin";
	 }
	@GetMapping({"/register"})
	 public String register(Model model) {
		LegalcaseDTO legalDTO = new LegalcaseDTO();
		model.addAttribute("legalDTO",legalDTO);
		 return "Register";
	 }
	
	@PostMapping("/register")
	public String register(@ModelAttribute LegalcaseDTO legalDTO , RedirectAttributes redirectAttributes) {
		caseservice.storeCase(legalDTO);
		redirectAttributes.addFlashAttribute("success","student saved succefully");
		return "redirect:/login";
	}
	
	@GetMapping({"/login"})
	 public String Login() {
		 return "Login";
	 }
	@GetMapping({"/add-lawyer"})
	 public String addlawyer() {
		 return "add_lawyer";
	 }
	@GetMapping({"/add-client"})
	 public String addclient() {
		 return "add_client";
	 }
	
	@GetMapping({"/service"})
	 public String services() {
		 return "service";
	 }
	

}
