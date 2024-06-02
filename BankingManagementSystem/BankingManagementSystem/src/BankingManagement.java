import java.sql.*;
import java.util.Scanner;
public class BankingManagement {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String USER = "system";
    private static final String PASSWORD = "Pooja@1008";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner=new Scanner(System.in);
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
//          createTables();
//			int flag=1;
//			int userch;
//			while(flag==1) {
//				addCustomer(scanner);
//				addAccount(scanner);
//				addLoan(scanner);
//				System.out.println("Added Succesfully.");
//				userch=scanner.nextInt();
//				if(userch==0) {
//					flag=0;
//				}
//			}
			
	            while (true) {
	                System.out.println("1. Show Customer Records");
	                System.out.println("2. Add Customer Record");
	                System.out.println("3. Delete Customer Record");
	                System.out.println("4. Update Customer Information");
	                System.out.println("5. Show Account Details of a Customer");
	                System.out.println("6. Show Loan Details of a Customer");
	                System.out.println("7. Deposit Money to an Account");
	                System.out.println("8. Withdraw Money from an Account");
	                System.out.println("9. Exit");
	                System.out.print("Enter your choice: ");
	                int choice = scanner.nextInt();

	                switch (choice) {
	                    case 1:
	                        showCustomerRecords(connection);
	                        break;
	                    case 2:
	                        addCustomerRecord(connection, scanner);
	                        break;
	                    case 3:
	                        deleteCustomerRecord(connection, scanner);
	                        break;
	                    case 4:
	                        updateCustomerInformation(connection, scanner);
	                        break;
	                    case 5:
	                        showAccountDetails(connection, scanner);
	                        break;
	                    case 6:
	                        showLoanDetails(connection, scanner);
	                        break;
	                    case 7:
	                        depositMoney(connection, scanner);
	                        break;
	                    case 8:
	                        withdrawMoney(connection, scanner);
	                        break;
	                    case 9:
	                        System.out.println("Exiting...");
	                        return;
	                    default:
	                        System.out.println("Invalid choice. Please try again.");
	                }
	            }
			
			} 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

		
	 public static void createTables() throws SQLException {
	        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	             Statement stmt = conn.createStatement()) {

	            // Create CUSTOMER table
	            String createCustomerTable = "CREATE TABLE CUSTOMER01 ("
	                    + "CUST_NO INT PRIMARY KEY, "
	                    + "NAME VARCHAR(100), "
	                    + "PHONENO VARCHAR(15), "
	                    + "CITY VARCHAR(50)"
	                    + ")";
	            stmt.executeUpdate(createCustomerTable);
	            System.out.println("CUSTOMER table created successfully.");

	            // Create ACCOUNT table
	            String createAccountTable = "CREATE TABLE ACCOUNT01 ("
	                    + "ACCOUNT_NO INT PRIMARY KEY, "
	                    + "CUST_NO INT, "
	                    + "TYPE VARCHAR(20), "
	                    + "BALANCE DECIMAL(15, 2), "
	                    + "BRANCH_CODE VARCHAR(10), "
	                    + "BRANCH_NAME VARCHAR(100), "
	                    + "BRANCH_CITY VARCHAR(50), "
	                    + "FOREIGN KEY (CUST_NO) REFERENCES CUSTOMER01(CUST_NO)"
	                    + ")";
	            stmt.executeUpdate(createAccountTable);
	            System.out.println("ACCOUNT table created successfully.");

	            // Create LOAN table
	            String createLoanTable = "CREATE TABLE LOAN01 ("
	                    + "LOAN_NO INT PRIMARY KEY, "
	                    + "CUST_NO INT, "
	                    + "LOAN_AMOUNT DECIMAL(15, 2), "
	                    + "BRANCH_CODE VARCHAR(10), "
	                    + "BRANCH_NAME VARCHAR(100), "
	                    + "BRANCH_CITY VARCHAR(50), "
	                    + "FOREIGN KEY (CUST_NO) REFERENCES CUSTOMER(CUST_NO)"
	                    + ")";
	            stmt.executeUpdate(createLoanTable);
	            System.out.println("LOAN table created successfully.");

	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw e;
	        }
	    }
	  //Method to add a customer
    public static void addCustomer(Scanner scanner) throws SQLException {
        System.out.print("Enter Customer No: ");
        int custNo = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone No: ");
        String phoneNo = scanner.nextLine();
        System.out.print("Enter City: ");
        String city = scanner.nextLine();
        
        String query = "INSERT INTO CUSTOMER01 (CUST_NO, NAME, PHONENO, CITY) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, custNo);
            pstmt.setString(2, name);
            pstmt.setString(3, phoneNo);
            pstmt.setString(4, city);
            pstmt.executeUpdate();
            System.out.println("Customer added successfully.");
        }
    }

    // Method to add an account
    public static void addAccount(Scanner scanner) throws SQLException {
        System.out.print("Enter Account No: ");
        int accountNo = scanner.nextInt();
        System.out.print("Enter Customer No: ");
        int custNo = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Account Type: ");
        String type = scanner.nextLine();
        System.out.print("Enter Balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Branch Code: ");
        String branchCode = scanner.nextLine();
        System.out.print("Enter Branch Name: ");
        String branchName = scanner.nextLine();
        System.out.print("Enter Branch City: ");
        String branchCity = scanner.nextLine();
        
        String query = "INSERT INTO ACCOUNT01 (ACCOUNT_NO, CUST_NO, TYPE, BALANCE, BRANCH_CODE, BRANCH_NAME, BRANCH_CITY) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, accountNo);
            pstmt.setInt(2, custNo);
            pstmt.setString(3, type);
            pstmt.setDouble(4, balance);
            pstmt.setString(5, branchCode);
            pstmt.setString(6, branchName);
            pstmt.setString(7, branchCity);
            pstmt.executeUpdate();
            System.out.println("Account added successfully.");
        }
    }

    // Method to add a loan
    public static void addLoan(Scanner scanner) throws SQLException {
        System.out.print("Enter Loan No: ");
        int loanNo = scanner.nextInt();
        System.out.print("Enter Customer No: ");
        int custNo = scanner.nextInt();
        System.out.print("Enter Loan Amount: ");
        double loanAmount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Branch Code: ");
        String branchCode = scanner.nextLine();
        System.out.print("Enter Branch Name: ");
        String branchName = scanner.nextLine();
        System.out.print("Enter Branch City: ");
        String branchCity = scanner.nextLine();
        
        String query = "INSERT INTO LOAN01 (LOAN_NO, CUST_NO, LOAN_AMOUNT, BRANCH_CODE, BRANCH_NAME, BRANCH_CITY) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, loanNo);
            pstmt.setInt(2, custNo);
            pstmt.setDouble(3, loanAmount);
            pstmt.setString(4, branchCode);
            pstmt.setString(5, branchName);
            pstmt.setString(6, branchCity);
            pstmt.executeUpdate();
            System.out.println("Loan added successfully.");
        }
    }
	 
	 // Method to show customer records
    public static void showCustomerRecords(Connection conn) throws SQLException {
        String query = "SELECT * FROM CUSTOMER01";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("CUST_NO: " + rs.getInt("CUST_NO"));
                System.out.println("NAME: " + rs.getString("NAME"));
                System.out.println("PHONENO: " + rs.getString("PHONENO"));
                System.out.println("CITY: " + rs.getString("CITY"));
                System.out.println("-----------------------");
            }
        }
    }

    // Method to add a customer record
    public static void addCustomerRecord(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Customer No: ");
        int custNo = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone No: ");
        String phoneNo = scanner.nextLine();
        System.out.print("Enter City: ");
        String city = scanner.nextLine();

        String query = "INSERT INTO CUSTOMER01 (CUST_NO, NAME, PHONENO, CITY) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, custNo);
            pstmt.setString(2, name);
            pstmt.setString(3, phoneNo);
            pstmt.setString(4, city);
            pstmt.executeUpdate();
            System.out.println("Customer added successfully.");
        }
    }

    // Method to delete a customer record
    public static void deleteCustomerRecord(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Customer No to delete: ");
        int custNo = scanner.nextInt();

        String query = "DELETE FROM CUSTOMER01 WHERE CUST_NO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, custNo);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("Customer not found.");
            }
        }
    }

    // Method to update customer information
    public static void updateCustomerInformation(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Customer No to update: ");
        int custNo = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.println("1. Update Name");
        System.out.println("2. Update Phone No");
        System.out.println("3. Update City");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String query;
        String newValue;
        switch (choice) {
            case 1:
                System.out.print("Enter new Name: ");
                newValue = scanner.nextLine();
                query = "UPDATE CUSTOMER01 SET NAME = ? WHERE CUST_NO = ?";
                break;
            case 2:
                System.out.print("Enter new Phone No: ");
                newValue = scanner.nextLine();
                query = "UPDATE CUSTOMER01 SET PHONENO = ? WHERE CUST_NO = ?";
                break;
            case 3:
                System.out.print("Enter new City: ");
                newValue = scanner.nextLine();
                query = "UPDATE CUSTOMER01 SET CITY = ? WHERE CUST_NO = ?";
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
                return;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newValue);
            pstmt.setInt(2, custNo);
            pstmt.executeUpdate();
            System.out.println("Customer information updated successfully.");
        }
    }
    // Method to show account details of a customer
    public static void showAccountDetails(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Customer No: ");
        int custNo = scanner.nextInt();

        String query = "SELECT * FROM ACCOUNT01 WHERE CUST_NO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, custNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ACCOUNT_NO: " + rs.getInt("ACCOUNT_NO"));
                    System.out.println("TYPE: " + rs.getString("TYPE"));
                    System.out.println("BALANCE: " + rs.getDouble("BALANCE"));
                    System.out.println("BRANCH_CODE: " + rs.getString("BRANCH_CODE"));
                    System.out.println("BRANCH_NAME: " + rs.getString("BRANCH_NAME"));
                    System.out.println("BRANCH_CITY: " + rs.getString("BRANCH_CITY"));
                    System.out.println("-----------------------");
                }
            }
        }
    }

 // Method to show loan details of a customer
    public static void showLoanDetails(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Customer No: ");
        int custNo = scanner.nextInt();

        String query = "SELECT * FROM LOAN01 WHERE CUST_NO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, custNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("LOAN_NO: " + rs.getInt("LOAN_NO"));
                    System.out.println("LOAN_AMOUNT: " + rs.getDouble("LOAN_AMOUNT"));
                    System.out.println("BRANCH_CODE: " + rs.getString("BRANCH_CODE"));
                    System.out.println("BRANCH_NAME: " + rs.getString("BRANCH_NAME"));
                    System.out.println("BRANCH_CITY: " + rs.getString("BRANCH_CITY"));
                    System.out.println("-----------------------");
                }
            }
        }
    }

    // Method to deposit money to an account
    public static void depositMoney(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Account No: ");
        int accountNo = scanner.nextInt();
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();

        String query = "UPDATE ACCOUNT01 SET BALANCE = BALANCE + ? WHERE ACCOUNT_NO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, accountNo);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Deposit successful.");
            } else {
                System.out.println("Account not found.");
            }
        }
    }

    // Method to withdraw money from an account
    public static void withdrawMoney(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Account No: ");
        int accountNo = scanner.nextInt();
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();

        String checkBalanceQuery = "SELECT BALANCE FROM ACCOUNT01 WHERE ACCOUNT_NO = ?";
        try (PreparedStatement checkPstmt = conn.prepareStatement(checkBalanceQuery)) {
            checkPstmt.setInt(1, accountNo);
            try (ResultSet rs = checkPstmt.executeQuery()) {
                if (rs.next()) {
                    double balance = rs.getDouble("BALANCE");
                    if (balance >= amount) {
                        String query = "UPDATE ACCOUNT01 SET BALANCE = BALANCE - ? WHERE ACCOUNT_NO = ?";
                        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                            pstmt.setDouble(1, amount);
                            pstmt.setInt(2, accountNo);
                            pstmt.executeUpdate();
                            System.out.println("Withdrawal successful.");
                        }
                    } else {
                        System.out.println("Insufficient balance.");
                    }
                } else {
                    System.out.println("Account not found.");
                }
            }
        }
    }


}
