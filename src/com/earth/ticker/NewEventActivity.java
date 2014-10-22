package com.earth.ticker;

import com.earth.ticker.fragment.DrawerFragment;
import com.earth.ticker.fragment.NewEventFragment;
import com.earth.ticker.util.SQLOperate;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewEventActivity extends ActionBarActivity {

	private DrawerFragment mDrawerFragment;
	public static String NE = "newEvent";
	private TextView new_event_name;

	
	private TextView new_date_picker;
	private TextView new_time_picker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		//new_time_picker = (EditText) findViewById(R.id.new_time_picker);
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		
		NewEventFragment newEventfragment = new NewEventFragment();
		fragmentTransaction.add(R.id.new_event_fragment, newEventfragment);
		fragmentTransaction.commit();
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.new_event, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.new_event_create: {
			Log.d(NE, "confirm clicked");
			if (eventCreateCheck()) {
				// TODO 生成这个新的事件
				
				//SQLOperate.addEvent(getApplicationContext());
				SQLOperate.addEvent(getApplicationContext(), new_event_name
						.getText().toString(), "unattributed", null, null,
						null, null, null, null, null, null, null);
				this.finish();
			}
			break;

		}
		case R.id.new_event_delete: {

			this.finish();
			break;
		}
		case android.R.id.home: {
			this.finish();
		}
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * at least name should be filled
	 * 
	 * @return true pass the check
	 * 
	 */
	public boolean eventCreateCheck() {
		new_event_name = (EditText) findViewById(R.id.new_event_name);
		Log.d(NE, "if this is running");
		String name = new_event_name.getText().toString();
		Log.d(NE, "name of event-->" + name);
		if (name != null && !name.trim().equals(""))
			return true;

		Toast.makeText(getApplicationContext(), "Event name not null!",
				Toast.LENGTH_SHORT);
		return false;
	}

}
