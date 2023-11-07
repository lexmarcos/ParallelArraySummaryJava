import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ItemProcessor extends Thread {
    private final CyclicBarrier barrier;
    private List<Item> items = null;
    private final Result result;

    public ItemProcessor(String name, CyclicBarrier barrier, List<Item> items, Result result) {
        super(name);
        this.barrier = barrier;
        this.items = items;
        this.result = result;
    }

    private void addToResultsIfTotalLessThanFive(Item item, List<Integer> idsWithTotalLessThanFive) {
        if (item.getTotal() < 5) {
            idsWithTotalLessThanFive.add(item.getId());
        }
    }

    private void addToResultsIfTotalMoreThanFive(Item item, List<Integer> idsWithTotalMoreThanFive) {
        if(item.getTotal() > 5) {
            idsWithTotalMoreThanFive.add(item.getId());
        }
    }

    private void addToResultsTotalByGroup(Item item, List<Double> totalsByGroup) {
        result.addTotalByGroup(item.getTotal(), item.getGroup());
        Utils.addToArrayOfTotalsByGroup(totalsByGroup, item.getTotal(), item.getGroup());
    }

    private void addToResultsCombinedTotal(double subtotal) {
        result.addCombinedTotal(subtotal);
    }

    private void addToResultsIdsWithLessAndMoreThanFive(List<Integer> idsWithTotalLessThanFive, List<Integer> idsWithTotalMoreThanFive){
        result.addIdsWithTotalMoreThanFive(idsWithTotalMoreThanFive);
        result.addIdsWithTotalLessThanFive(idsWithTotalLessThanFive);
    }

    private void saveLogs(List<Integer> idsWithTotalLessThanFive, List<Integer> idsWithTotalMoreThanFive, List<Double> totalsByGroup){
        Displayer.logTotalOfItems(getName(), items);
        Displayer.logTotalByGroup(getName(), totalsByGroup);
        Displayer.logIdsWithTotalLessThanFive(getName(), idsWithTotalLessThanFive);
        Displayer.logIdsWithTotalMoreThanFive(getName(), idsWithTotalMoreThanFive);
    }

    @Override
    public void run() {
        try {
            String threadLogName = getName();
            double subtotal = 0;
            boolean isToSaveLogs = false;

            List<Integer> idsWithTotalLessThanFive = new ArrayList<>();
            List<Integer> idsWithTotalMoreThanFive = new ArrayList<>();

            List<Double> totalsByGroup = new ArrayList<>();

            for (Item item : items) {
                subtotal += item.getTotal();
                addToResultsIfTotalLessThanFive(item, idsWithTotalLessThanFive);
                addToResultsIfTotalMoreThanFive(item, idsWithTotalMoreThanFive);
                addToResultsTotalByGroup(item, totalsByGroup);
                if(isToSaveLogs){
                    Displayer.logIndividualItem(threadLogName, item);
                }
            }
            addToResultsIdsWithLessAndMoreThanFive(idsWithTotalLessThanFive, idsWithTotalMoreThanFive);
            addToResultsCombinedTotal(subtotal);

            if(isToSaveLogs){
                saveLogs(idsWithTotalLessThanFive,
                        idsWithTotalMoreThanFive, totalsByGroup);
            }

            barrier.await();

            System.out.println("Thread " + this.getName() + " released.");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
