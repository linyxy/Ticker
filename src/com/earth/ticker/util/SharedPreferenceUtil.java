package com.earth.ticker.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SharedPreferenceUtil {

	
	public static final String userInfo = "userInof";
	public static String sharedP = "sharedP";
	
	public SharedPreferenceUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * һ����������SharedPreference�ĺ���
	 * @param ctx
	 * @param fileName
	 * @param key
	 * @param value
	 */
	public static void updateSharedPreference (Context ctx,String fileName, String key,String value)
	{
		SharedPreferences S  = ctx.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = S.edit();
		editor.putString(key, value);
		editor.commit();
		Log.d(sharedP, "update sharedP");
	}
	
	
	/**
	 * ������ȡĳ�ض���ֵ
	 * @param ctx
	 * @param fileName
	 * @param key
	 * @return str
	 */
	public static String readSharedPreference(Context ctx,String fileName,String key)
	{
		SharedPreferences S = ctx.getSharedPreferences(fileName, 0);
		String str = S.getString(key, "");
		Log.d(sharedP, "successfully read from sharedP");
		return str;
	}
	
	
}
