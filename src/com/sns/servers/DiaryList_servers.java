package com.sns.servers;

import java.util.HashMap;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

import com.example.powersns.SOAPUtils;
import com.sns.Urls.Urls;

import android.os.AsyncTask;

public class DiaryList_servers extends AsyncTask<String, Integer, SoapObject>{

	protected SoapObject doInBackground(String... arg0) {
		// 下面是参数初始化
		String URL = Urls.getURL();
		
		String method_name = "GetDiaryList";
		SoapObject results = null;
		SoapObject request = new SoapObject(Urls.getNAME_SPACE(),method_name);
		request.addProperty("UID", arg0[0]);
		results = new SOAPUtils().getWebServiceInfo(request, URL, method_name);		
		return results;
	}

}
