import javax.swing.*;
import java.awt.*;
class grid{
 public static void main(String [] args){
 JFrame f=new JFrame("Grid Layout");
 JButton b1=new JButton("Satu");
 JButton b2=new JButton("Dua");
 JButton b3=new JButton("Tiga");
 JButton b4=new JButton("Empat");
 JButton b5=new JButton("Lima");

 GridLayout gl=new GridLayout(3,2);
 f.getContentPane().setLayout(gl);
 f.getContentPane().add(b1);
 f.getContentPane().add(b2);
 f.getContentPane().add(b3);
 f.getContentPane().add(b4);
 f.getContentPane().add(b5);

 f.pack();
 f.setVisible(true);

 }
}
