package interfacciagrafica;

import clientlog.ClientLogAttivitaXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

class BottoneLog extends Button{
    public BottoneLog(String contenuto) {
        super(contenuto);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED,event->{
            ClientLogAttivitaXML.inviaLogClickBottone(this.getText(), "JLibretto");
        });
    }
}
