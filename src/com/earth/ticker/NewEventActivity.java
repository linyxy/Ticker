package com.earth.ticker;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class NewEventActivity extends ActionBarActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.activity_new_event);
	    ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		super.onCreate(savedInstanceState);
		
		
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
				//TODO 生成这个新的事件
			}
			
		}
		if(id == R.id.new_event_delete)
		{
			//TODO 关闭这个窗口返回
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 
	 * @return true 通过检查
	 */
	private boolean eventCreateCheck(){
		//TODO 检查是否需要填写的项目都已经填写
		return true;
	}


}
