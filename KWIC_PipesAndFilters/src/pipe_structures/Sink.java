package pipe_structures;

public abstract class Sink<T> extends Runner{
    protected Pipe<T> input;

    public Sink(Pipe<T> input) {
        this.input = input;
    }

    @Override
    public void run(){
        read(input);
    }

    public abstract void read(Pipe<T> pipe);
}
