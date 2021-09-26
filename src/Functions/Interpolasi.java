package src.Functions;
// import java.lang.Math;

public class Interpolasi {
    // belom bisa dipakekkk DAN MASIH KASAR BAGNETNGETNGET
    // tinggal fill in the comments aja... kalo gaada yg salah XD LOL bau2nya ada si LOL XD
    public static void solveInterpolasi(Matrix m, double x){
        if (m.rows != m.cols - 1){
            System.out.println("Dibutuhkan " + (m.cols - 1) + " buah persamaan untuk " + (m.cols - 1) + " buah variabel");
            return;
        }
        // lakuin solveSPLGauss ke matrixnya
        // hasil SPLGaussnya disimpen di array double
        int i, tot = 0;
        for (i = 0; i <= Func.getLastIdxRow(m); i++){
            // tot += hasil[i] * Math.pow(x, i);
        }
        System.out.println(tot);
    }
}
