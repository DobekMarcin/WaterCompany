package md.program.database.model;

import java.util.Objects;

public class Partner implements  Cloneable{
    private Integer id;
    private String name;
    private String surname;
    private String address;
    private String postCode;
    private String post;
    private String nip;
    private Integer peopleCount;
    private Boolean archives;
    private Boolean company;
    private Boolean meter;
    private Integer year;
    private Integer month;

    public Partner() {
    }

    public Partner(Integer id, String name, String surname, String address, String postCode, String post, String nip, Integer peopleCount, Boolean archives, Boolean company, Boolean meter,Integer year,Integer month) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.postCode = postCode;
        this.post = post;
        this.nip = nip;
        this.peopleCount = peopleCount;
        this.archives = archives;
        this.company = company;
        this.meter = meter;
        this.year = year;
        this.month = month;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }

    public Boolean getArchives() {
        return archives;
    }

    public void setArchives(Boolean archives) {
        this.archives = archives;
    }

    public Boolean getCompany() {
        return company;
    }

    public void setCompany(Boolean company) {
        this.company = company;
    }

    public Boolean getMeter() {
        return meter;
    }

    public void setMeter(Boolean meter) {
        this.meter = meter;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public boolean equals(Partner partner) {
        if(this.id==partner.id && this.name.equals(partner.name) && this.surname.equals(partner.surname) && this.address.equals(partner.address) &&
        this.postCode.equals(partner.getPostCode()) && this.post.equals(partner.post) && this.nip.equals(partner.nip) && this.peopleCount==partner.peopleCount &&
        this.archives==partner.archives && this.company==partner.company && this.meter== partner.meter && this.year==partner.year && this.month == partner.month)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "Partner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", postCode='" + postCode + '\'' +
                ", post='" + post + '\'' +
                ", nip='" + nip + '\'' +
                ", peopleCount=" + peopleCount +
                ", archives=" + archives +
                ", company=" + company +
                ", meter=" + meter +
                ", year=" + year +
                ", month=" + month +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
