package edu.strongsubgroup.agreement.model;

import edu.strongsubgroup.agreement.common.UserRoles;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder

@Entity
@Table(name = "roles")
public class Role extends AbstractEntity<Long> {

    @Enumerated(EnumType.STRING)
    @Column(name = "name", unique = true, nullable = false)
    private UserRoles name;
}
