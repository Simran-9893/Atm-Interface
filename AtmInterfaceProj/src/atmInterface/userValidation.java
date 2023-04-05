package atmInterface;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class userValidation {

	Scanner sc = new Scanner(System.in);
	int acct_no;

	void validation() throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AtmInterface", "root", "root");
			System.out.println("Enter Account No. :");
			int acct_no = sc.nextInt();
			this.acct_no=acct_no;
			System.out.println("Enter Pin :");
			int pin = sc.nextInt();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("Select acct_no,pin from users where acct_no=" + acct_no);
			if (rs.next()) {

				if (acct_no == rs.getInt(1) && pin == rs.getInt(2)) {
					System.out.println("Login Successfull");
					this.acct_no=acct_no;
					Operations ops= new Operations();
					int choice;
					while(true)
					{
						System.out.println("Enter your choice");
						System.out.println("1.Withdraw");
						System.out.println("2.Deposite");
						System.out.println("3.Transfer");
						System.out.println("4.History");
						System.out.println("5.Check Balance");
						System.out.println("6.Exit");
						choice = sc.nextInt();
						if(choice==1)
						{
							ops.withdraw(acct_no);
						}	
						else if(choice==2) {
							ops.deposite(acct_no);
						}
						else if(choice==3) {
							ops.transferMoney(acct_no);
						}
						else if(choice==4) {
							ops.history(acct_no);
						}
						else if(choice==5) {
							ops.checkBalance(acct_no);
						}
						else if(choice==6) {
							System.out.println("Successfully Exit");
							System.exit(0);
						}
						else
						{
							System.out.println("Enter valid choice");
						}
						
					}
					
				} else {
					System.out.println("Invalid pin!!! ");
				}
			} else {
				System.out.println("Invalid acctount no");
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		userValidation uv = new userValidation();
		uv.validation();

	}

}