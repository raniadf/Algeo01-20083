package Functions;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.lang.Math;

public class Gaussian {

    /** GAUSS ELIMINATION
     * Membentuk matriks eselon baris dengan metode eliminasi Gauss
     * @param M matriks augmented
     * @return matriks eselon baris
     */
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

        return M;
    }

    /** GAUSS-JORDAN ELIMINATION
     * Membentuk matriks eselon baris tereduksi dengan metode eliminasi Gauss-Jordan
     * @param M matriks augmented
     * @return matriks eselon baris tereduksi
     */
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

    /** SOLVE UNIQUE SPL
     * Mencari solusi unik dari sebuah SPL
     * @param M matriks SPL
     * @return array of double berisi solusi SPL yang unik
     */
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

    /** GET LETTER
     * Mengembalikan karakter dari sebuah nilai integer, digunakan untuk solusi parametrik
     * @param i integer yang ingin diubah ke karakter
     * @return karakter dari integer
     */
    public static char getLetter(int i) {
        return (char) (i + 65);
    }

    /** IS INDEX VALID?
     * Memvalidasi apakah sebuah indeks tidak terdapat dalam sebuah array double
     * @param checkIdx array double terkait
     * @param idx indeks yang ingin dicek keberadaannya
     * @return boolean, benar/salah sebuah indeks tidak terdapat dalam sebuah array double
     */
    public static boolean IdxValid(double[] checkIdx, int idx) {
        boolean valid = true;

        // if idx is already stored, valid = false
        if (checkIdx[idx] == idx){
            valid = false;
        }

        return valid;
    }
    
    /** IS STRING VALID?
     * Memvalidasi apakah sebuah indeks tidak terdapat dalam sebuah array string
     * @param checkStr array string terkait
     * @param idx indeks yang ingin dicek keberadaannya
     * @return boolean, benar/salah sebuah indeks tidak terdapat dalam sebuah array string
     */
    public static boolean StrValid(String[] checkStr, int idx) {
        boolean valid = true;

        // if idx is already stored, valid = false
        if (checkStr[idx] != ""){
            valid = false;
        }

        return valid;
    }

    /** SOLVE SPL (GAUSS/GAUSS-JORDAN)
     * Menyelesaikan SPL dalam bentuk matriks eselon baris/eleson baris tereduksi sesuai jenis solusinya
     * @param M matriks SPL
     * @param SPLtype metode penyelesaian SPL (GAUSS/GAUSS-JORDAN)
     * @return array of string berisi solusi SPL
     */
    public static String[] SolveSPL(Matrix M, String SPLtype) {
        
        // Create double array to put all the solution
        double[] solution = new double[M.cols - 1];
        String print = "<html><center>";
        /**
         1. ASSIGN MATRIX 
         **/
        if (SPLtype == "Gauss") {
            M = Gauss(M);
        } else if (SPLtype == "Gauss Jordan") {
            M = GaussJordan(M);
        }
        
        // Modify matrix
        M = Func.ZeroNRound(M);

        /**
         2. CHECK SOLUTION TYPE
         **/
        // Set parameter
        boolean foundSol = false;
        boolean unique = false;
        boolean foundOne = true;

        // Create int for index
        int i, j;

        // Row loops 
        for (i=0; i<M.rows; i++){

            // Case : No Solution
            // findLeadingOne in the last column
            // Ex : 0 0 0 0 1 -> 0x1 + 0x2 + 0x3 + 0x4 = 1 -> RJC
            if (Func.findLeadingOne(M, i) == (M.cols-1)){
                foundSol = false;
                unique = false;
                foundOne = false;
                break;
            }

            // Case : Solution Available
            for (j=0; j<M.cols; j++){
                foundSol = true;
                // If all main diagonal elements (except the last column) == 1 -> unique solution
                if (i==j && j!=M.cols-1){
                    // Break the loop if 0 is found
                    if (Func.getElmt(M,i,j)!=1){
                        foundOne = false;
                    }
                }
            }
        }

        // Case : Unique solution
        if (foundOne){
            unique = true;
        } 
        // Case : Many solution
        else if (!foundOne && foundSol){
            unique = false;
        }

        /**
         3. PRINT & FIND SOLUTION 
         **/
        
        // Case 1 : No Solution
        if (!foundSol) {
            String[] strNoSolution = new String[1];
            JOptionPane.showMessageDialog(null, "SPL tidak memiliki solusi.", "X Solution Bruv :'v", JOptionPane.WARNING_MESSAGE);            
            return strNoSolution;
        }
        // Case 2 : Unique Solution
        else if (foundSol && unique) {
            // 1. Find the unique solution
            solution = UniqueSPL(M);
            String[] strUniqueSol = new String[M.cols-1];

            // 2. Print the solution
            for (i = 0; i < solution.length; i++) {
                String solniq = "";
                print += "x" + (i + 1) + " = " + solution[i] + "<br>";
                solniq =  "x" + (i + 1) + " = " + solution[i];
                strUniqueSol[i] = solniq;
            }
            JLabel label = new JLabel(print);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            JOptionPane.showMessageDialog(null, label, "Hasilnya Nih :V", JOptionPane.PLAIN_MESSAGE);
            return strUniqueSol;
        }
        // Case 3 : Many Solution
        else {
            // Create double array
            int letter = 0;
            double[] temp = new double[M.cols];
            double[] idxElementUsed = new double[M.cols-1];
            
            // Create & fill string and string array
            String sol = "";
            String[] strManySol = new String[M.cols-1];
            
            for (i = 0; i < strManySol.length; i++){
                strManySol[i] = "";
            }

            // Store 0 for the double array
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
                    print += "x" + (firstIdx + 1) + " = 0<br>";
                    sol = "x" + (firstIdx + 1) + " = 0";
                    if (IdxValid(idxElementUsed, firstIdx)) {
                        idxElementUsed[firstIdx] = firstIdx;
                    }
                    
                    if (StrValid(strManySol, firstIdx)){
                        strManySol[firstIdx] = sol;
                    }
                }

                // Other case
                if (countOne > 1) {
                    // x.. = last column
                    if (countOne == 2 && temp[temp.length - 1] == 1) {
                        print += "x" + (firstIdx + 1) + " = " + Func.getElmt(M, row, temp.length - 1) + "<br>";
                        sol = "x" + (firstIdx + 1) + " = " + Func.getElmt(M, row, temp.length - 1);
                        if (IdxValid(idxElementUsed, firstIdx)) {
                            idxElementUsed[firstIdx] = firstIdx;
                        }
                        
                        if (StrValid(strManySol, firstIdx)){
                            strManySol[firstIdx] = sol;
                        }
                    }

                    else {
                        // Set main element to false
                        MainElmt = false;
                        sol = "";

                        // Column loop
                        for (i = 0; i < temp.length; ++i) {
                            // Get out of the loop if temp[i] != 1
                            if (temp[i] == 0){
                                continue;
                            }

                            // Head element case
                            // Ex:  x.. = ........
                            if (i == firstIdx) {
                                print += "x" + (i + 1) + " = ";
                                sol += "x" + (i + 1) + " = ";
                                if (IdxValid(idxElementUsed, firstIdx)) {
                                    idxElementUsed[firstIdx] = firstIdx;
                                }
                            }
                            
                            // Not head element but not tail
                            else if (i != firstIdx && temp[i] != 0 && MainElmt && i != M.cols - 1) {
                                if (Func.getElmt(M, row, i) > 0) {
                                    print += " - " + String.format("%.3f", Math.abs(Func.getElmt(M, row, i))) + "x" + (i + 1);
                                    sol += " - " + String.format("%.3f", Math.abs(Func.getElmt(M, row, i))) + "x" + (i + 1);
                                } 
                                else {
                                    print += " + " + String.format("%.3f", Math.abs(Func.getElmt(M, row, i))) + "x" + (i + 1);
                                    sol += " + " + String.format("%.3f", Math.abs(Func.getElmt(M, row, i))) + "x" + (i + 1);
                                }
                            }

                            // i = last column 
                            else if (i != firstIdx && temp[i] != 0 && MainElmt && i == M.cols - 1) {
                                if (Func.getElmt(M, row, i) < 0) {
                                    print += " - " + String.format("%.3f", Math.abs(Func.getElmt(M, row, i)));
                                    sol += " - " + String.format("%.3f", Math.abs(Func.getElmt(M, row, i)));
                                } else {
                                    print += " + " + String.format("%.3f", Math.abs(Func.getElmt(M, row, i)));
                                    sol += " + " + String.format("%.3f", Math.abs(Func.getElmt(M, row, i)));
                                }
                            }

                            // Head element after "="
                            else if (!MainElmt && i != firstIdx && temp[i] != 0 && i!= M.cols-1) {
                                if (Func.getElmt(M, row, i) > 0) {
                                    print += " - " + String.format("%.3f", Math.abs(Func.getElmt(M, row, i))) + "x" + (i + 1);
                                    sol += " - " + String.format("%.3f", Math.abs(Func.getElmt(M, row, i))) + "x" + (i + 1);
                                } else {
                                    print += String.format("%.3f", Math.abs(Func.getElmt(M, row, i))) + "x" + (i + 1);
                                    sol += String.format("%.3f", Math.abs(Func.getElmt(M, row, i))) + "x" + (i + 1);
                                }
                                MainElmt = true;
                            }
                            
                        }

                        if (StrValid(strManySol, firstIdx)){
                            strManySol[firstIdx] = sol;
                        }
                        print += "<br>";
                    }
                }

                ++row;
            }

            // Print non head element
            for (i = 0; i<idxElementUsed.length; i++){
                if (idxElementUsed[i]==0 && StrValid(strManySol, i)){
                    print += "x" + (i + 1) + " = " + getLetter(letter) + "<br>";
                    sol = "x" + (i + 1) + " = " + getLetter(letter);
                    letter++;
                }
                
                if (StrValid(strManySol, i)){
                    strManySol[i] = sol;
                }
            }
            JLabel label = new JLabel(print);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            JOptionPane.showMessageDialog(null, label, "Hasilnya Nih :V", JOptionPane.PLAIN_MESSAGE);
            return strManySol;
        }
    }
}
