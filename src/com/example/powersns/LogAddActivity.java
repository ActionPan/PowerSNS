package com.example.powersns;

import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;

import com.sns.servers.RegisterNewAccount_servers;
import com.sns.servers.WriteDiary_servers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogAddActivity extends Activity{
	TextView tv_title;
	EditText et_theme,et_content;
	Button bt_back,bt_asve,bt_submit,bt_again;
	
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_add_layout);
		init();
		bt_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LogAddActivity.this,LogListActivity.class);
				startActivity(intent);
				LogAddActivity.this.finish();
			}
		});
		bt_asve.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editor editor=sp.edit();
				editor.putString("theme", et_theme.getText().toString());
				editor.putString("content", et_content.getText().toString());
				editor.commit();
				Toast.makeText(LogAddActivity.this, "已保存", Toast.LENGTH_LONG).show();
			}
		});
		bt_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				load(et_theme.getText().toString(),et_content.getText().toString());
			}
		});
		bt_again.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clear();
			}
		});
	}
	public void init(){
		tv_title=(TextView)findViewById(R.id.log_add_title);
		et_theme=(EditText)findViewById(R.id.log_add_theme);
		et_content=(EditText)findViewById(R.id.log_add_content);
		bt_back=(Button)findViewById(R.id.log_add_back);
		bt_asve=(Button)findViewById(R.id.log_add_asve);
		bt_submit=(Button)findViewById(R.id.log_add_submit);
		bt_again=(Button)findViewById(R.id.log_add_again);
		
		sp=getSharedPreferences("asveFile", Context.MODE_PRIVATE);
		
		tv_title.setText(Global.str_UID+"日志列表");
	}
	public void load(String theme,String content){
		try {
			String result = new WriteDiary_servers().execute(theme,content,Global.str_UID).get();
//			SoapObject result = new UpdateDiary_servers().execute(content,theme).get();
			if(result.equals("true")){
				Toast.makeText(LogAddActivity.this, "提交成功", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(LogAddActivity.this, "提交失败", Toast.LENGTH_LONG).show();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub	
		et_theme.setText(sp.getString("theme", null));
		et_content.setText(sp.getString("content", null));
		super.onResume();
	}
	public void clear(){
		et_theme.setText("");
		et_content.setText("");
	}
}
