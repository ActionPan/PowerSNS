package com.sns.servers;

import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask;

import com.example.powersns.SOAPUtils;
import com.sns.Urls.Urls;

public class UpdateInfo_Service extends AsyncTask<String, String, String>{
	protected String doInBackground(String... arg0) {
		String URL = Urls.getURL();
		String method_name="UpdateUserInfo";
		Map<String,String> maps=new HashMap<String,String>();
		maps.put("UID", arg0[0]);
		maps.put("_name", arg0[1]);
		maps.put("_age", arg0[2]);
		maps.put("_gender", arg0[3]);
		maps.put("_mood", arg0[4]);
		
		String result=SOAPUtils.callWebServiceWithParams(URL, method_name, maps);
		return result;
	}
}
