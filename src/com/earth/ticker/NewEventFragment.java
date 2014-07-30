package com.earth.ticker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class NewEventFragment extends Fragment {
	
	private ImageButton new_event_top_icon;
	private TextView new_event_name;
	
	private ScrollView new_event_scroll_view;
	private TextView new_note_text;
	private ImageButton new_note_attach;
	
	private EditText new_date_picker;
	private EditText new_time_picker;
	
	private Spinner new_folder_selction;
	private ImageButton new_folder_attach;
	
	private Spinner new_participate_text;
	private EditText new_contact_attach;
	
	private Spinner new_extra_text;
	private EditText new_extra_attach;
	
	
	private ListView new_sub_event;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View newEventView = inflater.inflate(R.layout.fragment_new_event, container,false);
				//TODO 填充页面

		return newEventView;
	}
	
	

}
