package labs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lab3 {
    private int[][] distances;
    private int[] bestPath;
    private int bestLength;

    private int[][] getMass() throws Exception {
        try {
            int[][] matrix;
            BufferedReader reader = new BufferedReader(new FileReader("D:\\Навчання\\8 семестр\\ДМСАПР\\dm\\src\\files\\lab3.txt"));
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
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    public void start() throws Exception {
        this.distances = getMass();
        int n = distances.length;
        bestPath = new int[n];
        for (int i = 0; i < n; i++) {
            bestPath[i] = i;
        }
        bestLength = Integer.MAX_VALUE;
        List<Integer> available = new ArrayList<Integer>();
        for (int i = 1; i < n; i++) {
            available.add(i);
        }
        BranchAndBound(new int[] { 0 }, 0, available);
        System.out.println("Shortest path by branch and bound method: " + Arrays.toString(bestPath));
        System.out.println("Length: " + bestLength);
    }

    private void BranchAndBound(int[] path, int length, List<Integer> available) {
        if (available.isEmpty()) {
            length += distances[path[path.length - 1]][path[0]];
            if (length < bestLength) {
                bestLength = length;
                System.arraycopy(path, 0, bestPath, 0, path.length);
            }
            return;
        }
        if (length >= bestLength) {
            return;
        }
        int lastCity = path[path.length - 1];
        for (int i = 0; i < available.size(); i++) {
            int city = available.get(i);
            int newLength = length + distances[lastCity][city];
            if (newLength < bestLength) {
                int[] newPath = Arrays.copyOf(path, path.length + 1);
                newPath[path.length - 1] = city;
                List<Integer> newAvailable = new ArrayList<Integer>(available);
                newAvailable.remove(i);
                BranchAndBound(newPath, newLength, newAvailable);
            }
        }
    }
}
