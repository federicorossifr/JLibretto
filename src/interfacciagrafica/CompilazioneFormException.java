package interfacciagrafica;

import javafx.scene.Node;

public class CompilazioneFormException extends Exception {
    private final Node causaEccezione;
    
    public CompilazioneFormException(Node c) {
        super();
        causaEccezione = c;
    }
    
    public Node getCausa() {
        return causaEccezione;
    }
}
