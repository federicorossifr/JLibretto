////////////////////////////////////77
package jlibretto.frontend;

import jlibretto.middleware.Esame;
import jlibretto.middleware.ControlloreListaEsami;
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
        ObservableList<Double> medieMobili[] = ControlloreListaEsami.getIstanza().calcolaValoriMediaMobile();
        Series<String,Double> valoriMediaMobile = new Series<>();        
        getData().clear();
        Double iterazioneMediaMobile = 0.0;
        for(int i = 0; i < fl.size();++i) {
            iterazioneMediaMobile = (tipoMedia.equals("aritmetica"))? medieMobili[0].get(i):medieMobili[1].get(i);
            valoriMediaMobile.getData().add(new XYChart.Data(fl.get(i).getNome(),iterazioneMediaMobile));
        }
        setData(FXCollections.observableArrayList(valoriMediaMobile));
        String titoloGrafico = "Grafico media ("+tipoMedia+")";
        if(iterazioneMediaMobile > 0)
            titoloGrafico+= ", media attuale: "+new DecimalFormat("#.##").format(iterazioneMediaMobile);
        setTitle(titoloGrafico);           
    }
 }
