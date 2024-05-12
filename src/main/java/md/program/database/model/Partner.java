package md.program.database.model;

public class Partner {
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

    public Partner() {
    }

    public Partner(Integer id, String name, String surname, String address, String postCode, String post, String nip, Integer peopleCount, Boolean archives, Boolean company, Boolean meter) {
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
}
