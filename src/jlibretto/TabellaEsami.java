package jlibretto;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.NumberStringConverter;
public class TabellaEsami extends TableView {
    
        TableColumn colonnaNome = new TableColumn("Exam name");
        TableColumn creditsColumn = new TableColumn("Exam credits");
        TableColumn markColumn = new TableColumn("Exam mark");
        TableColumn dateColumn = new TableColumn("Exam data");
        public TabellaEsami() {
            setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            colonnaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            creditsColumn.setCellValueFactory(new PropertyValueFactory<>("crediti"));
            markColumn.setCellValueFactory(new PropertyValueFactory<>("voto"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("data"));

            setupCellEditFormats();
            setupCellEditCommits();
            
            
            colonnaNome.setSortable(false);
            creditsColumn.setSortable(false);
            markColumn.setSortable(false);            
            dateColumn.setSortable(false);
            setItems(RisorsaListaEsami.getInstance().getExams());
            setEditable(true);
            getColumns().addAll(colonnaNome,creditsColumn,markColumn,dateColumn);
        }

        private void setupCellEditFormats() {
            colonnaNome.setCellFactory(TextFieldTableCell.forTableColumn());
            creditsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));            
            markColumn.setCellFactory(ComboBoxTableCell.forTableColumn(Esame.listaVotiStandard));            
            dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        }
        
        private void completeCommit(Esame e,int rowIndex) {
            boolean result = GestoreArchiviazioneEsami.getInstance().editExam(e);
            System.out.println(result);
            if(result)
                RisorsaListaEsami.getInstance().notifyChangedExam(rowIndex);
        }

        private void setupCellEditCommits() {
            colonnaNome.setOnEditCommit(
                new EventHandler<CellEditEvent<Esame, String>>() {
                    @Override
                    public void handle(CellEditEvent<Esame, String> t) {
                        int index = t.getTablePosition().getRow();
                        Esame edited = (RisorsaListaEsami.getInstance().getExam(index));
                        edited.setNome(t.getNewValue());
                        completeCommit(edited,index);
                    }
                }
            );
            markColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<Esame, Integer>>() {
                    @Override
                    public void handle(CellEditEvent<Esame, Integer> t) {
                        int index = t.getTablePosition().getRow();
                        Esame edited = (RisorsaListaEsami.getInstance().getExam(index));                        
                        edited.setValutazione(t.getNewValue());
                        completeCommit(edited,index);
                    }
                }
            );
            creditsColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<Esame, Long>>() {
                    @Override
                    public void handle(CellEditEvent<Esame, Long> t) {
                        int index = t.getTablePosition().getRow();
                        Esame edited = (RisorsaListaEsami.getInstance().getExam(index));                         
                        edited.setCrediti(t.getNewValue().intValue());                        
                        completeCommit(edited,index);
                    }
                }
            );
            dateColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<Esame, String>>() {
                    @Override
                    public void handle(CellEditEvent<Esame, String> t) {
                        int index = t.getTablePosition().getRow();
                        Esame edited = (RisorsaListaEsami.getInstance().getExam(index));                                                 
                        edited.setData(t.getNewValue());   
                        completeCommit(edited,index);                        
                    }
                }
            );   
        }
}
