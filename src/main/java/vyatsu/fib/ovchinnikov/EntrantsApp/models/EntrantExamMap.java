package vyatsu.fib.ovchinnikov.EntrantsApp.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Промежуточная связь между абитуриентом и экзаменом.
 */
public class EntrantExamMap {

    /**
     * Id связи.
     */
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private UUID entrantExamId;

    /**
     * Id абитуриента.
     */
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private UUID entrantId;

    /**
     * Id экзамена.
     */
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private UUID examId;

    /**
     * Создание связи с указанными параментарми.
     * @param id id связи.
     * @param entrant id абитуриента.
     * @param exam id экзамена.
     */
    public EntrantExamMap(UUID id, Entrant entrant, Exam exam) {
        setEntrantExamId(id);
        setEntrantId(entrant.getId());
        setExamId(exam.getId());
    }
}
