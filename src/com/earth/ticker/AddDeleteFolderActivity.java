package com.earth.ticker;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.earth.ticker.assist.AlertDialog;
import com.earth.ticker.assist.FirstOpenSample;
import com.earth.ticker.util.SQLOperate;

public class AddDeleteFolderActivity extends Activity{
	
	private ImageButton add_Button;
	private ListView mlistView;
	private List<String> folder_list=new ArrayList<String>();
	private String str;//获得的EditText内容
	protected Activity context;
	private  PopupWindow pop;
	private int pos=0;
	@Override
	public void onCreate(Bundle saveInstanceState)
	{
		super.onCreate(saveInstanceState);
		setContentView(R.layout.adddelete_folder_layout);
		
		add_Button=(ImageButton)findViewById(R.id.addfolder_button);
		mlistView=(ListView)findViewById(R.id.deletefolder_list);
		
		/*
		 * used here to store one line into database
		 */
		FirstOpenSample.addSomeFolders(this);
		

		FolderListAdapter adapter=new FolderListAdapter();
		//文件夹列表的监听，弹出自定义alertdialog
		mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
			// 
			final AlertDialog ad=new AlertDialog(AddDeleteFolderActivity.this);
			ad.setTitle("编辑任务");
			ad.setMessage("请输入新的任务名称");
			ad.setNegativeButton("取消", new OnClickListener() {
 
					@Override
					public void onClick(View v) {
						// 
						ad.dismiss();					
					}
				});
			
			ad.setPositiveButton("确定", new OnClickListener() { 
				@Override
				public void onClick(View v) {
					// 
					
					/*if(ad.getEditText()!="")
					{
					 str=ad.getEditText();
					 Toast.makeText(getApplication(),str, Toast.LENGTH_LONG).show();
					}else
					{*/
					ad.dismiss();
					//}
				}
			});
			
			}
			});
		
		
		mlistView.setAdapter(adapter);
		
		//添加文件夹按钮监听，弹出对话框
		add_Button.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				
				final AlertDialog ad=new AlertDialog(AddDeleteFolderActivity.this);						
				
				ad.setTitle("添加文件夹");
				ad.setMessage("请输入新的文件夹名称");				               
				ad.setNegativeButton("取消", new OnClickListener() {
 
					@Override
					public void onClick(View v) {
						// 
						ad.dismiss();					
					}
				});

				ad.setPositiveButton("确定", new OnClickListener() { 
					@Override
					public void onClick(View v) {
						// 
						
						/*if(ad.getEditText()!="")
						{
						 str=ad.getEditText();
						 //
						}else
						{*/
						ad.dismiss();
						//}
					}
				});
			}
		});
		
	}
	
	/*
	 * read the lines from database into folder_list
	 * 
	 */
	  @Override
	protected void onStart() {
		  folder_list = SQLOperate.getAllFolders(this);
		  Log.d(SQLOperate.DBtag, folder_list.toString());
		super.onStart();
	}

	//文件夹列表显示适配器
	  class FolderListAdapter extends BaseAdapter{  
		  
	        @Override  
	        public int getCount() {  
	            //   
	            return folder_list.size();  
	        }  
	  
	        @Override  
	        public Object getItem(int position) {  
	            //   
	            return folder_list.get(position);  
	        }  
	  
	        @Override  
	        public long getItemId(int position) {  
	            //   
	            return position;  
	        }  
	        
	        @Override
	        public boolean areAllItemsEnabled()
	        {
	            // all items are separator
	            return true;
	        }

	        @Override
	        public boolean isEnabled(int position)
	        {
	            // all items are separator
	            return true; 
	        }
	        
	        @Override  
	        public View getView(final int position, View convertView, ViewGroup parent) {  
	          
	        	
	            View view=convertView;  
	            view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.delete_folder_item, null);                          	            
	            TextView text=(TextView)view.findViewById(R.id.delete_folder);
	            text.setText((String)folder_list.get(position));
	            ImageButton button=(ImageButton)view.findViewById(R.id.deletefolder_button);
	            final Button deleteButton=(Button)view.findViewById(R.id.delete_button);
	            //删除文件夹按钮监听，弹出删除按钮
	            button.setOnClickListener(new View.OnClickListener() {	            	
					@Override
					public void onClick(View arg0) {
						// 
						
						if(deleteButton!=null)
						{
						
							if(deleteButton.getVisibility()==View.VISIBLE)
							{
								deleteButton.setVisibility(View.GONE);								
								Animation animation = AnimationUtils.loadAnimation(
										AddDeleteFolderActivity.this, R.anim.push_right_out);  			              
								deleteButton.startAnimation(animation);																
							}else
							{
								deleteButton.setVisibility(View.VISIBLE);					
								Animation animation = AnimationUtils.loadAnimation(
										AddDeleteFolderActivity.this, R.anim.push_right_in);  			              
								deleteButton.startAnimation(animation); 								
							}
							
							//删除按钮监听，弹出删除确认对话框
							deleteButton.setOnClickListener(new View.OnClickListener() {
								
								@Override
								public void onClick(View arg0) {
									final AlertDialog ad=new AlertDialog(AddDeleteFolderActivity.this);						
									
									ad.setTitle("提示");
									ad.setMessage("确定删除该文件夹吗");	
									ad.setEdit(false);
									ad.setNegativeButton("取消", new OnClickListener() {
					                
										@Override
										public void onClick(View v) {
											// 
											ad.dismiss();					
										}
									});

									ad.setPositiveButton("确定", new OnClickListener() { 
										@Override
										public void onClick(View v) {
											// 
											
											deleteButton.setVisibility(View.GONE);																				
											Animation animation = AnimationUtils.loadAnimation(
													AddDeleteFolderActivity.this, R.anim.push_right_out);  			              
											deleteButton.startAnimation(animation);		
											ad.dismiss();
											
										}
									});
								}
							});
						}						
							
					}
				});
	            
	            return view;  										   	             
	     }     
	}
}
	  					
