
package serverlog;
import configurazione.ConfigurazioniNonDisponibiliException;
import java.net.ServerSocket;
import java.net.Socket;
import configurazione.GestoreConfigurazioniXML;


public class ServerDiLog {
    ServerSocket socketServerDiLog;
    Integer portaServer;
    String indirizzoServer;
    public ServerDiLog() throws ConfigurazioniNonDisponibiliException {
        portaServer = GestoreConfigurazioniXML.getIstanza().getPortaServerLog();
        indirizzoServer = GestoreConfigurazioniXML.getIstanza().getIPServerLog();
    }

    public void esegui() {
        try {
            System.out.println("In ascolto su: "+portaServer+ " "+indirizzoServer);
            socketServerDiLog = new ServerSocket(portaServer);
        } catch(Exception e) {
            System.out.println("Errore di avvio server: "+e.getMessage());
            return;
        }
        while(true) {
            try {
                Socket clientConnesso = socketServerDiLog.accept();
                System.out.println("Nuova connessione da: "+clientConnesso.getInetAddress());
                (new ServerThread(clientConnesso)).start();
            } catch(Exception e) {
                System.out.println("Errore di avvio server thread: "+e.getMessage());
                return;
            }
        }
        
    }
    
    public static void main(String[] args) {
        try {
            ServerDiLog sdl = new ServerDiLog();
            sdl.esegui();
        } catch(Exception e) {
            System.out.println("Errore nel caricamento delle configurazioni");
            System.exit(0);
        }
    }
    
}
