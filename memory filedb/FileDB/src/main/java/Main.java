import tools.exceptions.FormateMisMatchException;
import tools.Merger;
import tools.ParametersNotNullValidate;
import tools.Searcher;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class Main {

    public static void main(String[] args) {
        // Merged files
        File file;
        // tools.Searcher for files
        Searcher searcher;
        Scanner scanner = new Scanner(System.in);
        // create the user interface
        //ask user do they need to merge files or not
        //users could input any case of true/false
        System.out.println("Merge files?[TRUE/FALSE]: ");
        boolean isMerge = Boolean.parseBoolean(scanner.nextLine());
        //if user need to merge files
        if (isMerge) {
            try {
                file = Merger.mergeFile(Merger.FILE_DB);
                System.out.println("Merged files");
                searcher = new Searcher(file);
            } catch (FormateMisMatchException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                log.error("Error", e);
                throw new RuntimeException(e);
            }
        } else {// if user do not need to merge files, go to search
            try {
                searcher = new Searcher(new File(Objects.requireNonNull(Merger.classloader.getResource(Merger.FILE_DB)).toURI()));
            } catch (URISyntaxException e) {
                log.error("tools.Searcher initialized failed", e);
                System.out.println("tools.Searcher initialized failed");
                throw new RuntimeException(e);
            } catch (NullPointerException e) {
                log.error("Resource not find {}", Merger.FILE_DB, e);
                throw new RuntimeException(e);
            }
        }
        // validate the searcher object not null.
        ParametersNotNullValidate.validate(searcher, "Searcher can not be null");

        System.out.println("Starting search...");
        while (true) {
            System.out.println("Pls input the keyword you want to search(Q/q to quit): ");
            String keyword = scanner.nextLine();

            if (keyword.equalsIgnoreCase("q")) {
                break;
            }
            searcher.search(keyword);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
