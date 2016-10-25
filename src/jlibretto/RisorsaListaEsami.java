/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlibretto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author feder
 */
public class RisorsaListaEsami  {
    private ObservableList<Esame> listaEsami;
    private static RisorsaListaEsami _istanza;
    
    private RisorsaListaEsami() {
        listaEsami = FXCollections.observableArrayList();
    }
    
    public static RisorsaListaEsami getIstanza() {
        if(_istanza == null)
            _istanza = new RisorsaListaEsami();
        return _istanza;
    }
    
    public void aggiungiEsame(Esame e) {
            listaEsami.add(e);
    }
    
    public ObservableList<Esame> getListaEsami() {
        return listaEsami;
    }
    
    public Esame prelevaEsame(int index) {
        return listaEsami.get(index);
    }
    
    public void notificaCambiamentoEsame(int index) {
        Esame toBeNotified = listaEsami.get(index);
        listaEsami.remove(index);
        listaEsami.add(index,toBeNotified);
    }
}
