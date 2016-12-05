package configurazione;

import com.thoughtworks.xstream.XStream;
import java.io.*;
import java.nio.file.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.xml.sax.*;
import org.w3c.dom.Document;

public class GestoreConfigurazioniXML {
    private static Configurazioni c;
    public static Configurazioni ottieni() {
        if(c == null) {
            c = caricaConfigurazioni("./conf/configurazioni.xml","./conf/configurazioni.xsd");
        }
        System.out.println((new XStream()).toXML(c));
        return c;
    }
    
    private static boolean validaConfigurazioniXML(String fileXML,String fileSchemaXSD) {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document documentoConfigurazione = db.parse(new File(fileXML));
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schemaConfigurazione = sf.newSchema(new StreamSource(new File(fileSchemaXSD)));
            schemaConfigurazione.newValidator().validate(new DOMSource(documentoConfigurazione));
            return true;
        } catch(SAXException e) {
            System.out.println("Errore di validazione: "+e.getLocalizedMessage());
            return false;
        } catch(ParserConfigurationException | IOException e) {
            System.out.println("Errore: "+e.getLocalizedMessage());
            return false;
        }
    }
    private static Configurazioni caricaConfigurazioni(String percorsoXML,String percorsoSchemaXML) {
        try {
            XStream flussoXML = new XStream();
            flussoXML.alias("Configurazioni", configurazione.Configurazioni.class);
            flussoXML.useAttributeFor(Preferenze.class, "TipoMedia");
            if(!validaConfigurazioniXML(percorsoXML,percorsoSchemaXML))
                return null;
            System.out.println("Valida");
            String inputDaFileXML = new String(Files.readAllBytes(Paths.get(percorsoXML)));
            return (Configurazioni)flussoXML.fromXML(inputDaFileXML);
        } catch(Exception e) {
            System.out.println("Impossibile caricare la configurazione: "+e.getLocalizedMessage());
            return null;
        }     
    }
}
