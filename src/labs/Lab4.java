package labs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;

public class Lab4 {
    private int[][] graph;
    private int[] parent;

    private int[][] getMass() throws Exception {
        try
        {
            int[][] matrix;
            BufferedReader reader = new BufferedReader(new FileReader("D:\\Навчання\\8 семестр\\ДМСАПР\\dm\\src\\files\\lab4.txt"));
            String dimension = reader.readLine();
            char character = dimension.charAt(1);
            dimension = Character.toString(character);
            int numRows = Integer.parseInt(dimension);
            matrix = new int[numRows][numRows];

            for (int i = 0; i < numRows; i++) {
                String line = reader.readLine();
                String[] values = line.split(" ");
                for (int j = 0; j < numRows; j++) {
                    matrix[i][j] = Integer.parseInt(values[j]);
                }
            }
            reader.close();

            return matrix;
        }
        catch (Exception e)
        {
            throw new Exception(e.toString());
        }
    }

    private boolean BreadthFirstSearch(int source, int sink) {
        boolean[] visited = new boolean[graph.length];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int i = 0; i < graph.length; i++) {
                if (!visited[i] && graph[current][i] > 0) {
                    queue.add(i);
                    parent[i] = current;
                    visited[i] = true;
                }
            }
        }

        return visited[sink];
    }
    private int MaxFlow(int source, int sink) {
        parent = new int[graph.length];
        int maxFlow = 0;

        while (BreadthFirstSearch(source, sink)) {
            int pathFlow = Integer.MAX_VALUE;

            for (int i = sink; i != source; i = parent[i]) {
                int j = parent[i];
                pathFlow = Math.min(pathFlow, graph[j][i]);
            }

            for (int i = sink; i != source; i = parent[i]) {
                int j = parent[i];
                graph[j][i] -= pathFlow;
                graph[i][j] += pathFlow;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    public void start() throws Exception {
        graph = getMass();

        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }

        int maxFlow = MaxFlow(0, graph.length - 1);
        System.out.println("Maximum flow is: " + maxFlow);
    }
}
