
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class FormInserimentoEsame extends GridPane {
    
    final TextField inputNomeEsame = new TextField();
    final TextField inputCreditiEsame = new TextField();
    final ComboBox<Integer> inputValutazioneEsame = new ComboBox<>(Esame.listaVotiStandard);
    final DatePicker inputDataEsame = new DatePicker();
    
    public FormInserimentoEsame() {
        super();
        Label examName = new Label("Nome esame");
        Label examCredits = new Label("Crediti esame");
        Label examMark = new Label("Voto esame");
        Label examDate = new Label("Data esame");
        inputValutazioneEsame.setPromptText("Seleziona voto");
        inputValutazioneEsame.setEditable(true);
        Button formAction = new Button("Inserisci");
        inputDataEsame.setShowWeekNumbers(false);
        Node[] gridContent = new Node[]{examName,examCredits,examMark,examDate,inputNomeEsame,inputCreditiEsame,inputValutazioneEsame,inputDataEsame,formAction};
        impostaIndiciGriglia(gridContent,2,4);
        centraElementiInGriglia(gridContent);
        getChildren().addAll(Arrays.asList(gridContent));
        formAction.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e) -> creaEsame());
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
            insertedExam = new Esame(name,mark,credits,d);
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
    
    private void pulisciForm() {
        inputNomeEsame.clear();
        inputCreditiEsame.clear();
        inputValutazioneEsame.getEditor().clear();
        inputDataEsame.getEditor().clear();
    }
    
    private  static void centraElementiInGriglia(Node[] list) {
        for(Node n:list) {
            GridPane.setHalignment(n,HPos.CENTER);
            GridPane.setValignment(n,VPos.CENTER);
        }
    }
        
    private static void impostaIndiciGriglia(Node[] list,int columnNum,int rowNum) {
        for(int column = 0; column < columnNum; ++column)
            for(int row = 0; row < rowNum; ++row)
                GridPane.setConstraints(list[row+rowNum*column],column+1,row+1);
        GridPane.setConstraints(list[list.length-1],columnNum,rowNum+1);
    } 
}
