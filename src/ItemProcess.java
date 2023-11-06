import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ItemProcess extends Thread{
    private CyclicBarrier barrier;
    private List<Item> items = new ArrayList<>();
    private double total = 0;
    private List<Double> totalsByGroup = new ArrayList<>();

    public ItemProcess(String name, CyclicBarrier barrier, List<Item> items ) {
        super(name);
        this.barrier = barrier;
        this.items = items;
    }



    @Override
    public void run() {
        try {
            for (Item item : items) {
                this.total += item.getTotal();
            }

            synchronized (Main.class) {
                Main.totalCombinado += this.total;
            }

            System.out.println("Thread " + getName() + " " + "Subtotal: " + Main.truncNumber(this.total, 4));
            barrier.await();

            System.out.println("Thread " + this.getName() + " released.");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
