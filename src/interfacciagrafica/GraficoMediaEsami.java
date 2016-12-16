package interfacciagrafica;

import java.text.DecimalFormat;
import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.scene.chart.*;
import modellodati.*;

abstract class GraficoMediaEsami extends LineChart {
    private final String tipoMedia;
    public GraficoMediaEsami(String tipoM) {
        super(new CategoryAxis(),(new NumberAxis()));
        tipoMedia = tipoM;        
        setLegendVisible(false);
        setTitle("Grafico media ("+tipoMedia+")");            
        setAnimated(false);
        ControlloreListaEsami.getIstanza().getListaEsami().addListener((ListChangeListener.Change<? extends Esame> c) -> {
            aggiornaComponente(((ObservableList<Esame>)c.getList()).filtered(e -> e.getId() >= 0));
        });
        aggiornaComponente(ControlloreListaEsami.getIstanza().getListaEsami().filtered(e -> e.getId() >= 0));
    }
    
    public abstract Integer ottieniTermineSommatoria(int valutazione,int crediti);
    public abstract Integer ottieniIncrementoContatore(int valutazione,int crediti);
    
    public final void aggiornaComponente(FilteredList<Esame> esami) {
        Double sommatoria = 0.0;
        Integer contatore = 0;
        Double iterazioneMediaMobile = 0.0;
        Series<String,Double> valoriMediaMobile;        
        valoriMediaMobile = new Series<>();
        for(Esame e:esami) {
            System.out.println(e.getId());
            sommatoria+=ottieniTermineSommatoria(e.getValutazione(),e.getCrediti());
            contatore+=ottieniIncrementoContatore(e.getValutazione(),e.getCrediti());
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
