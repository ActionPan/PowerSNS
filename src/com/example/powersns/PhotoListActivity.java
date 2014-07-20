package com.example.powersns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.sns.servers.CreateAlbum_servers;
import com.sns.servers.GetAlbumList_servers;
import com.sns.servers.GetCommentList_servers;
import com.sns.servers.RegisterNewAccount_servers;

public class PhotoListActivity extends Activity{
	Button photo_back,photo_add;
	EditText et_name,et_descript;
	ListView lv_content;
	ArrayList<Map<String, String>> listItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_listview_layout);
		init();
		load();
		bind();
		
		
	}
	public void init(){
		photo_back=(Button)this.findViewById(R.id.photo_back);
		photo_add=(Button)this.findViewById(R.id.photo_add);
		lv_content=(ListView)this.findViewById(R.id.lv_content);
	}

	public void bind(){
		photo_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(PhotoListActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
		photo_add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder=new AlertDialog.Builder(PhotoListActivity.this);
				
				builder.setTitle("创建相册");
                LayoutInflater inflater=LayoutInflater.from(PhotoListActivity.this);
				View view=inflater.inflate(R.layout.createalbum, null);
				
				et_name=(EditText)view.findViewById(R.id.pho__name);
				et_descript=(EditText)view.findViewById(R.id.pho_descript);
				builder.setView(view);
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						create(et_name.getText().toString(),et_descript.getText().toString());
					}
				});
				
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
				
				builder.create().show();
			}

		});
	}

	
	public void load(){
		listItem = new ArrayList<Map<String, String>>(); 
//		SoapObject result=null;
		try {
			SoapObject result = new GetAlbumList_servers().execute(Global.str_UID).get();
			System.out.println("---------------------->"+result);
			SoapObject ret = (SoapObject)result.getProperty("getAlbumListResult");
			for(int i=0;i < ret.getPropertyCount();i++)
			{			
				SoapObject retures = (SoapObject)ret.getProperty(i);
				String [] coment=new String [8];
			    coment[0] = retures.getProperty(0).toString();//读取属性
				coment[1] = retures.getProperty(1).toString();
				coment[2] = retures.getProperty(2).toString();
				coment[3] = retures.getProperty(3).toString();
				coment[4] = retures.getProperty(4).toString();
				coment[5] = retures.getProperty(5).toString();
				coment[6] = retures.getProperty(6).toString();
				coment[7] = retures.getProperty(7).toString();
				
				Map<String, String> map = new HashMap<String, String>(); 
				map.put("_id",coment[0]);
				map.put("user_id",coment[1]);
				map.put("name",coment[2]);	
				map.put("descript",coment[3]);
				map.put("size",coment[4]);
				map.put("face_addr",coment[5]);	
				map.put("network_addr",coment[6]);
				map.put("file_path",coment[7]);
				listItem.add(map);
	        }
			//写到这里了 待更正
			 SimpleAdapter simAdapter=new SimpleAdapter(
		        		this,
		        		listItem,
		        		R.layout.photo_list_layout,
		        		new String[]{"face_addr","name","descript"},
		        		new int[]{R.id.photo_list_face_addr,R.id.photo_list_name,R.id.photo_list_descript}
     			        		);   
			 lv_content.setAdapter(simAdapter);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void create(String name,String descript){
		try {
			String result = new CreateAlbum_servers().execute(name,descript).get();
			if(result.equals("SUCCESS..")){
				Toast.makeText(PhotoListActivity.this,"创建成功", Toast.LENGTH_LONG).show();
				
				load();
			}else{
				Toast.makeText(PhotoListActivity.this,"创建失败", Toast.LENGTH_LONG).show();
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
