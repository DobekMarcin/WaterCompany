package md.program.database.model;

public class RateYear {
    private Integer id;
    private Integer year;
    private Double rate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public RateYear() {
        id=0;
        year=0;
        rate=0d;
    }
}
