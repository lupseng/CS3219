package kwic;

import java.util.Collections;

public class Alphabetizer {
    private DataStorageInterface<String> dataManager;

    public Alphabetizer(DataStorageInterface<String> dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * sorts kwic alphabetically.
     */
    public void alphabetize() {
        Collections.sort(dataManager.getShiftedWords(), String.CASE_INSENSITIVE_ORDER);
    }

}
