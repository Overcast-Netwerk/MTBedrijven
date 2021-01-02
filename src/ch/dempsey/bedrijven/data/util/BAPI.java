package ch.dempsey.bedrijven.data.util;

import java.sql.Connection;
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
		
		
		
	}
	
}
