package interfacciagrafica;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.NumberStringConverter;
import clientlog.*;
import javafx.event.Event;
import modellodati.*;
class TabellaEsami extends TableView {
    
        private final TableColumn colonnaNome = new TableColumn("Nome esame");
        private final TableColumn colonnaCrediti = new TableColumn("Crediti esame");
        private final TableColumn colonnaValutazione = new TableColumn("Valutazione esame");
        private final TableColumn colonnaData = new TableColumn("Data esame");
        private final TableColumn colonnaElimina = new TableColumn("Azione");
        public TabellaEsami() {
            setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            colonnaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colonnaCrediti.setCellValueFactory(new PropertyValueFactory<>("crediti"));
            colonnaValutazione.setCellValueFactory(new PropertyValueFactory<>("valutazione"));
            colonnaData.setCellValueFactory(new PropertyValueFactory<>("data"));

            impostaFormatoModificaCelle();
            impostaCompletamentoModificaCelle();
            impostaEliminazione();
            colonnaNome.setSortable(false);
            colonnaCrediti.setSortable(false);
            colonnaValutazione.setSortable(false);            
            colonnaData.setSortable(false);
            setItems(RisorsaListaEsami.getIstanza().getListaEsami());
            setEditable(true);
            getColumns().addAll(colonnaNome,colonnaCrediti,colonnaValutazione,colonnaData,colonnaElimina);
            addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
                ClientLogAttivitaXML.inviaLogClickTabella("TabellaEsami", "JLibretto");
            });
        }

        private void impostaFormatoModificaCelle() {
            colonnaNome.setCellFactory(TextFieldTableCell.forTableColumn());
            colonnaCrediti.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));            
            colonnaValutazione.setCellFactory(ComboBoxTableCell.forTableColumn(FormInserimentoEsame.listaVotiStandard));            
            colonnaData.setCellFactory(TextFieldTableCell.forTableColumn());
            colonnaElimina.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList("Elimina")));
        }
        
        private void completaModifica(Esame e,int indiceRiga) {
             RisorsaListaEsami.getIstanza().modificaEsame(e, indiceRiga);
        }
        
        private void impostaEliminazione() {
            colonnaElimina.setOnEditCommit(event -> {
                RisorsaListaEsami.getIstanza().eliminaEsame(ottieniRigaCella(event));
            });
        }
        
        private int ottieniRigaCella(Event cella) {
            CellEditEvent<Esame,?> c = (CellEditEvent<Esame, ?>) cella;
            return c.getTablePosition().getRow();
        }
        
        private Esame ottieniElementoModificato(Event cellaModificata) {
            int indice = ottieniRigaCella(cellaModificata);
            return RisorsaListaEsami.getIstanza().prelevaEsame(indice);
        }

        private void impostaCompletamentoModificaCelle() {
            colonnaNome.setOnEditCommit(event -> {
                Esame edited = ottieniElementoModificato(event);
                edited.setNome(((CellEditEvent<Esame,String>)event).getNewValue());
                completaModifica(edited,ottieniRigaCella(event));
            });
            colonnaValutazione.setOnEditCommit(event -> {
                Esame edited = ottieniElementoModificato(event);
                edited.setValutazione(((CellEditEvent<Esame,Integer>)event).getNewValue());
                completaModifica(edited,ottieniRigaCella(event));
            });
            colonnaCrediti.setOnEditCommit(event -> {
                Esame edited = ottieniElementoModificato(event);
                edited.setCrediti(((CellEditEvent<Esame,Long>)event).getNewValue().intValue());
                completaModifica(edited,ottieniRigaCella(event));
            });
            colonnaData.setOnEditCommit(event -> {
                Esame edited = ottieniElementoModificato(event);
                edited.setData(((CellEditEvent<Esame,String>)event).getNewValue());
                completaModifica(edited,ottieniRigaCella(event));
            }); 

        }
}
