package configurazione;
import xml.CaricatoreValidatoreXML;

public class GestoreParametriConfigurazioneXML {
    private static ParametriConfigurazione c;
    public static ParametriConfigurazione ottieniParametriConfigurazione() {
        if(c == null) {
            c = caricaParametriConfigurazioneDaXML("./conf/configurazioni.xml","./conf/configurazioni.xsd");
        }
        return c;
    }

    
    private static ParametriConfigurazione caricaParametriConfigurazioneDaXML(String percorsoXML,String percorsoSchemaXML) {
        CaricatoreValidatoreXML cvx = new CaricatoreValidatoreXML(percorsoXML,percorsoSchemaXML);
        cvx.getStreamXML().alias("Configurazioni", configurazione.ParametriConfigurazione.class);
        cvx.getStreamXML().useAttributeFor(configurazione.ParametriConfigurazione.class, "TipoMedia");
        cvx.getStreamXML().useAttributeFor(configurazione.ParametriConfigurazione.class, "ValoreLode");        
        return (ParametriConfigurazione)cvx.caricaOggettoDaXML();
    }

}
