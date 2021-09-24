package edu.strongsubgroup.agreement.model;

import edu.strongsubgroup.agreement.common.AgreementStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "agreement")
public class Agreement extends AbstractEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AgreementStatus status;

    @OneToMany(mappedBy = "agreement")
    private List<AgreementHistory> history;
}
