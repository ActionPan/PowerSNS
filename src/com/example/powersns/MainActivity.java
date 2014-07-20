package com.example.powersns;

import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;
import com.sns.servers.Personal_info_servers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity{
	TextView tv_nickname,tv_mood,tv_address;
	ImageView iv_gender;
	Button bt_revise_data,bt_revise_password,bt_log,bt_photo,bt_friend,bt_friend_feed,bt_visitor,bt_music;
	String Uid,nickname,mood,age,gander,Photo_Addr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		init();
//		Intent intent=getIntent();
//		Bundle bundle=intent.getExtras();
		Uid=Global.str_UID;
		update(Uid);
		//跳到完善资料
		bt_revise_data.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				Intent intent=new Intent(MainActivity.this,DataActivity.class);			
				intent.putExtra("nickname",nickname);
				intent.putExtra("age",age);
				intent.putExtra("mood",mood);
				intent.putExtra("gander",gander);
				MainActivity.this.startActivity(intent);
			}
		});
		//跳到修改密码
		bt_revise_password.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,ChangePasswordActivity.class);
				startActivity(intent);
			}
		});
		//跳到日志列表
		bt_log.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,LogListActivity.class);
				startActivity(intent);
			}
		});
		//跳到好友列表
		bt_friend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,FriendListActivity.class);
				startActivity(intent);
			}
		});
		//跳到相册列表
		bt_photo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,PhotoListActivity.class);
				startActivity(intent);
			}
		});
		
	}
	 public void update(String user){
		   try {
				SoapObject result = new Personal_info_servers().execute(Uid).get();
				SoapObject detail = (SoapObject) result.getProperty("GetUserInfoByIdResult");
				//ID
				Uid = detail.getProperty(0).toString();
				//name
				nickname = detail.getProperty(1).toString();
				//password
				
				//mood
				mood = detail.getProperty(3).toString();
				//age
				age = detail.getProperty(4).toString();							
				//Gender 
				gander = detail.getProperty(5).toString();
				//Photo_Addr 
				Photo_Addr = detail.getProperty(6).toString();				
				tv_nickname.setText(nickname);
				if(mood.equals("anyType{}")){
					tv_mood.setText("主人什么都没写.");
					mood="主人什么都没写.";
				}else{
					tv_mood.setText(mood);
				}							
				if(gander.equals("女")){
					iv_gender.setBackgroundResource(R.drawable.ic_female);
				}else{
					iv_gender.setBackgroundResource(R.drawable.ic_male);
				}
				if(Photo_Addr.equals("anyType{}")){
					tv_address.setText("无位置显示");
					Photo_Addr="无位置显示";
				}else{
					tv_address.setText(Photo_Addr);
				}	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
	   }
	 public void init(){
		    tv_nickname=(TextView)findViewById(R.id.main_nickname);
		    tv_mood=(TextView)findViewById(R.id.main_mood);
			tv_address=(TextView)findViewById(R.id.main_address);
			iv_gender=(ImageView)findViewById(R.id.main_gender);
			bt_revise_data=(Button)findViewById(R.id.main_revise_data);
			bt_revise_password=(Button)findViewById(R.id.main_revise_password);
			bt_log=(Button)findViewById(R.id.main_log);
			bt_photo=(Button)findViewById(R.id.main_photo);
			bt_friend=(Button)findViewById(R.id.main_friend);
			bt_friend_feed=(Button)findViewById(R.id.main_friend_feed);
			bt_visitor=(Button)findViewById(R.id.main_visitor);
			bt_music=(Button)findViewById(R.id.main_music);
			
	 }
	protected void onRestart(){
		// TODO Auto-generated method stub
		update(Uid);
		super.onRestart();
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
            // TODO Auto-generated method stub
            if(item.getItemId()==R.id.Exit_login)
            {
            	Exit_login();
            }else if(item.getItemId()==R.id.Exit_app)
            {
	            Exit_app();
	               }
            return super.onOptionsItemSelected(item);
    }
	  public void  Exit_login(){
	        AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(this);
	        alertbBuilder.setTitle("退出登录").setMessage("退出当前账号");
	        alertbBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        Intent intent = new Intent(MainActivity.this,LoginActivity.class); 
	                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                        MainActivity.this.startActivity(intent);
	                        MainActivity.this.finish();
	                    }
	                });
	        alertbBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        dialog.cancel();
	                    }
	                }).create();
	        alertbBuilder.show();
	    }
	  public void  Exit_app(){
	        AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(this);
	        alertbBuilder.setTitle("退出PowerSNS").setMessage("你确定要离开客户端吗？");
	        alertbBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        Intent intent = new Intent(MainActivity.this,LoginActivity.class); 
	                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                        MainActivity.this.finish();
	                    }
	                });
	        alertbBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        dialog.cancel();
	                    }
	                }).create();
	        alertbBuilder.show();
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	 
}	
