package anrix.model.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseService {

    private static final String DB_Driver = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:./students";

    private Connection connection;
    private Statement statement;

    private static final String INSERT_COMAND = "INSERT INTO Registration ";



    private static volatile DatabaseService instance;

    private DatabaseService() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }


    public void initialize(){


    }

    public void crateTable() {
        try {
            Class.forName(DB_Driver);
            connection = DriverManager.getConnection(DB_URL);
            statement = connection.createStatement();

            String sql =  "CREATE TABLE   REGISTRATION " +
                    "(name VARCHAR(255), " +
                    " surname VARCHAR(255), " +
                    " groupID VARCHAR(255), " +
                    " faculty VARCHAR(255), " +
                    " mark DOUBLE, " +
                    " gender VARCHAR(10), " +
                    " hash VARCHAR(10), " +
                    " PRIMARY KEY ( hash ))";
            statement.executeUpdate(sql);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
