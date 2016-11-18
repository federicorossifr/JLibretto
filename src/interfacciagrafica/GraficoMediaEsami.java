package interfacciagrafica;

import java.text.DecimalFormat;
import javafx.collections.*;
import javafx.scene.chart.*;
import modellodati.*;

abstract class GraficoMediaEsami extends LineChart {
    private final String tipoMedia;
    public GraficoMediaEsami(String tipoM) {
        super(new CategoryAxis(),(new NumberAxis()));
        tipoMedia = tipoM;        
        setLegendVisible(false);
        setTitle("Grafico media ("+tipoMedia+")");            
        ControlloreListaEsami.getIstanza().getListaEsami().addListener((ListChangeListener.Change<? extends Esame> c) -> {
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
    
    public abstract Integer ottieniTermineSommatoria(int valutazione,int crediti);
    public abstract Integer ottieniIncrementoContatore(int valutazione,int crediti);
    
    public final double aggiornaComponente(ObservableList<Esame> esami) {
        Double sommatoria = 0.0;
        Integer contatore = 0;
        Double iterazioneMediaMobile = 0.0;
        Series<String,Double> valoriMediaMobile;        
        valoriMediaMobile = new Series<>();
        for(Esame e:esami) {
            sommatoria+=ottieniTermineSommatoria(e.getValutazione(),e.getCrediti());
            contatore+=ottieniIncrementoContatore(e.getValutazione(),e.getCrediti());
            iterazioneMediaMobile = sommatoria/contatore;
            valoriMediaMobile.getData().add(new XYChart.Data(e.getNome(),iterazioneMediaMobile));
        }
        setData(FXCollections.observableArrayList(valoriMediaMobile));
        return iterazioneMediaMobile;
    }
 }
