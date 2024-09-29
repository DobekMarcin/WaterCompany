package md.program.database.repository;

import md.program.database.model.BKAccount;
import md.program.database.model.Partner;

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
        statement = connection.prepareStatement("SELECT id, root, account, description FROM md.account_plan where id>0 and root=?;");
        statement.setString(1,root+"");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new BKAccount();
            temp.setId(rs.getInt("id"));
            temp.setAccount(rs.getString("account"));
            temp.setRoot(rs.getString("root"));
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
        statement = connection.prepareStatement("SELECT id, root, account, description FROM md.account_plan where id=0;");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new BKAccount();
            temp.setId(rs.getInt("id"));
            temp.setAccount(rs.getString("account"));
            temp.setRoot(rs.getString("root"));
            temp.setDescription(rs.getString("description"));
        }
        connection.close();
        return temp;
    }
}
