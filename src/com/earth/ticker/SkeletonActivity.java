package com.earth.ticker;

import com.earth.time.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class SkeletonActivity extends  FragmentActivity 
{

	private DrawerLayout mDrawerLayout;
	private ListView mListView;
	private String[] mDrawerTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mDrawerTitle=getResources().getStringArray(R.array.left_menu);
		mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
		mListView=(ListView)findViewById(R.id.left_drawer);
		
		mListView.setAdapter(new ArrayAdapter<String>(this,R.layout.draw_list_item,mDrawerTitle));
		
		
		if(findViewById(R.id.main_fragment)!=null)
        {
        	if(savedInstanceState!=null)
        	{
        		return;
        	}
        DrawerFragment drawerFragment=new DrawerFragment();
        drawerFragment.setArguments(getIntent().getExtras());
        
        getSupportFragmentManager().beginTransaction()
        .add(R.id.main_fragment, drawerFragment).commit();
        }
	 
	}

}
