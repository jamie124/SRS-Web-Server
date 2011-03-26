package Users;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.*;

import com.google.gson.Gson;

import Database.DB;
import Question.Question;
import Server.Constants;
import Server.MessageLogger;

public class UserManager {

	private MessageLogger mMessageLogger;
	private DB db;

	// private XmlHandler mXmlHandler;

	private int mMaxUserKey;

	private HashMap<Integer, User> mUsersOnline;
	private HashMap<Integer, TutorDetails> mTutors;
	// Version of user details intended for transfer to clients
	private ArrayList<TransferrableUserDetails> mUserDetailsToTransfer;

	public UserManager(MessageLogger prMessageLogger, DB prDB) {
		this.mUsersOnline = new HashMap<Integer, User>();
		this.mMessageLogger = prMessageLogger;

		this.db = prDB;
		/*
		 * UserDetails testUser2 = new UserDetails(); testUser2.userID(2);
		 * testUser2.username("Test"); testUser2.userClass("");
		 * testUser2.deviceOS("Win7"); testUser2.userRole("Student");
		 * 
		 * addNewUser(testUser2);
		 */
	}

	public int maxUserKey() {
		return this.mMaxUserKey;
	}

	public void maxUserKey(int mMaxUserKey) {
		this.mMaxUserKey = mMaxUserKey;
	}

	public HashMap<Integer, User> usersOnline() {
		return mUsersOnline;
	}

	public void usersOnline(HashMap<Integer, User> mUsersOnline) {
		this.mUsersOnline = mUsersOnline;
	}

	public ArrayList<TransferrableUserDetails> userDetailsToTransfer() {
		return mUserDetailsToTransfer;
	}

	public void userDetailsToTransfer(ArrayList<TransferrableUserDetails> mUserDetailsToTransfer) {
		this.mUserDetailsToTransfer = mUserDetailsToTransfer;
	}

	public boolean loadTutors(String prFilename) {
		// Attempt to load settings from file
		// mTutors = mXmlHandler.LoadUserSettings(prFilename);
		if (mTutors != null) {
			return true;
		} else
			return false;
	}

	// Sets a question for each user
	public void setQuestions(Question prQuestion) {
		int i = 0;

		if (mUsersOnline.size() > 0) {
			while (i <= mMaxUserKey) {
				if (mUsersOnline.containsKey(i)) {
					// mUsersOnline.get(i).currQuestion(prQuestion);

					/*
					 * TODO: May not be needed
					 * mUsersOnline.get(i).currQuestion()
					 * .questionString(prQuestion.questionString());
					 * mUsersOnline
					 * .get(i).currQuestion().questionID(prQuestion.questionID
					 * ()); mUsersOnline[i].CurrQuestion.QuestionType =
					 * prQuestion.QuestionType;
					 * mUsersOnline[i].CurrQuestion.PossibleAnswers =
					 * prQuestion.PossibleAnswers;
					 * mUsersOnline[i].CurrQuestion.Answer = prQuestion.Answer;
					 */

				}
				i++;
			}
		}
	}

	// Get the largest key in the user hashmap
	private int getLargestUserKey() {
		int key = 0;

		for (int userKey : mUsersOnline.keySet()) {
			if (userKey > key)
				key = userKey;
		}

		return key;
	}

	// Check if the tutors list contains the provided name
	public boolean isUserATutor(String prUsername) {
		if (mTutors != null) {
			for (User userDetails : mUsersOnline.values()) {
				if (userDetails.userRole().equals("Tutor"))
					return true;
			}
		}
		return false;
	}

	// Check the password against a user
	public boolean verifyPassword(User prUser) {
		for (User userDetails : mUsersOnline.values()) {
			if (userDetails.password().equals(prUser.password()))
				return true;
		}
		return false;
	}

	// Get number of users online
	public int getNumOfUsers() {
		return usersOnline().size();
	}

	public void addNewUser(User prUser) {
		int iNewID = getLargestUserKey() + 1;

		if (mUsersOnline.size() == 0)
			mUsersOnline.put(1, prUser);
		else
			mUsersOnline.put(prUser.userID(), prUser);

		// Update max user key
		maxUserKey(iNewID);

		// sendUserListToTutors();
	}

