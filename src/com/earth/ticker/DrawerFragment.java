package com.earth.ticker;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
public  class DrawerFragment extends Fragment implements ActionBar.TabListener
{
	private SectionPagerAdapter msectionPagerAdapter;	
	private View mcustomview;
	private ImageView mscrolltime;
	private ImageView mscrollfile;
	private Button add_Button;
	ViewPager mViewPager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);				
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_viewpager,container, false);
		
		final android.app.ActionBar actionBar=getActivity().getActionBar();
		mcustomview=getActivity().getLayoutInflater().inflate(R.layout.custom_tab_view, null);
		add_Button=(Button)mcustomview.findViewById(R.id.tab_add);
		add_Button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(getActivity(),NewEventActivity.class);
				startActivity(intent);	
			}
		});
		
		actionBar.setCustomView(mcustomview);		
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		
		mscrolltime=(ImageView)mcustomview.findViewById(R.id.tab_scroll_time);
        mscrollfile=(ImageView)mcustomview.findViewById(R.id.tab_scroll_file);
        msectionPagerAdapter=new SectionPagerAdapter(getActivity().getSupportFragmentManager());
        
        mViewPager=(ViewPager)view.findViewById(R.id.pager);
        mViewPager.setAdapter(msectionPagerAdapter);
        
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
        	@Override
			public void onPageSelected(int position) {
				setCurrentScroll(position);				
			}
		});
      
    	return view;
    	}
    	 	
	  private void setCurrentScroll(int selection) 
  	    {
  		// TODO Auto-generated method stub
  		if (mscrolltime != null && mscrollfile != null ) 
  		  {
  	          mscrolltime.setVisibility(selection == 0 ? View.VISIBLE : View.INVISIBLE);
  	          mscrollfile.setVisibility(selection == 1 ? View.VISIBLE : View.INVISIBLE); 	        	
  		   }
  	    }	
	  
	@Override
	public void onResume() 
	{
		// TODO Auto-generated method stub
		super.onResume();		
		setCurrentScroll(mViewPager.getCurrentItem());
	}
	
	
	public class SectionPagerAdapter extends FragmentPagerAdapter
	{

		public SectionPagerAdapter(android.support.v4.app.FragmentManager fragmentManager) 
		{
			super(fragmentManager);
			// TODO Auto-generated constructor stub			
		}
		

		@Override
		public Fragment getItem(int position) 
		{
			// TODO Auto-generated method stub
			Fragment fragment = null;
			switch(position)
			{
			case 0:
				fragment=new TimeFragment();
				break;
			case 1:
				fragment=new FileFragment();
				break;
			default:
				break;
			}				
			return fragment;
		}

		@Override
		public int getCount() 
		{
			// TODO Auto-generated method stub
			return 2;
		}
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getActivity().getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	

	public void onTabReselected(android.support.v7.app.ActionBar.Tab arg0,
			android.support.v4.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(android.support.v7.app.ActionBar.Tab arg0,
			android.support.v4.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(android.support.v7.app.ActionBar.Tab arg0,
			android.support.v4.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}