package data;

import java.util.ArrayList;

public class Citation {

    private String conferenceName;
    private ArrayList<String> authorNames;
    private int year;//format 4 digits

    public Citation(String conferenceName, ArrayList<String> authorName, int year) {
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

    public ArrayList<String> getAuthorNames() {
        return authorNames;
    }

    public void setAuthorName(ArrayList<String> authorName) {
        authorName.sort(String::compareToIgnoreCase);
        this.authorNames = authorName;
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
                && this.authorNames.equals(otherCitation.getAuthorNames())
                && this.year == otherCitation.getYear()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 31 * this.getConferenceName().hashCode() + this.getAuthorNames().hashCode() + this.getYear();
    }
}
