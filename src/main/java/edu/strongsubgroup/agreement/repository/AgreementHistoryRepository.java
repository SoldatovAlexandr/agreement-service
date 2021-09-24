package edu.strongsubgroup.agreement.repository;

import edu.strongsubgroup.agreement.model.Agreement;
import edu.strongsubgroup.agreement.model.AgreementHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AgreementHistoryRepository extends JpaRepository<AgreementHistory, Long>, JpaSpecificationExecutor<AgreementHistory> {
    List<AgreementHistory> findByAgreement(Agreement agreement);
}
