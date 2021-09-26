package src.Functions;
final public class Matrix {
    final double[][] contents;
    final int rows;
    final int cols;

    public Matrix(int nRow, int nCol){
        this.contents = new double[nRow][nCol];
        this.cols = nCol;
        this.rows = nRow;
        int i, j;
        for (i = 0; i <= nRow - 1; i++) {
            for (j = 0; j <= nCol - 1; j++) {
                this.contents[i][j] = 0;
            }
        }
    }
}
