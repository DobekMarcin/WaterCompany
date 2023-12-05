package md.program.database.repository;

import java.sql.*;

public class UserRepository {

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
    public static void main(String[] args) {
        UserRepository app = new UserRepository();
        app.connect();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public Integer getAllUserCount() throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        Integer userCount = 0;
        statement = connection.prepareStatement("Select count(*) as count from md.user");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) userCount=rs.getInt("count");
        connection.close();
        return userCount;

    }

    public void createAdmin(String login, String password) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("INSERT INTO md.user (id,login,password) VALUES (1,?,?);");
        statement.setString(1, login);
        statement.setString(2, password);
        statement.executeUpdate();
        connection.close();
    }

    public Integer checkUser(String login, String password) throws SQLException {
        PreparedStatement statement=null;
        Connection connection = getConnection();
        Integer check = 0;
        statement = connection.prepareStatement("Select count(*) as count from md.user where login=? and password=?;");
        statement.setString(1,login);
        statement.setString(2,password);
        ResultSet rs = statement.executeQuery();
        while(rs.next())
            check=rs.getInt("count");
        connection.close();
        return  check;
    }
}
