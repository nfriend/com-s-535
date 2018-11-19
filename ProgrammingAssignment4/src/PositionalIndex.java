public class PositionalIndex {

  /**
   * Creates a new PositionalIndex instance
   *
   * @param rootFolder The folder containing the documents to index
   */
  public PositionalIndex(String rootFolder) {}

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
   * Returns the string representation of <tt>postings(t)</tt>.
   *
   * @param t The term
   */
  public void postingsList(String t) {
    throw new UnsupportedOperationException("Not implemented");
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
