package Users;

import Question.Question;

public class UserDetails
{

    private String mUsername;
    private String mPassword;
    private String mDeviceOS;
    private String mUserRole;
    private String mUserClass;

    // Question
    private Question mCurrQuestion;
    private String mCurrQuestionString;     // Question in String format
    private String mChatMessage;

    private boolean mUserListRequested;
    private boolean mQuestionListRequested;
    private boolean mConnected;
    
    public String username() {
		return mUsername;
	}
    public void username(String mUsername) {
		this.mUsername = mUsername;
	}
    public String password() {
		return mPassword;
	}
    public void password(String mPassword) {
		this.mPassword = mPassword;
	}
    public String deviceOS() {
		return mDeviceOS;
	}
    public void deviceOS(String mDeviceOS) {
		this.mDeviceOS = mDeviceOS;
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
   

}