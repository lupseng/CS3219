package pipe_structures;

import java.util.ArrayList;

public abstract class Sink<T> extends Runner{
    protected ArrayList<Pipe<T>> input = new ArrayList<>();

    public Sink(ArrayList<Pipe<T>> input) {
        this.input = input;
    }

    @Override
    public void run(){
        read(input);
    }

    public abstract void read(ArrayList<Pipe<T>> pipe);
}
