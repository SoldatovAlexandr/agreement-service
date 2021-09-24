package edu.strongsubgroup.agreement.repository.specification;

import edu.strongsubgroup.agreement.model.Agreement;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThan;
import net.kaczmarzyk.spring.data.jpa.domain.LessThan;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Join(path = "provider", alias = "p")
@Join(path = "merchant", alias = "m")
@And({
        @Spec(path = "p.guid", params = "provider", spec = Like.class),
        @Spec(path = "m.guid", params = "merchant", spec = Like.class),
        @Spec(path = "description", spec = Like.class),
        @Spec(path = "status", spec = Equal.class),
        @Spec(path = "createdAt", params = "date-from", spec = GreaterThan.class),
        @Spec(path = "createdAt", params = "date-to", spec = LessThan.class),
})
public interface AgreementSpecification extends Specification<Agreement> {
}
