package md.program.database.model;

public class Partner {
    private Integer id;
    private String name;
    private String surname;
    private String address;
    private Integer peopleCount;
    private Boolean archives;

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
}
