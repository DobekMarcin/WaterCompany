package md.program.database.repository;

import md.program.database.model.LogPartner;
import md.program.database.model.Partner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogPartnerRepository {

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

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }


    public void addPartnerLog(LogPartner partner) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Insert into md.partner_list_logs (id_logu,month,year,id_partnera,name,surname,address,postCode,post,nip,people_count,archives,company,meter) values ((Select coalesce(max(id_logu) +1,1) from md.partner_list_logs where id_partnera=?),?,?,?,?,?,?,?,?,?,?,?,?,?)");

        statement.setInt(1,partner.getId_partner());
        statement.setInt(2,partner.getMonth());
        statement.setInt(3,partner.getYear());
        statement.setInt(4, partner.getId_partner());
        statement.setString(5, partner.getName());
        statement.setString(6,partner.getSurname());
        statement.setString(7,partner.getAddress());
        statement.setString(8,partner.getPostCode());
        statement.setString(9,partner.getPost());
        statement.setString(10,partner.getNip());
        statement.setInt(11,partner.getPeopleCount());
        statement.setBoolean(12,partner.getArchives());
        statement.setBoolean(13,partner.getCompany());
        statement.setBoolean(14, partner.getMeter());
        statement.executeUpdate();
        connection.close();
    }

    public List<LogPartner> getAllLogForPartner(Integer partnerId) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        List<LogPartner> logPartnerList=new ArrayList<>();
        LogPartner temp = null;
        statement = connection.prepareStatement("SELECT id_logu, month, year, id_partnera, name, surname, address, postcode, post, nip, people_count, archives, company, meter" +
                " FROM md.partner_list_logs where id_partnera=? order by year,month,id_logu;");
        statement.setInt(1,partnerId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new LogPartner();
            temp.setId_logu(rs.getInt(1));
            temp.setMonth(rs.getInt(2));
            temp.setYear(rs.getInt(3));
            temp.setId_partner(rs.getInt(4));
            temp.setName(rs.getString(5));
            temp.setSurname(rs.getString(6));
            temp.setAddress(rs.getString(7));
            temp.setPostCode(rs.getString(8));
            temp.setPost(rs.getString(9));
            temp.setNip(rs.getString(10));
            temp.setPeopleCount(rs.getInt(11));
            temp.setArchives(rs.getBoolean(12));
            temp.setCompany(rs.getBoolean(13));
            temp.setMeter(rs.getBoolean(14));
            logPartnerList.add(temp);
        }
        connection.close();
        return logPartnerList;
    }

    public Boolean deleteLogPartner(LogPartner logPartner) throws SQLException {
            PreparedStatement statement;
            Connection connection = getConnection();

            statement = connection.prepareStatement("Delete from md.partner_list_logs where id_logu=? and id_partnera=?");
            statement.setInt(1,logPartner.getId_logu());
            statement.setInt(2,logPartner.getId_partner());
            statement.executeUpdate();
            connection.close();
            return true;
    }

    public Boolean deleteLogByPartner(Partner partner) throws SQLException {
        PreparedStatement statement;
        Connection connection = getConnection();

        statement = connection.prepareStatement("Delete from md.partner_list_logs where  id_partnera=?");
        statement.setInt(1,partner.getId());
        statement.executeUpdate();
        connection.close();
        return true;
    }

    public void saveDate(LogPartner logPartner) throws SQLException {
            PreparedStatement statement = null;
            Connection connection = getConnection();
            statement = connection.prepareStatement("Update md.partner_list_logs set month=?,year=? where id_logu=? and id_partnera=?");
            statement.setInt(1, logPartner.getMonth());
            statement.setInt(2,logPartner.getYear());
            statement.setInt(3,logPartner.getId_logu());
            statement.setInt(4,logPartner.getId_partner());
            statement.executeUpdate();
            connection.close();
    }

    public List<LogPartner> getAllLogForPartnerMaxPeriodTime(Integer partnerId) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        List<LogPartner> logPartnerList=new ArrayList<>();
        LogPartner temp = null;
        statement = connection.prepareStatement("Select * from md.partner_list_logs where id_logu in " +
                "(SELECT max(id_logu) FROM md.partner_list_logs where id_Partnera=? group by month,year ) " +
                "and id_partnera=?  order by year,month");
        statement.setInt(1,partnerId);
        statement.setInt(2,partnerId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new LogPartner();
            temp.setId_logu(rs.getInt(1));
            temp.setMonth(rs.getInt(2));
            temp.setYear(rs.getInt(3));
            temp.setId_partner(rs.getInt(4));
            temp.setName(rs.getString(5));
            temp.setSurname(rs.getString(6));
            temp.setAddress(rs.getString(7));
            temp.setPostCode(rs.getString(8));
            temp.setPost(rs.getString(9));
            temp.setNip(rs.getString(10));
            temp.setPeopleCount(rs.getInt(11));
            temp.setArchives(rs.getBoolean(12));
            temp.setCompany(rs.getBoolean(13));
            temp.setMeter(rs.getBoolean(14));
            logPartnerList.add(temp);
        }
        connection.close();
        return logPartnerList;
    }


    public LogPartner getLastMeterPartner(Integer partnerId) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        LogPartner temp = null;
        statement = connection.prepareStatement("Select * from md.partner_list_logs where id_partnera=? and (id_logu,year,month) = " +
                "(Select max(id_logu),max(year),max(month) from md.partner_list_logs where id_partnera=? and meter=false )");
        statement.setInt(1,partnerId);
        statement.setInt(2,partnerId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new LogPartner();
            temp.setId_logu(rs.getInt(1));
            temp.setMonth(rs.getInt(2));
            temp.setYear(rs.getInt(3));
            temp.setId_partner(rs.getInt(4));
            temp.setName(rs.getString(5));
            temp.setSurname(rs.getString(6));
            temp.setAddress(rs.getString(7));
            temp.setPostCode(rs.getString(8));
            temp.setPost(rs.getString(9));
            temp.setNip(rs.getString(10));
            temp.setPeopleCount(rs.getInt(11));
            temp.setArchives(rs.getBoolean(12));
            temp.setCompany(rs.getBoolean(13));
            temp.setMeter(rs.getBoolean(14));
        }
        connection.close();
        return temp;
    }
}
