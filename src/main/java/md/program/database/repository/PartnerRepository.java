package md.program.database.repository;

import md.program.database.model.Partner;
import md.program.database.model.PaymentPlan;
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
        statement = connection.prepareStatement("Select id,name,surname,address,people_count,archives from md.partner_list order by id");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new Partner();
            temp.setId(rs.getInt("id"));
            temp.setName(rs.getString("name"));
            temp.setSurname(rs.getString("surname"));
            temp.setAddress(rs.getString("address"));
            temp.setPeopleCount(rs.getInt("people_count"));
            temp.setArchives(rs.getBoolean("archives"));
            partnerList.add(temp);
        }
        connection.close();
        return partnerList;
    }

    public Integer getNextId() throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        Integer nextId=0;
        statement = connection.prepareStatement("Select coalesce(max(id),0)+1 as nextId from md.partner_list");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            nextId=rs.getInt("nextId");
        }
        connection.close();
        return nextId;
    }

    public void addPartner(Partner partner) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Insert into md.partner_list (id,name,surname,address,people_count,archives) values (?,?,?,?,?,?)");
        statement.setInt(1, partner.getId());
        statement.setString(2, partner.getName());
        statement.setString(3,partner.getSurname());
        statement.setString(4,partner.getAddress());
        statement.setInt(5,partner.getPeopleCount());
        statement.setBoolean(6,partner.getArchives());
        statement.executeUpdate();
        connection.close();
    }

    public void deleteRateYearById(Partner partner) throws SQLException {
        PreparedStatement statement;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Delete from md.partner_list where id=?");
        statement.setInt(1,partner.getId());
        statement.executeUpdate();
        connection.close();
    }

    public void updatePartner(Partner partner) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Update md.partner_list set name=?,surname=?,address=?,people_count=?,archives=? where id=?");
        statement.setString(1, partner.getName());
        statement.setString(2,partner.getSurname());
        statement.setString(3,partner.getAddress());
        statement.setInt(4,partner.getPeopleCount());
        statement.setBoolean(5,partner.getArchives());
        statement.setInt(6, partner.getId());
        statement.executeUpdate();
        connection.close();
    }
    public Partner getPartnerById(Integer id) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        Partner partner=null;
        Partner temp = null;
        statement = connection.prepareStatement("Select id,name,surname,address,people_count,archives from md.partner_list where id=?");
        statement.setInt(1,id);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new Partner();
            temp.setId(rs.getInt("id"));
            temp.setName(rs.getString("name"));
            temp.setSurname(rs.getString("surname"));
            temp.setAddress(rs.getString("address"));
            temp.setPeopleCount(rs.getInt("people_count"));
            temp.setArchives(rs.getBoolean("archives"));
            partner=temp;
        }
        connection.close();
        return partner;
    }
}
