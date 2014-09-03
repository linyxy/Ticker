package com.earth.ticker;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.earth.ticker.fragment.DrawerFragment;


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
		mListView.setOnItemClickListener(new listViewListener());
		
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
	class listViewListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Log.d("drawer", "the list have been clicked");
			switch(position)
			{
			case 3:
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), NoteListActivity.class);
			startActivity(intent);
			}
		}
		
	}

}
