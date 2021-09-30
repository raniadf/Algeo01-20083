package src.Functions;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.FileReader;
import java.lang.String;



public class Func{
    /* ===== READ-WRITE FILES & DISPLAY RESULT ===== */
    /** INPUT MATRIX
     * Menerima input dari user dan mengembalikan matriks sesuai kebutuhan user
     * @param inputType input dari user berupa string yang diterima melalui interface
     * @return matriks dengan ukuran dan isi elemen sesuai input dari user
     */
    public static Matrix inputMatrix(int inputType){
        int m, n;
        String strm, strn;
        Matrix fail = new Matrix(0, 0);

        if (inputType == 1){
            while(true){
                strm = JOptionPane.showInputDialog(null, "Masukkan jumlah baris: ", "Input Matrix", JOptionPane.PLAIN_MESSAGE);
                if (strm == null) return fail;
                else if (!strm.matches("[0-9]*") || strm.contains(" ")) continue;
                
                strn = JOptionPane.showInputDialog(null, "Masukkan jumlah kolom: ", "Input Matrix", JOptionPane.PLAIN_MESSAGE);
                if (strn == null) return fail;
                else if (!strm.matches("[0-9]*") || strm.contains(" ")) continue;
                else break;
            }
            m = Integer.parseInt(strm);
            n = Integer.parseInt(strn);
        }
        else if (inputType == 2 || inputType == 3){
            while(true){
                strm = JOptionPane.showInputDialog(null, "Masukkan n (ukuran matrix n x n): ", "Input Matrix", JOptionPane.PLAIN_MESSAGE);
                if (strm == null) return fail;
                else if (!strm.matches("[0-9]*") || strm.contains(" ")) continue;
                else break;
            }
            m = Integer.parseInt(strm);
            n = m;
        }
        else if (inputType == 4){
            while(true){
                strm = JOptionPane.showInputDialog(null, "Masukkan derajat polinomial: ", "Input Matrix", JOptionPane.PLAIN_MESSAGE);
                if (strm == null) return fail;
                else if (!strm.matches("[0-9]*") || strm.contains(" ")) continue;
                else break;
            }
            m = Integer.parseInt(strm) + 1;
            n = 2;
        }
        else{
            while(true){
                strm = JOptionPane.showInputDialog(null, "Masukkan banyak data: ", "Input Matrix", JOptionPane.PLAIN_MESSAGE);
                if (strm == null) return fail;
                else if (!strm.matches("[0-9]*") || strm.contains(" ")) continue;
                
                strn = JOptionPane.showInputDialog(null, "Masukkan banyak variabel peubah: ", "Input Matrix", JOptionPane.PLAIN_MESSAGE);
                if (strn == null) return fail;
                else if (!strm.matches("[0-9]*") || strm.contains(" ")) continue;
                else break;
            }
            m = Integer.parseInt(strm);
            n = Integer.parseInt(strn);
        }

        Matrix matrix = new Matrix(m, n);
        String str;
        int i, j;
        for (i = 0; i < m; i++){
            for (j = 0; j < n; j++){
                while(true){
                    if (inputType == 5){
                        if (j == getLastIdxCol(matrix)){
                            str = JOptionPane.showInputDialog(null, "Data ke-" + (i+1) + "\nMasukkan y: ", "Input Matrix", JOptionPane.PLAIN_MESSAGE); 
                        }
                        else{
                            str = JOptionPane.showInputDialog(null, "Data ke-" + (i+1) + "\nMasukkan x" + (j+1) + ": ", "Input Matrix", JOptionPane.PLAIN_MESSAGE);
                        }

                        if (str == null) return fail;
                        else if (str.matches("[0-9.-]*") && !str.contains(" ")) break;
                    }
                    else if (inputType == 4){
                        if (j == 0){
                            str = JOptionPane.showInputDialog(null, "Masukkan x" + j + ": ", "Input Matrix", JOptionPane.PLAIN_MESSAGE);
                        }
                        else{
                            str = JOptionPane.showInputDialog(null, "Masukkan y" + j + ": ", "Input Matrix", JOptionPane.PLAIN_MESSAGE);
                        }

                        if (str == null) return fail;
                        else if (str.matches("[0-9.-]*") && !str.contains(" ")) break;
                    }
                    else{
                        str = JOptionPane.showInputDialog(null, "Masukkan elemen matrix pada posisi (" + (i+1) + ", " + (j+1) + "): ", "Input Matrix", JOptionPane.PLAIN_MESSAGE);
                        if (str == null) return fail;
                        else if (str.matches("[0-9.-]*") && !str.contains(" ")) break;
                    }
                }
                double elmt = Double.parseDouble(str);
                setElmt(matrix, i, j, elmt);
            }
        }
        return matrix;
    }

