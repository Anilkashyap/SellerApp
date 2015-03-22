package in.findable.sellerapp;

import in.findable.sellerapp.utlis.Constant;
import in.findable.sellerapp.utlis.INetworkListener;
import in.findable.sellerapp.utlis.NetworkRequest;
import in.findable.sellerapp.utlis.Util;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class SigninActivity extends AbstractActivity implements
		INetworkListener {
	TextView signup;
	TextView forgot;
	EditText editmail, editpswd;
	CheckBox rememberBox;
	public String usr_name = "";
	public String usr_pwd = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		signup = (TextView) findViewById(R.id.txt_register_now);
		forgot = (TextView) findViewById(R.id.forgot);
		editmail = (EditText) findViewById(R.id.etUserName);
		editpswd = (EditText) findViewById(R.id.etPass);
		rememberBox=(CheckBox) findViewById(R.id.checkbox_remember);
		if(Util.isRemeberLogin(SigninActivity.this))
		{
			rememberBox.setChecked(true);
			editmail.setText(Util.getEmailID(this));
			editpswd.setText(Util.getPassword(this));
		}
		signup.setText(Html.fromHtml(getString(R.string.register_now)));
		rememberBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Util.setRememberLogin(SigninActivity.this, isChecked);
			}
		});
	}

	public void signup(View v) {

		// Intent intent=new Intent(SigninActivity.this,SignUpScreen.class);
		// startActivity(intent);
		// finish();
	}

	public void forgot(View v) {

		// Intent intent=new Intent(SigninActivity.this,Signinupscreen.class);
		// startActivity(intent);
		// finish();
	}

	public void signin(View v) {
		usr_name = editmail.getText().toString();
		usr_pwd = editpswd.getText().toString();
		if (!isOnline()) {
			displayMessage(R.string.internet_connection_not_available);

		} else if (!Util.isValidEmail(usr_name)) {
			editmail.setError("Invalid Email");

		} else if (!Util.isValidPassword(usr_pwd)) {
			editpswd.setError("Invalid Password");

		} else {
			// String key[]={"email","password"};
			// String values[]={usr_name,usr_pwd};
			JSONObject jsonPostObject = new JSONObject();
			try {
				jsonPostObject.put("email", usr_name);
				jsonPostObject.put("password", usr_pwd);
			} catch (JSONException e) {
				e.printStackTrace();
				jsonPostObject = null;
			}
			// NetworkRequest shoplistRequest = new
			// NetworkRequest(SigninActivity.this,
			// Constant.SOP_LIST_URL, this, NetworkRequest.GET, true);
			// shoplistRequest.execute();
			NetworkRequest logiunRequest = new NetworkRequest(
					SigninActivity.this, Constant.SIGN_IN_BY_EMAIL, this,
					NetworkRequest.POST, jsonPostObject, true);
			logiunRequest.execute();
		}

	}

	@Override
	public void onSuccess(String message) {
		try {

			if (!message.equals("") || message != null) {
				JSONObject _response = new JSONObject(message);
				String email = _response.getString("email");
				String user = _response.getString("user");
				String token = _response.getString("token");
				String name = _response.getString("name");
				Util.setLoginCredential(SigninActivity.this, email, token, name, user,usr_pwd);
				Intent intent = new Intent(SigninActivity.this,
						ShopList.class);
				startActivity(intent);
				finish();
			}
			
		} catch (Exception e) {
			displayMessage("Exception occured  during login");
			editpswd.setText("");
			editmail.setText("");

		}

	}

	@Override
	public void onFailure(String errorMessage) {
		System.out.println("error occured during class" + errorMessage);
	}

}
