
package modellodati;

import java.time.LocalDate;
import javafx.collections.*;

public class ControlloreListaEsami  {
    private ObservableList<Esame> listaEsami;
    private static ControlloreListaEsami _istanza;
    
    private ControlloreListaEsami() {
        listaEsami = FXCollections.observableArrayList();
        popolaEsami();
    }
    
    public static ControlloreListaEsami getIstanza() {
        if(_istanza == null)
            _istanza = new ControlloreListaEsami();
        return _istanza;
    }
    
    public void creaEsame(String n,Integer m,Integer c,LocalDate d) {
        Esame e = new Esame(n,m,c,d);
        int insertedId = GestoreArchiviazioneEsami.getIstanza().inserisciEsame(e);
        if(insertedId > 0) {
            e.setId(insertedId);
            aggiungiEsame(e);
        }        
    }
    
    public void eliminaEsame(int posizioneEsame) {
        Esame daRimuovere = prelevaEsame(posizioneEsame);
        boolean risultato = GestoreArchiviazioneEsami.getIstanza().rimuoviEsame(daRimuovere.getId());
        if(risultato) {
            listaEsami.remove(posizioneEsame);
        }
    }
    
    public void modificaEsame(Esame e,int posizioneEsame) {
        boolean result = GestoreArchiviazioneEsami.getIstanza().modificaEsame(e);
        if(result)
            notificaCambiamentoEsame(posizioneEsame);
    }
    
    private void popolaEsami() {
        getListaEsami().clear();
        GestoreArchiviazioneEsami.getIstanza().leggiEsami(getListaEsami());
    }
    
    private void aggiungiEsame(Esame e) {
            listaEsami.add(e);
    }
    
    public ObservableList<Esame> getListaEsami() {
        return listaEsami;
    }
    
    public Esame prelevaEsame(int index) {
        return listaEsami.get(index);
    }
    
    private void notificaCambiamentoEsame(int index) {
        Esame toBeNotified = listaEsami.get(index);
        listaEsami.remove(index);
        listaEsami.add(index,toBeNotified);
    }
}
