package src.Functions;

import javax.swing.JOptionPane;
import java.lang.Math;

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
        double[] solution = new double[M.cols - 1];

        // Initialize columns & rows
        int columns;
        int rows = M.rows;

        // Fill the solution with 0
        for (int i = 0; i < M.cols - 1; ++i) {
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
            for (int j = columns + 1; j < M.cols - 1; ++j) {
                solution[columns] -= Func.getElmt(M, rows, j) * solution[j];
            }
        }
        return solution;
    }

    public static char getLetter(int i) {
        return (char) (i + 64);
    }

    public static boolean IdxValid(double[] checkIdx, int idx) {
        boolean valid = false;
        for (int i = 0; i < checkIdx.length; ++i) {
            if (checkIdx[i] != 0) {
                valid = false;
            } else if (checkIdx[i] == idx) {
                valid = false;
            } else
                valid = true;
        }
        return valid;
    }

    // Solve SPL
    public static double[] SolveSPL(Matrix M, String SPLtype) {

        double[] solution = new double[M.cols - 1];
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
            JOptionPane.showMessageDialog(null, "SPL tidak memiliki solusi.", "X Solution Bruv :'v",
                    JOptionPane.WARNING_MESSAGE);
            solution = null;
        }
        // Case 2 : Unique Solution
        else if (foundSol && unique) {
            // 1. Find the unique solution
            solution = UniqueSPL(M);

            // 2. Print the solution
            for (i = 0; i < solution.length; i++) {
                System.out.println("x" + (i + 1) + " = " + solution[i]);
            }
        }
        // Case 3 : Many Solution
        else if (foundSol && !unique) {

            // Create double array
            double[] temp = new double[M.cols];
            double[] idxElementUsed = new double[M.cols-1];

            for (i = 0; i < M.cols - 1; ++i) {
                idxElementUsed[i] = 0;
            }
            // Set index row to 0
            int row = 0;

            // Row looping while true
            while (true) {
                // Break the loop
                if (row >= M.rows) {
                    break;
                }

                // Case where row = all 0 -> end the loop
                if (Func.findLeadingOne(M, row) == -1) {
                    break;
                }

                // Index to count one
                int countOne = 0;

                // Store temp value per row
                // Ex : 1 5 4 3 0 2 0 6 --> 1 1 1 1 0 1 0 1
                for (int col = 0; col < M.cols; col++) {
                    if (Func.getElmt(M, row, col) != 0) {
                        temp[col] = 1;
                        countOne++;
                    } else
                        temp[col] = 0;
                }

                // Find the first 1
                int firstIdx = 0;
                for (i = 0; i < temp.length; ++i) {
                    if (temp[i] == 1) {
                        firstIdx = i;
                        break;
                    }
                }

                // PRINT SOLUTION
                // If countOne = 1 and firstidx != last column index -> solution[firstidx] =
                // last column
                boolean MainElmt = false;
                if (countOne == 1 && firstIdx != (M.cols - 1)) {
                    System.out.print("x" + (firstIdx + 1) + " = 0");
                    System.out.println();
                    if (IdxValid(idxElementUsed, firstIdx)) {
                        idxElementUsed[firstIdx] = firstIdx;
                    }
                }

                // Other case
                if (countOne > 1) {
                    // x.. = last column
                    if (countOne == 2 && temp[temp.length - 1] == 1) {
                        System.out.println("x" + (firstIdx + 1) + " = " + Func.getElmt(M, row, temp.length - 1));
                        if (IdxValid(idxElementUsed, firstIdx)) {
                            idxElementUsed[firstIdx] = firstIdx;
                        }
                    }

                    else {
                        MainElmt = false;

                        for (i = 0; i < temp.length; ++i) {
                            if (temp[i] == 0){
                                continue;
                            }

                            if (i == firstIdx) {
                                System.out.print("x" + (i + 1) + " = ");
                                if (IdxValid(idxElementUsed, firstIdx)) {
                                    idxElementUsed[firstIdx] = firstIdx;
                                }
                            }

                            else if (i != firstIdx && temp[i] != 0 && MainElmt && i != M.cols - 1) {
                                if (Func.getElmt(M, row, i) > 0) {
                                    System.out.print(" - " + String.format("%.3f", Math.abs(Func.getElmt(M, row, i))) + "x" + (i + 1));
                                } else {
                                    System.out.print(" + " + String.format("%.3f", Math.abs(Func.getElmt(M, row, i))) + "x" + (i + 1));
                                }
                            }

                            else if (i != firstIdx && temp[i] != 0 && MainElmt && i == M.cols - 1) {
                                if (Func.getElmt(M, row, i) < 0) {
                                    System.out.print(" - " + String.format("%.3f", Math.abs(Func.getElmt(M, row, i))));
                                } else {
                                    System.out.print(" + " + String.format("%.3f", Math.abs(Func.getElmt(M, row, i))));
                                }
                            }

                            else if (!MainElmt && i != firstIdx && temp[i] != 0 && i!= M.cols-1) {
                                if (Func.getElmt(M, row, i) > 0) {
                                    System.out.print(" - " + String.format("%.3f", Math.abs(Func.getElmt(M, row, i))) + "x" + (i + 1));
                                } else {
                                    System.out.print(String.format("%.3f", Math.abs(Func.getElmt(M, row, i))) + "x" + (i + 1));
                                }
                                MainElmt = true;
                            }

                        }
                        System.out.println();
                    }
                }

                ++row;
            }

            for (i = 1; i<idxElementUsed.length; i++){
                if (idxElementUsed[i]==0){
                    System.out.println("x" + (i + 1) + " = " + getLetter(i));
                }
            }

            solution = null;
        }

        return solution;
    }
}
