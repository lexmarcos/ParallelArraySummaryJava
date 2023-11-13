package Utils;

import java.util.List;

public class UtilsFunctions {
    public static void addToArrayOfTotalsByGroup(List<Double> totalsByGroup, double total, int group){
        int index = group - 1;
        while(totalsByGroup.size() <= index) {
            totalsByGroup.add(0.0);
        }
        totalsByGroup.set(index, totalsByGroup.get(index) + total);
    }

}
