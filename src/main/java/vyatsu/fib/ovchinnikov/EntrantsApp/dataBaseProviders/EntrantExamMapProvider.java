package vyatsu.fib.ovchinnikov.EntrantsApp.dataBaseProviders;

import lombok.AccessLevel;
import lombok.Getter;
import vyatsu.fib.ovchinnikov.EntrantsApp.models.EntrantExamMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Провайдер для связи абитуриентов и экзаменов.
 */
public class EntrantExamMapProvider implements IDataBaseProvider<EntrantExamMap>{

    /**
     * Список связей между абитуриентоми и экзаменоми.
     */
    @Getter(AccessLevel.PUBLIC)
    private ArrayList<EntrantExamMap> entrantExamMap = new ArrayList<>();

    /**
     * Получение связи.
     * @param id id.
     * @return связь.
     */
    @Override
    public Optional<EntrantExamMap> get(UUID id) {
        return entrantExamMap.stream()
                .filter(elem -> elem.getEntrantExamId().equals(id))
                .findAny();
    }

    /**
     * Получение всех объектов.
     * @return список объектов.
     */
    @Override
    public List<EntrantExamMap> getAll() {
        return new ArrayList<>(entrantExamMap);
    }

    /**
     * Сохранение связи в базу данных.
     * @param object сохраняемая связь.
     * @return успех сохранения.
     */
    @Override
    public boolean save(EntrantExamMap object) {
        if (get(object.getEntrantExamId()).isPresent()) {
            throw new RuntimeException("Связь с id: " + object.getEntrantExamId() + " уже существует.");
        }

        if (entrantExamMap.stream()
                .anyMatch(element ->
                        element.getExamId().equals(object.getExamId()) &&
                        element.getEntrantId().equals(object.getEntrantId()))
        ) {
            throw new RuntimeException(String.format(
                    "Связь с указанным экзаменом (id: %s) и абитуриентом (id: %s) уже существует."
                    , object.getExamId()
                    , object.getEntrantId()));
        }

        entrantExamMap.add(object);

        return entrantExamMap.contains(object);
    }

    /**
     * Удаление связи из базы данных.
     * @param object удаляемая связь.
     * @return успех удаления.
     */
    @Override
    public boolean delete(EntrantExamMap object) {
        if (get(object.getEntrantExamId()).isEmpty()) {
            throw new RuntimeException("Связь с id: " + object.getEntrantExamId() + " не существует.");
        }

        if (entrantExamMap.stream()
                .noneMatch(element ->
                        element.getExamId().equals(object.getExamId()) &&
                                element.getEntrantId().equals(object.getEntrantId()))
        ) {
            throw new RuntimeException(String.format(
                    "Связь с указанным экзаменом (id: %s) и абитуриентом (id: %s) не существует."
                    , object.getExamId()
                    , object.getEntrantId()));
        }

        entrantExamMap.remove(object);

        return entrantExamMap.contains(object);
    }
}
