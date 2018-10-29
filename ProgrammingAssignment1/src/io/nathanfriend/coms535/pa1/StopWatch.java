package io.nathanfriend.coms535.pa1;

/**
 * A convenient way to track how much time an operation takes
 *
 * @author Nathan Friend
 */
public class StopWatch {

  /** The number of ns that have been accumulated on this StopWatch instance so far */
  private long accumulated = 0;

  /** The System.nanoTime() when the StopWatch was last started */
  private long lastStart;

  /** The current state of the StopWatch */
  private StopWatchState state = StopWatchState.Stopped;

  /** Initializes a new Stopwatch instance in "stopped" mode */
  public StopWatch() {}

  /** Starts the StopWatch */
  public void start() {
    if (state == StopWatchState.Stopped) {
      lastStart = System.nanoTime();
      state = StopWatchState.Running;
    }
  }

  /** Pauses the StopWatch */
  public void pause() {
    if (state == StopWatchState.Running) {
      accumulated += System.nanoTime() - lastStart;
      state = StopWatchState.Stopped;
    }
  }

  /**
   * Gets the amount of time on this StopWatch instance in nanoseconds
   *
   * @return The number of nanoseconds elapsed on this StopWatch
   */
  public long getNanoTime() {
    if (state == StopWatchState.Running) {
      long extra = System.nanoTime() - lastStart;
      return accumulated + extra;
    } else {
      return accumulated;
    }
  }
}

/**
 * An enum of all possible StopWatch states
 *
 * @author Nathan Friend
 */
enum StopWatchState {
  Running,
  Stopped
}
