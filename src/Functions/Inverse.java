package src.Functions;

public class Inverse {

    // Mengembalikan inverse dari matriks dengan metode kofaktor
    // Prekondisi: matriks persegi
    public static Matrix adjoin(Matrix m) {
        Matrix adjointMat = new Matrix(m.rows, m.cols);
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

    // Mengembalikan inverse dari matriks dengan metode Gauss Jordan
    // Prekondisi: matriks persegi
    public static Matrix gaussJordan(Matrix m) {
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
        SPL.GaussJordan(m); 
        for (i2 = 0; i2 < m.rows; i2++) {
            for (j2 = 0; j2 < m.cols; j2++) {
                inverseMat.contents[i2][j2] = temp.contents[i2][j2 + m.cols];
            }
        }
        return inverseMat;
    }

}
