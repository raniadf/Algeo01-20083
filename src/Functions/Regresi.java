package src.Functions;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

public class Regresi {
    /** SOLVE REGRESI
     * Memprediksi nilai y dengan menggunakan regresi linear
     * @param m matrix augmented persamaan linear
     * @return nilai y di titik x
     */
    public static String[] solveRegresi(Matrix m){
        String[] strresult = new String[2];
        int n = m.rows;
        int k = m.cols; 
        int i, j, par;

        // Menyimpan user input berisi estimasi nilai peubah dalam sebuah array double
        double[] var = new double[k - 1];
        String str;
        for (j = 0; j < k - 1; j++){
            while(true){
                str = JOptionPane.showInputDialog(null, "Masukkan estimasi nilai x" + (j+1) + ": ", "Regresi Linear Berganda", JOptionPane.PLAIN_MESSAGE);
                if (str == null){
                    return strresult;
                }
                else if (str.matches("[0-9.]*") && !str.contains(" ")){
                    break;
                }
            }
            var[j] = Double.parseDouble(str);
        }

        // Normal Estimation Equation for Multiple Linear Regression
        Matrix b = new Matrix(k, 1);
        Matrix a = new Matrix(k, k);

        for (i = 0; i <= Func.getLastIdxRow(b); i++){
            for (j = 0; j < n; j++){
                if (i == 0){
                    double temp = Func.getElmt(b, i, 0) + Func.getElmt(m, j, Func.getLastIdxCol(m));
                    Func.setElmt(b, i, 0, temp);
                }
                else{
                    double temp = Func.getElmt(b, i, 0) + (Func.getElmt(m, j, Func.getLastIdxCol(m)) * Func.getElmt(m, j, i - 1));
                    Func.setElmt(b, i, 0, temp);
                }
            }
        }

        for (i = 0; i <= Func.getLastIdxRow(a); i++){
            for (j = 0; j <= Func.getLastIdxCol(a); j++){
                if (i == 0){
                    if (j == 0){
                        Func.setElmt(a, i, j, n);
                    }
                    else {
                        for (par = 0; par < n; par++){
                            double temp = Func.getElmt(a, i, j) + Func.getElmt(m, par, j - 1);
                            Func.setElmt(a, i, j, temp);
                        }
                    }
                }
                else {
                    for (par = 0; par < n; par++){
                        if (j == 0){
                            double temp = Func.getElmt(a, i, j) + Func.getElmt(m, par, i - 1);
                            Func.setElmt(a, i, j, temp);
                        }
                        else{
                            double temp = Func.getElmt(a, i, j) + (Func.getElmt(m, par, i - 1) * Func.getElmt(m, par, j - 1));
                            Func.setElmt(a, i, j, temp);
                        }
                    }
                }
            }
        }

        Matrix ab = new Matrix(a.rows, a.cols + 1);
        for (i = 0; i <= Func.getLastIdxRow(ab); i++){
            for (j = 0; j <= Func.getLastIdxCol(ab); j++){
                if (j == Func.getLastIdxCol(ab)){
                    Func.setElmt(ab, i, Func.getLastIdxCol(ab), Func.getElmt(b, i, 0));
                }
                else{
                    Func.setElmt(ab, i, j, Func.getElmt(a, i, j));
                }
            }
        }

        // Menyimpan hasil Eliminasi Gauss pada matrix ab dalam array double
        double result[] = new double[ab.cols];
        ab = Gaussian.Gauss(ab);
        result = Gaussian.UniqueSPL(ab);

        // Print general equation
        DecimalFormat df = new DecimalFormat("####0.000000");
        String temp = "<html><center>";
        String equation = "y =";
        for (i = 0; i < result.length; i++){
            if (i == 0){
                equation += df.format(result[i]) + " ";
            }
            else{
                equation += "+ " + df.format(result[i]) + " x^" + (i + 1) + " ";
            }
        }
        
        // Print end result
        double approx = 0;
        String strapprox = "";
        for (i = 0; i < result.length; i++){
            if (i == 0){
                approx += result[i];
            }
            else{
                approx += result[i] * var[i - 1];
            }
        }
        strapprox = "Hasil penaksiran: " + df.format(approx);
        temp += equation + "<br>" + strapprox;
        JOptionPane.showMessageDialog(null, temp, "Hasilnya Nih :V", JOptionPane.PLAIN_MESSAGE);

        // Return array of string (for writing to file) 
        strresult[0] = equation;
        strresult[1] = strapprox;
        return strresult;
    }
}
