package ch.dempsey.bedrijven.data.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

public class Bedrijf {

	private int id;
	private UUID owner;
	private String name;
	
	
	protected Bedrijf(UUID owner, String name) {
		this.owner = owner;
		this.name = name;	
	}
	
	
	public int getId() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql = "SELECT * FROM companies WHERE name='"+name+"' AND owner_uuid='"+owner.toString()+"' AND disabled = 0";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				return rs.getInt("id");
			}
			conn.close();
			st.close();
			return 0;
		}catch(Exception e) {
			return 0;
		}
	}
	
	public UUID getOwner() {
		return owner;
	}
	
	
	public String getName() {
		return name;
	}
	
	public double getBalance() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql = "SELECT * FROM companies WHERE name='"+name+"' AND owner_uuid='"+owner.toString()+"' AND disabled = 0";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				return Double.valueOf(rs.getInt("id"));
			}
			conn.close();
			st.close();
			return 0;
		}catch(Exception e) {
			return 0;
		}
	}
	
	
	public String getCreationDate() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql = "SELECT * FROM companies WHERE name='"+name+"' AND owner_uuid='"+owner.toString()+"' AND disabled = 0";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				return rs.getTimestamp("creation_date").toString();
			}
			conn.close();
			st.close();
			return null;
		}catch(Exception e) {
			return null;
		}
	}
	
	public BedrijfsType getType() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql = "SELECT * FROM companies WHERE name='"+name+"' AND owner_uuid='"+owner.toString()+"' AND disabled = 0";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				switch(rs.getInt("type" )) {
					case 1:
						return BedrijfsType.ARBEID;
					case 2:
						return BedrijfsType.OVERHEID;
					default:
						return  BedrijfsType.HANDEL;
				}
			}
			conn.close();
			st.close();
			return BedrijfsType.HANDEL;
		}catch(Exception e) {
			return BedrijfsType.HANDEL;
		}
	}
	
	
	public void setBalance(double balance) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql = "UPDATE bedrijven SET balance="+String.valueOf(balance)+" WHERE id="+String.valueOf(id);
			PreparedStatement st = conn.prepareStatement(sql);
			if(st.execute()) {
				System.out.println("Updated Balance for company " + String.valueOf(id) + ":" + name + " to " + String.valueOf(balance));
			}
			st.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Werknemer> getWerknemers() {
		ArrayList<Werknemer> emp = new ArrayList<Werknemer>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql = "SELECT * FROM werknemers WHERE company_id = "+String.valueOf(getId())+" AND disabled = 0 ";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Werknemer werk = new Werknemer(UUID.fromString(rs.getString("user_uuid")), rs.getInt("company_id"));
				emp.add(werk);
			}
			st.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return emp;
	}
	
	public boolean isWerknemer(Werknemer werknemer) {
		return getWerknemers().contains(werknemer);
	}
	
	public void createFactuur(UUID client, UUID creator, int price, String description) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql = "INSERT INTO facturen (id, company_id, client_uuid, price, description, paid, creation_date, disabled, creator_uuid)"
					+ " VALUES (NULL, "+String.valueOf(getId())+", '"+client.toString()+"', "+String.valueOf(price)+", '"+description+", 0, null, 0, '"+creator.toString()+"')";
			PreparedStatement st = conn.prepareStatement(sql);
			if(st.execute()) {
				System.out.println("Created a new invoice for company: " + getId());
			}
			
			conn.close();
			st.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Factuur> getFacturen(){
		ArrayList<Factuur> facturen = new ArrayList<Factuur>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql = "SELECT * FROM facturen WHERE company_id="+String.valueOf(getId())+" AND disabled=0";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Factuur f = new Factuur(rs.getInt("company_id"), rs.getInt("id"));
				facturen.add(f);
			}
			st.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return facturen;
	}
	
	public void hireWerknemer(UUID user, double pay, WerknemersRol role) {
		try {
			
			Date d = new Date(System.currentTimeMillis());
			String now = String.valueOf(d.getYear())+"-"+String.valueOf(d.getMonth()+1)+"-"+String.valueOf(d.getDate());
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			int rs;
			switch(role) {
				case TEAM_LEADER:
					rs = 1;
					break;
				case ADMINSTRATOR:
					rs = 2;
					break;
				case EIGENAAR:
					rs = 3;
					break;
				default:
					rs = 0;
					break;
			}
			String sql = "INSERT INTO werknemers (id, user_uuid, company_id, role, pay, hiring_date, disabled) VALUES (NULL, "+user.toString()+", "+getId()+", "+role+", "+pay+", "+now+", 0)";
			PreparedStatement st = conn.prepareStatement(sql);
			if(st.execute()) {
				System.out.println("Company " + name + " has hired a new employee!");
			}
			st.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void fireWerknemer(UUID user) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(Info.url, Info.username, Info.password);
			String sql = "UPDATE werknemers SET disabled = 1 WHERE user_uuid='"+user.toString()+"' AND company_id='"+getId()+"' AND disabled = 0";
			PreparedStatement st = conn.prepareStatement(sql);
			st.execute();
			st.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
