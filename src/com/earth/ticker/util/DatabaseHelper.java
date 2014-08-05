package com.earth.ticker.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static String dataBase = "TickerBase";
	public static String DBtag = "database";

	private static final int VERSION = 1;

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public DatabaseHelper(Context context, String name) {
		this(context, name, VERSION);

	}
	public DatabaseHelper(Context context){
		this(context,dataBase,VERSION);
	}

	public DatabaseHelper(Context context, String name, int version) {
		this(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(DBtag, "create a Database");

		// crate table event TODO
		// db.execSQL("CREATE TABLE IF NOT EXISTS events (content TEXT,id TEXT,PRIMARY KEY (id))");

		// create table notes
		db.execSQL("CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY AUTOINCREMENT ,time_stamp INTEGER,"
				+ "content TEXT,last_change_date INTEGER)");
		// create table sub_events
		db.execSQL("CREATE TABLE IF NOT EXISTS sub_events (id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "time_stamp INTEGER,name TEXT,icon TEXT,state INTEGER)");
		// create table event_note_related
		db.execSQL("CREATE TABLE IF NOT EXISTS event_note_related (event_id INTEGER,note_id INTEGER)");
		// create table event_contact_work_related
		db.execSQL("CREATE TABLE IF NOT EXISTS event_contact_work_related (event_id INTEGER,name TEXT"
				+ "contact TEXT,work TEXT)");
		// create table event_sub_event_related
		db.execSQL("CREATE TABLE IF NOT EXISTS event_sub_event_related (event_id INTEGER,sub_event_id INTEGER)");
		Log.d(DBtag, "tables created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(DBtag, "oldVersion->" + oldVersion);
		Log.d(DBtag, "newVerson->" + newVersion);
		Log.d(DBtag, "update a Database");
	}

}
