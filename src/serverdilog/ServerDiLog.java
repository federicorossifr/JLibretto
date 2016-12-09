
package serverdilog;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerDiLog {
    Integer portaServer;
    String indirizzoServer;
    public ServerDiLog(String is,Integer ps) {
        portaServer = ps;
        indirizzoServer = is;
    }

    public void esegui() {
        while(true) {
            try(
                ServerSocket socketServerDiLog = new ServerSocket(portaServer);
            ) {
                 Socket clientConnesso = socketServerDiLog.accept();
                 System.out.println("Nuova connessione: "+clientConnesso.getInetAddress());
                (new ServerThread(clientConnesso)).start();
            } catch(Exception e) {
                System.out.println("Errore di avvio server thread: "+e.getMessage());
                return;
            }
        }
        
    }
    
    public static void main(String[] args) {
        if(args.length < 2) {
            System.out.println("[GUIDA] java serverdilog.ServerDiLog <ip> <porta>");
            System.exit(-1);
        }
        ServerDiLog sdl = new ServerDiLog(args[0],Integer.parseInt(args[1]));
        sdl.esegui();
    }
    
}
