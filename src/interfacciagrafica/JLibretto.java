package interfacciagrafica;

import logattivita.ClientLogAttivitaXML;
import configurazione.*;
import javafx.application.*;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.*;

public class JLibretto extends Application{
    private GraficoMediaEsami graficoMediaMobileEsami;
    private TabellaEsami tabellaEsami;

    @Override
    public void start(Stage primaryStage) {
        ClientLogAttivitaXML.inviaLogEventoApplicazione("JLibretto",0);
        BorderPane mainPanel = new BorderPane();
        VBox examsContentPanel = costruisciPannelloEsamiPrincipale();
        mainPanel.setCenter(examsContentPanel);
        StackPane root = new StackPane();
        root.getChildren().add(mainPanel);
        Scene scene = new Scene(root, 800, 600);
        impostaAzioniChiusuraApplicazione(primaryStage);
        scene.getStylesheets().add("file:./res/stile.css");
        primaryStage.setTitle("JLibretto");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        System.out.println("Caricamento contenuto form da cache");
    }
    
    private void impostaAzioniChiusuraApplicazione(Stage stage) {
        stage.setOnCloseRequest((WindowEvent we) -> {
           System.out.println("In fase di chiusura, salvataggio in cache del form.");
           tabellaEsami.salvaDatiInseritiInCache();
           System.out.println("Salvataggio completato.");
           ClientLogAttivitaXML.inviaLogEventoApplicazione("JLibretto",1);       
        });
    }

    private VBox costruisciPannelloEsamiPrincipale() {
        PulsanteElimina pulsanteElimina = new PulsanteElimina();
        
        try {
            int valoreLode = GestoreParametriConfigurazioneXML.ottieniParametriConfigurazione().ValoreLode;
            ObservableList<Integer> listaVoti = FXCollections.observableArrayList();
            for(int i = 18;i<=30;++i) listaVoti.add(i);
            listaVoti.add(valoreLode);
            tabellaEsami = new TabellaEsami(listaVoti,pulsanteElimina);
        } catch(Exception e) {
            e.printStackTrace();
            Platform.exit();
            System.exit(-1);
        }
        graficoMediaMobileEsami = creaGraficoEsami();
        
        VBox vb = new VBox();
        VBox.setVgrow(graficoMediaMobileEsami,Priority.ALWAYS);
        VBox.setVgrow(tabellaEsami, Priority.ALWAYS);
        VBox.setMargin(pulsanteElimina, new Insets(5,5,5,5));
        vb.getChildren().addAll(tabellaEsami,pulsanteElimina,graficoMediaMobileEsami);
        return vb;
    }
    
    private GraficoMediaEsami creaGraficoEsami() {
        String tipoMedia = "";
        try {
            tipoMedia = GestoreParametriConfigurazioneXML.ottieniParametriConfigurazione().TipoMedia;
        } catch(Exception e) {
            System.out.println("Errore nel caricamento delle configurazioni");
            Platform.exit();
            System.exit(1);
        }
        switch(tipoMedia) {
            case "aritmetica": return new GraficoMediaAritmetica();
            case "ponderata": return new GraficoMediaPonderata();
            default: return new GraficoMediaAritmetica();
        }
    }

}
