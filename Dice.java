import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Double.NaN;

public class Dice {
    private static int Ndice;
    private static Matrix NewMatrix;
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Integer> list = new ArrayList<>();
    public static void main(String[] args) {
        ArrayList<Integer> Tops = new ArrayList<>();
        ArrayList<Integer> Highs = new ArrayList<>();
        while (sc.hasNextInt()) {
            list.add(sc.nextInt());
            //System.out.println(list);
            if (list.size() == list.get(0) * 6 + 1) {
                list.remove(0);
                System.out.println(list.size());
                break;
            }
        }
        for (int d = 1; d < 7; d++) {

            System.out.println("GOING");

            Matrix DiceMatrix = new Matrix(list.size() - 1, 6);
            int z = -1;
            while (list.size() > 0) {
                z++;
                for (int j = 0; j < 6; j++) {
                    DiceMatrix.setElement(z, j, list.get(0));
                    list.remove(0);
                }
            }
            NewMatrix = DiceMatrix;
            int bot; //Index
            int opp; //Index
            ArrayList<Integer> botskey = new ArrayList<>();
            botskey.add(d);
            for (int row = 0; row < DiceMatrix.getNumRows(); row++) {
                bot = botskey.get(botskey.size() - 1);
                opp = findOppositeValueToValue(NewMatrix, row, bot);
                botskey.add(opp);
                NewMatrix.setElement(row, findIndex(NewMatrix, row, bot), 0);
                NewMatrix.setElement(row, findIndex(NewMatrix, row, opp), 0);


            }
            int high = 0;
            for (int m = 0; m < NewMatrix.getNumRows(); m++) {
                high = 0;
                for (int n = 0; n < NewMatrix.getNumCols(); n++) {
                    if (NewMatrix.getElement(m, n) > high) {
                        high = (int) NewMatrix.getElement(m, n);
                    }
                }
                Highs.add(high);
            }


            int output = 0;
            ArrayList<Integer> combinedHighs = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                for (int i = 0; i < Ndice; i++) {
                    output = output + Highs.get(i + (combinedHighs.size() * Ndice));
                }
                combinedHighs.add(output);
                output = 0;
            }

            int FINAL = 0;
            for (int i = 0; i < combinedHighs.size(); i++) {
                if (combinedHighs.get(i) > FINAL) {
                    FINAL = combinedHighs.get(i);
                }
            }

        }

    }

    private static int findIndex(Matrix m, int row, int x) {
        int result = 0;
        for (int i = 0; i < 6; i++) {
            if (m.getElement(row, i) == x) {
                result = i;
                return i;
            }
        }
        return result;
    }

    private static int findOppositeValueToValue(Matrix m, int row, int x) {
        int result = 0;
        for (int i = 0; i < 6; i++) {
            if (m.getElement(row, i) == x) {
                if (i == 0) result = (int) m.getElement(row, 5);
                if (i == 1) result = (int) m.getElement(row, 3);
                if (i == 2) result = (int) m.getElement(row, 4);
                if (i == 3) result = (int) m.getElement(row, 1);
                if (i == 4) result = (int) m.getElement(row, 2);
                if (i == 5) result = (int) m.getElement(row, 0);

            }
        }
        return result;
    }
}


class Matrix {    // Includes gaussian elimination methods
    public double[][] data;

    public Matrix(int m, int n) {
        data = new double[m][n];
    }

    public void copy(Matrix x) {
        for (int i = 0; i < x.getNumRows(); i++) {
            for (int j = 0; j < x.getNumCols(); j++) {
                data[i][j] = x.getElement(i, j);
            }
        }
    }

    public static Matrix gaussian(Matrix a, Matrix b) {
        int n = a.data.length;                // Number of unknowns
        Matrix q = new Matrix(n, n + 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)    // Form q matrix
                q.data[i][j] = a.data[i][j];
            q.data[i][n] = b.data[i][0];
        }

        forward_solve(q);                    // Do Gaussian elimination
        back_solve(q);                        // Perform back substitution

