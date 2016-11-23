package interfacciagrafica;

import logattivita.ClientLogAttivitaXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

class BottoneLogger extends Button{
    public BottoneLogger(String contenuto) {
        super(contenuto);
        this.getStyleClass().add("bottone");
        this.addEventHandler(MouseEvent.MOUSE_CLICKED,event->{
            ClientLogAttivitaXML.inviaLogClickBottone("JLibretto",this.getText());
        });
    }
}
