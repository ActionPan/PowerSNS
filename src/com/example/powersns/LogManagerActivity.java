package com.example.powersns;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;
import org.w3c.dom.Text;

import com.sns.Urls.Urls;
import com.sns.servers.DeleteDiary_servers;
import com.sns.servers.DiaryList_servers;
import com.sns.servers.GetCommentList_servers;
import com.sns.servers.UpdateDiary_servers;
import com.sns.servers.WriteDiary_servers;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class LogManagerActivity extends Activity{
	TextView tv_title,tv_date;
	EditText ed_title,ed_content;
	Button bt_back,bt_write,bt_resise,bt_resise_ok,bt_delete,bt_comment;
	ListView lv_comment;
	String did;
	ArrayList<Map<String, String>> listItem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_manager_layout);
		init();
		did=Global.Did;
		//加载评论
		load(did);   	
		bt_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LogManagerActivity.this,LogListActivity.class);
				LogManagerActivity.this.startActivity(intent);
				LogManagerActivity.this.finish();
			}
		});
		bt_write.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LogManagerActivity.this,LogAddActivity.class);
				LogManagerActivity.this.startActivity(intent);
				LogManagerActivity.this.finish();
			}
		});
		bt_resise_ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Update(ed_content.getText().toString(),did);
			}
		});
		bt_delete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Delete(did);
				Intent intent = new Intent();
				intent.setClass(LogManagerActivity.this,LogListActivity.class);
				LogManagerActivity.this.startActivity(intent);
				LogManagerActivity.this.finish();
			}
		});
	}
	public void init(){
		tv_title=(TextView)findViewById(R.id.log_manager_name);
		tv_date=(TextView)findViewById(R.id.log_manager_date);
		ed_title=(EditText)findViewById(R.id.log_manager_title);
		ed_content=(EditText)findViewById(R.id.log_manager_content);
		bt_back=(Button)findViewById(R.id.log_manager_back);
		bt_write=(Button)findViewById(R.id.log_manager_write);
		bt_resise_ok=(Button)findViewById(R.id.log_manager_resise_ok);
		bt_delete=(Button)findViewById(R.id.log_manager_delete);
		bt_comment=(Button)findViewById(R.id.log_manager_comment);
		
		lv_comment=(ListView)findViewById(R.id.log_manager_listview);
//		
		tv_title.setText(Global.str_UID+"日志列表");
		tv_date.setText(Global.DDate);
		ed_title.setText(Global.Dtitle);
		ed_content.setText(Global.DContent);
		
		
		
	}	
	public void load(String did){
		listItem = new ArrayList<Map<String, String>>();    
		SoapObject result=null;
		try {
			System.out.println("----------------------->"+result);
			result = new GetCommentList_servers().execute(did).get();
			System.out.println("----------------------->"+result);
			SoapObject ret = (SoapObject)result.getProperty("GetCommentListResult");
			System.out.println("----------------------->"+ret);
			for(int i=0;i < ret.getPropertyCount();i++)
			{			
				SoapObject retures = (SoapObject)ret.getProperty(i);
				String [] coment=new String [6];
			    coment[0] = retures.getProperty(0).toString();//读取属性
				coment[1] = retures.getProperty(1).toString();
				coment[2] = retures.getProperty(2).toString();
				coment[3] = retures.getProperty(3).toString();
				coment[4] = retures.getProperty(4).toString();
				coment[5] = retures.getProperty(5).toString();
				Map<String, String> map = new HashMap<String, String>(); 
				map.put("Cid",coment[0]);
				map.put("Did",coment[1]);
				map.put("CDate",coment[2]);	
				map.put("ComContent",coment[3]);
				map.put("FName",coment[4]);
				map.put("Photo_Addr",coment[5]);			
				listItem.add(map);
	        }
			
			 SimpleAdapter simAdapter=new SimpleAdapter(
		        		this,
		        		listItem,
		        		R.layout.log_manager_list_layout,
		        		new String[]{"FName","DDate","DContent"},
		        		new int[]{R.id.log_manager_list_nickname,R.id.log_manager_list_date,R.id.log_manager_list_comment}
     			        		);   
			 lv_comment.setAdapter(simAdapter);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
	}
	public void Update(String DContent,String did){
		try {
			String result = new UpdateDiary_servers().execute(DContent,did).get();
			if(result.equals("OK")){
				Toast.makeText(LogManagerActivity.this, "修改成功", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(LogManagerActivity.this, "修改失败", Toast.LENGTH_LONG).show();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Delete(String did){
		try {
			String result = new DeleteDiary_servers().execute(did).get();
			if(result.equals("OK")){
				Toast.makeText(LogManagerActivity.this, "删除成功", Toast.LENGTH_LONG).show();
				ed_title.setText("");
				ed_content.setText("");
				tv_date.setText("");
			}else{
				Toast.makeText(LogManagerActivity.this, "删除失败", Toast.LENGTH_LONG).show();
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
