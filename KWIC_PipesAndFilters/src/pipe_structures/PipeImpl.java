package pipe_structures;

import java.io.IOException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class PipeImpl<T> implements Pipe<T> {
    private Queue<T> buffer = new LinkedList<T>();
    private boolean isOpen = true;
    private boolean isDone = false;

    @Override
    public synchronized boolean write(T obj) {
        if (!isOpen) {
            throw new RuntimeException(new IOException("Error. Cannot write to a closed pipe."));
        } else if (obj == null) {
            throw new IllegalArgumentException("Error. Unable to write null to pipe.");
        }

        boolean isAdded = buffer.add(obj);
        notify();
        // System.out.println("added to pipe: " + (obj==null?"<null>":obj.toString()));
        return isAdded;
    }

    @Override
    public synchronized T read() throws InterruptedException {
        if (isDone) {
            throw new NoSuchElementException("Error. Cannot read from a close pipe.");
        }

        while (buffer.isEmpty()) {
            wait(); // wait for data
        }

        T obj = buffer.remove();
        if (obj == null) { // last element!
            isDone = true;
        }
        return obj;
    }

    @Override
    public synchronized void close() {
        isOpen = false;
        buffer.add(null);
        notify();
    }

}
