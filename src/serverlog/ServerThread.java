/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverlog;

import java.io.DataInputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import configurazione.GestoreConfigurazioniXML;

/**
 *
 * @author feder
 */
public class ServerThread extends Thread {
    private final Socket client;
    public ServerThread(Socket cl) {
        client = cl;
    }
    
    private void appendiAFile(String xml) {
        System.out.println(xml);
        if(!GestoreConfigurazioniXML.validaContenutoXML(xml, "attivita.xsd")) {
            return;
        }
        System.out.println("Valida");
        String ls = System.lineSeparator();
        xml+=ls+ls;
        try {
            System.out.println("Scrittura su file di log");
            if(!Files.exists(Paths.get("log.xml")))
                Files.createFile(Paths.get("log.xml"));
            Files.write(Paths.get("log.xml"),xml.getBytes(),StandardOpenOption.APPEND);
        } catch(Exception e) {
            System.out.println("Impossibile scrivere su file di log: "+e.getMessage());
        }
    }
    
    @Override
    public void run() {
        try {
            DataInputStream flussoIngressoClient = new DataInputStream(client.getInputStream());
            System.out.println("Ricezione xml");
            String attivitaXML = (String)flussoIngressoClient.readUTF();
            appendiAFile(attivitaXML);
        } catch(Exception e) {
            System.out.println("Errore di ricezione: "+e.getMessage());
        }
    }
}
