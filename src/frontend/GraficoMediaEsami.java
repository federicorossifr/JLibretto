package frontend;

import middleware.Esame;
import middleware.ControlloreListaEsami;
import java.text.DecimalFormat;
import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.scene.chart.*;

class GraficoMediaEsami extends LineChart {
    private final String tipoMedia;
    GraficoMediaEsami(String tipoM) {
        super(new CategoryAxis(),(new NumberAxis()));
        tipoMedia = tipoM;        
        setLegendVisible(false);
        setAnimated(false);
        ControlloreListaEsami.getIstanza().getListaEsamiSvolti().addListener((ListChangeListener.Change<? extends Esame> c) -> {
            aggiornaComponente(((ObservableList<Esame>)c.getList()));
        });
        aggiornaComponente(ControlloreListaEsami.getIstanza().getListaEsamiSvolti());
    }
    
    
    private void aggiornaComponente(ObservableList<Esame> esami) {
        FilteredList<Esame> fl = esami.filtered(e-> e.getId() >= 0);
        Double sommatoria = 0.0;
        Integer contatore = 0;
        Double iterazioneMediaMobile = 0.0;
        Series<String,Double> valoriMediaMobile;        
        valoriMediaMobile = new Series<>();
        getData().clear();
        for(Esame e:fl) {
            switch(tipoMedia) {
                case "ponderata": sommatoria+=(e.getValutazione()*e.getCrediti());
                                  contatore+=e.getCrediti();
                                  break;
                case "aritmetica": sommatoria+=(e.getValutazione()*e.getCrediti());
                                   contatore++;
                                   break;
                default:break;
            }
            iterazioneMediaMobile = sommatoria/contatore;
            valoriMediaMobile.getData().add(new XYChart.Data(e.getNome(),iterazioneMediaMobile));
        }
        setData(FXCollections.observableArrayList(valoriMediaMobile));
        String titoloGrafico = "Grafico media ("+tipoMedia+")";
        DecimalFormat formattatoreMedia = new DecimalFormat("#.##");
        String mediaFormattata = formattatoreMedia.format(iterazioneMediaMobile);
        if(iterazioneMediaMobile > 0)
            titoloGrafico+= ", media attuale: "+mediaFormattata;
        setTitle(titoloGrafico);           
    }
 }
