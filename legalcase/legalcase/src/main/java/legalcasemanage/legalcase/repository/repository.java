package legalcasemanage.legalcase.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import legalcasemanage.legalcase.model.LoyerModel;

@Repository
public interface repository extends JpaRepository<LoyerModel, Long> {
	
	//optional is used to avoid null pointer or exception , mainly is used for overcome from thease, (is important go through this), serch weather present or not
	Optional<LoyerModel> findByEmail(String email);

}


