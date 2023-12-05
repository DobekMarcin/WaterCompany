package md.program.database.repository;

import md.program.database.model.RateYear;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RateYearRepository {

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

    public List<RateYear> getAllRateYear() throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        List<RateYear> rateYears=new ArrayList<>();
        RateYear temp = null;
        statement = connection.prepareStatement("Select id,year,rate from md.rate_year");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new RateYear();
            temp.setId(rs.getInt("id"));
            temp.setYear(rs.getInt("year"));
            temp.setRate(rs.getDouble("rate"));
            rateYears.add(temp);
        }
        connection.close();
        return rateYears;

    }
}
