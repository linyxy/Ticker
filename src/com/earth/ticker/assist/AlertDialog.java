package com.earth.ticker.assist;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.earth.ticker.R;


public class AlertDialog {
	Context context;
	android.app.AlertDialog ad;
	TextView titleView;
	TextView messageView;
	EditText folderName;
	LinearLayout buttonLayout;
	
	public AlertDialog(Context context) {
		//
		this.context=context;
		ad=new android.app.AlertDialog.Builder(context).create();
		//鐐瑰嚮dialog澶栨秷澶�
		ad.setCanceledOnTouchOutside(true);
		ad.show();
		//鍏抽敭鍦ㄤ笅闈㈢殑涓よ,浣跨敤window.setContentView,鏇挎崲鏁翠釜瀵硅瘽妗嗙獥鍙ｇ殑甯冨眬
		Window window = ad.getWindow();
		window.setContentView(R.layout.addfolder_alertdialog);
		//寮瑰嚭杞敭鐩�
		window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		titleView=(TextView)window.findViewById(R.id.title);
		messageView=(TextView)window.findViewById(R.id.message);
		
		folderName=(EditText)window.findViewById(R.id.dialog_folder_name);
		
		buttonLayout=(LinearLayout)window.findViewById(R.id.buttonLayout);
		
		//String folder_name=folderName.getText().toString();
	}
    
	public void setTitle(String title) {
		titleView.setText(title);
	}
	
	public void setMessage(String message)
	{
		messageView.setText(message);
	}
    
	public void setEdit(boolean visible,String hint) {
		//
		if(visible)
		{
			folderName.setVisibility(View.VISIBLE);
			folderName.setHint(hint);
		}else
		{
			folderName.setVisibility(View.GONE);
		}
	}
	public String getEditText()
	{
		return folderName.getText().toString();
	}
	/**
	 * 璁剧疆鎸夐挳
	 * @param text
	 * @param listener
	 */
	public void setPositiveButton(String text,final View.OnClickListener listener)
	{
		Button button=(Button)buttonLayout.findViewById(R.id.position_button);
		button.setText(text);
		button.setTextColor(Color.parseColor("#3CB6E3"));
		button.setTextSize(20);
		button.setOnClickListener(listener);
	}
    
	/**
	 * 璁剧疆鎸夐挳
	 * @param text
	 * @param listener
	 */
	public void setNegativeButton(String text,final View.OnClickListener listener)
	{
		
		Button button=(Button)buttonLayout.findViewById(R.id.negtive_button);
		button.setText(text);
		button.setTextColor(Color.parseColor("#3CB6E3"));
		button.setTextSize(20);
		button.setOnClickListener(listener);
        
	}
	/**
	 * 鍏抽棴瀵硅瘽妗�
	 */
	public void dismiss() {
		ad.dismiss();
	}
    
	
	
    
}