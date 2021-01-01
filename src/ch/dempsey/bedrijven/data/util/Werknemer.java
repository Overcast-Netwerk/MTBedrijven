package ch.dempsey.bedrijven.data.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.UUID;

public class Werknemer {

	private int id;
	private UUID user;
	private int company_id;
	private WerknemersRol role;
	private double pay;
	private String hiringDate;
	
	protected Werknemer(int id, UUID user, int company_id, int role, double pay, String hiringDate) {
		this.id = id;
		this.user = user;
		this.company_id = company_id;
		this.pay = pay;
		this.hiringDate = hiringDate;
		switch(role) {
			case 1:
				this.role = WerknemersRol.TEAM_LEADER;
				break;
			case 2:
				this.role = WerknemersRol.ADMINSTRATOR;
				break;
			case 3:
				this.role = WerknemersRol.EIGENAAR;
				break;
			default:
				this.role = WerknemersRol.WERKNEMER;
				break;
		}
	}
	
	public int getId() {
		return id;
	}
	
	public UUID getUuid() {
		return user;
	}
	
	public int getCompanyId() {
		return company_id;
	}
	
	public WerknemersRol getRole() {
		return role;
	}
	
	public double getPay() {
		return pay;
	}
	
	public void setPay(double pay) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bedrijven", "root", "");
			String sql = "UPDATE werknemers SET pay="+String.valueOf(pay)+" WHERE company_id="+String.valueOf(company_id)+" AND user_uuid='"+user.toString()+"'";
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
		return hiringDate;
	}
	
	
	
}
