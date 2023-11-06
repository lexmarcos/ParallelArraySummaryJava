import java.util.ArrayList;
import java.util.List;

public class Result {
    private double totalCombinado = 0;
    private List<Double> totalsByGroup = new ArrayList<>();

    private List<Integer> idsWithTotalLessThanFIVE = new ArrayList<>();

    public synchronized void addIdWithTotalLessThanFIVE(int id) {
        idsWithTotalLessThanFIVE.add(id);
    }

    public synchronized List<Integer> getIdsWithTotalLessThanFIVE() {
        return new ArrayList<>(idsWithTotalLessThanFIVE);
    }

    public synchronized void addTotalCombinado(double total) {
        this.totalCombinado += total;
    }

    public synchronized double getTotalCombinado() {
        return totalCombinado;
    }

    public synchronized void addTotalByGroup(double total, int group) {
        while(totalsByGroup.size() <= group) {
            totalsByGroup.add(0.0);
        }
        totalsByGroup.set(group, totalsByGroup.get(group) + total);
    }

    public synchronized List<Double> getTotalsByGroup() {
        return new ArrayList<>(totalsByGroup);
    }

    public void displayResultOfGrups() {
        for (int i = 0; i < totalsByGroup.size(); i++) {
            System.out.println("Grupo " + (i + 1) + ": " + Main.truncNumber(totalsByGroup.get(i), 4));
        }
    }
}
