import java.util.*;

class TopoSort {
    private int V;
    private LinkedList<Integer>[] adj;

    public TopoSort(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList<>();
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    public SortResult topologicalSort() {
        for (int i = 0; i < 3; i++) {
            performSort();
        }
        return performSort();
    }

    private SortResult performSort() {
        long startTime = System.nanoTime();
        int iterations = 0;

        int[] inDegree = new int[V];
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> topOrder = new ArrayList<>();

        // Шаг 1: Вычисление степеней захода
        for (int i = 0; i < V; i++) {
            for (int node : adj[i]) {
                inDegree[node]++;
                iterations++;
            }
            iterations++;
        }

        // Шаг 2: Находим вершины с нулевой степенью
        for (int i = 0; i < V; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
            iterations++;
        }

        int cnt = 0;

        // Шаг 3: Обработка вершин
        while (!queue.isEmpty()) {
            int u = queue.poll();
            topOrder.add(u);

            for (int node : adj[u]) {
                if (--inDegree[node] == 0) {
                    queue.add(node);
                }
                iterations++;
            }
            cnt++;
            iterations++;
        }

        long endTime = System.nanoTime();

        return new SortResult(
                endTime - startTime,
                iterations,
                V,
                countEdges()
        );
    }

    private int countEdges() {
        int count = 0;
        for (LinkedList<Integer> list : adj) {
            count += list.size();
        }
        return count;
    }

    public static class SortResult {
        public final long timeNanos;
        public final int iterations;
        public final int vertices;
        public final int edges;

        public SortResult(long timeNanos, int iterations, int vertices, int edges) {
            this.timeNanos = timeNanos;
            this.iterations = iterations;
            this.vertices = vertices;
            this.edges = edges;
        }
    }
}