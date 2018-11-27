import org.junit.jupiter.api.Test;

public class QueryProcessorTest {
  @Test
  void testQueryProcessor() {
    String rootFolder = System.getProperty("user.dir") + "/src/test-input";
    //    String rootFolder = "C:\\Users\\Nathan\\Downloads\\IR";
    QueryProcessor qp = new QueryProcessor(rootFolder);
    
    qp.topKDocs("quick fox jumps", 10);
  }
}
