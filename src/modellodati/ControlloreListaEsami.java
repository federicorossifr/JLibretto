
package modellodati;

import javafx.collections.*;

public class ControlloreListaEsami  {
    private ObservableList<Esame> listaEsamiSvolti;
    private ObservableList<Esame> listaEsamiDisponibili;
    private static ControlloreListaEsami _istanza;
    
    private ControlloreListaEsami() {
        listaEsamiSvolti = FXCollections.observableArrayList();
        listaEsamiDisponibili = FXCollections.observableArrayList();
        popolaListeEsami();
        ordinaListaEsamiSvolti();
        //listaEsamiSvolti.add(new Esame());
    }
    
    public static ControlloreListaEsami getIstanza() {
        if(_istanza == null)
            _istanza = new ControlloreListaEsami();
        return _istanza;
    }
    
    private void ordinaListaEsamiSvolti() {
        listaEsamiSvolti.sort((e1,e2) -> e1.getData().compareTo(e2.getData()));        
    }
    
    public void creaEsame() {
        Esame e = listaEsamiSvolti.get(listaEsamiSvolti.size()-1);
        int insertedId = GestoreArchiviazioneEsami.getIstanza().inserisciEsame(e);
        if(insertedId > 0) {
            e.setId(insertedId);
            ordinaListaEsamiSvolti();
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
        if(result) {
            ordinaListaEsamiSvolti();
            notificaCambiamentoEsame(posizioneEsame);
        }
    }
    
    private void popolaListeEsami() {
        getListaEsamiSvolti().clear();
        GestoreArchiviazioneEsami.getIstanza().leggiEsamiSvolti(listaEsamiSvolti);
        GestoreArchiviazioneEsami.getIstanza().leggiEsamiDisponibili(listaEsamiDisponibili);
    }

    public ObservableList<Esame> getListaEsamiSvolti() {
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
