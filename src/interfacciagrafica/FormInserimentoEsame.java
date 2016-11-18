package interfacciagrafica;

import modellodati.ControlloreListaEsami;
import java.time.LocalDate;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.collections.*;

class FormInserimentoEsame extends GridPane {
    final TextField inputNomeEsame = new TextField();
    final TextField inputCreditiEsame = new TextField();
    final ComboBox<Integer> inputValutazioneEsame = new ComboBox<>();
    final DatePicker inputDataEsame = new DatePicker();    
    private final TextField inputCodiceUtente = new PasswordField();    
    private final Button pulsanteInvioForm = new BottoneLogger("Inserisci");
    private final Button pulsanteApplicaCodiceUtente = new BottoneLogger("Applica");
    private Label etichettaInputCodiceUtente = new Label("Codice utente");
    private Label etichettaInputNomeEsame = new Label("Nome esame");
    private Label etichettaInputCreditiEsame = new Label("Crediti esame");
    private Label etichettaInputValutazioneEsame = new Label("Valutazione esame");
    private Label etichettaInputDataEsame = new Label("Data esame");
    
    public FormInserimentoEsame(ObservableList<Integer> listaVoti) {
        super();
        inputValutazioneEsame.setPromptText("Seleziona voto");
        inputValutazioneEsame.setEditable(true);
        inputValutazioneEsame.setItems(listaVoti);
        inputDataEsame.setShowWeekNumbers(false);
        Node[] gridContent = new Node[]{etichettaInputCodiceUtente,null,etichettaInputNomeEsame,
                                        etichettaInputCreditiEsame,etichettaInputValutazioneEsame,etichettaInputDataEsame,
                                        null,inputCodiceUtente,pulsanteApplicaCodiceUtente,
                                        inputNomeEsame,inputCreditiEsame,inputValutazioneEsame,
                                        inputDataEsame,pulsanteInvioForm};
        impostaIndiciGriglia(gridContent,2,7);
        centraElementiInGriglia(gridContent);
        for(Node n:gridContent) {
            if(n == null) continue;
            getChildren().add(n);
        }
        pulsanteInvioForm.setDisable(true);
        pulsanteInvioForm.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e) -> creaEsame());
        pulsanteApplicaCodiceUtente.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e) -> applicaCodiceUtente());
        setVgap(5);
        setHgap(5);
        setAlignment(Pos.CENTER);
        caricaContenuto();
    }
    
    void salvaContenuto() {
        FormCache.salvaInCache(this);
    }
    
    private void caricaContenuto() {
        FormCache.caricaDaCache(this);
    }
    
    private Integer ottieniValutazione() {
        Integer mark;
        Object tmpMark = inputValutazioneEsame.getValue();
        if(tmpMark instanceof String) 
            mark = Integer.parseInt((String)tmpMark);
        else if(tmpMark instanceof Integer)
            mark = (Integer)tmpMark;
        else
            throw new NumberFormatException();
        if(inputValutazioneEsame.getItems().indexOf(mark) != -1)
            return mark;
        else
            throw new NumberFormatException();
    } 
    
    private void creaEsame() {
        try {
            String name = inputNomeEsame.getText();
            Integer credits = Integer.parseInt(inputCreditiEsame.getText());
            Integer mark = ottieniValutazione();
            LocalDate d = inputDataEsame.getValue();
            String codiceUtente = inputCodiceUtente.getText();
            ControlloreListaEsami.getIstanza().creaEsame(name, mark, credits, d, codiceUtente);
            pulisciForm();
        } catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
    
    private void applicaCodiceUtente() {
        try {
            String codice = inputCodiceUtente.getText();
            if(codice.length() < 8) return;
            inputCodiceUtente.setEditable(false);
            pulsanteInvioForm.setDisable(false);
            pulsanteApplicaCodiceUtente.setDisable(true);
            ControlloreListaEsami.getIstanza().popolaEsami(codice);
            pulisciForm();
        } catch(Exception e) {
            System.out.println("Errore nella compilazione del codice utente: "+e.getLocalizedMessage());
        }   
    }
    
    private void pulisciForm() {
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
