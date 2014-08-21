package com.earth.ticker.fragment;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar.LayoutParams;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.earth.ticker.AddDeleteFolderActivity;
import com.earth.ticker.DetailsEventActivity;
import com.earth.ticker.R;
import com.earth.ticker.assist.TasksInfo;


public class FolderFragment extends Fragment{
	
	private int height=0;
	private int GROUP_HEIGHT=0;
	private int CHILD_HEIGHT=52;
	private  PopupWindow pop;
	String[] groupFrom={"folderName","tasksCount"};
	int[] groupTo={R.id.folderName,R.id.tasksCount};
	String[] childFrom={"taskImage","taskName","taskSign"};
	int[] childTo={R.id.taskImage,R.id.taskName,R.id.taskSign};
	ArrayList<HashMap<String,Object>> groupData=null;
	ArrayList<ArrayList<HashMap<String,Object>>> childData=null; 		
		
	ExpandableListView exListView=null;	
	MyExpandableListViewAdapter adapter=null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_list_expandable, container,false);				
				
		groupData=new ArrayList<HashMap<String,Object>>();
		childData=new ArrayList<ArrayList<HashMap<String,Object>>> ();
		
		//创建groupLocation对象
		
		TasksInfo task1=new TasksInfo("和小伙伴们打Dota","玩LOL的都是小学生",R.drawable.sample_icon,"二次元");
		TasksInfo task2=new TasksInfo("和小伙伴们打Dota","玩LOL的都是小学生",R.drawable.sample_icon,"模板TODO");
		TasksInfo task3=new TasksInfo("和小伙伴们打Dota","玩LOL的都是小学生",R.drawable.sample_icon,"三次元");
		TasksInfo task4=new TasksInfo("和小伙伴们打Dota","玩LOL的都是小学生",R.drawable.sample_icon,"三次元");
		TasksInfo task5=new TasksInfo("和小伙伴们打Dota","玩LOL的都是小学生",R.drawable.sample_icon,"模板TODO");
		
		addUser(task1);
		addUser(task2);
		addUser(task3);
		addUser(task4);
		addUser(task5);
		addUser(task1);
		addUser(task2);
		addUser(task3);
		addUser(task4);
		addUser(task5);
		addUser(task3);
		addUser(task4);
		addUser(task5);
		addUser(task3);
		addUser(task4);
		addUser(task5);
								  		  
		//不能用HashMap的实参赋给Map形参，只能new一个HashMap对象赋给Map的引用！
				
		exListView=(ExpandableListView)view.findViewById(R.id.expandable_list);
		adapter=new MyExpandableListViewAdapter(getActivity(),groupData,R.layout.layout_list_folder,groupFrom,groupTo,childData,R.layout.layout_list_task,childFrom,childTo );
		exListView.setAdapter(adapter);
	
		//子列表点击事件监听，跳转到具体任务界面
		exListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// 				
				Intent intent=new Intent();
				intent.setClass(getActivity(), DetailsEventActivity.class);
				getActivity().startActivity(intent);
				return false;
			}
		});	
		
		//获得folder item的实际高度							
		View exGroupListItem= exListView.getExpandableListAdapter().getGroupView(0,false,null,exListView );
	    exGroupListItem.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		exGroupListItem.measure(0, 0);
		GROUP_HEIGHT=exGroupListItem.getMeasuredHeight();
		
		//获得task item的实际高度
		View exChildListItem=exListView.getExpandableListAdapter().getChildView(0, 0,false,null,exListView);
		exChildListItem.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		exChildListItem.measure(0, 0);
		CHILD_HEIGHT=exChildListItem.getMeasuredHeight();
		
		//设置folder viewgroup的高度
		ViewGroup.LayoutParams  params= exListView.getLayoutParams();
		height=groupData.size()*GROUP_HEIGHT-2;
		params.height=height;
		exListView.setLayoutParams(params);
		
		for(int i=0; i<groupData.size() ;i++){
			groupData.get(i).put("location",i);
		}
		
		exListView.setOnItemLongClickListener(new LongItemClick());
		
		return view;
	}
	
	//长按父列表，显示popupwindow
	 class LongItemClick implements OnItemLongClickListener
	{		
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			int position=arg2;
			// 
			for(int i=0;i<groupData.size();i++)
			{
				if(position==(Integer)groupData.get(i).get("location"))
				{
					//Toast.makeText(getActivity(), "长按点击", Toast.LENGTH_LONG).show();
					showPopUp(arg1,arg2,i); 
                    
				}
			}
			return false;
		}
	}
	 
	 //popupwindow显示函数，点击跳转到添加，删除文件夹界面
	 private void showPopUp(View view,int position,int i)
	 {
		 
		    Vibrator vibrator =(Vibrator)getActivity().getSystemService(Service.VIBRATOR_SERVICE);  		  		   		   
		    vibrator.vibrate(30);
		    //popUpWindow布局		 
			LayoutInflater inflater1 = LayoutInflater.from(getActivity()); 
			View viewpop=inflater1.inflate(R.layout.popupwindow_layout, null);
			pop = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, false);
			pop.setContentView(viewpop);
			Button btn = (Button)viewpop.findViewById(R.id.popwindow_Button);				
			// 需要设置一下此参数，点击外边可消失 		
			pop.setBackgroundDrawable(new BitmapDrawable()); 		
			//设置点击窗口外边窗口消失 		 
			pop.setOutsideTouchable(true); 		
			// 设置此参数获得焦点，否则无法点击 		
			pop.setFocusable(true);	
			//获得屏幕宽度
			WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE); 
            int width = wm.getDefaultDisplay().getWidth();
            //获得控件的位置
            int[] location=new int[2];
            view.getLocationInWindow(location);
            
		    pop.showAtLocation(view, Gravity.LEFT|Gravity.TOP,width/3, 
		    location[1]);
		    
		    btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// 
					pop.dismiss();
					Intent intent=new Intent();
					intent.setClass(getActivity(),AddDeleteFolderActivity.class);
					getActivity().startActivity(intent);
				}
			});
				
			
	 }
	
    //向列表中添加文件夹，这个函数暂时写在这，后台完成后，应该写到添加，删除文件夹界面中
	protected void addUser(TasksInfo task)
	{
		int i;
		//判断加入的文件夹是否已经存在
		for(i=0; i< groupData.size(); i++){
			if(groupData.get(i).get("folderName").toString().equals(task.folderInfo)){
				break;
			}
		}
		//如果加入的文件夹不存在，则加入并设置tasksCount为0；
		if(i>=groupData.size()){
			HashMap<String,Object> map=new HashMap<String,Object>();
			
			map.put("folderName",task.folderInfo );
			map.put("tasksCount", 0);
			groupData.add(map);
			
			ArrayList<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
			childData.add(list);
		}
		//添加任务信息到任务列表
		HashMap<String,Object> tasksData=new HashMap<String,Object>();
		tasksData.put("taskImage",task.taskImage );
		tasksData.put("taskName", task.taskName);
		tasksData.put("taskSign", task.taskSign);
		childData.get(i).add(tasksData);
		//添加任务数目
		Integer count=(Integer)groupData.get(i).get("tasksCount")+1;
		groupData.get(i).put("tasksCount", count);    
		groupData.get(i).put("expanded", false);
		
	}	

	/**
	 * ExpandableListView对应的适配器	
	 */
	public class MyExpandableListViewAdapter extends BaseExpandableListAdapter{
		
		private Context context=null;
		
		private ArrayList<HashMap<String,Object>> groupData=null;
		int groupLayout=0;
		private String[] groupFrom=null;
		private int[] groupTo=null;
		
		private ArrayList<ArrayList<HashMap<String,Object>>> childData=null;
		int childLayout=0;
		private String[] childFrom=null;
		private int[] childTo=null;
		
		
		

		public MyExpandableListViewAdapter(Context context, ArrayList<HashMap<String, Object>> groupData,
				int groupLayout, String[] groupFrom, int[] groupTo,
				ArrayList<ArrayList<HashMap<String, Object>>> childData, int childLayout,
				String[] childFrom, int[] childTo) {
			super();
			this.context = context;
			this.groupData = groupData;
			this.groupLayout = groupLayout;
			this.groupFrom = groupFrom;
			this.groupTo = groupTo;
			this.childData = childData;
			this.childLayout = childLayout;
			this.childFrom = childFrom;
			this.childTo = childTo;
			
		}

		@Override
		public Object getChild(int arg0, int arg1) {
			// 
			return null;
		}

		/**
		 * position与id一样，都是从0开始计数的，		 * 这里返回的id也是从0开始计数的
		 */
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			long id=0;
			for(int i=0;i<groupPosition; i++){
				id+=childData.size();
			}
			id+=childPosition;  
			return id;           
		}
		
		/**ChildViewHolder内部类**/
	    class ChildViewTask{
			ImageButton taskImage=null;
			TextView taskName=null;
			TextView taskSign=null;
		}
	    	   	    	    
		@Override
		public View getChildView(int groupPosition, int childPosition,boolean isLastChild,View convertView, ViewGroup parent) {
			// 
			/**
			 * 这里isLastChild目前没用到，如果出现异常再说
			 */
			ChildViewTask task=null;
			if(convertView==null){
				convertView= LayoutInflater.from(context).inflate(childLayout,null);
				                             //感觉这里需要把root设置成ViewGroup 对象
				/**
				 * ERROR!!这里不能把null换成parent，否则会出现异常退出，原因不太确定，可能是inflate方法获得的这个item的View
				 * 并不属于某个控件组，所以使用默认值null即可
				 */
				task=new ChildViewTask();
				task.taskImage=(ImageButton)convertView.findViewById(R.id.taskImage);
				task.taskName=(TextView)convertView.findViewById(R.id.taskName);
				task.taskSign=(TextView)convertView.findViewById(R.id.taskSign);
				convertView.setTag(task);
			}
			else{
				task=(ChildViewTask)convertView.getTag();
			}
			
			task.taskImage.setBackgroundResource((Integer)(childData.get(groupPosition).get(childPosition).get("taskImage")));
			task.taskName.setText(childData.get(groupPosition).get(childPosition).get("taskName").toString());
			task.taskSign.setText(childData.get(groupPosition).get(childPosition).get("taskSign").toString());
			//task.taskImage.setOnClickListener(new ImageClickListener(task));

			
			return convertView;
		}

		
		@Override
		public int getChildrenCount(int groupPosition) {
			// 
			return childData.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			// 
			return null;
		}

		@Override
		public int getGroupCount() {
			// 
			return groupData.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			// 
			return groupPosition;
		}

		
		class GroupViewFolder{
			//ImageView folderImage=null;
			TextView folderName=null;
			TextView tasksCount=null;
		}
		
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// 
			GroupViewFolder folder=null;
			if(convertView==null){
				convertView=LayoutInflater.from(context).inflate(groupLayout, null);
				folder=new GroupViewFolder();
				//holder.folderImage=(ImageView)convertView.findViewById(groupTo[0]);
				folder.folderName=(TextView)convertView.findViewById(R.id.folderName);
				folder.tasksCount=(TextView)convertView.findViewById(R.id.tasksCount);
				convertView.setTag(folder);
			}
			else{
				folder=(GroupViewFolder)convertView.getTag();
			} 
						
			folder.folderName.setText(groupData.get(groupPosition).get("folderName").toString());
			folder.tasksCount.setText(groupData.get(groupPosition).get("tasksCount").toString());
			
						
			//else的情况也要考虑，否则在绘制时出现错位现象
			
			/**
			 * 将刚刚创建的groupItem的相对坐标计算出来放在groupLocation中，这个是初始相对坐标
			 * 当点击打开一级菜单和关闭一级菜单时重新更新每一个group的相对坐标
			 */
			
			return convertView;
			/**
			 * 不要在适配器中调用适配器的内部方法，不然会出现奇怪的异常
			 * 
			 */
		}

		@Override
		public boolean hasStableIds() {
			// 
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// 
			return true;
		}

		/**
		 * 在设置ExpandableListView的宽度的时候，要注意每次点击展开或者关闭时，各个Group和所要显示的Item都会重绘
		 * 因此在每次绘制完毕之后都需要对height进行更新
		 */
		
		@Override
		public void onGroupExpanded(int groupPosition) {
			//
			super.onGroupExpanded(groupPosition);					
			groupData.get(groupPosition).put("expanded", true);					
			height+=childData.get(groupPosition).size()*CHILD_HEIGHT;
			ViewGroup.LayoutParams  params= exListView.getLayoutParams();
			params.height=height;
			exListView.setLayoutParams(params);
			for(int i=groupPosition+1; i<groupData.size(); i++){
			    groupData.get(i).put("location",(Integer)groupData.get(i).get("location")+childData.get(groupPosition).size());
			}						
		}

		@Override
		public void onGroupCollapsed(int groupPosition) {
			// 
			super.onGroupCollapsed(groupPosition);
			groupData.get(groupPosition).put("expanded", false);
			height=height-childData.get(groupPosition).size()*CHILD_HEIGHT;
			ViewGroup.LayoutParams  params= exListView.getLayoutParams();
			params.height=height;
			exListView.setLayoutParams(params);
			//获得groupitem的position
			for(int i=groupPosition+1; i<groupData.size(); i++){
			    groupData.get(i).put("location",(Integer)groupData.get(i).get("location")-childData.get(groupPosition).size());
			}
		}
						
	}

}
					

