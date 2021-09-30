package src.Functions;
import javax.swing.SwingConstants;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.JLabel;

public class Invers {
    /** SOLVE SPL (INVERS)
     * Menemukan solusi sistem persamaan linear (valid apabila sistem memiliki solusi unik)
     * @param m matriks yang ingin dicari solusinya
     * @return array of double berisi solusi
     */
    public static String[] solveSPL(Matrix m){
        String[] result = new String[m.rows];
        String print = "<html><center>";
        if (m.rows != m.cols - 1){
            JOptionPane.showMessageDialog(null, "Dibutuhkan " + (m.cols - 1) + " buah persamaan untuk " + (m.cols - 1) + " buah variabel", "Try again", JOptionPane.WARNING_MESSAGE);
            return result;
        }
        Matrix A = new Matrix(m.rows, m.cols - 1);
        Matrix b = new Matrix(m.rows, 1);
        Matrix var;

        int i, j;
        for (i = 0; i <= Func.getLastIdxRow(A); i++){
            for (j = 0; j <= Func.getLastIdxCol(A); j++){
                Func.setElmt(A, i, j, Func.getElmt(m, i, j));
            }
        }

        for (i = 0; i <= Func.getLastIdxRow(b); i++){
                Func.setElmt(b, i, 0, Func.getElmt(m, i, Func.getLastIdxCol(m)));
            }

        A = gaussJordan(A); // Invers matriks A dengan metode adjoin
        if (A.rows == m.rows + 1){
            return result;
        }
        var = Func.multiply(A, b); // Melakukan perkalian A dan b

        DecimalFormat df = new DecimalFormat("####0.0000");
        for (i = 0; i <= Func.getLastIdxRow(var); i++){
                print += "x" + (i+1) + " = " + df.format(Func.getElmt(var, i, 0)) + "<br>";
                result[i] = df.format(Func.getElmt(var, i, 0));
        }
        JLabel label = new JLabel(print);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(null, label, "Hasilnya Nih :V", JOptionPane.PLAIN_MESSAGE);
        return result;
    }

    /** INVERSE (ADJOINT)
     * Mengembalikan inverse dari matriks dengan metode kofaktor/adjoin
     * Prekondisi: matriks persegi (isSquare(m))
     * @param m matriks yang ingin dicari inversnya
     * @return matriks invers dari m
     */
    public static Matrix adjoint(Matrix m) {
        if (Determinant.cofExp(m) == 0){
            JOptionPane.showMessageDialog(null, "Tidak memiliki solusi!", "DETERMINAN = 0", JOptionPane.WARNING_MESSAGE);
            Matrix a = new Matrix(m.rows + 1, m.cols);
            return a;
        }
        Matrix adjointMat;
        Matrix inverseMat = new Matrix(m.rows, m.cols);

        double det = Determinant.cofExp(m);
        adjointMat = Func.transpose(Func.makeCofactor(m));

        int i, j;
        for (i = 0; i < m.rows; i++) {
            for (j = 0; j < m.cols; j++) {
                inverseMat.contents[i][j] = (1 / det) * adjointMat.contents[i][j];
            }
        }
        return inverseMat;
    }

    /** INVERSE (GAUSS JORDAN)
     * Mengembalikan inverse dari matriks dengan metode Gauss Jordan
     * Prekondisi: matriks persegi (isSquare(m))
     * @param m matriks yang ingin dicari inversnya
     * @return matriks invers dari m
     */
    public static Matrix gaussJordan(Matrix m) {
        if (Determinant.cofExp(m) == 0){
            JOptionPane.showMessageDialog(null, "Matriks tidak memiliki inverse\n \nApabila matriks merupakan SPL disarankan untuk mencoba metode Gauss-Jordan", "DETERMINAN = 0", JOptionPane.WARNING_MESSAGE);
            Matrix a = new Matrix(m.rows + 1, m.cols);
            return a;
        }
        Matrix inverseMat = new Matrix(m.rows, m.cols);
        Matrix temp = new Matrix(m.rows, m.cols * 2);

        int i, j;
        for (i = 0; i < m.rows; i++) {
            for (j = 0; j < m.cols; j++) {
                temp.contents[i][j] = m.contents[i][j];
            }
        }

        for (i = 0; i < m.rows; i++) {
            temp.contents[i][i + m.cols] = 1;
        }

        int i2, j2;
        m = Gaussian.GaussJordan(temp);
        for (i2 = 0; i2 < inverseMat.rows; i2++) {
            for (j2 = 0; j2 < inverseMat.cols; j2++) {
                inverseMat.contents[i2][j2] = temp.contents[i2][j2 + inverseMat.cols];
            }
        }
        return inverseMat;
    }
}
