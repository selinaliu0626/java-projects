import Exceptions.FormateMisMatchException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Utils {

    public static final String FILE_DB = "fileDB.csv";
    private static final ClassLoader classloader = Thread.currentThread().getContextClassLoader();

    //function name : mergeFile
    // parameters: the files address and the list of target files which need to be merge
    //merge the file in the list, and  a new single file with all contents in original files
    public static File mergeFile(String dbFile, List<String> remainingFiles) throws FormateMisMatchException, IOException {

        String[] headers = null;
        File fileDB;
        // check whether the given address exist files or not
        // if exist this file, generate the absolute path for this file
        //otherwise catch the exception
        try {
            fileDB = new File(classloader.getResource(dbFile).toURI());
        } catch (URISyntaxException e) {
            log.error("Error getting file DB file {}", dbFile, e);
            throw new RuntimeException(e);
        }

        //scan the headers in target file
        Scanner scanner = new Scanner(fileDB);

        if (scanner.hasNextLine()) {
            headers = scanner.nextLine().split(",");
        }
        scanner.close();

        //check the files in the given address
        List<File> files = remainingFiles.stream().map(x -> {
            URL resource = Utils.class.getClassLoader().getResource(x);
            if (resource == null) {
                log.error("Could not find file: " + x);
                throw new IllegalArgumentException("file:" + x + " not found!");
            } else {
                try {
                    return new File(resource.toURI());
                } catch (URISyntaxException e) {
                    log.error("Could not parse file: " + x, e);
                    throw new RuntimeException(e);
                }
            }
        }).collect(Collectors.toList());

        //write into file
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileDB, true));

        if(headers == null || headers.length == 0) {
            try {
                scanner = new Scanner(new File(classloader.getResource(remainingFiles.get(0)).toURI()));
                String headersStr = scanner.nextLine();
                headers = headersStr.split(",");
                writer.write(headersStr);
                writer.newLine();
            } catch (URISyntaxException e) {
                log.error("Error getting first file {}", remainingFiles.get(0), e);
                throw new RuntimeException(e);
            } finally {
                scanner.close();
            }
        }

        Iterator<File> iterFiles = files.iterator();

        while (iterFiles.hasNext()) {
            File nextFile = iterFiles.next();
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(nextFile));
            } catch (FileNotFoundException e) {
                log.error("Could not open file: " + nextFile.getName(), e);
                throw new RuntimeException(e);
            }
            String line = null;
            String[] firstLine = null;
            if ((line = reader.readLine()) != null) {
                firstLine = line.split(",");
            }
            //if headers do  not match, we could not merge
            if (!Arrays.equals (headers, firstLine)) {
                log.error("Header mismatch for merging");
                throw new FormateMisMatchException("Header mis-match between CSV files: '" +
                        dbFile + "' and '" + nextFile.getAbsolutePath());
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

    // function name : search
    //parameter: the file name, and the input keyword for searching
    //return: void, print the related contents in given format
    public static void search(File file, String keyWord) {
        List<String> results = new ArrayList<>();
        BufferedReader reader;
        try {
             reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            log.error("Could not open file: " + file.getName(), e);
            throw new RuntimeException(e);
        }

        //read and search for the keyword
        // if exists, add into the results
        String line;
        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                //check ignore case
                if(Pattern.compile(Pattern.quote(keyWord), Pattern.CASE_INSENSITIVE).matcher(line).find()) {
                    results.add(line);
                }
            }
            reader.close();
        } catch (IOException e) {
            log.error("Could not read file: {}", file.getName(), e);
            throw new RuntimeException(e);
        }
        for (String result : results) {
            log.info("Find line: {} for keyword: {}", result, keyWord);
            String[] resultArray = result.split(",");
            System.out.println("fund_raised_percent: " + resultArray[7] + ", close_date: " + resultArray[4] + ", category: "+ resultArray[1] + ", clickthrough_url: " + resultArray[3]);
        }
    }
}
