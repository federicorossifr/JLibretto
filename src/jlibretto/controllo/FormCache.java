package jlibretto.controllo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class FormCache implements Serializable {
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
                ObjectOutputStream streamUscitaBinario = new ObjectOutputStream(new FileOutputStream("cache.bin"));
                String nomeEsame,creditiEsame,votoEsame,dataEsame;
                try {
                    nomeEsame = form.inputNomeEsame.getText();
                } catch(Exception e) {nomeEsame = "";}
                try {
                    creditiEsame = form.inputCreditiEsame.getText();
                } catch(Exception e) {creditiEsame = "";}
                try {
                    votoEsame = form.inputValutazioneEsame.getEditor().getText();
                } catch(Exception e) {votoEsame = "";}
                try {
                    dataEsame = form.inputDataEsame.getEditor().getText();
                } catch(Exception e) {dataEsame = "";}
                
                streamUscitaBinario.writeObject(new FormCache(nomeEsame,creditiEsame,votoEsame,dataEsame));
            } catch(Exception e) {
                System.out.println("Erore nel salvare la cache form: "+e.getMessage());
        } 
    }
    public static void caricaDaCache(FormInserimentoEsame form) {
        try {
            ObjectInputStream streamIngressoBinario = new ObjectInputStream(new FileInputStream("cache.bin"));
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
