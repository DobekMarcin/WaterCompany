package md.program.database.model;

public class CounterYear {
    private Integer id;
    private Integer year;
    private Double counterRate;

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

    public Double getCounterRate() {
        return counterRate;
    }

    public void setCounterRate(Double counterRate) {
        this.counterRate = counterRate;
    }
}
