package vyatsu.fib.ovchinnikov.EntrantsApp.dataBaseProviders.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.UUID;

/**
 * Сущность для абитуриента.
 */
@Entity
@Table(name = "Entrant")
public class EntrantEntity {

    /**
     * Id.
     */
    @Id
    @Column(name = "Id", nullable = false)
    @Getter
    @Setter
    private UUID id;


    /**
     * Имя.
     */
    @Column(name = "Name", length = 30, nullable = false)
    @Getter
    @Setter
    private String name;

    /**
     * Фамилия.
     */
    @Column(name = "Surname", length = 30, nullable = false)
    @Getter
    @Setter
    private String surname;

    /**
     * Отчество.
     */
    @Column(name = "Patronymic", length = 30, nullable = true)
    @Getter
    @Setter
    private String patronymic;

    /**
     * Дата рождения.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "Birthday", nullable = false)
    @Getter
    @Setter
    private Date birthday;
}
