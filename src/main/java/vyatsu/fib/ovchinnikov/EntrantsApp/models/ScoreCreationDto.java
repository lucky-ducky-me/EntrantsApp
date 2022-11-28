package vyatsu.fib.ovchinnikov.EntrantsApp.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Обёртка для списка счётов.
 */
public class ScoreCreationDto {

    /**
     * Список целочисленных счётов.
     */
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private ArrayList<Score> scores;

    /**
     * Создание обёртки для списка счётов.
     * @param scores список счётов.
     */
    public ScoreCreationDto(ArrayList<Score> scores) {
        setScores(scores);
    }
}
