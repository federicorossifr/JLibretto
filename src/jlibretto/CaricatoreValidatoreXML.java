//////////////////
package jlibretto;
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
    }
    
    public boolean validaXML(String xml) { //(1)
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document documentoXML =  db.parse(new InputSource(new StringReader(xml))); 
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
    
    public Object caricaOggettoDaXML() { //(2)
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
    
    
    public XStream getStreamXML() { //(3)
        return streamXML;
    }
    
}


/*
    (1): Valida una stringa XML (non necessariamente da file): si utilizza InputSource e StringReader al posto di
         File(percorsoXML).
    (2): Crea un istanza di Object a partire da una stringa XML contenuta in un file. La responsabilità di cast ad un tipo più
         specializzato è riservata al cliente.
    (3): Restituisce l'oggetto XStream per permettere al cliente di configurare la conversione
         tramite alias e convertitori.
*/
