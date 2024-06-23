package md.program.database.model;

public class LogPartner {

    private Integer id_logu;
    private Integer month;
    private Integer year;
    private Integer id_partner;
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

    public LogPartner() {
    }

    public LogPartner(Integer year, Integer month, Partner partner) {
        this.id_logu=0;
        this.month=month;
        this.year=year;
        this.id_partner=partner.getId();
        this.name= partner.getName();
        this.surname= partner.getSurname();
        this.address= partner.getAddress();
        this.postCode= partner.getPostCode();
        this.post= partner.getPost();
        this.nip= partner.getNip();
        this.peopleCount=partner.getPeopleCount();
        this.archives=partner.getArchives();
        this.company=partner.getCompany();
        this.meter=partner.getMeter();
    }


    public Integer getId_logu() {
        return id_logu;
    }

    public void setId_logu(Integer id_logu) {
        this.id_logu = id_logu;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getId_partner() {
        return id_partner;
    }

    public void setId_partner(Integer id_partner) {
        this.id_partner = id_partner;
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

    @Override
    public String toString() {
        return "LogPartner{" +
                "id_logu=" + id_logu +
                ", month=" + month +
                ", year=" + year +
                ", id_partner=" + id_partner +
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
                '}';
    }
}
