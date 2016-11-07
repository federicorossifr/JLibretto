 package interfacciagrafica;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.NumberStringConverter;
import clientlog.AttivitaXML;
import clientlog.ClientLogAttivitaXML;
import clientlog.Loggable;
import clientlog.TipoAttivita;
import modellodati.Esame;
import modellodati.RisorsaListaEsami;
class TabellaEsami extends TableView implements Loggable {
    
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
            this.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)-> {
                (new ClientLogAttivitaXML(this)).start();
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
            colonnaElimina.setOnEditCommit(new EventHandler<CellEditEvent<Esame, String>>() {
                    @Override
                    public void handle(CellEditEvent<Esame, String> t) {
                        RisorsaListaEsami.getIstanza().eliminaEsame(ottieniRigaCella(t));
                    }
            });
        }
        
        private int ottieniRigaCella(CellEditEvent<Esame, ?> cella) {
            return cella.getTablePosition().getRow();
        }
        
        private Esame ottieniElementoModificato(CellEditEvent<Esame, ?> cellaModificata) {
            int indice = ottieniRigaCella(cellaModificata);
            return RisorsaListaEsami.getIstanza().prelevaEsame(indice);
        }

        private void impostaCompletamentoModificaCelle() {
            colonnaNome.setOnEditCommit(new EventHandler<CellEditEvent<Esame, String>>() {
                    @Override
                    public void handle(CellEditEvent<Esame, String> t) {
                        Esame edited = ottieniElementoModificato(t);
                        edited.setNome(t.getNewValue());
                        completaModifica(edited,ottieniRigaCella(t));
                    }
                }
            );
            colonnaValutazione.setOnEditCommit(new EventHandler<CellEditEvent<Esame, Integer>>() {
                    @Override
                    public void handle(CellEditEvent<Esame, Integer> t) {
                        Esame edited = ottieniElementoModificato(t);
                        edited.setValutazione(t.getNewValue());
                        completaModifica(edited,ottieniRigaCella(t));
                    }
                }
            );
            colonnaCrediti.setOnEditCommit(new EventHandler<CellEditEvent<Esame, Long>>() {
                    @Override
                    public void handle(CellEditEvent<Esame, Long> t) {
                        Esame edited = ottieniElementoModificato(t);
                        edited.setCrediti(t.getNewValue().intValue());                        
                        completaModifica(edited,ottieniRigaCella(t));
                    }
                }
            );
            colonnaData.setOnEditCommit(new EventHandler<CellEditEvent<Esame, String>>() {
                    @Override
                    public void handle(CellEditEvent<Esame, String> t) {
                        Esame edited = ottieniElementoModificato(t);                                              
                        edited.setData(t.getNewValue());   
                        completaModifica(edited,ottieniRigaCella(t));
                    }
                }
            ); 

        }

    @Override
    public AttivitaXML produciAttivita(TipoAttivita tipo) {
        AttivitaXML a = new AttivitaXML("JLibretto",TipoAttivita.CLICK_TABELLA,"TabellaEsami","");
        return a;
    }
}
