package interfacciagrafica;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

class FormCache implements Serializable {
    String contenutoInputNome;
    String contenutoInputCrediti;
    String contenutoInputValutazione;
    String contenutoInputData;
    
    public FormCache(String n,String c,String m,String d) {
        contenutoInputNome = n;
        contenutoInputCrediti = c;
        contenutoInputValutazione = m;
        contenutoInputData = d;
    }

    public static void salvaInCache(String n,String c,String m,String d) {
        try(ObjectOutputStream streamUscitaBinario = new ObjectOutputStream(new FileOutputStream("./cache/cache.bin"))) {
                streamUscitaBinario.writeObject(new FormCache(n,c,m,d));
            } catch(Exception e) {
                System.out.println("Erore nel salvare i dati inseriti: "+e.getMessage());
        } 
    }
    public static FormCache caricaDaCache() {
        try(ObjectInputStream streamIngressoBinario = new ObjectInputStream(new FileInputStream("./cache/cache.bin"))) {
            FormCache contenutoFormCache = (FormCache)streamIngressoBinario.readObject();
            return contenutoFormCache;
        } catch(IOException | ClassNotFoundException e) {
            System.out.println("Errore nel caricare i dati precedentemente inseriti: "+e.getMessage());
            return null;
        }
    }
}
