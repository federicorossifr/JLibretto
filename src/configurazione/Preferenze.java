
package configurazione;

import java.io.Serializable;

class Preferenze implements Serializable{
	String TipoMedia;
        int MinValutazione;
        int MaxValutazione;
        
        public Preferenze(String tm) {
            TipoMedia = tm;
        }
}
