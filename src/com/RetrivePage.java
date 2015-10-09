package com;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class RetrivePage {

	private static HttpClient httpclient = new HttpClient();
	
	public static boolean  downloagPge(String path) throws HttpException,IOException{
		InputStream input = null;
		OutputStream output = null;
		
		PostMethod postMehtod = new PostMethod(path);
/*		NameValuePair[] postData = new NameValuePair[2];
		postData[0] = new NameValuePair("name","lietu");
		postData[1] = new NameValuePair("password","******");
		postMehtod.addParameters(postData);*/
		
	
		int statusCode = httpclient.executeMethod(postMehtod);
		
		if(statusCode == HttpStatus.SC_OK){
			input = postMehtod.getResponseBodyAsStream();
			String filename = path.substring(path.lastIndexOf('/')+1);
			output = new FileOutputStream("test.html");
			
			int tempByte = -1;
			while((tempByte = input.read())>0){
				output.write(tempByte);
			}
			
			if(input !=null){
				input.close();
			}
			
			if(output != null){
				output.close();
			}
			return true;
		}
		
		
		return false;
	}
	
	
	
	public static void main(String[] args) {
		try{
			RetrivePage.downloagPge("http://www.lietu.com/");
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
