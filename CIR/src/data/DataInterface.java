package data;

import java.util.ArrayList;

public interface DataInterface {
    public int getNumDocuments();
    public int getNumDocuments(int year);
    public int getNumDocuments(String conName);
    public int getNumDocuments(String conName, int year);

    public int getNumCitations();
    public int getNumCitations(int year);
    public int getNumCitations(String conName);
    public int getNumCitations(String conName, int year);

    public int getNumCitations(String citerName, int citerYear, int citedYear);
    public int getNumCitations(String citerName, int citerYear, String cited);

    public int getNumUniqueCitations();
    public int getNumUniqueCitations(int year);
    public int getNumUniqueCitations(String conName);
    public int getNumUniqueCitations(String conName, int year);

    public int getNumAuthors();
    public int getNumAuthors(int year);
    public int getNumAuthors(String conName);
    public int getNumAuthors(String conName, int year);

    public ArrayList<String> getAuthorNames();
    public ArrayList<String> getAuthorNames(int year);
    public ArrayList<String> getAuthorNames(String conName);
    public ArrayList<String> getAuthorNames(String conName, int year);

    public int getNumTimesCited(String authorName);
    public int getNumTimesCited(String authorName, int year);
    public int getNumTimesCited(String authorName, String conName);
    public int getNumTimesCited(String authorName, String conName, int year);

    public int getOldestCitationYear();
    public int getNewestCitationYear();

}
