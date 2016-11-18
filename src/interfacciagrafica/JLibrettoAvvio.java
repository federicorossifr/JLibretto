package interfacciagrafica;

import logattivita.ClientLogAttivitaXML;
import configurazione.*;
import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.*;

public class JLibrettoAvvio extends Application{
    private FormInserimentoEsame formEsami;
    private GraficoMediaEsami graficoMediaMobileEsami;
    private TabellaEsami tabellaEsami;

    @Override
    public void start(Stage primaryStage) {
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
        ClientLogAttivitaXML.inviaLogEventoApplicazione("JLibretto",0);
    }
    
    private void impostaAzioniChiusuraApplicazione(Stage stage) {
        stage.setOnCloseRequest((WindowEvent we) -> {
           System.out.println("In fase di chiusura, salvataggio in cache del form.");
           formEsami.salvaContenuto();
           System.out.println("Salvataggio completato.");
           ClientLogAttivitaXML.inviaLogEventoApplicazione("JLibretto",1);       
        });
    }

    private VBox costruisciPannelloEsamiPrincipale() {
        try {
            ObservableList<Integer> listaVoti = GestoreConfigurazioniXML.getIstanza().getListaVotiDiponibili();
            tabellaEsami = new TabellaEsami(listaVoti);
            formEsami = costruisciFormInserimentoEsame(listaVoti);
        } catch(Exception e) {
            Platform.exit();
            System.exit(-1);
        }
        
        VBox vb = new VBox();
        HBox hb = new HBox();
        graficoMediaMobileEsami = creaGraficoEsami();
        HBox.setHgrow(formEsami,Priority.ALWAYS);
        HBox.setHgrow(graficoMediaMobileEsami,Priority.ALWAYS);
        hb.getChildren().addAll(formEsami,graficoMediaMobileEsami);
        VBox.setVgrow(hb, Priority.ALWAYS);
        VBox.setVgrow(tabellaEsami, Priority.ALWAYS);
        vb.getChildren().addAll(tabellaEsami,hb);
        return vb;
    }
    
    private GraficoMediaEsami creaGraficoEsami() {
        String tipoMedia = "";
        try {
            tipoMedia = GestoreConfigurazioniXML.getIstanza().getTipoMedia();
        } catch(ConfigurazioniNonDisponibiliException e) {
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
    
    private FormInserimentoEsame costruisciFormInserimentoEsame(ObservableList<Integer> listaVoti) {
        FormInserimentoEsame gp = new FormInserimentoEsame(listaVoti);
        return gp;
    }

    public static void main(String[] args) {
        System.out.println("Avvio applicazione...");
        launch(args);
        
    }
}
