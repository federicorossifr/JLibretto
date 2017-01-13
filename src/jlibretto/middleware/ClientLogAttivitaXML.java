///////////////////////////////
package jlibretto.middleware;
import com.thoughtworks.xstream.XStream;
import java.io.DataOutputStream;
import java.net.Socket;
import jlibretto.frontend.GestoreParametriConfigurazioneXML;
import jlibretto.middleware.AttivitaXML.MarcaTemporale;

public class ClientLogAttivitaXML extends Thread {
    private final AttivitaXML attivita;
    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"; //(1)    
    private ClientLogAttivitaXML(AttivitaXML a) {
        attivita = a;
        attivita.indirizzoIPClient = GestoreParametriConfigurazioneXML.ottieniParametriConfigurazione().IPClient;        
    }
    
    private String serializzaAttivitaInXML() {
        String stringaXml;
        XStream xs = new XStream();
        xs.alias("Attivita", AttivitaXML.class);
        xs.useAttributeFor(AttivitaXML.class,"nomeApplicazione");
        xs.useAttributeFor(AttivitaXML.MarcaTemporale.class,"formato");
        xs.processAnnotations(MarcaTemporale.class); //(1)
        stringaXml = XML_HEADER+System.lineSeparator()+xs.toXML(attivita);
        return stringaXml;
    }
    
    @Override
    public void run() {
        String indirizzoServerLog = GestoreParametriConfigurazioneXML.ottieniParametriConfigurazione().IPServerLog;
        Integer portaServerLog = GestoreParametriConfigurazioneXML.ottieniParametriConfigurazione().PortaServerLog;
        try (
            Socket socketInvioXML = new Socket(indirizzoServerLog,portaServerLog);
            DataOutputStream streamUscitaAlServer = new DataOutputStream(socketInvioXML.getOutputStream());
        ) {
            streamUscitaAlServer.writeUTF(serializzaAttivitaInXML());
        } catch(Exception e) {
            System.out.println("Impossibile inviare il log");
        }
    }
    
    private static void inviaLogClickComponente(String nomeApplicazione,String nomeComponente,String t) {
        AttivitaXML a = new AttivitaXML(nomeApplicazione,t,nomeComponente,"");
        (new ClientLogAttivitaXML(a)).start();
    }
    
    public static void inviaLogClickBottone(String nomeApplicazione,String nomeComponente) {
        inviaLogClickComponente(nomeApplicazione,nomeComponente,"CLICK_BOTTONE");
    }
    
    public static void inviaLogClickTabella(String nomeApplicazione,String nomeComponente) {
        inviaLogClickComponente(nomeApplicazione,nomeComponente,"CLICK_TABELLA");
    }
    
    public static void inviaLogEventoApplicazione(String nomeApplicazione,int chiusura) {
        String t = (chiusura==1)?"CHIUSURA_APPLICAZIONE":"AVVIO_APPLICAZIONE";
        AttivitaXML a = new AttivitaXML(nomeApplicazione,t,null,"");
        (new ClientLogAttivitaXML(a)).start();
    }
}

/*
    (1):Consente a XStream di utilizzare le annotazioni inserite su MarcaTemporale
        e utilizzare il convertitore specificato.
        ref: http://x-stream.github.io/annotations-tutorial.html#Aliasing
    (2):Header XML da usare nella produzione di righe di log XML.
*/