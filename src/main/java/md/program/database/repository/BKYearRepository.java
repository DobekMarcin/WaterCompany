package md.program.database.repository;

import md.program.database.model.BKYear;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BKYearRepository {

    private final String url = "jdbc:postgresql://127.0.0.1/water_company";
    private final String user = "postgres";
    private final String password = "root";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void connect() {
        try (Connection conn = getConnection()) {
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    public List<BKYear> getAllBKYear() throws SQLException {
        String sql = "SELECT id, year FROM md.bookkeeping_year ORDER BY id";
        List<BKYear> counterYears = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                BKYear temp = new BKYear();
                temp.setId(rs.getInt("id"));
                temp.setYear(rs.getInt("year"));
                counterYears.add(temp);
            }
        }
        return counterYears;
    }

    public void deleteBKYear(BKYear bkYear) throws SQLException {
        String sql = "DELETE FROM md.bookkeeping_year WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bkYear.getId());
            statement.executeUpdate();
        }
    }

    public void addYear(BKYear bkYear) throws SQLException {
        String sql = "INSERT INTO md.bookkeeping_year(id, year) " +
                "VALUES ((SELECT COALESCE(MAX(id), 0) + 1 FROM md.bookkeeping_year), ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bkYear.getYear());
            statement.executeUpdate();
        }
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

    public int chceckIsDefaultYear(Integer defaultYear) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM md.bookkeeping_year WHERE year = ?";
        int check = 0;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, defaultYear);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    check = rs.getInt("count");
                }
            }
        }
        return check;
    }

    public List<Integer> getYearList() throws SQLException {
        String sql = "SELECT DISTINCT year FROM md.bookkeeping_year ORDER BY year";
        List<Integer> yearList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                yearList.add(rs.getInt("year"));
            }
        }
        return yearList;
    }
}