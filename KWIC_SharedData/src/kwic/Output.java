package kwic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Output {
    private DataStorageInterface<String> dataManager;

    public Output(DataStorageInterface<String> dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * prints kwic into a file
     */
    public void printOutput() {

        try {

            File file = new File(dataManager.getOutputFileName());

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), false);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            for (String s : dataManager.getShiftedWords()) {
                writer.write(s);
                writer.newLine();
                System.out.println(s);
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }

}