    /** READ MATRIX
     * Membaca file .txt dengan nama sesuai input user
     * @return matriks yang diterima dari isi file .txt
     */
    public static Matrix readMatrix(){
        String filename = JOptionPane.showInputDialog(null, "Masukkan nama file:", "Read File", JOptionPane.PLAIN_MESSAGE);
        try {
            ArrayList<String> num = new ArrayList<String>();
            int rowcount = 0, colcount = 0;
            String filePath = ".\\test\\" + filename + ".txt";
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String rl = br.readLine();
            while (rl != null){
                String[] rls = rl.split(" ");
                for (String x : rls){
                    num.add(x);
                }
                rowcount++;
                colcount = rls.length;
                rl = br.readLine();
            }
            br.close();
            Matrix m = new Matrix(rowcount, colcount);
            int i, j, par = 0;
            for (i = 0; i < rowcount; i++){
                for (j = 0; j < colcount; j++){
                    setElmt(m, i, j, Double.parseDouble(num.get(par)));
                    par++;
                }
            }
            return m;
        }
        catch (Exception ie) {
            JOptionPane.showMessageDialog(null, "Yah, filenya gabisa dibaca!", "Uh-Oh...", JOptionPane.ERROR_MESSAGE);
            //ie.printStackTrace();
            Matrix m = new Matrix(0,0);
            return m;
        }
    }

