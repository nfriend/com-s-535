import java.util.*;
import org.junit.jupiter.api.Test;

public class QueryProcessorTest {
  @Test
  void testQueryProcessor() {
    String rootFolder = System.getProperty("user.dir") + "/src/test-input";
    //    String rootFolder = "C:\\Users\\Nathan\\Downloads\\IR";
    QueryProcessor qp = new QueryProcessor(rootFolder);

    String[] queries = new String[] {"quick brown fox", "blue"};
    int k = 10;

    for (String query : queries) {
      System.out.println("Top " + k + " documents for query \"" + query + "\":\n");
      System.out.println(DocumentAndScores.getTableHeader());
      List<DocumentAndScores> topKDS = qp.topKDocsForReport(query, k);
      for (DocumentAndScores ds : topKDS) {
        System.out.println(ds.toString());
      }
      System.out.println("\n");
    }
  }
}
