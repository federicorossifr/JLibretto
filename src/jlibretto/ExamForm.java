
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

public class ExamForm extends GridPane {
    
    private final TextField nameInput = new TextField();
    private final TextField creditsInput = new TextField();
    private final ComboBox<Integer> markInput = new ComboBox<>(Exam.defaultMarks);
    private final DatePicker dateInput = new DatePicker();
    
    public ExamForm() {
        super();
        Label examName = new Label("Nome esame");
        Label examCredits = new Label("Crediti esame");
        Label examMark = new Label("Voto esame");
        Label examDate = new Label("Data esame");
        markInput.setPromptText("Seleziona valutazione");
        markInput.setEditable(true);
        Button formAction = new Button("Inserisci");
        dateInput.setShowWeekNumbers(false);
        Node[] gridContent = new Node[]{examName,examCredits,examMark,examDate,nameInput,creditsInput,markInput,dateInput,formAction};
        setGridIndexes(gridContent,2,4);
        centerInGridPane(gridContent);
        getChildren().addAll(Arrays.asList(gridContent));
        formAction.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e) -> insertExam());
    }
    
    private static Integer getMark(ComboBox input) {
        Integer mark;
        Object tmpMark = input.getValue();
        if(tmpMark instanceof String) 
            mark = Integer.parseInt((String)tmpMark);
        else if(tmpMark instanceof Integer)
            mark = (Integer)tmpMark;
        else
            throw new NumberFormatException("Non è stato inserito un numero");
        if(mark >=18 && mark<=33)
            return mark;
        else
            throw new NumberFormatException("La valutazione non rientra nell'intervallo [18,33]");
    }
 
    private void insertExam() {
        try {
            Exam insertedExam;
            String name = nameInput.getText();
            Integer credits = Integer.parseInt(creditsInput.getText());
            Integer mark = getMark(markInput);
            LocalDate d = dateInput.getValue();
            insertedExam = new Exam(name,mark,credits,d);
            boolean result = ExamStoringManager.getInstance().insertExam(insertedExam);
            if(result)
                ExamObservableList.getInstance().addExam(insertedExam);
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
            GridPane.setHalignment(n,HPos.LEFT);
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
