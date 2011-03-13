package Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import Chat.ChatMessage;

public class MessageLogger {
	// Defines
	static int MESSAGE_SERVER = 1;
	static int MESSAGE_TUTOR = 2;
	static int MESSAGE_STUDENT = 3;

	static int MESSAGE_CHAT = 4;
	static int MESSAGE_NETWORK = 5;
	static int MESSAGE_ERROR = 6;
	static int MESSAGE_WARNING = 7;
	static int MESSAGE_QUESTION = 8;
	static int MESSAGE_ANSWER = 9;
	static int MESSAGE_COMMAND = 10;

	private HashMap<Integer, String> mMessageLog; // Store all local server
													// messages
	private Queue<ChatMessage> mChatMessageQueue; // Queue to hold messages to
													// be sent

	private int mCurrentMessageNo;
	private int mLastMessagePosted;

	public MessageLogger() {
		messageLog(new HashMap<Integer, String>());
		// mChatMessageQueue = new List<Message>();
		chatMessageQueue(new LinkedList<ChatMessage>());
	}

	public boolean isMessageQueueEmpty() {
		if (chatMessageQueue().size() == 0)
			return true;
		else
			return false;
	}

	// Get any messages in queue and return as a String to be sent to all
	// connected clients
	public String getMessagesToSend() {
		String iMessages = "";
		ChatMessage iMessage;

		if (chatMessageQueue().size() != 0) {
			for (int i = 0; i <= chatMessageQueue().size(); i++) {
				iMessage = (ChatMessage) chatMessageQueue().poll();
				iMessages += iMessage.message() + ": " + iMessage.message() + "\n";
			}
		}
		return iMessages;
	}

	// Gets the first message in the queue
	public ChatMessage getNextMessageToSend() {
		ChatMessage iLastMessage = new ChatMessage();

		iLastMessage = (ChatMessage) chatMessageQueue().poll();

		return iLastMessage;
	}

	public String displayLastMessages() {
		int iMessagesToReturn = 0;
		int iSize = messageLog().size();
		String iMessages = "";

		iMessagesToReturn = iSize - mLastMessagePosted;

		if (iSize == 1) {
			iMessages = messageLog().get(0);
		} else {
			for (int i = mLastMessagePosted; i < (mLastMessagePosted + iMessagesToReturn); i++) {
				iMessages += messageLog().get(i);
				// mLastMessagePosted++;
			}
		}

		mLastMessagePosted = iSize;
		return iMessages;
	}

	public void newMessage(String prMessage, int prFlag) {
		String iMsgToAdd = "";
		int iIndex = 0;

		iIndex = mCurrentMessageNo;

		switch (prFlag) {
		case 1:
			iMsgToAdd = "Server: " + prMessage + "\n";
			break;
		case 2:
			iMsgToAdd = "Admin: " + prMessage + "\n";
			break;
		case 3:
			iMsgToAdd = "Student: " + prMessage + "\n";
			break;
		case 4:
			iMsgToAdd = "Message: " + prMessage + "\n";
			break;
		case 5:
			iMsgToAdd = "Network: " + prMessage + "\n";
			break;
		case 6:
			iMsgToAdd = "Error: " + prMessage + "!" + "\n";
			break;
		case 7:
			iMsgToAdd = "Warning: " + prMessage + "\n";
			break;
		case 8:
			iMsgToAdd = "Question: " + prMessage + "\n";
			break;
		case 9:
			iMsgToAdd = "Answer: " + prMessage + "\n";
			break;
		case 10:
			iMsgToAdd = "> " + prMessage + "\n";
			break;
		}

		// To stop a bug as well as limit log spam a message must not be a
		// duplicate of the message before it
		// Fix at some point to allow for more accurate results
		String iPreviousMessage = "";
		if (messageLog().containsValue(iIndex - 1))
			iPreviousMessage = messageLog().get(iIndex - 1);

		if (iPreviousMessage != iMsgToAdd) {
			messageLog().put(iIndex, iMsgToAdd);
			mCurrentMessageNo += 1;
		}
	}

	public void clearAllMessages() {
		messageLog().clear();
	}

	public boolean isNewMsgAvailable() {
		if (messageLog().size() > mLastMessagePosted)
			return true;
		else
			return false;
	}

	public boolean isLogEmpty() {
		if (messageLog().size() == 0)
			return true;
		else
			return false;
	}

	public HashMap<Integer, String> messageLog() {
		return mMessageLog;
	}

	public void messageLog(HashMap<Integer, String> mMessageLog) {
		this.mMessageLog = mMessageLog;
	}

	public Queue<ChatMessage> chatMessageQueue() {
		return mChatMessageQueue;
	}

	public void chatMessageQueue(Queue<ChatMessage> mChatMessageQueue) {
		this.mChatMessageQueue = mChatMessageQueue;
	}
}