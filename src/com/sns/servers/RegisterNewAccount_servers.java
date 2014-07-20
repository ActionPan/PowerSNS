package com.sns.servers;

import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask;

import com.example.powersns.SOAPUtils;
import com.sns.Urls.Urls;

public class RegisterNewAccount_servers extends AsyncTask<String, String, String>{
	protected String doInBackground(String... arg0) {
		String URL = Urls.getURL();
		String method_name="RegisterNewAccount";
		Map<String,String> maps=new HashMap<String,String>();
		maps.put("UID", arg0[0]);
		maps.put("Password", arg0[1]);
		maps.put("NickName", arg0[2]);
		String result=SOAPUtils.callWebServiceWithParams(URL, method_name, maps);
		return result;
	}
}
