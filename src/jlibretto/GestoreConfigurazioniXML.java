/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlibretto;

import com.thoughtworks.xstream.XStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author feder
 */
public class GestoreConfigurazioniXML {
    public static Configurazioni parametriConfigurazione;
    private final String percorsoXML;
    private final String percorsoSchemaXML;
    
    public GestoreConfigurazioniXML(String xml,String xsd) {
        percorsoXML = xml;
        percorsoSchemaXML = xsd;
    }
    
    public boolean caricaConfigurazioni() {
        try {
            XStream flussoXML = new XStream();
            String inputDaFileXML = new String(Files.readAllBytes(Paths.get(percorsoXML)));
            System.out.println(inputDaFileXML);
            parametriConfigurazione = (Configurazioni) flussoXML.fromXML(inputDaFileXML);
            return true;
        } catch(Exception e) {
            System.out.println("Impossibile caricare la configurazione: "+e.getMessage());
            return false;
        }     
    }
}
