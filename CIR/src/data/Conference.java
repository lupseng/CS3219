package data;

public class Conference extends DocumentOwner {
    private String name;

    public Conference(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumTimesYearCited(int year) {
        int count = 0;
        for (Document doc : docs) {
            count += doc.getNumTimesYearCited(year);
        }
        return count;
    }

    public int getNumTimesConCited(String con) {
        int count = 0;
        for (Document doc : docs) {
            count += doc.getNumTimesConCited(con);
        }
        return count;
    }

}
