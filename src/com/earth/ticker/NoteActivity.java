package com.earth.ticker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.earth.ticker.util.SQLOperate;

public class NoteActivity extends Activity {

	TextView noteTime;// this time means last_change_time这里的time为最后修改时间
	TextView noteContent;
	String noteId;
	String originalContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 添加各类控件，并从intent里面得到noteId
		setContentView(R.layout.note_main);
		noteTime = (TextView) findViewById(R.id.note_time);
		noteContent = (TextView) findViewById(R.id.note_edit);
		Intent in = this.getIntent();
		Bundle bundle = in.getExtras();
		noteId = String.valueOf(bundle.get("note_id"));
		// way to come last activity
		// 添加actionbar
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void onStart() {
		super.onStart();
		// 从数据库读出数据并填充页面
		HashMap<String, String> note = (HashMap<String, String>) SQLOperate
				.getNoteById(this, noteId);
		noteContent.setText(note.get("content"));
		originalContent = note.get("content");
		String time = note.get("time");
		// convert time into specific form
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Long a = Long.valueOf(time);
		Date date = new Date(a);
		String d = df.format(date);

		noteTime.setText(d);
	}



	@Override
	protected void onStop() {
		// if (noteContent.getText().toString().trim().equals(""))
		// SQLOperate.deleteNote(this, Integer.parseInt(noteId), false);
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 填充actionBar menu
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.note, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// 给menu各个按钮添加效果
		switch (item.getItemId()) {
		case R.id.action_new:
			noteContent.setText("");
			originalContent = "";
			noteId = String.valueOf(SQLOperate.addNote(this, ""));
			convertTime(String.valueOf(new Date().getTime()));
			break;
		case R.id.action_delete:
			SQLOperate.deleteNote(this, Integer.parseInt(noteId), false);
			this.finish();
			break;
		case R.id.action_finish:
			// 如果新的String和旧的String不一样，就update
			if (!noteContent.getText().toString().trim()
					.equalsIgnoreCase(originalContent.trim())) {
				SQLOperate.updateNote(this, noteId, noteContent.getText()
						.toString());
			}
			this.finish();
			break;
		}
		return super.onOptionsItemSelected(item);

	}

	public static String convertTime(String time) {
		// convert time into specific form
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Long a = Long.valueOf(time);
		Date date = new Date(a);
		String d = df.format(date);
		return d;
	}
}