	// Add a new tutor to the list
	public void addTutor(TutorDetails prTutor) {
		if (mTutors == null)
			mTutors = new HashMap<Integer, TutorDetails>();

		if (mTutors.size() == 0)
			mTutors.put(1, prTutor);
		else
			mTutors.put(prTutor.id(), prTutor);
	}

	// Remove a tutor from the list
	public void removeTutor(String prTutorName) {
		int iTutorID = 0;

		iTutorID = getTutorIDUsingName(prTutorName);

		if (iTutorID != -1)
			mTutors.remove(iTutorID);
	}

	// Remove a user
	public void removeUser(String prUserName) {
		int iUserID = 0;

		iUserID = getUserIDUsingName(prUserName);

		if (iUserID != -1)
			mTutors.remove(iUserID);
	}

	private int getTutorIDUsingName(String prTutorName) {
		for (TutorDetails tutor : mTutors.values()) {
			if (tutor.name().equals(prTutorName))
				return tutor.id();
		}
		return -1;
	}

	private int getUserIDUsingName(String prUserName) {
		for (User user : mUsersOnline.values()) {
			if (user.userLogin().equals(prUserName))
				return user.userID();
		}
		return -1;
	}

	// Checks if the user details are valid
	public int checkUserLoginDetails(String prUserName, String prPassword) {

		User userDetails = db.getUser(prUserName);

		if (userDetails != null) {
			if (userDetails.password().equals(prPassword)) {
				db.setUserLoginStatus(prUserName, Constants.USER_LOGGED_IN);

				return Constants.LOGIN_SUCCESSFUL;
			} else {
				return Constants.PASSWORD_INCORRECT;
			}
		} else {
			return Constants.USERNAME_INCORRECT;
		}
	}

	// Log out the user
	public int logoutUser(String prUserName, String prPassword) {

		// Require a valid password to prevent other students from remotely
		// logging out each other.
		User userDetails = db.getUser(prUserName);

		if (userDetails != null) {
			if (userDetails.password().equals(prPassword)) {
				db.setUserLoginStatus(prUserName, Constants.USER_LOGGED_OUT);

				return Constants.LOGOUT_SUCCESSFUL;
			} else {
				return Constants.PASSWORD_INCORRECT;
			}
		} else {
			return Constants.USERNAME_INCORRECT;
		}
	}

	// Returns the user details as a JSON string
	public String getUserDetails(String username, String password) {
		// Require a valid password to prevent other students from remotely
		// logging out each other.
		User user = db.getUser(username);

		if (user != null) {
			if (user.password().equals(password)) {
				Gson gson = new Gson();
				String jsonString = gson.toJson(user);

				if (Constants.DEBUG)
					System.out.println(jsonString);
				return jsonString;
			}
		}

		return "";
	}

	// Gets the tutor object for the provided name
	public TutorDetails getTutorDetails(String prTutorName) {
		for (TutorDetails tutor : mTutors.values()) {
			if (tutor.name().equals(prTutorName))
				return tutor;
		}
		return null;
	}

	// Remove a user
	// Kick all connected users from server
	public void kickUsers() {
		mUsersOnline.clear();
	}

	// Check if the user name is available
	public boolean isUsernameAvailable(String prUsername) {
		for (User user : mUsersOnline.values()) {
			if (user.userLogin().equals(prUsername))
				return false;
		}
		return true;
	}

	// Demo user to help with server testing
	public void addDemoUser() {

		// addNewUser(testUser);

	}

	public JSONObject convertUsersToJSON() {
		JSONObject usersJSON = new JSONObject();

		for (User u : mUsersOnline.values()) {
			JSONObject user = new JSONObject();

			user.put("userID", u.userID());
			user.put("userName", u.userLogin());
			user.put("userFirstName", u.userFirstName());
			user.put("userLastName", u.userLastName());
			user.put("primaryDevice", u.primaryDevice());
			user.put("secondaryDevice", u.secondaryDevice());
			user.put("primaryDevice", u.primaryDevice());
			// user.put("isConnected", u.connected());

			usersJSON.put(Integer.toString(u.userID()), user);
		}
		return usersJSON;
	}
}
