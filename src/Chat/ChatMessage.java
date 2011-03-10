package Chat;

public class ChatMessage
{
    private String mSendTo;     // Can be either a client name or "ALL"
    private String mFrom;       // Where the message is from
    private String mMessage;    // Message to be sent
    
	public String sendTo() {
		return mSendTo;
	}
	public void sentTo(String mSendTo) {
		this.mSendTo = mSendTo;
	}
	public String from() {
		return mFrom;
	}
	public void from(String mFrom) {
		this.mFrom = mFrom;
	}
	public String message() {
		return mMessage;
	}
	public void message(String mMessage) {
		this.mMessage = mMessage;
	}

  
}
