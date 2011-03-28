package Question;

public class Question
{
    private int questionID;
    //private String mQuestion;
    private String questionTitle;
    private String questionType;
    private String possibleAnswerOne;
    private String possibleAnswerTwo;
    private String possibleAnswerThree;
    private String possibleAnswerFour;
    private String answer;
    
	public int questionID() {
		return questionID;
	}
	public void questionID(int questionID) {
		this.questionID = questionID;
	}
	
	public Question(int questionID, String questionTitle, String questionType, String possibleAnswerOne, String possibleAnswerTwo, String possibleAnswerThree,
			String possibleAnswerFour, String answer) {
		this.questionID = questionID;
		this.questionTitle = questionTitle;
		this.questionType = questionType;
		this.possibleAnswerOne = possibleAnswerOne;
		this.possibleAnswerTwo = possibleAnswerTwo;
		this.possibleAnswerThree = possibleAnswerThree;
		this.possibleAnswerFour = possibleAnswerFour;
		this.answer = answer;
	}
	
	public String questionTitle() {
		return questionTitle;
	}
	public void questionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	public String questionType() {
		return questionType;
	}
	public void questionType(String questionType) {
		this.questionType = questionType;
	}
	public String possibleAnswerOne() {
		return possibleAnswerOne;
	}
	public void possibleAnswerOne(String possibleAnswerOne) {
		this.possibleAnswerOne = possibleAnswerOne;
	}
	public String answer() {
		return answer;
	}
	public void answer(String answer) {
		this.answer = answer;
	}
	public String possibleAnswerTwo() {
		return possibleAnswerTwo;
	}
	public void possibleAnswerTwo(String possibleAnswerTwo) {
		this.possibleAnswerTwo = possibleAnswerTwo;
	}
	public String possibleAnswerThree() {
		return possibleAnswerThree;
	}
	public void possibleAnswerThree(String possibleAnswerThree) {
		this.possibleAnswerThree = possibleAnswerThree;
	}
	public String possibleAnswerFour() {
		return possibleAnswerFour;
	}
	public void possibleAnswerFour(String possibleAnswerFour) {
		this.possibleAnswerFour = possibleAnswerFour;
	}

   
}
