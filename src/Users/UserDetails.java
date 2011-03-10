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
    
	private String username() {
		return mUsername;
	}
	private void username(String mUsername) {
		this.mUsername = mUsername;
	}
	private String password() {
		return mPassword;
	}
	private void password(String mPassword) {
		this.mPassword = mPassword;
	}
	private String deviceOS() {
		return mDeviceOS;
	}
	private void deviceOS(String mDeviceOS) {
		this.mDeviceOS = mDeviceOS;
	}
	private String userRole() {
		return mUserRole;
	}
	private void userRole(String mUserRole) {
		this.mUserRole = mUserRole;
	}
	private String userClass() {
		return mUserClass;
	}
	private void userClass(String mClass) {
		this.mUserClass = mClass;
	}
	private String currQuestionString() {
		return mCurrQuestionString;
	}
	private void currQuestionString(String mCurrQuestionString) {
		this.mCurrQuestionString = mCurrQuestionString;
	}
	private String chatMessage() {
		return mChatMessage;
	}
	private void chatMessage(String mChatMessage) {
		this.mChatMessage = mChatMessage;
	}
	private boolean userListRequested() {
		return mUserListRequested;
	}
	private void userListRequested(boolean mUserListRequested) {
		this.mUserListRequested = mUserListRequested;
	}
	private boolean questionListRequested() {
		return mQuestionListRequested;
	}
	private void questionListRequested(boolean mQuestionListRequested) {
		this.mQuestionListRequested = mQuestionListRequested;
	}
	public Question CurrQuestion() {
		return mCurrQuestion;
	}
	public void CurrQuestion(Question mCurrQuestion) {
		this.mCurrQuestion = mCurrQuestion;
	}
   

}