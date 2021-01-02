package ch.dempsey.bedrijven.data.util;

public class Info {

	protected static String host = "localhost";
	protected static int port = 3306;
	protected static String database = "bedrijven";
	protected static String username = "root";
	protected static String password = "";
	
	protected static String url = "jdbc:mysql://"+host+":"+String.valueOf(port)+"/"+database+"?useSSL=false";
	
}
