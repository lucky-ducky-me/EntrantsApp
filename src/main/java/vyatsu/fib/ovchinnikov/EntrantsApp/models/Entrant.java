package vyatsu.fib.ovchinnikov.EntrantsApp.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Абитуриент.
 */
public class Entrant {

    //region Поля.

    /**
     * Id.
     */
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    @NotNull
    private UUID id;

    /**
     * Имя.
     */
    @Getter(AccessLevel.PUBLIC)
    @Size(min=2, max=30)
    private String name;

    /**
     * Фамилия.
     */
    @Getter(AccessLevel.PUBLIC)
    @Size(min=2, max=30)
    private String surname;

    /**
     * Отчество.
     */
    @Getter(AccessLevel.PUBLIC)
    @Size(min=2, max=30)
    private String patronymic;

    /**
     * День рождения.
     */
    @Getter(AccessLevel.PUBLIC)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthday;

    //endregion

    //region Конструкторы.
    /**
     * Создание абитуриента с параметрами по умолчанию.
     */
    public Entrant() {
        setId(UUID.randomUUID());
    }

    /**
     * Создание абитуриента с указанными параметрами.
     * @param id id.
     * @param name имя.
     * @param surname фамилия.
     * @param patronymic отчество.
     * @param birthday день рождения.
     */
    public Entrant(UUID id, String name, String surname, String patronymic, LocalDate birthday) {
        setId(id);
        setName(name);
        setSurname(surname);
        setPatronymic(patronymic);
        setBirthday(birthday);
    }

    //endregion

    //region Сеттеры.

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Имя не определено.");
        }

        this.name = name;
    }

    public void setSurname(String surname) {
        if (name == null) {
            throw new IllegalArgumentException("Фамилия не определено.");
        }

        this.surname = surname;
    }

    public void setPatronymic(String patronymic) {
        if (name == null) {
            throw new IllegalArgumentException("Отчество не определено.");
        }

        this.patronymic = patronymic;
    }

    public void setBirthday(LocalDate birthday) {
        if (birthday.compareTo(LocalDate.now()) > 0) {
            throw new IllegalArgumentException("День рождения не может быть позже текущей даты.");
        }

        this.birthday = birthday;
    }

    //endregion

    /**
     * Получени дня рождения в строковом формате.
     * @return строка с датой.
     */
    public String getBirthdayString() {
        var dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        return dateFormat.format(getBirthday());
    }
}
