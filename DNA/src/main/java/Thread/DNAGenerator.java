package Thread;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
@Getter
public class DNAGenerator {
    private static final Random RANDOM = new Random();

    private static final char[] GENOME = {'A', 'T', 'G', 'C'};

    private int totalGenomeCount = 100;
    private final int threadCount;
    private final int genomeSizePerThread;

    //use thread poll
    private ExecutorService executor;

    //store the results
    private List<String> genomeStrings;

    // using default totalGenomeCount: 100
    public DNAGenerator(int threadCount) {
        this.threadCount = threadCount;
        genomeSizePerThread = totalGenomeCount / threadCount;
        genomeStrings = new ArrayList<>();
        this.executor = Executors.newFixedThreadPool(threadCount);
    }

    // for customize totalGenomeCount
    public DNAGenerator(int threadCount, int totalGenomeCount) {
        this.threadCount = threadCount;
        this.totalGenomeCount = totalGenomeCount;
        this.genomeSizePerThread = totalGenomeCount / threadCount;
        genomeStrings = new ArrayList<>();
        this.executor = Executors.newFixedThreadPool(threadCount);
    }

    //according to the thread count to create the threads to run the generation
    //append the char in the stringBuilder and add to the list
    private void initializeThreadPoolAndRun() {
        IntStream.range(0, threadCount).forEach(item -> executor.execute(
                new Thread(() -> {
                    for (int x = 0; x < genomeSizePerThread; x++) {
                        StringBuilder sb = new StringBuilder();
                        for (int y = 0; y < 10; y++) {
                            sb.append(GENOME[RANDOM.nextInt(4)]);
                        }
                        genomeStrings.add(sb.toString());
                    }
                })
        ));
        executor.shutdown();
        try {
            if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    // if the size is not match the totalCount,continue run previous function

    public List<String> getGenomes() {
        if (genomeStrings.size() != totalGenomeCount) {
            initializeThreadPoolAndRun();
        }
        return this.genomeStrings;
    }
}
