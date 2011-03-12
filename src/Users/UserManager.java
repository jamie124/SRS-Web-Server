package Users;

import java.util.ArrayList;
import java.util.HashMap;

import Question.Question;
import Server.MessageLogger;

public class UserManager {

	private MessageLogger mMessageLogger;
	private XmlHandler mXmlHandler;

	private int mMaxUserKey;

	private HashMap<Integer, UserDetails> mUsersOnline;
	private HashMap<Integer, TutorDetails> mTutors;
	// Version of user details intended for transfer to clients
	private ArrayList<TransferrableUserDetails> mUserDetailsToTransfer;

	public UserManager(MessageLogger prMessageLogger) {
		mUsersOnline = new HashMap<Integer, UserDetails>();
		mMessageLogger = prMessageLogger;

		mXmlHandler = new XmlHandler();
	}

	public int maxUserKey() {
		return mMaxUserKey;
	}

	public void maxUserKey(int mMaxUserKey) {
		this.mMaxUserKey = mMaxUserKey;
	}

	public HashMap<Integer, UserDetails> usersOnline() {
		return mUsersOnline;
	}

	public void usersOnline(HashMap<Integer, UserDetails> mUsersOnline) {
		this.mUsersOnline = mUsersOnline;
	}

	public ArrayList<TransferrableUserDetails> userDetailsToTransfer() {
		return mUserDetailsToTransfer;
	}

	public void userDetailsToTransfer(ArrayList<TransferrableUserDetails> mUserDetailsToTransfer) {
		this.mUserDetailsToTransfer = mUserDetailsToTransfer;
	}

	public boolean LoadTutors(String prFilename) {
		// Attempt to load settings from file
		mTutors = mXmlHandler.LoadUserSettings(prFilename);
		if (mTutors != null) {
			return true;
		} else
			return false;
	}

	// Sets a question for each user
	public void SetQuestions(Question prQuestion) {
		int i = 0;

		if (mUsersOnline.size() > 0) {
			while (i <= mMaxUserKey) {
				if (mUsersOnline.containsKey(i)) {
					mUsersOnline.get(i).currQuestion(prQuestion);

					/*
					 * TODO: May not be needed
					 * mUsersOnline.get(i).currQuestion()
					 * .questionString(prQuestion.questionString());
					 * mUsersOnline
					 * .get(i).currQuestion().questionID(prQuestion.questionID
					 * ()); mUsersOnline[i].CurrQuestion.QuestionType =
					 * prQuestion.QuestionType;
					 * mUsersOnline[i].CurrQuestion.PossibleAnswers =
					 * prQuestion.PossibleAnswers;
					 * mUsersOnline[i].CurrQuestion.Answer = prQuestion.Answer;
					 */

				}
				i++;
			}
		}
	}

	// Get the largest key in the user dictionary
	private int getLargestUserKey() {
		int iCurrentLargestKey = 0;
		int key = 0;

		for (int keyString : mUsersOnline.keySet()) {
			if (keyString > key)
				key = keyString;
		}

		return key;
	}

	// Copy the important details from userDetails to transferrableUserDetails
	private void processUserDetails() {
		mUserDetailsToTransfer = new ArrayList<TransferrableUserDetails>();
		TransferrableUserDetails iTempDetails = new TransferrableUserDetails();

		// Loop through dictionary
		for (UserDetails userDetails : mUsersOnline.values()) {
			iTempDetails = new TransferrableUserDetails();
			iTempDetails.username(userDetails.username());
			iTempDetails.deviceOS(userDetails.deviceOS());
			iTempDetails.userRole(userDetails.userRole());

			mUserDetailsToTransfer.add(iTempDetails);
		}
	}

	// Send the current user details to tutors
	public void sendUserListToTutors() {
		processUserDetails();
		int i = 0;
		int iNumUsers = 0;

		iNumUsers = mMaxUserKey;

		// Hack to fix the bug that stops the server from sending userlist
		// update
		// when there there's only 1 user online.
		if (mUsersOnline.size() == 1) {
			i = getLargestUserKey();
		}

		while (i <= iNumUsers) {
			// Only send the list to a tutor
			if (mUsersOnline.containsKey(i)) {
				if (mUsersOnline.get(i).userRole() == "Tutor") {
					mUsersOnline.get(i).userListRequested(true);
				}
			}
			i++;
		}

	}

