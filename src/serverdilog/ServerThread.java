package serverdilog;

import java.io.*;
import java.net.Socket;
import java.nio.file.*;
import xml.CaricatoreValidatoreXML;


public class ServerThread extends Thread {
    private final Socket client;
    public ServerThread(Socket cl) {
        client = cl;
    }
    
    private void appendiAFile(String xml) {
        CaricatoreValidatoreXML validatore = new CaricatoreValidatoreXML(null,"../../etc/attivita.xsd");
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
    
    @Override
    public void run() {
        try(
                DataInputStream flussoIngressoClient = new DataInputStream(client.getInputStream());
           ) {
            System.out.println("Ricezione xml");
            String attivitaXML = (String)flussoIngressoClient.readUTF();
            appendiAFile(attivitaXML);
        } catch(Exception e) {
            System.out.println("Errore di ricezione: "+e.getMessage());
        }
    }
}
