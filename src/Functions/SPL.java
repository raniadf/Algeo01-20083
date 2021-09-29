package src.Functions;
import javax.swing.JOptionPane;

public class SPL {

    // Gauss elimination -> Output : Matrix
    public static Matrix Gauss(Matrix M) {
        // Set index
        int i = 0;

        // Forward loop
        for (int j = 0; j < M.cols; ++j) {
            // Set parameter found one in row to false
            boolean foundOneinRow = false;

            // Case matrix = 0
            if (Func.getElmt(M, i, j) == 0) {

                // Parameter notzero
                boolean notZero = false;
                // Integer to check the same columns
                int ColCheck = i + 1;

                // To check the existence of non zero number in the same column
                while (ColCheck < M.rows && !notZero) {
                    if (Func.getElmt(M, ColCheck, j) != 0) {
                        notZero = true;
                        // Swap the rows
                        Func.switchOBE(M, ColCheck + 1, i + 1);
                    }
                    // Change rows
                    ++ColCheck;
                }
            }

            // Case matrix != 0
            if (Func.getElmt(M, i, j) != 0) {

                // Divide the column by itself so that the leading column = 1
                double Divider = Func.getElmt(M, i, j);
                Func.divideOBE(M, i + 1, Divider);
                foundOneinRow = true;

                // Substract multiples of the row to other rows
                double Factor;
                int OtherRows = i + 1;
                while (OtherRows < M.rows) {
                    Factor = Func.getElmt(M, OtherRows, j);
                    Func.addOBE(M, OtherRows + 1, i + 1, ((-1) * Factor));
                    ++OtherRows;
                }

            }

            // Change row & column if found 1 in row
            // x found 1 = switch column, same row
            if (foundOneinRow) {
                ++i;
            }

            // Break the loop if index of rows > rows of matrix
            if (M.rows <= i) {
                break;
            }
        }

        M = Func.ZeroNRound(M);
        return M;
    }

    // Gauss Jordan Elimination -> Output : Matrix
    public static Matrix GaussJordan(Matrix M) {
        // Set index
        int i, j;

        // Replace the matrix with matrix gauss
        M = Gauss(M);

        // Backward loop
        for (i = M.rows - 1; i >= 0; --i) {

            for (j = M.cols - 1; j >= 0; --j) {

                // Case matrix = 1
                if (Func.getElmt(M, i, j) == 1) {
                    // Substract multiples of the row to other rows
                    double Factor;
                    int OtherRows = i - 1;
                    while (OtherRows >= 0) {
                        Factor = Func.getElmt(M, OtherRows, j);
                        Func.addOBE(M, OtherRows + 1, i + 1, ((-1) * Factor));
                        --OtherRows;
                    }
                }
            }
        }

        M = Func.ZeroNRound(M);
        return M;
    }

    // Find the solution for unique SPL
    public static double[] UniqueSPL(Matrix M) {
        // Create a double to put all the solution
        double[] solution = new double[M.cols];

        // Initialize columns & rows
        int columns;
        int rows = M.rows;

        // Fill the solution with 0
        for (int i = 0; i < M.cols; ++i) {
            solution[i] = 0;
        }

        // Find the value
        while (true) {
            --rows;
            // Break from the loop when idx row is not valid
            if (rows < 0) {
                break;
            }
            // Find the leading one from the row
            columns = Func.findLeadingOne(M, rows);

            // Case = -1 --> all 0 row
            if (columns == -1) {
                continue;
            }

            // Case = 2 --> if col + 1 = last col --> value = last col
            if (columns + 1 == M.cols) {
                solution[columns] = Func.getElmt(M, rows, M.cols - 1);
                continue;
            }

            // Case = 3 --> col+1 is not the last col
            solution[columns] = Func.getElmt(M, rows, M.cols - 1);

            // Looping to find the value
            for (int j = columns + 1; j < M.cols; ++j) {
                solution[columns] -= Func.getElmt(M, rows, j) * solution[j];
            }
        }

            // for (int i = 0; i < solution.length; i++) {
            //     System.out.println(solution[i]);
            // }

        return solution;
    }

