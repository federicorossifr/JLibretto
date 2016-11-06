
package modellodati;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RisorsaListaEsami  {
    private final ObservableList<Esame> listaEsami;
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
