package com.sns.servers;

import java.util.HashMap;
import java.util.Map;

import android.R.integer;
import android.os.AsyncTask;

import com.example.powersns.SOAPUtils;
import com.sns.Urls.Urls;

public class UpdateDiary_servers extends AsyncTask<String, String, String>{
	protected String doInBackground(String... arg0) {
		String URL = Urls.getURL();
		String method_name="UpdateDiary";
		Map<String,String> maps=new HashMap<String,String>();
		maps.put("DContent", arg0[0]);
		maps.put("did",arg0[1]);
		String result=SOAPUtils.callWebServiceWithParams(URL, method_name, maps);
		return result;
	}

}
