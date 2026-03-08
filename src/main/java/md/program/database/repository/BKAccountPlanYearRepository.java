package md.program.database.repository;


import md.program.database.model.BKAccountYear;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

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

    public void generateYearFromPattern(int targetYear) throws SQLException {
        Connection connection = getConnection();

        // Mapa: Klucz = ID ze wzorca, Wartość = Nowe ID w tabeli roku
        Map<Integer, Integer> oldToNewIdMap = new HashMap<>();
        // Mapa pomocnicza: Nowe ID -> Stare ID Rooota (do późniejszej aktualizacji)
        Map<Integer, Integer> newIdToOldRootMap = new HashMap<>();

        String insertSql = "INSERT INTO md.account_plan_year (year, account, description, syn, full_name,credit,debit) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String updateRootSql = "UPDATE md.account_plan_year SET root = ? WHERE id = ?";

        try {
            connection.setAutoCommit(false); // Start transakcji

            // 1. POBIERANIE WZORCÓW I WSTAWIANIE DO NOWEJ TABELI
            try (PreparedStatement insertStmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                 Statement selectStmt = connection.createStatement();
                 ResultSet rs = selectStmt.executeQuery("SELECT id, root, account, description, syn, full_name FROM md.account_plan WHERE id>0")) {

                while (rs.next()) {
                    int oldId = rs.getInt("id");
                    int oldRoot = rs.getInt("root");

                    insertStmt.setInt(1, targetYear);
                    insertStmt.setString(2, rs.getString("account"));
                    insertStmt.setString(3, rs.getString("description"));
                    insertStmt.setBoolean(4, rs.getBoolean("syn"));
                    insertStmt.setString(5, rs.getString("full_name"));
                    insertStmt.setDouble(6,0);
                    insertStmt.setDouble(7,0);
                    insertStmt.executeUpdate();

                    // Pobieramy nowe ID wygenerowane przez Postgresa
                    try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int newId = generatedKeys.getInt(1);
                            oldToNewIdMap.put(oldId, newId);
                            newIdToOldRootMap.put(newId, oldRoot);
                        }
                    }
                }
            }

            // 2. AKTUALIZACJA RELACJI ROOT W NOWEJ TABELI
            try (PreparedStatement updateStmt = connection.prepareStatement(updateRootSql)) {
                for (Map.Entry<Integer, Integer> entry : newIdToOldRootMap.entrySet()) {
                    int newId = entry.getKey();
                    int oldRootValue = entry.getValue();

                    // Jeśli root we wzorcu nie był zerem/nullem, szukamy nowego odpowiednika
                    if (oldRootValue != 0) {
                        Integer newRootId = oldToNewIdMap.get(oldRootValue);
                        if (newRootId != null) {
                            updateStmt.setInt(1, newRootId);
                            updateStmt.setInt(2, newId);
                            updateStmt.addBatch();
                        }
                    } else {
                        // Jeśli to konto główne, upewniamy się, że root to 0
                        updateStmt.setInt(1, 0);
                        updateStmt.setInt(2, newId);
                        updateStmt.addBatch();
                    }
                }
                updateStmt.executeBatch();
            }

            connection.commit();
            System.out.println("Plan kont na rok " + targetYear + " został wygenerowany pomyślnie.");

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }


    public void insertNewAccount(List<BKAccountYear> bkAccountList, int year) throws SQLException {
        String sql = "INSERT INTO md.account_plan_year(year, root, account, description, syn, full_name,credit,debit) VALUES (?,?,?,?,?,?,?,?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            connection.setAutoCommit(false);

            for (BKAccountYear account : bkAccountList) {
                statement.setInt(1, year);
                statement.setInt(2, account.getRoot());
                statement.setString(3, account.getAccount());
                statement.setString(4, account.getDescription());
                statement.setBoolean(5, account.getSyn());
                statement.setString(6, account.getFullName());
                statement.setDouble(7,account.getCredit());
                statement.setDouble(8,account.getDebit());

                statement.addBatch();
            }

            statement.executeBatch();
            connection.commit();
        }
    }


    public List<BKAccountYear> getAllAccount(int year) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        List<BKAccountYear> accountPlanList=new ArrayList<>();
        BKAccountYear temp = null;
        statement = connection.prepareStatement("SELECT id,year, root, account, description,syn,full_name,credit,debit FROM md.account_plan_year where year=?  order by account;");
        statement.setInt(1,year);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new BKAccountYear();
            temp.setId(rs.getInt("id"));
            temp.setYear(rs.getInt("year"));
            temp.setAccount(rs.getString("account"));
            temp.setRoot(rs.getInt("root"));
            temp.setDescription(rs.getString("description"));
            temp.setSyn(rs.getBoolean("syn"));
            temp.setFullName(rs.getString("full_name"));
            temp.setCredit(rs.getDouble("credit"));
            temp.setDebit(rs.getDouble("debit"));
            accountPlanList.add(temp);
        }
        connection.close();
        return accountPlanList;
    }

    public int deleteYearByYear(int year) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        int deletedRows = 0;

        try {
            statement = connection.prepareStatement("DELETE FROM md.account_plan_year WHERE year = ?;");
            statement.setInt(1, year);
            deletedRows = statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return deletedRows;
    }


}
