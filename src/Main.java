import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class Main {
    static List<Item> itemList = new ArrayList<>();

    public static double truncateNumber(double number, int decimalPlaces) {
        double multiplier = Math.pow(10, decimalPlaces);
        return Math.floor(multiplier * number) / multiplier;
    }

    public static void loadData(int N) {
        double expectedTotal = 0;
        Random rand = new Random();
        for(int i = 0; i < Math.pow(10, N); i++) {
            double total = truncateNumber(rand.nextDouble() * 10, 4);
            expectedTotal += total;

            int group = rand.nextInt(5) + 1;
            itemList.add(new Item(i, total, group));
        }
        System.out.println("Expected total: " + truncateNumber(expectedTotal, 4));
    }

    public static List<List<Item>> partitionList(int numberOfThreads){
        int sizeOfList = itemList.size();
        int partSize = sizeOfList / numberOfThreads;
        int remainder = sizeOfList % numberOfThreads;
        List<List<Item>> partitions = new ArrayList<>();

        for (int i = 0; i < sizeOfList; i += partSize) {
            int end = Math.min(i + partSize, sizeOfList);
            if (remainder > 0) {
                end++;
                remainder--;
            }
            partitions.add(new ArrayList<>(itemList.subList(i, end)));
            if (remainder > 0) i++;
        }
        return partitions;
    }

    public static void processItems(int numberOfThreads){
        System.out.println("Starting " + numberOfThreads + " threads.");
        Result result = new Result();
        long startTime = System.nanoTime();

        CyclicBarrier barrier = getCyclicBarrier(numberOfThreads, startTime, result);

        List<List<Item>> partitions = partitionList(numberOfThreads);
        for(int i = 0; i < numberOfThreads; i++){
            ItemProcessor threadItemProcessor = new ItemProcessor(
                    "Thread " + i, barrier, partitions.get(i), result);
            threadItemProcessor.start();
        }
    }

    private static CyclicBarrier getCyclicBarrier(int numberOfThreads, long startTime, Result result) {
        Runnable barrierAction = () -> {
            long endTime = System.nanoTime();
            long duration = endTime - startTime;

            double seconds = (double)duration / 1_000_000_000.0;
            System.out.println("Process time: " + seconds + " seconds.");

            result.displayGroupResults();
            System.out.println("Combined total from all threads: " +
                    Main.truncateNumber(result.getCombinedTotal(), 4));
        };

        return new CyclicBarrier(numberOfThreads, barrierAction);
    }

    public static void main(String[] args) {
        loadData(5);
        processItems(1);
    }
}
