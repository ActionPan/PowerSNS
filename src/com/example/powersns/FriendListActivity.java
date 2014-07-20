package com.example.powersns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;

import com.sns.servers.DeleteDiary_servers;
import com.sns.servers.DeleteFriend_servers;
import com.sns.servers.GetFriendList_servers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class FriendListActivity extends Activity{
	Button bt_back,bt_add;
	ListView lv_Friend;
	String fUid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_layout);
		//识别控件 
		init();
		try {
			//加载好友
			load();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bt_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(FriendListActivity.this,MainActivity.class);
				FriendListActivity.this.startActivity(intent);			
				FriendListActivity.this.finish();
			}
		});
		bt_add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(FriendListActivity.this,AddFriendActivity.class);
				FriendListActivity.this.startActivity(intent);			
			}
		});
	}
	public void init(){
		bt_back=(Button)findViewById(R.id.friend_back);
		bt_add=(Button)findViewById(R.id.friend_add);
		lv_Friend=(ListView)findViewById(R.id.friend_content);
	}
	public void load() throws InterruptedException, ExecutionException{
		SoapObject result = new GetFriendList_servers().execute(Global.str_UID).get();	
		SoapObject detail = (SoapObject) result.getProperty("GetFriendListResult");
		
		String Username = detail.getProperty(0).toString();
		String nickname = detail.getProperty(1).toString();
		
		String[] _Username=Username.split("\n");
		String[] _nickname=nickname.split("\n");
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>(); 
		for(int i=0;i < _Username.length;i++)
		{
        Map<String,Object> map=new HashMap<String,Object>();    
		if(!_Username[i].equals("anyType{}")){
       	    map.put("Username",_Username[i]);
			map.put("nickname",_nickname[i]);
		
	        list.add(map);
       }else{
       	    map.put("Username","");
	 		map.put("nickname","");
       	
       }   
        
		}
		 SimpleAdapter simAdapter=new SimpleAdapter(
	        		this,
	        		list,
	        		R.layout.friend_list_layout,
	        		new String[]{"Username","nickname"},
	        		new int[]{R.id.riend_mood,R.id.friend_nike}
    		
	        		);
	       
		 lv_Friend.setAdapter(simAdapter);
		 
		 //监听
		 lv_Friend.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				HashMap<String,String> map=(HashMap<String,String>)lv_Friend.getItemAtPosition(arg2);
				fUid=map.get("Username");
				 AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(FriendListActivity.this);
			        alertbBuilder.setMessage("要删除该好友吗？");
			        alertbBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int which) {
			                    	//删除好友
			                    	deleteFriend(fUid);
			                    	
			                    }
			                });
			        alertbBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int which) {
			                        dialog.cancel();
			                    }
			                }).create();
			        alertbBuilder.show();
				return false;
			}
		});
	}
	
	public void deleteFriend(String fUid){
		try {
			String result = new DeleteFriend_servers().execute(fUid).get();
			if(result.equals("OK")){
				Toast.makeText(FriendListActivity.this, "删除成功", Toast.LENGTH_LONG).show();
				load();
			}else{
				Toast.makeText(FriendListActivity.this, "删除失败", Toast.LENGTH_LONG).show();
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
