package interfacciagrafica;

import javafx.scene.chart.NumberAxis;
import modellodati.Esame;

class GraficoMediaAritmetica extends GraficoMediaEsami {

    public GraficoMediaAritmetica(NumberAxis n) {
        super(n,"aritmetica");
    }
    @Override
    public Integer ottieniTermineSommatoria(Esame e) {
        return e.getValutazione();
    }

    @Override
    public Integer ottieniIncrementoContatore(Esame e) {
        return 1;
    }
    
}
