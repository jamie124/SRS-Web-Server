package Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import Chat.ChatMessage;

public class MessageLogger
{
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
        
    HashMap<Integer, String> mMessageLog;			// Store all local server messages
    private Queue<ChatMessage> mChatMessageQueue;                // Queue to hold messages to be sent

    private int mCurrentMessageNo;
    private int mLastMessagePosted;

    public MessageLogger()
    {
        mMessageLog = new HashMap<Integer, String>();
        //mChatMessageQueue = new List<Message>();
        mChatMessageQueue = new LinkedList<ChatMessage>();
    }

    public boolean IsMessageQueueEmpty()
    {
        if (mChatMessageQueue.size() == 0)
            return true;
        else
            return false;
    }

    // Get any messages in queue and return as a String to be sent to all connected clients
    public String GetMessagesToSend()
    {
        String iMessages = "";
        ChatMessage iMessage;

        if (mChatMessageQueue.size() != 0)
        {
            for (int i = 0; i <= mChatMessageQueue.size(); i++)
            {
                iMessage = (ChatMessage)mChatMessageQueue.poll();
                iMessages += iMessage.message() + ": " + iMessage.message() + "\n";
            }
        }
        return iMessages;
    }

    // Gets the first message in the queue
    public ChatMessage GetNextMessageToSend()
    {
        ChatMessage iLastMessage = new ChatMessage();

        iLastMessage = (ChatMessage)mChatMessageQueue.poll();

        return iLastMessage;
    }

    public String DisplayLastMessages()
    {
        int iMessagesToReturn = 0;
        int iSize = mMessageLog.size();
        String iMessages = "";

        iMessagesToReturn = iSize - mLastMessagePosted;

        if (iSize == 1)
        {
            iMessages = mMessageLog.get(0);
        }
        else
        {
            for (int i = mLastMessagePosted; i < (mLastMessagePosted + iMessagesToReturn); i++)
            {
                iMessages += mMessageLog.get(i);
                //mLastMessagePosted++;
            }
        }

       
        mLastMessagePosted = iSize;
        return iMessages;
    }

    public void NewMessage(String prMessage, int prFlag)
    {
        String iMsgToAdd = "";
        int iIndex = 0;

        iIndex = mCurrentMessageNo;

        switch (prFlag)
        {
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

        // To stop a bug as well as limit log spam a message must not be a duplicate of the message before it
        // Fix at some point to allow for more accurate results
        String iPreviousMessage = "";
        if (mMessageLog.containsValue(iIndex - 1))
        	iPreviousMessage = mMessageLog.get(iIndex - 1);
        
        if (iPreviousMessage != iMsgToAdd)
        {
            mMessageLog.put(iIndex, iMsgToAdd);
            mCurrentMessageNo += 1;
        }
    }

    public void ClearAllMessages()
    {
        mMessageLog.clear();
    }

    public boolean IsNewMsgAvailable()
    {
        if (mMessageLog.size() > mLastMessagePosted)
            return true;
        else
            return false;
    }

    public boolean IsLogEmpty()
    {
        if (mMessageLog.size() == 0)
            return true;
        else
            return false;
    }
}