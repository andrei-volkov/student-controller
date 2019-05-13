package anrix.model.service;

import anrix.model.bean.Faculty;
import anrix.model.bean.Group;
import anrix.model.bean.Student;
import anrix.model.dao.ArrayListFacultyDAO;
import anrix.model.dao.FacultyDAO;
import javafx.scene.control.TreeItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static anrix.controller.MainViewController.mainGroupsTree;

public class DatabaseService {

    private static final String DB_Driver = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:./students";

    private FacultyDAO facultyDAO;

    private Connection connection;
    private Statement statement;

    public static boolean isAvailable = true;

    private static final String INSERT_COMMAND = "INSERT INTO Registration ";
    private static final String SELECT_COMMAND = "SELECT name, surname, groupID, " +
                                                "faculty, mark, gender, uuid " +
                                                "FROM Registration";
    private static final String REMOVE_COMMAND = "DELETE FROM Registration WHERE uuid = ";


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

    public void initialize() throws SQLException {
        isAvailable = false;

        facultyDAO = ArrayListFacultyDAO.getInstance();

        initializeFaculties();
        initializeGroups();
        initializeStudents();

        isAvailable = true;
    }

    private void initializeFaculties() throws SQLException {
        ArrayList<String> faculties = new ArrayList<>();

        ResultSet rs = statement.executeQuery(SELECT_COMMAND);

        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String facultyName = rs.getString("faculty");

            if (!faculties.contains(facultyName)) {
                faculties.add(facultyName);
            }
        }
        faculties.forEach(f -> facultyDAO.add(new Faculty(f)));
    }

    private void initializeGroups() throws SQLException {
        HashMap<String, String> map = new HashMap<>();

        ResultSet rs = statement.executeQuery(SELECT_COMMAND);

        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String groupName = rs.getString("groupID");
            String facultyName = rs.getString("faculty");

            if (!map.containsKey(groupName)) {
                map.put(groupName, facultyName);
            }
        }
        for (Map.Entry<String,String> entry : map.entrySet()) {
            Group group = new Group();
            group.setId(entry.getKey());
            facultyDAO.add(group, entry.getValue());
        }
    }

    private void initializeStudents() throws SQLException {
        ResultSet rs = statement.executeQuery(SELECT_COMMAND);

        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String name = rs.getString("name");
            String surname = rs.getString("surname");

            String groupName = rs.getString("groupID");
            String facultyName = rs.getString("faculty");

            String uuid = rs.getString("uuid");

            Double mark = Double.parseDouble(rs.getString("mark"));
            Student.GENDER gender = "MALE".equals(rs.getString("gender")) ?
                                    Student.GENDER.MALE : Student.GENDER.FEMALE;

            Student student = new Student(name, surname,
                                          groupName, facultyName,
                                          mark, gender, uuid);

            facultyDAO.add(student);
         }
    }

    public void add(Student student) {
        try {
            statement.executeUpdate(INSERT_COMMAND + student.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void remove(Student student) {
        isAvailable = false;

        try {
            statement.executeUpdate(REMOVE_COMMAND +
                    "'" + student.getId() + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        isAvailable = true;
    }

    public void crateTable() {
        try {
            Class.forName(DB_Driver);
            connection = DriverManager.getConnection(DB_URL);
            statement = connection.createStatement();

            String sql = "CREATE TABLE   REGISTRATION " +
                    "(name VARCHAR(255), " +
                    " surname VARCHAR(255), " +
                    " groupID VARCHAR(255), " +
                    " faculty VARCHAR(255), " +
                    " mark DOUBLE, " +
                    " gender VARCHAR(10), " +
                    " uuid VARCHAR(36), " +
                    " PRIMARY KEY ( uuid ))";
            statement.executeUpdate(sql);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void merge(String databaseUrl) throws SQLException {
        String newDBUrl = "jdbc:h2:" + databaseUrl;

        Connection newConnection = DriverManager.getConnection(newDBUrl);
        Statement newStatement = newConnection.createStatement();
        ResultSet rs = newStatement.executeQuery(SELECT_COMMAND);

        while (rs.next()) {
            String facultyName = rs.getString("faculty");
            if (!facultyDAO.contains(new Faculty(facultyName))) {
                mainGroupsTree.getRoot().getChildren().add(new TreeItem<>(facultyName));
                facultyDAO.add(new Faculty(facultyName));
            }
        }

        rs = newStatement.executeQuery(SELECT_COMMAND);

        while (rs.next()) {
            String groupName = rs.getString("groupID");
            String facultyName = rs.getString("faculty");

            Group group = new Group();
            group.setId(groupName);

            if (!facultyDAO.contains(group)) {
                facultyDAO.add(group, facultyName);

                for (TreeItem<String> item : mainGroupsTree.getRoot().getChildren()) {
                    if (facultyName.equals(item.getValue())) {
                        item.getChildren().add(new TreeItem<>(group.getId()));
                    }
                }
            }

        }

        rs = newStatement.executeQuery(SELECT_COMMAND);

        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String name = rs.getString("name");
            String surname = rs.getString("surname");

            String groupName = rs.getString("groupID");
            String facultyName = rs.getString("faculty");

            Double mark = Double.parseDouble(rs.getString("mark"));
            Student.GENDER gender = "MALE".equals(rs.getString("gender")) ?
                    Student.GENDER.MALE : Student.GENDER.FEMALE;


            Student student = new Student(name, surname,
                    groupName, facultyName,
                    mark, gender,
                    UUIDService.getUUID());

            facultyDAO.add(student);
        }

    }
}
