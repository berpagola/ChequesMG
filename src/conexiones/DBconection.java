/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexiones;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author martin
 */
public class DBconection {

    Connection conn = null;

    public DBconection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String db = "jdbc:mysql://localhost:3306/ChequesMG";
            conn = DriverManager.getConnection(db, "root", "root");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "No se ha podido detectar el driver. Contactarse con el administrador");
            System.exit(0);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la Base de Datos. Intente establecer la conexion o contacte con el administrador");
            System.out.println(ex.toString());
            System.exit(0);
        }
    }
    
    public String testUser(String user, String pass){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String db = "jdbc:mysql://"+InetAddress.getLocalHost().getHostAddress()+"/ChequesMG";
            DriverManager.getConnection(db, user, pass);
            return InetAddress.getLocalHost().getHostAddress();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "No se ha podido detectar el driver. Contactarse con el administrador");
            System.exit(0);
            return "";
        } catch (SQLException ex) {
            try{
                PreparedStatement ps= conn.prepareStatement(newUser1(user, pass));
                ps.execute();
                ps= conn.prepareStatement(newUser2(user, pass));
                ps.execute();
                ps= conn.prepareStatement(newUser3(user, pass));
                ps.execute();
                ps= conn.prepareStatement(newUser4(user, pass));
                ps.execute();
                return InetAddress.getLocalHost().getHostAddress();
            }catch (SQLException ex1){
                System.out.println(ex1);
                return "";
            } catch (UnknownHostException ex1) {
                Logger.getLogger(DBconection.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println(ex1);
                return "";
            } 
        } catch (UnknownHostException ex) {
            Logger.getLogger(DBconection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            return "";
        }
    }

    public Connection conectar() {
        return conn;
    }

    public void desconectar() {
        conn = null;
    }
    
    private String newUser1(String user, String pass){
        return "CREATE USER '"+user+"'@'localhost' IDENTIFIED BY '"+pass+"'";
    }
    
        private String newUser2(String user, String pass){
        return 
"GRANT ALL PRIVILEGES ON *.* TO '"+user+"'@'localhost' WITH GRANT OPTION";
    }
        
         private String newUser3(String user, String pass){
        return 
"CREATE USER '"+user+"'@'%' IDENTIFIED BY '"+pass+"'";
    }
         
         
         private String newUser4(String user, String pass){
        return "GRANT ALL PRIVILEGES ON *.* TO '"+user+"'@'%' WITH GRANT OPTION";
    }
    
    
    
}

