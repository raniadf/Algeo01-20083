package src.Functions;

public class SPL {
    public double[][] Gauss(double A[][]) {
        // Count the rows & columns
        int RowCount = A.length;
        int ColCount = A[0].length;

        // Set index
        int i, j;

        // Forward loop
        for (i = 0; i < RowCount; ++i) {

            for (j = 0; j < ColCount; ++j) {

                // Case matrix = 0
                if (A[i][j] == 0) {

                    // Parameter notzero
                    boolean notZero = false;
                    // Integer to check the same columns
                    int ColCheck = i + 1;

                    // To check the existence of non zero number in the same column
                    while (ColCheck < RowCount && !notZero) {
                        if (A[ColCheck][j] != 0) {
                            notZero = true;
                            // Swap the rows
                            switchOBE(A, ColCheck + 1, i);
                        }
                        ++ColCheck;
                    }
                }

                // Case matrix != 0
                if (A[i][j] != 0) {

                    // Divide the column by itself so that the leading column = 1
                    double Divider = A[i][j];
                    divideOBE(A, i + 1, Divider);

                    // Substract multiples of the row to other rows
                    double Factor;
                    int OtherRows = i + 1;
                    while (OtherRows < RowCount) {
                        Factor = A[OtherRows][j];
                        addOBE(A, OtherRows + 1, i + 1, ((-1) * Factor));
                        ++OtherRows;
                    }

                }
            }
        }

        return A;
    }

    public double[][] GaussJordan(double A[][]) {
        // Count the rows & columns
        int RowCount = A.length;
        int ColCount = A[0].length;

        // Set index
        int i, j;

        // Replace the matrix with matrix gauss
        A = Gauss(A);

        // Backward loop
        for (i = RowCount - 1; i >= 0; --i) {

            for (j = ColCount - 1; j >= 0; --j) {

                // Case matrix = 1
                if (A[i][j] == 1) {
                    // Substract multiples of the row to other rows
                    double Factor;
                    int OtherRows = i - 1;
                    while (OtherRows >= 0) {
                        Factor = A[OtherRows][j];
                        addOBE(A, OtherRows + 1, i + 1, ((-1) * Factor));
                        --OtherRows;
                    }
                }
            }
        }

        return A;
    }
}
