package com.sns.servers;

import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask;

import com.example.powersns.SOAPUtils;
import com.sns.Urls.Urls;

public class DeleteDiary_servers extends AsyncTask<String, String, String>{
	protected String doInBackground(String... arg0) {
		String URL = Urls.getURL();
		String method_name="DeleteDiary";
		Map<String,String> maps=new HashMap<String,String>();
		maps.put("did", arg0[0]);
		String result=SOAPUtils.callWebServiceWithParams(URL, method_name, maps);
		return result;
	}

}
