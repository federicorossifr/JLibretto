/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlibretto;

import javafx.scene.chart.NumberAxis;

/**
 *
 * @author feder
 */
public class GraficoMediaAritmetica extends GraficoMediaEsami {

    public GraficoMediaAritmetica(NumberAxis n) {
        super(n);
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
