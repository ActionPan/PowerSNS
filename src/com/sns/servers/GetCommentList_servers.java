package com.sns.servers;

import org.ksoap2.serialization.SoapObject;

import android.os.AsyncTask;

import com.example.powersns.SOAPUtils;
import com.sns.Urls.Urls;

public class GetCommentList_servers extends AsyncTask<String, Integer, SoapObject>{
	
	protected SoapObject doInBackground(String... arg0) {
		// 下面是参数初始化
		String URL = Urls.getURL();
		
		String method_name = "GetCommentList";
		SoapObject results = null;
		SoapObject request = new SoapObject(Urls.getNAME_SPACE(),method_name);
		request.addProperty("did", arg0[0]);
		results = new SOAPUtils().getWebServiceInfo(request, URL, method_name);		
		return results;
	}
}
