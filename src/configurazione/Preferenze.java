
package configurazione;

import java.io.Serializable;

public class Preferenze implements Serializable{
	public String TipoMedia;
        public int MinValutazione;
        public int MaxValutazione;
        public String CodiceUtente;
        
        public Preferenze(String tm,int mv,int Mv,String cu) {
            TipoMedia = tm;
            MinValutazione = mv;
            MaxValutazione = Mv;
            CodiceUtente = cu;
        }
}
