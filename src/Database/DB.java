package Database;

import java.sql.*;

import Server.Constants;
import Users.UserDetails;

public class DB {
	Connection conn = null;

	// Constants
	public static final int DB_CONNECTED = 0;
	public static final int DB_CONNECT_ERROR = -1;
	public static final int DB_ALREADY_CONNECTED = 1;

	public DB() {
		openDBConnection();
	}

	public int openDBConnection() {
		try {
			if (conn == null) {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/srsdb", "root", "Whitwell124");

				if (!conn.isClosed())
					System.out.println("Successfully connected to " + "MySQL server using TCP/IP...");
				return DB_CONNECTED;
			} else {
				return DB_ALREADY_CONNECTED;
			}

		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			return DB_CONNECT_ERROR;
		}
	}

	public void closeDBCOnnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Execute a SQL statement
	public Statement doExecuteStatement(String prStatement) {
		Statement st = null;
		try {
			if (!conn.isClosed()) {
				st = conn.createStatement();
				boolean val = st.execute(prStatement);

			} else {
				openDBConnection();
				doExecuteStatement(prStatement);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return st;
	}

	public void doUpdateStatement(String prStatement) {
		Statement st = null;
		try {
			if (!conn.isClosed()) {
				st = conn.createStatement();
				int val = st.executeUpdate(prStatement);

			} else {
				openDBConnection();
				doUpdateStatement(prStatement);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addUser(UserDetails user) {

		String sqlStmt = "INSERT INTO users (user_login, user_first_name, user_last_name, user_class, user_primary_device, user_secondary_device, user_role, user_password) "
				+ "VALUES('"
				+ user.userLogin()
				+ "','"
				+ user.userFirstName()
				+ "','"
				+ user.userLastName()
				+ "','"
				+ user.userClass()
				+ "','"
				+ user.primaryDevice()
				+ "','"
				+ user.secondaryDevice()
				+ "','"
				+ user.userRole() + "','" + user.password() + "')";

		doUpdateStatement(sqlStmt);

		if (Constants.DEBUG)
			System.out.println("Added user: " + user.userLogin());
	}

	public UserDetails getUser(String username) {
		String sqlStmt = "SELECT * FROM users WHERE user_login='" + username + "'";

		ResultSet results = null;
		Statement st = doExecuteStatement(sqlStmt);
		UserDetails user = null;

		if (st != null) {
			try {
				results = st.getResultSet();

				while (results.next()) {
					user = new UserDetails(results.getString("user_id"), results.getString("user_login"),
							results.getString("user_first_name"), results.getString("user_last_name"),
							results.getString("user_primary_device"), results.getString("user_secondary_device"),
							results.getString("user_role"), results.getString("user_class"),
							results.getString("user_password"));
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (Constants.DEBUG) {
			if (user != null)
				System.out.println("Got user: " + user.userLogin());
		}
		return user;
	}
}
