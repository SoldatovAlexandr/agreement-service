package edu.strongsubgroup.agreement.repository;

import edu.strongsubgroup.agreement.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant, Long>, JpaSpecificationExecutor<Merchant> {

    Optional<Merchant> findByGuid(String guid);

}
