package vyatsu.fib.ovchinnikov.EntrantsApp.dataBaseProviders;

import vyatsu.fib.ovchinnikov.EntrantsApp.models.Entrant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Провайдер для таблицы с абитуриентами.
 */
public class EntrantProvider implements IDataBaseProvider<Entrant> {

    /**
     * Список абитуриентов.
     */
    private final ArrayList<Entrant> entrants = new ArrayList<>();

    /**
     * Получение абитуриента.
     * @param id id.
     * @return абитуриент.
     */
    @Override
    public Optional<Entrant> get(UUID id) {
        return entrants.stream()
                .filter(entrant -> entrant.getId().equals(id))
                .findAny();
    }

    /**
     * Получение всех абитуриентов.
     * @return список абитуриентов.
     */
    @Override
    public List<Entrant> getAll() {
        return new ArrayList<>(entrants);
    }

    /**
     * Сохранение абитуриента в базу данных.
     * @param object удаляемый абитуриент.
     * @return успех сохранения.
     */
    @Override
    public boolean save(Entrant object) {
        if (get(object.getId()).isPresent()) {
             throw new RuntimeException("Абитуриент с id: " + object.getId() + " уже существует.");
        }

        entrants.add(object);

        return entrants.contains(object);
    }

    /**
     * Удаление абитуриента из базы данных.
     * @param object удаляемый абитуриент.
     * @return успех удаления.
     */
    @Override
    public boolean delete(Entrant object) {
        if (get(object.getId()).isEmpty()) {
            throw new RuntimeException("Абитуриент с id: " + object.getId() + " не существует.");
        }

        entrants.remove(object);

        return !entrants.contains(object);
    }

    /**
     * Получение количества абитурентов.
     * @return количество абитуриентов.
     */
    public Integer getEntrantsAmount() {
        return entrants.size();
    }
}
