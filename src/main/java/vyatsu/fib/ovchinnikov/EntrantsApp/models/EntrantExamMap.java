package vyatsu.fib.ovchinnikov.EntrantsApp.models;

import lombok.AccessLevel;
import lombok.Getter;
import org.javatuples.Pair;

import java.util.ArrayList;

/**
 * Промежуточная связь между абитуриентом и экзаменом.
 */
public class EntrantExamMap {

    /**
     * Список связей между абитуриентоми и экзаменоми.
     */
    @Getter(AccessLevel.PUBLIC)
    private ArrayList<Pair<Entrant, Exam>> entrantExamMap = new ArrayList<>();

    /**
     * Добавления связи между абитуриентом и экзаменом.
     * @param entrantExamPair связь между абитуриентом и экзаменом.
     * @return успех вставки.
     */
    public boolean addEntrantExamPair(Pair<Entrant, Exam> entrantExamPair) {
        if (entrantExamMap.contains(entrantExamPair)) {
            return false;
        }

        entrantExamMap.add(entrantExamPair);

        return entrantExamMap.contains(entrantExamPair);
    }
}
