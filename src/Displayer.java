import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

    public static void logTotalByGroup(String threadName, List<Item> items) {
        Map<Integer, Double> totalByGroup = items.stream()
                .collect(Collectors.groupingBy(
                        Item::getGrupo,
                        Collectors.summingDouble(Item::getTotal)));

        totalByGroup.forEach((group, total) ->
                writeToLogFile(threadName + "_log.txt", "Grupo " + group + " Total: " + total));
    }

    public static void logIdsWithTotalLessThanFive(String threadName, List<Item> items) {
        List<Integer> ids = items.stream()
                .filter(item -> item.getTotal() < 5)
                .map(Item::getId)
                .collect(Collectors.toList());

        writeToLogFile(threadName + "_log.txt", "\nIDs com Total < 5: " + ids + "\n");
    }

    public static void logIndividualItem(String threadName, Item item) {
        String itemDetails =
                "----------------------\n" +
                        "Item ID: " + item.getId() + "\n" +
                        "Total: " + item.getTotal() + "\n" +
                        "Grupo: " + item.getGrupo();

        writeToLogFile(threadName + "_log.txt", itemDetails);
    }
}
