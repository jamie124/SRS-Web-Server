package Users;

import Question.Question;

public class User {

	private int id;
	private String userName;
	private String userFirstName;
	private String userLastName;
	private String userPassword;
	private String primaryDevice;
	private String secondaryDevice;
	private String userRole;
	private String userClass;

	// Question
	//private Question mCurrQuestion;
	//private String mCurrQuestionString; // Question in String format
	//private String mChatMessage;

	//private boolean mUserListRequested;
	//private boolean mQuestionListRequested;
	//private boolean mConnected;

	public User() {
		id = 0;
		userName = "";
		userFirstName = "";
		userLastName = "";
		userPassword = "";
		primaryDevice = "";
		secondaryDevice = "";
		userRole = "";
		userClass = "";

		// Question
		/*
		mCurrQuestion = null;
		mCurrQuestionString = ""; // Question in String format
		mChatMessage = "";

		mUserListRequested = false;
		mQuestionListRequested = false;
		mConnected = false;
		*/
	}

	public User(String prUserID, String prUsername, String prUserFirstName, String prUserLastName, String prPrimaryDevice,
			String prSecondaryDevice, String prUserRole, String prUserClass, String prPassword) {
		id = Integer.parseInt(prUserID);
		userName = prUsername;
		userFirstName = prUserFirstName;
		userLastName = prUserLastName;
		userPassword = prPassword;
		primaryDevice = prPrimaryDevice;
		secondaryDevice = prSecondaryDevice;
		userRole = prUserRole;
		userClass = prUserClass;

		// Question
		/*
		mCurrQuestion = null;
		mCurrQuestionString = ""; // Question in String format
		mChatMessage = "";

		mUserListRequested = false;
		mQuestionListRequested = false;
		mConnected = false;
		*/
	}

	public String userLogin() {
		return userName;
	}

	public void userLogin(String mUsername) {
		this.userName = mUsername;
	}

	public String password() {
		return userPassword;
	}
	
	public void userPassword(String mPassword) {
		this.userPassword = mPassword;
	}

	public String primaryDevice() {
		return primaryDevice;
	}

	public void primaryDevice(String mPrimaryDevice) {
		this.primaryDevice = mPrimaryDevice;
	}

	public String secondaryDevice() {
		return secondaryDevice;
	}

	public void secondaryDevice(String mSecondaryDevice) {
		this.secondaryDevice = mSecondaryDevice;
	}

	public String userRole() {
		return userRole;
	}

	public void userRole(String mUserRole) {
		this.userRole = mUserRole;
	}

	public String userClass() {
		return userClass;
	}

	public void userClass(String mClass) {
		this.userClass = mClass;
	}

	/*
	public String currQuestionString() {
		return mCurrQuestionString;
	}

	public void currQuestionString(String mCurrQuestionString) {
		this.mCurrQuestionString = mCurrQuestionString;
	}

	public String chatMessage() {
		return mChatMessage;
	}

	public void chatMessage(String mChatMessage) {
		this.mChatMessage = mChatMessage;
	}

	public boolean userListRequested() {
		return mUserListRequested;
	}

	public void userListRequested(boolean mUserListRequested) {
		this.mUserListRequested = mUserListRequested;
	}

	public boolean questionListRequested() {
		return mQuestionListRequested;
	}

	public void questionListRequested(boolean mQuestionListRequested) {
		this.mQuestionListRequested = mQuestionListRequested;
	}

	public Question currQuestion() {
		return mCurrQuestion;
	}

	public void currQuestion(Question mCurrQuestion) {
		this.mCurrQuestion = mCurrQuestion;
	}
	*/
	
	public int userID() {
		return id;
	}

	public void userID(int mID) {
		this.id = mID;
	}

	/*
	public boolean connected() {
		return mConnected;
	}

	public void connected(boolean mConnected) {
		this.mConnected = mConnected;
	}
	*/

	public String userFirstName() {
		return userFirstName;
	}

	public void userFirstName(String mUserFirstName) {
		this.userFirstName = mUserFirstName;
	}

	public String userLastName() {
		return userLastName;
	}

	public void userLastName(String mUserLastName) {
		this.userLastName = mUserLastName;
	}

}