public class MinHashTimeRunner {

  /**
   * Runs the MinHashTime program
   *
   * @param args The command-line arguments (no arguments are used for this program)
   */
  public static void main(String[] args) {
    String folder = System.getProperty("user.dir") + "\\src\\space";
    MinHashTime mht = new MinHashTime();
    TimeReport report = mht.timer(folder, 600);
    System.out.println(report.toString());
  }
}
