package md.program.database.repository;

import md.program.database.model.BKPattern;
import md.program.database.model.Company;
import md.program.database.model.CounterRead;
import md.program.database.model.Partner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatternRepository {

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


    public List<BKPattern> getPatternList(int patternType) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();

        List<BKPattern> bkPatterns = new ArrayList<>();
        statement = connection.prepareStatement("SELECT pattern, pattern_des, debit,(Select full_name from md.account_plan where id=debit) as debittext, credit, " +
                " (Select full_name from md.account_plan where id=credit) as credittext, invoice, bank, cash FROM md.bookkeeping_patterns;");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            BKPattern temp = new BKPattern();
            temp.setId_pattern(rs.getInt("pattern"));
            temp.setDes_pattenr(rs.getString("pattern_des"));
            temp.setDebit(rs.getInt("debit"));
            temp.setDebitText(rs.getString("debittext"));
            temp.setCredit(rs.getInt("credit"));
            temp.setCreditText(rs.getString("credittext"));
            temp.setInvoice(rs.getBoolean("invoice"));
            temp.setBank(rs.getBoolean("bank"));
            temp.setCash(rs.getBoolean("cash"));
            bkPatterns.add(temp);
        }
        connection.close();
        return bkPatterns;
    }



    public void deletePattern(BKPattern bkPattern) throws SQLException {
        PreparedStatement statement;
        Connection connection = getConnection();
        statement = connection.prepareStatement("delete from md.bookkeeping_patterns where pattern=?");
        statement.setInt(1, bkPattern.getId_pattern());
        statement.executeUpdate();
        connection.close();
    }


        public void addPattern(BKPattern bkPattern) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("INSERT INTO md.bookkeeping_patterns(pattern, pattern_des, debit, credit, invoice, bank, cash) VALUES ((Select coalesce(max(pattern)+1,1) from md.bookkeeping_patterns), ?, ?, ?, ?, ?, ?);");
        statement.setString(1,bkPattern.getDes_pattenr());
            statement.setInt(2,bkPattern.getDebit());
        statement.setInt(3,bkPattern.getCredit());
        statement.setBoolean(4, bkPattern.getInvoice());
        statement.setBoolean(5,bkPattern.getBank());
        statement.setBoolean(6,bkPattern.getCash());
        statement.executeUpdate();
        connection.close();
    }

    public void updatePattern(BKPattern bkPattern) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("UPDATE md.bookkeeping_patterns SET  pattern_des=? WHERE pattern=?;");
        statement.setString(1, bkPattern.getDes_pattenr());
        statement.setInt(2,bkPattern.getId_pattern());
        statement.executeUpdate();
        connection.close();
    }

}
