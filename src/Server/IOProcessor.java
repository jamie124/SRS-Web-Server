package Server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import com.sun.java.swing.plaf.windows.WindowsBorders.DashedBorder;

import Answers.Answer;
import Answers.AnswerManager;
import Chat.ChatManager;
import Chat.ChatMessage;
import Database.DB;
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
	private DB mDB;

	// DictionarySerialiserMethods mSerialiserMethods;
	public MessageLogger messageLogger() {
		return mMessageLogger;
	}

	public void messageLogger(MessageLogger mMessageLogger) {
		this.mMessageLogger = mMessageLogger;
	}

	public QuestionManager questionManager() {
		return mQuestionManager;
	}

	public void questionManager(QuestionManager mQuestionManager) {
		this.mQuestionManager = mQuestionManager;
	}

	public UserManager userManager() {
		return mUserManager;
	}

	public void userManager(UserManager mUserManager) {
		this.mUserManager = mUserManager;
	}

	public AnswerManager answerManager() {
		return mAnswerManager;
	}

	public void answerManager(AnswerManager mAnswerManager) {
		this.mAnswerManager = mAnswerManager;
	}

	public ChatMessage chatMessage() {
		return mChatMessage;
	}

	public void chatMessage(ChatMessage mChatMessage) {
		this.mChatMessage = mChatMessage;
	}

	public ChatManager chatManager() {
		return mChatManager;
	}

	public void chatManager(ChatManager mChatManager) {
		this.mChatManager = mChatManager;
	}

	public Encryption encryption() {
		return mEncryption;
	}

	public void encryption(Encryption mEncryption) {
		this.mEncryption = mEncryption;
	}

	public DB db() {
		return mDB;
	}

	public void db(DB mDB) {
		this.mDB = mDB;
	}

	public IOProcessor(MessageLogger prMessageLogger, UserManager prUserManager, QuestionManager prQuestionManager,
			AnswerManager prAnswerManager, ChatManager prChatManager) {
		messageLogger(prMessageLogger);
		userManager(prUserManager);
		questionManager(prQuestionManager);
		answerManager(prAnswerManager);
		chatManager(prChatManager);

		// TODO: This shit is insecure
		encryption(new Encryption("P@ssword1"));
	}

	// Initialise without providing classes
	// I have a feeling this is a bad way of doing this
	public IOProcessor() {
		db(new DB());
		
		messageLogger(new MessageLogger());
		userManager(new UserManager(mMessageLogger, mDB));
		questionManager(new QuestionManager(mUserManager));
		answerManager(new AnswerManager());
		chatManager(new ChatManager());
		
		// TODO: This shit is insecure
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

	public void parseNewRequest(HttpServletRequest request, HttpServletResponse response) {
		String newRequest = request.getParameter("r");

		try {
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();

			// UserDetails iUser = mUserManager.getUserDetails(userIdString);

			if (newRequest != null) {
				if (newRequest.equals("chatMessage"))
					processChatString(newRequest);

				if (newRequest.equals("newQuestion"))
					processNewQuestion(request);

				if (newRequest.equals("newAnswer"))
					processAnswerString(request);

				if (newRequest.equals("newUser"))
					addNewUser(request);

				if (newRequest.equals("login")) {
					doLogin(request, response);
				}
			}
			// if (!chatString.equals(""))
			// processUserDetailsByString(removeFrontCharacters(prString, 2));
		} catch (IOException ex) {

		}
	}

	// Work out what the data is
	private char getDataContents(String prData) {
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
	private void processInstructionString(String prString) {
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

		iParser.parseString(iInstructions);

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
					iResult += mUserManager.usersOnline().get(q).userLogin();
					mMessageLogger.newMessage(iResult, MessageLogger.MESSAGE_COMMAND);
					iResult = "";
				}
			} else {
				mMessageLogger.newMessage("No users connected", MessageLogger.MESSAGE_COMMAND);
			}
		}
	}

	private void processChatString(String prString) {
		String iChatString = prString.replace("\0", "");

		// TODO: Reenable chat messages in JSON
		ChatMessage iNewMessageReceived = new ChatMessage();// mSerialiserMethods.ConvertStringToChatMessage(iChatString);

		mMessageLogger.chatMessageQueue().add(iNewMessageReceived);
		mMessageLogger.newMessage(iNewMessageReceived.from() + ": " + iNewMessageReceived.message(),
				MessageLogger.MESSAGE_CHAT);
	}

	// New implementation of question input using XML serialisation
	private void processNewQuestion(HttpServletRequest request) {
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
	private void processAnswerString(HttpServletRequest request) {
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
			iNewAnswer.username("TEST");
			iNewAnswer.answer(new String(iAnswerArray).replace("\0", ""));
			iNewAnswer.questionID(Integer.parseInt(new String(iQuestionIDArray).replace("\0", "")));
			iNewAnswer.answerSent(false);

			mAnswerManager.addAnswer(iNewAnswer);

			mMessageLogger.newMessage(
					"TEST" + " answered " + iNewAnswer.answer() + " for "
							+ mQuestionManager.getQuestionStringByID(iNewAnswer.questionID()),
					MessageLogger.MESSAGE_ANSWER);
		} else {
			mMessageLogger.newMessage("Response received after Time", MessageLogger.MESSAGE_SERVER);
		}
	}

	// Creates a new user from a HTTP request
	private UserDetails addNewUser(HttpServletRequest request) {

		UserDetails iNewUser = new UserDetails();

		iNewUser.userLogin(request.getParameter("userLogin"));
		iNewUser.userFirstName(request.getParameter("userFirstName"));
		iNewUser.userLastName(request.getParameter("userLastName"));
		iNewUser.primaryDevice(request.getParameter("primaryDevice"));
		iNewUser.secondaryDevice(request.getParameter("secondaryDevice"));
		iNewUser.userClass(request.getParameter("userClass"));
		iNewUser.userRole(request.getParameter("userRole"));
		iNewUser.userPassword(request.getParameter("userPassword"));
		// if (!iUserDetailsFromClient.password().equals(""))
		
		mDB.addUser(iNewUser);
		// iNewUser.password(encryption().decrypt(iUserDetailsFromClient.password()));

		// TODO add encryption
		iNewUser.userPassword(request.getParameter("userPass"));

		iNewUser.currQuestion(new Question());

		/*
		if (!iNewUser.userRole().equals("Tutor")) {
			mMessageLogger.newMessage(iNewUser.username() + " joined the server", MessageLogger.MESSAGE_STUDENT);
		} else {
			mMessageLogger.newMessage(iNewUser.username() + " joined the server", MessageLogger.MESSAGE_TUTOR);
		}
		*/
		return iNewUser;
	}

	// Login the user
	private void doLogin(HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/plain");
		try {
			PrintWriter out = response.getWriter();

			String username = request.getParameter("username");
			String password = request.getParameter("password");

			switch (mUserManager.checkUserLoginDetails(username, password)) {
			case Constants.LOGIN_FAILED:
				out.println(HttpServletResponse.SC_FORBIDDEN);
				break;
			case Constants.USERNAME_INCORRECT:
				out.println(HttpServletResponse.SC_NOT_FOUND);
				break;
			case Constants.PASSWORD_INCORRECT:
				out.println(HttpServletResponse.SC_UNAUTHORIZED);
				break;
			case Constants.LOGIN_SUCCESSFUL:
				out.println(HttpServletResponse.SC_ACCEPTED);
				break;
			}
		} catch (IOException ex) {

		}

	}

	private void respondToConnection(HttpServletResponse response, UserDetails prUser) {
		// Make sure the user name is not in use.
		if (mUserManager.isUsernameAvailable(prUser.userLogin())) {
			// Check if the user is a tutor or student
			if (mUserManager.isUserATutor(prUser.userLogin())) {
				// Check password
				if (mUserManager.verifyPassword(prUser)) {
					prUser.connected(true);
					mUserManager.addNewUser(prUser);
					mQuestionManager.sendQuestionListToTutors();

					// Send a response to client
					// sendConnectionResponse(prUser.username(),
					// "TUTORCONNECTED");
				} else {
					prUser.connected(false);
					// Send a response to client
					// sendConnectionResponse(prUser.username(),
					// "INCORRECTPASS");
				}
			} else {
				prUser.connected(true);
				mUserManager.addNewUser(prUser);
				mQuestionManager.sendQuestionListToTutors();

				// Send a response to client
				// sendConnectionResponse(prUser.username(),
				// "STUDENTCONNECTED");
			}

		} else {
			prUser.connected(false);
			// Send a response to client
			// sendConnectionResponse(prUser.username(), "USERNAMETAKEN");
		}
	}

}