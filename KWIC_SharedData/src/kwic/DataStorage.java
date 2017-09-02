package kwic;

import java.util.ArrayList;

public class DataStorage implements DataStorageInterface<String> {
    private static ArrayList<String> titles = new ArrayList<>();
    private static ArrayList<String> wordsToIgnore = new ArrayList<>(); // all lower case
    private static ArrayList<String> kwic = new ArrayList<>();
    private static String inFileName = "Input.txt"; // Default fileName
    private static String outFileName = "Output.txt"; // Default fileName

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
    public String getDefaultInputFileName() {
        return inFileName;
    }

    @Override
    public String getDefaultOutputFileName() {
        return outFileName;
    }
}
