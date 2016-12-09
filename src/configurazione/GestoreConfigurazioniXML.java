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
        return (Configurazioni)(new CaricatoreValidatoreXML(percorsoXML,percorsoSchemaXML)).caricaOggettoDaXML();
    }

}
