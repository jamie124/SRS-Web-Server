package Server;

import Users.UserManager;

// Parser is extremely simple and has been designed as I go along. A lot of functionality is hardcoded 
// and the method is likely not an efficient one.
public class InstructionParser
{
    QuestionManager mQuestionManager;
    AnswerManager mAnswerManager;

    UserManager mUserManager;
    String[] mInstructionArray = new String[8];     // Holds all the commands, currently 8 max

    public InstructionParser(IOProcessor prIOProcessor)
    {
        mQuestionManager = prIOProcessor.QuestionManager;
        mUserManager = prIOProcessor.UserManager;
        mAnswerManager = prIOProcessor.AnswerManager;
    }

    // Start parsing the String
    // First pass looks for each command
    public void ParseString(String prInstructionString)
    {
        Instruction iNewInstructions = new Instruction();

        // Split the String into an array
        mInstructionArray = prInstructionString.split(",");

        foreach (String iCommand in mInstructionArray){
            switch (iCommand.ToUpper())
            {
                case "SEND":
                    iNewInstructions.IsSending = true;
                    break;
                case "QUESTION":
                    iNewInstructions.IsQuestion = true;
                    break;
                case "ALL":
                    iNewInstructions.IsSendingToAll = true;
                    break;
                case "TIMEUP":
                    iNewInstructions.IsResponseTimeUp = true;
                    break;
                case "DELETE":
                    iNewInstructions.IsDeleting = true;
                    break;
                case "MODIFY":
                    iNewInstructions.IsModifying = true;
                    break;
                default:
                    iNewInstructions.QuestionName = iCommand.Replace("\"", "");
                    break;
            }
        }
        ExecuteInstructions(iNewInstructions);
    }

    // Executes the instructions
    private void ExecuteInstructions(Instruction prInstructions)
    {
        // Sending instructions
        if (prInstructions.IsSending)
        {
            SendingInstructions(prInstructions);
        }
        else if (prInstructions.IsDeleting)
        {
            DeleteInstructions(prInstructions);
        }
        else if (prInstructions.IsResponseTimeUp)
        {
            StopReceivingResponses();
        }
    }

    // Instructions related to sending stuff
    private void SendingInstructions(Instruction prInstructions)
    {
        question iQuestionToSend;

        // Send a question and get ready to recieve responses
        if (prInstructions.QuestionName != "")
        {
            iQuestionToSend = mQuestionManager.GetQuestionByName(prInstructions.QuestionName);

            if (iQuestionToSend != null)
            {
                if (prInstructions.IsSendingToAll)
                {
                    mAnswerManager.ClearAnswerList();           // Clear existing answers
                    mUserManager.SetQuestions(iQuestionToSend); // Queue the question to be sent
                    mAnswerManager.ReceiveResponses = true;     // Allow responses to be recieved
                }
            }
        }
    }

    // Instructions for deleting a question
    private void DeleteInstructions(Instruction prInstructions)
    {
        question iQuestionToDelete;

        if (prInstructions.QuestionName != "")
        {
            iQuestionToDelete = mQuestionManager.GetQuestionByName(prInstructions.QuestionName);

            if (iQuestionToDelete != null)
            {
                mQuestionManager.DeleteQuestion(iQuestionToDelete);
            }
        }

        mQuestionManager.SendQuestionListToTutors();
    }

    // Stop the server from receiving answers from students
    private void StopReceivingResponses()
    {
        mAnswerManager.ReceiveResponses = false;
    }
}