package legalcasemanage.legalcase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import legalcasemanage.legalcase.model.LegalCase;

@Repository
public interface CaseRepository extends JpaRepository<LegalCase, Long> {
    List<LegalCase> findByClientId(Long clientId);
}
