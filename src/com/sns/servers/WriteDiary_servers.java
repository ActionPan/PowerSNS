package com.sns.servers;

import java.util.HashMap;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

import android.os.AsyncTask;

import com.example.powersns.SOAPUtils;
import com.sns.Urls.Urls;

public class WriteDiary_servers extends AsyncTask<String, String, String>{
	protected String doInBackground(String... arg0) {
		String URL = Urls.getURL();
		String method_name="WriteDiary";
		Map<String,String> maps=new HashMap<String,String>();
		maps.put("title", arg0[0]);
		maps.put("content", arg0[1]);
		maps.put("UID", arg0[2]);
		String result=SOAPUtils.callWebServiceWithParams(URL, method_name, maps);
		return result;
	}
}
