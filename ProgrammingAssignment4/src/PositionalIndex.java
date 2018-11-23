import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class PositionalIndex {

  /** The inverted index */
  private Map<String, Map<String, List<Integer>>> index = new HashMap<>();

  /** The vector-space model */
  private Map<String, double[]> docVectors = new HashMap<>();

  /** The set of all terms in all documents */
  private List<String> allTerms = new ArrayList<>();

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
                        synchronized (this) {
                          if (!index.containsKey(term)) {
                            index.put(term, new HashMap<>());
                          }

                          index.get(term).put(file.getName(), terms.get(term));
                        }
                      });

              // put a placeholder in the doc vectors map for now so that
              // we can iterate over the list of files later
              docVectors.put(file.getName(), null);
            });

    // save the list of terms, sorted in the order we want
    allTerms = new ArrayList<>(index.keySet());
    allTerms.sort(String::compareTo);

    // compute the vector space model
    Logger.log("Computing the vector space model");

    docVectors
        .keySet()
        .forEach(
            doc -> {

              // build the map of terms that appear in the current document (and their positions)
              HashMap<String, List<Integer>> docTerms = new HashMap<>();
              index
                  .keySet()
                  .forEach(
                      term -> {
                        if (index.get(term).containsKey(doc)) {
                          docTerms.put(term, index.get(term).get(doc));
                        }
                      });

              docVectors.put(doc, VSMScore.getVector(index, allTerms, docTerms, docVectors.size()));
            });

    Date end = new Date();
    Logger.log(
        "Done preprocessing after " + ((end.getTime() - start.getTime()) / 1000) + " seconds");
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
    int termIndex = allTerms.indexOf(t);

    if (termIndex == -1) {
      return 0;
    }

    if (!docVectors.containsKey(d)) {
      throw new IllegalArgumentException("Document \"" + d + "\" does not exist in the index");
    }

    return docVectors.get(d)[termIndex];
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

    if (!docVectors.containsKey(doc)) {
      return 0;
    }

    HashMap<String, List<Integer>> queryTerms = TermExtractor.extract(query);
    double[] queryVector = VSMScore.getVector(index, allTerms, queryTerms, docVectors.size());
    double[] docVector = docVectors.get(doc);

    return VSMScore.cosSim(queryVector, docVector);
  }

  /**
   * Returns <tt>0.6 * TPScore(query, doc) + 0.4 * VSScore(query, doc)</tt>.
   *
   * @param query The user's search query
   * @param doc The document to test
   * @return The relevance of the document with respect to the query
   */
  public double Relevance(String query, String doc) {
    return 0.6 * TPScore(query, doc) + 0.4 * VSScore(query, doc);
  }
}
