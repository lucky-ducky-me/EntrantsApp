package vyatsu.fib.ovchinnikov.EntrantsApp.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Обёртка для списка экзаменов.
 */
public class ExamCreationDto {

    /**
     * Список экзаменов.
     */
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private ArrayList<Exam> exams;

    /**
     * Создание обёртки над экзаменами.
     * @param exams список экзаменов.
     */
    public ExamCreationDto(ArrayList<Exam> exams) {
        setExams(exams);
    }
}
