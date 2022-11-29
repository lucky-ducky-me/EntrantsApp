package vyatsu.fib.ovchinnikov.EntrantsApp.dataBaseProviders;

import lombok.AccessLevel;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Соединение с базой данных H2.
 */
public class H2Connection {

    @Getter(AccessLevel.PUBLIC)
    private static final String JDBC_URL = "jdbc:h2:meme:UniversityDb";

    @Getter(AccessLevel.PUBLIC)
    private static final String USER = "test";

    private static final String PASSWORD = "test";

    @Getter(AccessLevel.PUBLIC)
    private static final String DRIVER_CLASS = "org.h2.Driver";

    private static H2Connection h2Connection;

    @Getter(AccessLevel.PUBLIC)
    private Connection connection;

    private static Object mutex = new Object();

    /**
     * Получение соединения для работы с БД.
     * @return соединение.
     */
    public static H2Connection getH2Connection() {
        var connectionInstance = h2Connection;

        if (connectionInstance == null) {
            synchronized (mutex) {
                connectionInstance = h2Connection;

                if (connectionInstance == null) {
                    try {
                        h2Connection = connectionInstance = new H2Connection();
                    } catch (ClassNotFoundException | SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }

        return connectionInstance;
    }

    /**
     * Создание соединения с БД H2.
     */
    private H2Connection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER_CLASS);

        connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

}
