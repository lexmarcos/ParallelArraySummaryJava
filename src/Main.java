import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class Main extends Thread {
    static List<Item> items = new ArrayList<>();

    static double totalCombinado = 0;

    public static double truncNumber(double number, int decimalPlaces) {
        double multiplier = Math.pow(10, decimalPlaces);
        return Math.floor(multiplier * number) / multiplier;
    }

    public static void carregamento(int N) {
        double totalEsperado = 0;
        Random rand = new Random();
        for(int i=0; i<Math.pow(10, N); i++) {

            double total = truncNumber(rand.nextDouble() * 10, 4);
            totalEsperado += total;

            int grupo = rand.nextInt(5) + 1;
            items.add(new Item(i, total, grupo));
        }
        System.out.println("Total esperado: " + truncNumber(totalEsperado, 4));
    }

    public static void exibirTotais(List<Item> items) {
        for (Item item : items) {
            System.out.println(item.getTotal());
        }
    }


    public static List<List<Item>> sliceList(int T){
        int sizeOfList = items.size();
        int partSize = sizeOfList/ T;
        int remainder = sizeOfList % T;
        List<List<Item>> partitions = new ArrayList<>();

        for (int i = 0; i < sizeOfList; i += partSize) {
            int end = Math.min(i + partSize, sizeOfList);
            if (remainder > 0) {
                end++;
                remainder--;
                partitions.add(new ArrayList<>(items.subList(i, end)));
                i++;
            }else{
                partitions.add(new ArrayList<>(items.subList(i, end)));
            }


        }
        return partitions;
    }

    public static void processamento(int T){
        Runnable barrierAction = new Runnable() {
            public void run() {
                System.out.println("Total combinado de todas as threads: " + truncNumber(totalCombinado, 4));
            }
        };
        CyclicBarrier barrier = new CyclicBarrier(T, barrierAction);
        List<List<Item>> partitions = sliceList(T);
        for(int i = 0; i < T; i++){
            ItemProcess threadItemProcess = new ItemProcess("Thread " + i, barrier, partitions.get(i));
            threadItemProcess.start();
        }
    }

    public static void main(String[] args) {
        carregamento(7);
        processamento(2);
    }
}