import java.util.Random;

public class Main2SEM {
    public static void main(String[] args) {
        Random rand = new Random();
        int[] data = rand.ints(10000, 0, 100000).toArray();
        TwoThreeTreeFull.TwoThreeTree tree = new TwoThreeTreeFull.TwoThreeTree();

        long totalInsertTime = 0, totalSearchTime = 0, totalDeleteTime = 0;
        long totalInsertOps = 0, totalSearchOps = 0, totalDeleteOps = 0;
        for (int val : data) {
            long start = System.nanoTime();
            tree.insert(val);
            long end = System.nanoTime();
            totalInsertTime += (end - start);
            totalInsertOps += tree.counter.transitions;
        }

        for (int i = 0; i < 100; i++) {
            int val = data[rand.nextInt(data.length)];
            long start = System.nanoTime();
            tree.search(val);
            long end = System.nanoTime();
            totalSearchTime += (end - start);
            totalSearchOps += tree.counter.transitions;
        }

        for (int i = 0; i < 1000; i++) {
            int val = data[rand.nextInt(data.length)];
            long start = System.nanoTime();
            tree.delete(val);
            long end = System.nanoTime();
            totalDeleteTime += (end - start);
            totalDeleteOps += tree.counter.transitions;
        }

        System.out.printf("Вставка: %.2f нс, %.2f операций%n",
                totalInsertTime / 10000.0, totalInsertOps / 10000.0);
        System.out.printf("Поиск: %.2f нс, %.2f операций%n",
                totalSearchTime / 100.0, totalSearchOps / 100.0);
        System.out.printf("Удаление: %.2f нс, %.2f операций%n",
                totalDeleteTime / 1000.0, totalDeleteOps / 1000.0);
    }
}