	// Check if the tutors list contains the provided name
	public boolean isUserATutor(String prUsername) {
		if (mTutors != null) {
			for (UserDetails userDetails : mUsersOnline.values()) {
				if (userDetails.userRole() == "Tutor")
					return true;
			}
		}
		return false;
	}

	// Check the password against a user
	public boolean VerifyPassword(userDetails prUser)
    {
        foreach (KeyValuePair<int, TutorDetails> iUser in mTutors)
        {
            // Find the username
            if (iUser.Value.Name == prUser.Username)
            {
                // Check the pass
                if (iUser.Value.Password == prUser.Password)
                {
                    return true;
                }
            }
        }
        return false;
    }

	// Get number of users online
	public int GetNumOfUsers() {
		int iCount;
		// mThreadMutex.WaitOne();
		iCount = usersOnline().Count();
		// mThreadMutex.ReleaseMutex();

		return iCount;
	}

	// Gets a new free ID
	public int GetNewUserID(int prExtraIndex) {
		if (usersOnline().Count == 0)
			return 0;
		else
			return usersOnline().Last().Key + 1 + prExtraIndex;
	}

	public void AddNewUser(userDetails prUser) {
		int iNewID = GetNewUserID(0);
		int iExtra = 0;

		// Make sure the generated ID is not already in use
		while (true) {
			if (usersOnline().ContainsKey(iNewID)) {
				iExtra++;
				iNewID = GetNewUserID(iExtra);
			} else
				break;
		}

		usersOnline().Add(iNewID, prUser);

		// Update max user key
		maxUserKey(GetLargestKeyUserDict());

		SendUserListToTutors();
	}

	// Add a new tutor to the list
	public void AddTutor(TutorDetails prTutor)
    {
        if (mTutors == null)
            mTutors = new Dictionary<int, TutorDetails>();

        if (mTutors.Count == 0)
            mTutors.Add(0, prTutor);
        else
            mTutors.Add(mTutors.Last().Key + 1, prTutor);
    }

	// Remove a tutor from the list
	public void RemoveTutor(String prTutorName)
    {
        int iTutorID = 0;

        foreach (KeyValuePair<int, TutorDetails> iTutor in mTutors)
        {
            if (iTutor.Value.Name == prTutorName)
            {
                iTutorID = iTutor.Key;
                break;
            }
        }

        mTutors.Remove(iTutorID);
    }

	// Gets the tutor object for the provided name
	public TutorDetails GetTutorDetails(String prName)
    {
        foreach (KeyValuePair<int, TutorDetails> iTutor in mTutors)
        {
            if (iTutor.Value.Name == prName)
                return iTutor.Value;
        }
        return null;
    }

	// Remove a user
	public void RemoveUser(TcpClient prClient)
    {
        // Find the key
        int iKey = 0;

        foreach (KeyValuePair<int, userDetails> iUser in mUsersOnline)
        {
            if (iUser.Value.Client == prClient)
            {
                iKey = iUser.Key;
                break;
            }
        }

        //mThreadMutex.WaitOne();
        lock (mLock)
        {
            mUsersOnline.Remove(iKey);
        }
        

        //mThreadMutex.ReleaseMutex();
        
    }

	// Kick all connected users from server
	public void KickUsers()
    {
        //mThreadMutex.WaitOne();
        lock (mLock)
        {
            mUsersOnline.Clear();
        }
        //mThreadMutex.ReleaseMutex();
    }

	// Gets the username for the given client
	public String GetUsernameByClient(TcpClient prClient)
    {
        foreach (KeyValuePair<int, userDetails> iUser in mUsersOnline)
        {
            if (iUser.Value.Client == prClient)
            {
                return iUser.Value.Username;
            }
        }
        return "Unknown";
    }

	// Gets the user details for the given client
	public userDetails GetUserByClient(TcpClient prClient)
    {
        foreach (KeyValuePair<int, userDetails> iUser in mUsersOnline)
        {
            if (iUser.Value.Client == prClient)
            {
                return iUser.Value;
            }
        }
        return new userDetails();
    }

	// Check if the username is available
	public boolean IsUsernameAvailable(String prUsername)
    {
        foreach (KeyValuePair<int, userDetails> iUser in mUsersOnline)
        {
            if (iUser.Value.Username == prUsername)
            {
                return false;
            }
        }
        return true;
    }
}
