package labs;

import java.io.BufferedReader;
import java.io.FileReader;

public class Lab2 {
    private int V;
    private int[][] adj;
    private final String vertices = "abcdefghijknm";
    private int[][] getMass() throws Exception {
        try
        {
            int[][] matrix;
            BufferedReader reader = new BufferedReader(new FileReader("D:\\Навчання\\8 семестр\\ДМСАПР\\dm\\src\\files\\lab2.txt"));
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
    private void DFS(int u, boolean[] visited, int[] distance) {
        visited[u] = true;

        for (int v = 0; v < V; v++) {
            if (adj[u][v] != 0 && !visited[v]) {
                distance[0] += adj[u][v];
                DFS(v, visited, distance);
            }
        }
    }

    private int findShortestPath() {
        boolean[] visited = new boolean[V];
        int[] distance = new int[1];

        DFS(0, visited, distance);

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                return -1;
            }
        }

        return distance[0];
    }

    public void start() throws Exception {
        this.adj = getMass();
        this.V = adj.length;

        int shortestPath = findShortestPath();
        if (shortestPath != -1) {
            System.out.println("Shortest path for postman: " + shortestPath);
        } else {
            System.out.println("Not found");
        }
    }
}
