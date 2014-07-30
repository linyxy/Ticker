package com.earth.ticker;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class NewEventActivity extends ActionBarActivity {
	
	private DrawerFragment mDrawerFragment;
		
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{	
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_new_event);
	    ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);	    	    	    
		
		
		FragmentManager fragmentManager = getFragmentManager(); 
	    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	    
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
		int id = item.getItemId();
		if(id == R.id.new_event_create)
		{
			if(eventCreateCheck())
			{
				//TODO 
			}			
		}
		if(id == R.id.new_event_delete)
		{
			//TODO 
		}		
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 
	 * @return true 
	 */
	private boolean eventCreateCheck(){
		//TODO 
		return true;
	}


}
