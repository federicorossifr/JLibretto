
package xml;
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

public class CaricatoreValidatoreXML {
    private final String percorsoFileXML;
    private final String percorsoFileXSD;
    private final XStream streamXML;
    public CaricatoreValidatoreXML(String xml,String xsd) {
        percorsoFileXML = xml;
        percorsoFileXSD = xsd;
        streamXML = new XStream();
        streamXML.alias("Configurazioni", configurazione.Configurazioni.class);
        streamXML.useAttributeFor(configurazione.Preferenze.class, "TipoMedia");
    }
    
    public boolean validaXML(String xml) {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document documentoXML =  db.parse(new InputSource(new StringReader(xml))); //db.parse(new File(percorsoFileXML));
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new StreamSource(new File(percorsoFileXSD)));
            schema.newValidator().validate(new DOMSource(documentoXML));
            return true;
        } catch(SAXException e) {
            System.out.println("Errore di validazione: "+e.getLocalizedMessage());
            return false;
        } catch(ParserConfigurationException | IOException e) {
            System.out.println("Errore: "+e.getLocalizedMessage());
            return false;
        }
    }
    
    public Object caricaOggettoDaXML() {
        try {
            String inputDaFileXML = new String(Files.readAllBytes(Paths.get(percorsoFileXML)));
            if(!validaXML(inputDaFileXML))
                return null;
            return streamXML.fromXML(inputDaFileXML);
        } catch(Exception e) {
            System.out.println("Impossibile caricare XML: "+e.getLocalizedMessage());
            return null;
        }     
    }
    
}
