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

    private int addToResultsIfTotalLessThanFive(Item item, int idsWithTotalLessThanFive) {
        if (item.getTotal() < 5) {
            idsWithTotalLessThanFive++;
            return idsWithTotalLessThanFive;
        }
        return idsWithTotalLessThanFive;
    }

    private int addToResultsIfTotalMoreThanFive(Item item, int idsWithTotalMoreThanFive) {
        if(item.getTotal() > 5) {
            idsWithTotalMoreThanFive++;
            return idsWithTotalMoreThanFive;
        }
        return idsWithTotalMoreThanFive;
    }

    private void addToResultsTotalByGroup(Item item, List<Double> totalsByGroup) {
        result.addTotalByGroup(item.getTotal(), item.getGroup());
        Utils.addToArrayOfTotalsByGroup(totalsByGroup, item.getTotal(), item.getGroup());
    }

    private void addToResultsCombinedTotal(double subtotal) {
        result.addCombinedTotal(subtotal);
    }

    private void addToResultsIdsWithLessAndMoreThanFive(){
        result.addIdsWithTotalMoreThanFive();
        result.addIdsWithTotalLessThanFive();
    }

    private void saveLogs(int idsWithTotalLessThanFive, int idsWithTotalMoreThanFive, List<Double> totalsByGroup){
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

            int idsWithTotalLessThanFive = 0;
            int idsWithTotalMoreThanFive = 0;

            List<Double> totalsByGroup = new ArrayList<>();

            for (Item item : items) {
                subtotal += item.getTotal();
                idsWithTotalLessThanFive = addToResultsIfTotalLessThanFive(item, idsWithTotalLessThanFive);
                idsWithTotalMoreThanFive = addToResultsIfTotalMoreThanFive(item, idsWithTotalMoreThanFive);
                addToResultsTotalByGroup(item, totalsByGroup);

                if(isToSaveLogs){
                    Displayer.logIndividualItem(threadLogName, item);
                }
            }
            addToResultsIdsWithLessAndMoreThanFive();
            addToResultsCombinedTotal(subtotal);

            if(isToSaveLogs){
                saveLogs(idsWithTotalLessThanFive,
                        idsWithTotalMoreThanFive, totalsByGroup);
            }

            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
