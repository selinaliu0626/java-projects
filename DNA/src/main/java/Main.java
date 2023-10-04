import java.util.List;
import java.util.Scanner;
import Thread.DNAGenerator;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            DNAGenerator generator;
            // choose how many threads you want to use
            System.out.println("How many threads need to run (q/Q to exit)?");
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("q")) {
                break;
            }
            int numThreads = Integer.parseInt(command);
            // choose how many strings you want to generate
            System.out.println("How many strings do you need to generate (Y/y use default count)?");
            String totalCountNeed = scanner.nextLine();
            if(totalCountNeed.equalsIgnoreCase("y")){
                generator = new DNAGenerator(numThreads);
            }else{
                int total = Integer.parseInt(totalCountNeed);
                generator = new DNAGenerator(numThreads,total);
            }

            long start = System.currentTimeMillis();

            List<String> DNAs = generator.getGenomes();

            System.out.println("Finished, DNAs list size: " + DNAs.size() + ", execute time: " + (System.currentTimeMillis() - start));
        }
        scanner.close();
    }
}
