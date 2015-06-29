/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calendar;

import java.awt.GridLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author martin
 */
public class Mes extends JPanel {

    public static final int DOMINGO = 0;
    public static final int LUNES = 1;
    public static final int MARTES = 2;
    public static final int MIERCOLES = 3;
    public static final int JUEVES = 4;
    public static final int VIERNES = 5;
    public static final int SABADO = 6;

    public int excel[][] = new int[5][5];
    
    private Dia dias[][];
    private Date mes;
    private double total;
    private double totalpagado;

    public Mes() {
        super();
        mes = new Date();
        GridLayout layout= new GridLayout(5, 5);
        layout.setHgap(10);
        layout.setVgap(10);
        this.setLayout(layout);
        dias = new Dia[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                dias[j][i] = new Dia();
                this.add(dias[j][i]);
                excel[i][j]=0;
            }
        }
    }

    public void setMes(Date m) {
        mes = m;
        total=0;
        totalpagado=0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                dias[j][i].limpiarEventos();
                dias[j][i].setVisible(true);
                excel[i][j]=0;
                dias[j][i].setFeriado(false);
            }
        }
        mes.setDate(1);
        int num = 0;
        if (mes.getDay() != DOMINGO && mes.getDay() != SABADO) {
            for (int i = 0; i < mes.getDay() - LUNES; i++) {
                dias[i][0].setVisible(false);
                excel[i][0]=-1;
                num++;
            }
        } else {
            if (mes.getDay() == DOMINGO) {
                mes.setDate(mes.getDate() + 1);
            } else {
                mes.setDate(mes.getDate() + 2);
            }
        }
        int i = 0;
        do {
            dias[mes.getDay() - LUNES][i].setFecha(mes);
            num++;
            if (mes.getDay() == VIERNES) {
                mes.setDate(mes.getDate() + 3);
                if (mes.getDate() <= 5 && num>10) {
                    mes.setDate(1);
                }
                i++;
            } else {
                mes.setDate(mes.getDate() + 1);
            }
        } while (mes.getDate() != 1);
        mes.setMonth(mes.getMonth() - 1);
        num = 25 - num;
        for (int j = 5 - num; j < 5; j++) {
            dias[j][4].setVisible(false);
            excel[j][4]=-1;
        }
    }

    public void agregarEvento(String evento, double num, Date dia, int pagado) {
        try{
        if (dias[dia.getDay() - 1][0].getFecha().getDate() == dia.getDate()) {
            dias[dia.getDay() - 1][0].agregarEvento(evento, num,pagado);
        }}catch(NullPointerException e){}
        try{
        if (dias[dia.getDay() - 1][1].getFecha().getDate() == dia.getDate()) {
            dias[dia.getDay() - 1][1].agregarEvento(evento, num,pagado);
        }}catch(NullPointerException e){}
        try{
        if (dias[dia.getDay() - 1][2].getFecha().getDate() == dia.getDate()) {
            dias[dia.getDay() - 1][2].agregarEvento(evento, num,pagado);
        }}catch(NullPointerException e){}
        try{
        if (dias[dia.getDay() - 1][3].getFecha().getDate() == dia.getDate()) {
            dias[dia.getDay() - 1][3].agregarEvento(evento, num,pagado);
        }}catch(NullPointerException e){}
        try{       
        if (dias[dia.getDay() - 1][4].getFecha().getDate() == dia.getDate()) {
            dias[dia.getDay() - 1][4].agregarEvento(evento, num,pagado);
        }}catch(NullPointerException e){}
        if(pagado==1) totalpagado+=num;
        else total+=num;
    }
    
    public void agregarEvento(String evento, double num, String dia, int pagado){
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        try {
            agregarEvento(evento, num, formateador.parse(dia), pagado);
        } catch (ParseException ex) {
            Logger.getLogger(Mes.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            System.exit(1);
        }
    }
    
    public void agregarFeriado(Date dia){
        if (dia.getDay()== SABADO || dia.getDay()== DOMINGO) return;
        try{
        if (dias[dia.getDay() - 1][0].getFecha().getDate() == dia.getDate()) {
            dias[dia.getDay() - 1][0].setFeriado(true);
        }}catch(NullPointerException e){}
        try{
        if (dias[dia.getDay() - 1][1].getFecha().getDate() == dia.getDate()) {
            dias[dia.getDay() - 1][1].setFeriado(true);
        }}catch(NullPointerException e){}
        try{
        if (dias[dia.getDay() - 1][2].getFecha().getDate() == dia.getDate()) {
            dias[dia.getDay() - 1][2].setFeriado(true);
        }}catch(NullPointerException e){}
        try{
        if (dias[dia.getDay() - 1][3].getFecha().getDate() == dia.getDate()) {
            dias[dia.getDay() - 1][3].setFeriado(true);
        }}catch(NullPointerException e){}
        try{
        if (dias[dia.getDay() - 1][4].getFecha().getDate() == dia.getDate()) {
            dias[dia.getDay() - 1][4].setFeriado(true);
        }}catch(NullPointerException e){}
    }
    
    public void agregarFeriado(String dia){
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        try {
            agregarFeriado(formateador.parse(dia));
        } catch (ParseException ex) {
            Logger.getLogger(Mes.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            System.exit(1);
        }
    }
    
    public double getTotalMes(){
        return this.total;
    }
    
    public double getTotalPagadoMes(){
        return totalpagado;
    }
    
    public double getTotalSemana(int sem){
        double tot=0;
        for (int i=0; i<5; i++){
            tot+=dias[i][sem].getTotal();
        }
        tot= Math.round(tot*1000);
        tot= tot/1000;
        return tot;
    }
    
    public double getTotalPagadoSemana(int sem){
        double tot=0;
        for (int i=0; i<5; i++){
            tot+=dias[i][sem].getTotalPagado();
        }
        tot= Math.round(tot*1000);
        tot= tot/1000;
        return tot;
    }
    
    public Dia[][] getDias(){
        return dias;
    }
}
