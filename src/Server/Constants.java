package Server;

public class Constants {

	public static final boolean DEBUG = true;
	
	public static final int LOGIN_FAILED = -1;
	public static final int LOGIN_SUCCESSFUL = 0;
	public static final int USERNAME_INCORRECT = 1;
	public static final int PASSWORD_INCORRECT = 2;
	public static final int USER_ALREADY_LOGGED_IN = 3;
	public static final int LOGOUT_SUCCESSFUL = 4;
	
	// User Status Flags
	public static final int USER_LOGGED_OUT = 0;
	public static final int USER_LOGGED_IN = 1;
	public static final int USER_AWAY = 2;
}
