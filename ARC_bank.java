package BANK;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ARC_bank {

	static Scanner sc = new Scanner(System.in);
	static Connection c;
	static Statement s;
	static int chances = 3;
	static int idChance=3;
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
				c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ARCBank", "root", "reet9516");
				s = c.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		chooseOption();
	}

	public static void chooseOption() {
		System.out.println("Welcome to ARC BANK");
		System.out.println();
		System.out.println("Press->1  for Manager Login");
		System.out.println("Press->2  for USER Login");
		int num = sc.nextInt();
		switch (num) {
		
		case 1:
			System.out.println("------------------------------------------------------------------------------------------------------------------------");

			managerLogin();
			break;
		case 2:
			System.out.println("------------------------------------------------------------------------------------------------------------------------");

			loginOptions();
			break;
		default:
			System.out.println("please Enter a valid Option");
			System.out.println("------------------------------------------------------------------------------------------------------------------------");

			chooseOption();
		}

	}
	private static void loginOptions() {
		System.out.println("Welcome to ARC Bank");
		System.out.println("------------------------------------------------------------------------------------------------------------------------");
		System.out.println("Press 1 to Create Account");
		System.out.println("Press 2 to Login");
		System.out.println("Press 3 to LogOut and Go to HomePage");
		int num=sc.nextInt();
		switch(num) {
		case 1:
			System.out.println("------------------------------------------------------------------------------------------------------------------------");
			createAccount();
			break;
		case 2:
			System.out.println("------------------------------------------------------------------------------------------------------------------------");
		      userLogin();
		      break;
		case 3:
			System.out.println("------------------------------------------------------------------------------------------------------------------------");
		          chooseOption();
		          break;
			default:
				System.out.println("please enter valid option");
				System.out.println("------------------------------------------------------------------------------------------------------------------------");
				loginOptions();
		}
	}

	static public void managerLogin() {
		System.out.println("Hey manager welcome to the Bank");
		System.out.println("Please enter your valid Id ");
		int idNum = sc.nextInt();
		String finalid = "'" + idNum + "'";
		String idQuery = "select * from IDandPassword where ID =" + finalid;
		boolean found =false;
		try {
			boolean result = s.execute(idQuery);
			if (result) {
				ResultSet rs = s.getResultSet();
				while (rs.next()) {
					if (idNum == (rs.getInt(1))) {
						found=true;
						System.out.println("please Enter your valid Password");
						String password = sc.next();
						if (password.equals(rs.getString(2))) {
							System.out.println("------------------------------------------------------------------------------------------------------------------------");

							ManagerScreen();
						} else {
							if (chances > 0) {
								--chances;
								System.out.println("password is wrong");
								System.out.println("You have " + chances + " left");
								System.out.println("------------------------------------------------------------------------------------------------------------------------");

								managerLogin();
							} else {
								System.out.println("Try after few hours");
							}
						}
					}
				}
				if(found==false) {
					System.out.println(idNum+" is not found. Please create a new Account");
					System.out.println("------------------------------------------------------------------------------------------------------------------------");

					createAccount();
				} 

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		chances=3;

	}

	public static void userLogin() {
		boolean found=false;
		System.out.println("Hey User welcome to the Bank");
		System.out.println("Please enter your valid Id ");
		int idNum = sc.nextInt();
		String finalid = "'" + idNum + "'";
		String idQuery = "select * from IDandPassword where ID =" + finalid;
		try {
			boolean result = s.execute(idQuery);
			if (result) {
				ResultSet rs = s.getResultSet();
				while (rs.next()) {
					if (idNum == (rs.getInt(1))) {
						found=true;
						System.out.println("please Enter your valid Password");
						String password = sc.next();
						if (password.equals(rs.getString(2))) {
							System.out.println("------------------------------------------------------------------------------------------------------------------------");

							UserScreen(idNum);
						} else {
							if (chances >1) {
								chances--;
								System.out.println("password is wrong");
								System.out.println("You have " + chances + " attempt left");
								System.out.println("------------------------------------------------------------------------------------------------------------------------");

								userLogin();
							} else {
								System.out.println("You have reached the maximum try limits! Try Tomorrow");
								System.exit(0);
							}
						}
					}
				} if(found==false) {
					if(idChance>1) {
						idChance--;
					
					System.out.println("Account with "+idNum+" is not found. You have "+idChance+" attempt left");
					System.out.println("------------------------------------------------------------------------------------------------------------------------");
					System.out.println("Would you like to Login Again!--> Press 1--> to login.");
					System.out.println("Would you like to create a new account!--> Press 2--> to Create New Account.");
					System.out.println("Would you like to LogOut!--> Press 3--> to logOut.");
					int num=sc.nextInt();
					switch(num) {
					case 1:userLogin();
					break;
					case 2:createAccount();
					break;
					case 3:chooseOption();
					break;
						default:
							System.out.println("Invalid Input");
							chooseOption();
					}
				}else {
					System.out.println("You have reached the maximum limit of try. Try to login tomorrow");
					System.exit(0);
				}
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		chances=3;
		idChance=3;
	}
	

	public static void ManagerScreen() {
		System.out.println("This is Manager Work Screen");
	}

	
	public static void createAccount() {
		System.out.println("Hello Welcome to the new Account creation page.");
		System.out.println("Please provide the following Details correctly.");
		System.out.println();
		System.out.println("Enter Your Name");
		String name = sc.next();
		System.out.println();
		System.out.println("Enter the Mobile Number");
		String mobileNum = sc.next();
		System.out.println();
		System.out.println("Enter your EmailId ");
		String email = sc.next();
		System.out.println();
		System.out.println("Enter Your 12 digit AadhaarNumber");
		String aadharNum = sc.next();
		System.out.println();
		int NewUserId = (int) (Math.random() * Math.pow(10, 6));
		System.out.println(" Your USERID is " + NewUserId);
		System.out.println();
		System.out.println("Create a Strong Password");
		String UserPassword = sc.next();
		System.out.println("Password created Successfully please remember password for future uses.");
//		String inserQuery = "insert into USERDETAILS values ('" + name + "','" + mobileNum + "','" + email + "','"
//				+ aadharNum + "','" + UserPassword + "','" + NewUserId + "')";
		//System.out.println(inserQuery);
		System.out.println("----------------------------------------------------------------------------------------------");
       
		try {
			s.executeUpdate("insert into userdetails value ('" + name + "','" + mobileNum + "','" + email + "','"
					+ aadharNum + "','" + UserPassword + "','" + NewUserId + "')");
			s.executeUpdate("insert into idandpassword value ('"+NewUserId+"','"+UserPassword+"')");
			s.executeUpdate("insert into amountdetails value ('"+NewUserId+"','"+0+"','"+0+"','"+0+"')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 userLogin();
	}
	
	private static void UserScreen( int id) throws SQLException {
		String name=null;
	try {
		ResultSet rs=	s.executeQuery("select * from userdetails");
		while(rs.next()) {
			if(rs.getInt(6)==id) {
				name=rs.getNString(1);
			}
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
		System.out.println("Hello "+name+" , Welcome to the arc bank");
		System.out.println();
		System.out.println("Press 1--> To check Amount Balance");
		System.out.println("Press 2--> To check Amount Deposit");
		System.out.println("Press 3--> To check Amount withdraw");
		System.out.println("Press 4--> To Transfer the Money");
		System.out.println("Press 5--> To check Transaction Details");
		System.out.println("Press 6--> To check Log Out");
		int num=sc.nextInt();
		switch(num) {
		case 1:checkBalance(id,name);
			break;
		case 2:DepositAmount(id, name);
			break;
		case 3:withDrawAmount(id, name);
			break;
		case 4:transferMoney(id,name);
			break;
		case 5:transactionDetails(id,name);
			break;
		case 6:chooseOption();
			break;
			default:
				System.out.println("Please Enter a Valid Option");
				UserScreen(id);
		}
	
	}
	private static void checkBalance(int id,String name) throws SQLException {
		int totalAmount=0;
		try {
			ResultSet rs=s.executeQuery("select * from amountdetails");
			while(rs.next()) {
				if(rs.getInt(1)==id) {
					totalAmount=rs.getInt(4);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("HEY! "+name+", Your current balance is "+totalAmount);
		System.out.println();
		System.out.println("Press 1--> To Home Page");
		System.out.println("Press 2--> To LogOut");
		int num=sc.nextInt();
		switch (num) {
		case 1: UserScreen(id);
		break;
		case 2:chooseOption();
		break;
		default:
			checkBalance(id,name);
		}
	}
    private static void transferMoney(int id,String name) {
		System.out.println("Welcome to Amount Transfer Page");
		ResultSet rs=null;
		int totalAmount=0;
		int senderDebtAmt=0;
		try {
			 rs=s.executeQuery("select * from amountdetails");
			while(rs.next()) {
				if(rs.getInt(1)==id) {
					totalAmount=rs.getInt(4);
					senderDebtAmt=rs.getInt(2);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Please Enter Amount Recieptant ID");
		int recid=sc.nextInt();
		try {
			rs=s.executeQuery("select * from amountdetails");
		 while(rs.next()) {
			 if(rs.getInt(1)==recid) {
				 int recCurrAmt=rs.getInt(4);
				 int recCreditAmt=rs.getInt(3);
				 System.out.println("Enter the Amount value");
				 int amount=sc.nextInt();
				 System.out.println("Press 1--> To send the Money");
				 System.out.println("Press 2--> To cancel the Transaction");
				 System.out.println("Press 3--> To go on the Home page");
				 System.out.println("Press 4--> To LogOut");
				 int num=sc.nextInt();
				 switch (num) {
				case 1:
					if(amount<totalAmount) {
						recCurrAmt+=amount;
						recCreditAmt+=amount;
						totalAmount-=amount;
						senderDebtAmt+=amount;
						s.executeUpdate("update amountdetails set Current_balance="+recCurrAmt+", total_credit="
								+recCreditAmt+" where ID="+recid+";");
						s.executeUpdate("update amountdetails set Current_balance="+totalAmount+", total_debt="
								+senderDebtAmt+" where ID="+id+";");
						System.out.println(amount+" has been sent SuccessFully");
						System.out.println("Your remaining account balance is "+totalAmount);
						System.out.println("------------------------------------------------------------------------------------------------------------------------");
						UserScreen(id);
					}else {
						System.out.println("You don't have Sufficient Balane in your Account");
						UserScreen(id);
					}
					break;
				case 2:transferMoney(id, name);
				break;
				case 3:UserScreen(id);
				break;
				case 4:chooseOption();
				break;
				default:
					System.out.println("Invalid Input");
					transferMoney(id, name);
					
				}
			 }
		 }
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
    private static void DepositAmount(int id,String name) throws SQLException {
    	ResultSet rs =s.executeQuery("select total_credit, Current_balance from amountdetails where ID= "+id);
    	rs.next();
    	int currBal=rs.getInt(2);
    	int currCre=rs.getInt(1);
    	System.out.println("Please Enter amount to Deposit. It must be greater than 100rs");
    	int amount=sc.nextInt();
    	if(amount>100) {
    		currBal+=amount;
    		currCre+=amount;
    		s.execute("update amountdetails set total_credit="+currCre+" , Current_balance="+currBal +" where ID="+id);
    		System.out.println(amount+ " has been deposit successfully. Your current balance is --> "+currBal);
    		System.out.println();
    		System.out.println("Press 1 to return to Home Page");
    		System.out.println("Press 2 to LogOut");
    		int num=sc.nextInt();
    		switch (num) {
			case 1: UserScreen(id);
			break;
			case 2:
				System.out.println("Thank you! For Banking with us...");
				System.exit(1);
			default:
				System.out.println("Invalid Input");
				UserScreen(id);
			}
    	}
    }
    private static  void withDrawAmount(int id,String name) throws SQLException {
    	ResultSet rs =s.executeQuery("select total_debt, Current_balance from amountdetails where ID= "+id);
    	rs.next();
    	int currBal=rs.getInt(2);
    	int currDebt=rs.getInt(1);
    	System.out.println("Please Enter amount to Withdraw. It must be greater than 100rs");
    	int amount=sc.nextInt();
    	if(amount<currBal && amount>100) {
    		currBal-=amount;
    		currDebt=amount;
    		s.execute("update amountdetails set total_debt="+currDebt+" , Current_balance="+currBal +" where ID="+id);
    		System.out.println(amount+ " has been Withdrawn successfully. Your current balance is --> "+currBal);
    		System.out.println();
    		System.out.println("Press 1 to return to Home Page");
    		System.out.println("Press 2 to LogOut");
    		int num=sc.nextInt();
    		switch (num) {
			case 1: UserScreen(id);
			break;
			case 2:
				System.out.println("Thank you! For Banking with us...");
				System.exit(1);
			default:
				System.out.println("Invalid Input");
				UserScreen(id);
			}
    	}else {
    		System.out.println("Insufficient balance! Please try again");
    		withDrawAmount(id, name);
    	}
    }
    private static void transactionDetails(int id,String name) throws SQLException {
    	int da=0;
    	int ca=0;
		try {
			ResultSet rs= s.executeQuery("select * from amountdetails");
			while(rs.next()) {
				if(id==rs.getInt(1)) {
					da=rs.getInt(2);
					ca=rs.getInt(3);
				}
			}
			
		} catch (SQLException e) {
						e.printStackTrace();
		}
		System.out.println("For ID= "+id+" total Debit amount is "+da+" and Total Credit Amount is "+ca+".");
		System.out.println("Press 1--> For HomePage");
		System.out.println("Press 2--> For LogOut");
		System.out.println("Press 3--> For Close the Program");
		int num=sc.nextInt();
		switch(num) {
		case 1:UserScreen(id);
		break;
		case 2:chooseOption();
		break;
		case 3:
			System.out.println();
			System.exit(0);
		break; 
			default:
				System.out.println("Invalid Input");
				UserScreen(id);
		}
    	
	}

}
