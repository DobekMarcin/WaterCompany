package md.program.database.repository;

import md.program.database.model.BKAccount;
import md.program.modelFX.BKAccountFX;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BKAccountPlanYearRepository {

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

    public void insertNewAccount(List<BKAccount> bkAccountList, int year) throws SQLException {
        String sql = "INSERT INTO md.account_plan_year(year, root, account, description, syn, full_name) VALUES (?,?,?,?,?,?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            connection.setAutoCommit(false);

            for (BKAccount account : bkAccountList) {
                statement.setInt(1, year);
                statement.setInt(2, account.getRoot());
                statement.setString(3, account.getAccount());
                statement.setString(4, account.getDescription());
                statement.setBoolean(5, account.getSyn());
                statement.setString(6, account.getFullName());

                statement.addBatch();
            }

            statement.executeBatch();
            connection.commit();
        }
    }



    public List<BKAccount> getAllAccountLevel0(int year) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        List<BKAccount> accountPlanList=new ArrayList<>();
        BKAccount temp = null;
        statement = connection.prepareStatement("SELECT year, root, account, description,syn,full_name FROM md.account_plan_year where root=0 and year=?  order by account;");
           statement.setInt(1,year);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new BKAccount();
            temp.setId(rs.getInt("year"));
            temp.setAccount(rs.getString("account"));
            temp.setRoot(rs.getInt("root"));
            temp.setDescription(rs.getString("description"));
            temp.setSyn(rs.getBoolean("syn"));
            temp.setFullName(rs.getString("full_name"));
            accountPlanList.add(temp);
        }
        connection.close();
        return accountPlanList;
    }



    public List<BKAccount> getAllAccountLevel(BKAccountFX level0,int year) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        List<BKAccount> accountPlanList=new ArrayList<>();
        BKAccount temp = null;
        statement = connection.prepareStatement("SELECT year, root, account, description,syn,full_name FROM md.account_plan_year where root=? and year=?  order by account;");
        statement.setInt(1,level0.getId());
        statement.setInt(2,year);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new BKAccount();
            temp.setId(rs.getInt("year"));
            temp.setAccount(rs.getString("account"));
            temp.setRoot(rs.getInt("root"));
            temp.setDescription(rs.getString("description"));
            temp.setSyn(rs.getBoolean("syn"));
            temp.setFullName(rs.getString("full_name"));
            accountPlanList.add(temp);
        }
        connection.close();
        return accountPlanList;
    }

}
