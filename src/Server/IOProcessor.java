package Server;

import Chat.ChatManager;
import Chat.ChatMessage;
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

	public IOProcessor(MessageLogger prMessageLogger,
			UserManager prUserManager, QuestionManager prQuestionManager,
			AnswerManager prAnswerManager, ChatManager prChatManager) {
		messageLogger(prMessageLogger);
		userManager(prUserManager);
		questionManager(prQuestionManager);
		answerManager(prAnswerManager);
		chatManager(prChatManager);
		encryption(new Encryption());
		// mSerialiserMethods = new DictionarySerialiserMethods();
	}

	public String RemoveFrontCharacters(String prString, int prNumToRemove) {
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

	public void ParseNewString(String prString, UserDetails prUser) {
		char iContents = GetDataContents(prString);

		switch (iContents) {
		case 'i':
			ProcessInstructionString(RemoveFrontCharacters(prString, 2), prUser);
			break;
		case 'c':
			ProcessChatString(prString, prUser);
			break;
		case 'Q': // Old question parsing, to be removed
			ProcessQuestionString(RemoveFrontCharacters(prString, 2), prUser);
			break;
		case 'q':
			ProcessNewQuestion(prString);
			break;
		case 'A':
			ProcessAnswerString(RemoveFrontCharacters(prString, 2), prUser);
			break;
		case 'u':
			ProcessUserDetails(prString, prUser);
			break;
		case 'U':
			ProcessUserDetailsByString(RemoveFrontCharacters(prString, 2),
					prUser);
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
	private void ProcessInstructionString(String prString, UserDetails prUser) {
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
			mMessageLogger.NewMessage("Questions:",
					MessageLogger.MESSAGE_SERVER);

			if (mQuestionManager.QuestionList.Count > 0) {
				for (int q = 1; q <= mQuestionManager.QuestionList.Count; q++) {
					iResult += mQuestionManager.QuestionList[q].Question;
					mMessageLogger.NewMessage(iResult,
							mMessageLogger.MESSAGE_COMMAND);
					iResult = "";
				}
			}
		}
		// List all users
		if (iInstructions == "list users" || iInstructions == "list u") {
			if (mUserManager.UsersOnline.Count > 0) {
				mMessageLogger.NewMessage("Connected Users:",
						mMessageLogger.MESSAGE_COMMAND);

				for (int q = 1; q <= mUserManager.UsersOnline.Count; q++) {
					iResult += mUserManager.UsersOnline[q].Username;
					mMessageLogger.NewMessage(iResult,
							mMessageLogger.MESSAGE_COMMAND);
					iResult = "";
				}
			} else {
				mMessageLogger.NewMessage("No users connected",
						mMessageLogger.MESSAGE_COMMAND);
			}
		}
	}

	private void ProcessChatString(String prString, UserDetails prUser) {
		String iChatString = prString.replace("\0", "");
		ChatMessage iNewMessageReceived = mSerialiserMethods
				.ConvertStringToChatMessage(iChatString);

		mMessageLogger.chatMessageQueue().add(iNewMessageReceived);
		mMessageLogger.NewMessage(
				iNewMessageReceived.from() + ": " + iNewMessageReceived.message(),
				MessageLogger.MESSAGE_CHAT);
	}

	// Old question parsing, to be removed
	private void ProcessQuestionString(String prString, userDetails prUser) {
		boolean iQuestionFound = false;
		boolean iQuestionTypeFound = false;
		char[] iQuestionString = new char[50];
		char[] iQuestionType = new char[2];
		int iCurrPos = 0;

		question iNewQuestion = new question();

		for (int i = 0; i < prString.Length; i++) {
			if (prString[i] != ';') {
				if (!iQuestionTypeFound) {
					if (prString[i] != '|') {
						iQuestionType[iCurrPos] = prString[i];
						iCurrPos++;
					} else {
						iQuestionTypeFound = true;
						iCurrPos = 0;
					}
				} else {
					if (!iQuestionFound) {
						if (prString[i] != '|') {
							iQuestionString[iCurrPos] = prString[i];
							iCurrPos++;
						} else {
							iQuestionFound = true;
						}
					}
				}
			} else {
				break;
			}
		}

		if (QuestionManager().IsListEmpty()) {
			iNewQuestion.QuestionID = 1;
		} else {
			iNewQuestion.QuestionID = QuestionManager().GetNewQuestionID();
		}

		iNewQuestion.Question = new String(iQuestionString).Replace("\0", "");
		iNewQuestion.QuestionType = new String(iQuestionType);
		iNewQuestion.QuestionString = "Q;"
				+ QuestionManager().InsertQuestionNumber(
						prString.Replace("\0", ""), 1);

		QuestionManager().AddNewQuestion(iNewQuestion);
		mMessageLogger.NewMessage(iNewQuestion.Question + " added",
				mMessageLogger.MESSAGE_QUESTION);
	}

	// New implementation of question input using XML serialisation
	private void ProcessNewQuestion(String prQuestionArray) {
		question iNewQuestion = mSerialiserMethods
				.ConvertStringToQuestion(prQuestionArray);

		// Set the question ID
		if (QuestionManager().QuestionList.Count > 0)
			iNewQuestion.QuestionID = QuestionManager().QuestionList.Last().Key + 1;
		else
			iNewQuestion.QuestionID = 0;

		// Determine whether the question is being added or modified
		// TODO: Implement a better system to do this
		if (QuestionManager().IsQuestionNameInUse(iNewQuestion.Question)) {
			// Remove the old question and add the new one
			QuestionManager().RemoveQuestion(iNewQuestion.Question);
			QuestionManager().AddNewQuestion(iNewQuestion);
		}
		QuestionManager().AddNewQuestion(iNewQuestion);
		mMessageLogger.NewMessage(iNewQuestion.Question + " added",
				mMessageLogger.MESSAGE_QUESTION);

	}

	private void ProcessAnswerString(String prString, UserDetails prUser) {
		char[] iAnswerArray = new char[150]; // The max char size for all
												// answers is currently 150
		char[] iQuestionIDArray = new char[5]; // allow for up to 99999 ID's
		boolean iQuestionIDFound = false;
		boolean iAnswerFound = false;
		int iCurrPos = 0;
		Answer iNewAnswer = new Answer();

		if (AnswerManager().ReceiveResponses) {
			for (int i = 0; i < prString.Length; i++) {
				if (prString[i] != ';') {
					if (!iQuestionIDFound) {
						if (prString[i] != '|') {
							iQuestionIDArray[iCurrPos] = prString[i];
							iCurrPos++;
						} else {
							iQuestionIDFound = true;
							iCurrPos = 0;
						}
					} else {
						if (!iAnswerFound) {
							if (prString[i] != '|') {
								iAnswerArray[iCurrPos] = prString[i];
								iCurrPos++;
							} else {
								iAnswerFound = true;
							}
						}
					}
				} else {
					break;
				}
			}
			iNewAnswer.Username = prUser.Username;
			iNewAnswer.AnswerString = new String(iAnswerArray)
					.Replace("\0", "");
			iNewAnswer.QuestionID = Convert
					.ToInt32(new String(iQuestionIDArray).Replace("\0", ""));
			iNewAnswer.AnswerSent = false;

			AnswerManager().AddAnswer(iNewAnswer);

			mMessageLogger.NewMessage(
					prUser.Username
							+ " answered "
							+ iNewAnswer.AnswerString
							+ " for "
							+ QuestionManager().GetQuestionStringByID(
									iNewAnswer.QuestionID),
					mMessageLogger.MESSAGE_ANSWER);
		} else {
			mMessageLogger.NewMessage("Response received after Time",
					mMessageLogger.MESSAGE_SERVER);
		}
	}

	// Old String based user String
	private void ProcessUserDetailsByString(String prString, userDetails prUser) {
		int iPos = 0;
		int iCurrPos = 0;
		int iSection = 0;

		char[] iUsernameArray, iDeviceOSArray, iUserRoleArray;

		iUsernameArray = new char[40];
		iDeviceOSArray = new char[15];
		iUserRoleArray = new char[8];

		// Changed to allow for easier addition of information
		for (int i = 0; i < prString.Length; i++) {
			if (prString[i] != ';') {
				switch (iSection) {
				case 0: // User Role
					if (prString[i] != '|') {
						iUserRoleArray[iCurrPos] = prString[i];
						iCurrPos++;
					} else {
						iCurrPos = 0;
						iSection++;
					}
					break;
				case 1: // Username
					if (prString[i] != '|') {
						iUsernameArray[iCurrPos] = prString[i];
						iCurrPos++;
					} else {
						iSection++;
						iCurrPos = 0;
					}
					break;
				case 2: // Device OS
					if (prString[i] != '|') {
						iDeviceOSArray[iCurrPos] = prString[i];
						iCurrPos++;
					} else {
						iSection++;
						iCurrPos = 0;
					}
					break;
				}
			} else {
				break;
			}
		}
		prUser.UserRole = new String(iUserRoleArray).Replace("\0", "");
		prUser.DeviceOS = new String(iDeviceOSArray).Replace("\0", "");
		prUser.Username = new String(iUsernameArray).Replace("\0", "");
		prUser.CurrQuestion = new question();
		if (prUser.UserRole != "Tutor") {
			mMessageLogger.NewMessage(prUser.Username + " joined the server",
					mMessageLogger.MESSAGE_STUDENT);
		} else {
			mMessageLogger.NewMessage(prUser.Username + " joined the server",
					mMessageLogger.MESSAGE_TUTOR);
		}

		// mUserManager.AddNewUser(prUser);
		// mQuestionManager.SendQuestionListToTutors();

		RespondToConnection(prUser);
	}

	// New XML serialisation method for processing user details
	private void ProcessUserDetails(String prString, userDetails prUser) {
		transferrableUserDetails iUserDetailsFromClient = mSerialiserMethods
				.ConvertStringToUserDetails(prString);

		prUser.UserRole = iUserDetailsFromClient.UserRole;
		prUser.DeviceOS = iUserDetailsFromClient.DeviceOS;
		prUser.Username = iUserDetailsFromClient.Username;
		if (iUserDetailsFromClient.Password != "")
			prUser.Password = encryption().Decrypt(
					iUserDetailsFromClient.Password, "P@ssword1");

		prUser.CurrQuestion = new question();

		if (prUser.UserRole != "Tutor") {
			mMessageLogger.NewMessage(prUser.Username + " joined the server",
					mMessageLogger.MESSAGE_STUDENT);
		} else {
			mMessageLogger.NewMessage(prUser.Username + " joined the server",
					mMessageLogger.MESSAGE_TUTOR);
		}

		RespondToConnection(prUser);
	}

	private void RespondToConnection(userDetails prUser) {
		// Make sure the user name is not in use.
		if (UserManager().IsUsernameAvailable(prUser.Username)) {
			// Check if the user is a tutor or student
			if (UserManager().IsUserATutor(prUser.Username)) {
				// Check password
				if (UserManager().VerifyPassword(prUser)) {
					prUser.Connected = true;
					UserManager().AddNewUser(prUser);
					QuestionManager().SendQuestionListToTutors();

					// Send a response to client
					SendConnectionResponse(prUser.Client, "TUTORCONNECTED");
				} else {
					prUser.Connected = false;
					// Send a response to client
					SendConnectionResponse(prUser.Client, "INCORRECTPASS");
				}
			} else {
				prUser.Connected = true;
				UserManager().AddNewUser(prUser);
				QuestionManager().SendQuestionListToTutors();

				// Send a response to client
				SendConnectionResponse(prUser.Client, "STUDENTCONNECTED");
			}

		} else {
			prUser.Connected = false;
			// Send a response to client
			SendConnectionResponse(prUser.Client, "USERNAMETAKEN");
		}
	}

	// Send connection response to client
	private void SendConnectionResponse(object prClient,
			String prConnectionMessage) {
		NetworkStream iClientStream;
		TcpClient iClient;

		char[] iData = System.Text.Encoding.ASCII
				.GetChars(System.Text.Encoding.ASCII
						.GetBytes(prConnectionMessage));
		byte[] iConnectionMessage = System.Text.Encoding.ASCII.GetBytes(iData);

		iClient = (TcpClient) prClient;

		// Make sure the client is still connected
		iClientStream = iClient.GetStream();

		if (iConnectionMessage != null) {
			// Seems to have fixed, or greatly reduced the chance that the
			// response gets lost.
			while (true) {
				if (iClientStream.CanWrite) {
					iClientStream.Write(iConnectionMessage, 0,
							iConnectionMessage.Length);
					break;
				}
			}
		}

		Array.Clear(iConnectionMessage, 0, iConnectionMessage.Length);
	}
}
