package utils;

public class Timer {
    protected CurrentTimeSupplier currentTimeSupplier = new CurrentTimeSupplier();

    private long startTs;
    private long stopTs;

    public Timer() {
        this.startTs = 0;
        this.stopTs = 0;
    }

    public void setCurrentTimeSupplier(CurrentTimeSupplier currentTimeSupplier) {
        this.currentTimeSupplier = currentTimeSupplier;
    }

    /**
     * Start the timer.
     */
    public void start() {
        startTs = currentTimeSupplier.get().toEpochMilli();
    }

    /**
     * Stop the timer.
     */
    public void stop() {
        stopTs = stopTs != 0 ? stopTs : currentTimeSupplier.get().toEpochMilli();
    }

    /**
     * This function returns:
     * - 0 if the timer has not been started,
     * - the elasped time between the start time and now if the timer has not been stopped,
     * - the elapsed time between the start and stop times otherwise.
     */
    public long getElapsedTime() {
        long elapsedTime = 0;
        if (startTs != 0) {
            if (stopTs != 0) {
                elapsedTime = stopTs - startTs;
            } else {
                elapsedTime = currentTimeSupplier.get().toEpochMilli() - startTs;
            }
        }
        return elapsedTime;
    }
}
