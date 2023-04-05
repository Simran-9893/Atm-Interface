package atmInterface;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Operations {
	Scanner sc = new Scanner(System.in);

	void withdraw(int acct_no) throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AtmInterface", "root", "root");
			System.out.println("Enter withdraw amt. :");
			int amount = sc.nextInt();
			Statement stmt = con.createStatement();
			ResultSet rs1;
			rs1 = stmt.executeQuery("Select balance from users where acct_no=" + acct_no);

			while (rs1.next()) {
				int balance = rs1.getInt(1);
				if (balance > amount) {
					CallableStatement cstmt = con.prepareCall("{call WithdrawMoney(?, ?)}");
					cstmt.setInt(1, acct_no);
					cstmt.setInt(2, amount);
					cstmt.execute();
					System.out.println("Withdraw successfully");
				} else {
					System.out.println("Insuficient balance!!");
				}
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void deposite(int acct_no) throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AtmInterface", "root", "root");
			System.out.println("Enter deposite amt. :");
			int amount = sc.nextInt();
			CallableStatement cstmt = con.prepareCall("{call depositeMoney(?, ?)}");
			cstmt.setInt(1, acct_no);
			cstmt.setInt(2, amount);
			cstmt.execute();
			System.out.println("deposite successfully");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void transferMoney(int acct_no1) throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AtmInterface", "root", "root");
			System.out.println("Enter an account no : ");
			int acct_no2 = sc.nextInt();
			System.out.println("Enter amt to transfer :");
			int amount = sc.nextInt();
			Statement stmt =  con.createStatement();	
			ResultSet rs = stmt.executeQuery("select  balance from users where acct_no="+acct_no1);
			while(rs.next())
			{
				int balance=rs.getInt(1);
				if(balance>amount)
				{
					CallableStatement cstmt = con.prepareCall("{call Transfer(?,?,?)}");
					cstmt.setInt(1, acct_no1);
					cstmt.setInt(2, acct_no2);
					cstmt.setInt(3, amount);
					cstmt.execute();
					System.out.println("transfer successfully");
				} else {
					System.out.println("Insuficient balance!!");
				}
			}
			con.close();
			
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	void checkBalance(int acct_no) throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AtmInterface", "root", "root");
			Statement stmt =  con.createStatement();	
			ResultSet rs = stmt.executeQuery("select  balance from users where acct_no="+acct_no);
			while(rs.next())
			{
				System.out.println("Balance is : "+rs.getInt(1));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	

	
	void history(int acct_no) throws SQLException
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/AtmInterface","root","root");
			PreparedStatement stmt = con.prepareStatement("Select amount ,operation_name ,transdate  from transactions where acct_no="+acct_no);
			ResultSet rs =stmt.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getTimestamp(3));
			}
			con.close();
			
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

}
