package configurazione;

import java.io.Serializable;

public class Configurazioni implements Serializable {
    public Nucleo Nucleo;
    public Preferenze Preferenze;
    
    public Configurazioni(Nucleo n,Preferenze p) {
        Nucleo = n;
        Preferenze = p;
    }
}



