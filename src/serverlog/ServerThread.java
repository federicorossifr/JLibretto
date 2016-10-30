/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverlog;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author feder
 */
public class ServerThread extends Thread {
    private Socket client;
    public ServerThread(Socket cl) {
        client = cl;
    }
    
    private void appendiAFile(String xml) {
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
    
    public void run() {
        try {
            InputStream flussoIngressoClient = new BufferedInputStream(client.getInputStream());
            InputStreamReader lettoreFlussoIngresso = new InputStreamReader(flussoIngressoClient);
            char[] buffer = new char[8192];
            System.out.println("Ricezione xml");
            lettoreFlussoIngresso.read(buffer);
            String attivitaXML = (new String(buffer)).trim();
            appendiAFile(attivitaXML);
        } catch(Exception e) {
            System.out.println("Errore di ricezione: "+e.getMessage());
        }
    }
}