        Matrix x = new Matrix(n, 1);
        for (int i = 0; i < n; i++)
            x.data[i][0] = q.data[i][n];
        return x;
    }

    private static void forward_solve(Matrix q) {
        int n = q.data.length;

        for (int i = 0; i < n; i++) {    // Find row w/max element in this
            int maxRow = i;                // column, at or below diagonal
            for (int k = i + 1; k < n; k++)
                if (Math.abs(q.data[k][i]) > Math.abs(q.data[maxRow][i]))
                    maxRow = k;

            if (maxRow != i)                // If row not current row, swap
                for (int j = i; j <= n; j++) {
                    double t = q.data[i][j];
                    q.data[i][j] = q.data[maxRow][j];
                    q.data[maxRow][j] = t;
                }

            for (int j = i + 1; j < n; j++) {    // Calculate pivot ratio
                double pivot = q.data[j][i] / q.data[i][i];
                for (int k = i; k <= n; k++)    // Pivot operation itself
                    q.data[j][k] -= q.data[i][k] * pivot;
            }
        }
    }

    private static void back_solve(Matrix q) {
        int n = q.data.length;

        for (int j = n - 1; j >= 0; j--) {        // Start at last row
            double t = 0.0;                    // t- temporary
            for (int k = j + 1; k < n; k++)
                t += q.data[j][k] * q.data[k][n];
            q.data[j][n] = (q.data[j][n] - t) / q.data[j][j];
        }
    }

    public void setIdentity() {
        int i, j;
        int nrows = data.length;
        int ncols = data[0].length;
        for (i = 0; i < nrows; i++)
            for (j = 0; j < ncols; j++)
                if (i == j)
                    data[i][j] = 1.0;
                else
                    data[i][j] = 0.0;
    }

    public int getNumRows() {
        return data.length;
    }

    public int getNumCols() {
        return data[0].length;
    }

    public double getElement(int i, int j) {
        return data[i][j];
    }

    public void setElement(int i, int j, double val) {
        data[i][j] = val;
    }

    public void incrElement(int i, int j, double incr) {
        data[i][j] += incr;
    }

    public double getMin(int n) {
        double min = data[0][0];
        for (int i = 0; i < getNumCols(); i++) {
            if (data[i][n] < min) {
                if (data[i][n] == NaN) {
                } else {
                    min = data[i][n];
                    // added recursion to avoid self-loops on large samplesizes with low range.
                    if (min == 0) {
                        getMin(i + 1);
                    }
                }
            }
        }

        return min;
    }

    public Matrix add(Matrix b) {
        Matrix result = null;
        int nrows = data.length;
        int ncols = data[0].length;
        if (nrows == b.data.length && ncols == b.data[0].length) {
            result = new Matrix(nrows, ncols);
            for (int i = 0; i < nrows; i++)
                for (int j = 0; j < ncols; j++)
                    result.data[i][j] = this.data[i][j] + b.data[i][j];
        }
        return result;
    }

    public Matrix mult(Matrix b) {
        Matrix result = null;
        int nrows = data.length;
        int p = data[0].length;
        if (p == b.data.length) {
            result = new Matrix(nrows, b.data[0].length);
            for (int i = 0; i < nrows; i++)
                for (int j = 0; j < result.data[0].length; j++) {
                    double t = 0.0;
                    for (int k = 0; k < p; k++) {
                        t += data[i][k] * b.data[k][j];
                    }
                    result.data[i][j] = t;
                }
        }
        return result;
    }

    public void print() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++)
                System.out.print(data[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }
}
/*
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Float.NaN;

public class Dice {
    private static int Ndice;
    private static Matrix NewMatrix;
    public static void main(String[] args) {
        ArrayList<Integer> Tops = new ArrayList<>();
        File file = new File("src/Input");

        List<Integer> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(file);) {
            while (scanner.hasNextInt()) {
                list.add(scanner.nextInt());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        ArrayList<Integer> Highs = new ArrayList<>();
        for (int d = 1; d < 7; d++) {

            list = new ArrayList<>();
            try (Scanner scanner = new Scanner(file);) {
                while (scanner.hasNextInt()) {
                    list.add(scanner.nextInt());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Ndice = list.get(0);
            list.remove(0);
            Matrix DiceMatrix = new Matrix(Ndice, 6);
            int z = -1;
            while (list.size() > 0) {
                z++;
                for (int j = 0; j < 6; j++) {
                    DiceMatrix.setElement(z, j, list.get(0));
                    list.remove(0);
                }
            }
            NewMatrix = DiceMatrix;
            int bot; //Index
            int opp; //Index
            ArrayList<Integer> botskey = new ArrayList<>();
            botskey.add(d);
            for (int row = 0; row < DiceMatrix.getNumRows(); row++) {
                bot = botskey.get(botskey.size() - 1);
                opp = findOppositeValueToValue(NewMatrix, row, bot);
                botskey.add(opp);
                NewMatrix.setElement(row, findIndex(NewMatrix, row, bot), 0);
                NewMatrix.setElement(row, findIndex(NewMatrix, row, opp), 0);


            }
            int high = 0;
            for (int m = 0; m < NewMatrix.getNumRows(); m++) {
                high = 0;
                for (int n = 0; n < NewMatrix.getNumCols(); n++) {
                    if (NewMatrix.getElement(m, n) > high) {
                        high = (int) NewMatrix.getElement(m, n);
                    }
                }
                Highs.add(high);
            }
        }

        int output = 0;
        ArrayList<Integer> combinedHighs = new ArrayList<>();
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < Ndice; i++) {
                output = output + Highs.get(i + (combinedHighs.size() * Ndice));
            }
            combinedHighs.add(output);
            output = 0;
        }

        int FINAL = 0;
        for (int i = 0; i < combinedHighs.size(); i++) {
            if (combinedHighs.get(i) > FINAL) {
                FINAL = combinedHighs.get(i);
            }
        }
        try {
            FileWriter f = new FileWriter("StandardOutput.txt");
            BufferedWriter b = new BufferedWriter(f);
            PrintWriter out = new PrintWriter(b);
            out.print(FINAL);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static int findIndex(Matrix m, int row, int x) {
        int result = 0;
        for (int i = 0; i < 6; i++) {
            if (m.getElement(row, i) == x) {
                result = i;
                return i;
            }
        }
        return result;
    }
    private static int findOppositeValueToValue(Matrix m, int row, int x) {
        int result = 0;
        for (int i = 0; i < 6; i++) {
            if (m.getElement(row, i) == x) {
                if (i == 0) result = (int) m.getElement(row, 5);
                if (i == 1) result = (int) m.getElement(row, 3);
                if (i == 2) result = (int) m.getElement(row, 4);
                if (i == 3) result = (int) m.getElement(row, 1);
                if (i == 4) result = (int) m.getElement(row, 2);
                if (i == 5) result = (int) m.getElement(row, 0);

            }
        }
        return result;
    }}

class Matrix {    // Includes gaussian elimination methods
    public double[][] data;
    public Matrix(int m, int n) {
        data = new double[m][n];
    }
    public void copy(Matrix x){
        for(int i = 0; i < x.getNumRows(); i++){
            for(int j = 0; j < x.getNumCols(); j++){
                data[i][j] =  x.getElement(i,j);
            }
        }
    }
    public static Matrix gaussian(Matrix a, Matrix b) {
        int n = a.data.length;                // Number of unknowns
        Matrix q = new Matrix(n, n + 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)    // Form q matrix
                q.data[i][j] = a.data[i][j];
            q.data[i][n] = b.data[i][0];
        }

        forward_solve(q);                    // Do Gaussian elimination
        back_solve(q);                        // Perform back substitution

        Matrix x = new Matrix(n, 1);
        for (int i = 0; i < n; i++)
            x.data[i][0] = q.data[i][n];
        return x;
    }
    private static void forward_solve(Matrix q) {
        int n = q.data.length;

        for (int i = 0; i < n; i++) {    // Find row w/max element in this
            int maxRow = i;                // column, at or below diagonal
            for (int k = i + 1; k < n; k++)
                if (Math.abs(q.data[k][i]) > Math.abs(q.data[maxRow][i]))
                    maxRow = k;

            if (maxRow != i)                // If row not current row, swap
                for (int j = i; j <= n; j++) {
                    double t = q.data[i][j];
                    q.data[i][j] = q.data[maxRow][j];
                    q.data[maxRow][j] = t;
                }

            for (int j = i + 1; j < n; j++) {    // Calculate pivot ratio
                double pivot = q.data[j][i] / q.data[i][i];
                for (int k = i; k <= n; k++)    // Pivot operation itself
                    q.data[j][k] -= q.data[i][k] * pivot;
            }
        }
    }
    private static void back_solve(Matrix q) {
        int n = q.data.length;

        for (int j = n - 1; j >= 0; j--) {        // Start at last row
            double t = 0.0;                    // t- temporary
            for (int k = j + 1; k < n; k++)
                t += q.data[j][k] * q.data[k][n];
            q.data[j][n] = (q.data[j][n] - t) / q.data[j][j];
        }
    }
    public void setIdentity() {
        int i, j;
        int nrows = data.length;
        int ncols = data[0].length;
        for (i = 0; i < nrows; i++)
            for (j = 0; j < ncols; j++)
                if (i == j)
                    data[i][j] = 1.0;
                else
                    data[i][j] = 0.0;
    }
    public int getNumRows() {
        return data.length;
    }
    public int getNumCols() {
        return data[0].length;
    }
    public double getElement(int i, int j) {
        return data[i][j];
    }
    public void setElement(int i, int j, double val) {
        data[i][j] = val;
    }
    public void incrElement(int i, int j, double incr) {
        data[i][j] += incr;
    }
    public double getMin(int n) {
        double min = data[0][0];
        for (int i = 0; i < getNumCols(); i++) {
            if (data[i][n] < min) {
                if (data[i][n] == NaN) {
                } else {
                    min = data[i][n];
                    // added recursion to avoid self-loops on large samplesizes with low range.
                    if (min == 0) {
                        getMin(i + 1);
                    }
                }
            }
        }

        return min;
    }
    public Matrix add(Matrix b) {
        Matrix result = null;
        int nrows = data.length;
        int ncols = data[0].length;
        if (nrows == b.data.length && ncols == b.data[0].length) {
            result = new Matrix(nrows, ncols);
            for (int i = 0; i < nrows; i++)
                for (int j = 0; j < ncols; j++)
                    result.data[i][j] = this.data[i][j] + b.data[i][j];
        }
        return result;
    }
    public Matrix mult(Matrix b) {
        Matrix result = null;
        int nrows = data.length;
        int p = data[0].length;
        if (p == b.data.length) {
            result = new Matrix(nrows, b.data[0].length);
            for (int i = 0; i < nrows; i++)
                for (int j = 0; j < result.data[0].length; j++) {
                    double t = 0.0;
                    for (int k = 0; k < p; k++) {
                        t += data[i][k] * b.data[k][j];
                    }
                    result.data[i][j] = t;
                }
        }
        return result;
    }
    public void print() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++)
                System.out.print(data[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }
}*/