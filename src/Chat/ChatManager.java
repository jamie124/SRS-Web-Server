package Chat;

import java.util.LinkedList;
import java.util.Queue;

public class ChatManager
{
    private Queue<ChatMessage> mChatMessages; // Queue to hold messages to be sent


    public ChatManager()
    {
        chatMessages(new LinkedList<ChatMessage>());
    }


	public Queue<ChatMessage> chatMessages() {
		return mChatMessages;
	}


	public void chatMessages(Queue<ChatMessage> mChatMessages) {
		this.mChatMessages = mChatMessages;
	}
}

