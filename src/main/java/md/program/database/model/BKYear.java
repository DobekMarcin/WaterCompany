package md.program.database.model;


public class BKYear {

    private int id;
    private int year;

    public BKYear(int id, int year) {
        this.id = id;
        this.year = year;
    }

    public BKYear() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
