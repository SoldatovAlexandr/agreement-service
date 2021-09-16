package edu.strongsubgroup.agreement.repository.specification;

import edu.strongsubgroup.agreement.model.Merchant;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(path = "name", spec = Like.class),
        @Spec(path = "phoneNumber", spec = Like.class),
        @Spec(path = "guid", spec = Like.class),
        @Spec(path = "isActive", spec = Equal.class)
})
public interface MerchantSpecification extends Specification<Merchant> {
}
