package tools;

import tools.exceptions.FormateMisMatchException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Merger {

    public static final String FILE_DB = "fileDB.csv";
    public static final ClassLoader classloader = Thread.currentThread().getContextClassLoader();

    //function name : mergeFile
    // parameters: the name of target file
    // check is the target file exist or not, if not, create a new one, merge the files in the resource files
    //return the merged file
    public static File mergeFile(String dbFile) throws FormateMisMatchException, IOException {

        String[] headers = null;
        File fileDB;
        // check whether the given address exist files or not
        // if exist this file, generate the absolute path for this file
        //otherwise catch the exception
        try {
            fileDB = new File(Objects.requireNonNull(Merger.class.getClassLoader().getResource(dbFile)).getFile());
            boolean mkDir = fileDB.getParentFile().mkdirs();
            if (!mkDir) {
                log.info("File {} exists", dbFile);
                System.out.println(dbFile + "existed");
            } else {
                log.info("created file: {}", dbFile);
                System.out.println("created file: " + dbFile);
            }
        } catch (NullPointerException e) {
            log.error("Error getting file DB file {}", dbFile, e);
            throw new RuntimeException(e);
        }

        //scan the headers in target file
        Scanner scanner = new Scanner(fileDB);

        if (scanner.hasNextLine()) {
            headers = scanner.nextLine().split(",");
        }
        scanner.close();

        //read all the csv files in the resource folder, and collect to the files list

        List<File> files  = Arrays.stream(Objects.requireNonNull(fileDB.getParentFile().listFiles()))
                .filter(x -> x.isFile() && x.getName().endsWith("csv") && !x.getName().equalsIgnoreCase(FILE_DB))
                .collect(Collectors.toList());
        // if no files need to be merge in resource, return

        if (files.size() == 0) {
            System.out.println("No files found to merge.");
            return fileDB;
        }

        //write into file
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileDB, true));

        if(headers == null || headers.length == 0) {
            try {
                scanner = new Scanner(new File(files.get(0).toURI()));
                String headersStr = scanner.nextLine();
                headers = headersStr.split(",");
                writer.write(headersStr);
                writer.newLine();
            } finally {
                scanner.close();
            }
        }

        for (File file : files) {
            if (!file.getName().endsWith("csv")) {
                continue;
            }
            if (file.getName().equalsIgnoreCase(FILE_DB)) {
                continue;
            }
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                log.error("Could not open file: " + file.getName(), e);
                throw new RuntimeException(e);
            }
            String line;
            String[] firstLine = null;
            if ((line = reader.readLine()) != null) {
                firstLine = line.split(",");
            }
            //if headers do  not match, we could not merge
            if (!Arrays.equals (headers, firstLine)) {
                log.error("Header mismatch for merging");
                throw new FormateMisMatchException("Header mis-match between CSV files: '" +
                        dbFile + "' and '" + file.getAbsolutePath());
            }
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("\"null\"")) continue;
                while (line.split(",").length < headers.length) {
                    line += reader.readLine();
                }
                writer.write(line);
                writer.newLine();
            }
            reader.close();
        }
        writer.close();
        return fileDB;
    }
}
