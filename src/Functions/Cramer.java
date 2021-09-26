package src.Functions;

public class Cramer {
    // belom bisa dipakekkk
    // tinggal fill in the comments aja... kalo gaada yg salah XD LOL bau2nya ada si LOL XD
    public static void solveSPL(Matrix m){
        if (m.rows != m.cols - 1){
            System.out.println("Dibutuhkan " + (m.cols - 1) + " buah persamaan untuk " + (m.cols - 1) + " buah variabel");
            return;
        }
        Matrix A = new Matrix(m.rows, m.cols - 1);
        Matrix b = new Matrix(m.rows, 1);
        Matrix temp = new Matrix(m.rows, m.cols - 1);

        int i, j;
        for (i = 0; i <= Func.getLastIdxRow(A); i++){
            for (j = 0; j <= Func.getLastIdxCol(A); j++){
                Func.setElmt(A, i, j, Func.getElmt(m, i, j));
            }
        }
        for (i = 0; i <= Func.getLastIdxRow(b); i++){
                Func.setElmt(b, i, 0, Func.getElmt(m, i, Func.getLastIdxCol(m)));
        }

        // detA = Determinant(A) cari determinan A dulu
        for (j = 0; j <= Func.getLastIdxCol(A); j++){
            temp = A;
            for (i = 0; i <= Func.getLastIdxRow(A); i++){
                Func.setElmt(temp, i, j, Func.getElmt(b, i, 0));
            }
            // detTemp = Determinant(temp) cari determinannya
            //System.out.print("x" + (j+1) + " = " + (detTemp/detA) + "\n");
        }
    }
}