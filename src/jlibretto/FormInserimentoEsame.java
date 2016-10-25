
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
    
    final TextField nameInput = new TextField();
    final TextField creditsInput = new TextField();
    final ComboBox<Integer> markInput = new ComboBox<>(Esame.listaVotiStandard);
    final DatePicker dateInput = new DatePicker();
    
    public FormInserimentoEsame() {
        super();
        Label examName = new Label("Exam name");
        Label examCredits = new Label("Exam credits");
        Label examMark = new Label("Exam mark");
        Label examDate = new Label("Exam date");
        markInput.setPromptText("Select mark");
        markInput.setEditable(true);
        Button formAction = new Button("Insert");
        dateInput.setShowWeekNumbers(false);
        Node[] gridContent = new Node[]{examName,examCredits,examMark,examDate,nameInput,creditsInput,markInput,dateInput,formAction};
        setGridIndexes(gridContent,2,4);
        centerInGridPane(gridContent);
        getChildren().addAll(Arrays.asList(gridContent));
        formAction.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e) -> insertExam());
    }
    
    private Integer getMarkFromComboBox(ComboBox input) {
        Integer mark;
        Object tmpMark = input.getValue();
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
    
    
    
    private void insertExam() {
        try {
            Esame insertedExam;
            String name = nameInput.getText();
            Integer credits = Integer.parseInt(creditsInput.getText());
            Integer mark = getMarkFromComboBox(markInput);
            LocalDate d = dateInput.getValue();
            insertedExam = new Esame(name,mark,credits,d);
            int insertedId = GestoreArchiviazioneEsami.getInstance().insertExam(insertedExam);
            if(insertedId > 0) {
                insertedExam.setId(insertedId);
                RisorsaListaEsami.getInstance().addExam(insertedExam);
            }
            clearForm();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void clearForm() {
        nameInput.clear();
        creditsInput.clear();
        markInput.getEditor().clear();
        dateInput.getEditor().clear();
    }
    
    private  static void centerInGridPane(Node[] list) {
        for(Node n:list) {
            GridPane.setHalignment(n,HPos.CENTER);
            GridPane.setValignment(n,VPos.CENTER);
        }
    }
        
    private static void setGridIndexes(Node[] list,int columnNum,int rowNum) {
        for(int column = 0; column < columnNum; ++column)
            for(int row = 0; row < rowNum; ++row)
                GridPane.setConstraints(list[row+rowNum*column],column+1,row+1);
        GridPane.setConstraints(list[list.length-1],columnNum,rowNum+1);
    } 
}
