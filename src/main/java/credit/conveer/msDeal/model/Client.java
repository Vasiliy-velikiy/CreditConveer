package credit.conveer.msDeal.model;

import credit.conveer.msDeal.model.Enum.Gender;
import credit.conveer.msDeal.model.Enum.MaritalStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.STRING;

/**
 * Клиент
 */
@Table(name = "client")
@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Client {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name")
    private String last_name; //Фамилия
    @Column(name = "first_name")
    private String first_name;//Имя
    @Column(name = "middle_name")
    private String middle_name; //Отчество
    @Column(name = "birth_date")
    private LocalDate birth_date; //Дата рождения
    @Column(name = "email")
    private String email;  //Email адрес

    @Column(name = "gender")
    @Enumerated(STRING)
    private Gender gender;

    @Column(name = "marital_status")
    @Enumerated(STRING)
    private MaritalStatus marital_status; //Семейное положение MARRIED женат/замужем DIVORCED (в разводе) SINGLE (холост) WIDOW_WIDOWER (вдова/вдовец)
    @Column(name = "dependent_amount")
    private Integer dependent_amount;//(Количество иждивенцев)
    @Column(name = "passport")
    private String passport; //Паспорт
    @Column(name = "series")
    private String series;// серия
    @Column(name = "number")
    private String number; //номер
    @Column(name = "issue_date")
    private LocalDate issue_date; //дата выдачи
    @Column(name = "issue_branch")
    private String issue_branch; //отделение

    @OneToOne(mappedBy = "clientEmpl")
    private Employment employment;//Работа

    @Column(name = "account")
    private String account; //Счет клиента

    @OneToMany(mappedBy = "clientApp",
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            cascade = {PERSIST})
    private List<Application> applications;

    public void addApplication(Application application) {
        application.setClientApp(this);
        this.applications.add(application);
    }
}
