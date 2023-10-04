import Exceptions.FormateMisMatchException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

@Slf4j
public class MergeAndSearch {
    public static void main(String[] args) {
        String firstFile = "Indiegogo1.csv";
        String secondFile = "Indiegogo2.csv";
        String thirdFile = "Indiegogo3.csv";

        File file;
        try {
            file = Utils.mergeFile(Utils.FILE_DB, Arrays.asList(firstFile, secondFile, thirdFile));
        } catch (FormateMisMatchException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("Error", e);
            throw new RuntimeException(e);
        }
        System.out.println("Merged file, starting search...");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Pls input the keyword you want to search(Q/q to quit): ");
            String keyword = scanner.nextLine();

            if (keyword.equalsIgnoreCase("q")) {
                break;
            }
            Utils.search(file, keyword);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
