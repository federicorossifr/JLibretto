package interfacciagrafica;

import logattivita.*;
import javafx.scene.control.*;
import modellodati.*;

class PulsanteElimina extends Button{
    private int indiceDaEliminare;
    
    public PulsanteElimina() {
        super("Elimina");
        this.getStyleClass().add("bottone");
        this.setOnAction(event->{
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
