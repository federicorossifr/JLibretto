package configurazione;

import java.io.Serializable;

public class Nucleo implements Serializable {
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