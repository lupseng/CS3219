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

        // create pipes
        final Pipe<String> titlePipe = new PipeImpl<String>();
        final Pipe<String> ignorePipe = new PipeImpl<String>();
        final Pipe<String> outFileNamePipe = new PipeImpl<String>();
        final Pipe<String> kwicPipe = new PipeImpl<String>();
        final Pipe<String> sortedKwicPipe = new PipeImpl<String>();

        ArrayList<Pipe<String> > inputPipes = new ArrayList<>(); //used by Input and CircularShift
        inputPipes.add(titlePipe);          //connect to CircularShift
        inputPipes.add(ignorePipe);         //connect to CircularShift
        inputPipes.add(outFileNamePipe);    //connect to Output

        ArrayList<Pipe<String> > kwicPipes = new ArrayList<>(); //used by Alphabetizer
        kwicPipes.add(kwicPipe);

        ArrayList<Pipe<String> > outputPipes = new ArrayList<>(); //used by Output
        outputPipes.add(sortedKwicPipe);      //connect from Alphabetizer
        outputPipes.add(outFileNamePipe);    //connect from Input

        // create components that use the pipes
        final Pump<String> input = new Input(inputPipes);
        final Filter<String, String> circularShift = new CircularShift(inputPipes, kwicPipe);
        final Filter<String, String> alphabetizer = new Alphabetizer(kwicPipes, sortedKwicPipe);
        final Sink<String> output = new Output(outputPipes);

        // start all components
        input.start();
        circularShift.start();
        alphabetizer.start();
        output.start();

        //System.out.println("DONE");
    }

}
