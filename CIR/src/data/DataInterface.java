package data;

import java.util.ArrayList;

public interface DataInterface {
    public int getNumOfDocuments();
    public int getNumOfDocuments(int year);
    public int getNumOfDocuments(String conName);

    public int getNumOfCitations();
    public int getNumOfCitations(int year);
    public int getNumOfCitations(String conName);

    public int getNumOfCitations(String citer, int year);
    public int getNumOfCitations(String citer, String cited);

    public int getNumOfUniqueCitations();
    public int getNumOfUniqueCitations(int year);
    public int getNumOfUniqueCitations(String conName);

    public int getNumOfAuthors();
    public int getNumOfAuthors(int year);
    public int getNumOfAuthors(String conName);

    public ArrayList<String> getAuthorNames();
    public ArrayList<String> getAuthorNames(int year);
    public ArrayList<String> getAuthorNames(String conName);

    public int getNumTimesCited(String authorName);
    public int getNumTimesCited(String authorName, int year);
    public int getNumTimesCited(String authorName, String conName);

    public int getOldestYear();
    public int getNewestYear();

}
