package Server;

import javax.crypto.SecretKey;

import Answers.Answer;
import Answers.AnswerManager;
import Chat.ChatManager;
import Chat.ChatMessage;
import Question.Question;
import Users.TransferrableUserDetails;
import Users.UserManager;
import Users.UserDetails;

public class IOProcessor {
	private MessageLogger mMessageLogger;
	private QuestionManager mQuestionManager;
	private UserManager mUserManager;
	private AnswerManager mAnswerManager;
	private ChatMessage mChatMessage;
	private ChatManager mChatManager;
	private Encryption mEncryption;

	// DictionarySerialiserMethods mSerialiserMethods;
	MessageLogger messageLogger() {
		return mMessageLogger;
	}

	void messageLogger(MessageLogger mMessageLogger) {
		this.mMessageLogger = mMessageLogger;
	}

	QuestionManager questionManager() {
		return mQuestionManager;
	}

	void questionManager(QuestionManager mQuestionManager) {
		this.mQuestionManager = mQuestionManager;
	}

	UserManager userManager() {
		return mUserManager;
	}

	void userManager(UserManager mUserManager) {
		this.mUserManager = mUserManager;
	}

	AnswerManager answerManager() {
		return mAnswerManager;
	}

	void answerManager(AnswerManager mAnswerManager) {
		this.mAnswerManager = mAnswerManager;
	}

	ChatMessage chatMessage() {
		return mChatMessage;
	}

	void chatMessage(ChatMessage mChatMessage) {
		this.mChatMessage = mChatMessage;
	}

	ChatManager chatManager() {
		return mChatManager;
	}

	void chatManager(ChatManager mChatManager) {
		this.mChatManager = mChatManager;
	}

	Encryption encryption() {
		return mEncryption;
	}

	void encryption(Encryption mEncryption) {
		this.mEncryption = mEncryption;
	}

	public IOProcessor(MessageLogger prMessageLogger, UserManager prUserManager, QuestionManager prQuestionManager, AnswerManager prAnswerManager,
			ChatManager prChatManager) {
		messageLogger(prMessageLogger);
		userManager(prUserManager);
		questionManager(prQuestionManager);
		answerManager(prAnswerManager);
		chatManager(prChatManager);

		// TODO: Move the password to a more secure location
		encryption(new Encryption("P@ssword1"));
	}

	public String removeFrontCharacters(String prString, int prNumToRemove) {
		int iCount = 0;
		int iStringLength = prString.length();
		char[] iStringArray = new char[300];
		// String iProcessedString;
		char[] charString;

		charString = prString.toCharArray();
		for (int i = 0; i < iStringLength; i++) {
			if (i >= prNumToRemove) {
				if (charString[i] != ';') {
					iStringArray[iCount] = charString[i];
					iCount++;
				} else {
					// iProcessedString = new String(iStringArray);
					iStringArray[iCount] = charString[i];
					return new String(iStringArray);
				}
			}
		}
		return new String(iStringArray);
	}

	public void parseNewString(String prString, UserDetails prUser) {
		char iContents = GetDataContents(prString);

		switch (iContents) {
		case 'i':
			processInstructionString(removeFrontCharacters(prString, 2), prUser);
			break;
		case 'c':
			processChatString(prString, prUser);
			break;
		//case 'Q': // Old question parsing, to be removed
			//processQuestionString(removeFrontCharacters(prString, 2), prUser);
			//break;
		case 'q':
			processNewQuestion(prString);
			break;
		case 'A':
			processAnswerString(removeFrontCharacters(prString, 2), prUser);
			break;
		case 'u':
			processUserDetails(prString, prUser);
			break;
		case 'U':
			processUserDetailsByString(removeFrontCharacters(prString, 2), prUser);
			break;
		}
	}

	// Work out what the data is
	private char GetDataContents(String prData) {
		// String iDataString = new
		// String(Encoding.ASCII.GetChars(prData)).Replace("\0", "");
		char[] iDataTypeArray = new char[2];
		String iDataType;
		char[] dataArray = prData.toCharArray();

		// Loop through the first 2 chars
		for (int i = 0; i < 2; i++) {
			iDataTypeArray[i] = dataArray[i];
		}

		iDataType = new String(iDataTypeArray);

		// XML
		if (iDataType == "<?") {
			if (prData.matches("transferrableUserDetails")) {
				return 'u';
			} else if (prData.matches("question")) {
				return 'q';
			} else if (prData.matches("ChatMessage")) {
				return 'c';
			} else {
				return '?';
			}
		}
		// String based input
		else {
			String iFlag = iDataTypeArray.toString();
			if (iFlag.equals("I;")) {
				return 'i';
			} else if (iFlag.equals("Q;")) {
				return 'Q';
			} else if (iFlag.equals("A;")) {
				return 'A';
			} else if (iFlag.equals("U;")) {
				return 'U';
			}
		}
		return '?';
	}

