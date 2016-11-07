package configurazione;

import java.io.Serializable;

class Configurazioni implements Serializable {
    Nucleo Nucleo;
    Preferenze Preferenze;
    
    public Configurazioni(Nucleo n,Preferenze p) {
        Nucleo = n;
        Preferenze = p;
    }
}



