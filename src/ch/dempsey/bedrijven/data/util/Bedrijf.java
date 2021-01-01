package ch.dempsey.bedrijven.data.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.UUID;

public class Bedrijf {

	private int id;
	private UUID owner;
	private String name;
	private double balance;
	private String creation_date;
	private BedrijfsType type;
	
	
	protected Bedrijf(int id, UUID owner, double balance, String creation_date, int type) {
		this.id = id;
		this.owner = owner;
		this.balance = balance;
		this.creation_date = creation_date;
		switch(type) {
			case 0:
				this.type = BedrijfsType.HANDEL;
				break;
			case 1:
				this.type = BedrijfsType.ARBEID;
				break;
			case 2:
				this.type = BedrijfsType.OVERHEID;
				break;
			default:
				this.type = BedrijfsType.HANDEL;
				break;
		}	
	}
	
	
	public int getId() {
		return id;
	}
	
	public UUID getOwner() {
		return owner;
	}
	
	
	public String getName() {
		return name;
	}
	
	public double getBalance() {
		return balance;
	}
	
	
	public String getCreationDate() {
		return creation_date;
	}
	
	public BedrijfsType getType() {
		return type;
	}
	
	
	public void setBalance(double balance) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bedrijven", "root", "");
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
	
}
