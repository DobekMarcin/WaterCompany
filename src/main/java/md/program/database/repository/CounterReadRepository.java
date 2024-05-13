package md.program.database.repository;

import md.program.database.model.CounterRead;
import md.program.database.model.Partner;
import md.program.database.model.PaymentPlan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CounterReadRepository {

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

    public void addCounterRead(CounterRead counterRead) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Insert into md.counter_read (id,year_id,partner_id,m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12) values ((Select coalesce(max(id),0)+1 from md.payment_plan ),?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        statement.setInt(1, counterRead.getYearId());
        statement.setInt(2, counterRead.getPartnerId());
        statement.setDouble(3, counterRead.getM1());
        statement.setDouble(4, counterRead.getM2());
        statement.setDouble(5, counterRead.getM3());
        statement.setDouble(6, counterRead.getM4());
        statement.setDouble(7, counterRead.getM5());
        statement.setDouble(8, counterRead.getM6());
        statement.setDouble(9, counterRead.getM7());
        statement.setDouble(10, counterRead.getM8());
        statement.setDouble(11, counterRead.getM9());
        statement.setDouble(12, counterRead.getM10());
        statement.setDouble(13, counterRead.getM11());
        statement.setDouble(14, counterRead.getM12());

        statement.executeUpdate();
        connection.close();
    }



    public List<CounterRead> getAllCounterReadByYear(Integer yearId) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        List<CounterRead> counterReadList = new ArrayList<>();
        CounterRead temp = null;
        statement = connection.prepareStatement("SELECT id,year_id,partner_id, m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12 FROM md.counter_read where year_id=? order by partner_id");
        statement.setInt(1, yearId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new CounterRead();
            temp.setId(rs.getInt("id"));
            temp.setYearId(rs.getInt("year_id"));
            temp.setPartnerId(rs.getInt("partner_id"));
            temp.setM1(rs.getDouble("m1"));
            temp.setM2(rs.getDouble("m2"));
            temp.setM3(rs.getDouble("m3"));
            temp.setM4(rs.getDouble("m4"));
            temp.setM5(rs.getDouble("m5"));
            temp.setM6(rs.getDouble("m6"));
            temp.setM7(rs.getDouble("m7"));
            temp.setM8(rs.getDouble("m8"));
            temp.setM9(rs.getDouble("m9"));
            temp.setM10(rs.getDouble("m10"));
            temp.setM11(rs.getDouble("m11"));
            temp.setM12(rs.getDouble("m12"));
            counterReadList.add(temp);
        }
        connection.close();
        return counterReadList;
    }

    public void deleteCounterReadByYear(Integer year) throws SQLException {
        PreparedStatement statement;
        Connection connection = getConnection();
        statement = connection.prepareStatement("delete from md.counter_read where year_id=?");
        statement.setInt(1, year);
        statement.executeUpdate();
        connection.close();
    }

    public void updateCounterRead(CounterRead counterRead) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Update md.counter_read set m1=?,m2=?,m3=?,m4=?,m5=?,m6=?,m7=?,m8=?,m9=?,m10=?,m11=?,m12=? where id=?");
        statement.setDouble(1, counterRead.getM1());
        statement.setDouble(2, counterRead.getM2());
        statement.setDouble(3, counterRead.getM3());
        statement.setDouble(4, counterRead.getM4());
        statement.setDouble(5, counterRead.getM5());
        statement.setDouble(6, counterRead.getM6());
        statement.setDouble(7, counterRead.getM7());
        statement.setDouble(8, counterRead.getM8());
        statement.setDouble(9, counterRead.getM9());
        statement.setDouble(10, counterRead.getM10());
        statement.setDouble(11, counterRead.getM11());
        statement.setDouble(12, counterRead.getM12());
        statement.setInt(13, counterRead.getId());
        statement.executeUpdate();
        connection.close();
    }

    public List<Integer> getYearList() throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        Integer year = 0;
        List<Integer> yearList = new ArrayList<>();
        statement = connection.prepareStatement("SELECT distinct year_id as year FROM md.counter_read order by year_id;");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            year = rs.getInt("year");
            yearList.add(year);
        }
        connection.close();
        return yearList;
    }

}
