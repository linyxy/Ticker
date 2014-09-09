package com.earth.ticker.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLOperate {
	public static String DBtag = "database";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	/**
	 * sample method of SQL operation
	 * 
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
			Log.d(DBtag, cv.toString());
			db.setTransactionSuccessful();// 调用此方法会在执行到endTransaction()
											// 时提交当前事务，如果不调用此方法会回滚事务
			ifSuccessful = true;
			Log.d(DBtag, "success operation");
		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();
		return ifSuccessful;
	}

	/**
	 * basic query method even myself found this method useless 我也觉得这个函数没啥用
	 * 
	 * @param ctx
	 * @param sql
	 * @param selectionArgs
	 * @return
	 */
	public static Cursor basicQuery(Context ctx, String sql,
			String[] selectionArgs) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		Cursor cur = null;
		cur = db.rawQuery(sql, selectionArgs);
		Log.d(DBtag, "getCount of Cursor->" + String.valueOf(cur.getCount()));
		Log.d(DBtag, "success operation in Query");
		db.close();
		return cur;
	}

	/**
	 * Method to get all the folders
	 * 
	 * @param ctx
	 * @return
	 */
	public static ArrayList<String> getAllFolders(Context ctx) {
		ArrayList<String> folders = new ArrayList<String>();
		String sql = "select * from  event_folder_related";
		Cursor result = basicQuery(ctx, sql, null);
		Log.d(DBtag, "getCount of Cursor->" + String.valueOf(result.getCount()));
		while (result.moveToNext()) {
			int column = result.getColumnIndex("name");
			Log.d(DBtag, "name is at column->" + String.valueOf(column));
			String folder = result.getString(column);
			if (!folders.contains(folder)) {
				folders.add(folder);
			}
		}

		return folders;

	}

	public static Cursor getAllNotes(Context ctx) {
		String sql = "select * from  notes ORDER BY last_change_date DESC";
		Cursor result = basicQuery(ctx, sql, null);
		Log.d(DBtag, "getCount of Cursor->" + String.valueOf(result.getCount()));

		return result;

	}

	/**
	 * method to get all eventIdBy the name of folder
	 * 
	 * @param ctx
	 * @param folderName
	 * @return
	 */
	public static ArrayList<Long> getAllEventIdByFolder(Context ctx,
			String folderName) {

		ArrayList<Long> eventIds = new ArrayList<Long>();
		String sql = "select * from  event_folder_related WHERE event_id=?";
		String[] folder = new String[] { folderName };
		Cursor result = basicQuery(ctx, sql, folder);
		while (result.moveToNext()) {
			long event = result.getLong(result.getColumnIndex("event_id"));
			if (!eventIds.contains(event)) {
				eventIds.add(event);
			}
		}

		return eventIds;
	}

	/**
	 * method to get note from db
	 * 
	 * @param ctx
	 * @param noteId
	 * @return map content last_change_time
	 */
	public static Map<String, String> getNoteById(Context ctx, String noteId) {

		String sql = "select * from  notes WHERE id=?";
		String[] id = new String[] { noteId };
		Cursor result = basicQuery(ctx, sql, id);
		HashMap<String, String> note = new HashMap<String, String>();
		while (result.moveToNext()) {
			String content = result.getString(result.getColumnIndex("content"));
			String time = result.getString(result
					.getColumnIndex("last_change_date"));
			note.put("content", content);
			note.put("time", time);
		}

		return note;
	}

	/**
	 * method to add a new event
	 * 
	 * @param ctx
	 * @param name
	 * @param folder
	 * @param icon
	 * @param time_start
	 * @param duration
	 * @param repeat
	 * @param alarm
	 * @param alarm_name
	 * @param alarm_address
	 * @param extra
	 * @param event_status
	 * @return
	 */
	public static int addEvent(Context ctx, String name, String folder,
			String icon, String time_start, String duration, String repeat,
			String alarm, String alarm_name, String alarm_address,
			String extra, String event_status) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();

		ContentValues cv = new ContentValues();
		String id = null;
		int strid = -1;
		cv.put("id", id);
		String time_stamp = String.valueOf(Calendar.getInstance().getTime()
				.getTime());
		// get a time_stamp
		cv.put("time_stamp", time_stamp);
		cv.put("name", name);
		cv.put("icon", icon);
		cv.put("time_start", time_start);
		cv.put("duration", duration);
		cv.put("repeat", repeat);
		cv.put("alarm", alarm);
		cv.put("alarm_name", alarm_name);
		cv.put("alarm_address", alarm_address);
		cv.put("extra", extra);
		cv.put("event_status", event_status);
		db.beginTransaction();// 开始事务
		try {
			Log.d(DBtag, cv.toString());
			db.insert("events", null, cv);
			// get last inserted id
			// 获取自增id
			Cursor cursor = db.rawQuery(
					"select last_insert_rowid() from person", null);
			if (cursor.moveToFirst())
				strid = cursor.getInt(0);
			db.setTransactionSuccessful();

			Log.d(DBtag, "success operation");
		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();
		return strid;
	}
	
	/**
	 * a method that add event and return the id of that event
	 * a way to get new event id
	 * keep notice that 1. name be assign "unsure"
	 * @param ctx
	 * @return eventId
	 */
	public static int addEvent(Context ctx) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();

		ContentValues cv = new ContentValues();
		int strid = -1;
		String id = null;
		cv.put("id", id);
		String time_stamp = String.valueOf(Calendar.getInstance().getTime()
				.getTime());
		// get a time_stamp
		cv.put("time_stamp", time_stamp);
		cv.put("name", "unsure");
		db.beginTransaction();// 开始事务
		try {
			Log.d(DBtag, cv.toString());
			db.insert("events", null, cv);
			db.setTransactionSuccessful();

			Log.d(DBtag, "success operation");
			// get last inserted id
			// 获取自增id
			Cursor cursor = db.rawQuery(
					"select last_insert_rowid() from notes", null);
			if (cursor.moveToFirst())
				strid = cursor.getInt(0);
		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();

		return strid;
	}

	/**
	 * the method to add note into db
	 * 
	 * @param ctx
	 * @param content
	 * @return
	 */
	public static int addNote(Context ctx, String content) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();

		ContentValues cv = new ContentValues();
		int strid = -1;
		String id = null;
		cv.put("id", id);
		String time_stamp = String.valueOf(Calendar.getInstance().getTime()
				.getTime());
		// get a time_stamp
		cv.put("time_stamp", time_stamp);

		cv.put("content", content);
		cv.put("last_change_date", time_stamp);
		db.beginTransaction();// 开始事务
		try {
			Log.d(DBtag, cv.toString());
			db.insert("notes", null, cv);
			db.setTransactionSuccessful();

			Log.d(DBtag, "success operation");
			// get last inserted id
			// 获取自增id
			Cursor cursor = db.rawQuery(
					"select last_insert_rowid() from notes", null);
			if (cursor.moveToFirst())
				strid = cursor.getInt(0);
		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();

		return strid;
	}

	/**
	 * method insert a sub event and relate to event
	 * 
	 * @param ctx
	 * @param name
	 * @param icon
	 * @param related_event_id
	 * @return
	 */
	public static boolean addSubEvent(Context ctx, String name, String icon,
			long related_event_id) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		boolean ifSuccessful = false;
		ContentValues cv = new ContentValues();
		String id = null;
		cv.put("id", id);
		String time_stamp = String.valueOf(Calendar.getInstance().getTime()
				.getTime());
		// get a time_stamp
		cv.put("time_stamp", time_stamp);
		cv.put("name", name);
		cv.put("icon", icon);
		cv.put("state", "1");
		db.beginTransaction();// 开始事务
		try {
			Log.d(DBtag, cv.toString());
			long subEventId = db.insert("sub_events", null, cv);
			relateESubE(ctx, related_event_id, subEventId);
			db.setTransactionSuccessful();
			ifSuccessful = true;
			Log.d(DBtag, "success operation");

		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();
		return ifSuccessful;
	}

	/**
	 * method to add a new contact into an event
	 * 
	 * @param ctx
	 * @param event_id
	 * @param name
	 * @param contact
	 * @param work
	 * @return
	 */
	public static boolean addContact(Context ctx, long event_id, String name,
			String contact, String work) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		boolean ifSuccessful = false;
		ContentValues cv = new ContentValues();
		cv.put("event_id", String.valueOf(event_id));
		cv.put("name", name);
		cv.put("contact", contact);
		cv.put("work", work);
		db.beginTransaction();// 开始事务
		try {
			Log.d(DBtag, cv.toString());
			db.insert("event_contact_work_related", null, cv);
			db.setTransactionSuccessful();
			ifSuccessful = true;
			Log.d(DBtag, "success operation");
		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();
		return ifSuccessful;
	}

	/**
	 * 增加一个文件夹 method to add one folder
	 * 
	 * @param ctx
	 * @param name
	 * @return
	 */
	public static boolean addFolder(Context ctx, String name) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		boolean ifSuccessful = false;
		ContentValues cv = new ContentValues();
		cv.put("name", String.valueOf(name));
		db.beginTransaction();// 开始事务
		try {
			Log.d(DBtag, cv.toString());
			db.insert("event_folder_related", null, cv);
			db.setTransactionSuccessful();
			ifSuccessful = true;
			Log.d(DBtag, "success operation");
		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();
		return ifSuccessful;
	}

	/**
	 * method to add event into folder
	 * 
	 * @param ctx
	 * @param event_id
	 * @param name
	 * @return
	 */
	public static boolean relateEFolder(Context ctx, long event_id, String name) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		boolean ifSuccessful = false;
		ContentValues cv = new ContentValues();
		cv.put("event_id", String.valueOf(event_id));
		cv.put("name", name);
		db.beginTransaction();// 开始事务
		try {
			Log.d(DBtag, cv.toString());
			db.insert("event_contact_work_related", null, cv);
			db.setTransactionSuccessful();
			ifSuccessful = true;
			Log.d(DBtag, "success operation");
		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();
		return ifSuccessful;
	}

	/**
	 * method insert related id with sub related one
	 * 
	 * @param ctx
	 * @param eventId
	 * @param subEventId
	 * @return
	 */
	public static boolean relateESubE(Context ctx, long eventId, long subEventId) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		boolean ifSuccessful = false;
		ContentValues cv = new ContentValues();
		cv.put("event_id", eventId);
		cv.put("sub_event_id", subEventId);
		db.beginTransaction();// 开始事务
		try {
			Log.d(DBtag, cv.toString());
			db.insert("event_sub_event_related", null, cv);
			db.setTransactionSuccessful();
			ifSuccessful = true;
			Log.d(DBtag, "success operation");

		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();
		return ifSuccessful;
	}

	/**
	 * method to relate event with note
	 * 
	 * @param ctx
	 * @param eventId
	 * @param noteId
	 * @return
	 */
	public static boolean relateENote(Context ctx, long eventId, long noteId) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		boolean ifSuccessful = false;
		ContentValues cv = new ContentValues();
		cv.put("event_id", eventId);
		cv.put("note_id", noteId);
		db.beginTransaction();// 开始事务
		try {
			Log.d(DBtag, cv.toString());
			db.insert("event_note_related", null, cv);
			db.setTransactionSuccessful();
			ifSuccessful = true;
			Log.d(DBtag, "success operation");

		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();
		return ifSuccessful;
	}

	/**
	 * method to delete an event
	 * 
	 * @param ctx
	 * @param eventId
	 * @return
	 */
	public static boolean deleteEvent(Context ctx, long eventId) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		boolean ifSuccessful = false;
		db.beginTransaction();// 开始事务
		try {
			String whereClause = "event_id=? ";// 删除的条件
			String[] whereArgs = { String.valueOf(eventId) };// 删除的条件参数
			db.delete("events", whereClause, whereArgs);// 执行删除
			db.setTransactionSuccessful();
			Log.d(DBtag, "success operation");

			ifSuccessful = true;
		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();
		return ifSuccessful;
	}

	/**
	 * method to delete a folder action would not delete related activity but
	 * activity are not moved away they may lost some where or bug would occurr
	 * 
	 * @param ctx
	 * @param eventId
	 * @param name
	 * @return
	 */
	public static boolean deleteFolder(Context ctx, String name) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		boolean ifSuccessful = false;
		db.beginTransaction();// 开始事务
		try {
			String whereClause = " name=?";// 删除的条件
			String[] whereArgs = { name };// 删除的条件参数
			db.delete("event_folder_related", whereClause, whereArgs);// 执行删除
			db.setTransactionSuccessful();
			Log.d(DBtag, "success operation");
			ifSuccessful = true;
		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();
		return ifSuccessful;
	}

	/**
	 * delete the folder with its related events
	 * 
	 * @param ctx
	 * @param name
	 * @return
	 */
	public static boolean deleteFolderREvent(Context ctx, String name) {
		boolean ifSuccessfulDeleteEvents = false;
		boolean ifSuccessfulDeleteFolder = false;
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		Cursor targetEvents = null;
		db.beginTransaction();// 开始事务
		try {
			String[] col = { "event_id" };
			String[] args = { name };
			targetEvents = db.query("event_folder_related", col, "name", args,
					null, null, null);
			db.setTransactionSuccessful();
			Log.d(DBtag, "success operation");
		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();

		// start to delete all the events in the folder
		if (targetEvents != null) {
			ifSuccessfulDeleteEvents = true;
			if (targetEvents.moveToFirst()) {
				while (!targetEvents.isLast()) {
					long event_id = targetEvents.getLong(targetEvents
							.getColumnIndex("event_id"));
					boolean delete = deleteEvent(ctx, event_id);
					if (!delete) {
						ifSuccessfulDeleteEvents = false;
						Log.d(DBtag, "problems occurs when deleting problem");
					}
				}
			}
			targetEvents.close();
		}
		// delete the folder
		ifSuccessfulDeleteFolder = deleteFolder(ctx, name);

		return ifSuccessfulDeleteEvents && ifSuccessfulDeleteFolder;
	}

	/**
	 * delete the relationship for event and note
	 * 
	 * @param ctx
	 * @param eventId
	 * @param noteId
	 * @return
	 */
	public static boolean deleteNoteRelate(Context ctx, long eventId,
			long noteId) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		boolean ifSuccessful = false;
		db.beginTransaction();// 开始事务
		try {

			String whereClause = "event_id=? AND note_id=?";// 删除的条件
			String[] whereArgs = { String.valueOf(eventId),
					String.valueOf(noteId) };// 删除的条件参数
			db.delete("event_sub_event_related", whereClause, whereArgs);// 执行删除
			db.setTransactionSuccessful();
			Log.d(DBtag, "success operation");

			ifSuccessful = true;
		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();
		return ifSuccessful;
	}

	/**
	 * method to delete a specific sub event
	 * 
	 * @param ctx
	 * @param subEventId
	 * @return
	 */
	public static boolean deleteSubEvent(Context ctx, long subEventId) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		boolean ifSuccessful = false;
		db.beginTransaction();// 开始事务
		try {
			String whereClause = "sub_event_id=?";// 删除的条件
			String whereClause2 = "id=?";
			String target = String.valueOf(subEventId);
			String[] whereArgs = { target };// 删除的条件参数
			db.delete("event_sub_event_related", whereClause, whereArgs);// 执行删除
			db.delete("sub_events", whereClause2, whereArgs);
			db.setTransactionSuccessful();
			Log.d(DBtag, "success operation");

			ifSuccessful = true;
		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();
		return ifSuccessful;
	}

	/**
	 * method to delete a contact
	 * 
	 * @param ctx
	 * @param eventId
	 * @param name
	 * @return
	 */
	public static boolean deleteContact(Context ctx, long eventId, String name) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		boolean ifSuccessful = false;
		db.beginTransaction();// 开始事务
		try {
			String whereClause = "event_id=? AND name=?";// 删除的条件
			String[] whereArgs = { String.valueOf(eventId), name };// 删除的条件参数
			db.delete("event_contact_work_related", whereClause, whereArgs);// 执行删除
			db.setTransactionSuccessful();
			Log.d(DBtag, "success operation");

			ifSuccessful = true;
		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();
		return ifSuccessful;
	}

	/**
	 * method to delete a specific note
	 * 
	 * @param ctx
	 * @param note_id
	 * @return
	 */
	public static boolean deleteNote(Context ctx, long note_id,
			boolean ifRelatedEvent) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		boolean ifSuccessful = false;
		db.beginTransaction();// 开始事务
		try {
			String whereClause = "sub_event_id=?";// 删除的条件
			String whereClause2 = "id=?";
			String target = String.valueOf(note_id);
			String[] whereArgs = { target };// 删除的条件参数
			db.delete("notes", whereClause2, whereArgs);// 执行删除
			if (ifRelatedEvent) {
				db.delete("event_note_related", whereClause, whereArgs);
			}
			db.setTransactionSuccessful();
			Log.d(DBtag, "success operation");
			ifSuccessful = true;
		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();
		return ifSuccessful;
	}

	/**
	 * sample method to update
	 * 
	 * @param ctx
	 * @param table
	 * @param whereClause
	 * @param Args
	 * @param column
	 * @param content
	 * @return
	 */
	public static boolean sampleUpdate(Context ctx, String table,
			String whereClause, String Args, String column, String content) {
		DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		boolean ifSuccessful = false;
		ContentValues cv = new ContentValues();// 实例化ContentValues
		cv.put(column, content);// 添加要更改的字段及内容
		whereClause = whereClause + "=?";// 修改条件
		String[] whereArgs = { Args };// 修改条件的参数

		db.beginTransaction();// 开始事务
		try {
			db.update(table, cv, whereClause, whereArgs);// 执行修改
			db.setTransactionSuccessful();
			ifSuccessful = true;
			Log.d(DBtag, "success operation");
		} finally {
			db.endTransaction();// 由事务的标志决定是提交事务，还是回滚事务
		}
		db.close();
		return ifSuccessful;
	}

	/**
	 * method to update a note automatically update last_change_date
	 * 
	 * @param ctx
	 * @param note_id
	 * @param content
	 * @return
	 */
	public static boolean updateNote(Context ctx, String note_id, String content) {
		String id = note_id;
		boolean upContent = false;
		boolean upTimeStamp = false;
		upContent = sampleUpdate(ctx, "notes", "id", id, "content", content);
		Date date = new Date();
		String time_stamp = String.valueOf(date.getTime());
		// get a time_stamp
		upTimeStamp = sampleUpdate(ctx, "notes", "id", id, "last_change_date",
				time_stamp);
		return upContent && upTimeStamp;
	}

	/**
	 * method to update subEvent
	 * 
	 * @param ctx
	 * @param subevent_id
	 * @param name
	 * @param state
	 * @param icon
	 * @return
	 */
	public static boolean updateSubEvent(Context ctx, long subevent_id,
			String name, String state, String icon) {
		String id = String.valueOf(subevent_id);
		boolean upName = sampleUpdate(ctx, "sub_events", "id", id, "name", name);
		boolean upIcon = sampleUpdate(ctx, "sub_events", "id", id, "icon", icon);
		boolean upState = sampleUpdate(ctx, "sub_events", "id", id, "state",
				state);
		return upName && upIcon && upState;
	}

	/**
	 * method to change event to another folder
	 * 
	 * @param ctx
	 * @param event_id
	 * @param newFolder
	 * @return
	 */
	public static boolean updateFolder(Context ctx, long event_id,
			String newFolder) {
		String id = String.valueOf(event_id);
		boolean upNew = sampleUpdate(ctx, "event_folder_related", "id", id,
				"name", newFolder);
		return upNew;
	}

}
