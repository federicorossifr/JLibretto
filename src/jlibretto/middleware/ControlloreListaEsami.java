///////////////////////////////////////////////7
package jlibretto.middleware;

import javafx.collections.*;
import jlibretto.backend.GestoreArchiviazioneEsami;

public class ControlloreListaEsami  {
    private ObservableList<Esame> listaEsamiSvolti;
    private ObservableList<Esame> listaEsamiDisponibili;
    private GestoreArchiviazioneEsami archivioEsami;
    private static ControlloreListaEsami _istanza;
    
    private ControlloreListaEsami() {
        listaEsamiDisponibili = FXCollections.observableArrayList();
        listaEsamiSvolti = FXCollections.observableArrayList();
        archivioEsami = new GestoreArchiviazioneEsami();
        popolaListeEsami();
        ordinaListaEsamiSvolti();
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
        int insertedId = archivioEsami.inserisciEsame(e);
        if(insertedId > 0) {
            e.setId(insertedId);
            ordinaListaEsamiSvolti();
            listaEsamiSvolti.add(new Esame());
        }        
    }
    
    public void eliminaEsame(int posizioneEsame) {
        if(posizioneEsame == listaEsamiSvolti.size()-1) return;
        Esame daRimuovere = listaEsamiSvolti.get(posizioneEsame);
        boolean risultato = archivioEsami.rimuoviEsame(daRimuovere.getId());
        if(risultato) {
            listaEsamiSvolti.remove(posizioneEsame);
        }
    }
    
    public void modificaEsame(Esame e,int posizioneEsame) {
        if(posizioneEsame == listaEsamiSvolti.size()) return;
        boolean result = archivioEsami.modificaEsame(e);
        if(result) {
            ordinaListaEsamiSvolti();
            notificaCambiamentoEsame(posizioneEsame);
        }
    }
    
    private void popolaListeEsami() {
        archivioEsami.leggiEsamiDisponibili(listaEsamiDisponibili);
        archivioEsami.leggiEsamiSvolti(listaEsamiSvolti);
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
    
    public ObservableList<Double>[] calcolaValoriMediaMobile() {
        ObservableList<Double> ret[] = new  ObservableList[]{FXCollections.observableArrayList(),FXCollections.observableArrayList()};
        Integer contatoreAr=0,contatorePo=0;
        Double sommaAr=0.0,sommaPo=0.0;
        for(Esame e:listaEsamiSvolti) {
            if(e.getId() < 0) continue;
            sommaAr+=e.getValutazione();
            contatoreAr+=1;
            ret[0].add(sommaAr/contatoreAr);
            sommaPo+=e.getCrediti()*e.getValutazione();
            contatorePo+=e.getCrediti();
            ret[1].add(sommaPo/contatorePo);
        }
        return ret;
    }
}
