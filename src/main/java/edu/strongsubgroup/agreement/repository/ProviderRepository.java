package edu.strongsubgroup.agreement.repository;

import edu.strongsubgroup.agreement.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProviderRepository extends JpaRepository<Provider, Long>, JpaSpecificationExecutor<Provider> {

    Optional<Provider> findByGuid(String guid);

}
