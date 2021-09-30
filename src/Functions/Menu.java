package src.Functions;
import javax.swing.*;

public class Menu {
    public static void user(){
        String opt = "0";
        int option = Integer.parseInt(opt);
        while ((opt == "" || opt != null) && option != 1 && option != 2 && option != 3 && option != 4 && option != 5 && option != 6){
            opt = JOptionPane.showInputDialog(null, "KUMOL MATRIX CALC :V \n 1. Sistem Persamaan Linear \n 2. Determinan \n 3. Matriks Balikan \n 4. Interpolasi Polinom \n 5. Regresi Linear Berganda \n 6. Keluar \n Ketik angka: ", "Main Menu", JOptionPane.INFORMATION_MESSAGE);
            option = validasiParse(opt, option);
        }

        if (option == 6 || opt == null) System.exit(0);

        String mat = "0";
        int matrix = Integer.parseInt(mat);
        while (matrix != 1 && matrix != 2 && (mat != null || mat == "")){
            mat = JOptionPane.showInputDialog(null, "Pake file atau input manual? \n 1. File \n 2. Input manual");
            matrix = validasiParse(mat, matrix);
        }
        if (mat == null) return;
        
        Matrix m;
        if (matrix == 1) m = Func.readMatrix();
        else m = Func.inputMatrix(option);
        if (m.rows == 0 && m.cols == 0) return;

        String opt2 = "0";
        double resultDet = Double.NaN;
        int option2 = Integer.parseInt(opt2);
        String[] resultApprox = new String[2];
        Matrix mnew = new Matrix(m.rows + 1, m.cols);

        switch (option){
        case 1:
            while (option2 != 1 && option2 != 2 && option2 != 3 && option2 != 4 && (opt2 != null || opt2 == "")){ 
                opt2 = JOptionPane.showInputDialog(null, "SPL pake cara apa nih? \n 1. Metode Eliminasi Gauss \n 2. Metode Eliminasi Gauss-Jordan \n 3. Metode Matriks Balikan \n 4. Kaidah Cramer \n");
                option2 = validasiParse(opt2, option2);
            }
            if (opt2 == null) return;
            switch (option2){
            case 1:
                resultApprox = Gaussian.SolveSPL(m, "Gauss");
                break;
            case 2:
                resultApprox = Gaussian.SolveSPL(m, "Gauss Jordan");
                break;
            case 3:
                resultApprox = Invers.solveSPL(m);
                break;
            case 4:
                resultApprox = Cramer.solveSPL(m);
                break;
            }
            break;

        case 2:
            while (option2 != 1 && option2 != 2 && (opt2 != null || opt2 == "")){
                opt2 = JOptionPane.showInputDialog(null, "Determinan pake cara apa nih? \n 1. Reduksi Baris \n 2. Ekspansi Kofaktor");
                option2 = validasiParse(opt2, option2);
            }
            if (opt2 == null) return;
            switch (option2){
            case 1:
                resultDet = Determinant.rowRed(m);
                break;
            case 2:
                resultDet = Determinant.cofExp(m);
                break;
            }
            if (resultDet == Double.NaN) m = new Matrix(0, 0);
            else {
                m = new Matrix(1, 1);
                Func.setElmt(m, 0, 0, resultDet);
            }
            break;

        case 3:
            while (option2 != 1 && option2 != 2 && (opt2 != null || opt2 == "")){ 
                opt2 = JOptionPane.showInputDialog(null, "Invers pake cara apa nih? \n 1. Gauss-Jordan \n 2. Adjoin");
                option2 = validasiParse(opt2, option2);
            }
            if (opt2 == null) return;
            switch (option2){
            case 1:
                mnew = Invers.gaussJordan(m);
                break;
            case 2:
                mnew = Invers.adjoint(m);
                break;
            }
            break;

        case 4:
            String x;
            while(true){
                x = JOptionPane.showInputDialog(null, "Masukkan x yang akan ditaksir:");
                if (x == null || x.matches("[0-9.-]*")){
                    break;
                }
            }
            if (x == null) return;
            resultApprox = Interpolasi.solveInterpolasi(m, Double.parseDouble(x));
            break;

        case 5:
            resultApprox = Regresi.solveRegresi(m);
            break;
        }

        if (resultApprox == null || mnew.rows == m.rows + 1 || resultDet == Double.NaN){
            return;
        }

        if (option == 2){
            String temp = "<html><center>Hasil determinannya adalah: " + resultDet;
            JOptionPane.showMessageDialog(null, temp, "Hasilnya Nih :V", JOptionPane.PLAIN_MESSAGE);
        }
        else if (option == 3) Func.displayMatrix(mnew);

        int save = JOptionPane.showConfirmDialog(null, "Mau disave jadi file txt ga hasilnya?\n\n(hasil akhir akan berupa [input].txt)", "Save File", JOptionPane.YES_NO_OPTION);
        if (save == JOptionPane.YES_OPTION){
            if (option == 1|| option == 4 || option == 5){
                Func.writeMatrix(resultApprox);
            }
            else{
                Func.writeMatrix(mnew);
            }
        } else{
            return;
        }
    }

    public static int validasiParse(String opt, int option){
        if (opt != null){ 
            if (opt.matches("[0-9]+") && !opt.contains(" ")){
                if (!opt.isEmpty()){
                    option = Integer.parseInt(opt);
                }
            }
        }
        return option;
    }
}
