package sweng;

public class FixedMatrix {
    private final int SIZE = 10000;
    int [][] matrix = new int[SIZE][SIZE];

    public FixedMatrix() {
        int value = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix[i][j] = value;
                value++;
            }
        }
    }

    public int sumRows() {
        int accRow = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                accRow += matrix[i][j];
            }
        }
        return accRow;
    }

    public int sumColumns() {
        int accCol = 0;
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                accCol += matrix[i][j];
            }
        }
        return accCol;
    }
}