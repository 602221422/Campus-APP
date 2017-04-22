package com.example.poly_technic.university;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class About extends Activity
{
	Button backbutton;
	TextView banben;
	String banbenxinxi;
@Override
protected void onCreate(Bundle savedInstanceState)
{
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.about);
	banben=(TextView) this.findViewById(R.id.banben);
	   backbutton=(Button)this.findViewById(R.id.newsdetails_titlebar_previous);
       backbutton.setOnClickListener(new View.OnClickListener()
		{
			
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}
		});
	try
	{
		banben.setText(getVersion());
	} catch (NameNotFoundException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	};
}

 /**
  * 获取版本号
  * @return 当前应用的版本号
 * @throws NameNotFoundException 
  */
 public String getVersion() throws NameNotFoundException {
         PackageManager manager = this.getPackageManager();
        PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
         String version = info.versionName;
         return  version;
 }
 
 /** 
	 * 菜单、返回键响应 
	 */  
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    // TODO Auto-generated method stub  
	    if(keyCode == KeyEvent.KEYCODE_BACK)  
	       {    
	    	finish();      
	       }  
	    return false;  
	}  
}
