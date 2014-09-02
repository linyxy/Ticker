package com.earth.ticker;

import java.util.ArrayList;

import java.util.List;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
	private String str;//��õ�EditText����
	protected Activity context;
	private  PopupWindow pop;
	private int popHeight=0;
	private int popWidth=0;
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
		//�ļ����б�ļ�����Զ���alertdialog
		mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
			// 
			final AlertDialog ad=new AlertDialog(AddDeleteFolderActivity.this);
			ad.setTitle("�༭����");
			ad.setMessage("�������µ��������");
			ad.setNegativeButton("ȡ��", new OnClickListener() {
 
					@Override
					public void onClick(View v) {
						// 
						ad.dismiss();					
					}
				});
			
			ad.setPositiveButton("ȷ��", new OnClickListener() { 
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
		
		//����ļ��а�ť������Ի���
		add_Button.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// 
				final AlertDialog ad=new AlertDialog(AddDeleteFolderActivity.this);						
				
				ad.setTitle("����ļ���");
				ad.setMessage("�������µ��ļ������");				               
				ad.setNegativeButton("ȡ��", new OnClickListener() {
 
					@Override
					public void onClick(View v) {
						// 
						ad.dismiss();					
					}
				});

				ad.setPositiveButton("ȷ��", new OnClickListener() { 
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
	
	  //�ļ����б���ʾ������
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
	            // 
	        	
	            View view=convertView;  
	            view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.delete_folder_item, null);                          	            
	            TextView text=(TextView)view.findViewById(R.id.delete_folder);
	            text.setText((String)folder_list.get(position));
	            ImageButton button=(ImageButton)view.findViewById(R.id.deletefolder_button);
	            final LayoutInflater mInflater = LayoutInflater.from(view.getContext()); 
	            //item�ؼ�
	            final View currentview=view;
	            
	            //ɾ���ļ��а�ť�����ɾ��ť
	            button.setOnClickListener(new OnClickListener() {	            	
					
					@Override
					public void onClick(View arg0) {
						// 																											   
						
						View viewpop=mInflater.inflate(R.layout.delete_btn, null);
						pop = new PopupWindow(currentview, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, false);
						pop.setContentView(viewpop);
						Button delete_btn = (Button)viewpop.findViewById(R.id.delete_button);				
						// ��Ҫ����һ�´˲�������߿���ʧ 		
						pop.setBackgroundDrawable(new BitmapDrawable()); 		
						//���õ��������ߴ�����ʧ 		 
						pop.setOutsideTouchable(true); 		
						// ���ô˲����ý��㣬�����޷���� 		
						pop.setFocusable(true);	
						
						pop.getContentView().measure(0, 0);						    
					    popHeight = pop.getContentView().getMeasuredHeight();
						popWidth = pop.getContentView().getMeasuredWidth();
					    pop.setBackgroundDrawable(new BitmapDrawable()); 
						
			            //��ÿؼ���λ��
					    int[] location=new int[2];
			            currentview.getLocationInWindow(location);
			            
			            pop.setAnimationStyle(R.style.popwindow_delete_btn_anim_style);
						pop.update();
						pop.showAtLocation(currentview, Gravity.LEFT| Gravity.TOP,
								location[0] + currentview.getWidth(), location[1] + currentview.getHeight() / 2
								- popHeight/2);	
						
						delete_btn.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								final AlertDialog ad=new AlertDialog(AddDeleteFolderActivity.this);						
								
								ad.setTitle("��ʾ");
								ad.setMessage("ȷ��ɾ����ļ�����");	
								ad.setEdit(false);
								ad.setNegativeButton("ȡ��", new OnClickListener() {
				                
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										ad.dismiss();
										pop.dismiss();
									}
								});

								ad.setPositiveButton("ȷ��", new OnClickListener() { 
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub																																															
										ad.dismiss();
										pop.dismiss();
									}
								});
							}
						});
					}
				});
	            
	            return view;  										   	             
	     }     
	}
}
	  					
