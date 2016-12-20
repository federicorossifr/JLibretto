package interfacciagrafica;

import java.io.*;
class CacheInserimento implements Serializable {
    String cacheNome;
    String cacheData;
    int cacheCrediti;
    int cacheCodiceEsame;
    int cacheValutazione;
    boolean cacheValida;
    CacheInserimento(String n,int c,int m,String d,int ce) {
        cacheNome = n;
        cacheCrediti = c;
        cacheValutazione = m;
        cacheData = d;
        cacheCodiceEsame = ce;
    }
    
    CacheInserimento() {
        try(ObjectInputStream streamIngressoBinario = new ObjectInputStream(new FileInputStream("./cache/cache.bin"))) {
            CacheInserimento cache = (CacheInserimento)streamIngressoBinario.readObject();
            cacheNome = cache.cacheNome;
            cacheData = cache.cacheData;
            cacheCrediti = cache.cacheCrediti;
            cacheValutazione = cache.cacheValutazione;
            cacheCodiceEsame = cache.cacheCodiceEsame;
            cacheValida = true;
        } catch(IOException | ClassNotFoundException e) {
            System.out.println("Errore nel caricare i dati precedentemente inseriti: "+e.getMessage());
            cacheValida = false;
        }
    }

    void salvaDatiEsameInCache() {
        try(ObjectOutputStream streamUscitaBinario = new ObjectOutputStream(new FileOutputStream("./cache/cache.bin"))) {
                streamUscitaBinario.writeObject(this);
            } catch(Exception e) {
                System.out.println("Erore nel salvare i dati inseriti: "+e.getMessage());
        } 
    }
}
