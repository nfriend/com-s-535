/** A logging wrapper to allow centralized logging control */
public class Logger {

  /** Whether or not logging is enabled */
  public static boolean isLoggingEnabled = false;

  /**
   * Logs a message the console if logging is enabled
   *
   * @param message The message to log to the console
   */
  public static void log(String message) {

    if (isLoggingEnabled) {
      System.out.println(message);
    }
  }
}
