package jlibretto;

import java.io.Serializable;

public class Configurazioni implements Serializable {
    Nucleo Nucleo;
    Preferenze Preferenze;
    
    public Configurazioni(Nucleo n,Preferenze p) {
        Nucleo = n;
        Preferenze = p;
    }
    
    public String getIPServerLog() {
        return Nucleo.IPServerLog;
    }
    
    public Integer getPortaServerLog() {
        return Nucleo.PortaServerLog;
    }
    
}

class Nucleo implements Serializable {
	public Integer PortaServerLog;
	public String IPServerLog;
	public String HostnameDatabase;
	public Integer PortaDatabase;
	public String UtenteDatabase;
	public String PasswordDatabase;
        
        public Nucleo(Integer ps,String ips,String hd,Integer pd,String ud,String pwdb) {
            PortaServerLog = ps;
            IPServerLog = ips;
            HostnameDatabase = hd;
            PortaDatabase = pd;
            UtenteDatabase = ud;
            PasswordDatabase = pwdb;
        }
        
}

class Preferenze implements Serializable{
	public String TipoMedia;
        
        public Preferenze(String tm) {
            TipoMedia = tm;
        }
}
