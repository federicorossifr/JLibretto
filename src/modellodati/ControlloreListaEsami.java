
package modellodati;

import javafx.collections.*;

public class ControlloreListaEsami  {
    private ObservableList<Esame> listaEsamiSvolti;
    private ObservableList<Esame> listaEsamiDisponibili;
    private static ControlloreListaEsami _istanza;
    
    private ControlloreListaEsami() {
        listaEsamiSvolti = FXCollections.observableArrayList();
        listaEsamiDisponibili = FXCollections.observableArrayList();
        popolaEsami();
        listaEsamiSvolti.add(new Esame());
    }
    
    public static ControlloreListaEsami getIstanza() {
        if(_istanza == null)
            _istanza = new ControlloreListaEsami();
        return _istanza;
    }
    
    public void creaEsame() {
        Esame e = listaEsamiSvolti.get(listaEsamiSvolti.size()-1);
        int insertedId = GestoreArchiviazioneEsami.getIstanza().inserisciEsame(e);
        if(insertedId > 0) {
            e.setId(insertedId);
            listaEsamiSvolti.add(new Esame());
        }        
    }
    
    public void eliminaEsame(int posizioneEsame) {
        if(posizioneEsame == listaEsamiSvolti.size()-1) return;
        Esame daRimuovere = listaEsamiSvolti.get(posizioneEsame);
        boolean risultato = GestoreArchiviazioneEsami.getIstanza().rimuoviEsame(daRimuovere.getId());
        if(risultato) {
            listaEsamiSvolti.remove(posizioneEsame);
        }
    }
    
    public void modificaEsame(Esame e,int posizioneEsame) {
        if(posizioneEsame == listaEsamiSvolti.size()) return;
        boolean result = GestoreArchiviazioneEsami.getIstanza().modificaEsame(e);
        if(result)
            notificaCambiamentoEsame(posizioneEsame);
    }
    
    private void popolaEsami() {
        getListaEsami().clear();
        GestoreArchiviazioneEsami.getIstanza().leggiEsamiSvolti(listaEsamiSvolti);
        GestoreArchiviazioneEsami.getIstanza().leggiEsamiDisponibili(listaEsamiDisponibili);
    }

    public ObservableList<Esame> getListaEsami() {
        return listaEsamiSvolti;
    }
    
    public ObservableList<Esame> getListaEsamiDisponibili() {
        return listaEsamiDisponibili;
    }
    
    private void notificaCambiamentoEsame(int index) {
        Esame daNotificare = listaEsamiSvolti.get(index);
        listaEsamiSvolti.remove(index);
        listaEsamiSvolti.add(index,daNotificare);
    }
    
    public boolean confrontaConIndiceUltimoEsame(int i) {
        return i == listaEsamiSvolti.size()-1;
    }
}
