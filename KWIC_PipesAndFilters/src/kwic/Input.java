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
import java.util.Scanner;

import pipe_structures.Pipe;
import pipe_structures.Pump;

/**
 * This Pump gets file names from user input and then gets data from the specified text file.
 * It has 3 output pipe. 2 output are the list of titles and list of words to ignore.
 * The third output pipe is for the output_file name.
 */
public class Input extends Pump<String> {
    private static String default_inFileName = "Input.txt"; // Default fileName
    private static String default_outFileName = "Output.txt"; // Default fileName

    public Input(ArrayList<Pipe<String>> output) {
        super(output);
    }

    @Override
    public void pumpData(ArrayList<Pipe<String>> pipes) {
        Pipe<String> titlePipe = pipes.get(0);
        Pipe<String> ignorePipe = pipes.get(1);
        Pipe<String> outFileNamePipe = pipes.get(2);

        String inFileName = getInFileName();
        String outFileName = getOutFileName();

        processFile(titlePipe, ignorePipe, inFileName); // get data from input file and write to pipes
        outFileNamePipe.write(outFileName); // tell Output the output_file name

        // close pipes
        titlePipe.close();
        ignorePipe.close();
        outFileNamePipe.close();
    }

    private String getInFileName() {
        String inFileName;
        System.out.println("Please enter file name to read from: (Just leave blank if using the default, Input.txt)");
        Scanner sc = new Scanner(System.in);
        inFileName = sc.nextLine();

        if (inFileName.equals("")) {
            return default_inFileName;
        } else {
            return inFileName;
        }
    }

    private String getOutFileName() {
        String outFileName;
        System.out.println("Please enter file name to save to: (Just leave blank to use the default, Output.txt)");
        Scanner sc = new Scanner(System.in);
        outFileName = sc.nextLine();

        if (outFileName.equals("")) {
            return default_outFileName;
        } else {
            return outFileName;
        }
    }

    private static void processFile(Pipe<String> titlePipe, Pipe<String> ignorePipe, String fileName) {
        try {

            File file = new File(fileName);
            InputStream inStream = new FileInputStream(file);
            InputStreamReader inStreamReader = new InputStreamReader(inStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inStreamReader);
            boolean changeArray = false;
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    changeArray = !changeArray;
                } else if (changeArray) {
                    ignorePipe.write(line.toLowerCase());   //case insensitive
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
