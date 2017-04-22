package com.example.poly_technic.university;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.example.poly_technic.university.service.SyncHttp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;


public class login extends Activity {
	String marke;
	String name;
	String password;
	EditText userEditText;
	EditText passEditText;
	Button loginButton,cansel;
	private final int SECEND = 1;
	int mPosition;
	Serializable s;
	private Handler mHandler = new Handler()
	{
		
		public void handleMessage(Message msg)
		{
			switch (msg.arg1) {
		

			case SECEND:
				if ((boolean)msg.obj.equals("0")) {
					Toast.makeText(login.this, "你的账号或密码不正确", Toast.LENGTH_LONG).show(); 
				}else {
			    	SharedPreferences  sharedPreferences = getSharedPreferences("login", 0);
			    	SharedPreferences.Editor  editor  =  sharedPreferences.edit();
			    	editor.putString("name",name);
			    	editor.commit();
			    	finish();
				
				}
				break;
	
			}
		
			
		}
		
	};
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	setContentView(R.layout.login);
	super.onCreate(savedInstanceState);
	 userEditText=(EditText)findViewById(R.id.username2);
	 passEditText=(EditText)findViewById(R.id.password2);
	 loginButton=(Button)findViewById(R.id.login);
	 cansel=(Button)findViewById(R.id.cansel);
	 
		  //点击登录按钮的时候
	loginButton.setOnClickListener( new View.OnClickListener() {    
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			name=userEditText.getText().toString();
			password=passEditText.getText().toString(); 
			if ("".equals(name)||"".equals(password)) {
				
				Toast.makeText(login.this, "用户名和密码不能为空",1).show();
			}
			//提交时不为空
//		    if(!"".equals(name)){  
//		    	SharedPreferences  sharedPreferences = getSharedPreferences("login", 0);
//		    	SharedPreferences.Editor  editor  =  sharedPreferences.edit();
//		    	editor.putString("name",name);
//		    	editor.commit();
//		    	finish();
//             
//           }
			else {
				//账号和密码不为空时，启动线程
				new isstudentThread(name, password).start();
				
			}
		
			
		}
	
	});
	  //点击取消按钮的时候
	cansel.setOnClickListener( new View.OnClickListener() {
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
	    	finish();
       
      }
});

	

		
			
}

private String isstudent( String cid,String snum)
{
	String url1 = "http://minfy.cn/interface/testStudent.asp";
	String params = "sno="+cid+"&pw="+snum;
	String is = "0";	
	SyncHttp syncHttp = new SyncHttp();
	try
	{
		//以Get方式请求，并获得返回结果
		String retStr = syncHttp.httpGet(url1, params);  
		JSONObject jsonObject = new JSONObject(retStr);
		 is = jsonObject.getString("ret");
		return is;	
	} catch (Exception e)
	{
		e.printStackTrace();
		
	}
	return is;
}

private class isstudentThread extends Thread
{
	String cid;
	String snum;
	public isstudentThread(String cid,String snum) {
		// TODO Auto-generated constructor stub
		this.cid=cid;
		this.snum=snum;
	}
	@Override
	public void run()
	{
		// 从网络上获取新闻
	
		String yes=isstudent(cid,snum);
		Message msg = mHandler.obtainMessage();
		msg.arg1 = SECEND;
		msg.obj=yes;
		mHandler.sendMessage(msg);
}

}

}
