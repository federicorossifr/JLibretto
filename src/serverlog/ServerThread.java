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

/**
 *
 * @author feder
 */
public class ServerThread extends Thread {
    private Socket client;
    public ServerThread(Socket cl) {
        client = cl;
    }
    
    public void run() {
        try {
            InputStream flussoIngressoClient = new BufferedInputStream(client.getInputStream());
            InputStreamReader lettoreFlussoIngresso = new InputStreamReader(flussoIngressoClient);
            char[] buffer = new char[8192];
            System.out.println("Ricezione xml");
            lettoreFlussoIngresso.read(buffer);
            System.out.println((new String(buffer).trim()));
        } catch(Exception e) {
            System.out.println("Errore di ricezione: "+e.getMessage());
        }
    }
}
