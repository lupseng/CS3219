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

import pipe_structures.Pipe;
import pipe_structures.Pump;

/**
 * This Pump gets data from two text files and has 2 output pipe.
 * The 2 output are the list of titles and list of words to ignore.
 */
public class Input extends Pump<String>{

    public Input(ArrayList<Pipe<String>> output) {
        super(output);
    }

    @Override
    public void pumpData(ArrayList<Pipe<String>> pipes) {
        Pipe<String> titlePipe = pipes.get(0);
        titlePipe.write("The Day after Tomorrow");
        titlePipe.write("Fast and Furious");
        titlePipe.write("Man of Steel");

        Pipe<String> ignorePipe = pipes.get(1);
        ignorePipe.write("is");
        ignorePipe.write("the");
        ignorePipe.write("of");
        ignorePipe.write("and");
        ignorePipe.write("as");
        ignorePipe.write("a");
        ignorePipe.write("after");

        try {
            String fileName = getFileName(pipes.get(2));
            input(titlePipe, ignorePipe, fileName);
        } catch (InterruptedException ex) {
            System.out.println("Program Interrupted");
        }

        //close pipes
        titlePipe.close();
        ignorePipe.close();
        System.out.println("titlePipe & ignorePipe finished");
    }

    private String getFileName(Pipe<String> fileNamePipe) throws InterruptedException{
        String fileName;

        if((fileName = fileNamePipe.read()) != null) {
            fileNamePipe.close();
            return fileName;
        }

        return fileName;
    }

    private static void input(Pipe<String> titlePipe, Pipe<String> ignorePipe, String fileName) {
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
                    ignorePipe.write(line);
                } else {
                    titlePipe.write(line);
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

}
