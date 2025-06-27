package legalcasemanage.legalcase.model;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class LawyerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String full_name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String Confirm_password;

    private String role;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // Automatically set createdAt before persisting
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
        this.Confirm_password = confirm_password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

	public void setCreatedAt(Date createdAt) {
		// TODO Auto-generated method stub
		this.createdAt = createdAt;
	}
}



