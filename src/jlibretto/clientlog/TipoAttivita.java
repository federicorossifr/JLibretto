/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlibretto.clientlog;

import java.io.Serializable;

/**
 *
 * @author feder
 */
public enum TipoAttivita implements Serializable {
    CLICK_BOTTONE,AVVIO_APPLICAZIONE,CHIUSURA_APPLICAZIONE;

    @Override
    public String toString() {
        String s;
        switch(this) {
            case CLICK_BOTTONE: s="Click bottone";break;
            case AVVIO_APPLICAZIONE: s="Avvio applicazione";break;
            case CHIUSURA_APPLICAZIONE: s="Chiusura applicazione"; break;
            default: s="Generica";
        }
        return s;
    }
}
