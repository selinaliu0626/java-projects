package tools;

import tools.exceptions.FormateMisMatchException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class MergerTest {
    private static final String TESTFILE1 = "test1.csv";

    @Test
    public void testMerge() throws IOException, FormateMisMatchException {
        Merger.mergeFile(TESTFILE1);
    }

    @Test
    public void testInvalidDBFilePathString() {
        Assertions.assertThrows(RuntimeException.class, ()-> Merger.mergeFile("invalidFile"));
    }
}
