package com.earth.ticker.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.earth.ticker.R;
import com.earth.ticker.assist.NavDrawerItem;
import com.earth.ticker.assist.NavDrawerListAdapter;



public class MenuFragment extends Fragment implements OnItemClickListener {

	private ListView mDrawerList;
	private String[] mNavMenuTitles;
	private TypedArray mNavMenuIconsTypeArray;
	private ArrayList<NavDrawerItem> mNavDrawerItems;
	private NavDrawerListAdapter mAdapter;
	private SLMenuListOnItemClickListener mCallback;
	private int selected = -1;

	/**
	 * ��activity�󶨣�ʵ�ֻص�����
	 */
	@Override
	public void onAttach(Activity activity) {
		try {
			mCallback = (SLMenuListOnItemClickListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnResolveTelsCompletedListener");
		}
		super.onAttach(activity);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_menu, null);
		
		findView(rootView);
		
		return rootView;
	}

	private void findView(View rootView) {
		
		mDrawerList = (ListView) rootView.findViewById(R.id.left_menu);  
		
		mNavMenuTitles = getResources().getStringArray(R.array.left_menu);  
       
        mNavMenuIconsTypeArray = getResources()  
                    .obtainTypedArray(R.array.nav_drawer_icons);  
              
        mNavDrawerItems = new ArrayList<NavDrawerItem>();  
  
        //��listview�����Դ
        mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[0], mNavMenuIconsTypeArray  
                .getResourceId(0, -1)));  
     
        mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[1], mNavMenuIconsTypeArray  
                .getResourceId(1, -1)));  
        
        mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[2], mNavMenuIconsTypeArray  
                .getResourceId(2, -1)));  
       
        mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[3], mNavMenuIconsTypeArray  
                .getResourceId(3, -1)));  
       
        mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[4], mNavMenuIconsTypeArray  
                .getResourceId(4, -1)));           
  
        
        mNavMenuIconsTypeArray.recycle(); //�ͷ���Դ 
          
       
        mAdapter = new NavDrawerListAdapter(getActivity(),  
                        mNavDrawerItems);  
        mDrawerList.setAdapter(mAdapter);  
        mDrawerList.setOnItemClickListener(this);  
        
        //���û�б�ѡ��� ����Ĭ��ѡ���0��
        if(selected!=-1){
        	mDrawerList.setItemChecked(selected, true);  
            mDrawerList.setSelection(selected);  
        }else{
        	mDrawerList.setItemChecked(0, true);  
            mDrawerList.setSelection(0);  
        }
	}

	/**
	 * ʵ��listview�ĵ������
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		
        mDrawerList.setItemChecked(position, true);  
        mDrawerList.setSelection(position);  
        
        if(mCallback!=null){
        	mCallback.selectItem(position, mNavMenuTitles[position]);
        }
        selected = position;
	}

	/**
     * ���˵� ����ص��ӿ�
     */
    public interface SLMenuListOnItemClickListener{
    	
    	public void selectItem(int position,String title);

		
    }
}
