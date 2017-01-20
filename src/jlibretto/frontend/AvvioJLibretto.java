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

public class AvvioJLibretto extends Application{ //(1)
    private GraficoMediaEsami graficoMediaMobileEsami;
    private TabellaEsami tabellaEsami;
    private Button pulsanteElimina;
    private Label etichettaVotoLaurea;
    @Override
    public void start(Stage primaryStage) {
        ParametriConfigurazione pc = GestoreParametriConfigurazioneXML.ottieniParametriConfigurazione(); //(2)
        if(pc == null) {
            System.out.println("Errore di configurazione");
            Platform.exit();
            System.exit(-1);
        }                
        ClientLogAttivitaXML.inviaLogEventoApplicazione("JLibretto",0);
        StackPane root = new StackPane();
        root.getChildren().add(costruisciInterfacciaGrafica(pc));
        (new CacheInserimento()).impostaDatiCaricatiInTabella(tabellaEsami);
        Scene scene = new Scene(root, 800, 600);
        impostaAzioniChiusuraApplicazione(primaryStage);      
        scene.getStylesheets().add("file:../../res/stile.css");
        primaryStage.setTitle("JLibretto");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }
    
    private void impostaAzioniChiusuraApplicazione(Stage stage) { //(3)
        stage.setOnCloseRequest(e -> {
           (new CacheInserimento(tabellaEsami)).salvaDatiInCache();
           ClientLogAttivitaXML.inviaLogEventoApplicazione("JLibretto",1);       
        });
    }

    private VBox costruisciInterfacciaGrafica(ParametriConfigurazione pc) { //(4)
        pulsanteElimina = new Button("Elimina");
        ComboBox votoTesi = new ComboBox(FXCollections.observableArrayList(0,1,2,3));
        impostaAzioniMenuVotoTesi(votoTesi,pc.TipoMedia);
        votoTesi.setPromptText("Seleziona voto della tesi");
        etichettaVotoLaurea = new Label("Voto laurea:");
        impostaAzioniPulsanteElimina(pulsanteElimina);
        int valoreLode = pc.ValoreLode;
        tabellaEsami = new TabellaEsami(valoreLode);
        graficoMediaMobileEsami = new GraficoMediaEsami(pc.TipoMedia);
        HBox hb = new HBox();
        hb.getChildren().addAll(pulsanteElimina,votoTesi,etichettaVotoLaurea);
        VBox vb = new VBox();
        VBox.setVgrow(graficoMediaMobileEsami,Priority.ALWAYS);
        VBox.setVgrow(tabellaEsami, Priority.ALWAYS);
        HBox.setMargin(pulsanteElimina, new Insets(5,5,5,5));
        HBox.setMargin(votoTesi, new Insets(5,5,5,5));
        HBox.setMargin(etichettaVotoLaurea, new Insets(5,5,5,5));
        vb.getChildren().addAll(tabellaEsami,hb,graficoMediaMobileEsami);
        return vb;
    }
    
    private void impostaAzioniMenuVotoTesi(ComboBox c,String tipoMedia) { //(5)
        c.setOnAction(event -> {
            ClientLogAttivitaXML.inviaLogClickMenu("JLibretto", "Voto tesi");            
            int votoTesi = (int)c.getSelectionModel().getSelectedItem();
            Double mediaTot = ControlloreListaEsami.getIstanza().getMedia(tipoMedia.equals("ponderata"),false);
            if(mediaTot == 0) {
                etichettaVotoLaurea.setText("Voto laurea: ");
                return;
            }
            int mediaCar = (int)Math.round(ControlloreListaEsami.getIstanza().getMedia(tipoMedia.equals("ponderata"),true));
            Double passo = (30.0-18.0)/7;
            int votoCommissione = (mediaCar != 18 && mediaCar < 30 && mediaCar > 0)? (int)Math.ceil((mediaCar-18)/passo):0;
            votoCommissione = (mediaCar >= 30)? 7:votoCommissione;
            int votoLaurea = (int)Math.round(mediaTot*3+18+votoTesi+votoCommissione);
            votoLaurea = (votoLaurea>=110)? 110:votoLaurea;
            etichettaVotoLaurea.setText("Voto laurea: "+votoLaurea);
        });
    }
    
    private void impostaAzioniPulsanteElimina(Button b) { //(6)
        b.setOnAction(event -> {
            ClientLogAttivitaXML.inviaLogClickBottone("JLibretto", "Elimina");
            int i = tabellaEsami.getSelectionModel().getSelectedIndex();
            ControlloreListaEsami.getIstanza().eliminaEsame(i);
        });
    }
}

// (1): Classe entry point dell'applicazione JavaFX Jlibretto.
// (2): Reperimento dei parametri di configurazione per configurare le componenti grafiche successivamente
// (3): Aggiunta dell'handler per la gestione dei compiti alla chiusura dell'applicazione: salvataggio in cache
//      e invio del log al server di log.
// (4): Metodo che costruisce tutte le componenti grafiche dell'applicazione: tabella,grafico,pulsante di eliminazione e menu voto tesi
// (5): Aggiunta dell'handler per la gestione della selezione del voto della tesi: calcolo del voto di laurea
// (6): ""            ""             ""       del click sul pulsante di eliminazione di un esame.