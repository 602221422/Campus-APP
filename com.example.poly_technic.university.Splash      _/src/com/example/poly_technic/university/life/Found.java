 package com.example.poly_technic.university.life;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.poly_technic.university.R;
import com.example.poly_technic.university.model.Parameter;
import com.example.poly_technic.university.service.SyncHttp;

public class Found extends Activity
{
	 public RadioGroup mRadioGroup1;
	 public RadioButton mRadio1, mRadio2;
	 EditText title;
	 EditText content;
	 String xinxi="招领:";
	 private final int SECOND = 1;
	 private Handler mHandler = new Handler()
		{
			
			public void handleMessage(Message msg)
			{
				switch (msg.arg1) {
			
				case SECOND:
					Toast.makeText(Found.this,(CharSequence) msg.obj, Toast.LENGTH_SHORT).show();
					 title.setText("");//清空输入框的内容  
		                content.setText("");
					break;

				}
			}
		};
@Override
protected void onCreate(Bundle savedInstanceState)
{
	
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.found);
	mRadioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
	  mRadio1 = (RadioButton) findViewById(R.id.radio0);
	  mRadio2 = (RadioButton) findViewById(R.id.radio1);
	  mRadioGroup1.setOnCheckedChangeListener(radiogpchange);
	Button backbutton;
	backbutton=(Button)this.findViewById(R.id.newsdetails_titlebar_previous);
	backbutton.setOnClickListener(new View.OnClickListener()
	{
		
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			finish();
		}
	});
	title=(EditText)findViewById(R.id.et1);
	content=(EditText)findViewById(R.id.et2);
	Button fabuButton=(Button)findViewById(R.id.fanbu);
	fabuButton.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
	        String titleString = title.getText().toString(); 
	        String contenString=content.getText().toString();
            if(!"".equals(titleString)&&!"".equals(contenString)){  
            			new PostThread().start();
		               
					
				}
              
		}
	});
	 
}
private RadioGroup.OnCheckedChangeListener radiogpchange = new RadioGroup.OnCheckedChangeListener() {
	
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (checkedId == mRadio1.getId()) {
			xinxi="招领：";
		
		   } else if (checkedId == mRadio2.getId()) {
			   
	       xinxi="失物:";
		   }

		
	}
};
private String send()
{
	
	SyncHttp syncHttp = new SyncHttp();
	String url = "http://minfy.cn/interface/postFound.asp";
	List<Parameter> params = new ArrayList<Parameter>();
	//Spanned tempString=  content.getText();
	params.add(new Parameter("title", xinxi+ title.getText().toString()));    
	 //params.add(new Parameter("content",  Html.toHtml(tempString)));
	 params.add(new Parameter("content",  content.getText().toString()));
	//全为第二类新闻
	params.add(new Parameter("id",  "2"));
	try
	{
		String retStr = syncHttp.httpPost(url, params);
		JSONObject jsonObject = new JSONObject(retStr);  
		int retCode = jsonObject.getInt("ret");
		if (0==retCode)
		{
			
			
			return "发布成功";
		}
	}
	
	 catch (Exception e)
	{
		e.printStackTrace();
	}
	return "发布失败";
	
}



private class PostThread extends Thread
{
	@Override
	public void run()
	{
		// 从网络上获取新闻
		String result=send();
		Message msg = mHandler.obtainMessage();
		msg.arg1 = SECOND;
		msg.obj=result;
		mHandler.sendMessage(msg);
}

}
}


