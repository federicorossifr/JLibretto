
package jlibretto;

import java.time.LocalDate;
import java.util.Arrays;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class ExamForm extends GridPane {
    
    private TextField nameInput = new TextField();
    private TextField creditsInput = new TextField();
    private TextField markInput = new TextField();
    private DatePicker dateInput = new DatePicker();
    
    public ExamForm() {
        super();
        Label examName = new Label("Nome esame");
        Label examCredits = new Label("Crediti esame");
        Label examMark = new Label("Voto esame");
        Label examDate = new Label("Data esame");
        Button formAction = new Button("Inserisci");
        dateInput.setShowWeekNumbers(false);
        Node[] gridContent = new Node[]{examName,examCredits,examMark,examDate,nameInput,creditsInput,markInput,dateInput,formAction};
        setGridIndexes(gridContent,2,4);
        centerInGridPane(gridContent);
        getChildren().addAll(Arrays.asList(gridContent));
        formAction.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e) -> insertExam());
    }
    
    private void insertExam() {
        Exam insertedExam;
        String name = nameInput.getText();
        Integer credits = Integer.parseInt(creditsInput.getText());
        Integer mark = Integer.parseInt(markInput.getText());
        LocalDate d = dateInput.getValue();
        insertedExam = new Exam(name,mark,credits,d);
        boolean result = ExamStoringManager.getInstance().insertExam(insertedExam);
        
        if(result)
            ExamObservableList.getInstance().addExam(insertedExam);
        clearForm();
    }
    
    private void clearForm() {
        nameInput.clear();
        creditsInput.clear();
        markInput.clear();
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
