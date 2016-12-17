package interfacciagrafica;

import logattivita.ClientLogAttivitaXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import modellodati.ControlloreListaEsami;

class PulsanteElimina extends Button{
    private int indiceDaEliminare;
    
    public PulsanteElimina() {
        super("Elimina");
        this.getStyleClass().add("bottone");
        this.addEventHandler(MouseEvent.MOUSE_CLICKED,event->{
            ClientLogAttivitaXML.inviaLogClickBottone("JLibretto",this.getText());
            elimina();
        });
        setDisable(true);
    }
    
    public void impostaIndice(int i) {
        System.out.println("Punto "+i);
        indiceDaEliminare = i;
        if(ControlloreListaEsami.getIstanza().confrontaConIndiceUltimoEsame(i))
            setDisable(true);
        else
            setDisable(false);
    }
    
    private void elimina() {
        ControlloreListaEsami.getIstanza().eliminaEsame(indiceDaEliminare);
        setDisable(true);
    }
}
