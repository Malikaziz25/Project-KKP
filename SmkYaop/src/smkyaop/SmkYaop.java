/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smkyaop;

import javax.swing.JOptionPane;

/**
 *
 * @author Bunga Permatasari
 */
public class SmkYaop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       Login login = new Login();
       Splash start = new Splash();
        try{
            start.setVisible(true);
            for(int i = 1; i<=100; i++){
                int counter = i*2;
                Thread.sleep(100);     
                start.getProgress().setValue(counter);
                 
                if(counter>=100){
                    login.setVisible(true);
                    start.dispose();
                    
                }
            }            
        }catch(InterruptedException ex){
             JOptionPane.showMessageDialog(null, ex.getMessage());
        }       
        
    }
    
}
