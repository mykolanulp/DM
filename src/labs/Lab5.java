package labs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Lab5 {
    private int[][] mass1;
    private int[][] mass2;

    private final String path1 = "D:\\Навчання\\8 семестр\\ДМСАПР\\dm\\src\\files\\lab1.txt";
    private final String path2 = "D:\\Навчання\\8 семестр\\ДМСАПР\\dm\\src\\files\\lab5.txt";

    private int[][] getMass(String path) throws Exception {
        try
        {
            int[][] matrix;
            BufferedReader reader = new BufferedReader(new FileReader(path));
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

    private boolean checkIsomorphic()
    {
        List<Integer> list1 = new ArrayList<Integer>();
        List<Integer> list2 = new ArrayList<Integer>();

        for (int i = 0; i < mass1.length; i++) {
            for (int j = 0; j < mass1.length; j++) {
                list1.add(mass1[i][j]);
                list2.add(mass2[i][j]);
            }
        }

        Collections.sort(list1);
        Collections.sort(list2);

        for (int i = 0; i < list1.size(); i++) {
            if (!list1.get(i).equals(list2.get(i))) {
                return false;
            }
        }

        return true;
    }

    public void start() throws Exception {
        mass1 = getMass(path1);
        mass2 = getMass(path2);

        for (int i = 0; i < mass1.length; i++) {
            for (int j = 0; j < mass1[0].length; j++) {
                System.out.print(mass1[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();

        for (int i = 0; i < mass2.length; i++) {
            for (int j = 0; j < mass2[0].length; j++) {
                System.out.print(mass2[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Is two matrices isomorphic? - " + checkIsomorphic());
    }
}
