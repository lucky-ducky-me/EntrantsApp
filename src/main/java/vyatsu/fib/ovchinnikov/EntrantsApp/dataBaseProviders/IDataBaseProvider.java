package vyatsu.fib.ovchinnikov.EntrantsApp.dataBaseProviders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Провайдер базы данных.
 */
public interface IDataBaseProvider<T> {

    /**
     * Получение объекта.
     * @param id id.
     * @return объект.
     */
    Optional<T> get(UUID id);

    /**
     * Получение всех объектов.
     * @return список объектов.
     */
    List<T> getAll();

    /**
     * Сохранение объекта в базу данных.
     * @param object удаляемый объект.
     * @return успех сохранения.
     */
    boolean save(T object);

    /**
     * Удаление объекта из базы данных.
     * @param object удаляемый объект.
     * @return успех удаления.
     */
    boolean delete(T object);
}
