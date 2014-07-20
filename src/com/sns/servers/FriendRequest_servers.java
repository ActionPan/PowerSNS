package com.sns.servers;

import java.util.HashMap;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

import com.example.powersns.Global;
import com.example.powersns.SOAPUtils;
import com.sns.Urls.Urls;

import android.os.AsyncTask;


public class FriendRequest_servers extends AsyncTask<String, String, String>{
	protected String doInBackground(String... arg0) {
		String URL = Urls.getURL();
		String method_name="FriendRequest";
		Map<String,String> maps=new HashMap<String,String>();
		maps.put("UID1", Global.str_UID);
		maps.put("UID2", arg0[0]);		
		String result=SOAPUtils.callWebServiceWithParams(URL, method_name, maps);
		return result;
	}
}
