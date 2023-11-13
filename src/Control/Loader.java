package Control;

import Model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Loader {
    public static double truncateNumber(double number, int decimalPlaces) {
        double multiplier = Math.pow(10, decimalPlaces);
        return Math.floor(multiplier * number) / multiplier;
    }

    public static List<Item> loadData(int N) {
        List<Item> itemList = new ArrayList<>();
        double expectedTotal = 0;
        Random rand = new Random();
        for(int i = 0; i < Math.pow(10, N); i++) {
            double total = truncateNumber(rand.nextDouble() * 10, 4);
            expectedTotal += total;

            int group = rand.nextInt(5) + 1;
            itemList.add(new Item(i, total, group));
        }
        System.out.println("🎯 Expected total: " + truncateNumber(expectedTotal, 4) + "\n");
        return itemList;
    }

    public static List<List<Item>> partitionList(int numberOfThreads, List<Item> itemList){
        int sizeOfList = itemList.size();
        int partSize = sizeOfList / numberOfThreads;
        int remainder = sizeOfList % numberOfThreads;
        List<List<Item>> partitions = new ArrayList<>();

        for (int i = 0; i < sizeOfList; i += partSize) {
            int end = Math.min(i + partSize, sizeOfList);
            if (remainder > 0) {
                end++;
                remainder--;
            }
            partitions.add(new ArrayList<>(itemList.subList(i, end)));
            if (remainder > 0) i++;
        }
        return partitions;
    }
}
