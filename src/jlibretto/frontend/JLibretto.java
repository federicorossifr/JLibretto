package jlibretto.frontend;

import jlibretto.middleware.CacheInserimento;
import jlibretto.middleware.ControlloreListaEsami;
import jlibretto.middleware.ClientLogAttivitaXML;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class JLibretto extends Application{
    private GraficoMediaEsami graficoMediaMobileEsami;
    private TabellaEsami tabellaEsami;
    private Button pulsanteElimina;
    @Override
    public void start(Stage primaryStage) {
        ClientLogAttivitaXML.inviaLogEventoApplicazione("JLibretto",0);
        ParametriConfigurazione pc = GestoreParametriConfigurazioneXML.ottieniParametriConfigurazione();
        if(pc == null) {
            System.out.println("Errore di configurazione");
            Platform.exit();
            System.exit(-1);
        }                
        StackPane root = new StackPane();
        root.getChildren().add(costruisciInterfacciaGrafica(pc));
        (new CacheInserimento()).impostaDatiCaricatiInTabella(tabellaEsami);
        Scene scene = new Scene(root, 800, 600);
        impostaAzioniChiusuraApplicazione(primaryStage);      
        scene.getStylesheets().add("file:./res/stile.css");
        primaryStage.setTitle("JLibretto");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }
    
    private void impostaAzioniChiusuraApplicazione(Stage stage) {
        stage.setOnCloseRequest(e -> {
           (new CacheInserimento(tabellaEsami)).salvaDatiInCache();
           ClientLogAttivitaXML.inviaLogEventoApplicazione("JLibretto",1);       
        });
    }

    private VBox costruisciInterfacciaGrafica(ParametriConfigurazione pc) {
        pulsanteElimina = new Button("Elimina");
        impostaAzioniPulsanteElimina(pulsanteElimina);
        int valoreLode = pc.ValoreLode;
        tabellaEsami = new TabellaEsami(valoreLode);
        graficoMediaMobileEsami = new GraficoMediaEsami(pc.TipoMedia);
        VBox vb = new VBox();
        VBox.setVgrow(graficoMediaMobileEsami,Priority.ALWAYS);
        VBox.setVgrow(tabellaEsami, Priority.ALWAYS);
        VBox.setMargin(pulsanteElimina, new Insets(5,5,5,5));
        vb.getChildren().addAll(tabellaEsami,pulsanteElimina,graficoMediaMobileEsami);
        return vb;
    }
    
    private void impostaAzioniPulsanteElimina(Button b) {
        b.setOnAction(event -> {
            ClientLogAttivitaXML.inviaLogClickBottone("JLibretto", "Elimina");
            int i = tabellaEsami.getSelectionModel().getSelectedIndex();
            ControlloreListaEsami.getIstanza().eliminaEsame(i);
        });
    }
}
