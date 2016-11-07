package configurazione;

import java.io.Serializable;

class Nucleo implements Serializable {
	Integer PortaServerLog;
	String IPServerLog;
	String HostnameDatabase;
	Integer PortaDatabase;
	String UtenteDatabase;
	String PasswordDatabase;
        
        public Nucleo(Integer ps,String ips,String hd,Integer pd,String ud,String pwdb) {
            PortaServerLog = ps;
            IPServerLog = ips;
            HostnameDatabase = hd;
            PortaDatabase = pd;
            UtenteDatabase = ud;
            PasswordDatabase = pwdb;
        }
        
}