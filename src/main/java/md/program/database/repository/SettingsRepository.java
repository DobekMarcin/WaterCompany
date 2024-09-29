package md.program.database.repository;

import java.sql.*;

// set=1 Rok domyślny
// set=2 Rok BO
// set=3 Zamknij BO

public class SettingsRepository {

    private final String url = "jdbc:postgresql://127.0.0.1/water_company";
    private final String user = "postgres";
    private final String password = "root";

    public void connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void deleteYearSettings() throws SQLException {
        PreparedStatement statement;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Delete from md.settings where id=1");
        statement.executeUpdate();
        connection.close();
    }

    public void insertNewDefaultYear(Integer year) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Insert into md.settings (id,settings) values (1,?)");
        statement.setInt(1, year);
        statement.executeUpdate();
        connection.close();
    }

    public Integer getDefaultYear() throws SQLException {
        PreparedStatement statement=null;
        Connection connection = getConnection();
        Integer check = -1;
        statement = connection.prepareStatement("Select coalesce(settings,0) as year from md.settings where id=1;");
        ResultSet rs = statement.executeQuery();
        while(rs.next())
            check=rs.getInt("year");
        connection.close();
        return  check;
    }

    public Integer getBOYear() throws SQLException {
        PreparedStatement statement=null;
        Connection connection = getConnection();
        Integer check = -1;
        statement = connection.prepareStatement("Select coalesce(settings,0) as year from md.settings where id=2;");
        ResultSet rs = statement.executeQuery();
        while(rs.next())
            check=rs.getInt("year");
        connection.close();
        return  check;
    }

    public void deleteBOSettings() throws SQLException {
        PreparedStatement statement;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Delete from md.settings where id=2");
        statement.executeUpdate();
        connection.close();
    }

    public void insertNewBOYear(Integer year) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Insert into md.settings (id,settings) values (2,?)");
        statement.setInt(1, year);
        statement.executeUpdate();
        connection.close();
    }

    public void closeBO() throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Insert into md.settings (id,settings) values (3,1)");
        statement.executeUpdate();
        connection.close();
    }

    public Integer getCloseBO() throws SQLException {
        PreparedStatement statement=null;
        Connection connection = getConnection();
        Integer check = -1;
        statement = connection.prepareStatement("Select coalesce(settings,0) as bo from md.settings where id=3;");
        ResultSet rs = statement.executeQuery();
        while(rs.next())
            check=rs.getInt("bo");
        connection.close();
        return  check;
    }

    public void deleteCloseBoSettings() throws SQLException {
        PreparedStatement statement;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Delete from md.settings where id=3");
        statement.executeUpdate();
        connection.close();
    }
}
