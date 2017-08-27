package kwic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;

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

        try {

            File file = new File("Input");
            InputStream inStream = new FileInputStream(file);
            InputStreamReader inStreamReader = new InputStreamReader(inStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inStreamReader);
            boolean changeArray = false;
            String line;

            while((line = reader.readLine()) != null) {
                if(line.isEmpty()) {
                    changeArray = !changeArray;
                } else if(changeArray) {
                    wordsToIgnore.add(line);
                } else {
                    titles.add(line);
                }
            }

            reader.close();

        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found!");
        } catch (RuntimeException ex) {
            System.out.println("Error. Cannot write to a closed pipe!");
        } catch (IOException ex) {
            System.out.println("Invalid File!");
        }
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
        Collections.sort(kwic);
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
