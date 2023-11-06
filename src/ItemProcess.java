import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ItemProcess extends Thread{
    private CyclicBarrier barrier;
    private List<Item> items = new ArrayList<>();
    private double total = 0;
    private List<Double> totalsByGroup = new ArrayList<>();
    private Result result;

    public ItemProcess(String name, CyclicBarrier barrier, List<Item> items, Result result) {
        super(name);
        this.barrier = barrier;
        this.items = items;
        this.result = result;
    }

    private void addToResultsIdsWithTotalLessThanFIVE(Item item) {
        if(item.getTotal() < 5) {
            result.addIdWithTotalLessThanFIVE(item.getId());
        }
    }

    private void addToResultsTotalByGroup(Item item) {
        result.addTotalByGroup(item.getTotal(), item.getGrupo() - 1);
    }

    private void addToResultsTotalCombinado(double subtotal) {
        result.addTotalCombinado(subtotal);
    }

    @Override
    public void run() {
        try {
            String threadLogName = getName();
            double subtotal = 0;
            for (Item item : items) {
                subtotal += item.getTotal();
                addToResultsIdsWithTotalLessThanFIVE(item);
                addToResultsTotalByGroup(item);
                Displayer.logIndividualItem(threadLogName, item);
            }
            addToResultsTotalCombinado(subtotal);

            Displayer.logTotalOfItems(threadLogName, items);
            Displayer.logTotalByGroup(threadLogName, items);
            Displayer.logIdsWithTotalLessThanFive(threadLogName, items);

            barrier.await();

            System.out.println("Thread " + this.getName() + " released.");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
