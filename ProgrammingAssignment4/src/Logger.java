import java.text.SimpleDateFormat;
import java.util.Date;

/** A logging wrapper to allow centralized logging control */
public class Logger {

  /** Whether or not logging is enabled */
  public static boolean isLoggingEnabled = false;

  /** Date format for log messages */
  private static SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");

  /** ANSI green color */
  private static final String ANSI_GREEN = "\u001B[32m";

  /** ANSI reset color */
  public static final String ANSI_RESET = "\u001B[0m";

  /**
   * Logs a message the console if logging is enabled
   *
   * @param message The message to log to the console
   */
  public static void log(String message) {

    if (isLoggingEnabled) {
      String timestamp = format.format(new Date());
      System.out.println(ANSI_GREEN + timestamp + ": " + ANSI_RESET + message);
    }
  }
}
