package vyatsu.fib.ovchinnikov.EntrantsApp.dataBaseProviders;

import lombok.AccessLevel;
import lombok.Getter;
import vyatsu.fib.ovchinnikov.EntrantsApp.models.Entrant;
import vyatsu.fib.ovchinnikov.EntrantsApp.models.EntrantExamMap;
import vyatsu.fib.ovchinnikov.EntrantsApp.models.Exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Провайдер для связи абитуриентов и экзаменов.
 */
public class EntrantExamMapProvider implements IDataBaseProvider<EntrantExamMap> {

    /**
     * Список связей между абитуриентоми и экзаменоми.
     */
    @Getter(AccessLevel.PUBLIC)
    private final ArrayList<EntrantExamMap> entrantExamMap = new ArrayList<>();

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
     * Получение всех связей.
     * @return список связей.
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

        if (new ExamProvider().get(object.getExamId()).isEmpty()) {
            throw new RuntimeException("Экзамена с id: " + object.getExamId() + " не существует.");
        }

        if (new ExamProvider().get(object.getEntrantId()).isEmpty()) {
            throw new RuntimeException("Абитуриента с id: " + object.getEntrantId() + " не существует.");
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

    /**
     * Получение экзаменов у указанного абитуриента.
     * @param entrant абитуриент.
     * @return список экзаменов.
     */
    public ArrayList<Exam> getExamsByEntrant(Entrant entrant) {
        if (entrant == null) {
            throw new NullPointerException("Абитуриент не определён.");
        }

        var foundExams = new ArrayList<Exam>();

        var foundExamsIds = entrantExamMap.stream()
                .filter(elem -> elem.getEntrantId().equals(entrant.getId()))
                .map(EntrantExamMap::getExamId);

        var foundExamsOptional = foundExamsIds
                .map(examId -> new ExamProvider().get(examId))
                .collect(Collectors.toCollection(ArrayList::new));

        foundExams = foundExamsOptional.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toCollection(ArrayList::new));

        return foundExams;
    }

    /**
     * Получение абитуриентов, сдававших указанный экзамен.
     * @param exam экзамен.
     * @return список абитуриентов.
     */
    public ArrayList<Entrant> getEntrantsByExam(Exam exam) {
        if (exam == null) {
            throw new NullPointerException("Экзамен не определён.");
        }

        var foundEntrants = new ArrayList<Entrant>();

        var foundEntrantsIds = entrantExamMap.stream()
                .filter(elem -> elem.getExamId().equals(exam.getId()))
                .map(EntrantExamMap::getEntrantId);

        var foundExamsOptional = foundEntrantsIds
                .map(entrantId -> new EntrantProvider().get(entrantId))
                .collect(Collectors.toCollection(ArrayList::new));

        foundEntrants = foundExamsOptional.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toCollection(ArrayList::new));

        return foundEntrants;
    }
}
