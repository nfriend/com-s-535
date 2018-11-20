import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class PositionalIndex {

  /** The inverted index */
  private Map<String, Map<String, List<Integer>>> index = new HashMap<>();

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
                            index.put(term, new HashMap<>());
                          }

                          index.get(term).put(file.getName(), terms.get(term));
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
    if (!index.containsKey(term)) {
      return 0;
    }

    Map<String, List<Integer>> postings = index.get(term);
    if (!postings.containsKey(doc)) {
      return 0;
    } else {
      return postings.get(doc).size();
    }
  }

  /**
   * Returns the number of documents in which <tt>term</tt> appears.
   *
   * @param term The term to test
   * @return The number of documents
   */
  public int docFrequency(String term) {
    if (!index.containsKey(term)) {
      return 0;
    }

    return index.get(term).size();
  }

  /**
   * Returns the string representation of <tt>postings(t)</tt>, in the format specified in the
   * assignment
   *
   * @param t The term
   * @return The string representation
   */
  public String postingsList(String t) {

    Map<String, List<Integer>> docs = index.get(t);

    List<String> postings = new ArrayList<>();
    for (String doc : docs.keySet()) {
      postings.add(
          String.format(
              "<%s:%s>",
              doc,
              String.join(
                  ",",
                  docs.get(doc).stream().map(i -> i.toString()).collect(Collectors.toList()))));
    }

    return "[" + String.join(",", postings) + "]";
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
   * @param doc The document to test
   * @return The TPScore of the query with respect to the document
   */
  public double TPScore(String query, String doc) {
    HashMap<String, List<Integer>> queryTerms = TermExtractor.extract(query);

    List<List<Integer>> positions = new ArrayList<>();
    queryTerms
        .keySet()
        .forEach(
            term -> {
              if (!index.containsKey(term)) {
                positions.add(new ArrayList<>());
              } else {
                Map<String, List<Integer>> postings = this.index.get(term);
                if (!postings.containsKey(doc)) {
                  positions.add(new ArrayList<>());
                } else {
                  positions.add(postings.get(doc));
                }
              }
            });

    return TPScore.compute(positions);
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
   * Returns <tt>0.6×T SScore(query, doc)+0.4×V SScore(query, doc)</tt>.
   *
   * @param query The user's search query
   * @param doc The document to test
   * @return The relevance of the document with respect to the query
   */
  public double Relevance(String query, String doc) {
    throw new UnsupportedOperationException("Not implemented");
  }
}
