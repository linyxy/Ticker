package com.earth.ticker.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar.LayoutParams;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.earth.ticker.AddDeleteFolderActivity;
import com.earth.ticker.DetailsEventActivity;
import com.earth.ticker.R;
import com.earth.ticker.SkeletonActivity;
import com.earth.ticker.assist.FirstOpenSample;
import com.earth.ticker.util.SQLOperate;

public class FolderFragment extends Fragment {

	private int height = 0;
	private int GROUP_HEIGHT = 0;
	private int CHILD_HEIGHT = 52;
	public static final int REQUST_CODE_ADDDELETE = 00;
	public static final int FOLDER_NAME_CHANG = 11;
	private boolean exPanded;
	private PopupWindow pop;
	String[] groupFrom = { "folderName", "tasksCount" };
	int[] groupTo = { R.id.folderName, R.id.tasksCount };
	String[] childFrom = { "taskImage", "taskName", "taskSign" };
	int[] childTo = { R.id.taskImage, R.id.taskName, R.id.taskSign };
	ArrayList<HashMap<String, Object>> groupData = null;
	ArrayList<ArrayList<HashMap<String, Object>>> childData = null;
	ViewGroup.LayoutParams params;
	ExpandableListView exListView = null;
	MyExpandableListViewAdapter adapter = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_list_expandable,
				container, false);

		groupData = new ArrayList<HashMap<String, Object>>();
		childData = new ArrayList<ArrayList<HashMap<String, Object>>>();

		initUser();
		// ������HashMap��ʵ�θ���Map�βΣ�ֻ��newһ��HashMap���󸳸�Map�����ã�

		exListView = (ExpandableListView) view
				.findViewById(R.id.expandable_list);
		adapter = new MyExpandableListViewAdapter(getActivity(), groupData,
				R.layout.layout_list_folder, groupFrom, groupTo, childData,
				R.layout.layout_list_task, childFrom, childTo);
		exListView.setAdapter(adapter);
		// adapter.notifyDataSetChanged();

		// ���folder item��ʵ�ʸ߶�
		View exGroupListItem = exListView.getExpandableListAdapter()
				.getGroupView(0, false, null, exListView);
		exGroupListItem.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		exGroupListItem.measure(0, 0);
		GROUP_HEIGHT = exGroupListItem.getMeasuredHeight();

		// ���task item��ʵ�ʸ߶�
		View exChildListItem = exListView.getExpandableListAdapter()
				.getChildView(0, 0, false, null, exListView);
		exChildListItem.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		exChildListItem.measure(0, 0);
		CHILD_HEIGHT = exChildListItem.getMeasuredHeight();

		// ���б����¼�������ת�������������
		exListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra(
						"taskName",
						childData.get(groupPosition).get(childPosition)
								.get("taskName").toString());
				intent.setClass(getActivity(), DetailsEventActivity.class);
				getActivity().startActivity(intent);
				return true;
			}
		});

		exListView.setOnItemLongClickListener(new LongItemClick());
		return view;
	}

	// �������б?��ʾpopupwindow
	class LongItemClick implements OnItemLongClickListener {
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			int position = arg2;
			// TODO Auto-generated method stub
			for (int i = 0; i < groupData.size(); i++) {
				if (position == (Integer) groupData.get(i).get("location")) {
					// Toast.makeText(getActivity(), "�������",
					// Toast.LENGTH_LONG).show();
					showPopUp(arg1, arg2, i);

				}
			}
			return false;
		}
	}

	// popupwindow��ʾ��������ת����ӣ�ɾ���ļ��н���
	private void showPopUp(View view, int position, int i) {

		Vibrator vibrator = (Vibrator) getActivity().getSystemService(
				Service.VIBRATOR_SERVICE);
		vibrator.vibrate(15);
		// popUpWindow����
		LayoutInflater inflater1 = LayoutInflater.from(getActivity());
		View viewpop = inflater1.inflate(R.layout.popupwindow_layout, null);
		pop = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, false);
		pop.setContentView(viewpop);
		Button btn = (Button) viewpop.findViewById(R.id.popwindow_Button);
		// ��Ҫ����һ�´˲�������߿���ʧ
		pop.setBackgroundDrawable(new BitmapDrawable());
		// ���õ��������ߴ�����ʧ
		pop.setOutsideTouchable(true);
		// ���ô˲����ý��㣬�����޷����
		pop.setFocusable(true);
		// �����Ļ���
		WindowManager wm = (WindowManager) getActivity().getSystemService(
				Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		// ��ÿؼ���λ��
		int[] location = new int[2];
		view.getLocationInWindow(location);

		pop.showAtLocation(view, Gravity.LEFT | Gravity.TOP, width / 3,
				location[1]);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pop.dismiss();
				Intent intent = new Intent();
				intent.setClass(getActivity(), AddDeleteFolderActivity.class);
				getActivity().startActivity(intent);
			}
		});
	}

	protected void initUser() {
		// 若allFolders的大小为空，则向groupData中添加空的group，防止出现空指针的问题
		ArrayList<String> allFolders = SQLOperate.getAllFolders(getActivity());
		// HashMap<String,Object> group
		ArrayList<HashMap<String, Object>> childList = new ArrayList<HashMap<String, Object>>();

		if (allFolders != null && allFolders.size() > 0) {
			for (int i = 0; i < allFolders.size(); i++) {
				HashMap<String, Object> group = new HashMap<String, Object>();
				ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
				ArrayList<Long> allEventId = SQLOperate.getAllEventIdbyFolder(
						getActivity(), allFolders.get(i));
				group.put("folderName", allFolders.get(i));
				if (allEventId != null && allEventId.size() > 0) {
					group.put("tasksCount", allEventId.size());
					for (long singleEventId : allEventId) {
						String sql = "select * from  events WHERE id=?";
						String[] eventId = new String[] { String
								.valueOf(singleEventId) };
						Cursor cursor = SQLOperate.basicQuery(getActivity(),
								sql, eventId);
						if (cursor != null && cursor.getCount() > 0) {
							cursor.moveToFirst();
							// 开始把task对象new在循环的外面 造成add时前一个数据总被后一个数据覆盖
							HashMap<String, Object> task = new HashMap<String, Object>();
							task.put("taskName", cursor.getString(cursor
									.getColumnIndex("name")));
							task.put(
									"taskSign",
									cursor.getString(cursor
											.getColumnIndex("time_stamp"))
											+ cursor.getString(cursor
													.getColumnIndex("alarm"))
											+ cursor.getString(cursor
													.getColumnIndex("repeat"))
											+ cursor.getString(cursor
													.getColumnIndex("duration")));
							list.add(task);
						}
					}
					childData.add(list);
					Log.d("childData", childData.toString());
				} else {
					// 若eventId的大小为零，则向childData中添加空的list，防止出现空指针错误
					childData.add(list);
					group.put("tasksCount", 0);
				}
				group.put("expanded", false);
				groupData.add(group);
			}
		} else {
			// groupData.add(group);
			childData.add(childList);
		}
	}

	/**
	 * ExpandableListView��Ӧ��������
	 */
	public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

		private Context context = null;

		private ArrayList<HashMap<String, Object>> groupData = null;
		int groupLayout = 0;
		private String[] groupFrom = null;
		private int[] groupTo = null;

		private ArrayList<ArrayList<HashMap<String, Object>>> childData = null;
		int childLayout = 0;
		private String[] childFrom = null;
		private int[] childTo = null;

		public MyExpandableListViewAdapter(Context context,
				ArrayList<HashMap<String, Object>> groupData, int groupLayout,
				String[] groupFrom, int[] groupTo,
				ArrayList<ArrayList<HashMap<String, Object>>> childData,
				int childLayout, String[] childFrom, int[] childTo) {
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
		 * position��idһ���Ǵ�0��ʼ����ģ� ���ﷵ�ص�idҲ�Ǵ�0��ʼ�����
		 */
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			long id = 0;
			for (int i = 0; i < groupPosition; i++) {
				id += childData.size();
			}
			id += childPosition;
			return id;
		}

		/** ChildViewHolder�ڲ��� **/
		class ChildViewTask {
			ImageView taskImage = null;
			TextView taskName = null;
			TextView taskSign = null;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			/**
			 * ����isLastChildĿǰû�õ����������쳣��˵
			 */
			ChildViewTask task = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(childLayout,
						null);

				/**
				 * ERROR!!���ﲻ�ܰ�null����parent�����������쳣�˳���ԭ��̫ȷ����������
				 * inflate������õ����item��View
				 * ��������ĳ���ؼ��飬����ʹ��Ĭ��ֵnull����
				 */
				task = new ChildViewTask();
				task.taskImage = (ImageView) convertView
						.findViewById(R.id.taskImage);
				task.taskName = (TextView) convertView
						.findViewById(R.id.taskName);
				task.taskSign = (TextView) convertView
						.findViewById(R.id.taskSign);
				convertView.setTag(task);
			} else {
				task = (ChildViewTask) convertView.getTag();
			}

			if (childData.get(groupPosition) != null
					&& childData.get(groupPosition).size() > 0) {
				// task.taskImage.setBackgroundResource((Integer)(childData.get(groupPosition).get(childPosition).get("taskImage")));
				task.taskName.setText(childData.get(groupPosition)
						.get(childPosition).get("taskName").toString());
				task.taskSign.setText(childData.get(groupPosition)
						.get(childPosition).get("taskSign").toString());
				// task.taskImage.setOnClickListener(new
				// ImageClickListener(task));
			}
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

		class GroupViewFolder {
			// ImageView folderImage=null;
			TextView folderName = null;
			TextView tasksCount = null;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			GroupViewFolder folder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(groupLayout,
						null);
				folder = new GroupViewFolder();
				// holder.folderImage=(ImageView)convertView.findViewById(groupTo[0]);
				folder.folderName = (TextView) convertView
						.findViewById(R.id.folderName);
				folder.tasksCount = (TextView) convertView
						.findViewById(R.id.tasksCount);
				convertView.setTag(folder);
			} else {
				folder = (GroupViewFolder) convertView.getTag();
			}

			if (groupData != null && groupData.size() > 0) {
				folder.folderName.setText(groupData.get(groupPosition)
						.get("folderName").toString());
				folder.tasksCount.setText(groupData.get(groupPosition)
						.get("tasksCount").toString());
			}

			// else�����ҲҪ���ǣ������ڻ���ʱ���ִ�λ����

			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		/**
		 * ������ExpandableListView�Ŀ�ȵ�ʱ��Ҫע��ÿ�ε��չ�����߹ر�ʱ������Group����Ҫ��
		 * ʾ��Item�����ػ� �����ÿ�λ������֮����Ҫ��height���и���
		 */

		@Override
		public void onGroupExpanded(int groupPosition) {
			// TODO Auto-generated method stub
			super.onGroupExpanded(groupPosition);
			groupData.get(groupPosition).put("expanded", true);
			height += childData.get(groupPosition).size() * CHILD_HEIGHT;
			ViewGroup.LayoutParams params = exListView.getLayoutParams();
			params.height = height;
			exListView.setLayoutParams(params);
			for (int i = groupPosition + 1; i < groupData.size(); i++) {
				groupData.get(i).put(
						"location",
						(Integer) groupData.get(i).get("location")
								+ childData.get(groupPosition).size());
			}
		}

		@Override
		public void onGroupCollapsed(int groupPosition) {
			// TODO Auto-generated method stub
			super.onGroupCollapsed(groupPosition);
			groupData.get(groupPosition).put("expanded", false);
			height = height - childData.get(groupPosition).size()
					* CHILD_HEIGHT;
			ViewGroup.LayoutParams params = exListView.getLayoutParams();
			params.height = height;
			exListView.setLayoutParams(params);
			// ���groupitem��position
			for (int i = groupPosition + 1; i < groupData.size(); i++) {
				groupData.get(i).put(
						"location",
						(Integer) groupData.get(i).get("location")
								- childData.get(groupPosition).size());
			}
		}

	}

	/**
	 * ���б��ˢ��
	 */
	@Override
	public void onStart() {
		super.onStart();
		int childNum = 0;
		ArrayList<String> expandList = null;
		expandList = new ArrayList<String>();

		for (int i = 0; i < groupData.size(); i++) {
			if ((boolean) groupData.get(i).get("expanded").equals(true)) {
				expandList.add(groupData.get(i).get("folderName").toString());
			}
		}

		groupData.clear();
		childData.clear();

		initUser();

		for (int i = 0; i < expandList.size(); i++) {
			for (int j = 0; j < groupData.size(); j++) {
				if (expandList.get(i)
						.equals(groupData.get(j).get("folderName"))) {
					// groupData.get(j).remove("expanded");
					groupData.get(j).put("expanded", true);
					childNum += childData.get(j).size();
				}
			}
		}

		params = exListView.getLayoutParams();
		height = groupData.size() * GROUP_HEIGHT - 2 + childNum * CHILD_HEIGHT;
		params.height = height;
		exListView.setLayoutParams(params);

		int location = 0;

		for (int i = 0; i < groupData.size(); i++) {
			if (i == 0) {
				groupData.get(i).put("location", 0);
			} else {
				if ((boolean) groupData.get(i - 1).get("expanded").equals(true)) {
					location += childData.get(i - 1).size();
				}
				groupData.get(i).put("location", i + location);
			}

		}

		adapter.notifyDataSetChanged();
	}


}
