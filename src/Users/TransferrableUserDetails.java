package Users;

//This is cut down version of the userDetails, containing only the important information
public class TransferrableUserDetails
{
    private String mUsername;
    private String mPassword;
    private String mDeviceOS;
    private String mUserRole;
    
	public String username() {
		return mUsername;
	}
	public void username(String mUsername) {
		this.mUsername = mUsername;
	}
	public String password() {
		return mPassword;
	}
	public void password(String mPassword) {
		this.mPassword = mPassword;
	}
	public String deviceOS() {
		return mDeviceOS;
	}
	public void deviceOS(String mDeviceOS) {
		this.mDeviceOS = mDeviceOS;
	}
	public String userRole() {
		return mUserRole;
	}
	public void userRole(String mUserRole) {
		this.mUserRole = mUserRole;
	}

 
}


