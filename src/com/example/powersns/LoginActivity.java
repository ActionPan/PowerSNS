package com.example.powersns;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;

import com.sns.servers.LoginCheck_servers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class LoginActivity extends Activity {
	EditText login_username,login_password;
	Button bt_login,bt_register;
	CheckBox cb_remeber;
	SharedPreferences sp;
	String username,password;
	static String static_UID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_layout);
		//控件识别
		inti();		
		cb_remeber.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(buttonView.isChecked()){
					sp.edit().putBoolean("isChecked", true).commit();
				}else{
					sp.edit().putBoolean("isChecked", false).commit();
				}
			}
		});
		
		bt_login.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				username=login_username.getText().toString();
				password=login_password.getText().toString();
				if(cb_remeber.isChecked()){
					Editor editor=sp.edit();
					editor.putBoolean("isChecked", true);
					editor.putString("username", username);
					editor.putString("password", password);
					editor.commit();
				}
//				new RegTask().execute(username,password);
				try {
					asdf(username,password);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		bt_register.setOnClickListener(new View.OnClickListener() {
	
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if(sp.getBoolean("isChecked", false)){
			cb_remeber.setChecked(true);
			login_username.setText(sp.getString("username", null));
			login_password.setText(sp.getString("password", null));
		}
		super.onResume();
	}

	public void inti(){
		login_username=(EditText)findViewById(R.id.login_username);
		login_password=(EditText)findViewById(R.id.login_password);
		sp=getSharedPreferences("spFile", Context.MODE_PRIVATE);
		
		bt_login=(Button)findViewById(R.id.bt_login);
		bt_register=(Button)findViewById(R.id.bt_register);
		cb_remeber=(CheckBox)findViewById(R.id.cb_remeber);
	}
	public void asdf(String username,String password) throws InterruptedException, ExecutionException{
		String result = new LoginCheck_servers().execute(username,password).get();
		if(result.equals("true")){
			Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//			intent.putExtra("Uid",username);
			Global.str_UID=username;
			startActivity(intent);
			LoginActivity.this.finish();
		}else{
			Toast.makeText(LoginActivity.this, "用户名或密码错误!", Toast.LENGTH_LONG).show();
		}
	}
}
