import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParallelArraySummary {

    private List<Item> items = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }

    public void carregamento(int N) {
        Random rand = new Random();
        for(int i=0; i<10*N; i++) {
            double total = rand.nextDouble() * 10;
            int grupo = rand.nextInt(5) + 1;
            items.add(new Item(i, total, grupo));
        }
    }
}
