package kwic;

import java.util.ArrayList;

import pipe_structures.Pipe;
import pipe_structures.Pump;

/**
 * This Pump gets data from two text files and has 2 output pipe.
 * The 2 output are the list of titles and list of words to ignore.
 */
public class Input extends Pump<String>{

    public Input(ArrayList<Pipe<String>> output) {
        super(output);
    }

    @Override
    public void pumpData(ArrayList<Pipe<String>> pipes) {
        Pipe<String> titlePipe = pipes.get(0);
        titlePipe.write("The Day after Tomorrow");
        titlePipe.write("Fast and Furious");
        titlePipe.write("Man of Steel");

        titlePipe.close();
        System.out.println("titlePipe finished");

        Pipe<String> ignorePipe = pipes.get(1);
        ignorePipe.write("is");
        ignorePipe.write("the");
        ignorePipe.write("of");
        ignorePipe.write("and");
        ignorePipe.write("as");
        ignorePipe.write("a");
        ignorePipe.write("after");

        ignorePipe.close();
        System.out.println("ignorePipe finished");
    }

}
