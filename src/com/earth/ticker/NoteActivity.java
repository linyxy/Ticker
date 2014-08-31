package com.earth.ticker;

import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.earth.ticker.util.SQLOperate;

public class NoteActivity extends Activity {

	TextView noteTime;// this time means last_change_time这里的time为最后修改时间
	TextView noteContent;
	String noteId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_main);
		noteTime = (TextView) findViewById(R.id.note_time);
		noteContent = (TextView) findViewById(R.id.note_edit);
		Intent in = this.getIntent();
		Bundle bundle = in.getExtras();
		noteId = (String) bundle.get("note_id");

	}

	@Override
	protected void onStart() {
		super.onStart();
		HashMap<String, String> note = (HashMap<String, String>) SQLOperate
				.getNoteById(this, noteId);
		noteContent.setText(note.get("content"));
		String time = note.get("time");
		
		// TODO convert time into specific form
		noteTime.setText(time);
	}

	@Override
	protected void onStop() {
		SQLOperate.updateNote(this,  noteId,(String) noteContent.getText());
		super.onStop();
	}

}
