package configurazione;

import com.thoughtworks.xstream.XStream;
import java.io.*;
import java.nio.file.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.xml.sax.*;
import org.w3c.dom.Document;

public class GestoreConfigurazioniXML {
    private Configurazioni ParametriConfigurazione;
    private static GestoreConfigurazioniXML _istanza;
    
    private GestoreConfigurazioniXML() throws ConfigurazioniNonDisponibiliException {
        boolean risultatoCaricamento = caricaConfigurazioni("configurazioni.xml","configurazioni.xsd");
        if(!risultatoCaricamento)
            throw new ConfigurazioniNonDisponibiliException();
    }
    
    public static GestoreConfigurazioniXML getIstanza() throws ConfigurazioniNonDisponibiliException {
        if(_istanza == null) 
            _istanza = new GestoreConfigurazioniXML();
        return _istanza;
    }
    
    public static boolean validaConfigurazioniXML(String fileXML,String fileSchemaXSD) {
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
    private boolean caricaConfigurazioni(String percorsoXML,String percorsoSchemaXML) {
        try {
            XStream flussoXML = new XStream();
            flussoXML.alias("Configurazioni", configurazione.Configurazioni.class);
            if(!validaConfigurazioniXML(percorsoXML,percorsoSchemaXML))
                return false;
            System.out.println("Valida");
            String inputDaFileXML = new String(Files.readAllBytes(Paths.get(percorsoXML)));            
            ParametriConfigurazione = (Configurazioni) flussoXML.fromXML(inputDaFileXML);
            return true;
        } catch(Exception e) {
            System.out.println("Impossibile caricare la configurazione: "+e.getLocalizedMessage());
            return false;
        }     
    }
    
    public String getIPServerLog() {return ParametriConfigurazione.Nucleo.IPServerLog;}
    public Integer getPortaServerLog() {return ParametriConfigurazione.Nucleo.PortaServerLog;}
    public String getHostnameDatabase() {return ParametriConfigurazione.Nucleo.HostnameDatabase;}
    public Integer getPortaDatabase() {return ParametriConfigurazione.Nucleo.PortaDatabase;}
    public String getUtenteDatabase() {return ParametriConfigurazione.Nucleo.UtenteDatabase;}
    public String getPasswordDatabase() {return ParametriConfigurazione.Nucleo.PasswordDatabase;}
    public String getTipoMedia() {return ParametriConfigurazione.Preferenze.TipoMedia;}
    public Integer getMinValutazione() { return ParametriConfigurazione.Preferenze.MinValutazione;}
    public Integer getMaxValutazione() { return ParametriConfigurazione.Preferenze.MaxValutazione;}
    public ObservableList<Integer> getListaVotiDiponibili() {
        ObservableList<Integer> lv = FXCollections.observableArrayList();
        int vMax = getMaxValutazione();
        for(int vScorri = getMinValutazione();vScorri<=vMax;++vScorri) 
            lv.add(vScorri);
        return lv;
    }
}
