package md.program.database.repository;

import md.program.database.model.Partner;
import md.program.database.model.PartnerBO;

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

    public List<PartnerBO> getAllPartner() throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        List<PartnerBO> partnerBOList=new ArrayList<>();
        PartnerBO temp=null;
        statement = connection.prepareStatement("SELECT   id,name,surname,address,postCode,post,nip,people_count,archives,company,meter,B.year as year,month,A.bo from md.company_payment_bo A left join md.partner_list B on A.partner_id=id order by id");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new PartnerBO();
            temp.getPartner().setId(rs.getInt("id"));
            temp.getPartner().setName(rs.getString("name"));
            temp.getPartner().setSurname(rs.getString("surname"));
            temp.getPartner().setAddress(rs.getString("address"));
            temp.getPartner().setPostCode(rs.getString("postCode"));
            temp.getPartner().setPost(rs.getString("post"));
            temp.getPartner().setNip(rs.getString("nip"));
            temp.getPartner().setPeopleCount(rs.getInt("people_count"));
            temp.getPartner().setArchives(rs.getBoolean("archives"));
            temp.getPartner().setCompany(rs.getBoolean("company"));
            temp.getPartner().setMeter(rs.getBoolean("meter"));
            temp.getPartner().setYear(rs.getInt("year"));
            temp.getPartner().setMonth(rs.getInt("month"));
            temp.setPartnerId(rs.getInt("id"));
            temp.setBo(rs.getDouble("bo"));
            partnerBOList.add(temp);
        }
        connection.close();
        return partnerBOList;
    }
    public Boolean insertBOFromSelect() throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Insert into md.company_payment_bo(partner_id,year,bo)  Select id,(Select settings from md.settings where id=2),0 from md.partner_list;");
        Boolean rs = statement.execute();

        connection.close();
        return rs;
    }

    public Integer checkPartnerInBO(Partner partner) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        Integer check = 0;
        statement = connection.prepareStatement("Select count(*) as count from md.company_payment_bo where partner_id=?;");
        statement.setInt(1, partner.getId());
        ResultSet rs = statement.executeQuery();
        while (rs.next())
            check = rs.getInt("count");
        connection.close();
        return check;
    }

    public void updatePartnerBO(PartnerBO partnerBO) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Update md.company_payment_bo set bo=? where partner_id=?");
        statement.setDouble(1, partnerBO.getBo());
        statement.setInt(2,partnerBO.getPartnerId());
        statement.executeUpdate();
        connection.close();
    }
}
