package kwic;

import java.util.ArrayList;

import pipe_structures.Filter;
import pipe_structures.Pipe;
import pipe_structures.PipeImpl;
import pipe_structures.Pump;
import pipe_structures.Sink;

public class kwic {
    public static void main(String[] args) {
        // create pipes
        final Pipe<String> titlePipe = new PipeImpl<String>();
        final Pipe<String> ignorePipe = new PipeImpl<String>();
        final Pipe<String> kwicPipe = new PipeImpl<String>();
        final Pipe<String> sortedKwicPipe = new PipeImpl<String>();

        ArrayList<Pipe<String> > inputPipes = new ArrayList<>();
        inputPipes.add(titlePipe);
        inputPipes.add(ignorePipe);

        ArrayList<Pipe<String> > kwicPipes = new ArrayList<>();
        kwicPipes.add(kwicPipe);

        // create components that use the pipes
        final Pump<String> input = new Input(inputPipes);
        final Filter<String, String> circularShift = new CircularShift(inputPipes, kwicPipe);
        final Filter<String, String> alphabetizer = new Alphabetizer(kwicPipes, sortedKwicPipe);
        final Sink<String> output = new Output(sortedKwicPipe);

        // start all components
        input.start();
        circularShift.start();
        alphabetizer.start();
        output.start();

        System.out.println("runner finished");
    }
}
