/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfacciagrafica;

import javafx.scene.chart.NumberAxis;
import modellodati.Esame;

/**
 *
 * @author feder
 */
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