    // Solve SPL
    public static void SolveSPL(Matrix M, String SPLtype) {

        // ASSIGN MATRIX
        if (SPLtype == "Gauss") {
            M = Gauss(M);
        } else if (SPLtype == "Gauss Jordan") {
            M = GaussJordan(M);
        }

        // CHECK SOLUTION TYPE
        boolean foundSol = false;
        boolean unique = false;
        int i, j;

        // Check the last row
        // Case 1 : last element != 0
        // 1. No Solution
        // [1 2 1 2] [1]
        // [0 1 1 2] [0]
        // [0 0 0 2] [-1]

        // 2. Unique Solution
        // 1 1 1 0
        // 0 1 -1 1
        // 0 0 1 1
        if (Func.getElmt(M, M.rows - 1, M.cols - 1) != 0) {
            j = M.cols - 2;
            while (!foundSol && j >= 0) {
                // Case 1.2
                if (Func.getElmt(M, M.rows - 1, j) != 0) {
                    unique = true;
                    foundSol = true;
                }
                // Case 1.1 = always 0 and always !foundSol
                --j;
            }
        }

        // Case 2 : last element == 0
        // 3. Many Solution
        // 1 2 1 1
        // 0 1 1 2
        // 0 0 0 0
        if (Func.getElmt(M, M.rows - 1, M.cols - 1) == 0) {
            for (j = M.cols - 1; j >= 0; --j) {
                if (Func.getElmt(M, M.rows - 1, j) != 0) {
                    break;
                } else {
                    foundSol = true;
                    unique = false;
                }
            }
        }

        // PRINT SOLUTION
        // Case 1 : No Solution
        if (!foundSol) {
            JOptionPane.showMessageDialog(null, "SPL tidak memiliki solusi.", "X Solution Bruv :'v", JOptionPane.WARNING_MESSAGE);
        }
        // Case 2 : Unique Solution
        else if (foundSol && unique) {
            // 1. Find the unique solution
            double[] solution = new double[M.cols];
            solution = UniqueSPL(M);

            // 2. Print the solution

            for (i = 0; i < solution.length - 1; i++) {
                System.out.println("x" + (i + 1) + " = " + solution[i]);
            }
        }
        // Case 3 : Many Solution
        else if (foundSol && !unique) {
            // ADUH GATAU PARAMETRIK GIMANA
        }
    }
}

// public static void SPLGauss(Matrix M) {
// M = Gauss(M);

// // CHECK SOLUTION TYPE
// boolean foundSol = false;
// boolean unique = false;
// int i, j;

// // Check the last row
// // Case 1 : last element != 0
// // 1. No Solution
// // [1 2 1 2] [1]
// // [0 1 1 2] [0]
// // [0 0 0 2] [-1]
// // 2. Unique Solution
// // 1 1 1 0
// // 0 1 -1 1
// // 0 0 1 1
// if (Func.getElmt(M, M.rows - 1, M.cols - 1) != 0) {
// j = M.cols - 1;
// while (!foundSol && j >= 0) {
// // Case 1.2
// if (Func.getElmt(M, M.rows - 1, j) != 0) {
// unique = true;
// foundSol = true;
// }
// // Case 1.1 = always 0 and always !foundSol
// --j;
// }
// }

// // Case 2 : last element == 0
// // 3. Many Solution
// // 1 2 1 1
// // 0 1 1 2
// // 0 0 0 0
// if (Func.getElmt(M, M.rows - 1, M.cols - 1) == 0) {
// for (j = M.cols - 1; j >= 0; --j) {
// if (Func.getElmt(M, M.rows - 1, j) != 0) {
// break;
// } else {
// foundSol = true;
// unique = false;
// }
// }
// }

// // PRINT SOLUTION
// double[] solution = new double[M.rows];
// if (!foundSol) {
// // No solution
// System.out.println("SPL tidak memiliki solusi.");
// } else if (foundSol && unique) {
// // Find the unique solution
// for (i = M.rows - 1; i >= 0; --i) {
// double sum = 0.0;
// for (j = i + 1; j < M.rows; ++j) {
// sum = sum + (Func.getElmt(M, i, j) * solution[j]);
// }
// solution[i] = (Func.getElmt(M, i, M.rows - 1) - sum) / Func.getElmt(M, i, i);
// }

// // Print the solution
// for (i = 0; i < solution.length; i++) {
// System.out.println("x" + (i + 1) + " = " + solution[i]);
// }
// } else if (foundSol && !unique) {
// // ADUH GATAU PARAMETRIK GIMANA
// }

// }

// public static void SPLGaussJordan(Matrix M) {
// M = GaussJordan(M);

// // CHECK SOLUTION TYPE
// boolean foundSol = false;
// boolean unique = false;
// int i, j;

// // PRINT SOLUTION
// double[] solution = new double[M.rows];
// if (!foundSol) {
// // No solution --> Output = null;
// System.out.println("SPL tidak memiliki solusi.");
// solution = null;
// } else if (foundSol && unique) {
// // Find the unique solution
// for (i = M.rows - 2; i >= 0; --i) {
// double sum = 0.0;
// for (j = i + 1; j < M.rows; ++j) {
// sum = sum + (Func.getElmt(M, i, j) * solution[j]);
// }
// solution[i] = (Func.getElmt(M, i, M.rows - 1) - sum) / Func.getElmt(M, i, i);
// }

// // Print the solution
// for (i = 0; i < solution.length; i++) {
// System.out.println("x" + (i + 1) + " = " + solution[i]);
// }
// } else if (foundSol && !unique) {
// // ADUH GATAU PARAMETRIK GIMANA
// }

// }
// }
