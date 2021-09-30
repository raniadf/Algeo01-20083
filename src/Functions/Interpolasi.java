package src.Functions;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

import java.lang.Math;

public class Interpolasi {

    /** SOLVE INTERPOLASI
     * Menaksir nilai y di sembarang titik dengan menentukan polinom pn(x) dari n+1 buah titik
     * @param m matriks yang berisi titik-titik
     * @param x titik yang nilai fungsinya akan ditaksir
     * @return nilai y di titik x
     */
    public static String[] solveInterpolasi(Matrix m, double x){
        Matrix px = new Matrix(m.rows, m.rows + 1);
        String[] strresult = new String[2];
        double tot = 0;
        int i, j;

        if (m.cols != 2){
            JOptionPane.showMessageDialog(null, "Data kurang atau tidak valid, coba lagi!", "Uh Oh...", JOptionPane.WARNING_MESSAGE);
            return strresult;
        }

        // Membuat matrix baru berisi sistem persamaan lanjar
        for (i = 0; i <= Func.getLastIdxRow(px); i++){
            for (j = 0; j <= Func.getLastIdxCol(px); j++){
                if (j == 0){
                    Func.setElmt(px, i, j, 1);
                }
                else if (j == Func.getLastIdxCol(px)){
                    Func.setElmt(px, i, j, Func.getElmt(m, i, 1));
                }
                else{
                    Func.setElmt(px, i, j, Math.pow(Func.getElmt(m, i, 0), j));
                }
            }
        }
        // Menyelesaikan sistem persamaan dengan metode eliminasi Gauss
        px = Gaussian.Gauss(px);
        double[] result = Gaussian.UniqueSPL(px);

        String temp = "<html><center>";
        DecimalFormat df = new DecimalFormat("####0.0000");
        String equation = ("<html><center>p" + String.valueOf(Func.getLastIdxRow(m)) + "(x) = ");
        for (i = 0; i < result.length; i++){
            if (i == 0){
                equation += df.format(result[i]) + " + ";
            }
            else if (i == result.length - 1){
                equation += df.format(result[i]) + " x^" + i;
            }
            else{
                equation += df.format(result[i]) + " x^" + i + " + ";
            }
            tot += result[i] * Math.pow(x, i);
        }
        String strapprox = "Hasil penaksiran: " + df.format(tot);
        temp += equation + "<br>" + strapprox;
        JOptionPane.showMessageDialog(null, temp, "Hasilnya Nih :V", JOptionPane.PLAIN_MESSAGE);

        // Return array of string (for writing to file) 
        strresult[0] = equation;
        strresult[1] = strapprox;
        return strresult;
    }
}