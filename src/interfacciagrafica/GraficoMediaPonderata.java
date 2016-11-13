package interfacciagrafica;

import javafx.scene.chart.NumberAxis;
import modellodati.Esame;

class GraficoMediaPonderata extends GraficoMediaEsami {

    public GraficoMediaPonderata(NumberAxis n) {
        super(n,"ponderata");
    }
    @Override
    public Integer ottieniTermineSommatoria(Esame e) {
        return e.getCrediti()*e.getValutazione();
    }

    @Override
    public Integer ottieniIncrementoContatore(Esame e) {
        return e.getCrediti();
    }
    
}
