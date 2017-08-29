package kwic;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
    private static String inFileName = "Input.txt"; //Default fileName
    private static String outFileName = "Output.txt"; //Default fileName

    public static void main(String[] args) {
        try {
            input((args.length  > 0) ? args[0] : inFileName);
            circularShift();
            alphabetizer();
            output((args.length  > 1) ? args[1] : outFileName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gets input from files and store them into titles and wordsToIgnore.
     * @throws IOException
     */
    private static void input(String fileName) throws IOException{

        try {

            File file = new File(fileName);
            InputStream inStream = new FileInputStream(file);
            InputStreamReader inStreamReader = new InputStreamReader(inStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inStreamReader);
            boolean changeArray = false;
            String line;

            while((line = reader.readLine()) != null) {
                if(line.isEmpty()) {
                    changeArray = !changeArray;
                } else if(changeArray) {
                    wordsToIgnore.add(line.toLowerCase());  //case insensitive
                } else {
                    titles.add(line);
                }
            }

            reader.close();

        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("File Not Found!");
        } catch (IOException ex) {
            throw new IOException("Invalid File!");
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
    private static void output(String fileName){

        try {

        File file = new File(fileName);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), false);
        BufferedWriter writer = new BufferedWriter(fileWriter);

        for(String s : kwic){
            writer.write(s);
            writer.newLine();
            System.out.println(s);
        }

        writer.close();

        } catch(IOException e) {
            System.out.println("Error writing to file");
        }
    }

}
