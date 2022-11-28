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
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PRIVATE)
    private UUID entrantExamId;

    /**
     * Id абитуриента.
     */
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PRIVATE)
    private UUID entrantId;

    /**
     * Id экзамена.
     */
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PRIVATE)
    private UUID examId;

    /**
     * Количесто баллов за экзамен.
     */
    @Getter(AccessLevel.PUBLIC)
    private Integer examScore;

    public EntrantExamMap() {
        setEntrantExamId(UUID.randomUUID());
    }

    /**
     * Создание связи с указанными параментарми.
     * @param id id связи.
     * @param entrant id абитуриента.
     * @param exam id экзамена.
     * @param examScore количество баллоа за экзамен.
     */
    public EntrantExamMap(UUID id, Entrant entrant, Exam exam, Integer examScore) {
        setEntrantExamId(id);
        setEntrantId(entrant.getId());
        setExamId(exam.getId());
    }

    public void setExamScore(Integer examScore) {
        if (examScore < 0) {
            throw new IllegalArgumentException("Количество баллов не может быть отрицательным.");
        }

        this.examScore = examScore;
    }

}
