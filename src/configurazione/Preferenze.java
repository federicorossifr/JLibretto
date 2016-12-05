
package configurazione;

import java.io.Serializable;

public class Preferenze implements Serializable{
	public String TipoMedia;
        public int MinValutazione;
        public int MaxValutazione;
        
        public Preferenze(String tm,int mv,int Mv) {
            TipoMedia = tm;
            MinValutazione = mv;
            MaxValutazione = Mv;
        }
}