	// Interprets instructions sent from command line
	private void processInstructionString(String prString, UserDetails prUser) {
		InstructionParser iParser = new InstructionParser(this);

		String iInstructions;
		String iResult = "";
		int iLength = prString.length();

		char[] iInstructionStringArray = prString.toCharArray();

		char[] iStringArray = new char[iLength];

		for (int i = 0; i < iLength; i++) {
			if (iInstructionStringArray[i] != ';') {
				iStringArray[i] = iInstructionStringArray[i];
			}
		}

		iInstructions = new String(iStringArray).replace("\0", "");

		iParser.ParseString(iInstructions);

		// TODO: Proper parsing.
		// List all questions
		if (iInstructions == "list questions" || iInstructions == "list q") {
			mMessageLogger.newMessage("Questions:", MessageLogger.MESSAGE_SERVER);

			if (mQuestionManager.questionList().size() > 0) {
				for (int q = 1; q <= mQuestionManager.questionList().size(); q++) {
					iResult += mQuestionManager.questionList().get(q).questionString();
					mMessageLogger.newMessage(iResult, MessageLogger.MESSAGE_COMMAND);
					iResult = "";
				}
			}
		}
		// List all users
		if (iInstructions == "list users" || iInstructions == "list u") {
			if (mUserManager.usersOnline().size() > 0) {
				mMessageLogger.newMessage("Connected Users:", MessageLogger.MESSAGE_COMMAND);

				for (int q = 1; q <= mUserManager.usersOnline().size(); q++) {
					iResult += mUserManager.usersOnline().get(q).username();
					mMessageLogger.newMessage(iResult, MessageLogger.MESSAGE_COMMAND);
					iResult = "";
				}
			} else {
				mMessageLogger.newMessage("No users connected", MessageLogger.MESSAGE_COMMAND);
			}
		}
	}

	private void processChatString(String prString, UserDetails prUser) {
		String iChatString = prString.replace("\0", "");

		// TODO: Reenable chat messages in JSON
		ChatMessage iNewMessageReceived = new ChatMessage();// mSerialiserMethods.ConvertStringToChatMessage(iChatString);

		mMessageLogger.chatMessageQueue().add(iNewMessageReceived);
		mMessageLogger.newMessage(iNewMessageReceived.from() + ": " + iNewMessageReceived.message(), MessageLogger.MESSAGE_CHAT);
	}

	// New implementation of question input using XML serialisation
	private void processNewQuestion(String prQuestionArray) {
		Question iNewQuestion = new Question();// mSerialiserMethods.ConvertStringToQuestion(prQuestionArray);

		// Set the question ID
		if (mQuestionManager.questionList().size() > 0)
			iNewQuestion.questionID(mQuestionManager.getLargestQuestionKey() + 1);
		else
			iNewQuestion.questionID(0);

		// Determine whether the question is being added or modified
		// TODO: Implement a better system to do this
		if (mQuestionManager.isQuestionStringInUse(iNewQuestion.questionString())) {
			// Remove the old question and add the new one
			mQuestionManager.removeQuestion(iNewQuestion);
			mQuestionManager.addNewQuestion(iNewQuestion);
		}
		mQuestionManager.addNewQuestion(iNewQuestion);
		mMessageLogger.newMessage(iNewQuestion.questionString() + " added", MessageLogger.MESSAGE_QUESTION);

	}

	// TODO: Add JSON parsing for answers
	private void processAnswerString(String prString, UserDetails prUser) {
		char[] iAnswerArray = new char[150]; // The max char size for all
												// answers is currently 150
		char[] iQuestionIDArray = new char[5]; // allow for up to 99999 ID's
		boolean iQuestionIDFound = false;
		boolean iAnswerFound = false;
		int iCurrPos = 0;
		Answer iNewAnswer = new Answer();

		if (mAnswerManager.receiveResponses()) {
			/*
			 * for (int i = 0; i < prString.length(); i++) { if (prString[i] !=
			 * ';') { if (!iQuestionIDFound) { if (prString[i] != '|') {
			 * iQuestionIDArray[iCurrPos] = prString[i]; iCurrPos++; } else {
			 * iQuestionIDFound = true; iCurrPos = 0; } } else { if
			 * (!iAnswerFound) { if (prString[i] != '|') {
			 * iAnswerArray[iCurrPos] = prString[i]; iCurrPos++; } else {
			 * iAnswerFound = true; } } } } else { break; } }
			 */
			iNewAnswer.username(prUser.username());
			iNewAnswer.answer(new String(iAnswerArray).replace("\0", ""));
			iNewAnswer.questionID(Integer.parseInt(new String(iQuestionIDArray).replace("\0", "")));
			iNewAnswer.answerSent(false);

			mAnswerManager.addAnswer(iNewAnswer);

			mMessageLogger.newMessage(
					prUser.username() + " answered " + iNewAnswer.answer() + " for "
							+ mQuestionManager.getQuestionStringByID(iNewAnswer.questionID()), MessageLogger.MESSAGE_ANSWER);
		} else {
			mMessageLogger.newMessage("Response received after Time", MessageLogger.MESSAGE_SERVER);
		}
	}

