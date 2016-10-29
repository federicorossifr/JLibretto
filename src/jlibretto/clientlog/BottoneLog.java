/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlibretto.clientlog;


import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author feder
 */
public class BottoneLog extends Button implements Loggable {
    public BottoneLog(String contenuto) {
        super(contenuto);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED,
                (MouseEvent e) -> produciAttivita(TipoAttivita.CLICK_BOTTONE));
    }

    @Override
    public AttivitaXML produciAttivita(TipoAttivita tipo) {
        AttivitaXML a = new AttivitaXML(tipo,this.getText(),"");
        inviaAttivita(a);
        return a;
    }

    @Override
    public void inviaAttivita(AttivitaXML attivita) {
        //crea client e invia
        System.out.println(attivita.serializzaInXML());
    }
}
