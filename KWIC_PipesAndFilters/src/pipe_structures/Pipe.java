package pipe_structures;

public interface Pipe<T> {
    public boolean write(T obj);
    public T read() throws InterruptedException;
    public void close();

}
