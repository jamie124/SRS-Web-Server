package Answers;

import java.util.HashMap;


public class AnswerManager {

	private HashMap<Integer, Answer> mAnswersList = new HashMap<Integer, Answer>();

	private boolean mReceiveResponses; // Tells the server if it can process
										// responses from client

	public AnswerManager() {
		receiveResponses(true);
	}

	public void AddAnswer(Answer prAnswer) {
		int iAnswerKey;
		boolean iAnswerAdded = false;

		if (mAnswersList.size() == 0)
			mAnswersList.put(0, prAnswer);
		else {
			iAnswerKey = getLargestKey();

			while (!iAnswerAdded) {

				mAnswersList.put(iAnswerKey, prAnswer);
				iAnswerAdded = true;
			}

		}
	}

	// Get the largest key
	private int getLargestKey() {
		int key = 0;

		for (int keyString : mAnswersList.keySet()) {
			if (keyString > key)
				key = keyString;
		}

		return key;
	}

	// Clear the servers copy version of the answer list
	public void ClearAnswerList() {
		if (mAnswersList.size() > 0)
			mAnswersList.clear();
	}

	// Delete all answers for a certain question
	public void DeleteAnswersForQuestion(int prQuestionID) {
		int iLargestKey = getLargestKey();

		if (mAnswersList.size() > 0) {
			for (int i = 0; i <= iLargestKey; i++) {
				if (mAnswersList.get(i) != null) {
					if (mAnswersList.get(i).questionID() == prQuestionID) {
						mAnswersList.remove(i);
					}
				}
			}
		}
	}

	public boolean receiveResponses() {
		return mReceiveResponses;
	}

	public void receiveResponses(boolean mReceiveResponses) {
		this.mReceiveResponses = mReceiveResponses;
	}
}