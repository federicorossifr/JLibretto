package interfacciagrafica;

class GraficoMediaAritmetica extends GraficoMediaEsami {

    public GraficoMediaAritmetica() {
        super("aritmetica");
    }

    @Override
    public Integer ottieniTermineSommatoria(int valutazione, int crediti) {
        return valutazione;
    }

    @Override
    public Integer ottieniIncrementoContatore(int valutazione, int crediti) {
        return 1;
    }
    
}
