/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlibretto.clientlog;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import jlibretto.GestoreConfigurazioniXML;

/**
 *
 * @author feder
 */
public class ClientLogAttivitaXML implements Runnable {
    AttivitaXML attivita;
    Socket socketInvioXML;
    public ClientLogAttivitaXML(AttivitaXML a) {
        attivita = a;
    }
    
    @Override
    public void run() {
        
        try {
            String indirizzoServerLog = GestoreConfigurazioniXML.parametriConfigurazione.getIPServerLog();
            Integer portaServerLog = GestoreConfigurazioniXML.parametriConfigurazione.getPortaServerLog();
            socketInvioXML = new Socket(indirizzoServerLog,portaServerLog);
            OutputStream streamUscitaAlServer = new BufferedOutputStream(socketInvioXML.getOutputStream());
            OutputStreamWriter scrittoreXMLAServer = new OutputStreamWriter(streamUscitaAlServer);
            scrittoreXMLAServer.write(attivita.serializzaInXML());
            scrittoreXMLAServer.flush();
        } catch(Exception e) {
            System.out.println("Errore di connessione al server di log: "+e.getMessage());
        }
    }
    
}
