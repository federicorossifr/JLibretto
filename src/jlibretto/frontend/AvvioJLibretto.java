package jlibretto.frontend;

import jlibretto.middleware.CacheInserimento;
import jlibretto.middleware.ControlloreListaEsami;
import jlibretto.middleware.ClientLogAttivitaXML;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class AvvioJLibretto extends Application{
    private GraficoMediaEsami graficoMediaMobileEsami;
    private TabellaEsami tabellaEsami;
    private Button pulsanteElimina;
    private Label outputVotoLaurea;
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
        ComboBox votoTesi = new ComboBox(FXCollections.observableArrayList(0,1,2,3));
        impostaAzioniMenuVotoTesi(votoTesi,pc.TipoMedia);
        votoTesi.setPromptText("Seleziona voto della tesi");
        outputVotoLaurea = new Label("Voto laurea:");
        impostaAzioniPulsanteElimina(pulsanteElimina);
        int valoreLode = pc.ValoreLode;
        tabellaEsami = new TabellaEsami(valoreLode);
        graficoMediaMobileEsami = new GraficoMediaEsami(pc.TipoMedia);
        HBox hb = new HBox();
        hb.getChildren().addAll(pulsanteElimina,votoTesi,outputVotoLaurea);
        VBox vb = new VBox();
        VBox.setVgrow(graficoMediaMobileEsami,Priority.ALWAYS);
        VBox.setVgrow(tabellaEsami, Priority.ALWAYS);
        HBox.setMargin(pulsanteElimina, new Insets(5,5,5,5));
        HBox.setMargin(votoTesi, new Insets(5,5,5,5));
        HBox.setMargin(outputVotoLaurea, new Insets(5,5,5,5));
        vb.getChildren().addAll(tabellaEsami,hb,graficoMediaMobileEsami);
        return vb;
    }
    
    private void impostaAzioniMenuVotoTesi(ComboBox c,String tipoMedia) {
        c.setOnAction(event -> {
            int votoTesi = (int)c.getSelectionModel().getSelectedItem();
            Double mediaTot = ControlloreListaEsami.getIstanza().getMedia(tipoMedia.equals("ponderata"),false);
            if(mediaTot == 0) {
                outputVotoLaurea.setText("Voto laurea: ");
                return;
            }
            int mediaCar = (int)Math.round(ControlloreListaEsami.getIstanza().getMedia(tipoMedia.equals("ponderata"),true));
            Double passo = (30.0-18.0)/7;
            int votoCommissione = (mediaCar != 18 && mediaCar < 30 && mediaCar > 0)? (int)Math.ceil((mediaCar-18)/passo):0;
            votoCommissione = (mediaCar >= 30)? 7:votoCommissione;
            int votoLaurea = (int)Math.round(mediaTot*3+18+votoTesi+votoCommissione);
            votoLaurea = (votoLaurea>=110)? 110:votoLaurea;
            outputVotoLaurea.setText("Voto laurea: "+votoLaurea);
        });
    }
    
    private void impostaAzioniPulsanteElimina(Button b) {
        b.setOnAction(event -> {
            ClientLogAttivitaXML.inviaLogClickBottone("JLibretto", "Elimina");
            int i = tabellaEsami.getSelectionModel().getSelectedIndex();
            ControlloreListaEsami.getIstanza().eliminaEsame(i);
        });
    }
}
