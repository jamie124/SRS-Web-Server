package Answers;

//Currently the answer is only tied to the user by the username
// further developments may require more data.
public class Answer
{
    private int mQuestionID;
    private String mAnswer;
    private String mUsername;
    private boolean mAnswerSent;   // Has the answer been sent to the tutor?
    
	public int questionID() {
		return mQuestionID;
	}
	public void questionID(int mQuestionID) {
		this.mQuestionID = mQuestionID;
	}
	public String answer() {
		return mAnswer;
	}
	public void answer(String mAnswer) {
		this.mAnswer = mAnswer;
	}
	public String username() {
		return mUsername;
	}
	public void username(String mUsername) {
		this.mUsername = mUsername;
	}
	public boolean answerSent() {
		return mAnswerSent;
	}
	public void answerSent(boolean mAnswerSent) {
		this.mAnswerSent = mAnswerSent;
	}

}