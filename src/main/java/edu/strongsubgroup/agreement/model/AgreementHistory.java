package edu.strongsubgroup.agreement.model;


import edu.strongsubgroup.agreement.common.AgreementStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "agreement_history")
public class AgreementHistory extends AbstractEntity<Long> {

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AgreementStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "agreement_id", nullable = false)
    private Agreement agreement;

}
