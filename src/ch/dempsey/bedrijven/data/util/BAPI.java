package ch.dempsey.bedrijven.data.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

public class BAPI {

	public static ArrayList<Bedrijf> getBedrijvenByPlayer(Player player){
		ArrayList<Bedrijf> b = new ArrayList<Bedrijf>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql = "SELECT * FROM bedrijven WHERE owner_uuid='"+player.getUniqueId().toString()+"' AND disabled = 0)";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Bedrijf bv = new Bedrijf(UUID.fromString(rs.getString("owner_uuid")), rs.getString("name"));
				b.add(bv);
			}
			st.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	public static ArrayList<Bedrijf> getBedrijvenByEmployee(Player player) {
		ArrayList<Bedrijf> b = new ArrayList<Bedrijf>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql = "SELECT * FROM bedrijven WHERE id=(SELECT company_id FROM werknemers WHERE user_uuid='"+player.getUniqueId().toString()+"' AND disabled = 0)";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Bedrijf bv = new Bedrijf(UUID.fromString(rs.getString("owner_uuid")), rs.getString("name"));
				b.add(bv);
			}
			st.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	public static void createBedrijf(Player player, String name, double balance, BedrijfsType type) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			int t;
			switch(type) {
				case ARBEID:
					t = 1;
					break;
				case OVERHEID:
					t = 2;
					break;
				default:
					t = 0;
					break;
			}
			Date d = new Date(System.currentTimeMillis());
			String now = String.valueOf(d.getYear())+"-"+String.valueOf(d.getMonth()+1)+"-"+String.valueOf(d.getDate());
			String sql = "INSERT INTO bedrijven (id, owner_uuid, balance, creation_date, disabled, type) VALUES (NULL, '"+player.getUniqueId().toString()+"' '"+name+"', "+String.valueOf(balance)+", "+now+", 0, "+String.valueOf(t)+")";
			PreparedStatement st = conn.prepareStatement(sql);
			if(st.execute()) {
				System.out.println("New company registered with name: " + name); 
			}
			st.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static Bedrijf getBedrijfById(int id) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql = "SELECT * FROM bedrijven WHERE id="+String.valueOf(id)+" AND disabled = 0";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				Bedrijf b = new Bedrijf(UUID.fromString(rs.getString("owner_uuid")), rs.getString("name"));
				st.close();
				conn.close();
				return b;
			}else {
				conn.close();
				st.close();
				return null;
			}
		}catch(Exception e) {
			return null;
		}
		
	}
	
	public static Bedrijf getBedrijfByBTW(int btw) {
		return getBedrijfById(btw);
	}
	
	
	public static void deleteBedrijf(int id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String bsql = "UPDATE bedrijven SET disabled = 1 WHERE id="+String.valueOf(id);
			String wsql = "UPDATE werknemers SET disabled = 1 WHERE company_id="+String.valueOf(id);
			String fsql = "UPDATE facturen SET disabled = 1 WHERE company_id="+String.valueOf(id);
			PreparedStatement st = conn.prepareStatement(bsql);
			PreparedStatement st1 = conn.prepareStatement(wsql);
			PreparedStatement st2 = conn.prepareStatement(fsql);
			
			st.executeUpdate();
			st1.executeUpdate();
			st2.executeUpdate();
			
			st.close();
			st1.close();
			st2.close();
			
			conn.close();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
