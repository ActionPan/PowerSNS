package com.sns.Urls;

public class Urls {
	static String URL="http://10.10.16.192/ZZL/Service1.asmx";
//	static String URL="http://192.168.0.27/myservers/Service1.asmx";
//	static String URL="http://192.168.0.200/tomservice/Service1.asmx";
	
	static String NAME_SPACE="http://tempuri.org/";
	public Urls(){
		
	}
	public Urls(String URL,String NAME_SPACE){
		this.URL=URL;
		this.NAME_SPACE=NAME_SPACE;
	}
	public static String getURL(){
		
		return URL;
	}
	public void setURL(String URL){
		this.URL=URL;
	}
	
	public static String getNAME_SPACE(){
		
		return NAME_SPACE;
	}
	public void setNAME_SPACE(String NAME_SPACE){
		this.NAME_SPACE=NAME_SPACE;
	}
}
