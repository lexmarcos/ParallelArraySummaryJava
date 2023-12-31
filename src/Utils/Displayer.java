package Utils;

import Control.Loader;
import Model.Item;

import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Displayer {

    private static synchronized void writeToLogFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logTotalOfItems(String threadName, List<Item> items) {
        double total = items.stream()
                .mapToDouble(Item::getTotal)
                .sum();
        writeToLogFile(threadName + "_log.txt", "\nTotal dos Itens: " + total);
    }

    public static void logTotalByGroup(String threadName, List<Double> groupTotals) {
        for (int i = 0; i < groupTotals.size(); i++) {
            System.out.println("Group size " + groupTotals.size());
            writeToLogFile(threadName + "_log.txt", "Grupo " + (i + 1) + ": " + groupTotals.get(i));
        }
    }

    public static void displayGroupResults(List<Double> totalsByGroup) {
        for (int i = 0; i < totalsByGroup.size(); i++) {
            System.out.println("📊 Group " + (i + 1) + ": " + Loader.truncateNumber(totalsByGroup.get(i), 4));
        }
    }

    public static void logIdsWithTotalLessThanFive(String threadName, int idsWithTotalLessThanFive) {
        writeToLogFile(threadName + "_log.txt", "\nIds With Total Less Than Five: " + idsWithTotalLessThanFive + "\n");
    }

    public static void logIdsWithTotalMoreThanFive(String threadName, int idsWithTotalMoreThanFive) {
        writeToLogFile(threadName + "_log.txt", "\nIds With Total More Than Five: " + idsWithTotalMoreThanFive + "\n");
    }

    public static void logIndividualItem(String threadName, Item item) {
        String itemDetails =
                "----------------------\n" +
                "Item ID: " + item.getId() + "\n" +
                "Total: " + item.getTotal() + "\n" +
                "Grupo: " + item.getGroup();

        writeToLogFile(threadName + "_log.txt", itemDetails);
    }
}
