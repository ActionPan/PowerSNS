package com.example.powersns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;

import com.sns.servers.DiaryList_servers;
import com.sns.servers.UpdateInfo_Service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class LogListActivity extends Activity{
	TextView tv_title;//,tv_list_title,tv_list_date,tv_list_content;
	Button bt_back,bt_write;
	ListView lv_content;
	String Uid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_listview_layout);
		init();
		//加载
		
		Uid=Global.str_UID;
		load(Uid);
		bt_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LogListActivity.this,MainActivity.class);
				LogListActivity.this.startActivity(intent);
				
			}
		});
		bt_write.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LogListActivity.this,LogAddActivity.class);
				LogListActivity.this.startActivity(intent);
				
			}
		});
	}
	public void init(){
		tv_title=(TextView)findViewById(R.id.log_Listview_title);
		bt_back=(Button)findViewById(R.id.log_listview_back);
		bt_write=(Button)findViewById(R.id.log_listview_write);
		
		lv_content=(ListView)findViewById(R.id.log_listView_lv);

	}
	public void load(String uid){
		try {
			tv_title.setText(Global.str_UID+"日志列表");
			
			SoapObject result = new DiaryList_servers().execute(uid).get();			
			SoapObject detail = (SoapObject) result.getProperty("GetDiaryListResult");
			String Did = detail.getProperty(0).toString();
			String Dtitle = detail.getProperty(1).toString();
			String DDate = detail.getProperty(2).toString();
			String DContent=detail.getProperty(3).toString();
			
			String[] _Did=Did.split("\n");
			String[] _Dtitle=Dtitle.split("\n");
			String[] _Ddate=DDate.split("\n");
			String[] _DContent=DContent.split("\n");
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>(); 
			for(int i=0;i < _Did.length;i++)
			{
	        Map<String,Object> map=new HashMap<String,Object>();
	        if(!_Did[i].equals("anyType{}")){
	        	map.put("Did",_Did[i]);
				map.put("Dtitle",_Dtitle[i]);
				map.put("DDate",_Ddate[i]);	
				map.put("DContent",_DContent[i]);
	 		
	 	        list.add(map);
	        }else{
	        	map.put("Did","");
				map.put("Dtitle","");
				map.put("DDate","");	
				map.put("DContent","");
	        	
	        }

			}
			 SimpleAdapter simAdapter=new SimpleAdapter(
		        		this,
		        		list,
		        		R.layout.log_list_layout,
		        		new String[]{"Dtitle","DDate","DContent"},
		        		new int[]{R.id.log_list_title,R.id.log_list_date,R.id.log_list_content}
        		
		        		);	       
			 lv_content.setAdapter(simAdapter);
			 
			 lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						HashMap<String,String> map=(HashMap<String,String>)lv_content.getItemAtPosition(arg2);
						Global.Did=map.get("Did");
						Global.Dtitle=map.get("Dtitle");
						Global.DDate=map.get("DDate");
						Global.DContent=map.get("DContent");										
						Intent intent = new Intent();
						intent.setClass(LogListActivity.this,LogManagerActivity.class);
						LogListActivity.this.startActivity(intent);
						
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
	protected void onRestart(){
		// TODO Auto-generated method stub
		load(Uid);
		super.onRestart();
	}
}
