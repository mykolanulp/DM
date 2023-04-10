package labs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Lab1 {

    private int sum = 0;
    private List<List<Node>> nodes;

    private int[][] getMass() throws Exception {
        try
        {
            int[][] matrix;
            BufferedReader reader = new BufferedReader(new FileReader("D:\\Навчання\\8 семестр\\ДМСАПР\\dm\\src\\files\\lab1.txt"));
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
    private void solveBoruvka(int[][] matrix) {
        findMinTree(matrix);
        System.out.println("\n");
    }

    private void findMinTree(int[][] matrix) {
        int passed = 1;
        this.nodes = createMatrix(matrix);
        this.sum = 0;
        this.sum += doMinInit(matrix);
        Node current = this.nodes.get(0).stream().filter(x -> x.getValue() == this.sum).findFirst().get();
        Node previus = this.nodes.get(0).stream().filter(x -> x.getValue() == this.sum).findFirst().get();
        this.nodes.get(current.getColumn()).get(current.getRaw()).setChecked();

        System.out.println("Iteration #0" + " Link: " + current.getName() + " Value: " + current.getValue());

        while (true) {
            if (passed == 7)
                break;

            current = compareMinValues(matrix, current);
            this.sum += current.getValue();
            this.nodes.get(current.getColumn()).get(current.getRaw()).setChecked();
            System.out.println("Iteration #" + passed + " Link: " + current.getName() + " Value: " + current.getValue());
            if (current.getRaw() != previus.getRaw()) {
                deactivateRow(previus.getRaw());
            } else if (current.getColumn() != previus.getColumn()) {
                deactivateCol(previus.getColumn());
            }
            previus = current;

            passed++;
        }

        System.out.println("Minimum sum: " + this.sum);
    }

    private void deactivateRow(int row) {
        this.nodes.get(row).forEach(x -> x.setChecked());
    }

    private void deactivateCol(int column) {
        for (List<Node> nodes : this.nodes) {
            nodes.stream().filter(x -> x.getColumn() == column).findFirst().get().setChecked();
        }
    }

    private List<List<Node>> createMatrix(int[][] matrix) {
        String mapName = "abcdefghijk";
        List<List<Node>> nodes = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            nodes.add(new ArrayList<>());
            for (int j = 0; j < matrix.length; j++) {
                nodes.get(i).add(
                        new Node(i, j, matrix[i][j] == 0 ? true : false, matrix[i][j],
                                "" + mapName.charAt(i) + mapName.charAt(j)
                        )
                );
            }
        }
        return nodes;
    }

    private int doMinInit(int[][] matrix) {
        int max = matrix[0][0];
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[0][i] > max) {
                max = matrix[0][i];
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[0][i] < max && matrix[0][i] != 0) {
                max = matrix[0][i];
            }
        }
        return max;
    }

    private Node compareMinValues(int[][] matrix, Node node) {
        Node minRaw = getMinRawValue(matrix, node);
        Node minCol = getMinColumnValue(matrix, node);

        if (minRaw == null) {
            return minCol;
        }
        if (minCol == null) {
            return minRaw;
        }

        return minRaw.getValue() < minCol.getValue() ? minRaw : minCol;
    }
    private Node getMinRawValue(int[][] matrix, Node node) {
        int min = 500;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[node.getRaw()][i] < min && i != node.getColumn()
                    && !this.nodes.get(node.getRaw()).get(i).isCheked()) {
                min = matrix[node.getRaw()][i];
            }
        }
        final int actualMin = min;
        return this.nodes.get(node.getRaw()).stream()
                .filter(x -> x.getValue() == actualMin && x.getColumn() != node.getColumn())
                .findFirst().orElse(null);
    }

    private Node getMinColumnValue(int[][] matrix, Node node) {
        int min = 500;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][node.getColumn()] < min && i != node.getRaw()
                    && !this.nodes.get(i).get(node.getColumn()).isCheked()) {
                min = matrix[i][node.getColumn()];
            }
        }
        Node target = null;
        final int actualMin = min;
        for (List<Node> nodes : this.nodes) {
            target = nodes.stream()
                    .filter(x -> x.getColumn() == node.getColumn() && x.getValue() == actualMin
                            && x.getRaw() != node.getRaw())
                    .findFirst().orElse(null);
            if (target != null)
                break;
        }

        return target;
    }
    public void start() throws Exception {
        solveBoruvka(getMass());
    }
}

class Node
{
    private int raw;
    private int column;
    private int value;
    private boolean isChecked;
    private String name = "";

    public Node()
    {

    }
    public Node(int raw, int column, boolean isChecked, int value, String name)
    {
        this.raw = raw;
        this.column = column;
        this.isChecked = isChecked;
        this.value = value;
        this.name = name;
    }
    public void setChecked()
    {
        this.isChecked = true;
    }
    public int getValue()
    {
        return this.value;
    }
    public int getRaw()
    {
        return this.raw;
    }
    public int getColumn()
    {
        return this.column;
    }
    public boolean isCheked ()
    {
        return this.isChecked;
    }
    public String getName() {
        return this.name;
    }
}
