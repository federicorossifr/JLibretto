/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlibretto.clientlog;
import java.io.DataOutputStream;
import java.net.Socket;
import configurazione.GestoreConfigurazioniXML;

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
            String indirizzoServerLog = GestoreConfigurazioniXML.ParametriConfigurazione.getIPServerLog();
            Integer portaServerLog = GestoreConfigurazioniXML.ParametriConfigurazione.getPortaServerLog();
            System.out.println("Tentativo di connessione: "+indirizzoServerLog+" "+portaServerLog);  
            
            socketInvioXML = new Socket(indirizzoServerLog,portaServerLog);
            System.out.println("Socket connesso: "+socketInvioXML.getInetAddress()+" "+socketInvioXML.getPort());
            
            DataOutputStream streamUscitaAlServer = new DataOutputStream(socketInvioXML.getOutputStream());
            attivita.indirizzoIPClient = socketInvioXML.getLocalAddress().toString();
            streamUscitaAlServer.writeUTF(attivita.serializzaInXML());
        } catch(Exception e) {
            System.out.println("Errore di connessione al server di log: "+e.getMessage());
        }
    }
    
}
