/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual;

import conexiones.DBconection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author martin
 */
public class AgregarCheque extends javax.swing.JFrame {

    /**
     * Creates new form AgregarCheque
     */
    TablaCheques parent;
    DBconection con;

    public AgregarCheque(TablaCheques paren) {
        parent = paren;
        initComponents();
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cheque = new javax.swing.JTextField();
        factura = new javax.swing.JTextField();
        chequera = new javax.swing.JTextField();
        importe = new javax.swing.JTextField();
        beneficiario = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        fEmision = new com.toedter.calendar.JDateChooser();
        fPago = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("Numero de cheque");

        jLabel2.setText("Numero de Factura");

        jLabel3.setText("Numero de chequera");

        jLabel5.setText("Importe");

        jLabel6.setText("Beneficiario");

        jLabel7.setText("Fecha de emision");

        jLabel8.setText("Fecha de pago");

        cheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chequeActionPerformed(evt);
            }
        });

        factura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facturaActionPerformed(evt);
            }
        });

        chequera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chequeraActionPerformed(evt);
            }
        });

        importe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importeActionPerformed(evt);
            }
        });
        importe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                importeKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                importeKeyPressed(evt);
            }
        });

        beneficiario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beneficiarioActionPerformed(evt);
            }
        });

        jButton1.setText("Agregar y continuar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Agregar y finalizar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(35, 35, 35)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel1)
                                    .add(jLabel2))
                                .add(27, 27, 27)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(factura, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                                    .add(cheque)))
                            .add(jLabel3)))
                    .add(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jButton1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jButton2)))
                .addContainerGap(12, Short.MAX_VALUE))
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(chequera, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()
                        .add(35, 35, 35)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jLabel6)
                                .add(32, 32, 32)
                                .add(beneficiario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 183, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jLabel5)
                                .add(35, 35, 35)
                                .add(importe, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 138, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel7)
                                    .add(jLabel8))
                                .add(18, 18, 18)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(fPago, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(fEmision, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))))
                .add(0, 34, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel1)
                    .add(cheque, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(12, 12, 12)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel2)
                    .add(factura, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(12, 12, 12)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel3)
                    .add(chequera, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(24, 24, 24)
                        .add(jLabel5)
                        .add(15, 15, 15))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(importe, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)))
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel6)
                    .add(beneficiario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(17, 17, 17)
                        .add(jLabel7)
                        .add(20, 20, 20)
                        .add(jLabel8)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 19, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(fEmision, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(18, 18, 18)
                        .add(fPago, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(15, 15, 15))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chequeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chequeActionPerformed

    private void facturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_facturaActionPerformed

    private void chequeraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chequeraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chequeraActionPerformed

    private void importeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_importeActionPerformed

    private void beneficiarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beneficiarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_beneficiarioActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        agregar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        agregar();
        parent.setVisible(true);
        parent.setEnabled(true);
        this.dispose();
        parent.llenarTabla();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void importeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_importeKeyPressed

    private void importeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importeKeyTyped
        char c = evt.getKeyChar();
        if ((c < '0' || c > '9') && c != '.') {
            evt.consume();
        }
    }//GEN-LAST:event_importeKeyTyped

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        parent.setVisible(true);
        parent.setEnabled(true);
        this.dispose();
        parent.llenarTabla();
    }//GEN-LAST:event_formWindowClosing

    public static int agregarCheque(String cheque, String importe, String chequera, String factura, String beneficiario, Date fEmision, Date fPago) {
        try {
            DBconection con = new DBconection();
            Date day;
            Statement st = con.conectar().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM `Cheques` WHERE Numero = '" + cheque + "'");
            if (!rs.first()) {
                day = (Date) fPago.clone();
                day = Feriados.actualizarFinSemana(day);
                st.executeUpdate("INSERT INTO Cheques(Numero, Importe, Chequera, Factura, Beneficiario, FechaEmision, FechaPago, FechaOriginal, Pagado) VALUE ('" + cheque + "', '" + importe + "', '" + chequera + "', '" + factura + "', '" + beneficiario + "','" + TablaCheques.convFecha(fEmision) + "', '" + TablaCheques.convFecha(day) + "' , '" + TablaCheques.convFecha(fPago) + "', '0')");
                Feriados.actualizarFechas(cheque);
                //JOptionPane.showMessageDialog(null, "Se agrego el cheque correctamente");
                con.desconectar();
                return 0;
            } else {
                //JOptionPane.showMessageDialog(null, "El cheque ya existe");
                con.desconectar();
                return 1;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la Base de Datos. Intente establecer la conexion o contacte con el administrador");
            System.out.println(ex);
            System.exit(0);
            return 2;
        }
    }

    private void agregar() {
        try {
            if (cheque.getText() == "" || cheque.getText() == null || importe.getText() == "" || importe.getText() == null || fEmision.getDate() == null || fPago.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Los campos \"Numero de Cheque\", \"Importe\", \"Fecha de Emision\" y \"Fecha de Pago\" son obligatorios");
            } else {
                if (fEmision.getDate().after(fPago.getDate())) {
                    JOptionPane.showMessageDialog(null, "La fecha de emision no puede ser posterior a la fecha de pago");
                } else {
                    con = new DBconection();
                    Date day;
                    Statement st = con.conectar().createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM `Cheques` WHERE Numero = '" + cheque.getText() + "'");
                    if (!rs.first()) {
                        day = (Date) fPago.getDate().clone();
                        day = Feriados.actualizarFinSemana(day);
                        st.executeUpdate("INSERT INTO Cheques(Numero, Importe, Chequera, Factura, Beneficiario, FechaEmision, FechaPago, FechaOriginal, Pagado) VALUE ('" + cheque.getText() + "', '" + importe.getText() + "', '" + chequera.getText() + "', '" + factura.getText() + "', '" + beneficiario.getText() + "','" + (fEmision.getDate().getYear() + 1900) + "-" + (fEmision.getDate().getMonth() + 1) + "-" + fEmision.getDate().getDate() + "', '" + TablaCheques.convFecha(day) + "' , '" + (fPago.getDate().getYear() + 1900) + "-" + (fPago.getDate().getMonth() + 1) + "-" + fPago.getDate().getDate() + "', '0')");
                        Feriados.actualizarFechas(cheque.getText());
                        JOptionPane.showMessageDialog(null, "Se agrego el cheque correctamente");
                        cheque.setText("");
                        importe.setText("");
                        chequera.setText("");
                        factura.setText("");
                        beneficiario.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "El cheque ya existe");
                    }
                    con.desconectar();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la Base de Datos. Intente establecer la conexion o contacte con el administrador");
            System.out.println(ex);
            System.exit(0);
        }
    }

    public static String FechaDB(String anio, String mes, String dia) {
        String fecha = new String(anio + "-" + mes + "-" + dia);
        return fecha;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AgregarCheque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgregarCheque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgregarCheque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgregarCheque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField beneficiario;
    private javax.swing.JTextField cheque;
    private javax.swing.JTextField chequera;
    private com.toedter.calendar.JDateChooser fEmision;
    private com.toedter.calendar.JDateChooser fPago;
    private javax.swing.JTextField factura;
    private javax.swing.JTextField importe;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}