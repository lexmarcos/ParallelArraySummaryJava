package Control;

import Model.Item;
import Model.Result;
import Utils.Displayer;

import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Process {
    int numberOfThreads = 0;
    List<List<Item>> partitions = null;

    public Process(int numberOfThreads, List<List<Item>> partitions){
        this.numberOfThreads = numberOfThreads;
        this.partitions = partitions;
    }

    public void processItems(){
        System.out.println("🚀 Starting " + numberOfThreads + " threads.");
        Result result = new Result();
        long startTime = System.currentTimeMillis();

        CyclicBarrier barrier = this.getCyclicBarrier(startTime, result);

        for(int i = 0; i < this.numberOfThreads; i++){
            ItemProcessor threadItemProcessor = new ItemProcessor(
                    "Thread " + i, barrier, this.partitions.get(i), result);
            threadItemProcessor.start();
        }
    }

    private CyclicBarrier getCyclicBarrier(long startTime, Result result) {
        Runnable barrierAction = () -> {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            System.out.println("⏱ Process time: " + duration + " ms.\n");
            Displayer.displayGroupResults(result.getTotalsByGroup());
            System.out.println("\n🔢 Combined total from all threads: " +
                    Loader.truncateNumber(result.getCombinedTotal(), 4));
        };

        return new CyclicBarrier(this.numberOfThreads, barrierAction);
    }
}
