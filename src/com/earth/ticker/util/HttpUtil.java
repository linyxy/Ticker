/**
 * 
 */
package com.earth.ticker.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;
/**
 * Description:
 * <br/>Copyright (C), 2012-2022, linyxy
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  linyxy
 * @version  1.0
 */
public class HttpUtil
{
	// ����HttpClient����
	public static final String HttpTag = "HTTP_TASK";
	
	public static HttpClient httpClient = new DefaultHttpClient();
	//public static final String BASE_URL = "";
	/**
	 * 
	 * @param url ���������URL
	 * @return ��������Ӧ�ַ�
	 * @throws Exception
	 */
	public static String getRequest(String url)
		throws Exception
	{
		// ����HttpGet����		
		Log.d(HttpTag, "starting a new HTTP request");
		HttpGet get = new HttpGet(url);
		// ����GET����
		HttpResponse httpResponse = httpClient.execute(get);
		// ���������ɹ��ط�����Ӧ
		Log.d(HttpTag, "connecting the server");
		if (httpResponse.getStatusLine()
			.getStatusCode() == 200)
		{
			Log.d(HttpTag, "Server response successfully!");
			// ��ȡ��������Ӧ�ַ�
			String result = EntityUtils
				.toString(httpResponse.getEntity());

			return result;
		}
		
		return null;
	}

	/**
	 * 
	 * @param url ���������URL
	 * @param params �������?
	 * @return ��������Ӧ�ַ�
	 * @throws Exception
	 */
	public static String postRequest(String url
		, Map<String ,String> rawParams)throws Exception
	{
		Log.d(HttpTag, "start a post request");
		
		System.out.println("size of raw Params "+rawParams.size());
		
		// ����HttpPost����
		HttpPost post = new HttpPost(url);
		// ���ݲ������Ƚ϶�Ļ����ԶԴ��ݵĲ�����з�װ
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for(String key : rawParams.keySet())
		{
			System.out.println("key name: "+key+" key value: "+rawParams.get(key));
			//��װ�������?
			params.add(new BasicNameValuePair(key , rawParams.get(key)));
		}
		// �����������?
		post.setEntity(new UrlEncodedFormEntity(
			params, "UTF-8"));
		Log.d(HttpTag, "encoded the pattern into post");
		
		Log.i(HttpTag,"url----->"+ url);
		
		// ����POST����
		HttpResponse httpResponse = httpClient.execute(post);
		// ���������ɹ��ط�����Ӧ
		
		Log.i(HttpTag, "server status--->"+httpResponse.getStatusLine()
				.getStatusCode());
		
		if (httpResponse.getStatusLine()
			.getStatusCode() == 200)
		{
			Log.d(HttpTag, "server successfully responesd");
			// ��ȡ��������Ӧ�ַ�
			String result = EntityUtils
				.toString(httpResponse.getEntity());
			Log.d(HttpTag, result);
			return result;
		}
		return null;
	}
}
