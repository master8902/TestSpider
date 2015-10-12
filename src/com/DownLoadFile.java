package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class DownLoadFile {

	/*
	 * 根据URL和网页类型生成需要保存的网页的文件名，去除URL中的非中文名字符号
	 */

	public String getFileNameByUrl(String url, String contentType) {
		// 移除http
		url = url.substring(7);
		// text/html类型
		if (contentType.indexOf("html") != -1) {
			url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
			return url;
		} else {// 如application/pdf类型
			return url.replaceAll("[\\?/:*|<>\"]", "_") + "." + contentType.substring(contentType.lastIndexOf("/") + 1);
		}
	}

	/*
	 * 保存网页字节数组到本地文件，filePath为要保存的文件的相对地址
	 */
	private void saveToLocal(InputStream str, String filePath) {
		try {

			BufferedReader bufReader = new BufferedReader(new InputStreamReader(str));
			BufferedWriter bufWriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(filePath, true), "UTF-8"));
			String input = "";
			// 每读一行进行一次写入动作
			while (bufReader!=null&&(!(input = bufReader.readLine()).equals(""))&&(input!=null)) {
				bufWriter.write(input);			
			}

			if(bufReader!=null)
				bufReader.close();
			if(bufWriter!=null)
				bufWriter.close();

		} catch (ArrayIndexOutOfBoundsException e) {

			System.out.println("没有指定文件");

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	/*
	 * private void saveToLocal(byte[] data,String filePath){ try{
	 * DataOutputStream out = new DataOutputStream( new FileOutputStream(new
	 * File(filePath))); for(int i=0;i <data.length ;i++) out.write(data[i]);
	 * out.flush(); out.close(); }catch(IOException e){ e.printStackTrace(); } }
	 */
	// 下载URL指向的网页
	public String downloadFile(String url) {
		String filepath = null;
		// 生成HttpClient对象并设置参数
		HttpClient httpClient = new HttpClient();
		// 设置HTTP连接超时5S
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		// 生成GetMethod对象并设置参数
		GetMethod getMethod = new GetMethod(url);
		// 设置get请求超时5S
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		// 设置请求重试处理
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		// 执行HTTP GET请求
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			// 判断访问的状态码
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed:" + getMethod.getStatusLine());
				filepath = null;
			}
			// 出来http响应的内容
			// byte[] responseBody =
			// getMethod.getResponseBodyAsStream();//读取字为字节数组

			InputStream inputStream = getMethod.getResponseBodyAsStream();
			/*BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = br.readLine()) != null) {
				stringBuffer.append(str);
			}*/

			// 根据网页url生成保存时的文件名
			filepath = "temp\\" + getFileNameByUrl(url, getMethod.getResponseHeader("Content-Type").getValue());
			saveToLocal(inputStream, filepath);
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.err.println("Please chech your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			// 网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}

		return filepath;
	}
}
