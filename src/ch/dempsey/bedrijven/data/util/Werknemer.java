package ch.dempsey.bedrijven.data.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class Werknemer {

	private int id;
	private UUID user;
	private int company_id;
	private WerknemersRol role;
	private double pay;
	private String hiringDate;
	
	protected Werknemer(UUID user, int company_id) {
		this.user = user;
		this.company_id = company_id;
		
	}
	
	public int getId() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql = "SELECT * FROM werknemers WHERE company_id="+String.valueOf(company_id)+" AND user_uuid='"+user.toString()+"' AND disabled = 0";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				int ids = rs.getInt("id");
				st.close();
				conn.close();
				return ids;
			}
			conn.close();
			st.close();
			return 0;
		}catch(Exception e) {
			return 0;
		}
	}
	
	public UUID getUuid() {
		return user;
	}
	
	public int getCompanyId() {
		return company_id;
	}
	
	public WerknemersRol getRole() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql = "SELECT * FROM werknemers WHERE company_id="+String.valueOf(company_id)+" AND user_uuid='"+user.toString()+"' AND disabled = 0";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				switch(rs.getInt("role") ) {
					case 1:
						return WerknemersRol.TEAM_LEADER;
					case 2:
						return WerknemersRol.ADMINSTRATOR;
					case 3:
						return WerknemersRol.EIGENAAR;
					default:
						return WerknemersRol.WERKNEMER;
				}
				
			}
			conn.close();
			st.close();
			return WerknemersRol.WERKNEMER;
		}catch(Exception e) {
			return WerknemersRol.WERKNEMER;
		}
	}
	
	public void setRole(WerknemersRol role) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			int r;
			switch(role) {
				case TEAM_LEADER:
					r = 1;
					break;
				case ADMINSTRATOR:
					r = 2;
					break;
				case EIGENAAR:
					r = 3;
					break;
				default:
					r = 0;
					break;
			}
			String sql = "UPDATE werknemers SET role="+String.valueOf(r)+" WHERE user_uuid = '"+user.toString()+"' AND company_id="+String.valueOf(getCompanyId())+"";
			PreparedStatement st = conn.prepareStatement(sql);
			if(st.execute()) {
				System.out.println("Updated role for " + user.toString());
			}
			st.close();
			conn.close();
		}catch(Exception e) {
			System.out.println("Failed to update role  for " + user.toString());
		}
	}
	
	public double getPay() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql = "SELECT * FROM werknemers WHERE company_id="+String.valueOf(company_id)+" AND user_uuid='"+user.toString()+"' AND disabled = 0";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				return rs.getInt("pay");
			}
			conn.close();
			st.close();
			return 0;
		}catch(Exception e) {
			return 0;
		}
		
	}
	
	public void setPay(double pay) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql = "UPDATE werknemers SET pay="+String.valueOf(pay)+" WHERE company_id="+String.valueOf(company_id)+" AND user_uuid='"+user.toString()+"' AND disabled = 0";
			PreparedStatement st = conn.prepareStatement(sql);
			if(st.execute()) {
				System.out.println("Updated pay for employee " + String.valueOf(id) + ":" + user.toString() + " to " + String.valueOf(pay));
			}
			st.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getHiringDate() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql = "SELECT * FROM werknemers WHERE company_id="+String.valueOf(company_id)+" AND user_uuid='"+user.toString()+"' AND disabled = 0";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				return rs.getTimestamp("hiring_date").toString();
			}
			conn.close();
			st.close();
			return null;
		}catch(Exception e) {
			return null;
		}
	}
	
	
	
}
