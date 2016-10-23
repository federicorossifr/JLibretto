package jlibretto;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.NumberStringConverter;
public class ExamTableView extends TableView {
    
        TableColumn nameColumn = new TableColumn("Esame");
        TableColumn creditsColumn = new TableColumn("Crediti");
        TableColumn markColumn = new TableColumn("Voto");
        TableColumn dateColumn = new TableColumn("Data");
        public ExamTableView() {
            setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            creditsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
            markColumn.setCellValueFactory(new PropertyValueFactory<>("mark"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

            setupCellEditFormats();
            setupCellEditCommits();
            
            
            nameColumn.setSortable(false);
            creditsColumn.setSortable(false);
            markColumn.setSortable(false);            
            dateColumn.setSortable(false);
            setItems(ExamObservableList.getInstance().getExams());
            setEditable(true);
            getColumns().addAll(nameColumn,creditsColumn,markColumn,dateColumn);
        }

        private void setupCellEditFormats() {
            nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            creditsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));            
            markColumn.setCellFactory(ComboBoxTableCell.forTableColumn(Exam.defaultMarks));            
            dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        }

        private void setupCellEditCommits() {
            nameColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<Exam, String>>() {
                    @Override
                    public void handle(CellEditEvent<Exam, String> t) {
                        int index = t.getTablePosition().getRow();
                        (ExamObservableList.getInstance().getExam(index)).setName(t.getNewValue());
                        ExamObservableList.getInstance().notifyChangedExam(index);
                        
                    }
                }
            );
            markColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<Exam, Integer>>() {
                    @Override
                    public void handle(CellEditEvent<Exam, Integer> t) {
                        int index = t.getTablePosition().getRow();
                        (ExamObservableList.getInstance().getExam(index)).setMark(t.getNewValue().intValue());
                        ExamObservableList.getInstance().notifyChangedExam(index);
                        
                    }
                }
            );
            creditsColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<Exam, Long>>() {
                    @Override
                    public void handle(CellEditEvent<Exam, Long> t) {
                        int index = t.getTablePosition().getRow();
                        (ExamObservableList.getInstance().getExam(index)).setCredits(t.getNewValue().intValue());                        
                        ExamObservableList.getInstance().notifyChangedExam(index);                        
                    }
                }
            );
            dateColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<Exam, String>>() {
                    @Override
                    public void handle(CellEditEvent<Exam, String> t) {
                        int index = t.getTablePosition().getRow();
                        (ExamObservableList.getInstance().getExam(index)).setDate(t.getNewValue());   
                        ExamObservableList.getInstance().notifyChangedExam(index);                        
                    }
                }
            );   
        }
}
