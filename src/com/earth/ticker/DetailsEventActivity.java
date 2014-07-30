package com.earth.ticker;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DetailsEventActivity extends Activity{

	private List<String> list=null;	
	private List<String> unfinished_subList=new ArrayList<String>();
	private List<String> finished_subList=new ArrayList<String>();
	private ListView listView;
	private android.app.ActionBar actionBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_layout);
		listView=(ListView)findViewById(R.id.event_list);
		initData();
		SubTasksAdapter adapter=new SubTasksAdapter();
		listView.setAdapter(adapter);
		actionBar=getActionBar();
		actionBar.show();
	}
     
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	public void initData()
	{
		list=new ArrayList<String>();		
		unfinished_subList.add("重写打印样式表");		
		list.addAll(unfinished_subList);
		finished_subList.add("修复手机版边框");
		list.addAll(finished_subList);
	}			

	 private class SubTasksAdapter extends BaseAdapter{  
		  
	        @Override  
	        public int getCount() {  
	            // TODO Auto-generated method stub  
	            return list.size();  
	        }  
	  
	        @Override  
	        public Object getItem(int position) {  
	            // TODO Auto-generated method stub  
	            return list.get(position);  
	        }  
	  
	        @Override  
	        public long getItemId(int position) {  
	            // TODO Auto-generated method stub  
	            return position;  
	        }  
	        @Override  
	        public boolean isEnabled(int position) {  
	            // TODO Auto-generated method stub  	             
	             return super.isEnabled(position);  
	        }  
	        @Override  
	        public View getView(int position, View convertView, ViewGroup parent) {  
	            // TODO Auto-generated method stub  
	            View view=convertView;  
	            if(position<unfinished_subList.size()){  
	                view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.unfinished_subtask_list, null); 	                
	            }else{  
	                view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.finished_subtask_list, null); 
	                TextView text=(TextView)view.findViewById(R.id.finished_subtask_list);  		           
		            text.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
	            } 	            	            
	            return view;  
	        }  
	 }        	     

}
