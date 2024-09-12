package md.program.database.repository;

import md.program.database.model.CounterYear;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CounterCompanyYearRepository {

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

    public List<CounterYear> getAllCounterYear() throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        List<CounterYear> counterYears=new ArrayList<>();
        CounterYear temp = null;
        statement = connection.prepareStatement("Select id,year,rate from md.counter_company_rate_year order by id");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new CounterYear();
            temp.setId(rs.getInt("id"));
            temp.setYear(rs.getInt("year"));
            temp.setCounterRate(rs.getDouble("rate"));
            counterYears.add(temp);
        }
        connection.close();
        return counterYears;

    }
    public void addRateYear(CounterYear counterYear) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Insert into md.counter_company_rate_year (id,year,rate) values ((Select coalesce(max(id),0)+1 from md.counter_rate_year),?,?)");
        statement.setInt(1, counterYear.getYear());
        statement.setDouble(2, counterYear.getCounterRate());
        statement.executeUpdate();
        connection.close();
    }
    public void deleteRateYearById(CounterYear counterYear) throws SQLException {
        PreparedStatement statement;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Delete from md.counter_company_rate_year where id=?");
        statement.setInt(1,counterYear.getId());
        statement.executeUpdate();
        connection.close();
    }

    public Integer getNextYear() throws SQLException {
        PreparedStatement statement=null;
        Connection connection = getConnection();
        Integer check = -1;
        statement = connection.prepareStatement("Select coalesce(max(year),-1)+1 as year from md.counter_company_rate_year;");
        ResultSet rs = statement.executeQuery();
        while(rs.next())
            check=rs.getInt("year");
        connection.close();
        return  check;
    }

    public CounterYear getCounterYear(Integer year) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        CounterYear temp = null;
        statement = connection.prepareStatement("Select id,year,rate from md.counter_company_rate_year where year=? order by id limit 1;");
        statement.setInt(1,year);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new CounterYear();
            temp.setId(rs.getInt("id"));
            temp.setYear(rs.getInt("year"));
            temp.setCounterRate(rs.getDouble("rate"));
        }
        connection.close();
        return temp;

    }
}
