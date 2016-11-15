package clientlog;
import configurazione.ConfigurazioniNonDisponibiliException;
import java.io.DataOutputStream;
import java.net.Socket;
import configurazione.GestoreConfigurazioniXML;

public class ClientLogAttivitaXML extends Thread {
    private final AttivitaXML attivita;
    private Socket socketInvioXML;
    private ClientLogAttivitaXML(AttivitaXML a) {
        attivita = a;
    }
    @Override
    public void run() {
        try {
            String indirizzoServerLog;
            Integer portaServerLog;
            
            indirizzoServerLog = GestoreConfigurazioniXML.getIstanza().getIPServerLog();
            portaServerLog = GestoreConfigurazioniXML.getIstanza().getPortaServerLog();
            System.out.println("Tentativo di connessione: "+indirizzoServerLog+" "+portaServerLog);  
            
            socketInvioXML = new Socket(indirizzoServerLog,portaServerLog);
            System.out.println("Socket connesso: "+socketInvioXML.getInetAddress()+" "+socketInvioXML.getPort());
            
            DataOutputStream streamUscitaAlServer = new DataOutputStream(socketInvioXML.getOutputStream());
            attivita.indirizzoIPClient = socketInvioXML.getLocalAddress().toString();
            streamUscitaAlServer.writeUTF(attivita.serializzaInXML());
        } catch(ConfigurazioniNonDisponibiliException exx) {
            System.out.println("Errore nel caricamento della configurazione");
        } 
        catch(Exception e) {
            System.out.println("Errore di connessione al server di log: "+e.getLocalizedMessage());
        }
    }
    
    private static void inviaLogClickComponente(String nomeApplicazione,String nomeComponente,TipoAttivita t) {
        AttivitaXML a = new AttivitaXML(nomeApplicazione,t,nomeComponente,"");
        (new ClientLogAttivitaXML(a)).start();
    }
    
    public static void inviaLogClickBottone(String nomeApplicazione,String nomeComponente) {
        inviaLogClickComponente(nomeApplicazione,nomeComponente,TipoAttivita.CLICK_BOTTONE);
    }
    
    public static void inviaLogClickTabella(String nomeApplicazione,String nomeComponente) {
        inviaLogClickComponente(nomeApplicazione,nomeComponente,TipoAttivita.CLICK_TABELLA);
    }
    
    public static void inviaLogEventoApplicazione(String nomeApplicazione,int chiusura) {
        TipoAttivita t = (chiusura==1)?TipoAttivita.CHIUSURA_APPLICAZIONE:TipoAttivita.AVVIO_APPLICAZIONE;
        AttivitaXML a = new AttivitaXML(nomeApplicazione,t,null,"");
        (new ClientLogAttivitaXML(a)).start();
    }
}
