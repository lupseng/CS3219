package kwic;

import pipe_structures.Pipe;
import pipe_structures.Sink;

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
            System.out.println("sink finished");
        } catch (InterruptedException e) {
            System.err.println("interrupted");
            e.printStackTrace();
        } finally {
            System.out.close();
        }

    }

}
