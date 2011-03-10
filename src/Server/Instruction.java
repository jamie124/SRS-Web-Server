package Server;

//Holds information about an instruction to be executed
public class Instruction
{
    // Probably a shit method of doing this
    // Flags for sending
    private boolean isSending;
    private boolean isQuestion;
    private boolean isSendingToAll;

    // Flags for deleting
    private boolean isDeleting;

    // Flags for modifying
    private boolean isModifying;
    // Misc
    private boolean isResponseTimeUp;   // If flagged stops the server processing new responses

    private String mQuestionName;

   
    public Instruction()
    {
        isSending(false);
        isQuestion(false);
        isSendingToAll(false);
        isResponseTimeUp(false);
        isModifying(false);
    }


	public boolean isSending() {
		return isSending;
	}


	public void isSending(boolean isSending) {
		this.isSending = isSending;
	}


	public boolean isQuestion() {
		return isQuestion;
	}


	public void isQuestion(boolean isQuestion) {
		this.isQuestion = isQuestion;
	}


	public boolean isSendingToAll() {
		return isSendingToAll;
	}


	public void isSendingToAll(boolean isSendingToAll) {
		this.isSendingToAll = isSendingToAll;
	}


	public boolean isDeleting() {
		return isDeleting;
	}


	public void isDeleting(boolean isDeleting) {
		this.isDeleting = isDeleting;
	}


	public boolean isModifying() {
		return isModifying;
	}


	public void isModifying(boolean isModifying) {
		this.isModifying = isModifying;
	}


	public boolean isResponseTimeUp() {
		return isResponseTimeUp;
	}


	public void isResponseTimeUp(boolean isResponseTimeUp) {
		this.isResponseTimeUp = isResponseTimeUp;
	}


	public String isQuestionName() {
		return mQuestionName;
	}


	public void isQuestionName(String mQuestionName) {
		this.mQuestionName = mQuestionName;
	}
}