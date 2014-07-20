package com.sns.servers;

import java.util.HashMap;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

import android.os.AsyncTask;

import com.example.powersns.Global;
import com.example.powersns.SOAPUtils;
import com.sns.Urls.Urls;

public class DeleteFriend_servers extends AsyncTask<String, String, String>{
	protected String doInBackground(String... arg0) {
		String URL = Urls.getURL();
		String method_name="DeleteFriend";
		Map<String,String> maps=new HashMap<String,String>();
		maps.put("UID", Global.str_UID);
		maps.put("fUid", arg0[0]);
		String result=SOAPUtils.callWebServiceWithParams(URL, method_name, maps);
		return result;
	}
}
