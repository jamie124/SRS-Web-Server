package Server;

import java.util.HashMap;

import com.google.gson.Gson;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QEncoderStream;

import Database.DB;
import Question.Question;
import Users.User;
import Users.UserManager;

public class QuestionManager {
	private UserManager mUserManager;
	private DB db;

	private HashMap<Integer, Question> mQuestionList;

	private int mLastQuestionAddedToList;

	public QuestionManager(UserManager prUserManager, DB prDB) {
		mQuestionList = new HashMap<Integer, Question>();
		mLastQuestionAddedToList = 0;
		mUserManager = prUserManager;
		db = prDB;
	}

	/*
	 * public boolean assignQuestionToStudent(String prUsername, String
	 * prQuestionName){
	 * 
	 * 
	 * return false; }
	 */

	public boolean addNewQuestion(Question prQuestion) {
		if (!questionList().containsKey(prQuestion.questionID()))
			questionList().put(prQuestion.questionID(), prQuestion);
		else
			return false;

		return true;

	}

	// Remove the requested question
	public boolean removeQuestion(Question prQuestion) {
		for (Question question : mQuestionList.values()) {
			if (prQuestion.questionID() == question.questionID()) {
				mQuestionList.remove(prQuestion);
				return true;
			}
		}

		return false;

	}

	// Get the largest key
	public int getLargestQuestionKey() {
		int key = 0;

		for (int keyString : mQuestionList.keySet()) {
			if (keyString > key)
				key = keyString;
		}

		return key;
	}

	// Returns a new ID to use for a question
	public int getNewQuestionID() {
		int iId = 0;

		iId = getLargestQuestionKey() + 1;
		return iId;
	}

	public boolean isListEmpty() {
		if (questionList().size() == 0)
			return true;
		else
			return false;
	}

	/*
	 * public String getLastQuestionAdded() { int i = mLastQuestionAddedToList;
	 * 
	 * String iQuestionType = ""; String iQuestionCode = "";
	 * 
	 * if (questionList().containsKey(i)) { iQuestionCode =
	 * questionList().get(i).questionType();
	 * 
	 * if (iQuestionCode.equals("MC")) { iQuestionType = "Multi-Choice"; } else
	 * if (iQuestionCode.equals("SA")) { iQuestionType = "Short Answer"; } else
	 * if (iQuestionCode.equals("TF")) { iQuestionType = "True/False"; } else if
	 * (iQuestionCode.equals("MA")) { iQuestionType = "Matching"; }
	 * 
	 * mLastQuestionAddedToList++; return questionList().get(i).questionString()
	 * + " - " + iQuestionType; } mLastQuestionAddedToList++; return ""; }
	 */

	// Check if a new question is available
	public boolean isNewQuestionAvailable() {
		if (getLargestQuestionKey() >= mLastQuestionAddedToList)
			return true;
		else
			return false;
	}

	// Get the requested question object
	public Question getQuestionByID(int prQuestionID) {
		Question iQuestion = mQuestionList.get(prQuestionID);
		return iQuestion;
	}

	/*
	 * // Returns the question id for provided question name public int
	 * getQuestionIDByString(String prQuestionString) { for (Question question :
	 * mQuestionList.values()) { if
	 * (question.questionString().equals(prQuestionString)) { return
	 * question.questionID(); } }
	 * 
	 * // Question not found return -1; }
	 * 
	 * // Checks if the question string is already in use public boolean
	 * isQuestionStringInUse(String prQuestionString) { for (Question question :
	 * mQuestionList.values()) { if
	 * (question.questionString().equals(prQuestionString)) { return true; } }
	 * return false; }
	 * 
	 * 
	 * // Get question by name public Question getQuestionByNameString(String
	 * prQuestionString) { for (Question question : mQuestionList.values()) { if
	 * (question.questionString() == prQuestionString) { return question; } } //
	 * Question not found return null; }
	 * 
	 * // Get the the requested question public String getQuestionStringByID(int
	 * prQuestionID) { if (mQuestionList.containsKey(prQuestionID)) return
	 * mQuestionList.get(prQuestionID).questionString(); else return ""; }
	 */

	public HashMap<Integer, Question> questionList() {
		return mQuestionList;
	}

	public void questionList(HashMap<Integer, Question> mQuestionList) {
		this.mQuestionList = mQuestionList;
	}

	// Returns the next question in JSON format
	public String getNextQuestion(String username) {
		// Require a valid password to prevent other students from remotely
		// logging out each other.
		Question question = db.getNextQuestion(username);
		
		if (question != null) {
			Gson gson = new Gson();
			String jsonString = gson.toJson(question);

			if (Constants.DEBUG)
				System.out.println(jsonString);
			return jsonString;
		}

		return "";
	}
}
