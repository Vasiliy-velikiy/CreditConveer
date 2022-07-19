package credit.conveer.msDeal.model;


import credit.conveer.msDeal.model.Enum.ChangeType;
import credit.conveer.msDeal.model.Enum.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.STRING;

@Table(name = "history")
@Entity
@Getter
@Setter
@Accessors(chain = true)
public class ApplicationStatusHistory {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "status")
    @Enumerated(STRING)
    private Status status;
    @Column(name = "time")
    private LocalDateTime time;
    @Column(name = "changeType")
    @Enumerated(STRING)
    private ChangeType changeType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {PERSIST})
    @JoinColumn(name = "application_id")
    private Application application;
}
