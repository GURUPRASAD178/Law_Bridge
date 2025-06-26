package legalcasemanage.legalcase.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import legalcasemanage.legalcase.model.LawyerModel;

@Repository
public interface LawyerRepository extends JpaRepository<LawyerModel, Long> {
    
    // Optional helps avoid null pointer issues during email lookup
    Optional<LawyerModel> findByEmail(String email);
    boolean existsByEmail(String email);

}
