package src.Functions;
import java.text.DecimalFormat;
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
        double tot = 0;
        int i, j;
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
        px = SPL.Gauss(px);
        double[] result = SPL.UniqueSPL(px);
        DecimalFormat df = new DecimalFormat("####0.0000");
        System.out.print("p" + Func.getLastIdxRow(m) + "(x) = ");
        String equation = ("p" + String.valueOf(Func.getLastIdxRow(m)) + "(x) = ");
        for (i = 0; i < result.length; i++){
            if (i == 0){
                System.out.print(df.format(result[i]) + " + ");
                equation += df.format(result[i]) + " + ";
            }
            else if (i == result.length - 1){
                System.out.print(df.format(result[i]) + " x^" + i);
                equation += df.format(result[i]) + " x^" + i;
            }
            else{
                System.out.print(df.format(result[i]) + " x^" + i + " + ");
                equation += df.format(result[i]) + " x^" + i + " + ";
            }
            tot += result[i] * Math.pow(x, i);
        }
        System.out.println("\n");
        String strapprox = df.format(tot);
        System.out.println("Hasil penaksiran: " + strapprox);

        // Return array of string (for writing to file) 
        String[] strresult = new String[2];
        strresult[0] = equation;
        strresult[1] = strapprox;
        return strresult;
    }
}