    /** WRITE MATRIX
     * Menulis dan menyimpan matriks yang diterima dari file .txt
     * @param m matriks yang ingin ditulis dan disimpan
     */
    public static void writeMatrix(Matrix m){
        String filename = JOptionPane.showInputDialog(null, "Simpan file dengan nama ______.txt", JOptionPane.PLAIN_MESSAGE);
        DecimalFormat df = new DecimalFormat("####0.00");
        try {
            FileWriter writer = new FileWriter(".\\test\\" + filename + ".txt");
            int i, j;
            for (i = 0; i <= getLastIdxRow(m); i++){
                for (j = 0; j <= getLastIdxCol(m); j++) {
                    if (j != getLastIdxCol(m)){
                        writer.write(df.format(getElmt(m, i, j)) + " ");
                    }
                    else{
                        writer.write(String.valueOf(df.format(getElmt(m, i, j))));
                    }
                }
                writer.write("\n");
            }        
            writer.close();
            JOptionPane.showMessageDialog(null, "Save berhasil!", "File Saved", JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Yah, error! Maaf ya :(", "Uh-Oh...", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    public static void writeMatrix(String[] m){
        String filename = JOptionPane.showInputDialog(null, "Simpan file dengan nama ______.txt", JOptionPane.PLAIN_MESSAGE);
        try {
            FileWriter writer = new FileWriter(".\\test\\" + filename + ".txt");
            int i;
            for (i = 0; i < m.length; i++){
                writer.write(m[i]);
                writer.write("\n");
            }
            writer.close();
            JOptionPane.showMessageDialog(null, "Save berhasil!", "File Saved", JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Yah, error! Maaf ya :(", "Uh-Oh...", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /** DISPLAY MATRIX
     * Menampilkan setiap elemen matriks pada terminal
     * @param m matriks yang ingin ditampilkan pada terminal
     */
    public static void displayMatrix(Matrix m){
        DecimalFormat df = new DecimalFormat("####0.00");
        String temp = "<html><center>";
        int i, j;
        for (i = 0; i <= getLastIdxRow(m); i++){
            for (j = 0; j <= getLastIdxCol(m); j++) {
                if (j != getLastIdxCol(m)){
                    temp += df.format(getElmt(m, i, j)) + " ";
                }
                else{
                    temp += df.format(getElmt(m, i, j));
                }
            }
            temp += "<br>";
        }
        JLabel label = new JLabel(temp);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        JOptionPane.showMessageDialog(null, label, "Hasilnya Nih :V", JOptionPane.PLAIN_MESSAGE);
    }

    /* ===== SELEKTOR: GET & SET ===== */
    /** GET ELEMENT OF MATRIX
     * Mengakses elemen pada baris i dan kolom j dari matriks
     * @param m matriks yang ingin diakses elemennya
     * @param i indeks baris pada matriks
     * @param j indeks kolom pada matriks
     * @return double, elemen matriks yang berada pada baris i dan kolom j
     */
    public static double getElmt(Matrix m, int i, int j){
        return m.contents[i][j];
    }

    /** SET ELEMENT OF MATRIX
     * Mengisi matriks baris i dan kolom j dengan elemen baru
     * @param m matriks yang ingin diisi elemennya
     * @param i indeks baris pada matriks
     * @param j indeks kolom pada matriks
     */
    public static void setElmt(Matrix m, int i, int j, double fill){
        m.contents[i][j] = fill;
    }

    public static int getLastIdxRow(Matrix m){
        return m.rows - 1;
    }

    public static int getLastIdxCol(Matrix m){
        return m.cols - 1;
    }

    /* OPERASI BARIS ELEMENTER */
    // parameter row, BUKAN idx row
    public static void multiplyOBE(Matrix m, int row, double multiplier){
        int j;
        for (j = 0; j <= getLastIdxCol(m); j++){
            double newElmt = getElmt(m, row - 1, j) * multiplier;
            setElmt(m, row - 1, j, newElmt);
        }
    }
    
    public static void divideOBE(Matrix m, int row, double divider) {
        int j;
        for (j = 0; j <= getLastIdxCol(m); j++) {
            double newElmt = getElmt(m, row - 1, j) / divider;
            setElmt(m, row - 1, j, newElmt);
        }
    }

    public static void switchOBE(Matrix m, int row1, int row2){
        double[] temp = m.contents[row1 - 1];
        m.contents[row1 - 1] = m.contents[row2 - 1];
        m.contents[row2 - 1] = temp;
    }

    public static void addOBE(Matrix m, int row1, int row2, double multiplier){
        int j;
        double addedVal;
        for (j = 0; j <= getLastIdxCol(m); j++){
            addedVal = getElmt(m, row2 - 1, j) * multiplier;
            setElmt(m, row1 - 1, j, getElmt(m, row1 - 1, j) + addedVal);
        }
    }

    /* VALIDASI */
    public static boolean isSquare(Matrix m){
        return (m.rows == m.cols);
    }

    /* MANIPULASI MATRIX LAINNYA */
    public static Matrix copyMatrix(Matrix m){
        Matrix copy = new Matrix(m.rows, m.cols);
        int i, j;
        for (i = 0; i < m.rows; i++){
            for (j = 0; j < m.cols; j++){
                setElmt(copy, i, j, getElmt(m, i, j));
            }
        }
        return copy;
    }

    public static Matrix multiply(Matrix m1, Matrix m2){
        Matrix m3 = new Matrix(m1.rows, m2.cols);
        for (int i = 0; i <= getLastIdxRow(m1); i++){
            for (int j = 0; j <= getLastIdxCol(m2); j++){
                for (int k = 0; k <= getLastIdxCol(m1); k++){
                    m3.contents[i][j] += (m1.contents[i][k] * m2.contents[k][j]);
                }
            }
        }
        return m3;
    }

    /* FUNGSI-FUNGSI HELPER LAINNYA */
    /**
     * Mengembalikan banyaknya elemen pada matriks
     * */
    public static int nbElmt(Matrix m){
        return (m.rows * m.cols);
    }

    /**
     * Mengembalikan matriks minor
     * */
    public static Matrix makeMinor(Matrix m, int rowAcuan, int colAcuan){
        Matrix minor;
        int x, y, i, j;
        minor = new Matrix(m.rows - 1, m.cols - 1);

        // i untuk baris m; j untuk baris m
        // x untuk baris minor; y untuk baris minor
        x = 0; y = 0;
        for (i = 0; i < m.rows; ++i) {
            if (i == rowAcuan) {
                // Elemen yang berada di baris rowAcuan di-skip
                continue;
            }
            for (j = 0; j < m.cols; ++j) {
                if (j == colAcuan) {
                    // Elemen yang berada di kolom colAcuan di-skip
                    continue;
                }

                setElmt(minor, x, y++, getElmt(m, i, j));
                if (y == minor.cols) {
                    y = 0;
                    x++;
                }
            }
        }
        return minor;
    }

    /**
     * Mengubah matriks pemanggil menjadi matriks segitiga atas
     * dan mengembalikan banyaknya proses pertukaran baris pada prosesnya
     * */
    public static int makeSgtgAtas(Matrix m) {
        int i, j, switchCount = 0;

        // i nandain baris yang sedang diproses
        for (i = 0; i < m.rows; i++) {
            double pivot = getElmt(m, i, i),
                   tempPivot = pivot, // Pivot yang digunakan ketika mencari pivot tidak 0
                   firstElmt, // Elemen pertama tiap baris
                   constant; // Konstanta pengali matriks

            // Mencari pivot sampai pivot tidak 0
            int z = i; // iterator pencarian pivot tidak 0
            for (; z < m.rows && tempPivot == 0; ++z) {
                tempPivot = getElmt(m, z, i);
            }

            // Jika setelah dicari, pivot masih 0, lewati baris yang sedang
            // diproses
            if (tempPivot == 0) {
                continue;
            // Jika ditemukan pivot tidak 0, tukar dengan baris yang sedang
            // diproses
            } else if (pivot == 0 && tempPivot != 0) {
                pivot = tempPivot;
                switchCount++;
                switchOBE(m, i + 1, --z + 1); // Baris yang ingin diproses = indeks + 1
            }

            // Setelah dikali pivot, 0-kan semua elemen yang sekolom dan di
            // bawah pivot
            for (j = i + 1; j < m.rows; ++j) {
                firstElmt = getElmt(m, j, i);
                constant = -1 * firstElmt / pivot;

                addOBE(m, j + 1, i + 1, constant); // Baris yang ingin diproses = indeks + 1
            }
        }

        return switchCount;
    }

    /**
     * Mengembalikan matriks transpose
     * */
    public static Matrix transpose(Matrix m) {
        int i, j;
        Matrix transposeMat = new Matrix(m.cols, m.rows);

        for (i = 0; i < m.rows; i++) {
            for (j = 0; j < m.cols; j++) {
                transposeMat.contents[j][i] = m.contents[i][j];
            }
        }
        return transposeMat;
    }

    /**
     * Mengembalikan matriks kofaktor
     * */
    public static Matrix makeCofactor(Matrix m) {
        int i, j, itemp, jtemp, tempRow, tempCol;
        Matrix cofactorMat = new Matrix(m.rows, m.cols);
        
        for (i = 0; i < m.rows; i++) {
            for (j = 0; j < m.cols; j++)
            {
                Matrix temp = new Matrix(m.rows - 1, m.cols - 1);
                tempRow = 0;
                for (itemp = 0; itemp < m.rows; itemp++)
                {
                    tempCol = 0;
                    for (jtemp = 0; jtemp < m.cols; jtemp++)
                    {
                        if (itemp != i && jtemp != j)
                        {
                            temp.contents[tempRow][tempCol] = m.contents[itemp][jtemp];
                            tempCol++;
                            if (tempCol == m.cols - 1) {
                                tempRow++;
                            }
                        }
                    }
                    
                }
                cofactorMat.contents[i][j] = Math.pow(-1, i + j) * 
                                             Determinant.cofExp(temp);
            }
        }
        return cofactorMat;
    }

    /**
     * Mengembalikan matriks dengan decimal 2 dan -0 diubah menjadi 0
     * */
    public static Matrix ZeroNRound(Matrix M) {
        for (int i = 0; i < M.rows; ++i) {
            for (int j = 0; j < M.cols; ++j) {
                if (Func.getElmt(M, i, j) == 0 || Func.getElmt(M, i, j) == (-0)) {
                    Func.setElmt(M, i, j, 0);
                } else {
                    DecimalFormat df = new DecimalFormat("######.####");
                    String formatted = df.format(Func.getElmt(M, i, j));
                    double d = Double.parseDouble(formatted);
                    Func.setElmt(M, i, j, d);
                }
            }
        }
        return M;
    }

    /**
     * Mengembalikan index dari leading one
     * */
    public static int findLeadingOne(Matrix M, int row) {
        // Set j for column loop
        int j;
        // Set idx to -1 (not found)
        int idx = -1;

        // Forward loop
        for (j = 0; j < M.cols; ++j) {
            // If found 1 -> idx = j
            if (Func.getElmt(M, row, j) == 1) {
                idx = j;
                break;
            }
        }

        return idx;
    }
}
