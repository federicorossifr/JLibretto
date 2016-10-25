
package jlibretto;

import java.time.LocalDate;
import java.util.Arrays;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class FormInserimentoEsame extends GridPane {
    
    final TextField inputNomeEsame = new TextField();
    final TextField inputCodiceUtente = new PasswordField();
    final TextField inputCreditiEsame = new TextField();
    final ComboBox<Integer> inputValutazioneEsame = new ComboBox<>(Esame.listaVotiStandard);
    final DatePicker inputDataEsame = new DatePicker();
    final Button pulsanteInvioForm = new Button("Inserisci");
    final Button pulsanteApplicaCodiceUtente = new Button("Applica");
    public FormInserimentoEsame() {
        super();
        Label etichettaInputCodiceUtente = new Label("Codice utente");
        Label etichettaInputNomeEsame = new Label("Nome esame");
        Label etichettaInputCreditiEsame = new Label("Crediti esame");
        Label etichettaInputValutazioneEsame = new Label("Voto esame");
        Label etichettaInputDataEsame = new Label("Data esame");
        inputValutazioneEsame.setPromptText("Seleziona voto");
        inputValutazioneEsame.setEditable(true);
        
        inputDataEsame.setShowWeekNumbers(false);
        Node[] gridContent = new Node[]{etichettaInputCodiceUtente,null,etichettaInputNomeEsame,
                                        etichettaInputCreditiEsame,etichettaInputValutazioneEsame,etichettaInputDataEsame,
                                        null,inputCodiceUtente,pulsanteApplicaCodiceUtente,
                                        inputNomeEsame,inputCreditiEsame,inputValutazioneEsame,
                                        inputDataEsame,pulsanteInvioForm};
        impostaIndiciGriglia(gridContent,2,7);
        centraElementiInGriglia(gridContent);
        for(Node n:gridContent) {
            if(n == null) continue;
            getChildren().add(n);
        }
        pulsanteInvioForm.setDisable(true);
        pulsanteInvioForm.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e) -> creaEsame());
        pulsanteApplicaCodiceUtente.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e) -> applicaCodiceUtente());
    }
    
    private Integer ottieniValutazione() {
        Integer mark;
        Object tmpMark = inputValutazioneEsame.getValue();
        if(tmpMark instanceof String) 
            mark = Integer.parseInt((String)tmpMark);
        else if(tmpMark instanceof Integer)
            mark = (Integer)tmpMark;
        else
            throw new NumberFormatException();
        if(mark >=18 && mark<=33)
            return mark;
        else
            throw new NumberFormatException();
    }
    
    
    
    private void creaEsame() {
        try {
            Esame insertedExam;
            String name = inputNomeEsame.getText();
            Integer credits = Integer.parseInt(inputCreditiEsame.getText());
            Integer mark = ottieniValutazione();
            LocalDate d = inputDataEsame.getValue();
            String codiceUtente = inputCodiceUtente.getText();
            insertedExam = new Esame(name,mark,credits,d,codiceUtente);
            int insertedId = GestoreArchiviazioneEsami.getIstanza().inserisciEsame(insertedExam);
            if(insertedId > 0) {
                insertedExam.setId(insertedId);
                RisorsaListaEsami.getIstanza().aggiungiEsame(insertedExam);
            }
            pulisciForm();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
 
    private void applicaCodiceUtente() {
        try {
            String codice = inputCodiceUtente.getText();
            if(codice.length() < 8) return;
            inputCodiceUtente.setEditable(false);
            pulsanteInvioForm.setDisable(false);
            pulsanteApplicaCodiceUtente.setDisable(true);
            GestoreArchiviazioneEsami.getIstanza().leggiEsami(codice);
        } catch(Exception e) {
            System.out.println("Errore nella compilazione del codice utente: "+e.getMessage());
        }
        
    }
    private void pulisciForm() {
        inputNomeEsame.clear();
        inputCreditiEsame.clear();
        inputValutazioneEsame.getEditor().clear();
        inputDataEsame.getEditor().clear();
    }
    
    private  static void centraElementiInGriglia(Node[] list) {
        for(Node n:list) {
            if(n == null) continue;
            GridPane.setHalignment(n,HPos.CENTER);
            GridPane.setValignment(n,VPos.CENTER);
        }
    }
        
    private static void impostaIndiciGriglia(Node[] list,int columnNum,int rowNum) {
        for(int column = 0; column < columnNum; ++column) {
            for(int row = 0; row < rowNum; ++row) {
                if(list[row+rowNum*column]==null) continue;
                GridPane.setConstraints(list[row+rowNum*column],column+1,row+1);
            }
        }
    } 


}
