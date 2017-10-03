package data;

public class Citation {

    private String conferenceName;
    private String authorName;
    private int year;

    public Citation(String conferenceName, String authorName, int year) {
        this.setConferenceName(conferenceName);
        this.setAuthorName(authorName);
        this.setYear(year);
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object other){
        if(other == null){
            return false;
        }
        if(!(other instanceof Citation)){
            return false;
        }
        Citation otherCitation = (Citation) other;
        if(this.conferenceName.equals(otherCitation.getConferenceName())
                && this.authorName.equals(otherCitation.getAuthorName())
                && this.year == otherCitation.getYear()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 31 * this.getConferenceName().hashCode() + this.getAuthorName().hashCode() + this.getYear();
    }
}
