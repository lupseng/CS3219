package pipe_structures;

import java.util.ArrayList;

public abstract class Filter<I, O> extends Runner {
    protected ArrayList<Pipe<I>> input = new ArrayList<>();
    protected Pipe<O> output;

    public Filter(ArrayList<Pipe<I>> input, Pipe<O> output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        process(input, output);
    }

    protected abstract void process(ArrayList<Pipe<I>> input, Pipe<O> output);
}