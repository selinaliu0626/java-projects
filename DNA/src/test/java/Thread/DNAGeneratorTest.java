package Thread;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.*;

class DNAGeneratorTest {

    private DNAGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new DNAGenerator(20);
    }

    @Test
    void getGenomes() {
        List<String> genomes = generator.getGenomes();
        Assertions.assertEquals(100, genomes.size());
    }

    @Test
    void getTotalGenomeCount() {
        int count = generator.getTotalGenomeCount();
        Assertions.assertEquals(100, count);
    }

    @Test
    void getThreadCount() {
        int threadCount = generator.getThreadCount();
        Assertions.assertEquals(20, threadCount);
    }

    @Test
    void getGenomeSizePerThread() {
        int genomeSizePerThread = generator.getGenomeSizePerThread();
        Assertions.assertEquals(5, genomeSizePerThread);
    }

    @Test
    void getExecutor() {
        ExecutorService executor = generator.getExecutor();
        Assertions.assertNotNull(executor);
    }

    @Test
    void getGenomesWithCustomizeGenomeCountTest() {
        generator = new DNAGenerator(20, 200);
        List<String> genomes = generator.getGenomes();
        Assertions.assertEquals(200, genomes.size());
    }
}