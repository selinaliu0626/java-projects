package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


import java.util.List;

// This class is for recording the memory for searching
@ToString
@Data
@AllArgsConstructor
@Slf4j
public class SearchEntity {
    // search keyword
    private String word;
    private long lastTimeSearched;
    private long firstTimeSearched;
    //search frequency
    private int searchFreq;
    private List<String> results;

    public void printSummary() {
        System.out.println("Keywords: " + word + ", lastTimeSearched: " + lastTimeSearched + ", Frequency: " + searchFreq + ", results size: " + results.size());
    }

    public void printResults() {
        for (String result : results) {
            String[] resultArray = result.split(",");
            System.out.println("fund_raised_percent: " + resultArray[7] + ", close_date: " + resultArray[4] + ", category: " + resultArray[1] + ", clickthrough_url: " + resultArray[3]);
        }
    }
}
