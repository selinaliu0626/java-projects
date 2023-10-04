package tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;


class SearcherTest {

    private Searcher searcher;

    @BeforeEach
    void setUp() {
        File testFile = new File(Objects.requireNonNull(SearcherTest.class.getClassLoader().getResource("test1.csv")).getFile());
        searcher = new Searcher(testFile);
    }

    @Test
    void searchTest() {
        String nonResultString = "temp for string to search without any results";
        searcher.search(nonResultString);

        Assertions.assertEquals(1, searcher.getMemory().size());
        Assertions.assertTrue(searcher.getMemory().containsKey(nonResultString));
        Assertions.assertEquals(0, searcher.getMemory().get(nonResultString).getResults().size());
        Assertions.assertEquals(1, searcher.getMemory().get(nonResultString).getSearchFreq());

        searcher.search(nonResultString);

        Assertions.assertEquals(0, searcher.getMemory().get(nonResultString).getResults().size());
        Assertions.assertEquals(2, searcher.getMemory().get(nonResultString).getSearchFreq());
    }

    @Test
    void searchResultOneTest() {
        String nonResultString = "Wellness1";
        searcher.search(nonResultString);

        Assertions.assertEquals(1, searcher.getMemory().size());
        Assertions.assertTrue(searcher.getMemory().containsKey(nonResultString));
        Assertions.assertEquals(1, searcher.getMemory().get(nonResultString).getResults().size());
        Assertions.assertEquals(1, searcher.getMemory().get(nonResultString).getSearchFreq());

        searcher.search(nonResultString);

        Assertions.assertEquals(1, searcher.getMemory().get(nonResultString).getResults().size());
        Assertions.assertEquals(2, searcher.getMemory().get(nonResultString).getSearchFreq());
    }
}