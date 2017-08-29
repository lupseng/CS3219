package kwic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import pipe_structures.Pipe;
import pipe_structures.Sink;

/**
 * This Sink has 2 input pipe which contains the sorted kwic and the output_file name.
 * The output will be saved to a text file with the specified name.
 */
public class Output extends Sink<String> {

    public Output(ArrayList<Pipe<String>> input) {
        super(input);
    }

    @Override
    public void read(ArrayList<Pipe<String>> input) {
        Pipe<String> contentPipe = input.get(0);
        Pipe<String> fileNamePipe = input.get(1);
        String fileName = getFileName(fileNamePipe);

        try {
            File file = new File(fileName);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), false);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            String line;
            while ((line = contentPipe.read()) != null) {
                writer.write(line);
                writer.newLine();
                System.out.println(line);
            }

            writer.close();
        } catch (InterruptedException e) {
            System.err.println("Program interrupted");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Failed to write to file!");
        } finally {
            System.out.close();
        }

        contentPipe.close();
    }

    private String getFileName(Pipe<String> fileNamePipe) {
        String fileName = "";

        try {
            fileName = fileNamePipe.read();
            fileNamePipe.close();
        } catch (InterruptedException e) {
            System.err.println("Program interrupted while reading fileName");
            e.printStackTrace();
        }
        return fileName;
    }

}
