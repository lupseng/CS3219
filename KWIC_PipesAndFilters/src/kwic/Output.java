package kwic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import pipe_structures.Pipe;
import pipe_structures.Sink;

/**
 * This Sink has 1 input pipe which contains the sorted kwic.
 * The output will be saved to a text file.
 */
public class Output extends Sink<String> {
    private String fileName = "Output"; //Default fileName

    public Output(Pipe<String> input, Pipe<String> fileName) {
        super(input);
        this.fileName = getFileName(fileName);
    }

    @Override
    public void read(Pipe<String> input) {
        try {
            File file = new File(fileName);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);  //true, file will not be overwritten.
            BufferedWriter writer = new BufferedWriter(fileWriter);

            String line;
            while ((line = input.read()) != null) {
                writer.write(line);
                writer.newLine();
                System.out.println(line);
            }

            writer.close();
        } catch (InterruptedException e) {
            System.err.println("Program interrupted");
            e.printStackTrace();
        } catch(IOException e) {
            System.err.println("Failed to write to file!");

        } finally {
            System.out.close();
        }

    }

    public String getFileName(Pipe<String> fileNameOutPipe) {
        try {
            String definedName = fileNameOutPipe.read();
            fileNameOutPipe.close();
            return definedName != null ? definedName : fileName;
        } catch (InterruptedException e) {
            System.err.println("Program interrupted while reading fileName");
            e.printStackTrace();
        }
        return fileName;
    }

}
