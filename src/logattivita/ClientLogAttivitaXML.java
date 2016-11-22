package logattivita;
import java.io.DataOutputStream;
import java.net.Socket;
import configurazione.GestoreConfigurazioniXML;

public class ClientLogAttivitaXML extends Thread {
    private final AttivitaXML attivita;
    private ClientLogAttivitaXML(AttivitaXML a) {
        attivita = a;
    }
    @Override
    public void run() {
        String indirizzoServerLog;
        Integer portaServerLog;
        try {
            indirizzoServerLog = GestoreConfigurazioniXML.getIstanza().getIPServerLog();
            portaServerLog = GestoreConfigurazioniXML.getIstanza().getPortaServerLog();
        } catch(Exception e) {
            System.out.println("Impossibile configurare il client.");
            return;
        }
        
        try (
            Socket socketInvioXML = new Socket(indirizzoServerLog,portaServerLog);
            DataOutputStream streamUscitaAlServer = new DataOutputStream(socketInvioXML.getOutputStream());
        ) {            
            attivita.indirizzoIPClient = socketInvioXML.getLocalAddress().toString();
            streamUscitaAlServer.writeUTF(attivita.serializzaInXML());
        } catch(Exception e) {
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