	// Old String based user String
	private void processUserDetailsByString(String prString, UserDetails prUser) {
		int iPos = 0;
		int iCurrPos = 0;
		int iSection = 0;

		char[] iUsernameArray, iDeviceOSArray, iUserRoleArray;

		iUsernameArray = new char[40];
		iDeviceOSArray = new char[15];
		iUserRoleArray = new char[8];

		/*
		 * // Changed to allow for easier addition of information for (int i =
		 * 0; i < prString.length(); i++) { if (prString[i] != ';') { switch
		 * (iSection) { case 0: // User Role if (prString[i] != '|') {
		 * iUserRoleArray[iCurrPos] = prString[i]; iCurrPos++; } else { iCurrPos
		 * = 0; iSection++; } break; case 1: // Username if (prString[i] != '|')
		 * { iUsernameArray[iCurrPos] = prString[i]; iCurrPos++; } else {
		 * iSection++; iCurrPos = 0; } break; case 2: // Device OS if
		 * (prString[i] != '|') { iDeviceOSArray[iCurrPos] = prString[i];
		 * iCurrPos++; } else { iSection++; iCurrPos = 0; } break; } } else {
		 * break; } }
		 */
		prUser.userRole(new String(iUserRoleArray).replace("\0", ""));
		prUser.deviceOS(new String(iDeviceOSArray).replace("\0", ""));
		prUser.username(new String(iUsernameArray).replace("\0", ""));
		prUser.currQuestion(new Question());
		if (!prUser.userRole().equals("Tutor")) {
			mMessageLogger.newMessage(prUser.username() + " joined the server", MessageLogger.MESSAGE_STUDENT);
		} else {
			mMessageLogger.newMessage(prUser.username() + " joined the server", MessageLogger.MESSAGE_TUTOR);
		}

		// mUserManager.AddNewUser(prUser);
		// mQuestionManager.SendQuestionListToTutors();

		respondToConnection(prUser);
	}

	// New XML serialisation method for processing user details
	// TODO: Convert to JSON
	private void processUserDetails(String prString, UserDetails prUser) {
		TransferrableUserDetails iUserDetailsFromClient = new TransferrableUserDetails();// mSerialiserMethods.ConvertStringToUserDetails(prString);

		prUser.userRole(iUserDetailsFromClient.userRole());
		prUser.deviceOS(iUserDetailsFromClient.deviceOS());
		prUser.username(iUserDetailsFromClient.username());
		if (!iUserDetailsFromClient.password().equals(""))
			prUser.password(encryption().decrypt(iUserDetailsFromClient.password()));

		prUser.currQuestion(new Question());

		if (!prUser.userRole().equals("Tutor")) {
			mMessageLogger.newMessage(prUser.username() + " joined the server", MessageLogger.MESSAGE_STUDENT);
		} else {
			mMessageLogger.newMessage(prUser.username() + " joined the server", MessageLogger.MESSAGE_TUTOR);
		}

		respondToConnection(prUser);
	}

	private void respondToConnection(UserDetails prUser) {
		// Make sure the user name is not in use.
		if (mUserManager.isUsernameAvailable(prUser.username())) {
			// Check if the user is a tutor or student
			if (mUserManager.isUserATutor(prUser.username())) {
				// Check password
				if (mUserManager.verifyPassword(prUser)) {
					prUser.connected(true);
					mUserManager.addNewUser(prUser);
					mQuestionManager.sendQuestionListToTutors();

					// Send a response to client
					sendConnectionResponse(prUser.username(), "TUTORCONNECTED");
				} else {
					prUser.connected(false);
					// Send a response to client
					sendConnectionResponse(prUser.username(), "INCORRECTPASS");
				}
			} else {
				prUser.connected(true);
				mUserManager.addNewUser(prUser);
				mQuestionManager.sendQuestionListToTutors();

				// Send a response to client
				sendConnectionResponse(prUser.username(), "STUDENTCONNECTED");
			}

		} else {
			prUser.connected(false);
			// Send a response to client
			sendConnectionResponse(prUser.username(), "USERNAMETAKEN");
		}
	}

	// Send connection response to client
	private void sendConnectionResponse(String userName, String prConnectionMessage) {
		// TODO Add stuff here
	}
}
