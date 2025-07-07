package legalcasemanage.legalcase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import legalcasemanage.legalcase.model.LegalCase;
import legalcasemanage.legalcase.repository.CaseRepository;

@Service
public class CaseService {
    @Autowired
    private CaseRepository caseRepository;

    public void saveNewCase(Long clientId, Long lawyerId, String caseTitle, String description) {
        LegalCase legalCase = new LegalCase();
        legalCase.setClientId(clientId);
        legalCase.setLawyerId(lawyerId);
        legalCase.setCaseTitle(caseTitle);
        legalCase.setDescription(description);
        caseRepository.save(legalCase);
    }
}
