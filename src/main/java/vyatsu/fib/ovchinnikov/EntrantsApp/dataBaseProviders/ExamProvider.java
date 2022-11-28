package vyatsu.fib.ovchinnikov.EntrantsApp.dataBaseProviders;

import vyatsu.fib.ovchinnikov.EntrantsApp.models.Exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Провайдер для таблицы c экзаменами.
 */
public class ExamProvider implements IDataBaseProvider<Exam> {

    /**
     * Список экзаменов.
     */
    static private final ArrayList<Exam> exams = new ArrayList<>();

    /**
     * Получение экзамена.
     * @param id id.
     * @return экзамен.
     */
    @Override
    public Optional<Exam> get(UUID id) {
        return exams.stream()
                .filter(exam -> exam.getId().equals(id))
                .findAny();
    }

    /**
     * Получение всех экзаменов.
     * @return список экзаменов.
     */
    @Override
    public List<Exam> getAll() {
        return new ArrayList<>(exams);
    }

    /**
     * Сохранение экзамена в базу данных.
     * @param object сохраняемый экзамен.
     * @return успех сохранения.
     */
    @Override
    public boolean save(Exam object) {
        if (get(object.getId()).isPresent()) {
            throw new RuntimeException("Экзамен с id: " + object.getId() + " уже существует.");
        }

        exams.add(object);

        return exams.contains(object);
    }

    /**
     * Удаление экзамена из базы данных.
     * @param object удаляемый экзамен.
     * @return успех удаления.
     */
    @Override
    public boolean delete(Exam object) {
        if (get(object.getId()).isEmpty()) {
            throw new RuntimeException("Экзамен с id: " + object.getId() + " не существует.");
        }

        exams.remove(object);

        return !exams.contains(object);
    }

    /**
     * Получение экзамена по предмету.
     * @param subject предмет.
     * @return экзамен.
     */
    public Optional<Exam> getBySubject(String subject) {
        return exams.stream()
                .filter(exam -> exam.getSubject().equals(subject))
                .findAny();
    }
}
