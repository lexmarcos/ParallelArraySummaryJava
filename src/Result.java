import java.util.ArrayList;
import java.util.List;

public class Result {
    private double combinedTotal = 0;
    private List<Double> totalsByGroup = new ArrayList<>();

    private List<Integer> idsWithTotalLessThanFive = new ArrayList<>();

    private List<Integer> idsWithTotalMoreThanFive = new ArrayList<>();

    public synchronized void addIdsWithTotalLessThanFive(List<Integer> ids) {
        idsWithTotalLessThanFive.addAll(ids);
    }

    public synchronized void addIdsWithTotalMoreThanFive(List<Integer> ids) {
        idsWithTotalMoreThanFive.addAll(ids);
    }

    public synchronized List<Integer> getIdsWithTotalLessThanFive() {
        return new ArrayList<>(idsWithTotalLessThanFive);
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

    public synchronized List<Double> getTotalsByGroup() {
        return new ArrayList<>(totalsByGroup);
    }

    public void displayGroupResults() {
        for (int i = 0; i < totalsByGroup.size(); i++) {
            System.out.println("Group " + (i + 1) + ": " + Main.truncateNumber(totalsByGroup.get(i), 4));
        }
    }
}
