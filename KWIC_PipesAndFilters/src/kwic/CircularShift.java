package kwic;

import java.util.ArrayList;

import pipe_structures.Filter;
import pipe_structures.Pipe;

public class CircularShift extends Filter<String, String> {

    public CircularShift(ArrayList<Pipe<String>> input, Pipe<String> output) {
        super(input, output);
    }

    @Override
    protected void process(ArrayList<Pipe<String>> input, Pipe<String> output) {

        try {
            Pipe<String> titlePipe = input.get(0);
            Pipe<String> ignorePipe = input.get(1);

            ArrayList<String> ignoreList = getIgnoreListFromPipe(ignorePipe);   //this is the list of words to ignore

            String title;
            while ((title = titlePipe.read()) != null) {

                //TODO:

                output.write("result");//this writes to output pipe
            }
        } catch (InterruptedException e) {
            System.err.println("interrupted");
            e.printStackTrace();
            return;
        }
        output.close();
    }

    private ArrayList<String> getIgnoreListFromPipe(Pipe<String> ignorePipe) throws InterruptedException {
        ArrayList<String> ignoreList = new ArrayList<>();
        String word;

        while ((word = ignorePipe.read()) != null) {
            ignoreList.add(word);
        }

        return ignoreList;
    }

}
