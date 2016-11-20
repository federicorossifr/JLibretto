package interfacciagrafica;

import java.io.*;

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

    public static void salvaInCache(FormInserimentoEsame form) {
        try {
                ObjectOutputStream streamUscitaBinario = new ObjectOutputStream(new FileOutputStream("./cache/cache.bin"));
                String nomeEsame,creditiEsame,votoEsame,dataEsame;
                nomeEsame = form.inputNomeEsame.getCharacters().toString();
                creditiEsame = form.inputCreditiEsame.getCharacters().toString();
                votoEsame = form.inputValutazioneEsame.getEditor().getCharacters().toString();
                dataEsame = form.inputDataEsame.getEditor().getCharacters().toString();
                streamUscitaBinario.writeObject(new FormCache(nomeEsame,creditiEsame,votoEsame,dataEsame));
            } catch(Exception e) {
                System.out.println("Erore nel salvare la cache form: "+e.getMessage());
        } 
    }
    public static void caricaDaCache(FormInserimentoEsame form) {
        try {
            ObjectInputStream streamIngressoBinario = new ObjectInputStream(new FileInputStream("./cache/cache.bin"));
            FormCache contenutoFormCache = (FormCache)streamIngressoBinario.readObject();
            form.inputCreditiEsame.setText(contenutoFormCache.contenutoInputCrediti);
            form.inputValutazioneEsame.getEditor().setText(contenutoFormCache.contenutoInputValutazione);
            form.inputDataEsame.getEditor().setText(contenutoFormCache.contenutoInputData);
            form.inputNomeEsame.setText(contenutoFormCache.contenutoInputNome);
        } catch(IOException | ClassNotFoundException e) {
            System.out.println("Errore nel caricare la cache form: "+e.getMessage());
        }
    }
}
