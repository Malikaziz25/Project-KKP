/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import database.Database;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Bunga Permatasari
 */

public class Helper {
    
    public static void alert(String msg){
        JOptionPane.showMessageDialog(null,msg);
    }
    
    public static int tanya(String message){
        return JOptionPane.showConfirmDialog(null, message);
    }
    
    public static Boolean onlyNumber(java.awt.event.KeyEvent evt){
        Boolean rtn = true;
      char c = evt.getKeyChar();
      if (!((c >= '0') && (c <= '9') ||
         (c == KeyEvent.VK_BACK_SPACE) ||
         (c == KeyEvent.VK_DELETE)|| c == KeyEvent.VK_ENTER) ||
         (c == KeyEvent.VK_ESCAPE) ||
         (c == KeyEvent.VK_UP) ||
          (c == KeyEvent.VK_DOWN) ||
          (c == KeyEvent.VK_RIGHT) ||
          (c == KeyEvent.VK_LEFT)) {        
            evt.consume();            
            rtn = false;
      }
      return rtn;
      
    }    
    
    
    public void generateReportLogo(String path,HashMap param,String judul){
        param.put("logo",getLogo());
            try{
            Connection con = Database.getConn();
            InputStream is = getClass().getResourceAsStream(path);            
            JasperPrint print = JasperFillManager.fillReport(is, param,con);
            JRViewer aViewer = new JRViewer(print);
            JDialog viewer = new JDialog();
            viewer.setTitle(judul);
            viewer.setAlwaysOnTop(true);
            viewer.getContentPane().add(aViewer);
            viewer.setBounds(150, 60, 1200, 800);
            viewer.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);            
            viewer.setVisible(true);            
            
        }catch(JRException ex){
               System.out.println(ex.toString());
        }    
    }
    public InputStream getLogo(){
        return this.getClass().getResourceAsStream("/images/smkyaop.png");
    }
    
        public void generateReportTable(String path,HashMap param,TableModel model){
            try{
                
            InputStream is = getClass().getResourceAsStream(path);        
            JasperPrint jp=JasperFillManager.fillReport(is, param,new JRTableModelDataSource(model));
            JasperViewer.viewReport(jp, false);
            JRViewer aViewer = new JRViewer(jp);
            JDialog viewer = new JDialog();
            viewer.setAlwaysOnTop(true);
            viewer.getContentPane().add(aViewer);
            viewer.setBounds(150, 60, 1200, 800);
            viewer.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);            
            viewer.setVisible(true);            
            
            
        }catch(JRException ex){
               System.out.println(ex.toString());
        }    
    }    
    
    
}
