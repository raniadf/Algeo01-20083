package src.Functions;

public class SPL {
    public static Matrix Gauss(Matrix M) {
        // Set index
        int i, j;

        // Forward loop
        for (i = 0; i < M.rows; ++i) {

            for (j = 0; j < M.cols; ++j) {

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
                            Func.switchOBE(M, ColCheck + 1, i);
                        }
                        ++ColCheck;
                    }
                }

                // Case matrix != 0
                if (Func.getElmt(M, i, j) != 0) {

                    // Divide the column by itself so that the leading column = 1
                    double Divider = Func.getElmt(M, i, j);
                    Func.divideOBE(M, i + 1, Divider);

                    // Substract multiples of the row to other rows
                    double Factor;
                    int OtherRows = i + 1;
                    while (OtherRows < M.rows) {
                        Factor = Func.getElmt(M, OtherRows, j);
                        Func.addOBE(M, OtherRows + 1, i + 1, ((-1) * Factor));
                        ++OtherRows;
                    }

                }
            }
        }

        return M;
    }

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

        return M;
    }

    public static int findLeadingOne (Matrix M, int row){
        int j;
        int idx = -1;

        for (j = 0; j < M.cols-1; ++j){
            if (Func.getElmt(M, row, j) == 1){
                idx = j;
                break;
            }
        }
        
        return idx;
    }

    public static void SolveSPL(Matrix M, String SPLtype){
        
        // ASSIGN MATRIX
        if (SPLtype = "Gauss"){
            M = Gauss(M);
        } else if (SPLtype = "Gauss Jordan"){
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
            j = M.cols - 1;
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
        double[] solution = new double[M.cols];
        int col;
        int row = M.rows;

        if (!foundSol) {
            // No solution
            System.out.println("SPL tidak memiliki solusi.");
        } else if (foundSol && unique) {
            // 1. Find the unique solution

            // Set the solution value to 0
            for (i = 0; i < M.cols; ++i){
                solution[i] = 0;
            }

            // Find the value
            while (true){
                --row;
                if (row < 0){
                    break;
                }
                col = findLeadingOne(M, row);

                if (col == -1){
                    continue;
                }

                if (col+1 == M.cols){
                    solution[col] = Func.getElmt(M, row, M.cols-1);
                    continue;
                }
                solution[col] = Func.getElmt(M, rows, M.cols-1);
                
                for (j=col+1; j<M.cols; ++j){
                    solution[col] -= Func.getElmt(M, row, j)*solution[j];
                }
            }

            // 2. Print the solution
            for (i = 0; i < solution.length; i++) {
                System.out.println("x" + (i + 1) + " = " + solution[i]);
            }
        } else if (foundSol && !unique) {
            // ADUH GATAU PARAMETRIK GIMANA
        }


    }
    

    //     public static void SPLGauss(Matrix M) {
    //         M = Gauss(M);

    //         // CHECK SOLUTION TYPE
    //         boolean foundSol = false;
    //         boolean unique = false;
    //         int i, j;

    //         // Check the last row
    //         // Case 1 : last element != 0
    //         // 1. No Solution
    //         // [1 2 1 2] [1]
    //         // [0 1 1 2] [0]
    //         // [0 0 0 2] [-1]
    //         // 2. Unique Solution
    //         // 1 1 1 0
    //         // 0 1 -1 1
    //         // 0 0 1 1
    //         if (Func.getElmt(M, M.rows - 1, M.cols - 1) != 0) {
    //             j = M.cols - 1;
    //             while (!foundSol && j >= 0) {
    //                 // Case 1.2
    //                 if (Func.getElmt(M, M.rows - 1, j) != 0) {
    //                     unique = true;
    //                     foundSol = true;
    //                 }
    //                 // Case 1.1 = always 0 and always !foundSol
    //                 --j;
    //             }
    //         }

    //         // Case 2 : last element == 0
    //         // 3. Many Solution
    //         // 1 2 1 1
    //         // 0 1 1 2
    //         // 0 0 0 0
    //         if (Func.getElmt(M, M.rows - 1, M.cols - 1) == 0) {
    //             for (j = M.cols - 1; j >= 0; --j) {
    //                 if (Func.getElmt(M, M.rows - 1, j) != 0) {
    //                     break;
    //                 } else {
    //                     foundSol = true;
    //                     unique = false;
    //                 }
    //             }
    //         }

    //         // PRINT SOLUTION
    //         double[] solution = new double[M.rows];
    //         if (!foundSol) {
    //             // No solution
    //             System.out.println("SPL tidak memiliki solusi.");
    //         } else if (foundSol && unique) {
    //             // Find the unique solution
    //             for (i = M.rows - 1; i >= 0; --i) {
    //                 double sum = 0.0;
    //                 for (j = i + 1; j < M.rows; ++j) {
    //                     sum = sum + (Func.getElmt(M, i, j) * solution[j]);
    //                 }
    //                 solution[i] = (Func.getElmt(M, i, M.rows - 1) - sum) / Func.getElmt(M, i, i);
    //             }

    //             // Print the solution
    //             for (i = 0; i < solution.length; i++) {
    //                 System.out.println("x" + (i + 1) + " = " + solution[i]);
    //             }
    //         } else if (foundSol && !unique) {
    //             // ADUH GATAU PARAMETRIK GIMANA
    //         }

    //     }

    //     public static void SPLGaussJordan(Matrix M) {
    //         M = GaussJordan(M);

    //         // CHECK SOLUTION TYPE
    //         boolean foundSol = false;
    //         boolean unique = false;
    //         int i, j;

    //         // PRINT SOLUTION
    //         double[] solution = new double[M.rows];
    //         if (!foundSol) {
    //             // No solution --> Output = null;
    //             System.out.println("SPL tidak memiliki solusi.");
    //             solution = null;
    //         } else if (foundSol && unique) {
    //             // Find the unique solution
    //             for (i = M.rows - 2; i >= 0; --i) {
    //                 double sum = 0.0;
    //                 for (j = i + 1; j < M.rows; ++j) {
    //                     sum = sum + (Func.getElmt(M, i, j) * solution[j]);
    //                 }
    //                 solution[i] = (Func.getElmt(M, i, M.rows - 1) - sum) / Func.getElmt(M, i, i);
    //             }

    //             // Print the solution
    //             for (i = 0; i < solution.length; i++) {
    //                 System.out.println("x" + (i + 1) + " = " + solution[i]);
    //             }
    //         } else if (foundSol && !unique) {
    //             // ADUH GATAU PARAMETRIK GIMANA
    //         }

    //     }
    // }