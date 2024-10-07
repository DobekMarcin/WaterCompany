package md.program.database.repository;

import md.program.database.model.BKAccount;
import md.program.database.model.CounterRead;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BKAccountPlanRepository {

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


    public List<BKAccount> getAccountPlanList(Integer root) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        List<BKAccount> accountPlanList=new ArrayList<>();
        BKAccount temp = null;
        statement = connection.prepareStatement("SELECT id, root, account, description FROM md.account_plan where id>0 and root=? order by account;");
        statement.setInt(1,root);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new BKAccount();
            temp.setId(rs.getInt("id"));
            temp.setAccount(rs.getString("account"));
            temp.setRoot(rs.getInt("root"));
            temp.setDescription(rs.getString("description"));
            accountPlanList.add(temp);
        }
        connection.close();
        return accountPlanList;
    }

    public BKAccount getAccountHeader() throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();

        BKAccount temp = null;
        statement = connection.prepareStatement("SELECT id, root, account, description FROM md.account_plan where id=0 order by account;");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new BKAccount();
            temp.setId(rs.getInt("id"));
            temp.setAccount(rs.getString("account"));
            temp.setRoot(rs.getInt("root"));
            temp.setDescription(rs.getString("description"));
        }
        connection.close();
        return temp;
    }

    public void save(BKAccount bkAccount) throws SQLException {

            PreparedStatement statement = null;
            Connection connection = getConnection();
            statement = connection.prepareStatement("Update md.account_plan set account=?,description=? where id=?");
            statement.setInt(3,bkAccount.getId());
            statement.setString(2, bkAccount.getDescription());
            statement.setString(1,bkAccount.getAccount());
            statement.executeUpdate();
            connection.close();
    }

    public void delete(BKAccount bkAccountEdit) throws SQLException {
        PreparedStatement statement;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Delete from md.account_plan where id=?");
        statement.setInt(1,bkAccountEdit.getId());
        statement.executeUpdate();
        connection.close();
    }

    public void insertNewAccount(BKAccount bkAccount) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("INSERT INTO md.account_plan( " +
                "id , root, account, description) " +
                "VALUES ( (Select coalesce(max(id)+1,1) from md.account_plan), ? , ?, ?);");

        statement.setInt(1, bkAccount.getRoot());
        statement.setString(2, bkAccount.getAccount());
        statement.setString(3, bkAccount.getDescription());

        statement.executeUpdate();
        connection.close();
    }

    public int checkChildren(BKAccount bkAccountEdit) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();

        int temp = 0;
        statement = connection.prepareStatement("SELECT coalesce(count(*),0) as accountCount FROM md.account_plan where root=? ;");
        statement.setInt(1,bkAccountEdit.getId());
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp= rs.getInt("accountCount");
        }
        connection.close();

        return temp;
    }

    public int getRoot(BKAccount bkAccount) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();

        int temp = 0;
        statement = connection.prepareStatement("SELECT root FROM md.account_plan where id=? ;");
        statement.setInt(1, bkAccount.getId());
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp= rs.getInt("root");
        }
        connection.close();
        return temp;
    }
}
