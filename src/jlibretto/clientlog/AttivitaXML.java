package jlibretto.clientlog;

import com.thoughtworks.xstream.XStream;
import configurazione.GestoreConfigurazioniXML;
import java.io.Serializable;
import java.time.LocalDateTime;

public class AttivitaXML implements Serializable {
    public TipoAttivita tipo;
    public String nomeApplicazione;
    public String nomeComponente;
    public String indirizzoIPClient;
    public String istante;
    public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    
    
    public AttivitaXML(String nomeA,TipoAttivita tA,String nomeC,String indirizzo) {
        tipo = tA;
        nomeApplicazione = nomeA;
        nomeComponente = nomeC;
        indirizzoIPClient = indirizzo;
        istante = LocalDateTime.now().toString();
    }
    
    public String serializzaInXML() {
        String stringaXml;
        XStream xs = new XStream();
        xs.alias("Attività", AttivitaXML.class);
        xs.useAttributeFor(AttivitaXML.class, "tipo");
        stringaXml = XML_HEADER+System.lineSeparator()+xs.toXML(this);
        return stringaXml;
    }
}
