package data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Document {
    private Set<Author> authors;// diff spellings of same person?
    private ArrayList<Citation> cites;
    private Conference conference;
    private Year year;

    public Document(Conference conference, Year year) {
        this.setConference(conference);
        this.setYear(year);
        this.authors = new HashSet<>();
        this.cites = new ArrayList<>();
    }

    public int getNumAuthors() {
        return authors.size();
    }

    public int getNumCites() {
        return cites.size();
    }

    public int getNumUniqueCites() {
        Set<Citation> unique = new HashSet<Citation>(cites);
        return unique.size();
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public void addAuthor(String name) {
        this.authors.add(new Author(name));
    }

    public void addCite(String conferenceName, String authorName, int year) {
        this.cites.add(new Citation(conferenceName, authorName, year));
    }

    public ArrayList<String> getAuthorNames() {
        ArrayList<String> toReturn = new ArrayList<>();

        for (Author a : authors) {
            toReturn.add(a.getName());
        }

        return toReturn;
    }

    public int getNumTimesAuthorCited(String authorName) {
        int count = 0;
        for (Citation cite : cites) {
            if (cite.getAuthorName().equals(authorName)) {
                count++;
            }
        }
        return count;
    }

    public int getNumTimesYearCited(int year) {
        int count = 0;
        for (Citation cite : cites) {
            if (cite.getYear() == year) {
                count++;
            }
        }
        return count;
    }

    public int getNumTimesConCited(String conName) {
        int count = 0;
        for (Citation cite : cites) {
            if (cite.getConferenceName().equals(conName)) {
                count++;
            }
        }
        return count;
    }
}
