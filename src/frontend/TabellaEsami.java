package frontend;
import middleware.*;
import java.time.LocalDate;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.*;
public class TabellaEsami extends TableView<Esame> {
    
        private final TableColumn<Esame,Esame> colonnaNome = new TableColumn("Nome esame");
        private final TableColumn<Esame,Integer> colonnaCrediti = new TableColumn("Crediti esame");
        private final TableColumn<Esame,Integer> colonnaValutazione = new TableColumn("Valutazione esame");
        private final TableColumn<Esame,LocalDate> colonnaData = new TableColumn("Data esame");
        public boolean esameInviabile = false;
        
        private ObservableList<Integer> listaVoti;
        public TabellaEsami(int valoreLode) {
            setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            colonnaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colonnaCrediti.setCellValueFactory(new PropertyValueFactory<>("crediti"));
            colonnaValutazione.setCellValueFactory(new PropertyValueFactory<>("valutazione"));
            colonnaData.setCellValueFactory(new PropertyValueFactory<>("data"));
            impostaListaVoti(valoreLode);
            impostaFormatoModificaCelle();
            impostaSelezioneNomeEsame();            
            impostaCompletamentoModificaDatiEsame();
            colonnaNome.setSortable(false);
            colonnaCrediti.setSortable(false);
            colonnaData.setSortable(false);
            colonnaValutazione.setSortable(false);
            setItems(ControlloreListaEsami.getIstanza().getListaEsamiSvolti());
            setEditable(true);
            getColumns().addAll(colonnaNome,colonnaCrediti,colonnaValutazione,colonnaData);
            addEventHandler(MouseEvent.MOUSE_CLICKED,e->ClientLogAttivitaXML.inviaLogClickTabella("JLibretto", "TabellaEsami"));
            gestisciPressioneInvio();
        }
        
        private void impostaListaVoti(int valoreLode) {
            listaVoti = FXCollections.observableArrayList();
            for(int i = 18;i<=30;++i) listaVoti.add(i);
            listaVoti.add(valoreLode);
        }
        
        private void gestisciPressioneInvio() {
            setOnKeyPressed(event -> {
                if(event.getCode() != KeyCode.ENTER) return;
                if(esameInviabile) {
                    ControlloreListaEsami.getIstanza().creaEsame();
                }
                esameInviabile = false;
            });
        }

        private void impostaFormatoModificaCelle() {
            colonnaNome.setCellFactory(ComboBoxTableCell.forTableColumn(ControlloreListaEsami.getIstanza().getListaEsamiDisponibili()));
            colonnaValutazione.setCellFactory(ComboBoxTableCell.forTableColumn(listaVoti));            
            colonnaData.setCellFactory(col -> new CellaTabellaCalendario());
        }
        
        private void completaModifica(Esame e,int indiceRiga) {
             ControlloreListaEsami.getIstanza().modificaEsame(e, indiceRiga);
             getSelectionModel().select(indiceRiga);
        }
        
        private void impostaSelezioneNomeEsame() {
            colonnaNome.setOnEditCommit(event -> {
                String c = event.getRowValue().toString();
                if(getSelectionModel().getSelectedIndex() == getItems().size() -1) {
                    System.out.println("Modifica come inserimento");
                    event.getRowValue().setCrediti(event.getNewValue().getCrediti());
                    event.getRowValue().setNome(event.getNewValue().getNome());
                    event.getRowValue().setCodiceEsame(event.getNewValue().getCodiceEsame());
                    colonnaCrediti.setVisible(false);
                    colonnaCrediti.setVisible(true);
                    esameInviabile = true; 
                } else {
                    event.getRowValue().setNome(c);
                    colonnaNome.setVisible(false);
                    colonnaNome.setVisible(true);
                }
            });
        }
        
        private void impostaCompletamentoModificaDatiEsame() {
            colonnaValutazione.setOnEditCommit(event -> {
                event.getRowValue().setValutazione(event.getNewValue());
                completaModifica(event.getRowValue(),event.getTablePosition().getRow());
            });
            
            colonnaData.setOnEditCommit(event -> {
                event.getRowValue().setData(event.getNewValue());
                completaModifica(event.getRowValue(),event.getTablePosition().getRow());
            });
        }
}
