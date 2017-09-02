package kwic;

import java.util.ArrayList;

public class DataStorage implements DataStorageInterface<String> {
    private static ArrayList<String> titles = new ArrayList<>();
    private static ArrayList<String> wordsToIgnore = new ArrayList<>(); // all lower case
    private static ArrayList<String> kwic = new ArrayList<>();
    private String inFileName = "Input.txt"; // Default fileName
    private String outFileName = "Output.txt"; // Default fileName

    public DataStorage() {

    }

    @Override
    public void addTitle(String title) {
        titles.add(title);
    }

    @Override
    public void addWordsToIgnore(String wordToIgnore) {
        wordsToIgnore.add(wordToIgnore);
    }

    @Override
    public void addShiftedWord(String shiftedWord) {
        kwic.add(shiftedWord);
    }

    @Override
    public ArrayList<String> getTitles() {
        return titles;
    }

    @Override
    public ArrayList<String> getWordsToIgnore() {
        return wordsToIgnore;
    }

    @Override
    public ArrayList<String> getShiftedWords() {
        return kwic;
    }

    @Override
    public String getInputFileName() {
        return inFileName;
    }

    @Override
    public String getOutputFileName() {
        return outFileName;
    }

    @Override
    public void setInputFileName(String fileName) {
        this.inFileName = fileName;
    }

    @Override
    public  void setOutputFileName(String fileName) {
        this.outFileName = fileName;
    }

}
