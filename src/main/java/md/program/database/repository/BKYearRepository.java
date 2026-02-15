package md.program.database.repository;

import md.program.database.model.BKYear;
import md.program.database.model.CounterYear;
import md.program.database.model.RateYear;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BKYearRepository {

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

    public List<BKYear> getAllBKYear() throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        List<BKYear> counterYears=new ArrayList<>();
        BKYear temp = null;
        statement = connection.prepareStatement("Select id,year from md.bookkeeping_year order by id");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new BKYear();
            temp.setId(rs.getInt("id"));
            temp.setYear(rs.getInt("year"));
            counterYears.add(temp);
        }
        connection.close();
        return counterYears;

    }

    public void deleteBKYear(BKYear bkYear) throws SQLException {
        PreparedStatement statement;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Delete from md.bookkeeping_year where id=?");
        statement.setInt(1,bkYear.getId());
        statement.executeUpdate();
        connection.close();
    }

    public void addYear(BKYear bkYear) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("INSERT INTO md.bookkeeping_year(id, year) VALUES ((Select coalesce(max(id),0)+1 from md.bookkeeping_year), ?);");
        statement.setInt(1, bkYear.getYear());
        statement.executeUpdate();
        connection.close();
    }

    public boolean existsByYear(int year) throws SQLException {
        String sql = "SELECT 1 FROM md.bookkeeping_year WHERE year = ? LIMIT 1";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, year);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

}
