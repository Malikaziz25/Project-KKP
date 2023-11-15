import javax.swing.*;
import java.awt.*;
class border{
 public static void main (String [] args){
 JFrame f=new JFrame("Border Layout");
 BorderLayout bl=new BorderLayout();
 f.getContentPane().setLayout(bl);

 JButton b1=new JButton("NORTH");
 JButton b2=new JButton("SOUTH");
 JButton b3=new JButton("EAST");
 JButton b4=new JButton("WEST");
 JButton b5=new JButton("CENTER");

 f.getContentPane().add(b1,BorderLayout.NORTH);
 f.getContentPane().add(b2,BorderLayout.SOUTH);
 f.getContentPane().add(b3,BorderLayout.EAST);
 f.getContentPane().add(b4,BorderLayout.WEST);
 f.getContentPane().add(b5,BorderLayout.CENTER);
 f.pack();
 f.setVisible(true);
 }
}