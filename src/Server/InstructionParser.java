package Server;

import Answers.AnswerManager;
import Question.Question;
import Users.UserManager;

// Parser is extremely simple and has been designed as I go along. A lot of functionality is hardcoded 
// and the method is likely not an efficient one.
public class InstructionParser {
	QuestionManager mQuestionManager;
	AnswerManager mAnswerManager;

	UserManager mUserManager;
	String[] mInstructionArray = new String[8]; // Holds all the commands,
												// currently 8 max

	public InstructionParser(IOProcessor prIOProcessor) {
		mQuestionManager = prIOProcessor.questionManager();
		mUserManager = prIOProcessor.userManager();
		mAnswerManager = prIOProcessor.answerManager();
	}

	// Start parsing the String
	// First pass looks for each command
	public void parseString(String prInstructionString) {
		Instruction iNewInstructions = new Instruction();

		// Split the String into an array
		mInstructionArray = prInstructionString.split(",");

		for (String iCommand : mInstructionArray) {
			String iUpperCaseCommand = iCommand.toUpperCase();

			if (iUpperCaseCommand.equals("SEND")) {
				iNewInstructions.isSending(true);
			} else if (iUpperCaseCommand.equals("QUESTION")) {
				iNewInstructions.isQuestion(true);
			} else if (iUpperCaseCommand.equals("ALL")) {
				iNewInstructions.isSendingToAll(true);
			} else if (iUpperCaseCommand.equals("TIMEUP")) {
				iNewInstructions.isResponseTimeUp(true);
			} else if (iUpperCaseCommand.equals("DELETE")) {
				iNewInstructions.isDeleting(true);
			} else if (iUpperCaseCommand.equals("MODIFY")) {
				iNewInstructions.isModifying(true);
			} else {
				iNewInstructions.questionName(iCommand.replace("\"", ""));
			}
		}
		//executeInstructions(iNewInstructions);
	}

	/*
	// Executes the instructions
	private void executeInstructions(Instruction prInstructions) {
		// Sending instructions
		if (prInstructions.isSending()) {
			sendingInstructions(prInstructions);
		} else if (prInstructions.isDeleting()) {
			deleteInstructions(prInstructions);
		} else if (prInstructions.isResponseTimeUp()) {
			stopReceivingResponses();
		}
	}
*/
	/*
	// Instructions related to sending stuff
	private void sendingInstructions(Instruction prInstructions) {
		Question iQuestionToSend;

		// Send a question and get ready to receive responses
		if (!prInstructions.questionName().equals("")) {
			iQuestionToSend = mQuestionManager.getQuestionByNameString(prInstructions.questionName());

			if (iQuestionToSend != null) {
				if (prInstructions.isSendingToAll()) {
					mAnswerManager.clearAnswerList(); // Clear existing answers
					mUserManager.setQuestions(iQuestionToSend); 
					mAnswerManager.receiveResponses(true); // Allow responses to be received
				}
			}
		}
	}
	*/

	/*
	// Instructions for deleting a question
	private void deleteInstructions(Instruction prInstructions) {
		Question iQuestionToDelete;

		if (!prInstructions.questionName().equals("")) {
			iQuestionToDelete = mQuestionManager.getQuestionByNameString(prInstructions.questionName());

			if (iQuestionToDelete != null) {
				mQuestionManager.removeQuestion(iQuestionToDelete);
			}
		}

		mQuestionManager.sendQuestionListToTutors();
	}
	*/
	
	// Stop the server from receiving answers from students
	private void stopReceivingResponses() {
		mAnswerManager.receiveResponses(false);
	}
}