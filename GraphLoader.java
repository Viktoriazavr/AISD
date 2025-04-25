import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class GraphLoader {
    public static TopoSort loadFromFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        int V = scanner.nextInt();
        int E = scanner.nextInt();

        TopoSort graph = new TopoSort(V);
        for (int i = 0; i < E; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            graph.addEdge(u, v);
        }
        scanner.close();

        return graph;
    }
}