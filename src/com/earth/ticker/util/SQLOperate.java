package com.earth.ticker.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLOperate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * sample method of SQL operation
	 * @param ctx
	 * @return
	 */
	public static boolean sampleSQL(Context ctx) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		boolean ifSuccessful = false;
		ContentValues cv = new ContentValues();
		db.beginTransaction();// 开始事务
		try {

			db.setTransactionSuccessful();// 调用此方法会在执行到endTransaction()
											// 时提交当前事务，如果不调用此方法会回滚事务
			ifSuccessful = true;
		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();
		return ifSuccessful;
	}
	
	/**
	 * the method to add note into db
	 * @param ctx
	 * @param time_stamp
	 * @param content
	 * @param last_change_date
	 * @return
	 */
	public static boolean addNote(Context ctx,int time_stamp,
			String content,int last_change_date) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		boolean ifSuccessful = false;
		ContentValues cv = new ContentValues();
		String id = null;
		cv.put("id", id);
		cv.put("time_stamp", time_stamp);
		cv.put("content",	content);
		cv.put("last_change_date", last_change_date);
		db.beginTransaction();// 开始事务
		try {
			db.insert("notes", null, cv);
			db.setTransactionSuccessful();
			ifSuccessful = true;
		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();
		return ifSuccessful;
	}
	
	/**
	 * meothd insert a sub event and relate to event
	 * @param ctx
	 * @param time_stamp
	 * @param name
	 * @param icon
	 * @param state
	 * @param related_event_id
	 * @return
	 */
	public static boolean addSubEvent(Context ctx,int time_stamp,String name,
			String icon,int state,long related_event_id) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		boolean ifSuccessful = false;
		ContentValues cv = new ContentValues();
		String id = null;
		cv.put("id", id);
		cv.put("time_stamp", time_stamp);
		cv.put("name", name);
		cv.put("icon", icon);
		cv.put("state", state);
		db.beginTransaction();// 开始事务
		try {
			long subEventId = db.insert("sub_events", null, cv);
			relateESubE(ctx,related_event_id,subEventId);
			db.setTransactionSuccessful();
			ifSuccessful = true;
		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();
		return ifSuccessful;
	}
	
	/**
	 * method insert related id with sub related one
	 * @param ctx
	 * @param eventId
	 * @param subEventId
	 * @return
	 */
	public static boolean relateESubE(Context ctx,long eventId,long subEventId) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		boolean ifSuccessful = false;
		ContentValues cv = new ContentValues();
		cv.put("event_id", eventId);
		cv.put("sub_event_id", subEventId);
		db.beginTransaction();// 开始事务
		try {
			db.insert("event_sub_event_related", null, cv);
			db.setTransactionSuccessful();
			ifSuccessful = true;
		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();
		return ifSuccessful;
	}

}
