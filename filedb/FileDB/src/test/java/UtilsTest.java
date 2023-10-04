import Exceptions.FormateMisMatchException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UtilsTest {
    private static final String TESTFILE1 = "test1.csv";
    private static final String TESTFILE2 = "test2.csv";
    private static final String TESTFILE3 = "test3.csv";
    private static List<String> remainingFiles = new ArrayList<>();

    @BeforeAll
    static void setup() {
        remainingFiles.add(TESTFILE2);
        remainingFiles.add(TESTFILE3);
    }
    @Test
    public void testMerge() throws FormateMisMatchException, IOException {
        Utils.mergeFile(TESTFILE1, remainingFiles);
    }
}
