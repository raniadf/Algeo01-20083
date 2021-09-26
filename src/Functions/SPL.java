package src.Functions;

public class SPL {
    public static Matrix Gauss(Matrix A) {
        // Set index
        int i, j;

        // Forward loop
        for (i = 0; i < A.rows; ++i) {

            for (j = 0; j < A.cols; ++j) {

                // Case matrix = 0
                if (Func.getElmt(A, i, j) == 0) {

                    // Parameter notzero
                    boolean notZero = false;
                    // Integer to check the same columns
                    int ColCheck = i + 1;

                    // To check the existence of non zero number in the same column
                    while (ColCheck < A.rows && !notZero) {
                        if (Func.getElmt(A, ColCheck, j) != 0) {
                            notZero = true;
                            // Swap the rows
                            Func.switchOBE(A, ColCheck + 1, i);
                        }
                        ++ColCheck;
                    }
                }

                // Case matrix != 0
                if (Func.getElmt(A, i, j) != 0) {

                    // Divide the column by itself so that the leading column = 1
                    double Divider = Func.getElmt(A, i, j);
                    Func.divideOBE(A, i + 1, Divider);

                    // Substract multiples of the row to other rows
                    double Factor;
                    int OtherRows = i + 1;
                    while (OtherRows < A.rows) {
                        Factor = Func.getElmt(A, OtherRows, j);
                        Func.addOBE(A, OtherRows + 1, i + 1, ((-1) * Factor));
                        ++OtherRows;
                    }

                }
            }
        }

        return A;
    }

    public static Matrix GaussJordan(Matrix A) {
        // Set index
        int i, j;

        // Replace the matrix with matrix gauss
        A = Gauss(A);

        // Backward loop
        for (i = A.rows - 1; i >= 0; --i) {

            for (j = A.cols - 1; j >= 0; --j) {

                // Case matrix = 1
                if (Func.getElmt(A, i, j) == 1) {
                    // Substract multiples of the row to other rows
                    double Factor;
                    int OtherRows = i - 1;
                    while (OtherRows >= 0) {
                        Factor = Func.getElmt(A, OtherRows, j);
                        Func.addOBE(A, OtherRows + 1, i + 1, ((-1) * Factor));
                        --OtherRows;
                    }
                }
            }
        }

        return A;
    }
}
