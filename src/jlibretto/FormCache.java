package jlibretto;

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
                ObjectOutputStream toBin = new ObjectOutputStream(new FileOutputStream("cache.bin"));
                String name,credits,mark,date;
                try {
                    name = form.inputNomeEsame.getText();
                } catch(Exception e) {
                    name = "";
                }
                try {
                    credits = form.inputCreditiEsame.getText();
                } catch(Exception e) {
                    credits = "";
                }
                try {
                    mark = form.inputValutazioneEsame.getEditor().getText();
                } catch(Exception e) {
                    mark = "";
                }
                try {
                    date = form.inputDataEsame.getEditor().getText();
                } catch(Exception e) {
                    date = "";
                }
                toBin.writeObject(new FormCache(name,credits,mark,date));
            } catch(Exception e) {
                System.out.println("Erore nel salvare la cache form: "+e.getMessage());
        } 
    }
    public static void caricaDaCache(FormInserimentoEsame form) {
        try {
            ObjectInputStream fromBin = new ObjectInputStream(new FileInputStream("cache.bin"));
            FormCache formCachedData = (FormCache)fromBin.readObject();
            form.inputCreditiEsame.setText(formCachedData.contenutoInputCrediti);
            form.inputValutazioneEsame.getEditor().setText(formCachedData.contenutoInputValutazione);
            form.inputDataEsame.getEditor().setText(formCachedData.contenutoInputData);
            form.inputNomeEsame.setText(formCachedData.contenutoInputNome);
        } catch(IOException | ClassNotFoundException e) {
            System.out.println("Errore nel caricare la cache form: "+e.getMessage());
        }
    }
}
