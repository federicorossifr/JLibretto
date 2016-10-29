package jlibretto.clientlog;

import com.thoughtworks.xstream.XStream;
import java.io.Serializable;
import java.time.LocalDateTime;

public class AttivitaXML implements Serializable {
    public TipoAttivita tipo;
    public String nomeComponente;
    public String indirizzoIPClient;
    public String istante;
    
    public AttivitaXML(TipoAttivita tA,String nomeC,String indirizzo) {
        tipo = tA;
        nomeComponente = nomeC;
        indirizzoIPClient = indirizzo;
        istante = LocalDateTime.now().toString();
    }
    
    public String serializzaInXML() {
        String stringaXml;
        XStream xs = new XStream();
        xs.alias("Attività", AttivitaXML.class);
        xs.useAttributeFor(AttivitaXML.class, "tipo");
        stringaXml = xs.toXML(this);
        return stringaXml;
    }
    
    public static void main(String[] args) {
        AttivitaXML a = new AttivitaXML(TipoAttivita.CLICK_BOTTONE,"Inserisci","127.0.0.1");
        a.serializzaInXML();
    }
}
