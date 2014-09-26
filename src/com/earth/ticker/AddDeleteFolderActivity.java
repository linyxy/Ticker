package com.earth.ticker;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.earth.ticker.assist.AlertDialog;
import com.earth.ticker.util.SQLOperate;

public class AddDeleteFolderActivity extends Activity {

	private ImageButton add_Button;
	private FolderListAdapter adapter;
	private ListView mlistView;
	private List<String> folder_list = new ArrayList<String>();
	private LinearLayout newFolder;
	private String str;// 获得的EditText内容
	// public AddDeleteFolderActivity context;
	private PopupWindow pop;
	private int popHeight = 0;
	private int popWidth = 0;
	private int pos = 0;

	@Override
	public void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
		setContentView(R.layout.adddelete_folder_layout);

		add_Button = (ImageButton) findViewById(R.id.addfolder_button);
		mlistView = (ListView) findViewById(R.id.deletefolder_list);
		newFolder = (LinearLayout) findViewById(R.id.new_folder);

		// 初始化文件夹列表
		initFolderList();
		// 文件夹列表的监听，弹出自定义alertdialog
		mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final String oldFolder = parent.getItemAtPosition(position)
						.toString();
				Log.d("AD", "name of old->" + oldFolder);
				final AlertDialog ad = new AlertDialog(
						AddDeleteFolderActivity.this);
				ad.setTitle("编辑文件夹");
				ad.setMessage("请输入新的文件夹名称");
				ad.setEdit(true, "请输入文件夹名");
				ad.setNegativeButton("取消", new OnClickListener() {
					@Override
					public void onClick(View v) {

						ad.dismiss();
					}
				});

