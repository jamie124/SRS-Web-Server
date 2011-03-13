package Users;

public class TutorDetails
{
	private int mID;
    private String mName;
    private String mPassword;
    private String mTutorClass;
    
	public String name() {
		return mName;
	}
	public void name(String mName) {
		this.mName = mName;
	}
	public String password() {
		return mPassword;
	}
	public void password(String mPassword) {
		this.mPassword = mPassword;
	}
	public String tutorClass() {
		return mTutorClass;
	}
	public void tutorClass(String mTutorClass) {
		this.mTutorClass = mTutorClass;
	}
	public int id() {
		return mID;
	}
	public void id(int mID) {
		this.mID = mID;
	}
    
    
}
