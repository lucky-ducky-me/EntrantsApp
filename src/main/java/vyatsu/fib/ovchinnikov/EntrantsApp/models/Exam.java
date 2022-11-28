package vyatsu.fib.ovchinnikov.EntrantsApp.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Экзамен.
 */
public class Exam {

    //region Поля.

    /**
     * Id.
     */
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private UUID id;

    /**
     * Предмет.
     */
    @Getter(AccessLevel.PUBLIC)
    private String subject;

    /**
     * Количество баллов.
     */
    @Getter(AccessLevel.PUBLIC)
    private Integer score;

    //endregion

    //region Конструкторы.

    /**
     * Создание экзамена с параметрами по умолчанию.
     */
    public Exam() {}

    /**
     * Создание экзамена с указанными параметрами.
     * @param subject предмет.
     * @param score количество предметов.
     */
    public Exam(UUID id, String subject, Integer score) {
        setId(id);
        setSubject(subject);
        setScore(score);
    }

    //endregion

    //region Сеттеры.

    public void setSubject(String subject) {
        if (subject == null) {
            throw new IllegalArgumentException("Предмет не указан.");
        }

        this.subject = subject;
    }

    public void setScore(Integer score) {
        if (score < 0) {
            throw new IllegalArgumentException("Количество баллов не может быть отрицательным.");
        }

        this.score = score;
    }

    //endregion
}
