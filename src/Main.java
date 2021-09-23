package src;
import javax.swing.JOptionPane;

public class Main {
  // ges ini cuma testing maaf :V
    public static void main(String[] args) {
      String input_filename = JOptionPane.showInputDialog(null, "Enter filename:");
      Matrix m = Func.readMatrix(input_filename);
      System.out.println(m.cols);
      System.out.println(m.rows);
      Func.displayMatrix(m);
      String output_filename = JOptionPane.showInputDialog(null, "Saving this file as ______.txt (pls fill in da blankz):");
      Func.writeMatrix(output_filename, m);
    }
}