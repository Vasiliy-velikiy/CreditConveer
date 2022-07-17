package credit.conveer.msDeal.Model;


import credit.conveer.msDeal.Model.Enum.ChangeType;
import credit.conveer.msDeal.Model.Enum.Status;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.CascadeType.*;
import static javax.persistence.CascadeType.REFRESH;
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
    Status status;
    @Column(name = "time")
    LocalDateTime time;
    @Column(name = "changeType")
    @Enumerated(STRING)
    ChangeType changeType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {PERSIST, MERGE, DETACH, REFRESH})
    @JoinColumn(name = "application_id")
    Application application;
}
