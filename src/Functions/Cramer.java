package src.Functions;
import javax.swing.JOptionPane;
import java.text.DecimalFormat;

public class Cramer {
    /** SOLVE SPL (CRAMER)
     * Menemukan solusi sistem persamaan linear (valid apabila sistem memiliki solusi unik)
     * @param m matriks yang ingin dicari solusinya
     * @return array of double berisi solusi
     */
    public static double[] solveSPL(Matrix m){
        double[] store = new double[m.cols - 1];
        if (m.rows != m.cols - 1){
            JOptionPane.showMessageDialog(null, "Dibutuhkan " + (m.cols - 1) + " buah persamaan linier untuk " + (m.cols - 1) + " peubah", "Uh Oh...", JOptionPane.WARNING_MESSAGE);
            Interface.user();
            return store;
        }

        //Persamaan linier Ax = b menjadi matriks A dan matriks b
        Matrix A = new Matrix(m.rows, m.cols - 1);
        Matrix b = new Matrix(m.rows, 1);
        int i, j;
        for (i = 0; i <= Func.getLastIdxRow(A); i++){
            for (j = 0; j <= Func.getLastIdxCol(A); j++){
                Func.setElmt(A, i, j, Func.getElmt(m, i, j));
            }
        }
        for (i = 0; i <= Func.getLastIdxRow(b); i++){
                Func.setElmt(b, i, 0, Func.getElmt(m, i, Func.getLastIdxCol(m)));
        }

        // Mencari determinan matriks A
        double detA = Determinant.cofExp(A);
        if (detA == 0){
            JOptionPane.showMessageDialog(null, "Sistem tidak memiliki solusi atau tidak bisa diselesaikan dengan metode ini!", "DETERMINAN = 0", JOptionPane.WARNING_MESSAGE);
            Interface.user();
        }

        // Mencari determinan matriks Ai
        Matrix Ai;
        for (j = 0; j <= Func.getLastIdxCol(A); j++){
            Ai = Func.copyMatrix(A);
            for (i = 0; i <= Func.getLastIdxRow(A); i++){
                Func.setElmt(Ai, i, j, Func.getElmt(b, i, 0));
            }
            double detAi = Determinant.cofExp(Ai);
            DecimalFormat df = new DecimalFormat("####0.00");
            System.out.print("x" + (j+1) + " = " + df.format(detAi/detA) + "\n");
            store[j] = detAi/detA;
        }
        return store;
    }
}