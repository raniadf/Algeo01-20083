package src.Functions;
import java.lang.Math;

import javax.swing.JOptionPane;

public class Determinant {

    /** EKSPANSI KOFAKTOR
     * Metode menghitung determinan matriks dengan ekspansi kofaktor
     * @param m matriks yang ingin dihitung determinannya
     * @return nilai determinan dari matriks m, NaN jika m bukan matriks persegi
     */
    public static double cofExp(Matrix m) {
        double det = 0.0;
        Matrix minor = new Matrix(m.rows - 1, m.cols - 1);

        if (!Func.isSquare(m)) {
            JOptionPane.showMessageDialog(null, "Determinan tidak dapat dihitung karena bukan matriks persegi.", 
                                            "GAGAL MENGHITUNG DETERMINAN MATRIKS", JOptionPane.WARNING_MESSAGE);
            return Double.NaN;
        }

        // Basis
        if (Func.nbElmt(m) == 1) {
            return Func.getElmt(m, 0, 0);
        } else if (m.rows == 2 && m.cols == 2) {
            return ((Func.getElmt(m, 0, 0) * Func.getElmt(m, 1, 1)) -
                    (Func.getElmt(m, 0, 1) * Func.getElmt(m, 1, 0)));   
        }

        // Rekurens
        // Baris yang digunakan adalah baris pertama
        // j sebagai penanda kolom acuan
        int j;
        for (j = 0; j < m.cols; ++j) {
            minor = Func.makeMinor(m, 0, j);

            det += ((j % 2 == 0 ? 1 : ~0) *
                    Func.getElmt(m, 0, j) *
                cofExp(minor));
        }
        return det;
    }

    /** REDUKSI BARIS
     * Metode menghitung determinan matriks dengan reduksi baris pada matriks m
     * @param m matriks yang ingin dihitung determinannya
     * @return nilai determinan dari matriks m, NaN jika m bukan matriks persegi
     */
    public static double rowRed(Matrix m) {
        double det = 1.0;
        int switchCount;
        Matrix tempMat;

        tempMat = Func.copyMatrix(m);

        if (!Func.isSquare(m)) {
            JOptionPane.showMessageDialog(null, "Determinan tidak dapat dihitung karena bukan matriks persegi.", 
                                            "GAGAL MENGHITUNG DETERMINAN MATRIKS", JOptionPane.WARNING_MESSAGE);
            return Double.NaN;
        }

        int i;
        switchCount = Func.makeSgtgAtas(tempMat);
        for (i = 0; i < tempMat.rows; ++i) {
            det *= Func.getElmt(tempMat, i, i);
        }
        
        det *= Math.pow(-1, switchCount);
        return det;
    }
        /*
        // validasi matriks persegi
        if (!Func.isSquare(m)) {
            JOptionPane.showMessageDialog(null, "Determinan tidak dapat dihitung karena bukan matriks persegi.", 
                                            "GAGAL MENGHITUNG DETERMINAN MATRIKS", JOptionPane.WARNING_MESSAGE);
            return Double.NaN;
        }

        int i = 0;
        int j;
        int k;    // variable yang digunakan untuk mengecek baris setelahnya
        double koef;
        boolean flag;
        double switchCount = 1.0;
        Matrix tempMat;
        
        tempMat = Func.copyMatrix(m);

        // perulangan dari baris pertama-terakhir dan kolom pertama-sebelum terakhir
        for (j = 0; ((i <= Func.getLastIdxRow(tempMat)) && (j < Func.getLastIdxCol(tempMat))); j++){
               
            boolean NextProcess = true;        // indikator untuk lanjut ke proses berikutnya
               
            if (Func.getElmt(tempMat, i, j) == 0){

                k = i + 1;
                flag = false;
                while ((!flag) && (k <= Func.getLastIdxRow(tempMat))){
                    // lakukan perulangan sampai ditemukan elemen kolom j yang != 0
                    if (Func.getElmt(tempMat, k, j) != 0){
                        flag = true;
                    } 
                    else {
                        k++;
                    }
                }

                // ketika ditemukan elemen != 0 di baris k, maka dilakukan pertukaran
                if (flag){
                    Func.switchOBE(tempMat, i, k);
                    switchCount *= (-1);
                } 
                else {
                    NextProcess = false;
                }
            }

            if (NextProcess){
                // proses pembuatan segitiga atas
                switchCount *= (Func.getElmt(tempMat, i, j));
                Func.divideOBE(tempMat, i, Func.getElmt(tempMat, i, j));
                for (k = i + 1; k <= Func.getLastIdxRow(tempMat); k++){
                    koef = -(Func.getElmt(tempMat, k, j) / Func.getElmt(tempMat, i, j));
                    Func.addOBE(tempMat, i, k, koef);
                }
                i++;
            }  
        }

        double det = Func.getElmt(tempMat, 0, 0);

        int x;
        for (x = 1; x <= Func.getLastIdxRow(tempMat); x++){
            det *= Func.getElmt(tempMat, x, x);
        }

        return (det * switchCount);
    */

}
