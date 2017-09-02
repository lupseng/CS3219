package kwic;

import java.io.IOException;

public class MasterControl {
    private static DataStorageInterface<String> dataManager;
    private static Input input;
    private static CircularShift circularShift;
    private static Alphabetizer alphabetizer;
    private static Output output;

    public static void main(String[] args) {
        init(args);
        processingData();

    }

    public static void init(String[] args) {

        dataManager = new DataStorage();
        input = new Input(dataManager);
        circularShift = new CircularShift(dataManager);
        alphabetizer = new Alphabetizer(dataManager);
        output = new Output(dataManager);

        //set Input and Output fileNames;
        if(args.length == 2) {
            dataManager.setInputFileName(args[0]);
            dataManager.setInputFileName(args[1]);
        }

    }

    public static void processingData() {

        try {
            input.processInput();
            circularShift.doCircularShift();
            alphabetizer.alphabetize();
            output.printOutput();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
