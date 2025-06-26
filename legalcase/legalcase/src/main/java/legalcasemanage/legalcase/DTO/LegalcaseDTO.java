package legalcasemanage.legalcase.DTO;

public class LegalcaseDTO {
	private String full_name;
	private String email;
	private String password;
	private String Confirm_password;
	private String role;
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirm_password() {
		return Confirm_password;
	}
	public void setConfirm_password(String confirm_password) {
		Confirm_password = confirm_password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	

}
