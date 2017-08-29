package kwic;

import java.util.ArrayList;
import java.util.Collections;

import pipe_structures.Filter;
import pipe_structures.Pipe;

/**
 * This Filter has one input pipe and one output pipe.
 * The input is the unsorted kwic and the output is the sorted kwic
 */
public class Alphabetizer extends Filter<String, String> {

    public Alphabetizer(ArrayList<Pipe<String>> input, Pipe<String> output) {
        super(input, output);
    }

    @Override
    protected void process(ArrayList<Pipe<String>> input, Pipe<String> output) {
        try {
            Pipe<String> kwicPipe = input.get(0);
            ArrayList<String> kwic = new ArrayList<>();

            String line;
            while ((line = kwicPipe.read()) != null) {
                kwic.add(line);
            }

            Collections.sort(kwic);

            for (String sortedLine : kwic) {
                output.write(sortedLine);
            }

        } catch (InterruptedException e) {
            System.err.println("Program interrupted");
            e.printStackTrace();
            return;
        }
        output.close();
    }

}
