package com.earth.ticker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.earth.ticker.util.SQLOperate;

public class NoteListActivity extends Activity {

	String[] con = { "content", "date" };
	int[] ids = { R.id.note_item_content, R.id.note_item_date };
	List<Map<String, Object>> listData=new ArrayList<Map<String, Object>>();
	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_notes);
		listView=(ListView)findViewById(R.id.notes_list);
		listData = getData();
		Log.d("note","data of list->"+ listData.toString());
		SimpleAdapter adapter = new SimpleAdapter(this, listData,
				R.layout.note_list_item, con, ids);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {				
				Map<String, Object> item = listData.get(position);
				String noteId = (String) item.get("note_id");
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), NoteActivity.class);
				intent.putExtra("note_id", noteId);
				startActivity(intent);
			}
			
		});
	}

	@Override
	protected void onStart() {	
		super.onStart();
	}

	protected List<Map<String, Object>> getData() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Cursor result = (Cursor) SQLOperate.getAllNotes(this);
		result.moveToFirst();
		while (result.moveToNext()) {
			Map<String, Object> note = new HashMap<String, Object>();
			note.put("note_id", result.getInt(result.getColumnIndex("id")));

			note.put("content",
					result.getString(result.getColumnIndex("content")));
			Log.d("note",
					"content of note->"
							+ result.getString(result.getColumnIndex("content")));

			String time = result.getString(result
					.getColumnIndex("last_change_date"));
			Log.d("note",
					"time of note->"
							+ result.getString(result
									.getColumnIndex("last_change_date")));
			
			long a = Long.valueOf(time);
			// convert time
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date(a);
			String d = df.format(date);
			note.put("date", d);

			list.add(note);
		}

		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.notes, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_new:
			// generate a new Id and then open it
			int noteId;
			Intent intent = new Intent(this, NoteActivity.class);
			noteId = SQLOperate.addNote(this, "");
			intent.putExtra("note_id", noteId);
			startActivity(intent);
		}

		return super.onOptionsItemSelected(item);
	}	

}
