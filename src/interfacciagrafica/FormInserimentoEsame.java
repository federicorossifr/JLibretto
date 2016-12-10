package interfacciagrafica;

import modellodati.ControlloreListaEsami;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.collections.*;
import javafx.util.StringConverter;

class FormInserimentoEsame extends GridPane {
    final TextField inputNomeEsame = new TextField();
    final TextField inputCreditiEsame = new TextField();
    final ComboBox<Integer> inputValutazioneEsame = new ComboBox<>();
    final DatePicker inputDataEsame = new DatePicker();    
    private final Button pulsanteInvioForm = new BottoneLogger("Inserisci");
    private Label etichettaInputNomeEsame = new Label("Nome esame");
    private Label etichettaInputCreditiEsame = new Label("Crediti esame");
    private Label etichettaInputValutazioneEsame = new Label("Valutazione esame");
    private Label etichettaInputDataEsame = new Label("Data esame");
    
    public FormInserimentoEsame(ObservableList<Integer> listaVoti) {
        super();
        inputValutazioneEsame.setPromptText("Seleziona voto");
        inputValutazioneEsame.setEditable(true);
        inputDataEsame.setEditable(false);
        inputValutazioneEsame.setItems(listaVoti);
        inputDataEsame.setShowWeekNumbers(false);
        impostaFormatoInputData()        ;
        Node[] gridContent = new Node[]{etichettaInputNomeEsame,etichettaInputCreditiEsame,etichettaInputValutazioneEsame,etichettaInputDataEsame,
                                        null,inputNomeEsame,inputCreditiEsame,inputValutazioneEsame,inputDataEsame,
                                        pulsanteInvioForm};
        impostaIndiciGriglia(gridContent,2,5);
        centraElementiInGriglia(gridContent);
        for(Node n:gridContent) {
            if(n == null) continue;
            getChildren().add(n);
        }
        pulsanteInvioForm.setOnAction(event -> creaEsame());
        setVgap(5);
        setHgap(5);
        setAlignment(Pos.CENTER);
        caricaContenuto();
    }
    
    private void impostaFormatoInputData() {
         inputDataEsame.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });   
    }
    
    void salvaContenuto() {
        FormCache.salvaInCache(inputNomeEsame.getCharacters().toString(),
                               inputCreditiEsame.getCharacters().toString(),
                               inputValutazioneEsame.getEditor().getCharacters().toString(),
                               inputDataEsame.getEditor().getCharacters().toString());
    }
    
    
    private void caricaContenuto() {
        FormCache contenuto = FormCache.caricaDaCache();
        if(contenuto == null) return;
        inputNomeEsame.setText(contenuto.contenutoInputNome);
        inputCreditiEsame.setText(contenuto.contenutoInputCrediti);
        inputValutazioneEsame.getEditor().setText(contenuto.contenutoInputValutazione);
        if(contenuto.contenutoInputData.length() > 0)
            inputDataEsame.setValue(LocalDate.parse(contenuto.contenutoInputData,DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
        System.out.println(inputDataEsame.getEditor().getCharacters());
    }
    
    private Integer ottieniValutazione() throws CompilazioneFormException {
        try {
            Integer valutazione = Integer.parseInt((inputValutazioneEsame.getEditor().getCharacters().toString()));
            if(inputValutazioneEsame.getItems().indexOf(valutazione) != -1)
                return valutazione;
            throw new CompilazioneFormException(inputValutazioneEsame);
        } catch(NumberFormatException e) {
            throw new CompilazioneFormException(inputValutazioneEsame);
        }
    } 
    
    private String ottieniNome() throws CompilazioneFormException {
        try {
            String s = inputNomeEsame.getText();
            if(s.length() > 0)
                return inputNomeEsame.getText();
            throw new CompilazioneFormException(inputNomeEsame);
        } catch(Exception e) {
            throw new CompilazioneFormException(inputNomeEsame);
        }
    }
    
    private Integer ottieniCrediti() throws CompilazioneFormException {
        try {
            return Integer.parseInt(inputCreditiEsame.getText());
        } catch(Exception e) {
            throw new CompilazioneFormException(inputCreditiEsame);
        }
    }
    
    private LocalDate ottieniData() throws CompilazioneFormException {
        try {
            LocalDate d = inputDataEsame.getValue();
            System.out.println(d);
            if(d != null)
                return d;
            throw new CompilazioneFormException(inputDataEsame);
        } catch(Exception e) {
            throw new CompilazioneFormException(inputDataEsame);
        }
    }
    
    private void creaEsame() {
        try {
            String name = ottieniNome();
            Integer credits = ottieniCrediti();
            Integer mark = ottieniValutazione();
            LocalDate d = ottieniData();
            ControlloreListaEsami.getIstanza().creaEsame(name, mark, credits, d);
            pulisciForm();
        } catch(CompilazioneFormException e) {
            rimuoviErrori();            
            e.getCausa().getStyleClass().add("erroreForm");
            System.out.println(e.getLocalizedMessage());
        }
    }

    
    private void rimuoviErrori() {
        inputNomeEsame.getStyleClass().remove("erroreForm");
        inputCreditiEsame.getStyleClass().remove("erroreForm");
        inputValutazioneEsame.getStyleClass().remove("erroreForm");
        inputDataEsame.getStyleClass().remove("erroreForm");
    }
    
    private void pulisciForm() {
        rimuoviErrori();
        inputNomeEsame.clear();
        inputCreditiEsame.clear();
        inputValutazioneEsame.getEditor().clear();
        inputDataEsame.getEditor().clear();
    }
    
    private  static void centraElementiInGriglia(Node[] lista) {
        for(Node n:lista) {
            if(n == null) continue;
            GridPane.setHalignment(n,HPos.CENTER);
            GridPane.setValignment(n,VPos.CENTER);
        }
    }
        
    private static void impostaIndiciGriglia(Node[] lista,int numeroColonne,int numeroRighe) {
        for(int column = 0; column < numeroColonne; ++column) {
            for(int row = 0; row < numeroRighe; ++row) {
                if(lista[row+numeroRighe*column]==null) continue;
                GridPane.setConstraints(lista[row+numeroRighe*column],column+1,row+1);
            }
        }
    } 


}
