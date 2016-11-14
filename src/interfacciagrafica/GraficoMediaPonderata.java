package interfacciagrafica;
class GraficoMediaPonderata extends GraficoMediaEsami {

    public GraficoMediaPonderata() {
        super("ponderata");
    }

    @Override
    public Integer ottieniTermineSommatoria(int valutazione, int crediti) {
        return valutazione*crediti;
    }

    @Override
    public Integer ottieniIncrementoContatore(int valutazione, int crediti) {
        return crediti;
    }
}
