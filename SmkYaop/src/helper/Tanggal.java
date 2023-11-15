/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.Timer;
import javax.swing.JLabel;
import java.util.Date;

/**
 *
 * @author acer
 */
public class Tanggal extends JLabel implements ActionListener {
    private SimpleDateFormat format;
    private Timer timer;
    private Date date;
    
    public Tanggal(){
        timer = new Timer(1000,this);
        format = new SimpleDateFormat("EEEEE,dd MMMMM yyyy HH:m:ss",new java.util.Locale("id","ID"));
        date = new Date();
        timer.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        date.setTime(System.currentTimeMillis());
        this.setText(format.format(date));
    }
    
    @Override
    public void setFont(Font font) {
        super.setFont(new java.awt.Font("Tahoma", 1, 18)); 
    }

    
}
