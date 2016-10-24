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
    
        TableColumn nameColumn = new TableColumn("Exam name");
        TableColumn creditsColumn = new TableColumn("Exam credits");
        TableColumn markColumn = new TableColumn("Exam mark");
        TableColumn dateColumn = new TableColumn("Exam data");
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
        
        private void completeCommit(Exam e,int rowIndex) {
            boolean result = ExamStoringManager.getInstance().editExam(e);
            System.out.println(result);
            if(result)
                ExamObservableList.getInstance().notifyChangedExam(rowIndex);
        }

        private void setupCellEditCommits() {
            nameColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<Exam, String>>() {
                    @Override
                    public void handle(CellEditEvent<Exam, String> t) {
                        int index = t.getTablePosition().getRow();
                        Exam edited = (ExamObservableList.getInstance().getExam(index));
                        edited.setName(t.getNewValue());
                        completeCommit(edited,index);
                    }
                }
            );
            markColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<Exam, Integer>>() {
                    @Override
                    public void handle(CellEditEvent<Exam, Integer> t) {
                        int index = t.getTablePosition().getRow();
                        Exam edited = (ExamObservableList.getInstance().getExam(index));                        
                        edited.setMark(t.getNewValue());
                        completeCommit(edited,index);
                    }
                }
            );
            creditsColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<Exam, Long>>() {
                    @Override
                    public void handle(CellEditEvent<Exam, Long> t) {
                        int index = t.getTablePosition().getRow();
                        Exam edited = (ExamObservableList.getInstance().getExam(index));                         
                        edited.setCredits(t.getNewValue().intValue());                        
                        completeCommit(edited,index);
                    }
                }
            );
            dateColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<Exam, String>>() {
                    @Override
                    public void handle(CellEditEvent<Exam, String> t) {
                        int index = t.getTablePosition().getRow();
                        Exam edited = (ExamObservableList.getInstance().getExam(index));                                                 
                        edited.setDate(t.getNewValue());   
                        completeCommit(edited,index);                        
                    }
                }
            );   
        }
}
