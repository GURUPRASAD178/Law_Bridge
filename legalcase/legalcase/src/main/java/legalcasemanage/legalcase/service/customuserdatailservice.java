package legalcasemanage.legalcase.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import legalcasemanage.legalcase.model.LoyerModel;
import legalcasemanage.legalcase.repository.repository;

public class customuserdatailservice implements UserDetailsService {
	
	private repository repo;
	
	
	public customuserdatailservice(repository repo) {
		super();
		this.repo = repo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		LoyerModel loyermodel=repo.findByEmail(username)
				.orElseThrow(
						()-> new UsernameNotFoundException("username not found:"+username));
				
		return new customusersetails(loyermodel);
	}
	

}
