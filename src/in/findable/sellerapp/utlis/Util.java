package in.findable.sellerapp.utlis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class Util {
	private final static String SELLER_APP_PREFERENCE = "seller_app_preference";

	public static boolean isValidEmail(String usr_name) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(usr_name);
		return matcher.matches();
	}

	public static boolean isValidPassword(String usr_pwd) {
		if (usr_pwd != null && usr_pwd.length() > 4) {
			return true;
		}
		return false;
	}

	public static String getUserName(Context context) {
		return context.getSharedPreferences(SELLER_APP_PREFERENCE,
				Context.MODE_PRIVATE).getString("name", "");
	}

	public static void setEmailID(Context context, String email) {
		context.getSharedPreferences(SELLER_APP_PREFERENCE,
				Context.MODE_PRIVATE).edit().putString("email", email).commit();

	}

	public static void setPassword(Context context, String password) {
		context.getSharedPreferences(SELLER_APP_PREFERENCE,
				Context.MODE_PRIVATE).edit().putString("password", password)
				.commit();

	}

	public static String getPassword(Context context) {
		return context.getSharedPreferences(SELLER_APP_PREFERENCE,
				Context.MODE_PRIVATE).getString("password", "");

	}

	public static String getEmailID(Context context) {
		return context.getSharedPreferences(SELLER_APP_PREFERENCE,
				Context.MODE_PRIVATE).getString("email", "");

	}

	public static void setLoginCredential(Context context, String email,
			String token, String name, String user, String password) {
		Editor editor = context.getSharedPreferences(SELLER_APP_PREFERENCE,
				Context.MODE_PRIVATE).edit();
		editor.putString("email", email).commit();
		editor.putString("token", token).commit();
		editor.putString("name", name).commit();
		editor.putString("user", user).commit();
		editor.putString("password", password).commit();

		editor.commit();

	}

	public static void setRememberLogin(Context context, boolean isRemember) {
		context.getSharedPreferences(SELLER_APP_PREFERENCE,
				Context.MODE_PRIVATE).edit()
				.putBoolean("is_remember", isRemember).commit();

	}

	public static boolean isRemeberLogin(Context context) {
		return context.getSharedPreferences(SELLER_APP_PREFERENCE,
				Context.MODE_PRIVATE).getBoolean("is_remember", false);
	}

}
