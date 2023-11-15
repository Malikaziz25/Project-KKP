/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TOSHIBA
 */
public class Database {
    private final static String username = "root";
    private final static String password = "";
    private final static String dbName = "smkyaop";
    private final static String url = "jdbc:mysql://127.0.0.1/"+dbName;    
    private static Connection conn = null;
    
    public static Connection  getConn(){
        if(conn == null){
            try {
                conn =  DriverManager.getConnection(url,username,password);
           } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        return conn;
    }
 
    
}
