////////////////////////////
package jlibretto.middleware;
import jlibretto.frontend.TabellaEsami;
import java.io.*;
import java.time.LocalDate;
public class CacheInserimento implements Serializable {
    String cacheNome;
    String cacheData;
    int cacheCrediti;
    int cacheCodiceEsame;
    int cacheValutazione;
    boolean cacheCaratterizzante;
    boolean cacheValida;
    public CacheInserimento(TabellaEsami t) {
        Esame cachable = t.getItems().get(t.getItems().size() -1);        
        cacheNome = cachable.getNome();
        cacheCrediti = cachable.getCrediti();
        cacheValutazione = cachable.getValutazione();
        cacheData = cachable.getData().toString();
        cacheCodiceEsame = cachable.getCodiceEsame();
        cacheCaratterizzante = cachable.getCaratterizzante();
    }
    
    public CacheInserimento() {
        try(ObjectInputStream streamIngressoBinario = new ObjectInputStream(new FileInputStream("../../cache/cache.bin"))) {
            CacheInserimento cache = (CacheInserimento)streamIngressoBinario.readObject();
            cacheNome = cache.cacheNome;
            cacheData = cache.cacheData;
            cacheCrediti = cache.cacheCrediti;
            cacheValutazione = cache.cacheValutazione;
            cacheCodiceEsame = cache.cacheCodiceEsame;
            cacheCaratterizzante = cache.cacheCaratterizzante;
            cacheValida = true;
        } catch(IOException | ClassNotFoundException e) {
            System.out.println("Errore nel caricare i dati precedentemente inseriti: "+e.getMessage());
            cacheValida = false;
        }
    }

    public void salvaDatiInCache() {
        try(ObjectOutputStream streamUscitaBinario = new ObjectOutputStream(new FileOutputStream("../../cache/cache.bin"))) {
                streamUscitaBinario.writeObject(this);
            } catch(Exception e) {
                System.out.println("Erore nel salvare i dati inseriti: "+e.getMessage());
        } 
    }
    
    public void impostaDatiCaricatiInTabella(TabellaEsami t) {
        if(cacheValida) {
            t.getItems().add(new Esame(-1,cacheCodiceEsame,cacheNome,cacheValutazione,cacheCrediti,LocalDate.parse(cacheData),cacheCaratterizzante));
        } else {
            t.getItems().add(new Esame());
        }
    }
}
