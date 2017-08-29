package kwic;

import java.util.ArrayList;

import pipe_structures.Filter;
import pipe_structures.Pipe;

/**
 * This Filter has 2 input pipes and 1 output pipe.
 * The two input pipes are the list of titles and list of words to ignores.
 * The output is the kwic.
 *
 * Find keywords in titles and add new line to kwic for each keyword. Shift words such that each keyword is
 * the start of its own line.
 */
public class CircularShift extends Filter<String, String> {

    public CircularShift(ArrayList<Pipe<String>> input, Pipe<String> output) {
        super(input, output);
    }

    @Override
    protected void process(ArrayList<Pipe<String>> input, Pipe<String> output) {

        try {
            Pipe<String> titlePipe = input.get(0);
            Pipe<String> ignorePipe = input.get(1);

            ArrayList<String> ignoreList = getIgnoreListFromPipe(ignorePipe); // this is the list of words to ignore

            String title;
            while ((title = titlePipe.read()) != null) {

                String[] words = title.split(" ");

                for (String word : words) {
                    if (!ignoreList.contains(word.toLowerCase())) {
                        // keyword found
                        int index = title.indexOf(word);
                        output.write(title.substring(index).concat(" ").concat(title.substring(0, index)));
                        // this writes to output pipe
                    }
                }
            }

        } catch (InterruptedException e) {
            System.err.println("Program interrupted");
            e.printStackTrace();
            return;
        }
        output.close();
    }

    /**
     * This method gets the data from ignorePipe and creates the arraylist of words to ignore and returns it.
     *
     * @param ignorePipe
     *            the data comes from this pipe
     * @return ignoreList the arraylist of words to ignore
     */
    private ArrayList<String> getIgnoreListFromPipe(Pipe<String> ignorePipe) throws InterruptedException {
        ArrayList<String> ignoreList = new ArrayList<>();
        String word;

        while ((word = ignorePipe.read()) != null) {
            ignoreList.add(word);
        }

        return ignoreList;
    }

}
