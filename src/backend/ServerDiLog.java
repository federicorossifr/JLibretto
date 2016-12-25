package backend;
import java.io.*;
import java.net.*;
import java.net.Socket;
import java.nio.file.*;

import middleware.CaricatoreValidatoreXML;

class ServerDiLog {
    Integer portaServer;
    String indirizzoServer;
    private ServerDiLog(String is,Integer ps) {
        portaServer = ps;
        indirizzoServer = is;
    }
    
    private void appendiAFile(String xml) {
        CaricatoreValidatoreXML validatore = new CaricatoreValidatoreXML(null,"../../log/attivita.xsd");
        if(!validatore.validaXML(xml)) {
            return;
        }
        String ls = System.lineSeparator();
        xml+=ls+ls;
        try {
            if(!Files.exists(Paths.get("../../log/log.xml")))
                Files.createFile(Paths.get("../../log/log.xml"));
            Files.write(Paths.get("../../log/log.xml"),xml.getBytes(),StandardOpenOption.APPEND);
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
                String attivitaXML = (String)dis.readUTF();
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
            System.out.println("[GUIDA] java serverdilog.ServerDiLog <ip> <porta>");
            System.exit(-1);
        }
        ServerDiLog sdl = new ServerDiLog(args[0],Integer.parseInt(args[1]));
        sdl.esegui();
    }
    
}
