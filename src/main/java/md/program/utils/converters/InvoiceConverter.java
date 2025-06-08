package md.program.utils.converters;

import md.program.database.model.Company;
import md.program.database.model.Invoice;
import md.program.modelFX.CompanyFX;
import md.program.modelFX.InvoiceFX;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InvoiceConverter {

    public static Invoice convertToInvoice(InvoiceFX invoiceFX) throws ParseException {
        Invoice invoice = new Invoice();
        invoice.setId(invoiceFX.getId());
        invoice.setYear(invoiceFX.getYear());
        invoice.setInvoiceDate(Date.valueOf(invoiceFX.getInvoiceDate().toString()));
        invoice.setCompanyId(invoiceFX.getCompanyId());
        invoice.setCompanyName(invoiceFX.getCompanyName());
        invoice.setInvoiceDes(invoiceFX.getInvoiceDes());
        invoice.setPk(invoiceFX.isPk());
        invoice.setAmount(invoiceFX.getAmount());
        invoice.setInvoiceNumber(invoiceFX.getInvoiceNumber());

        return invoice;
    }

    public static InvoiceFX convertToInvoiceFX(Invoice invoice) {
        InvoiceFX invoiceFX = new InvoiceFX();

        invoiceFX.setId(invoice.getId());
        invoiceFX.setYear(invoice.getYear());
        invoiceFX.setInvoiceDate(invoice.getInvoiceDate().toString());
        invoiceFX.setCompanyId(invoice.getCompanyId());
        invoiceFX.setCompanyName(invoice.getCompanyName());
        invoiceFX.setInvoiceDes(invoice.getInvoiceDes());
        invoiceFX.setPk(invoice.getPk());
        invoiceFX.setAmount(invoice.getAmount());
        invoiceFX.setInvoiceNumber(invoice.getInvoiceNumber());

        return invoiceFX;
    }


}
