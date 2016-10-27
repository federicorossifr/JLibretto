package jlibretto;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class JLibrettoMain extends Application {
    TabellaEsami examTable;
    FormInserimentoEsame examForm;
    GraficoMediaEsami mobileAvg;
    static Font bolder = Font.font(Font.getDefault().getFamily(),FontWeight.BOLD,Font.getDefault().getSize());
    static Font greater = Font.font(Font.getDefault().getFamily(),FontWeight.BOLD,Font.getDefault().getSize()+3);

    @Override
    public void start(Stage primaryStage) {
        caricaConfigurazioniXML();
        BorderPane mainPanel = new BorderPane();
        VBox examsContentPanel = makeExamsContentPanel();
        mainPanel.setCenter(examsContentPanel);
        StackPane root = new StackPane();
        root.getChildren().add(mainPanel);
        Scene scene = new Scene(root, 800, 600);
        impostaSalvataggioForm(primaryStage);
        primaryStage.setTitle("JLibretto");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
      
        
        System.out.println("Caricamento contenuto form da cache");
        FormCache.caricaDaCache(examForm);
       
    }
    
    private void impostaSalvataggioForm(Stage stage) {
        stage.setOnCloseRequest((WindowEvent we) -> {
           System.out.println("In fase di chiusura, salvataggio in cache del form.");
           FormCache.salvaInCache(examForm);
           System.out.println("Salvataggio completato.");
        });
    }
    
    private void caricaConfigurazioniXML() {
        GestoreConfigurazioniXML gcx = new GestoreConfigurazioniXML("configurazioni.xml","");
        if(!gcx.caricaConfigurazioni()) {
            Platform.exit();
            System.exit(-1);
        }
    }

    private VBox makeExamsContentPanel() {
        VBox vb = new VBox();
        examTable = new TabellaEsami();
        HBox hb = new HBox();
        examForm = makeExamForm();
        examForm.setVgap(5);
        examForm.setHgap(5);
        examForm.setAlignment(Pos.CENTER);
        NumberAxis na = new NumberAxis();
        na.setLowerBound(18);
        na.setUpperBound(33);
        mobileAvg = new GraficoMediaEsami(na);
        HBox.setHgrow(examForm,Priority.ALWAYS);
        HBox.setHgrow(mobileAvg,Priority.ALWAYS);
        hb.getChildren().addAll(examForm,mobileAvg);
        VBox.setVgrow(hb, Priority.ALWAYS);
        VBox.setVgrow(examTable, Priority.ALWAYS);
        vb.getChildren().addAll(examTable,hb);
        return vb;
    }
    
    private FormInserimentoEsame makeExamForm() {
        FormInserimentoEsame gp = new FormInserimentoEsame();
        return gp;
    }

    public static void main(String[] args) {
        System.out.println("Avvio applicazione...");
        launch(args);
        
    }

    
}
