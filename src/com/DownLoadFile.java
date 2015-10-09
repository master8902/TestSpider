package com;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class DownLoadFile {

	public String getFileNameByUrl(String url,String contentType){
		url = url.substring(7);
		if(contentType.indexOf("html" )!= -1){
			url = url.replaceAll("[\\?/:*|<>\"]","_") + ".html";
			return url;
		}else{
			return url.replaceAll("[\\?/:*|<>\"]","_") + "."+contentType.substring(contentType.lastIndexOf("/")+1);
		}
	}
	
	private void saveToLocal(byte[] data,String filePath){
		try{
			DataOutputStream out = new DataOutputStream(
					new FileOutputStream(new File(filePath)));
			for(int i=0;i <data.length ;i++)
				out.write(data[i]);
			out.flush();
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}		
	}
	
	public String downloadFile(String url){
		String filepath = null;
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		
		try{
			int statusCode = httpClient.executeMethod(getMethod);
			if(statusCode != HttpStatus.SC_OK){
				System.err.println("Method failed:" + getMethod.getStatusLine());
				filepath = null;
			}
			
			byte[] responseBody = getMethod.getResponseBody();
			filepath = "temp\\"
					+ getFileNameByUrl(url,getMethod.getResponseHeader("Content-Type").getValue());
			saveToLocal(responseBody,filepath);
		}catch(HttpException e){
			System.err.println("Please chech your provided http address!");
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}finally {
			getMethod.releaseConnection();
		}
		
		return filepath;
	}
}
