package Database;

import java.sql.*;

import Question.Question;
import Server.Constants;
import Users.User;

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

	public void addUser(User user) {

		String sqlStmt = "INSERT INTO users (user_login, user_first_name, user_last_name, user_class, user_primary_device, user_secondary_device, user_role, user_password) "
				+ "VALUES(?,?,?,?,?,?,?,?)";

		try {
			PreparedStatement ps = conn.prepareStatement(sqlStmt);
			int index = 1;

			ps.setString(index++, user.userLogin());
			ps.setString(index++, user.userFirstName());
			ps.setString(index++, user.userLastName());
			ps.setString(index++, user.userClass());
			ps.setString(index++, user.primaryDevice());
			ps.setString(index++, user.secondaryDevice());
			ps.setString(index++, user.userRole());
			ps.setString(index++, user.password());

			ps.execute();
		} catch (SQLException ex) {
		}

		if (Constants.DEBUG)
			System.out.println("Added user: " + user.userLogin());
	}

	public void setUserLoginStatus(String username, int loginStatusFlag) {
		String sqlStmt = "UPDATE users SET last_update=NOW(), login_status=? WHERE user_login=?";

		try {
			PreparedStatement ps = conn.prepareStatement(sqlStmt);

			int index = 1;

			ps.setInt(index++, loginStatusFlag);
			ps.setString(index, username);

			ps.execute();
		} catch (SQLException ex) {
			if (Constants.DEBUG)
				System.out.println(ex.getMessage());
		}
	}

	public User getUser(String username) {
		User user = null;
		try {
			String sqlStmt = "SELECT * FROM users WHERE user_login= ?";
			PreparedStatement ps = conn.prepareStatement(sqlStmt);

			int index = 1;

			ps.setString(index, username);

			ResultSet results = ps.executeQuery();

			while (results.next()) {
				user = new User(results.getString("user_id"), results.getString("user_login"),
						results.getString("user_first_name"), results.getString("user_last_name"),
						results.getString("user_primary_device"), results.getString("user_secondary_device"),
						results.getString("user_role"), results.getString("user_class"),
						results.getString("user_password"));
			}
		} catch (SQLException ex) {
			if (Constants.DEBUG)
				System.out.println(ex.getMessage());
		}

		if (Constants.DEBUG) {
			if (user != null)
				System.out.println("Got user: " + user.userLogin());
		}
		return user;
	}

	public Question getNextQuestion(String prUsername) {
		Question nextQuestion = null;
		try {
			String sqlStmt = "SELECT u.user_login, uq.rec_id, q.question_id, q.question_title, q.question_type, q.question_answer, q.possible_answer_one, "
					+ "q.possible_answer_two, q.possible_answer_three, q.possible_answer_four "
					+ "FROM users AS u, user_question AS uq, questions AS q "
					+ "WHERE u.user_id = uq.user_id "
					+ "AND q.question_id = uq.question_id " + "AND u.user_login=? " + "ORDER BY uq.rec_id LIMIT 1";

			PreparedStatement ps = conn.prepareStatement(sqlStmt);

			int index = 1;

			ps.setString(index, prUsername);

			ResultSet results = ps.executeQuery();

			while (results.next()) {
				nextQuestion = new Question(results.getInt("question_id"), results.getString("question_title"),
						results.getString("question_type"), results.getString("possible_answer_one"),
						results.getString("possible_answer_two"), results.getString("possible_answer_three"),
						results.getString("possible_answer_four"), results.getString("question_answer"));
			}

		} catch (SQLException ex) {
			if (Constants.DEBUG)
				System.out.println(ex.getMessage());
		}
		return nextQuestion;
	}
}
