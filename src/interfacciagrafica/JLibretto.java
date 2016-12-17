package interfacciagrafica;

import logattivita.ClientLogAttivitaXML;
import configurazione.*;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        //scene.getStylesheets().add("file:./res/stile.css");
        primaryStage.setTitle("JLibretto");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        System.out.println("Caricamento contenuto form da cache");
    }
    
    private void impostaAzioniChiusuraApplicazione(Stage stage) {
        stage.setOnCloseRequest((WindowEvent we) -> {
           System.out.println("In fase di chiusura, salvataggio in cache del form.");
           //formEsami.salvaContenuto();
           System.out.println("Salvataggio completato.");
           ClientLogAttivitaXML.inviaLogEventoApplicazione("JLibretto",1);       
        });
    }

    private VBox costruisciPannelloEsamiPrincipale() {
        PulsanteElimina pulsanteElimina = new PulsanteElimina();
        
        try {
            int vm = GestoreConfigurazioniXML.ottieni().Preferenze.MinValutazione;
            int vM = GestoreConfigurazioniXML.ottieni().Preferenze.MaxValutazione;
            ObservableList<Integer> listaVoti = FXCollections.observableArrayList();
            for(int i = vm;i<=vM;++i) listaVoti.add(i);
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
        vb.getChildren().addAll(tabellaEsami,pulsanteElimina,graficoMediaMobileEsami);
        return vb;
    }
    
    private GraficoMediaEsami creaGraficoEsami() {
        String tipoMedia = "";
        try {
            tipoMedia = GestoreConfigurazioniXML.ottieni().Preferenze.TipoMedia;
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
