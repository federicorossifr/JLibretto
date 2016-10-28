package jlibretto;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

abstract class GraficoMediaEsami extends LineChart {
    
    public GraficoMediaEsami(NumberAxis n) {
        super(new CategoryAxis(),n);
        setLegendVisible(false);
        setTitle("Grafico media");
        RisorsaListaEsami.getIstanza().getListaEsami().addListener((ListChangeListener.Change<? extends Esame> c) -> {
            Double avg = aggiornaComponente((ObservableList<Esame>) c.getList());
            setTitle("Grafico media, media attuale: "+avg);
            
        });
        setAnimated(false);
    }
    
    public abstract Integer ottieniTermineSommatoria(Esame e);
    public abstract Integer ottieniIncrementoContatore(Esame e);
    
    public double aggiornaComponente(ObservableList<Esame> esami) {
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
