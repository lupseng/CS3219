package pipe_structures;

public abstract class Runner implements Runnable {
    private boolean isRunning = false;

    @Override
    abstract public void run();

    public void start() {
        if (!isRunning) {
            Thread thread = new Thread(this);
            thread.start();
            isRunning = true;
        }
    }

    public void stop() {
        isRunning = false;
    }
}
