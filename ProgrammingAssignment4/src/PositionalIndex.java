import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class PositionalIndex {

  /** The inverted index */
  private HashMap<String, List<Posting>> index = new HashMap<String, List<Posting>>();

  /**
   * Creates a new PositionalIndex instance
   *
   * @param rootFolder The folder containing the documents to index
   */
  public PositionalIndex(String rootFolder) {
    File[] files = new File(rootFolder).listFiles();

    // handle the case where the rootFolder parameter is invalid
    if (files == null) {
      throw new IllegalArgumentException(
          "The directory path provided, \"" + rootFolder + "\", is not a directory");
    }

    // process the files in parallel
    Date start = new Date();
    Arrays.asList(files)
        .parallelStream()
        .forEach(
            file -> {
              Logger.log("Indexing file " + file.getName());

              // read all of the lines of the file
              List<String> lines = new ArrayList<String>();
              try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                  lines.add(line);
                }
              } catch (IOException e) {
                System.out.println("Error while reading file " + file.getAbsolutePath());
                e.printStackTrace();
              }

              // extract the terms and their positions from the file's lines
              HashMap<String, List<Integer>> terms = TermExtractor.extract(lines);

              // add the terms and their positions to our index
              terms
                  .keySet()
                  .forEach(
                      term -> {
                        synchronized (index) {
                          if (!index.containsKey(term)) {
                            index.put(term, new ArrayList<Posting>());
                          }

                          index.get(term).add(new Posting(file.getName(), terms.get(term)));
                        }
                      });
            });

    Date end = new Date();
    Logger.log("Done indexing after " + ((end.getTime() - start.getTime()) / 1000) + " seconds");
  }

  /**
   * Returns the number of times term appears in doc.
   *
   * @param term The term to test
   * @param doc The document to test
   * @return The number of times term appears in doc
   */
  public int termFrequency(String term, String doc) {
    throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * Returns the number of documents in which <tt>term</tt> appears.
   *
   * @param term The term to test
   * @return The number of documents
   */
  public int docFrequency(String term) {
    throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * Returns the string representation of <tt>postings(t)</tt>, in the format specified in the
   * assignment
   *
   * @param t The term
   * @return The string representation
   */
  public String postingsList(String t) {

    return "["
        + String.join(
            ",", index.get(t).stream().map(p -> p.toString()).collect(Collectors.toList()))
        + "]";
  }

  /**
   * Returns the weight of term t in document d
   *
   * @param t The term
   * @param d The document
   * @return The weight of t
   */
  public double weight(String t, String d) {
    throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * Returns <tt>TPScore(query, doc)</tt>.
   *
   * @param query The user's search query
   * @param doc THe document to test
   * @return The TPScore of the query with respect to the document
   */
  public double TPScore(String query, String doc) {
    throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * Returns <tt>VSScore(query, doc)</tt>.
   *
   * @param query The user's search query
   * @param doc The document to test
   * @return The VSScode of the query with respect to the document
   */
  public double VSScore(String query, String doc) {
    throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * Returns <tt>0.6�T SScore(query, doc)+0.4�V SScore(query, doc)</tt>.
   *
   * @param query The user's search query
   * @param doc The document to test
   * @return The relevance of the document with respect to the query
   */
  public double Relevance(String query, String doc) {
    throw new UnsupportedOperationException("Not implemented");
  }
}
