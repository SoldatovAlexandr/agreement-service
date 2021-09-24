package edu.strongsubgroup.agreement.repository;

import edu.strongsubgroup.agreement.model.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AgreementRepository extends JpaRepository<Agreement, Long>, JpaSpecificationExecutor<Agreement> {
}
