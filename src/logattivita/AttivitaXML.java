package logattivita;

import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.annotations.*;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class AttivitaXML implements Serializable {
    private final TipoAttivita tipo;
    private final String nomeApplicazione;
    private final String nomeComponente;
    String indirizzoIPClient;
    private MarcaTemporale marcaTemporale;
    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    
    
    public AttivitaXML(String nomeA,TipoAttivita tA,String nomeC,String indirizzo) {
        tipo = tA;
        nomeApplicazione = nomeA;
        nomeComponente = nomeC;
        indirizzoIPClient = indirizzo;
        String formato = "dd/MM/yyyy HH:mm:ss";
        marcaTemporale = new MarcaTemporale(LocalDateTime.now().format(DateTimeFormatter.ofPattern(formato)),formato);
    }
    
    public String serializzaInXML() {
        String stringaXml;
        XStream xs = new XStream();
        xs.alias("Attivita", AttivitaXML.class);
        xs.useAttributeFor(AttivitaXML.class, "tipo");
        xs.useAttributeFor(AttivitaXML.MarcaTemporale.class,"formato");
        xs.processAnnotations(MarcaTemporale.class);
        stringaXml = XML_HEADER+System.lineSeparator()+xs.toXML(this);
        return stringaXml;
    }
    
    @XStreamConverter(value=ToAttributedValueConverter.class, strings={"marca"})
    public static class MarcaTemporale {
        private String formato;
        private String marca;
        public MarcaTemporale(String mt,String f) {
            marca = mt;
            formato = f;
        }
    }
}
