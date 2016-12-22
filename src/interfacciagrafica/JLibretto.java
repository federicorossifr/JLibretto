package interfacciagrafica;

import logattivita.ClientLogAttivitaXML;
import configurazione.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.*;

public class JLibretto extends Application{
    private GraficoMediaEsami graficoMediaMobileEsami;
    private TabellaEsami tabellaEsami;

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
           tabellaEsami.salvaDatiInseritiInCache();
           ClientLogAttivitaXML.inviaLogEventoApplicazione("JLibretto",1);       
        });
    }

    private VBox costruisciInterfacciaGrafica(ParametriConfigurazione pc) {
        PulsanteElimina pulsanteElimina = new PulsanteElimina();
        int valoreLode = pc.ValoreLode;
        tabellaEsami = new TabellaEsami(valoreLode,pulsanteElimina);
        graficoMediaMobileEsami = costruisciGraficoEsami(pc);
        VBox vb = new VBox();
        VBox.setVgrow(graficoMediaMobileEsami,Priority.ALWAYS);
        VBox.setVgrow(tabellaEsami, Priority.ALWAYS);
        VBox.setMargin(pulsanteElimina, new Insets(5,5,5,5));
        vb.getChildren().addAll(tabellaEsami,pulsanteElimina,graficoMediaMobileEsami);
        return vb;
    }
    
    private GraficoMediaEsami costruisciGraficoEsami(ParametriConfigurazione pc) {
        switch(pc.TipoMedia) {
            case "aritmetica": return new GraficoMediaAritmetica();
            case "ponderata": return new GraficoMediaPonderata();
            default: return new GraficoMediaAritmetica();
        }
    }

}
