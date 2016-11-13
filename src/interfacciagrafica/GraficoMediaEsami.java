package interfacciagrafica;

import java.text.DecimalFormat;
import javafx.collections.*;
import javafx.scene.chart.*;
import modellodati.*;

abstract class GraficoMediaEsami extends LineChart {
    private final String tipoMedia;
    public GraficoMediaEsami(NumberAxis n,String tipoM) {
        super(new CategoryAxis(),n);
        tipoMedia = tipoM;        
        setLegendVisible(false);
        setTitle("Grafico media ("+tipoMedia+")");            
        RisorsaListaEsami.getIstanza().getListaEsami().addListener((ListChangeListener.Change<? extends Esame> c) -> {
            Double mediaFinale = aggiornaComponente((ObservableList<Esame>) c.getList());
            String titoloGrafico = "Grafico media ("+tipoMedia+")";
            DecimalFormat formattatoreMedia = new DecimalFormat("#.##");
            String mediaFormattata = formattatoreMedia.format(mediaFinale);
            if(mediaFinale > 0)
                titoloGrafico+= ", media attuale: "+mediaFormattata;
            setTitle(titoloGrafico);   
        });
        setAnimated(false);
    }
    
    public abstract Integer ottieniTermineSommatoria(Esame e);
    public abstract Integer ottieniIncrementoContatore(Esame e);
    
    public final double aggiornaComponente(ObservableList<Esame> esami) {
        Double sommatoria = 0.0;
        Integer contatore = 0;
        Double iterazioneMediaMobile = 0.0;
        Series<String,Double> valoriMediaMobile;        
        valoriMediaMobile = new Series<>();
        for(Esame e:esami) {
            sommatoria+=ottieniTermineSommatoria(e);
            contatore+=ottieniIncrementoContatore(e);
            iterazioneMediaMobile = sommatoria/contatore;
            valoriMediaMobile.getData().add(new XYChart.Data(e.getNome(),iterazioneMediaMobile));
        }
        setData(FXCollections.observableArrayList(valoriMediaMobile));
        return iterazioneMediaMobile;
    }
 }
