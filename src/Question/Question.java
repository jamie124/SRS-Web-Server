package Question;

public class Question
{
    private int mQuestionID;
    //private String mQuestion;
    private String mQuestionString;
    private String mQuestionType;
    private String[] mPossibleAnswers;
    private String mAnswer;
    
	public int questionID() {
		return mQuestionID;
	}
	public void questionID(int mQuestionID) {
		this.mQuestionID = mQuestionID;
	}
	/*
	public void Question(String mQuestion) {
		this.mQuestion = mQuestion;
	}
	*/
	public String questionString() {
		return mQuestionString;
	}
	public void questionString(String mQuestionString) {
		this.mQuestionString = mQuestionString;
	}
	public String questionType() {
		return mQuestionType;
	}
	public void questionType(String mQuestionType) {
		this.mQuestionType = mQuestionType;
	}
	public String[] possibleAnswers() {
		return mPossibleAnswers;
	}
	public void possibleAnswers(String[] mPossibleAnswers) {
		this.mPossibleAnswers = mPossibleAnswers;
	}
	public String answer() {
		return mAnswer;
	}
	public void answer(String mAnswer) {
		this.mAnswer = mAnswer;
	}

   
}
