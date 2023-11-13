import java.util.ArrayList;
import java.util.List;

public class Result {
    private double combinedTotal = 0;
    private List<Double> totalsByGroup = new ArrayList<>();

    private int idsWithTotalLessThanFive = 0;

    private int idsWithTotalMoreThanFive = 0;

    public synchronized void addIdsWithTotalLessThanFive() {
        this.idsWithTotalLessThanFive++;
    }

    public synchronized void addIdsWithTotalMoreThanFive() {
        this.idsWithTotalMoreThanFive++;
    }

    public synchronized void addCombinedTotal(double total) {
        this.combinedTotal += total;
    }

    public synchronized double getCombinedTotal() {
        return combinedTotal;
    }

    public synchronized void addTotalByGroup(double total, int group) {
        Utils.addToArrayOfTotalsByGroup(totalsByGroup, total, group);
    }

    public void displayGroupResults() {
        for (int i = 0; i < totalsByGroup.size(); i++) {
            System.out.println("Group " + (i + 1) + ": " + Main.truncateNumber(totalsByGroup.get(i), 4));
        }
    }
}
