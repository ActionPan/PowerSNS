package com.sns.servers;

import java.util.HashMap;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

import com.example.powersns.SOAPUtils;
import com.sns.Urls.Urls;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.view.View.OnClickListener;

public class Personal_info_servers extends AsyncTask<String, Integer, SoapObject> {

	@Override
	protected SoapObject doInBackground(String... arg0) {
		// 下面是参数初始化
		String URL = Urls.getURL();
		
		String method_name = "GetUserInfoById";
		SoapObject results = null;
		SoapObject request = new SoapObject(Urls.getNAME_SPACE(),method_name);
		request.addProperty("UID", arg0[0]);
		results = new SOAPUtils().getWebServiceInfo(request, URL, method_name);		
		return results;
	}

	@Override
	protected void onPostExecute(SoapObject result) {
//		super.onPostExecute(result);
				
	}

}
