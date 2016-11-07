
package configurazione;

import java.io.Serializable;

class Preferenze implements Serializable{
	String TipoMedia;
        
        public Preferenze(String tm) {
            TipoMedia = tm;
        }
}
