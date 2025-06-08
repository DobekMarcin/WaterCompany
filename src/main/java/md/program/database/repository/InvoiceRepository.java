package md.program.database.repository;

import md.program.database.model.Invoice;
import md.program.database.model.Partner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceRepository {

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

    public List<Invoice> getAllInvoice(int yearOfInvoice) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        List<Invoice> invoiceList=new ArrayList<>();
        Invoice temp = null;
        statement = connection.prepareStatement("SELECT A.id as id, A.year as year,A.invoice_number as invoice_number, A.invoice_date as invoice_date, A.company_id as company_id,B.name as name, A.invoice_des as invoice_des, A.invoice_amount as invoice_amount, A.bookkeeping as bookkeeping  FROM md.invoice_list A left join md.company_list B on A.company_id=B.id where year=? order by A.id,A.year;");
        statement.setInt(1,yearOfInvoice);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            temp = new Invoice();
            temp.setId(rs.getInt("id"));
            temp.setYear(rs.getInt("year"));
            temp.setInvoiceNumber(rs.getString("invoice_number"));
            temp.setInvoiceDate(rs.getDate("invoice_date"));
            temp.setCompanyId(rs.getInt("company_id"));
            temp.setCompanyName(rs.getString("name"));
            temp.setInvoiceDes(rs.getString("invoice_des"));
            temp.setAmount(rs.getDouble("invoice_amount"));
            temp.setPk(rs.getBoolean("bookkeeping"));
            invoiceList.add(temp);
        }
        connection.close();
        return invoiceList;
    }

    public void deleteInvoiceById(Invoice invoice) throws SQLException {
        PreparedStatement statement;
        Connection connection = getConnection();
        statement = connection.prepareStatement("Delete from md.invoice_list where id=? and year=?");
        statement.setInt(1,invoice.getId());
        statement.setInt(2,invoice.getYear());
        statement.executeUpdate();
        connection.close();
    }

    public Integer getNextId(int year) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        Integer nextId=0;
        statement = connection.prepareStatement("Select coalesce(max(id),0)+1 as nextId from md.invoice_list where year=?");
        statement.setInt(1,year);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            nextId=rs.getInt("nextId");
        }
        connection.close();
        return nextId;
    }

    public void addInvoice(Invoice invoice) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("INSERT INTO md.invoice_list(id, year, invoice_number, invoice_date, company_id, invoice_des, invoice_amount, bookkeeping) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
        statement.setInt(1, invoice.getId());
        statement.setInt(2, invoice.getYear());
        statement.setString(3,invoice.getInvoiceNumber());
        statement.setDate(4,invoice.getInvoiceDate());
        statement.setInt(5,invoice.getCompanyId());
        statement.setString(6,invoice.getInvoiceDes());
        statement.setDouble(7,invoice.getAmount());
        statement.setBoolean(8,invoice.getPk());
        statement.executeUpdate();
        connection.close();
    }

    public void updateInvoice(Invoice invoice) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = getConnection();
        statement = connection.prepareStatement("UPDATE md.invoice_list SET  invoice_number=?, invoice_date=?, company_id=?, invoice_des=?, invoice_amount=?, bookkeeping=? WHERE id=? and year=?;");
        statement.setInt(1, invoice.getYear());
        statement.setString(1,invoice.getInvoiceNumber());
        statement.setDate(2,invoice.getInvoiceDate());
        statement.setInt(3,invoice.getCompanyId());
        statement.setString(4,invoice.getInvoiceDes());
        statement.setDouble(5,invoice.getAmount());
        statement.setBoolean(6,invoice.getPk());
        statement.setInt(7,invoice.getId());
        statement.setInt(8,invoice.getYear());
        statement.executeUpdate();
        connection.close();
    }


}
