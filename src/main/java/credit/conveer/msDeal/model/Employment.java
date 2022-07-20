package credit.conveer.msDeal.model;

import credit.conveer.msDeal.model.Enum.EmploymentStatus;
import credit.conveer.msDeal.model.Enum.Position;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.EnumType.STRING;

/**
 * Работа
 */
@Entity
@Table(name = "employment")
@Getter
@Setter
@Accessors(chain = true)
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employment_status")
    @Enumerated(STRING)
    private   EmploymentStatus employment_status; //(Рабочий статус) UNEMPLOYED (безработный) SELF_EMPLOYED (самозанятый) EMPLOYED (трудоустроен) BUSINESS_OWNER (владелец бизнеса)
    private   String employer;// работодатель
    private BigDecimal salary;// зарплата

    @Column(name = "position")
    @Enumerated(STRING)
    private  Position position; //должность WORKER (работник) MID_MANAGER (менеджер среднего звена) TOP_MANAGER (топ-менеджер) OWNER (владелец)
    private  Integer work_experience_total; //(общий опыт работы)
    private  Integer work_experience_current; //опыт работы на текущем месте

    @OneToOne()
    @JoinColumn(name="client_id")
    private Client clientEmpl;
}
