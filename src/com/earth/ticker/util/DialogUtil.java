/**
 * 
 */
package com.earth.ticker.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.app.Activity;

public class DialogUtil
{
	// ����һ����ʾ��Ϣ�ĶԻ���
	public static void showDialog(final Context ctx
		, String msg , boolean closeSelf)
	{
		// ����һ��AlertDialog.Builder����
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx)
			.setMessage(msg).setCancelable(false);
		if(closeSelf)
		{
			builder.setPositiveButton("ȷ��", new OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					// ����ǰActivity
					((Activity)ctx).finish();
				}
			});		
		}
		else
		{
			builder.setPositiveButton("ȷ��", null);
		}
		builder.create().show();
	}	
	// ����һ����ʾָ������ĶԻ���?
	public static void showDialog(Context ctx , View view)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx)
			.setView(view).setCancelable(false)
			.setPositiveButton("ȷ��", null);
		builder.create()
			.show();
	}
}
