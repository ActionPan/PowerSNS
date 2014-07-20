package com.sns.servers;

import java.util.HashMap;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

import com.example.powersns.LoginActivity;
import com.example.powersns.MainActivity;
import com.example.powersns.SOAPUtils;
import com.sns.Urls.Urls;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
	
public class LoginCheck_servers extends AsyncTask<String, String, String>  {
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		String URL=Urls.getURL();
		String method_name="LoginCheck";
		Map<String,String> maps=new HashMap<String,String>();
		maps.put("UID", arg0[0]);
		maps.put("password", arg0[1]);
		String result=SOAPUtils.callWebServiceWithParams(URL, method_name, maps);
		return result;
	
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
	

}
