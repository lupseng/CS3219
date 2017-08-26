package kwic;

import pipe_structures.Pipe;
import pipe_structures.Sink;

/**
 * This Sink has 1 input pipe which contains the sorted kwic.
 * The output will be saved to a text file.
 */
public class Output extends Sink<String> {

    public Output(Pipe<String> input) {
        super(input);
    }

    @Override
    public void read(Pipe<String> input) {
        try {
            String line;
            while ((line = input.read()) != null) {
                System.out.println(line);
            }
        } catch (InterruptedException e) {
            System.err.println("Program interrupted");
            e.printStackTrace();
        } finally {
            System.out.close();
        }

    }

}
