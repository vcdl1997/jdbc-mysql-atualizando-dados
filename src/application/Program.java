package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

import db.DB;
import db.DbException;

public class Program {

	public static void main(String[] args){
		Locale.setDefault(Locale.US);
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM seller LIMIT 10");
			
			while(rs.next()) {
				int id = rs.getInt("Id");
				Double salary = rs.getDouble("BaseSalary") * 1.10;
				String seller = rs.getString("Name");
			
				PreparedStatement ps = conn.prepareStatement("UPDATE seller "
					+ "SET BaseSalary = ? "
					+ "WHERE Id = ?");
				ps.setDouble(1, salary);
				ps.setInt(2, id);
				
				int rowsAffected = ps.executeUpdate();
				
				if(rowsAffected > 0) {
					System.out.printf("Salário do profissional %s com aumento de 10%%: %.2f\n", seller, salary);
				}
			}
			
		}catch(DbException e) {
			System.out.println(e.getMessage());
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
		
	}

}
