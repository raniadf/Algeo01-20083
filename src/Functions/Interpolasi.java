package src.Functions;
import java.text.DecimalFormat;
import java.lang.Math;

public class Interpolasi {
    // belom bisa dipakekkk DAN MASIH KASAR BAGNETNGETNGET
    // tinggal fill in the comments aja... kalo gaada yg salah XD LOL bau2nya ada si LOL XD
    public static double solveInterpolasi(Matrix m, double x){
        Matrix p2x = new Matrix(m.rows, 4);
        double tot = 0; // INI GANTI!!!!
        int i, j;

        for (i = 0; i < p2x.rows; i++){
            for (j = 0; j < p2x.cols; j++){
                if (j == 0){
                    Func.setElmt(p2x, i, j, 1);
                }
                else if (j == 1){
                    Func.setElmt(p2x, i, j, Func.getElmt(m, i, 0));
                }
                else if (j == 2){
                    Func.setElmt(p2x, i, j, Math.pow(Func.getElmt(m, i, 0), 2));
                }
                else{
                    Func.setElmt(p2x, i, j, Func.getElmt(m, i, 1));
                }
            }
        }
        // lakuin solveSPLGauss ke matrixnya
        // hasil SPLGaussnya disimpen di array double
        p2x = SPL.Gauss(p2x);
        double[] result = SPL.UniqueSPL(p2x);
        for (i = 0; i < result.length - 1; i++){
            tot += result[i] * Math.pow(x, i);
        }
        DecimalFormat df = new DecimalFormat("####0.00");
        System.out.println("Hasil penaksiran: " + df.format(tot));
        return tot;
    }
}
