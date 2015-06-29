/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual;

import static Visual.TablaCheques.FechaToTable;
import static Visual.TablaCheques.convFecha;
import conexiones.DBconection;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Martin
 */

public class ActualizarThread extends Thread {
    
    Actualizar paren;
    DBconection con;
    
    public ActualizarThread(String nro, Actualizar p){
        super(nro);
        paren=p;
    }
    
    @Override
    public void run(){
        try {
            String StringArray[];
            Date FechaPago;
            Date FechaEmision;
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yy");
            String beneficiario;
            String importe;
            String numero;
            String factura;
            String chequera;
            StringTokenizer stk;
            String testParcer;
            int cant=0;
            int total=0;
            int index;
            JFileChooser file = new JFileChooser();
            int dir = file.showOpenDialog(paren);
            if (dir == JFileChooser.APPROVE_OPTION) {
                String name = file.getSelectedFile().getAbsolutePath();
                FileInputStream fichero = new FileInputStream(name);
                HSSFWorkbook libro = new HSSFWorkbook(fichero);
                HSSFSheet hoja = libro.getSheetAt(0);       
                Iterator<Row> iteradorFila = hoja.rowIterator();
                Iterator<Cell> iteradorCelda;
                Row row=null;
                Cell cell;
                
                //se obtiene el total de cheques a importar;
                while (iteradorFila.hasNext()){ 
                    row= iteradorFila.next();
                    iteradorCelda = row.cellIterator();
                    cell = iteradorCelda.next();
                    if (cell.getStringCellValue().contains("TOTALES")){
                        while(total==0){
                            try {
                                total=(int) cell.getNumericCellValue();
                                cell= iteradorCelda.next();
                            } catch (IllegalStateException ex){
                                cell= iteradorCelda.next();
                            }
                        } 
                    }
                }
                
                hoja = libro.getSheetAt(0);       
                iteradorFila = hoja.rowIterator();
                con = new DBconection();
                paren.setVisible(true);
                while (iteradorFila.hasNext()) {
                    row = iteradorFila.next();
                    iteradorCelda = row.cellIterator();
                    cell = iteradorCelda.next();
                    try {
                        FechaPago = (Date) formateador.parse(cell.getStringCellValue()).clone();
                        iteradorCelda.next();
                        FechaEmision = null;
                        while (FechaEmision == null) {
                            cell = iteradorCelda.next();
                            try {
                                FechaEmision = (Date) formateador.parse(cell.getStringCellValue()).clone();
                            } catch (ParseException ex) {
                            }
                        }
                        chequera = null;
                        numero = null;
                        factura = null;
                        beneficiario = null;
                        index=cell.getStringCellValue().indexOf("CHEQUERA");
                        while (index==-1){
                            cell = iteradorCelda.next();
                            index=cell.getStringCellValue().indexOf("CHEQUERA");
                        }
                        index+=8;
                        while (beneficiario== null){    
                            try {
                                stk=null;
                                if (chequera==null){
                                    stk = new StringTokenizer(cell.getStringCellValue().substring(index));
                                    testParcer= stk.nextToken();
                                    Integer.parseInt(testParcer);
                                    chequera=new String(testParcer);
                                    StringArray= cell.getStringCellValue().split("CHEQUERA");
                                    if (StringArray.length>1) StringArray= StringArray[1].split("Nº");
                                    chequera= StringArray[0].replace(" ", "");
                                    Integer.parseInt(chequera);
                                }

                                if (numero==null){
                                    if (stk==null){
                                        stk = new StringTokenizer(cell.getStringCellValue());
                                    }
                                    while (stk.hasMoreTokens() && numero==null){
                                        try{
                                            testParcer= stk.nextToken();
                                            Integer.parseInt(testParcer);
                                            numero=new String(testParcer);
                                        } catch (NumberFormatException ex) {}
                                    }
                                }
                                
                                if (factura==null){
                                    if (stk==null){
                                        stk = new StringTokenizer(cell.getStringCellValue());
                                    }
                                    while (stk.hasMoreTokens() && factura==null){
                                        try{
                                            testParcer= stk.nextToken();
                                            Integer.parseInt(testParcer);
                                            factura=new String(testParcer);
                                        } catch (NumberFormatException ex) {}
                                    }
                                }
                                
                                while (stk.hasMoreTokens() && !stk.nextToken().equals("-")){}
                                if (stk.hasMoreTokens()){
                                    beneficiario= new String();
                                    while(stk.hasMoreTokens()) beneficiario+=stk.nextToken()+" ";
                                }else{
                                    cell = iteradorCelda.next();
                                }
                            } catch (NumberFormatException ex) {
                                cell = iteradorCelda.next();
                            }
                        }
                        importe = null;
                        while (importe == null) {                            
                            cell = iteradorCelda.next();
                            try {
                                importe = cell.getStringCellValue();
                                importe = importe.replaceAll("-", "");
                                importe = importe.replaceAll("\\.", "");
                                importe = importe.replaceAll(",", ".");
                                Double.parseDouble(importe);
                            } catch (NumberFormatException ex) {
                                importe = null;
                            }
                        }
                        Visual.AgregarCheque.agregarCheque(numero, importe, chequera, factura, beneficiario, FechaEmision, FechaPago);
                        cant++;
                        paren.getbarra().setValue((int)((cant*100)/total));
                        paren.getporce().setText(String.valueOf(paren.getbarra().getValue())+ "%");
                        paren.repaint();
                    } catch (ParseException ex) {
                    }
                }
                JOptionPane.showMessageDialog(null, "Se importaron "+cant+" cheques correctamente de "+total);
                Statement st2 = con.conectar().createStatement();
                st2.executeUpdate("DELETE FROM Actualizacion");
                st2.executeUpdate("INSERT INTO Actualizacion(fecha) VALUE('" + FechaToTable(convFecha(new Date())) + "')");
            }
            paren.parent.setEnabled(true);
            paren.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la Base de Datos. Intente establecer la conexion o contacte con el administrador c");
            System.out.println(ex.toString());
            System.exit(0);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error al importar el archivo. Intentelo nuevamente y si el problema persiste contacte al administrador");
            System.out.println(ex.toString());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar la planilla de Excel. contacte con el administrador");
            System.out.println(ex.toString());
            System.exit(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar la planilla de Excel. contacte con el administrador "+ex.toString());
            System.out.println(ex.toString());
            System.exit(0);
        }  
    }
    
    

}
