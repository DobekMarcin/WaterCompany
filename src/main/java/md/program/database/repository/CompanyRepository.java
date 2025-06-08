package md.program.database.repository;

import md.program.database.model.Company;
import md.program.database.model.Partner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyRepository {

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

    public List<Company> getAllCompany() throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        List<Company> companyList=new ArrayList<>();
        Company temp = null;
        statement = connection.prepareStatement("SELECT id, name, nip, place, post_code, post, address, phone, email FROM md.company_list order by id");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new Company();
            temp.setId(rs.getInt("id"));
            temp.setName(rs.getString("name"));
            temp.setNip(rs.getString("nip"));
            temp.setPlace(rs.getString("place"));
            temp.setPost_code(rs.getString("post_code"));
            temp.setPost(rs.getString("post"));
            temp.setAddress(rs.getString("address"));
            temp.setPhone(rs.getString("phone"));
            temp.setEmail(rs.getString("email"));
            companyList.add(temp);
        }
        connection.close();
        return companyList;
    }

    public Integer getNextId() throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        Integer nextId=0;
        statement = connection.prepareStatement("Select coalesce(max(id),0)+1 as nextId from md.company_list");
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            nextId=rs.getInt("nextId");
        }
        connection.close();
        return nextId;
    }

    public void addCompany(Company company) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("INSERT INTO md.company_list(id, name, nip, place, post_code, post, address, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setInt(1, company.getId());
        statement.setString(2, company.getName());
        statement.setString(3,company.getNip());
        statement.setString(4, company.getPlace());
        statement.setString(5, company.getPost_code());
        statement.setString(6, company.getPost());
        statement.setString(7, company.getAddress());
        statement.setString(8,company.getPhone());
        statement.setString(9,company.getEmail());

        statement.executeUpdate();
        connection.close();
    }

    public void deleteCompanyById(Company company) throws SQLException {
        PreparedStatement statement;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Delete from md.company_list where id=?");
        statement.setInt(1,company.getId());
        statement.executeUpdate();
        connection.close();
    }

    public void updateCompany(Company company) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("UPDATE md.company_list SET name=?, nip=?, place=?, post_code=?, post=?, address=?, phone=?, email=? WHERE id=?;");
        statement.setString(1, company.getName());
        statement.setString(2,company.getNip());
        statement.setString(3,company.getPlace());
        statement.setString(4,company.getPost_code());
        statement.setString(5,company.getPost());
        statement.setString(6,company.getAddress());
        statement.setString(7,company.getPhone());
        statement.setString(8,company.getEmail());
        statement.setInt(9,company.getId());

        statement.executeUpdate();
        connection.close();
    }

    public Integer checkCompanyInInvoice(Company company) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        Integer check = 0;
        statement = connection.prepareStatement("Select count(*) as count from md.invoice_list where company_id=?;");
        statement.setInt(1, company.getId());
        ResultSet rs = statement.executeQuery();
        while (rs.next())
            check = rs.getInt("count");
        connection.close();
        return check;
    }
}
