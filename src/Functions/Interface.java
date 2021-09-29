package src.Functions;
import javax.swing.*;

public class Interface {
    public static void user(){
        String opt = "0";
        int option = Integer.parseInt(opt);
        while ((opt == "" || opt != null) && option != 1 && option != 2 && option != 3 && option != 4 && option != 5 && option != 6){
            opt = JOptionPane.showInputDialog(null, "KUMOL MATRIX CALC :V \n 1. Sistem Persamaan Linear \n 2. Determinan \n 3. Matriks Balikan \n 4. Interpolasi Polinom \n 5. Regresi Linear Berganda \n 6. Keluar \n Ketik angka: ", "Main Menu", JOptionPane.INFORMATION_MESSAGE);
            option = validasiParse(opt, option);
        }

        if (option == 6 || opt == null){
            System.exit(0);
        }

        String mat = "0";
        int matrix = Integer.parseInt(mat);
        while (matrix != 1 && matrix != 2 && (mat != null || mat == "")){
            mat = JOptionPane.showInputDialog(null, "Pake file atau input manual? \n 1. File \n 2. Input manual");
            matrix = validasiParse(mat, matrix);
        }
        if (mat == null){
            return;
        }
        
        Matrix m;
        double result = 0;
        if (matrix == 1){
            m = Func.readMatrix();
        }
        else{
            if (option == 1){
                m = Func.inputMatrix("SPL");
            }
            else if (option == 2){
                m = Func.inputMatrix("Determinan");
            }
            else if (option == 3){
                m = Func.inputMatrix("Invers");
            }
            else if (option == 4){
                m = Func.inputMatrix("Interpolasi");
            }
            else{
                m = Func.inputMatrix("Regresi");
            }
        }

        switch (option){
        case 1:
            String optSPL = "0";
            double[] resultSPL;
            int optionSPL = Integer.parseInt(optSPL);
            while (optionSPL != 1 && optionSPL != 2 && optionSPL != 3 && optionSPL != 4 && (optSPL != null || optSPL == "")){ 
                optSPL = JOptionPane.showInputDialog(null, "SPL pake cara apa nih? \n 1. Metode Eliminasi Gauss \n 2. Metode Eliminasi Gauss-Jordan \n 3. Metode Matriks Balikan \n 4. Kaidah Cramer \n");
                optionSPL = validasiParse(optSPL, optionSPL);
            }
            if (optSPL == null){
                return;
            }
            switch (optionSPL){
            case 1:
                resultSPL = SPL.SolveSPL(m, "Gauss");
                break;
            case 2:
                resultSPL = SPL.SolveSPL(m, "Gauss Jordan");
                break;
            case 3:
                resultSPL = Cramer.solveSPL(m);// NANTI GANTI jadi result = Invers.solveSPL(m); 
                break;
            case 4:
                resultSPL = Cramer.solveSPL(m);
                break;
            default:
                resultSPL = Cramer.solveSPL(m); // ini cm biar initialized aja 
            }
            m = new Matrix(resultSPL.length, 1);
            int i;
            for (i = 0; i < resultSPL.length; i++){
                Func.setElmt(m, i, 0, resultSPL[i]);
            }
            break;

        case 2:
            String optDet = "0";
            int optionDet = Integer.parseInt(optDet);
            while (optionDet != 1 && optionDet != 2 && (optDet != null || optDet == "")){
                optDet = JOptionPane.showInputDialog(null, "Determinan pake cara apa nih? \n 1. Reduksi Baris \n 2. Ekspansi Kofaktor");
                optionDet = validasiParse(optDet, optionDet);
            }
            if (optDet == null){
                return;
            }
            switch (optionDet){
            case 1:
                result = Determinant.rowRed(m);
                break;
            case 2:
                result = Determinant.cofExp(m);
                break;
            }
            System.out.println("Determinan adalah: " + result);
            break;
        case 3:
            String optInv = "0";
            int optionInv = Integer.parseInt(optInv);
            while (optionInv != 1 && optionInv != 2 && (optInv != null || optInv == "")){ 
                optInv = JOptionPane.showInputDialog(null, "Invers pake cara apa nih? \n 1. Gauss-Jordan \n 2. Adjoin");
                optionInv = validasiParse(optInv, optionInv);
            }
            if (optInv == null){
                return;
            }
            switch (optionInv){
            case 1:
                m = Invers.gaussJordan(m);
                break;
            case 2:
                m = Invers.adjoin(m);
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
            if (x == null){
                return;
            }
            result = Interpolasi.solveInterpolasi(m, Double.parseDouble(x));
            break;
        case 5:
            result = Regresi.solveRegresi(m);
            break;
        }

        JOptionPane.showMessageDialog(null, "Hasil ada di command prompt!");
        int save = JOptionPane.YES_NO_OPTION;
        save = JOptionPane.showConfirmDialog(null, "Mau disave jadi file .txt ga hasilnya?", "Save File", save);
        if (save == JOptionPane.YES_OPTION){
            if (option == 2 || option == 4 || option == 5){
                Matrix temp = new Matrix(1,1);
                Func.setElmt(temp, 0, 0, result);
                Func.writeMatrix(temp);
            }
            else{
                Func.writeMatrix(m);
            }
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
