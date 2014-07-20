package com.example.powersns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;

import com.sns.servers.AddFriend_servers;
import com.sns.servers.DeleteFriend_servers;
import com.sns.servers.DiaryList_servers;
import com.sns.servers.FriendRequest_servers;
import com.sns.servers.GetFriendRequestList_servers;
import com.sns.servers.RegisterNewAccount_servers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class AddFriendActivity extends Activity{
	Button bt_back,bt_search;
	EditText et_fUid;
	ListView lv_Friend;
	String fuid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_add_layout);
		init();
		GetRequest();
		bt_search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				fuid=et_fUid.getText().toString();
				if(fuid.equals("anyType{}")){
					Toast.makeText(AddFriendActivity.this,"������Ҫ���ҵ�UID", Toast.LENGTH_LONG).show();
				}else
				load(fuid);
			}
		});
		bt_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(AddFriendActivity.this,FriendListActivity.class);
				AddFriendActivity.this.startActivity(intent);
				AddFriendActivity.this.finish();
			}
		});
		
	}
	public void init(){
		bt_back=(Button)findViewById(R.id.friend_add_back);
		bt_search=(Button)findViewById(R.id.friend_add_search);
		et_fUid=(EditText)findViewById(R.id.friend_add_fUid);
		lv_Friend=(ListView)findViewById(R.id.friend_add_friendlist);
	}
	public void load(String UID2){
		try {
			String result = new FriendRequest_servers().execute(UID2).get();
			if(result.equals("N")){
				Toast.makeText(AddFriendActivity.this,"��������û�ID������", Toast.LENGTH_LONG).show();
			}if(result.equals("E")){
				Toast.makeText(AddFriendActivity.this,"���û�������ĺ���", Toast.LENGTH_LONG).show();				
			}if(result.equals("R")){
				Toast.makeText(AddFriendActivity.this,"��������ѷ���", Toast.LENGTH_LONG).show();				
			}if(result.equals("A")){
				Toast.makeText(AddFriendActivity.this,"��������ѽ���", Toast.LENGTH_LONG).show();				
			}if(result.equals("Y")){
				Toast.makeText(AddFriendActivity.this,"�ѳɹ�������������", Toast.LENGTH_LONG).show();				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void GetRequest(){
		SoapObject result;
		try {
			result = new GetFriendRequestList_servers().execute(Global.str_UID).get();
			System.out.println("------------------------>"+result);
			SoapObject detail = (SoapObject) result.getProperty("GetFriendRequestListResult");
			String Username = detail.getProperty(0).toString();
			String nickname = detail.getProperty(1).toString();
			
			String[] _Username=Username.split("\n");
			String[] _nickname=nickname.split("\n");
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>(); 
		
			for(int i=0;i < _Username.length;i++)
			{
	        Map<String,Object> map=new HashMap<String,Object>();
	        //������
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
			 lv_Friend.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						HashMap<String,String> map=(HashMap<String,String>)lv_Friend.getItemAtPosition(arg2);
						final String fUid1=map.get("Username");
						 AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(AddFriendActivity.this);
					        alertbBuilder.setMessage("Ҫ��Ӹú�����");
					        alertbBuilder.setPositiveButton("����", new DialogInterface.OnClickListener() {
					                    public void onClick(DialogInterface dialog, int which) {
					                    	//ȷ�����
					                    	addFriend(fUid1,"A");
					                    	
					                    }
					                });
					        alertbBuilder.setNegativeButton("�ܾ�", new DialogInterface.OnClickListener() {
					                    public void onClick(DialogInterface dialog, int which) {
					                    	addFriend(fUid1,"");
					                    	GetRequest();
					                        dialog.cancel();
					                    }
					                });
					        alertbBuilder.show();
						return false;
					}
				});
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		
	}
	//ȷ����Ӻ���  ������
	public void addFriend(String fUid,String flag){
		try {
			String result = new AddFriend_servers().execute(fUid,flag).get();
			if(result.equals("Y")){
				Toast.makeText(AddFriendActivity.this, "��ӳɹ�", Toast.LENGTH_LONG).show();
				GetRequest();
			}else{
				Toast.makeText(AddFriendActivity.this, "�Ѿܾ�", Toast.LENGTH_LONG).show();
				
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
