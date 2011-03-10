package Server;

import java.util.ArrayList;
import java.util.HashMap;

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
    private Queue mChatMessageQueue;                // Queue to hold messages to be sent

    private int mCurrentMessageNo;
    private int mLastMessagePosted;

    public MessageLogger()
    {
        mMessageLog = new Dictionary<int, string>();
        //mChatMessageQueue = new List<Message>();
        mChatMessageQueue = new Queue();
    }

    public bool IsMessageQueueEmpty()
    {
        if (mChatMessageQueue.Count == 0)
            return true;
        else
            return false;
    }

    // Get any messages in queue and return as a string to be sent to all connected clients
    public string GetMessagesToSend()
    {
        string iMessages = "";
        chatMessage iMessage;

        if (mChatMessageQueue.Count != 0)
        {
            for (int i = 0; i <= mChatMessageQueue.Count; i++)
            {
                iMessage = (chatMessage)mChatMessageQueue.Dequeue();
                iMessages += iMessage.sUsername + ": " + iMessage.sMessage + "\n";
            }
        }
        return iMessages;
    }

    // Gets the first message in the queue
    public ChatMessage GetNextMessageToSend()
    {
        ChatMessage iLastMessage = new ChatMessage();

        iLastMessage = (ChatMessage)mChatMessageQueue.Dequeue();

        return iLastMessage;
    }

    public string DisplayLastMessages()
    {
        int iMessagesToReturn = 0;
        int iSize = mMessageLog.Count;
        string iMessages = "";

        iMessagesToReturn = iSize - mLastMessagePosted;

        if (iSize == 1)
        {
            iMessages = mMessageLog[0];
        }
        else
        {
            for (int i = mLastMessagePosted; i < (mLastMessagePosted + iMessagesToReturn); i++)
            {
                iMessages += mMessageLog[i];
                //mLastMessagePosted++;
            }
        }

       
        LastMessagePosted = iSize;
        return iMessages;
    }

    public void NewMessage(string prMessage, int prFlag)
    {
        string iMsgToAdd = "";
        int iIndex = 0;

        iIndex = CurrentMessageNo;

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
        string iPreviousMessage;
        mMessageLog.TryGetValue(iIndex - 1, out iPreviousMessage);
        if (iPreviousMessage != iMsgToAdd)
        {
            mMessageLog.Add(iIndex, iMsgToAdd);
            CurrentMessageNo += 1;
        }
    }

    public void ClearAllMessages()
    {
        mMessageLog.Clear();
    }

    public bool IsNewMsgAvailable()
    {
        if (mMessageLog.Count() > mLastMessagePosted)
            return true;
        else
            return false;
    }

    public bool IsLogEmpty()
    {
        if (mMessageLog.Count() == 0)
            return true;
        else
            return false;
    }
}