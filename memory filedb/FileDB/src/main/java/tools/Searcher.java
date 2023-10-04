package tools;

import model.SearchEntity;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Data
public class Searcher {
    private File dbFile;

    private Map<String, SearchEntity> memory;

    private BufferedReader reader;

    public Searcher(File dbFile) {
        log.info("initializing tools.Searcher");
        this.dbFile = dbFile;
        boolean mkDir = dbFile.getParentFile().mkdirs();
        if (!mkDir) {
            System.out.println(dbFile + "existed");
        }
        memory = new HashMap<>();
        try {
            reader = new BufferedReader(new FileReader(dbFile));
        } catch (FileNotFoundException e) {
            log.error("Could not open file: " + dbFile.getName(), e);
            throw new RuntimeException(e);
        }
        log.info("tools.Searcher created.");
    }


     // reset the reader.
    private void resetReader() {
        try {
            reader= new BufferedReader(new FileReader(dbFile));
        } catch (FileNotFoundException e) {
            log.error("Could not open file: " + dbFile.getName(), e);
            throw new RuntimeException(e);
        }
    }


    // function name :search
    //parameters: keyWord input keyword for searching
    //print the contents related to this keyword
    public void search(String keyWord) {
        SearchEntity searchResult;
        //check from memory
        // if already exist this keyword, then we only need to update the last search time and frequency
        if (memory.containsKey(keyWord)) {
            searchResult = memory.get(keyWord);
            searchResult.setLastTimeSearched(System.currentTimeMillis());
            searchResult.setSearchFreq(searchResult.getSearchFreq() + 1);
        } else {
            //otherwise, not exist in the memory
            // this means we need to create a new SearchEntity instance to store our result
            resetReader();
            List<String> results = new ArrayList<>();
            //read and search for the keyword
            // if exists, add into the results
            String line;
            try {
                reader.readLine();
                while ((line = reader.readLine()) != null) {
                    //check ignore case
                    if (Pattern.compile(Pattern.quote(keyWord), Pattern.CASE_INSENSITIVE).matcher(line).find()) {
                        results.add(line);
                    }
                }
                reader.close();
            } catch (IOException e) {
                log.error("Could not read file: {}", dbFile.getName(), e);
                throw new RuntimeException(e);
            }
            results = results.stream().distinct().collect(Collectors.toList());
            log.info("Found {} results for keyword {}", results.size(), keyWord);
            searchResult = new SearchEntity(keyWord, System.currentTimeMillis(), System.currentTimeMillis(), 1, results);
            memory.put(keyWord, searchResult);
        }
        System.out.println("Summary:");
        searchResult.printSummary();
        System.out.println("Results:");
        searchResult.printResults();
    }
}
