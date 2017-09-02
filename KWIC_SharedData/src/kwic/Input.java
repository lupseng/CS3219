package kwic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Input {
    private DataStorageInterface<String> dataManager;

    public Input(DataStorageInterface<String> dataManager) {
        this.dataManager = dataManager;
    }

    public void processInput() throws IOException {

        try {

            File file = new File(dataManager.getInputFileName());
            InputStream inStream = new FileInputStream(file);
            InputStreamReader inStreamReader = new InputStreamReader(inStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inStreamReader);
            boolean changeArray = false;
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    changeArray = !changeArray;
                } else if (changeArray) {
                    dataManager.addWordsToIgnore(line.toLowerCase()); // case insensitive
                } else {
                    dataManager.addTitle(line);
                }
            }

            reader.close();

        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("File Not Found!");
        } catch (IOException ex) {
            throw new IOException("Invalid File!");
        }
    }

}
