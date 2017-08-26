package kwic;

import java.util.ArrayList;

public class MasterControl {
    private static ArrayList<String> titles = new ArrayList<>();
    private static ArrayList<String> wordsToIgnore = new ArrayList<>(); //all lower case
    private static ArrayList<String> kwic = new ArrayList<>();

    public static void main(String[] args) {
        input();
        circularShift();
        alphabetizer();
        output();
    }

    /**
     * Gets input from files and store them into titles and wordsToIgnore.
     */
    private static void input(){
        titles.add("The Day after Tomorrow");
        titles.add("Fast and Furious");
        titles.add("Man of Steel");

        //toLowerCase() before add to wordsToIgnore
        wordsToIgnore.add("is");
        wordsToIgnore.add("the");
        wordsToIgnore.add("of");
        wordsToIgnore.add("and");
        wordsToIgnore.add("as");
        wordsToIgnore.add("a");
        wordsToIgnore.add("after");
    }

    /**
     * Find keywords in titles and add new line to kwic for each keyword.
     * Shift words such that each keyword is the start of its own line.
     */
    private static void circularShift(){
        for(String title : titles){
            String[] words = title.split(" ");

            for(String word : words){
                if(!wordsToIgnore.contains(word.toLowerCase())){
                    //keyword found
                    int index = title.indexOf(word);
                    kwic.add(title.substring(index).concat(" ").concat(title.substring(0, index)));
                }
            }
        }

    }

    /**
     * sorts kwic alphabetically.
     */
    private static void alphabetizer(){


    }

    /**
     * prints kwic into a file
     */
    private static void output(){

        for(String s : kwic){
            System.out.println(s);
        }
    }

}
