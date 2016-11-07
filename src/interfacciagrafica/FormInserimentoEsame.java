
package interfacciagrafica;

import modellodati.RisorsaListaEsami;
import java.time.LocalDate;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import clientlog.BottoneLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class FormInserimentoEsame extends GridPane {
    
    final TextField inputNomeEsame = new TextField();
    private final TextField inputCodiceUtente = new PasswordField();
    final TextField inputCreditiEsame = new TextField();
    final ComboBox<Integer> inputValutazioneEsame = new ComboBox<>(listaVotiStandard);
    final DatePicker inputDataEsame = new DatePicker();
    final Button pulsanteInvioForm = new BottoneLog("Inserisci");
    final Button pulsanteApplicaCodiceUtente = new BottoneLog("Applica");
    static final ObservableList<Integer> listaVotiStandard;
    static {
        listaVotiStandard = FXCollections.observableArrayList();
        for(int mm = 18; mm<=33; ++mm)
            listaVotiStandard.add(mm);
    }
    
    public FormInserimentoEsame() {
        super();
        Label etichettaInputCodiceUtente = new Label("Codice utente");
        Label etichettaInputNomeEsame = new Label("Nome esame");
        Label etichettaInputCreditiEsame = new Label("Crediti esame");
        Label etichettaInputValutazioneEsame = new Label("Valutazione esame");
        Label etichettaInputDataEsame = new Label("Data esame");
        inputValutazioneEsame.setPromptText("Seleziona voto");
        inputValutazioneEsame.setEditable(true);
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
        if(mark >=18 && mark<=33)
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
            RisorsaListaEsami.getIstanza().creaEsame(name, mark, credits, d, codiceUtente);
            pulisciForm();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
 
    private void applicaCodiceUtente() {
        try {
            String codice = inputCodiceUtente.getText();
            if(codice.length() < 8) return;
            inputCodiceUtente.setEditable(false);
            pulsanteInvioForm.setDisable(false);
            pulsanteApplicaCodiceUtente.setDisable(true);
            RisorsaListaEsami.getIstanza().popolaEsami(codice);
            pulisciForm();
        } catch(Exception e) {
            System.out.println("Errore nella compilazione del codice utente: "+e.getMessage());
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
