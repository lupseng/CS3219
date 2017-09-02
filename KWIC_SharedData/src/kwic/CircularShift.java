package kwic;

public class CircularShift {
    private DataStorageInterface<String> dataManager;

    public CircularShift(DataStorageInterface<String> dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * Find keywords in titles and add new line to kwic for each keyword. Shift words such that each keyword
     * is the start of its own line.
     */
    public void doCircularShift() {
        for (String title : dataManager.getTitles()) {
            String[] words = title.split(" ");

            for (String word : words) {
                if (!dataManager.getWordsToIgnore().contains(word.toLowerCase())) {
                    // keyword found
                    int index = title.indexOf(word);
                    dataManager.addShiftedWord(title.substring(index).concat(" ").concat(title.substring(0, index)));
                }
            }
        }
    }

}
