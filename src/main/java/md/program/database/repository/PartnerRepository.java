package md.program.database.repository;

import md.program.database.model.Partner;
import md.program.database.model.RateYear;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartnerRepository {

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

    public List<Partner> getAllPartner() throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        List<Partner> partnerList=new ArrayList<>();
        Partner temp = null;
        statement = connection.prepareStatement("Select id,name,surname,address,people_count,archives from md.partner_list");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new Partner();
            temp.setId(rs.getInt("id"));
            temp.setName(rs.getString("name"));
            temp.setSurname(rs.getString("surname"));
            temp.setAddress(rs.getString("archives"));
            temp.setPeopleCount(rs.getInt("people_count"));
            temp.setArchives(rs.getBoolean("archives"));
            partnerList.add(temp);
        }
        connection.close();
        return partnerList;

    }
}
