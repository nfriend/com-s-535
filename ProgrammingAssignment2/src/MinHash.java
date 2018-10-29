import java.io.File;
import java.util.*;

public class MinHash {

  /** The internal list of all documents */
  private List<File> allFiles;

  /** The ready-to-use MinMash matrix */
  private MinHashMatrix minHashMatrix;

  /** The term document matrix */
  private TermDocumentMatrix tdMatrix;

  /** The number of terms in the term document matrix */
  private int numTerms;

  /** All of the names of the documents in the MinHash matrix */
  private String[] allDocs;

  /** The in[][] representation of the MinHash matrix */
  private int[][] minHashIntMatrix;

  /** The int[][] representation of the term document matrix */
  private int[][] tdIntMatrix;

  /** The number of permutations used to create this MinHash class */
  private int numPermutations;

  /**
   * Initializes a new MinHash object
   *
   * @param folder The name of a folder containing our document collection for which we wish to
   *     construct MinHash matrix
   * @param numPermutations The number of permutations to be used in creating the MinHash matrix
   */
  public MinHash(String folder, int numPermutations) {

    // get all of the files in the specified folder
    File docFolder = new File(folder);
    this.allFiles = Arrays.asList(docFolder.listFiles());

    tdMatrix = new TermDocumentMatrix();

    // loop over every document and load all of the terms
    // into our term document matrix
    TermExtractor extractor = new TermExtractor();
    this.allFiles
        .stream()
        .forEach(
            f -> {
              Logger.log("Extracting terms from file " + f.getName());
              this.tdMatrix.loadTerms(f.getName(), extractor.extractFromFile(f));
            });

    // construct a MinHash matrix from our term document matrix
    this.minHashMatrix = new MinHashMatrix(this.tdMatrix, numPermutations);

    // compute some properties needed for methods in this class
    this.numTerms = tdMatrix.getAllTerms().size();
    this.allDocs = tdMatrix.getAllDocuments().toArray(new String[0]);
    this.tdIntMatrix = tdMatrix.getMatrix();
    this.minHashIntMatrix = minHashMatrix.getMatrix();
    this.numPermutations = numPermutations;
  }

  /**
   * Returns an array of String consisting of all the names of files in the document collection
   *
   * @return An array file names
   */
  public String[] allDocs() {
    return allDocs;
  }

  /**
   * Returns the MinHash matrix of the collection
   *
   * @return The MinHash matrix
   */
  public int[][] minHashMatrix() {
    return minHashIntMatrix;
  }

  /**
   * Returns the term document matrix of the collection
   *
   * @return The term document matrix
   */
  public int[][] termDocumentMatrix() {
    return tdIntMatrix;
  }

  /**
   * Returns the size of union of all documents (Note that each document is a multiset of terms).
   *
   * @return The size of the union of all documents
   */
  public int numTerms() {
    return numTerms;
  }

  /**
   * Returns the number of permutations used to construct the MinHash matrix.
   *
   * @return The permutation count
   */
  public int numPermutations() {
    return numPermutations;
  }
}
