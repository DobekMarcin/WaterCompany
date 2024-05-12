package md.program.database.repository;

import md.program.database.model.Partner;
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
        statement = connection.prepareStatement("Select id,year,rate,payment_plan_is_generated from md.rate_year order by id");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new RateYear();
            temp.setId(rs.getInt("id"));
            temp.setYear(rs.getInt("year"));
            temp.setRate(rs.getDouble("rate"));
            temp.setPaymentPlanIsGenerated(rs.getBoolean("payment_plan_is_generated"));
            rateYears.add(temp);
        }
        connection.close();
        return rateYears;

    }
    public void addRateYear(RateYear rateYear) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Insert into md.rate_year (id,year,rate,payment_plan_is_generated) values ((Select coalesce(max(id),0)+1 from md.rate_year),?,?,?)");
        statement.setInt(1, rateYear.getYear());
        statement.setDouble(2, rateYear.getRate());
        statement.setBoolean(3,false);
        statement.executeUpdate();
        connection.close();
    }
    public void deleteRateYearById(RateYear rateYear) throws SQLException {
        PreparedStatement statement;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Delete from md.rate_year where id=?");
        statement.setInt(1,rateYear.getId());
        statement.executeUpdate();
        connection.close();
    }

    public void updateRateYearGeneratedStatus(RateYear rateYear) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Update md.rate_year set payment_plan_is_generated=? where id=?");
        statement.setBoolean(1, rateYear.getPaymentPlanIsGenerated());
        statement.setInt(2,rateYear.getId());
        statement.executeUpdate();
        connection.close();
    }
    public Boolean checkRateYearGeneratedStatus(RateYear rateYear) throws SQLException {
        PreparedStatement statement=null;
        Connection connection = getConnection();
        Boolean check = false;
        statement = connection.prepareStatement("Select payment_plan_is_generated as result from md.rate_year where id=?;");
        statement.setInt(1,rateYear.getId());
        ResultSet rs = statement.executeQuery();
        while(rs.next())
            check=rs.getBoolean("result");
        connection.close();
        return  check;
    }
    public Integer getNextYear() throws SQLException {
        PreparedStatement statement=null;
        Connection connection = getConnection();
        Integer check = -1;
        statement = connection.prepareStatement("Select coalesce(max(year),-1)+1 as year from md.rate_year;");
        ResultSet rs = statement.executeQuery();
        while(rs.next())
            check=rs.getInt("year");
        connection.close();
        return  check;
    }
}
