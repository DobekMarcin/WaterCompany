package md.program.database.repository;

import md.program.database.model.Partner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartnerBORepository {

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
        statement = connection.prepareStatement("Select id,name,surname,address,postCode,post,nip,people_count,archives,company,meter,year,month from md.partner_list left join  order by id");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new Partner();
            temp.setId(rs.getInt("id"));
            temp.setName(rs.getString("name"));
            temp.setSurname(rs.getString("surname"));
            temp.setAddress(rs.getString("address"));
            temp.setPostCode(rs.getString("postCode"));
            temp.setPost(rs.getString("post"));
            temp.setNip(rs.getString("nip"));
            temp.setPeopleCount(rs.getInt("people_count"));
            temp.setArchives(rs.getBoolean("archives"));
            temp.setCompany(rs.getBoolean("company"));
            temp.setMeter(rs.getBoolean("meter"));
            temp.setYear(rs.getInt("year"));
            temp.setMonth(rs.getInt("month"));
            partnerList.add(temp);
        }
        connection.close();
        return partnerList;
    }

}
