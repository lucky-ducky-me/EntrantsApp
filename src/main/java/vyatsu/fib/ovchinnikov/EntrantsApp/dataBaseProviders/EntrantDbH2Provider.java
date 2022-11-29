package vyatsu.fib.ovchinnikov.EntrantsApp.dataBaseProviders;

import vyatsu.fib.ovchinnikov.EntrantsApp.dataBaseProviders.entities.EntrantEntity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Провайдер для абитуриента для БД H2.
 */
public class EntrantDbH2Provider implements IDataBaseProvider<EntrantEntity> {

    /**
     * Соединение с БД H2.
     */
    private H2Connection h2Connection;


    /**
     * Создание провайдера для работы с абитуриентами в БД H2.
     */
    public EntrantDbH2Provider() throws SQLException {
        h2Connection = H2Connection.getH2Connection();

        var connection = h2Connection.getConnection().createStatement();

        var queryString =
                "CREATE TABLE IF NOT EXISTS Entrant" +
                "(Id UUID PRIMARY KEY NOT NULL," +
                "Birthday DATE NOT NULL, " +
                "Name varchar(30) NOT NULL," +
                "Surname varchar(30) NOT NULL," +
                "Patronymic varchar(30));" +
                "INSERT INTO Entrant (Id, Birthday, Name, Surname, Patronymic)\n" +
                "VALUES (RANDOM_UUID(), '23.03.2022', 'Иванов1', 'Иван1', 'Иванович'" +
                "VALUES (RANDOM_UUID(), '24.03.2022', 'Иванов2', 'Иван2', 'Иванович'" +
                "VALUES (RANDOM_UUID(), '25.03.2022', 'Иванов3', 'Иван3', 'Иванович'" +
                "VALUES (RANDOM_UUID(), '26.03.2022', 'Иванов4', 'Иван4', 'Иванович');";

        connection.execute(queryString);
        connection.close();
    }

    /**
     * Получение абитуриента.
     * @param id id.
     * @return абитуриент.
     */
    @Override
    public Optional<EntrantEntity> get(UUID id) {
        return Optional.empty();
    }

    /**
     * Получение всех абитуриентов.
     * @return список абитуриентов.
     */
    @Override
    public List<EntrantEntity> getAll() {
        try {
            List<EntrantEntity> entrantEntities = new ArrayList<>();

            var connection = h2Connection.
                    getConnection().
                    createStatement();

            var resultSet = connection.
                    executeQuery("SELECT * FROM Entrant");

            while (resultSet.next()) {
                var id = (UUID) resultSet.getObject("Id");

                var name = resultSet.getString("Name");

                var surName = resultSet.getString("Surname");

                var patronymic = resultSet.getString("Patronymic");

                var birthday = resultSet.getDate("birthday");

                var entrantEntity = new EntrantEntity();

                entrantEntity.setId(id);
                entrantEntity.setName(name);
                entrantEntity.setSurname(surName);
                entrantEntity.setPatronymic(patronymic);
                entrantEntity.setBirthday(birthday);

                entrantEntities.add(entrantEntity);
            }

            connection.close();

            return entrantEntities;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Сохранение абитуриента в базу данных.
     * @param object сохраняемый абитуриент.
     * @return успех сохранения.
     */
    @Override
    public boolean save(EntrantEntity object) {
        try {
            var connection= h2Connection.
                    getConnection().
                    createStatement();

            var queryString = String.format("INSERT INTO Entrant (Id, Birthday, Name, Surname, Patronymic)\n" +
                    "VALUES (%s, '%s, %s, %s, %s)"
                    , object.getId()
                    , object.getBirthday()
                    , object.getName()
                    , object.getSurname()
                    , object.getPatronymic());

            var result = connection.execute(queryString);
            connection.close();

            return result;

        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Удаление абитуриента из базы данных.
     * @param object удаляемый абитуриент.
     * @return успех удаления.
     */
    @Override
    public boolean delete(EntrantEntity object) {
        try {
            var connection = h2Connection.
                    getConnection().
                    createStatement();

            var queryString = "DELETE FROM Entrant WHERE Id = "
                    + object.getId();

            var result = connection.execute(queryString);
            connection.close();

            return result;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
