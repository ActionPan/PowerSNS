package com.example.powersns;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;

import com.sns.servers.LoginCheck_servers;
import com.sns.servers.RegisterNewAccount_servers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	EditText et_username,et_password1,et_password2,et_nickname;
	String username,password1,password2,nickname;
	Button bt_register,bt_cancel;
	Context context;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register_layout);
        et_username=(EditText)findViewById(R.id.register_username);
        et_password1=(EditText)findViewById(R.id.register_password1);
        et_password2=(EditText)findViewById(R.id.register_password2);
        et_nickname=(EditText)findViewById(R.id.register_nickname);
        bt_register=(Button)findViewById(R.id.register_register);
        bt_cancel=(Button)findViewById(R.id.register_cancel);
        context=this;
        bt_register.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				username=et_username.getText().toString().trim();
				password1=et_password1.getText().toString().trim();
				password2=et_password2.getText().toString().trim();
				nickname=et_nickname.getText().toString().trim();
				if (null == username || "".equals(username)) {
					Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_LONG).show();
				} else if (!password1.equals(password2)) {
					Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_LONG).show();
				} else {
					// 
//					new RegTask().execute(username,password1,nickname);
					RegTask(username,password1,nickname);
					
				}

				
			}
		});
        bt_cancel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
				startActivity(intent);
				RegisterActivity.this.finish();
			}
		});
        
    }
    	public void RegTask(String username,String password,String nickname){
    		try {
				String result = new RegisterNewAccount_servers().execute(username,password,nickname).get();
				if(result.equals("true")){
					Toast.makeText(RegisterActivity.this,"注册成功", Toast.LENGTH_LONG).show();
					Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
					startActivity(intent);
					RegisterActivity.this.finish();
				}else{
					Toast.makeText(RegisterActivity.this,"注册失败", Toast.LENGTH_LONG).show();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
}
