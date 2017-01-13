////////////////////////////////////////////////////
package jlibretto.backend;
import java.io.*;
import java.net.*;
import java.net.Socket;
import java.nio.file.*;
import jlibretto.CaricatoreValidatoreXML;

class ServerDiLog { //(1)
    private Integer portaServer;
    private ServerDiLog(Integer ps) {
        portaServer = ps;
    }
    
    private void appendiAFile(String xml) {
        CaricatoreValidatoreXML validatore = new CaricatoreValidatoreXML(null,"../../log/attivita.xsd");
        if(!validatore.validaXML(xml)) { //(3)
            return;
        }
        String ls = System.lineSeparator();
        xml+=ls+ls;
        try {
            if(!Files.exists(Paths.get("../../log/log.xml")))
                Files.createFile(Paths.get("../../log/log.xml"));
            Files.write(Paths.get("../../log/log.xml"),xml.getBytes(),StandardOpenOption.APPEND); //(4)
        } catch(Exception e) {
            System.out.println("Impossibile scrivere su file di log: "+e.getMessage());
        }
    }

    private void esegui() {
        while(true) {
            System.out.println("In attesa di connessioni...");
            try(
                ServerSocket socketServerDiLog = new ServerSocket(portaServer);
                Socket s = socketServerDiLog.accept();
                DataInputStream dis = new DataInputStream(s.getInputStream());
            ) {
                String attivitaXML = (String)dis.readUTF(); //(2)
                System.out.println("Ricevuto: "+attivitaXML);
                appendiAFile(attivitaXML);                
            } catch(Exception e) {
                System.out.println("Errore server: "+e.getMessage());
                return;
            }
        }
        
    }
    
    public static void main(String[] args) {
        if(args.length < 2) {
            System.out.println("[GUIDA] java serverdilog.ServerDiLog <porta>");
            System.exit(-1);
        }
        ServerDiLog sdl = new ServerDiLog(Integer.parseInt(args[0]));
        sdl.esegui();
    }
    
}

// (1): Classe entry point per l'applicativo server di log, viene avviata da riga di comando specificando
//      la porta di ascolto
// (2): Ricezione su socket TCP dei caratteri UTF del log XML
// (3): Validazione del contenuto del log XML ricevuto contro lo schema XSD
// (4): Aggiunta a file del contenuto appena ricevuto.