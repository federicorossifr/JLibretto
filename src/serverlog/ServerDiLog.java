/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverlog;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import jlibretto.GestoreConfigurazioniXML;
/**
 *
 * @author feder
 */
public class ServerDiLog implements Runnable {
    ServerSocket socketServerDiLog;
    Integer portaServer;
    String indirizzoServer;
    public ServerDiLog() {
        GestoreConfigurazioniXML gcx = new GestoreConfigurazioniXML("configurazioni.xml","configurazioni.xsd");
        gcx.caricaConfigurazioni();
        portaServer = gcx.parametriConfigurazione.getPortaServerLog();
        indirizzoServer = gcx.parametriConfigurazione.getIPServerLog();
    }

    @Override
    public void run() {
        try {
            System.out.println("In ascolto su: "+portaServer+ " "+indirizzoServer);
            socketServerDiLog = new ServerSocket(portaServer);
        } catch(Exception e) {
            System.out.println("Errore di avvio server: "+e.getMessage());
        }
        while(true) {
            try {
                Socket clientConnesso = socketServerDiLog.accept();
                System.out.println("Nuova connessione da: "+clientConnesso.getInetAddress());
                (new ServerThread(clientConnesso)).start();
            } catch(Exception e) {
                System.out.println("Errore di avvio server: "+e.getMessage());
            }
        }
        
    }
    
    public static void main(String[] args) {
        ServerDiLog sdl = new ServerDiLog();
        (new Thread(sdl)).start();
    }
    
}
