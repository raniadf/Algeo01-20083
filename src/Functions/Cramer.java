package src.Functions;
import javax.swing.JOptionPane;
import java.text.DecimalFormat;

public class Cramer {
    public static double[] solveSPL(Matrix m){
        double[] store = new double[m.cols - 1];
        if (m.rows != m.cols - 1){
            JOptionPane.showMessageDialog(null, "Dibutuhkan " + (m.cols - 1) + " buah persamaan untuk " + (m.cols - 1) + " buah variabel", "Try again", JOptionPane.WARNING_MESSAGE);
            Interface.user();
            return store;
        }
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

        double detA = Determinant.cofExp(A);
        if (detA == 0){
            JOptionPane.showMessageDialog(null, "Sistem tidak memiliki solusi atau tidak bisa diselesaikan dengan metode ini!", "DETERMINAN = 0", JOptionPane.WARNING_MESSAGE);
            Interface.user();
        }

        Matrix temp;
        for (j = 0; j <= Func.getLastIdxCol(A); j++){
            temp = Func.copyMatrix(A);
            for (i = 0; i <= Func.getLastIdxRow(A); i++){
                Func.setElmt(temp, i, j, Func.getElmt(b, i, 0));
            }
            double detTemp = Determinant.cofExp(temp);
            DecimalFormat df = new DecimalFormat("####0.00");
            System.out.print("x" + (j+1) + " = " + df.format(detTemp/detA) + "\n");
            store[j] = detTemp/detA;
        }
        return store;
    }
}