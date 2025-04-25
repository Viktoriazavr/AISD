import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    private static final int WARMUP_RUNS = 5; //Количество "разогревочных" прогонов
    private static final int MEASUREMENT_RUNS = 10; //Количество измерительных прогонов для каждого теста
    private static final Random RANDOM = new Random(42); //Генератор псевдослучайных чисел с фиксированным seed (42)

    public static void main(String[] args) {
        try {
            cleanTestData();
            generateTestData(100, 100, 10000); // 100 тестов от 100 до 10000 вершин
            testAlgorithmPerformance();
        } catch (IOException | InterruptedException e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void cleanTestData() throws IOException {
        Path dir = Paths.get("test_data");
        if (Files.exists(dir)) {
            Files.walk(dir)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
        Files.createDirectories(dir);
    }

    private static void generateTestData(int numTests, int minSize, int maxSize) throws IOException {
        System.out.println("Генерация тестовых данных...");

        for (int i = 0; i < numTests; i++) {
            int size = minSize + (maxSize - minSize) * i / numTests;
            String fileName = "test_data/graph_" + size + "_" + (i+1) + ".txt";

            try (PrintWriter writer = new PrintWriter(fileName)) {
                int layers = 3 + RANDOM.nextInt(5);
                List<List<Integer>> topology = new ArrayList<>();

                for (int j = 0; j < size; j++) {
                    int layer = j % layers;
                    if (layer >= topology.size()) {
                        topology.add(new ArrayList<>());
                    }
                    topology.get(layer).add(j);
                }

                List<String> edges = new ArrayList<>();
                for (int fromLayer = 0; fromLayer < topology.size()-1; fromLayer++) {
                    for (int fromNode : topology.get(fromLayer)) {
                        int toLayer = fromLayer + 1;
                        for (int toNode : topology.get(toLayer)) {
                            if (RANDOM.nextDouble() < 0.4) {
                                edges.add(fromNode + " " + toNode);
                            }
                        }
                    }
                }

                writer.println(size);
                writer.println(edges.size());
                edges.forEach(writer::println);
            }
        }
        System.out.println("Сгенерировано " + numTests + " тестовых файлов");
    }

    private static void testAlgorithmPerformance() throws IOException, InterruptedException {
        File[] files = getSortedTestFiles();
        if (files == null || files.length == 0) {
            System.out.println("Нет файлов для тестирования");
            return;
        }

        try (PrintWriter resultWriter = new PrintWriter("test_data/results.csv")) {
            resultWriter.println("Вершины,Ребра,Время(мс),Операции");

            for (File file : files) {
                TopoSort graph = GraphLoader.loadFromFile(file.getPath());

                for (int i = 0; i < WARMUP_RUNS; i++) {
                    graph.topologicalSort();
                }

                // Чистка памяти перед замерами
                System.gc();
                Thread.sleep(100);

                // Измерения
                long totalTime = 0;
                int totalIterations = 0;

                for (int i = 0; i < MEASUREMENT_RUNS; i++) {
                    TopoSort.SortResult result = graph.topologicalSort();
                    totalTime += result.timeNanos;
                    totalIterations += result.iterations;
                }

                resultWriter.printf("%d,%d,%d,%d\n",
                        graph.topologicalSort().vertices,
                        graph.topologicalSort().edges,
                        totalTime / MEASUREMENT_RUNS,
                        totalIterations / MEASUREMENT_RUNS);
            }
        }
        System.out.println("Тестирование завершено. Результаты сохранены в test_data/results.csv");
    }

    private static File[] getSortedTestFiles() {
        File dir = new File("test_data");
        File[] files = dir.listFiles((d, name) -> name.startsWith("graph_"));

        if (files == null) return null;

        Arrays.sort(files, Comparator.comparingInt(f ->
                Integer.parseInt(f.getName().split("_")[1])));

        return files;
    }
}
