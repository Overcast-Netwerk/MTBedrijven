package ch.dempsey.bedrijven.data.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class Factuur {

	private int company_id, id;
	
	protected Factuur(int company_id, int id) {
		this.company_id = company_id;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public int getCompanyId() {
		return company_id;
	}
	
	public UUID getClient() {
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql  = "SELECT * FROM facturen WHERE id="+String.valueOf(id)+" AND company_id="+String.valueOf(company_id)+" AND disabled=0";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				
				UUID res = UUID.fromString(rs.getString("client_uuid"));
				st.close();
				conn.close();
				return res;
			}
			
			return null;
		}catch(Exception e) {
			return null;
		}
		
	}
	
	public UUID getCreator() {
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql  = "SELECT * FROM facturen WHERE id="+String.valueOf(id)+" AND company_id="+String.valueOf(company_id)+" AND disabled=0";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				UUID res = UUID.fromString(rs.getString("creator_uuid"));
				st.close();
				conn.close();
				return res;
			}
			
			return null;
		}catch(Exception e) {
			return null;
		}
	}
	
	public boolean isPaid() {
		try {	
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql  = "SELECT * FROM facturen WHERE id="+String.valueOf(id)+" AND company_id="+String.valueOf(company_id)+" AND disabled=0";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				boolean res;
				if(rs.getInt("paid") != 1) {
					res = false;
				}else {
					res = true;
				}	
				st.close();
				conn.close();
				return res;
			}
			
			return false;
		}catch(Exception e) {
			return false;
		}
	}
	
	public double getPrice() {
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql  = "SELECT * FROM facturen WHERE id="+String.valueOf(id)+" AND company_id="+String.valueOf(company_id)+" AND disabled=0";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				double res = rs.getInt("price");
				st.close();
				conn.close();
				return res;
			}
			
			return 0;
		}catch(Exception e) {
			return 0;
		}
	}
	
	public String getDescription() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql  = "SELECT * FROM facturen WHERE id="+String.valueOf(id)+" AND company_id="+String.valueOf(company_id)+" AND disabled=0";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				String res = rs.getString("description");
				st.close();
				conn.close();
				return res;
			}
			
			return null;
		}catch(Exception e) {
			return null;
		}
	}
	
}
