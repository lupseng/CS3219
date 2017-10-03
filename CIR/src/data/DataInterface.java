package data;

import java.util.ArrayList;

public interface DataInterface {
    public int getNumDocuments();
    public int getNumDocuments(int year);
    public int getNumDocuments(String conName);

    public int getNumCitations();
    public int getNumCitations(int year);
    public int getNumCitations(String conName);

    public int getNumCitations(String citer, int year);
    public int getNumCitations(String citer, String cited);

    public int getNumUniqueCitations();
    public int getNumUniqueCitations(int year);
    public int getNumUniqueCitations(String conName);

    public int getNumAuthors();
    public int getNumAuthors(int year);
    public int getNumAuthors(String conName);

    public ArrayList<String> getAuthorNames();
    public ArrayList<String> getAuthorNames(int year);
    public ArrayList<String> getAuthorNames(String conName);

    public int getNumTimesCited(String authorName);
    public int getNumTimesCited(String authorName, int year);
    public int getNumTimesCited(String authorName, String conName);

    public int getOldestYear();
    public int getNewestYear();

}
