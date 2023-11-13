import Control.Process;
import Model.Item;
import Control.Loader;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter N (power for inserts): ");
        int N = scanner.nextInt();

        System.out.print("Enter T (number of threads): ");
        int T = scanner.nextInt();

        List<Item> items = Loader.loadData(N);
        Process process = new Process(T, Loader.partitionList(T, items));
        process.processItems();
    }
}
