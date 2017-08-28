package kwic;

import java.util.ArrayList;

import pipe_structures.Filter;
import pipe_structures.Pipe;
import pipe_structures.PipeImpl;
import pipe_structures.Pump;
import pipe_structures.Sink;

/**
 * This is the main class of the program.
 * It handles the creation of the Pipe and Filters and starts them.
 */
public class kwic {

    public static void main(String[] args) {
        //Default FileName
        final String defaultfileName = "Input";

        // create pipes
        final Pipe<String> titlePipe = new PipeImpl<String>();
        final Pipe<String> ignorePipe = new PipeImpl<String>();
        final Pipe<String> fileNamePipe = new PipeImpl<String>();
        final Pipe<String> kwicPipe = new PipeImpl<String>();
        final Pipe<String> sortedKwicPipe = new PipeImpl<String>();

        ArrayList<Pipe<String> > inputPipes = new ArrayList<>();
        fileNamePipe.write((args.length  > 0) ? args[0] : defaultfileName);
        inputPipes.add(titlePipe);
        inputPipes.add(ignorePipe);
        inputPipes.add(fileNamePipe);

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

        System.out.println("DONE");
    }

}
