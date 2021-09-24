package edu.strongsubgroup.agreement.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "provider")
public class Provider extends AbstractEntity<Long> {

    @Column(name = "guid")
    private String guid;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "provider")
    private List<Agreement> agreements;

    @Builder
    public Provider(Long id, String guid, String name, String phoneNumber, boolean isActive,
                    LocalDateTime createdAt, List<Agreement> agreements) {
        setId(id);
        this.guid = guid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }
}
