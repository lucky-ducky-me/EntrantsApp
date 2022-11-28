package vyatsu.fib.ovchinnikov.EntrantsApp.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
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
    @NonNull
    private UUID id;

    /**
     * Предмет.
     */
    @Getter(AccessLevel.PUBLIC)
    @NonNull
    private String subject;

    //endregion

    //region Конструкторы.

    /**
     * Создание экзамена с параметрами по умолчанию.
     */
    public Exam() {
    }

    /**
     * Создание экзамена с указанными параметрами.
     * @param subject предмет.
     */
    public Exam(UUID id, String subject) {
        setId(id);
        setSubject(subject);
    }

    //endregion

    //region Сеттеры.

    public void setSubject(String subject) {
        if (subject == null) {
            throw new IllegalArgumentException("Предмет не указан.");
        }

        this.subject = subject;
    }

    //endregion
}
