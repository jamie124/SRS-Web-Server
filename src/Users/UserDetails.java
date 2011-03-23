package Users;

import Question.Question;

public class UserDetails {

	private int mID;
	private String mUsername;
	private String mUserFirstName;
	private String mUserLastName;
	private String mPassword;
	private String mPrimaryDevice;
	private String mSecondaryDevice;
	private String mUserRole;
	private String mUserClass;

	// Question
	private Question mCurrQuestion;
	private String mCurrQuestionString; // Question in String format
	private String mChatMessage;

	private boolean mUserListRequested;
	private boolean mQuestionListRequested;
	private boolean mConnected;

	public UserDetails() {
		mID = 0;
		mUsername = "";
		mUserFirstName = "";
		mUserLastName = "";
		mPassword = "";
		mPrimaryDevice = "";
		mSecondaryDevice = "";
		mUserRole = "";
		mUserClass = "";

		// Question
		mCurrQuestion = null;
		mCurrQuestionString = ""; // Question in String format
		mChatMessage = "";

		mUserListRequested = false;
		mQuestionListRequested = false;
		mConnected = false;
	}

	public UserDetails(String prUserID, String prUsername, String prUserFirstName, String prUserLastName, String prPrimaryDevice,
			String prSecondaryDevice, String prUserRole, String prUserClass, String prPassword) {
		mID = Integer.parseInt(prUserID);
		mUsername = prUsername;
		mUserFirstName = prUserFirstName;
		mUserLastName = prUserLastName;
		mPassword = prPassword;
		mPrimaryDevice = prPrimaryDevice;
		mSecondaryDevice = prSecondaryDevice;
		mUserRole = prUserRole;
		mUserClass = prUserClass;

		// Question
		mCurrQuestion = null;
		mCurrQuestionString = ""; // Question in String format
		mChatMessage = "";

		mUserListRequested = false;
		mQuestionListRequested = false;
		mConnected = false;
	}

	public String userLogin() {
		return mUsername;
	}

	public void userLogin(String mUsername) {
		this.mUsername = mUsername;
	}

	public String password() {
		return mPassword;
	}

	public void userPassword(String mPassword) {
		this.mPassword = mPassword;
	}

	public String primaryDevice() {
		return mPrimaryDevice;
	}

	public void primaryDevice(String mPrimaryDevice) {
		this.mPrimaryDevice = mPrimaryDevice;
	}

	public String secondaryDevice() {
		return mSecondaryDevice;
	}

	public void secondaryDevice(String mSecondaryDevice) {
		this.mSecondaryDevice = mSecondaryDevice;
	}

	public String userRole() {
		return mUserRole;
	}

	public void userRole(String mUserRole) {
		this.mUserRole = mUserRole;
	}

	public String userClass() {
		return mUserClass;
	}

	public void userClass(String mClass) {
		this.mUserClass = mClass;
	}

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

	public int userID() {
		return mID;
	}

	public void userID(int mID) {
		this.mID = mID;
	}

	public boolean connected() {
		return mConnected;
	}

	public void connected(boolean mConnected) {
		this.mConnected = mConnected;
	}

	public String userFirstName() {
		return mUserFirstName;
	}

	public void userFirstName(String mUserFirstName) {
		this.mUserFirstName = mUserFirstName;
	}

	public String userLastName() {
		return mUserLastName;
	}

	public void userLastName(String mUserLastName) {
		this.mUserLastName = mUserLastName;
	}

}