				ad.setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(View v) {
						// 编辑文件夹
						String newFolderName = ad.getEditText();
						//
						SQLOperate.updateFolder(getApplication(), oldFolder,
								newFolderName);

						notifyDataChange();
						ad.dismiss();
					}
				});

			}
		});

		// mlistView.setAdapter(adapter);

		// 添加文件夹按钮监听，弹出对话框
		newFolder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final AlertDialog ad = new AlertDialog(
						AddDeleteFolderActivity.this);

				ad.setTitle("添加文件夹");
				ad.setMessage("请输入新的文件夹名称");
				ad.setEdit(true, "请输入文件夹名");
				ad.setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(View v) {

						ad.dismiss();
					}
				});

				ad.setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(View v) {

						if (ad.getEditText() != null) {
							String folderName = ad.getEditText();
							// 添加新文件夹
							SQLOperate.addFolder(getApplication(), folderName);
							
							notifyDataChange();
							Log.d("DBtag",
									SQLOperate.getAllFolders(getApplication())
											.toString());
							ad.dismiss();
						} else {
							ad.dismiss();
						}
					}
				});
			}
		});

	}

	// 初始化Folder显示列表
	public void initFolderList() {
		folder_list = new ArrayList<String>();

		ArrayList<String> folders = SQLOperate.getAllFolders(this);

		if (folders.size() > 0) {
			for (String folder : folders)
				folder_list.add(folder);
		}

		adapter = new FolderListAdapter();
		mlistView.setAdapter(adapter);
	}

	// listview的更新
	public void notifyDataChange() {
		folder_list.clear();
		ArrayList<String> folders = SQLOperate.getAllFolders(this);

		if (folders.size() > 0) {
			for (String folder : folders)
				folder_list.add(folder);
		}

		adapter.notifyDataSetChanged();
	}

	// 文件夹列表显示适配器
	class FolderListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return folder_list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return folder_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public boolean areAllItemsEnabled() {
			// all items are separator
			return true;
		}

		@Override
		public boolean isEnabled(int position) {
			// all items are separator
			return true;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub \

			View view = convertView;

			view = LayoutInflater.from(getApplicationContext()).inflate(
					R.layout.delete_folder_item, null);
			final TextView text = (TextView) view
					.findViewById(R.id.delete_folder);
			text.setText((String) folder_list.get(position));
			ImageButton button = (ImageButton) view
					.findViewById(R.id.deletefolder_button);
			final LayoutInflater mInflater = LayoutInflater.from(view
					.getContext());
			// item控件
			final View currentview = view;

			// 删除文件夹按钮监听，弹出删除按钮
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					View viewpop = mInflater.inflate(R.layout.delete_btn, null);
					pop = new PopupWindow(currentview,
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT, false);
					pop.setContentView(viewpop);
					Button delete_btn = (Button) viewpop
							.findViewById(R.id.delete_button);
					// 需要设置一下此参数，点击外边可消失
					pop.setBackgroundDrawable(new BitmapDrawable());
					// 设置点击窗口外边窗口消失
					pop.setOutsideTouchable(true);
					// 设置此参数获得焦点，否则无法点击
					pop.setFocusable(true);

					pop.getContentView().measure(0, 0);
					popHeight = pop.getContentView().getMeasuredHeight();
					popWidth = pop.getContentView().getMeasuredWidth();
					pop.setBackgroundDrawable(new BitmapDrawable());

					// 获得控件的位置
					int[] location = new int[2];
					currentview.getLocationInWindow(location);

					pop.setAnimationStyle(R.style.popwindow_delete_btn_anim_style);
					pop.update();
					pop.showAtLocation(currentview, Gravity.LEFT | Gravity.TOP,
							location[0] + currentview.getWidth(), location[1]
									+ currentview.getHeight() / 2 - popHeight
									/ 2);

					delete_btn.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View arg0) {
							final AlertDialog ad = new AlertDialog(
									AddDeleteFolderActivity.this);

							ad.setTitle("提示");
							ad.setMessage("确定删除该文件夹吗");
							ad.setEdit(false, null);
							ad.setNegativeButton("取消", new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									ad.dismiss();
									pop.dismiss();
								}
							});

							ad.setPositiveButton("确定", new OnClickListener() {
								@Override
								public void onClick(View v) {

									String folderName = (String) folder_list
											.get(position);
									ArrayList<Long> eventResult = SQLOperate
											.getAllEventIdbyFolder(
													getApplication(),
													folderName);
									if (eventResult != null
											&& eventResult.size() >= 1) {
										SQLOperate.deleteFolderREvent(
												getApplication(), folderName);
										for (int i = 0; i < eventResult.size(); i++) {
											// 删除子任务
											ArrayList<Long> subEventResult = SQLOperate
													.getAllSubEventIdbyEventId(
															getApplication(),
															eventResult.get(i));
											if (subEventResult != null
													&& subEventResult.size() >= 1) {
												for (int j = 0; j < subEventResult
														.size(); j++) {
													SQLOperate.deleteSubEvent(
															getApplication(),
															subEventResult
																	.get(j));
												}
											}

											// 删除注释
											ArrayList<Long> noteEventResult = SQLOperate
													.getAllNoteIdbyEventId(
															getApplication(),
															eventResult.get(i));
											if (noteEventResult != null
													&& noteEventResult.size() >= 1) {
												// 删除note与event的关联
												SQLOperate.deleteNoteRelate(
														getApplication(),
														eventResult.get(i));

												for (int j = 0; j < noteEventResult
														.size(); j++) {
													SQLOperate.deleteNote(
															getApplication(),
															noteEventResult
																	.get(j),
															false);
												}

											}

											// 删除contact_work
											ArrayList<String> workNames = SQLOperate
													.getContactWorkByEventId(
															getApplication(),
															eventResult.get(i));
											if (workNames != null
													&& workNames.size() >= 1) {
												for (int j = 0; j < workNames
														.size(); j++) {
													SQLOperate.deleteContact(
															getApplication(),
															workNames.get(j));
												}
											}
										}
									} else {
										SQLOperate.deleteFolder(
												getApplication(), folderName);
									}

									notifyDataChange();
									Log.d("DBtag",
											SQLOperate.getAllFolders(
													getApplication())
													.toString());

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
