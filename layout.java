import javax.swing.*;
import java.awt.*;
class flow{
 public static void main (String [] args){
 JFrame f=new JFrame("Flow Layout");
 FlowLayout fl=new FlowLayout(FlowLayout.CENTER);

 JButton b1=new JButton("Posisi 1");
 JButton b2=new JButton("Posisi 2");
 JButton b3=new JButton("Posisi 3");
 JButton b4=new JButton("Posisi 4");
 JButton b5=new JButton("Posisi 5");

 f.getContentPane().setLayout(fl);
 f.getContentPane().add(b1);
 f.getContentPane().add(b2);
 f.getContentPane().add(b3);
 f.getContentPane().add(b4);
 f.getContentPane().add(b5);
 f.setSize(500,80);
 f.setVisible(true);
 }
}