package md.program.utils.converters;

import md.program.database.model.Company;
import md.program.modelFX.CompanyFX;


public class CompanyConverter {

    public static Company convertToCompany(CompanyFX companyFX) {
        Company company = new Company();
        company.setId(companyFX.getId());
        company.setName(companyFX.getName());
        company.setNip(companyFX.getNip());
        company.setPlace(companyFX.getPlace());
        company.setPost_code(companyFX.getPost_code());
        company.setPost(companyFX.getPost());
        company.setAddress(companyFX.getAddress());
        company.setPhone(companyFX.getPhone());
        company.setEmail(companyFX.getEmail());

return company;
    }

    public static CompanyFX convertToCompanyFXX(Company company) {
        CompanyFX companyFX = new CompanyFX();

        companyFX.setId(company.getId());
        companyFX.setName(company.getName());
        companyFX.setNip(company.getNip());
        companyFX.setPlace(company.getPlace());
        companyFX.setPost_code(company.getPost_code());
        companyFX.setPost(company.getPost());
        companyFX.setAddress(company.getAddress());
        companyFX.setPhone(company.getPhone());
        companyFX.setEmail(company.getEmail());
        return companyFX;
    }




}
