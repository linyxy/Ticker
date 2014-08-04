package com.earth.ticker.fragment;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.earth.ticker.R;
import com.earth.ticker.assist.ContactsInfo;

public class FolderFragment extends Fragment{
	private int height=0;
	private int GROUP_HEIGHT=0;
	private int CHILD_HEIGHT=52;
	private int VISIBILITY_GONE=0;
	private int VISIBILITY_VISIBLE=1;
	int[] photoRes={R.drawable.contact_0,R.drawable.contact_1,R.drawable.contact_2,R.drawable.contact_3};
	 
	String[] groupFrom={"groupImage","groupName","childCount"};
	int[] groupTo={R.id.groupImage,R.id.groupName,R.id.childCount};
	String[] childFrom={"userImage","userName","userSign"};
	int[] childTo={R.id.ct_photo,R.id.ct_name,R.id.ct_sign};
	ArrayList<HashMap<String,Object>> groupData=null;
	ArrayList<ArrayList<HashMap<String,Object>>> childData=null; 
	
	ArrayList<HashMap<String,Object>> groupItenformation=null;  //用于存放groupItem的相对位置
	
	int[] groupIndicator={R.drawable.toright,R.drawable.todown};
	ExpandableListView exListView=null;
	
	ScrollView scroll=null;
	LinearLayout titleGroupView=null;
	ImageView titleImage=null;
	TextView titleGroupName=null;
	TextView titleChildCount=null;
	MyExpandableListViewAdapter adapter=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);				
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.layout_list_expandable,container, false);
		
		groupData=new ArrayList<HashMap<String,Object>>();
		childData=new ArrayList<ArrayList<HashMap<String,Object>>> ();
		
		//创建groupLocation对象
		//groupItemInformation=new ArrayList<HashMap<String,Object>>();
		
		ContactsInfo user1=new ContactsInfo("暗夜之殇","总有一天会寻找到自己的幸福",R.drawable.contact_0,"我的好友");
		ContactsInfo user2=new ContactsInfo("街角的幸福","有些事终于想开了",R.drawable.contact_1,"我的好友");
		ContactsInfo user3=new ContactsInfo("愤怒的小胖","谁再叫我小胖我跟谁急！",R.drawable.contact_3,"朋友");
		ContactsInfo user4=new ContactsInfo("放羊的星星","What ever",R.drawable.contact_2,"陌生人");
		ContactsInfo user5=new ContactsInfo("都市丽人","我要我的完美",R.drawable.contact_4,"我的好友");
		
		addUser(user1);
		addUser(user2);
		addUser(user3);
		addUser(user4);
		addUser(user5);
		addUser(user1);
		addUser(user2);
		addUser(user3);
		addUser(user4);
		addUser(user5);
		addUser(user3);
		addUser(user4);
		addUser(user5);
		addUser(user3);
		addUser(user4);
		addUser(user5);
		
		
		
		  
		  
		//不能用HashMap的实参赋给Map形参，只能new一个HashMap对象赋给Map的引用！
		
		
		exListView=(ExpandableListView)view.findViewById(R.id.expandable_list);
		adapter=new MyExpandableListViewAdapter(getActivity(),groupData,R.layout.layout_list_floder,groupFrom,groupTo,childData,R.layout.layout_list_task,childFrom,childTo );
		exListView.setAdapter(adapter);
		exListView.setGroupIndicator(null);
		exListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				System.out.println("I am clicked!!!");
				//Intent intent=new Intent();
				//intent.setClass(ContactsListExpandable.this, ChatActivity.class);
				//ContactsListExpandable.this.startActivity(intent);
				return false;
			}
		});
		
		
		scroll=(ScrollView)view.findViewById(R.id.list_scroll);
													
		View exGroupListItem= exListView.getExpandableListAdapter().getGroupView(0,false, null, exListView );
	    exGroupListItem.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		exGroupListItem.measure(0, 0);
		GROUP_HEIGHT=exGroupListItem.getMeasuredHeight();
		
		View exChildListItem=exListView.getExpandableListAdapter().getChildView(0, 0, false, null, exListView);
		exChildListItem.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		exChildListItem.measure(0, 0);
		CHILD_HEIGHT=exChildListItem.getMeasuredHeight();
		
		ViewGroup.LayoutParams  params= exListView.getLayoutParams();
		height=groupData.size()*GROUP_HEIGHT-2;
		params.height=height;
		exListView.setLayoutParams(params);
		
		for(int i=0; i<groupData.size() ;i++){
			groupData.get(i).put("location",i*GROUP_HEIGHT);
		}				
										
		titleGroupView=(LinearLayout)view.findViewById(R.id.titleGroupView);
		titleGroupName=(TextView)view.findViewById(R.id.title_groupName);
		titleChildCount=(TextView)view.findViewById(R.id.title_childCount);		
		titleGroupView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int groupPosition=(Integer)titleGroupView.getTag();

				exListView.collapseGroup(groupPosition);
				
				new LocationCheckThread().start();
			    
				
			}
			
		});
		
        scroll.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction()==MotionEvent.ACTION_MOVE && isChecking==false){
					new LocationCheckThread().start();
				}
				return false;
			}
		});
		return view;
	}
	
	/**
	 * 实现这个Activity的最后一个效果，即滑动到某一个Group那么改GroupItem就在屏幕顶端悬停的效果
	 * 方法：需要使用onTouchEvent方法，这个比用onScrollView方法靠谱
	 */
	private boolean isChecking=false;
	
	
	private class LocationCheckThread extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			isChecking=true;
			int[] location=new int[2];
			int beforeY=0;
			int i;
			ExpandableHandler mHandler=new ExpandableHandler(Looper.getMainLooper());
			while(true){
				//exListView.getLocationOnScreen(location);
				  
				exListView.getLocationOnScreen(location);
				if(beforeY==location[1]){                    //控件停止运动，线程关闭
					isChecking=false;
					return;
				}
				else{
					beforeY=location[1];
					for(i=groupData.size()-1; i>=0; i--){
						if((Boolean)groupData.get(i).get("expanded")&&(Integer)groupData.get(i).get("location")+location[1]<=24){
							

							Message msg=new Message();
							msg.arg1=childData.get(i).size();
							msg.arg2=i;
							msg.obj=groupData.get(i).get("groupName");
							msg.what=VISIBILITY_VISIBLE;
							mHandler.sendMessage(msg);
							break;
						}
					}
					if(i<0){
						Message msg=new Message();
						msg.what=VISIBILITY_GONE;
						msg.obj="";                  //必须加这一步，否则会有空指针异常
						mHandler.sendMessage(msg);
					}
					
				}

				try {
					this.sleep(80);         //sleep的时间不能过短！！
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
	
	}
	
	
	public class ExpandableHandler extends Handler{

		public ExpandableHandler(Looper looper){
			super(looper);
		}
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			int listNum=msg.arg1;
			int visibility=msg.what;
			int groupPos=msg.arg2;
			String groupName=msg.obj.toString();
			
			if(visibility==VISIBILITY_GONE){
				titleGroupView.setVisibility(View.GONE);
				
				return;
			}
			else{

				titleGroupView.setVisibility(View.VISIBLE);
				titleGroupName.setText(groupName);
				titleChildCount.setText(""+listNum);
				
				titleGroupView.setTag(groupPos);        //给这个View控件设置一个标签属性，用于存放groupPosition
				/**
				 * setText()中的参数不能使int型！！
				 */
			}
			
		}
		
	}

	protected void addUser(ContactsInfo user)
	{
		int i;
		for(i=0; i< groupData.size(); i++){
			if(groupData.get(i).get("groupName").toString().equals(user.groupInfo)){
				break;
			}
		}
		if(i>=groupData.size()){
			HashMap<String,Object> map=new HashMap<String,Object>();
			map.put("groupImage", groupIndicator);
			map.put("groupName",user.groupInfo );
			map.put("childCount", 0);
			groupData.add(map);
			
			ArrayList<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
			childData.add(list);
		}
		
		HashMap<String,Object> userData=new HashMap<String,Object>();
	    userData.put("userImage",user.userImage );
		userData.put("userName", user.userName);
		userData.put("userSign", user.userSign);
		childData.get(i).add(userData);
		Integer count=(Integer)groupData.get(i).get("childCount")+1;
		groupData.get(i).put("childCount", count);    
		groupData.get(i).put("expanded", false);

		
	}
	
	
	
	
	
	
	









	/**
	 * ExpandableListView对应的适配器
	 * @author DragonGN
	 *
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
			// TODO Auto-generated method stub
			return null;
		}

		/**
		 * position与id一样，都是从0开始计数的，
		 * 这里返回的id也是从0开始计数的
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
	    class ChildViewHolder{
			ImageButton userImage=null;
			TextView userName=null;
			TextView userSign=null;
		}
	    
	    /**头像点击事件监听类**/
	    class ImageClickListener implements OnClickListener{

	    	ChildViewHolder holder=null;
	    	public ImageClickListener(ChildViewHolder holder){
	    		this.holder=holder;
	    	}
	    	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, holder.userName.getText()+" is clicked", Toast.LENGTH_SHORT).show();
				
			}
	    	
	    }

	    
	    
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			/**
			 * 这里isLastChild目前没用到，如果出现异常再说
			 */
			ChildViewHolder holder=null;
			if(convertView==null){
				convertView= LayoutInflater.from(context).inflate(childLayout,null);
				                             //感觉这里需要把root设置成ViewGroup 对象
				/**
				 * ERROR!!这里不能把null换成parent，否则会出现异常退出，原因不太确定，可能是inflate方法获得的这个item的View
				 * 并不属于某个控件组，所以使用默认值null即可
				 */
				holder=new ChildViewHolder();
				holder.userImage=(ImageButton)convertView.findViewById(childTo[0]);
				holder.userName=(TextView)convertView.findViewById(childTo[1]);
				holder.userSign=(TextView)convertView.findViewById(childTo[2]);
				convertView.setTag(holder);
			}
			else{
				holder=(ChildViewHolder)convertView.getTag();
			}
			
			holder.userImage.setBackgroundResource((Integer)(childData.get(groupPosition).get(childPosition).get(childFrom[0])));
			holder.userName.setText(childData.get(groupPosition).get(childPosition).get(childFrom[1]).toString());
			holder.userSign.setText(childData.get(groupPosition).get(childPosition).get(childFrom[2]).toString());
			holder.userImage.setOnClickListener(new ImageClickListener(holder));

			
			return convertView;
		}

		
		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return childData.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return groupData.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		
		class GroupViewHolder{
			ImageView image=null;
			TextView groupName=null;
			TextView childCount=null;
		}
		
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			GroupViewHolder holder=null;
			if(convertView==null){
				convertView=LayoutInflater.from(context).inflate(groupLayout, null);
				holder=new GroupViewHolder();
				holder.image=(ImageView)convertView.findViewById(groupTo[0]);
				holder.groupName=(TextView)convertView.findViewById(groupTo[1]);
				holder.childCount=(TextView)convertView.findViewById(groupTo[2]);
				convertView.setTag(holder);
			}
			else{
				holder=(GroupViewHolder)convertView.getTag();
			} 
			
			int[] groupIndicator=(int[])groupData.get(groupPosition).get(groupFrom[0]);
			holder.image.setBackgroundResource(groupIndicator[isExpanded?1:0]);
			holder.groupName.setText(groupData.get(groupPosition).get(groupFrom[1]).toString());
			holder.childCount.setText(groupData.get(groupPosition).get(groupFrom[2]).toString());
			
			
			if(groupPosition==groupData.size()-1){
				convertView.setBackgroundResource(R.drawable.list_lastitem_border);
			}
			else{
				convertView.setBackgroundResource(R.drawable.list_item_border);
			}
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
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

		/**
		 * 在设置ExpandableListView的宽度的时候，要注意每次点击展开或者关闭时，各个Group和所要显示的Item都会重绘
		 * 因此在每次绘制完毕之后都需要对height进行更新
		 */
		
		@Override
		public void onGroupExpanded(int groupPosition) {
			// TODO Auto-generated method stub
			super.onGroupExpanded(groupPosition);
		

			/**
			 * 更新groupItem的相对坐标
			 */
			groupData.get(groupPosition).put("expanded", true);
			for(int i=groupPosition+1; i<groupData.size(); i++){
			    groupData.get(i).put("location",(Integer)groupData.get(i).get("location")+childData.get(groupPosition).size()*CHILD_HEIGHT );
			}
			
			float deviationFix=0;         //对ExpandableListView高度的误差修正
			for(int i=0;i<groupData.size() ;i++){
				deviationFix +=(Integer)groupData.get(i).get("location")/CHILD_HEIGHT*0.5;
			}
			
			height+=childData.get(groupPosition).size()*CHILD_HEIGHT;
			ViewGroup.LayoutParams  params= exListView.getLayoutParams();
			params.height=height-floatToInt(deviationFix);
			exListView.setLayoutParams(params);
			
			
			
		}

		@Override
		public void onGroupCollapsed(int groupPosition) {
			// TODO Auto-generated method stub
			super.onGroupCollapsed(groupPosition);
			height=height-childData.get(groupPosition).size()*CHILD_HEIGHT;
			ViewGroup.LayoutParams  params= exListView.getLayoutParams();
			params.height=height;
			exListView.setLayoutParams(params);
			
			/**
			 * 更新groupItem的相对坐标
			 */
			groupData.get(groupPosition).put("expanded", false);
			for(int i=groupPosition+1; i<groupData.size(); i++){
			    groupData.get(i).put("location",(Integer)groupData.get(i).get("location")-childData.get(groupPosition).size()*CHILD_HEIGHT );
			}
		}
		
		
		private int floatToInt(double f){
			if(f>0){
				return (int)(f+0.5);
			}
			else{
				return (int)(f-0.5);
			}
			
		}

	}
		
}
