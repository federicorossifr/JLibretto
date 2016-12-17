package configurazione;
import xml.CaricatoreValidatoreXML;

public class GestoreConfigurazioniXML {
    private static Configurazioni c;
    public static Configurazioni ottieni() {
        if(c == null) {
            c = caricaConfigurazioni("./conf/configurazioni.xml","./conf/configurazioni.xsd");
        }
        return c;
    }

    
    private static Configurazioni caricaConfigurazioni(String percorsoXML,String percorsoSchemaXML) {
        CaricatoreValidatoreXML cvx = new CaricatoreValidatoreXML(percorsoXML,percorsoSchemaXML);
        cvx.getStreamXML().alias("Configurazioni", configurazione.Configurazioni.class);
        cvx.getStreamXML().useAttributeFor(configurazione.Preferenze.class, "TipoMedia");
        return (Configurazioni)cvx.caricaOggettoDaXML();
    }

}
