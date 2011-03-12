package Server;

import java.util.HashMap;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QEncoderStream;

import Question.Question;
import Users.UserManager;

public class QuestionManager {
	UserManager mUserManger;

	private HashMap<Integer, Question> mQuestionList;

	private int mLastQuestionAddedToList;

	public QuestionManager(UserManager prUserManager) {
		mQuestionList = new HashMap<Integer, Question>();
		mLastQuestionAddedToList = 0;
		mUserManger = prUserManager;
	}

	// Send question data to tutors
	public void SendQuestionListToTutors() {
		int i = 0;
		if (mUserManger.UsersOnline.Count > 0) {
			while (i <= mUserManger.MaxUserKey) {
				// Only send the list to a tutor
				if (mUserManger.UsersOnline.ContainsKey(i)) {
					if (mUserManger.UsersOnline[i].UserRole == "Tutor") {
						mUserManger.UsersOnline[i].QuestionListRequested = true;
					}
				}
				i++;
			}
		}
	}

	// Insert the question number into the string
	public String insertQuestionNumber(String prQuestionString, int prQuestionNum) {
		return prQuestionString .Insert(0, prQuestionNum.ToString() + "|");
	}

	public bool AddNewQuestion(question prQuestion) {
		if (!questionList().ContainsValue(prQuestion)) {
			if (!questionList().ContainsKey(prQuestion.QuestionID))
				questionList().Add(prQuestion.QuestionID, prQuestion);
			else
				questionList().Add(questionList().Keys.Last() + 1, prQuestion);
			SendQuestionListToTutors();
			return true;
		} else {
			return false;
		}
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

	// Gets the largest key in question dictionary
	private int GetLargestKeyQuestionDict()
    {
        int iCurrentLargestKey = 0;
        foreach (KeyValuePair<int, question> iQuestion in mQuestionList)
        {
            if (iQuestion.Key > iCurrentLargestKey)
            {
                iCurrentLargestKey = iQuestion.Key;
            }
        }
        return iCurrentLargestKey;
    }

	public int GetNewQuestionID() {
		int iID = 0;

		iID = questionList()[questionList().Last().Key].QuestionID + 1;
		return iID;
	}

	public bool IsListEmpty() {
		if (questionList().Count == 0)
			return true;
		else
			return false;
	}

	public string GetLastQuestionAdded() {
		int i = mLastQuestionAddedToList;

		string iQuestionType = "";

		if (questionList().ContainsKey(i)) {
			switch (questionList()[i].QuestionType) {
			case "MC":
				iQuestionType = "Multi-Choice";
				break;
			case "SA":
				iQuestionType = "Short Answer";
				break;
			case "TF":
				iQuestionType = "True/False";
				break;
			case "MA":
				iQuestionType = "Matching";
				break;
			}

			mLastQuestionAddedToList++;
			return questionList()[i].Question + " - " + iQuestionType;
		}
		mLastQuestionAddedToList++;
		return "";
	}

	// Check if a new question is available
	public bool IsNewQuestionAvailable() {
		if (GetLargestKeyQuestionDict() >= mLastQuestionAddedToList)
			return true;
		else
			return false;
	}

	// Get the requested question object
	public question GetQuestionByID(int prQuestionID) {
		question iQuestion = questionList()[prQuestionID];
		return iQuestion;
	}

	// Returns the question id for provided questionname
	public int GetQuestionIDByString(string prQuestionName)
    {
        foreach (KeyValuePair<int, question> iQuestion in mQuestionList)
        {
            if (iQuestion.Value.Question == prQuestionName)
            {
                return iQuestion.Value.QuestionID;
            }
        }
        // Question not found
        return -1;
    }

	// Checks if the question is already in use
	public bool IsQuestionNameInUse(string prQuestionName)
    {
        foreach (KeyValuePair<int, question> iQuestion in mQuestionList)
        {
            if (iQuestion.Value.Question == prQuestionName)
                return true;
        }
        return false;
    }

	// Get question by name
	public question GetQuestionByName(string prQuestionName)
    {
        foreach (KeyValuePair<int, question> iQuestion in mQuestionList)
        {
            if (iQuestion.Value.Question == prQuestionName)
            {
                return iQuestion.Value;
            }
        }
        // Question not found
        return null;
    }

	// Get the the requested question
	public string GetQuestionStringByID(int prQuestionID) {
		return questionList()[prQuestionID].Question;
	}

	// Serialisation function.
	public void GetObjectData(SerializationInfo info, StreamingContext ctxt) {

		info.AddValue("UserList", questionList());
	}

	public HashMap<String, Question> questionList() {
		return mQuestionList;
	}

	public void questionList(HashMap<String, Question> mQuestionList) {
		this.mQuestionList = mQuestionList;
	}
}
