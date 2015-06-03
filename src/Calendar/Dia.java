/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calendar;

import java.awt.Color;
import java.awt.Font;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

/**
 *
 * @author martin
 */

public class Dia extends JScrollPane {

    private Date fecha;
    public JList lista;
    private DefaultListModel eventos;
    private String dias[];
    private double total;
    private double totalpagado;

    public Dia() {
        super();
        total = 0;
        totalpagado = 0;
        dias = new String[7];
        dias[0] = new String("Domingo");
        dias[1] = new String("Lunes");
        dias[2] = new String("Martes");
        dias[3] = new String("Miercoles");
        dias[4] = new String("Jueves");
        dias[5] = new String("Viernes");
        dias[6] = new String("Sabado");
        eventos = new DefaultListModel();
        this.limpiarEventos();
        lista = new JList();
        lista.setModel(eventos);
        lista.setVisibleRowCount(5);
        TitledBorder borde = new TitledBorder("a");
        borde.setTitleJustification(2);
        TitledBorder TotImporte = new TitledBorder("$10");
        TotImporte.setTitleFont(new Font("Arial", Font.PLAIN, 10));
        TotImporte.setTitlePosition(TitledBorder.BOTTOM);
       // TotImporte.setTitleColor(Color.green);
        TotImporte.setTitleJustification(2);
        borde.setBorder(TotImporte);
        this.setBorder(borde);
        this.setViewportView(lista);
    }

    public void setFecha(Date f) {
        fecha = (Date) f.clone();
        TitledBorder borde = (TitledBorder) this.getBorder();
        borde.setTitle(dias[fecha.getDay()] + " " + fecha.getDate());
        limpiarEventos();
        this.setFeriado(false);
        setTituloImp();
    }

    public void agregarEvento(String evento, double num, int pagado) {
        if (pagado == 1) {
            totalpagado += num;
            totalpagado += num;
            totalpagado = Math.round(totalpagado * 1000);
            totalpagado = totalpagado / 1000;
            eventos.addElement("***"+evento);
        } else {
            total += num;
            total = Math.round(total * 1000);
            total = total / 1000;
            eventos.addElement(evento);
            setTituloImp();
            //  lista.setModel(eventos);
        }
    }

    public void eliminarEvento(String evento) {
        StringTokenizer st;
        int indices[] = lista.getSelectedIndices();
        for (int i = indices.length; i > 0; i--) {
            st = new StringTokenizer((String) eventos.get(indices[i]));
            total -= Double.parseDouble(st.nextToken().substring(1));
            total = Math.round(total * 1000);
            total = total / 1000;
            eventos.remove(indices[i]);
        }
        setTituloImp();
    }

    public void setColor(Color c) {
        this.setBackground(c);
        lista.setBackground(c);
    }

    public void setFeriado(boolean b) {
        if (b) {
            this.setBackground(Color.red);
            lista.setBackground(Color.red);
            this.setEnabled(false);
            lista.setEnabled(false);
        } else {
            this.setBackground(Color.WHITE);
            lista.setBackground(Color.WHITE);
            this.setEnabled(true);
            lista.setEnabled(true);
        }
    }

    public void limpiarEventos() {
        eventos.clear();
        total = 0;
        totalpagado=0;
    }
    
    public void setTituloImp(){
        TitledBorder b = (TitledBorder) this.getBorder();
        TitledBorder borde = (TitledBorder) b.getBorder();
        borde.setTitle("Total $"+String.valueOf(total));
    }

    public Date getFecha() {
        return this.fecha;
    }

    public double getTotal() {
        return this.total;
    }
    
    public double getTotalPagado(){
        return totalpagado;
    }
    
}
