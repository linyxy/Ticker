package com.earth.ticker.assist;

import android.content.Context;
import android.util.Log;

import com.earth.ticker.R;
import com.earth.ticker.util.SQLOperate;

/**
 * this method is used to demo a sample status
 * Usage:
 * 1.when user first open the app 
 * 2.to fill sample data used for UI text into database
 * @author pro
 *
 */
public class FirstOpenSample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static void addSomeFolders(Context ctx)
	{
		Log.d(SQLOperate.DBtag, "create a sample folder");
		boolean isFolderCeated;
		isFolderCeated = SQLOperate.addFolder(ctx,(String)ctx.getResources().getText(R.string.sampleFolder));
		Log.d(SQLOperate.DBtag, "the table is created? "+String.valueOf(isFolderCeated));
		isFolderCeated = SQLOperate.addFolder(ctx,(String)ctx.getResources().getText(R.string.sampleFolder2));
		Log.d(SQLOperate.DBtag, "the table is created? "+String.valueOf(isFolderCeated));
		isFolderCeated = SQLOperate.addFolder(ctx,(String)ctx.getResources().getText(R.string.sampleFolder3));
		Log.d(SQLOperate.DBtag, "the table is created? "+String.valueOf(isFolderCeated));
	}

	public static void addSomeEventForFolderFragmentTest(Context ctx){
		SQLOperate.addEvent(ctx, "da bao jian", (String)ctx.getResources().getText(R.string.sampleFolder),
				"ample_icon", "","", "", "", "","","","1");
		SQLOperate.addEvent(ctx, "买水", (String)ctx.getResources().getText(R.string.sampleFolder),
				"ample_icon", "","", "", "", "","","","1");
		SQLOperate.addEvent(ctx, "赚取节操", (String)ctx.getResources().getText(R.string.sampleFolder),
				"ample_icon", "","", "", "", "","","","1");
		
	}
}
