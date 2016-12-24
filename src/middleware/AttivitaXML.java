package middleware;

import com.thoughtworks.xstream.annotations.*;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class AttivitaXML implements Serializable {
    public final String tipo;
    public final String nomeApplicazione;
    public final String nomeComponente;
    public String indirizzoIPClient;
    public MarcaTemporale marcaTemporale;
    
    public AttivitaXML(String nomeA,String tA,String nomeC,String indirizzo) {
        tipo = tA;
        nomeApplicazione = nomeA;
        nomeComponente = nomeC;
        indirizzoIPClient = indirizzo;
        String formato = "dd/MM/yyyy HH:mm:ss";
        marcaTemporale = new MarcaTemporale(LocalDateTime.now().format(DateTimeFormatter.ofPattern(formato)),formato);
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
