import java.util.ArrayList;
import java.util.List;

public class Result {
    private double totalCombinado = 0;

    private List<Double> totalsByGroup = new ArrayList<>();

    public double getTotalCombinado() {
        return totalCombinado;
    }

    public void setTotalCombinado(double totalCombinado) {
        this.totalCombinado = totalCombinado;
    }

    public List<Double> getTotalsByGroup() {
        return totalsByGroup;
    }

    public void setTotalsByGroup(List<Double> totalsByGroup) {
        this.totalsByGroup = totalsByGroup;
    }
}
