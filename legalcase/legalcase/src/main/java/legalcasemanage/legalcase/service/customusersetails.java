package legalcasemanage.legalcase.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import legalcasemanage.legalcase.model.LoyerModel;

public class customusersetails implements UserDetails {
	
	private LoyerModel loyermodel;
	
	
	public customusersetails(LoyerModel loyermodel) {
		super();
		this.loyermodel = loyermodel;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return List.of(new SimpleGrantedAuthority(loyermodel.getRole()));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return loyermodel.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return loyermodel.getEmail();
	}

}
