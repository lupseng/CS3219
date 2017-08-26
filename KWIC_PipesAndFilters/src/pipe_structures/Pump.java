package pipe_structures;

import java.util.ArrayList;

public abstract class Pump<T> extends Runner {
    protected ArrayList<Pipe<T>> output = new ArrayList<>();

    public Pump(ArrayList<Pipe<T>> output) {
        this.output = output;
    }

    @Override
    public void run() {
        pumpData(output);
    }

    public abstract void pumpData(ArrayList<Pipe<T>> pipe);
}