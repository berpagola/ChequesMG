/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual;

import conexiones.DBconection;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author martin
 */
public class TablaCheques extends javax.swing.JFrame {
    
    DBconection con;

    public static String moneyFormat(double total){
        double aux= Math.round(total*1000);
        return "$"+((long) (aux / 1000))+"."+((long)(aux % 1000));
    }
    
    /**
     * Creates new form TablaCheques
     */
    public TablaCheques() {
        try {
            initComponents();
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
            EmisionA.setDate(new Date());
            EmisionD.setDate(formateador.parse("1999-01-01"));
            PagarA.setDate(new Date());
            PagarD.setDate(formateador.parse("1999-01-01"));
            EmisionA.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {                
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    llenarTabla();
                }
            });
            EmisionD.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {                
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    llenarTabla();
                }
            });
            PagarA.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {                
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    llenarTabla();
                }
            });
            PagarD.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {                
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    llenarTabla();
                }
            });
            llenarTabla();
        } catch (ParseException ex) {
            Logger.getLogger(TablaCheques.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    }
    
    public static String convFecha(Date d) {
        return (d.getYear() + 1900) + "-" + (d.getMonth() + 1) + "-" + d.getDate();
    }
    
    public static String convFecha(String s) {
        try {
            SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
            Date d = formateador.parse(s);
            return (d.getYear() + 1900) + "-" + (d.getMonth() + 1) + "-" + d.getDate();
        } catch (ParseException ex) {
            Logger.getLogger(TablaCheques.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            System.exit(0);
            return null;
        }
    }
    
    public static String FechaToTable(String s) {
        try {
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
            Date d = formateador.parse(s);
            return (d.getDate() + "-" + (d.getMonth() + 1) + "-" + (d.getYear() + 1900));
        } catch (ParseException ex) {
            Logger.getLogger(TablaCheques.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            System.exit(0);
            return null;
        }
    }
    
    class DoubleComparator implements Comparator {
        
        public int compare(Object o1, Object o2) {
            String a = (String) o1;
            String b = (String) o2;
            Double int1 = new Double(a.substring(2));
            Double int2 = new Double(b.substring(2));
            return int1.compareTo(int2);
        }
        
        public boolean equals(Object o2) {
            return this.equals(o2);
        }
    }
    
    class FechaComparator implements Comparator {
        
        public int compare(Object o1, Object o2) {
            try {
                String a = (String) o1;
                String b = (String) o2;
                SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
                Date a1 = formateador.parse(a);
                Date b1 = formateador.parse(b);
                return a1.compareTo(b1);
            } catch (ParseException ex) {
                Logger.getLogger(TablaCheques.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
                System.exit(0);
                return 1;
            }
        }
        
        public boolean equals(Object o2) {
            return this.equals(o2);
        }
    }
    
    public void llenarTabla() {
        try {
            con = new DBconection();
            String exp;
            DefaultTableModel modelo = new DefaultTableModel() {
                //Con esto conseguimos que la tabla no se pueda editar
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;                    
                }
            };
            modelo.addColumn("Fecha de Pago");
            modelo.addColumn("Beneficiario");
            modelo.addColumn("Emision");
            modelo.addColumn("Factura");
            modelo.addColumn("Nro. Cheque");
            
            modelo.addColumn("Importe");
            modelo.addColumn("Chequera");
            
            modelo.addColumn("Estado");
            ResultSet rs;
            Statement st;
            double total = 0;
            st = con.conectar().createStatement();
            String p = String.valueOf(Estado.getSelectedIndex());
            String EA, ED, PA, PD;
            if (EmitidoAC.isSelected()) {
                EA = convFecha(EmisionA.getDate());
            } else {
                EA = new String("2100-01-01");
            }
            if (EmitidoDC.isSelected()) {
                ED = convFecha(EmisionD.getDate());
            } else {
                ED = new String("1999-01-01");
            }
            if (PagarAC.isSelected()) {
                PA = convFecha(PagarA.getDate());
            } else {
                PA = new String("2100-01-01");
            }
            if (PagarDC.isSelected()) {
                PD = convFecha(PagarD.getDate());
            } else {
                PD = new String("1999-01-01");
            }
            rs = st.executeQuery("SELECT * FROM `Cheques` WHERE (Pagado=" + p + " OR " + p + "=2) AND FechaEmision<='" + EA + "' AND FechaEmision>='" + ED + "' AND FechaPago<'" + PA + "' AND FechaPago>'" + PD + "'");
            while (rs.next()) {
                Object[] fila = new Object[8];
                fila[4] = rs.getString("Numero");
                fila[3] = rs.getString("Factura");
                fila[6] = rs.getString("Chequera");
                fila[5] = "$ " + rs.getString("Importe");
                fila[1] = rs.getString("Beneficiario");
                fila[2] = FechaToTable(rs.getString("FechaEmision"));
                fila[0] = FechaToTable(rs.getString("FechaPago"));
                total += rs.getDouble("Importe");
                if (rs.getInt("Pagado") == 0) {
                    fila[7] = "Pendiente de Pago";
                } else {
                    fila[7] = "Pagado";
                }
                modelo.addRow(fila); // Añade una fila al final del modelo de la tabla
            }
            rs.close();
            st.close();
            ImporteTot.setText(moneyFormat(total));
            tabla.setModel(modelo);
            tabla.updateUI();
            tabla.getColumnModel().getColumn(0).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(250);
            // tabla.getColumnModel().getColumn(0).setResizable(false);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
            //  tabla.getColumnModel().getColumn(1).setResizable(false);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(100);
            // tabla.getColumnModel().getColumn(2).setResizable(false);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
            //  tabla.getColumnModel().getColumn(3).setResizable(false);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(7).setPreferredWidth(100);
            //Actualiza la tabla
            tabla.setRowHeight(25);
            TableRowSorter<TableModel> ordenar = new TableRowSorter<TableModel>(modelo);
            ordenar.setComparator(5, new DoubleComparator());
            ordenar.setComparator(0, new FechaComparator());
            ordenar.setComparator(2, new FechaComparator());
            tabla.setRowSorter(ordenar);
            //tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            ResultSet rs2;
            Statement st2 = con.conectar().createStatement();
            rs2 = st2.executeQuery("SELECT * FROM Actualizacion");
            rs2.next();
            try {
                UltImpo.setText(rs2.getString("fecha"));                
            } catch (SQLException e) {
                UltImpo.setText("no se cargo archivo");
            }   
            this.repaint();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la Base de Datos. Intente establecer la conexion o contacte con el administrador a");
            System.out.println(e);
            System.exit(0);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        Estado = new javax.swing.JComboBox();
        EmisionA = new com.toedter.calendar.JDateChooser();
        EmisionD = new com.toedter.calendar.JDateChooser();
        PagarA = new com.toedter.calendar.JDateChooser();
        PagarD = new com.toedter.calendar.JDateChooser();
        EmitidoAC = new javax.swing.JCheckBox();
        EmitidoDC = new javax.swing.JCheckBox();
        PagarAC = new javax.swing.JCheckBox();
        PagarDC = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ImporteTot = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        UltImpo = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        jMenu2.setText("File");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Edit");
        jMenuBar1.add(jMenu3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabla);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Administrar Cheques", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jButton1.setText("Agregar Cheque");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Modificar Cheque");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Pagar Cheque");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Cancelar Pago");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Eliminar Cheque");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(83, 83, 83)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(jButton2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jButton1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jButton3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jButton4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jButton5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 176, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 47, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jButton3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jButton4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Consultas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        Estado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pendiente de Pago", "Pagado", "Todos" }));
        Estado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EstadoActionPerformed(evt);
            }
        });

        EmitidoAC.setText("Emitido antes del");
        EmitidoAC.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                EmitidoACItemStateChanged(evt);
            }
        });

        EmitidoDC.setText("Emitido luego del");
        EmitidoDC.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                EmitidoDCItemStateChanged(evt);
            }
        });

        PagarAC.setSelected(true);
        PagarAC.setText("A pagar antes del");
        PagarAC.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PagarACItemStateChanged(evt);
            }
        });

        PagarDC.setText("A pagar luego del");
        PagarDC.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PagarDCItemStateChanged(evt);
            }
        });

        jLabel1.setText("Estado");

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(EmitidoAC)
                    .add(EmitidoDC)
                    .add(PagarAC)
                    .add(PagarDC)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel1))
                .add(18, 18, 18)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, PagarA, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, EmisionD, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, EmisionA, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, Estado, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(PagarD, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(Estado, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(EmisionA, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(EmitidoAC, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(EmisionD, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(EmitidoDC, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(PagarA, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(PagarAC, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(PagarD, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(PagarDC, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel3.setText("Importe Total:");

        ImporteTot.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel4.setText("Ultima Importacion de Planilla:");

        jMenu1.setText("Archivo");

        jMenuItem1.setText("Importar Planilla");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem4.setText("Usuario Invitado");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar2.add(jMenu1);

        jMenu4.setText("Fechas");

        jMenuItem2.setText("Feriados");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem2);

        jMenuBar2.add(jMenu4);

        jMenu5.setText("Calendario");

        jMenuItem3.setText("Abrir Calendario");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem3);

        jMenuBar2.add(jMenu5);

        setJMenuBar(jMenuBar2);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(layout.createSequentialGroup()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1090, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(27, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 133, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(ImporteTot)
                        .add(475, 475, 475)
                        .add(jLabel4)
                        .add(18, 18, 18)
                        .add(UltImpo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 179, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(404, 404, 404))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(12, 12, 12)
                        .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 525, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel3)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jLabel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(UltImpo, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .add(ImporteTot, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int r = tabla.getSelectedRow();
        if (tabla.getSelectedRows().length < 1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un expediente a eliminar");
        } else {
            EditarCheques edicion = new EditarCheques(this, tabla.getSelectedRows(), tabla);
            this.setVisible(false);
            this.setEnabled(false);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            if (tabla.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un Cheque a pagar");
            } else {
                int seleccion = JOptionPane.showOptionDialog(
                        this, // Componente padre
                        "Se marcaran como pagos los cheques seleccionados, ¿esta seguro de esta operacion?", //Mensaje
                        "Advertencia", // Título
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, // null para icono por defecto.
                        new Object[]{"Si", "No"},// null para YES, NO y CANCEL
                        "Si");
                if (seleccion != -1) {
                    if ((seleccion + 1) == 1) {
                        int sel[] = tabla.getSelectedRows();
                        for (int l = sel.length - 1; l >= 0; l--) {
                            Statement st = con.conectar().createStatement();
                            String cheque = (String) tabla.getValueAt(sel[l], 4);
                            st.executeUpdate("UPDATE Cheques SET Pagado= 1 WHERE Numero=" + cheque);
                        }
                        JOptionPane.showMessageDialog(null, "Se actualizaron los cheques correctamente");
                        llenarTabla();
                    }
                }        // TODO add your handling code here:
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la Base de Datos. Intente establecer la conexion o contacte con el administrador b");
            System.exit(0);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        AgregarCheque a = new AgregarCheque(this);
        this.setVisible(false);
        this.setEnabled(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void EstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EstadoActionPerformed
        llenarTabla();
    }//GEN-LAST:event_EstadoActionPerformed

    private void EmitidoACItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_EmitidoACItemStateChanged
        llenarTabla();        // TODO add your handling code here:
    }//GEN-LAST:event_EmitidoACItemStateChanged

    private void EmitidoDCItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_EmitidoDCItemStateChanged
        llenarTabla();         // TODO add your handling code here:
    }//GEN-LAST:event_EmitidoDCItemStateChanged

    private void PagarACItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PagarACItemStateChanged
        llenarTabla();         // TODO add your handling code here:
    }//GEN-LAST:event_PagarACItemStateChanged

    private void PagarDCItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PagarDCItemStateChanged
        llenarTabla();         // TODO add your handling code here:
    }//GEN-LAST:event_PagarDCItemStateChanged

               //             FechaEmision= new Date();
    //     FechaPago=(Date)formateador.parse(cell.getStringCellValue()).clone();

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        Actualizar act = new Actualizar(this);
        this.setEnabled(false);
        /*        try {
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
            int dir = file.showOpenDialog(this);
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
                Statement st = con.conectar().createStatement();
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
                                    while(stk.hasMoreTokens()) beneficiario+=stk.nextToken();
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
                        AgregarCheque.agregarCheque(numero, importe, chequera, factura, beneficiario, FechaEmision, FechaPago);
                        cant++;
                    } catch (ParseException ex) {
                    }
                }
                JOptionPane.showMessageDialog(null, "Se importaron "+cant+" cheques correctamente de "+total);
                Statement st2 = con.conectar().createStatement();
                st2.executeUpdate("DELETE FROM Actualizacion");
                st2.executeUpdate("INSERT INTO Actualizacion(fecha) VALUE('" + FechaToTable(convFecha(new Date())) + "')");
                llenarTabla();
            }
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
        }   */     
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Feriados f = new Feriados(this);
        this.setVisible(false);
        this.setEnabled(false);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            if (tabla.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un Cheque a pagar");
            } else {
                int seleccion = JOptionPane.showOptionDialog(
                        this, // Componente padre
                        "Se marcaran como pendiente de pago los cheques seleccionados, ¿esta seguro de esta operacion?", //Mensaje
                        "Advertencia", // Título
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, // null para icono por defecto.
                        new Object[]{"Si", "No"},// null para YES, NO y CANCEL
                        "Si");
                if (seleccion != -1) {
                    if ((seleccion + 1) == 1) {
                        int sel[] = tabla.getSelectedRows();
                        for (int l = sel.length - 1; l >= 0; l--) {
                            Statement st = con.conectar().createStatement();
                            String cheque = (String) tabla.getValueAt(sel[l], 4);
                            st.executeUpdate("UPDATE Cheques SET Pagado= 0 WHERE Numero=" + cheque);
                        }
                        JOptionPane.showMessageDialog(null, "Se actualizaron los cheques correctamente");
                        llenarTabla();
                    }
                }        // TODO add your handling code here:
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la Base de Datos. Intente establecer la conexion o contacte con el administrador");
            System.exit(0);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            int sel[] = tabla.getSelectedRows();
            String cheques[] = new String[sel.length];
            for (int i = 0; i < sel.length; i++) {
                cheques[i] = new String((String) tabla.getValueAt(sel[i], 4));
            }
            if (tabla.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un Cheque a pagar");
            } else {
                int seleccion = JOptionPane.showOptionDialog(
                        this, // Componente padre
                        "Se eliminaran los cheques seleccionados, ¿esta seguro de esta operacion?", //Mensaje
                        "Advertencia", // Título
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, // null para icono por defecto.
                        new Object[]{"Si", "No"},// null para YES, NO y CANCEL
                        "Si");
                if (seleccion != -1) {
                    if ((seleccion + 1) == 1) {
                        for (int l = sel.length - 1; l >= 0; l--) {
                            Statement st = con.conectar().createStatement();
                            st.executeUpdate("DELETE FROM Cheques WHERE Numero='" + cheques[l] + "'");
                        }
                        JOptionPane.showMessageDialog(null, "Se aa actualizaron los cheques correctamente");
                        llenarTabla();
                    }
                }        // TODO add your handling code here:
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la Base de Datos. Intente establecer la conexion o contacte con el administrador d");
            System.exit(0);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        VisualCalendario cal = new VisualCalendario(this);
        this.setVisible(false);
        this.setEnabled(false);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        DireccionDB dir = new DireccionDB(this);
        this.setVisible(false);
        this.setEnabled(false);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

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
            java.util.logging.Logger.getLogger(TablaCheques.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TablaCheques.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TablaCheques.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TablaCheques.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(TablaCheques.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(TablaCheques.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(TablaCheques.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(TablaCheques.class.getName()).log(Level.SEVERE, null, ex);
                }                
                new TablaCheques().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser EmisionA;
    private com.toedter.calendar.JDateChooser EmisionD;
    private javax.swing.JCheckBox EmitidoAC;
    private javax.swing.JCheckBox EmitidoDC;
    private javax.swing.JComboBox Estado;
    private javax.swing.JLabel ImporteTot;
    private com.toedter.calendar.JDateChooser PagarA;
    private javax.swing.JCheckBox PagarAC;
    private com.toedter.calendar.JDateChooser PagarD;
    private javax.swing.JCheckBox PagarDC;
    private javax.swing.JLabel UltImpo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
