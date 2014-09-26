package com.earth.ticker;

import com.earth.ticker.fragment.DrawerFragment;
import com.earth.ticker.fragment.NewEventFragment;
import com.earth.ticker.util.SQLOperate;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class NewEventActivity extends ActionBarActivity {

	private DrawerFragment mDrawerFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

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
			if (eventCreateCheck()) {
				// TODO 生成这个新的事件
				SQLOperate.addEvent(getApplicationContext());
			}
			break;

		}
		case R.id.new_event_delete: {
			
			this.finish();
			break;
		}
		case android.R.id.home:{
			this.finish();
		}
		}

		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * at least name should be filled
	 * @return true pass the check
	 * 
	 */
	public boolean eventCreateCheck() {
		TextView new_event_name = (TextView) findViewById(R.id.new_event_name);
		String name = new_event_name.getText().toString();
		if(name != null && !name.trim().equals(""))
			return true;
		return false;
	}


}
