package jlibretto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.application.Application;
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
        BorderPane mainPanel = new BorderPane();
        VBox examsContentPanel = makeExamsContentPanel();
        mainPanel.setCenter(examsContentPanel);
        StackPane root = new StackPane();
        root.getChildren().add(mainPanel);
        Scene scene = new Scene(root, 1024, 600);
        
        primaryStage.setTitle("JLibretto");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
           System.out.println("Closing,saving form cache...");
           formToCache(); 
           System.out.println("Saved.");
        });
        cacheToForm();
        
        
        GestoreArchiviazioneEsami.getInstance().readExams();
    }
    
    private void formToCache() {
        try {
                ObjectOutputStream toBin = new ObjectOutputStream(new FileOutputStream("cache.bin"));
                String name,credits,mark,date;
                try {
                    name = examForm.nameInput.getText();
                } catch(Exception e) {
                    name = "";
                }
                try {
                    credits = examForm.creditsInput.getText();
                } catch(Exception e) {
                    credits = "";
                }
                try {
                    mark = examForm.markInput.getEditor().getText();
                } catch(Exception e) {
                    mark = "";
                }
                try {
                    date = examForm.dateInput.getEditor().getText();
                } catch(Exception e) {
                    date = "";
                }
                toBin.writeObject(new FormCache(name,credits,mark,date));
            } catch(Exception e) {
                System.out.println("Erore nel salvare la cache form: "+e.getMessage());
        } 
    }
    private void cacheToForm() {
        try {
            ObjectInputStream fromBin = new ObjectInputStream(new FileInputStream("cache.bin"));
            FormCache formCachedData = (FormCache)fromBin.readObject();
            examForm.creditsInput.setText(formCachedData.creditsBinaryInput);
            examForm.markInput.getEditor().setText(formCachedData.markBinaryInput);
            examForm.dateInput.getEditor().setText(formCachedData.dateBinaryInput);
            examForm.nameInput.setText(formCachedData.nameBinaryInput);
        } catch(IOException | ClassNotFoundException e) {
            System.out.println("Errore nel caricare la cache form: "+e.getMessage());
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
        launch(args);
        
    }

    
}
