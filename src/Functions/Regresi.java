package src.Functions;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

public class Regresi {
    public static double solveRegresi(Matrix m){
        int n = m.rows;
        int k = m.cols; 
        int i, j, par;

        // Store user input of values to be estimated in an array of double
        double[] tempVar = new double[k - 1];
        String str;
        JOptionPane.showMessageDialog(null, "Masukkan nilai yang akan ditaksir!", "Regresi Linear Berganda", JOptionPane.QUESTION_MESSAGE);
        for (j = 0; j < k - 1; j++){
            while(true){
                str = JOptionPane.showInputDialog(null, "Enter x" + (j+1) + ": ", "Regresi Linear Berganda", JOptionPane.PLAIN_MESSAGE);
                if (str == null){
                    Interface.user();
                }
                else if (str.matches("[0-9.]*") && !str.contains(" ")){
                    break;
                }
            }
            tempVar[j] = Double.parseDouble(str);
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

        // Store values found through Gauss Elimination in array of double
        double result[] = new double[ab.cols];
        ab = SPL.Gauss(ab);
        result = SPL.UniqueSPL(ab);

        // Print general equation
        System.out.print("y = ");
        for (i = 0; i < result.length - 1; i++){
            if (i == 0){
                System.out.print(result[i] + " ");
            }
            else{
                System.out.print("+ " + result[i] + "x" + (i + 1) + " ");
            }
        }
        
        // Print end result
        System.out.print("\n");
        double approx = 0;
        for (i = 0; i < result.length - 1; i++){
            if (i == 0){
                approx += result[i];
            }
            else{
                approx += result[i] * tempVar[i - 1];
            }
        }
        DecimalFormat df = new DecimalFormat("####0.00");
        System.out.println("Hasil penaksiran: " + df.format(approx));
        return approx;
    }
}
