///////////////////////////////////////////////7
package jlibretto.middleware;

import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
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
    }
    
    public static ControlloreListaEsami getIstanza() {
        if(_istanza == null)
            _istanza = new ControlloreListaEsami();
        return _istanza;
    }
    
    
    public void creaEsame() {
        Esame e = listaEsamiSvolti.get(listaEsamiSvolti.size()-1);
        int insertedId = archivioEsami.inserisciEsame(e);
        if(insertedId > 0) {
            e.setId(insertedId);
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
            notificaCambiamentoEsame(posizioneEsame);
        }
    }
    
    private void popolaListeEsami() {
        archivioEsami.leggiEsamiDisponibili(listaEsamiDisponibili);
        archivioEsami.leggiEsamiSvolti(listaEsamiSvolti);
    }

    public ObservableList<Esame> getListaEsamiSvolti() {return listaEsamiSvolti;}
    public ObservableList<Esame> getListaEsamiDisponibili() {return listaEsamiDisponibili;}
    
    private void notificaCambiamentoEsame(int index) { //(1)
        Esame daNotificare = listaEsamiSvolti.get(index);
        listaEsamiSvolti.remove(index);
        listaEsamiSvolti.add(index,daNotificare);
    }
    
    
    public ObservableList<Double>[] calcolaValoriMediaMobile(boolean caratterizzanti) {
        ObservableList<Double> ret[] = new  ObservableList[]{FXCollections.observableArrayList(),FXCollections.observableArrayList()};
        Integer contatoreAr=0,contatorePo=0;
        Double sommaAr=0.0,sommaPo=0.0;
        FilteredList<Esame> fl = listaEsamiSvolti.filtered(es -> (es.getId() >= 0 && (!caratterizzanti || es.getCaratterizzante())));
        for(Esame e:fl) {
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
    
    public Double getMedia(boolean ponderata,boolean caratterizzanti) {
        int indice = (ponderata)?1:0;
        ObservableList<Double> ret = calcolaValoriMediaMobile(caratterizzanti)[indice];
        if(ret.size() == 0) return 0.0;
        Double media = ret.get(ret.size()-1);
        return media;
    }
}


//(1)   Il pattern observer di ObservableList permette di osservare 
//      solo i cambiamenti "esterni" della lista (aggiunta,rimozione di elementi)
//      ma non i cambiamenti delle proprietà della lista, quando un esame viene modificato
//      si notifica agli osservatori rimuovendolo e reinserendolo nella stessa posizione.