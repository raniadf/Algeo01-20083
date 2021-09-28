package src.Functions;
import javax.swing.JOptionPane;

public class Invers {
    // belom bisa dipakekkk
    // tinggal fill in the comments aja... kalo gaada yg salah XD LOL bau2nya ada si LOL XD
    public static Matrix solveSPL(Matrix m){
        // GENERALNYA: x = A^(-1) b --> untuk matrix m * n
        // kalo n (kolom) != m+1 (baris+1) kluarin output "dibutuhkan n persamaan untuk n variiabel (n nya pake baris/kolom)
        // pisah A sama b
        // inverse A
        // A(^-1) * b
        if (m.rows != m.cols - 1){
            JOptionPane.showMessageDialog(null, "Dibutuhkan " + (m.cols - 1) + " buah persamaan untuk " + (m.cols - 1) + " buah variabel", "Try again", JOptionPane.WARNING_MESSAGE);
            Interface.user();
            return m;
        }
        Matrix A = new Matrix(m.rows, m.cols - 1);
        Matrix b = new Matrix(m.rows, 1);
        Matrix var = new Matrix(m.rows, 1);

        int i, j;
        for (i = 0; i <= Func.getLastIdxRow(A); i++){
            for (j = 0; j <= Func.getLastIdxCol(A); j++){
                Func.setElmt(A, i, j, Func.getElmt(m, i, j));
            }
        }

        for (i = 0; i <= Func.getLastIdxRow(b); i++){
                Func.setElmt(b, i, 0, Func.getElmt(m, i, Func.getLastIdxCol(m)));
            }

        //inverse(A);
        var = Func.multiply(A, b);

        for (i = 0; i <= Func.getLastIdxRow(var); i++){
                System.out.print("x" + (i+1) + " = " + Func.getElmt(var, i, 0) + "\n");
        }
        return m;
    }
}
