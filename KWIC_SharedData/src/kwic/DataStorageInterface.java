package kwic;

import java.util.ArrayList;

public interface DataStorageInterface<T> {
    void addTitle(T title);
    void addWordsToIgnore(T wordToIgnore);
    void addShiftedWord(T shiftedWord);
    ArrayList<T> getTitles();
    ArrayList<T> getWordsToIgnore();
    ArrayList<T> getShiftedWords();
    void setInputFileName(String fileName);
    void setOutputFileName(String fileName);
    T getInputFileName();
    T getOutputFileName();
}
