package jlibretto;

import java.io.Serializable;

public class Configurazioni implements Serializable {
    public static Nucleo nucleo;
    public static Preferenze preferenze;
}

class Nucleo {
	public static Integer PortaServerLog;
	public static String IPServerLog;
	public static String HostnameDatabase;
	public static Integer PortaDatabase;
	public static String UtenteDatabase;
	public static String PasswordDatabase;
}

class Preferenze {
	public static String TipoMedia;
}
