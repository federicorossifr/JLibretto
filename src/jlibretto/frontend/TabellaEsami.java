///////////////////////////////////
package jlibretto.frontend;
import jlibretto.middleware.*;
import java.time.LocalDate;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.*;
public class TabellaEsami extends TableView<Esame> {
    
        private final TableColumn<Esame,Esame> colonnaNome = new TableColumn("Nome esame"); //(5)
        private final TableColumn<Esame,Integer> colonnaCrediti = new TableColumn("Crediti esame");
        private final TableColumn<Esame,Integer> colonnaValutazione = new TableColumn("Valutazione esame");
        private final TableColumn<Esame,LocalDate> colonnaData = new TableColumn("Data esame");
        
        private ObservableList<Integer> listaVoti;
        public TabellaEsami(int valoreLode) {
            setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //(1)
            colonnaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colonnaCrediti.setCellValueFactory(new PropertyValueFactory<>("crediti"));
            colonnaValutazione.setCellValueFactory(new PropertyValueFactory<>("valutazione"));
            colonnaData.setCellValueFactory(new PropertyValueFactory<>("data"));
            impostaListaVoti(valoreLode);
            impostaFormatoModificaCelle();
            impostaSelezioneNomeEsame();            
            impostaCompletamentoModificaDatiEsame();
            colonnaNome.setSortable(false); //(2)
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
                if(getItems().get(getItems().size()-1).getCodiceEsame() > 0) { //(3)
                    ControlloreListaEsami.getIstanza().creaEsame();
                }
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
                if(getSelectionModel().getSelectedIndex() == getItems().size() -1) { //(4)
                    event.getRowValue().setCrediti(event.getNewValue().getCrediti());
                    event.getRowValue().setNome(event.getNewValue().getNome());
                    event.getRowValue().setCodiceEsame(event.getNewValue().getCodiceEsame());
                    event.getRowValue().setCaratterizzante(event.getNewValue().getCaratterizzante());
                    colonnaCrediti.setVisible(false); //(6)
                    colonnaCrediti.setVisible(true);
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

// (1): Proprietà grafica: le colonne occupano tutta la dimensione in larghezza in egual misura
// (2): Colonne non ordinabili in quanto ha senso (per come è fatto un libretto) solo l'ordinamento
//      per data dal più vecchio al più nuovo.
// (3): Solo se è stato selezionato l'esame dalla prima colonna è possibile premere INVIO per inserirlo
// (4): Non ha senso modificare il nome dell'esame di un esame già inserito, al massimo lo si può eliminare
// (5): Ogni elemento del ComboBox è in realtà un oggetto Esame e non la sola proprietà nomeEsame.
//      quando viene selezionato l'esame dal menu a tendina le proprietà nomeEsame,crediti e caratterizzante
//      vengono replicate sull'esame che rappresenta la riga, cioè quello che verrà inserito nel DB.
// (6): Necessario per visualizzare le modifiche della riga nella tabella. 