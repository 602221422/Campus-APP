package com.example.poly_technic.university.life;

import com.example.poly_technic.university.R;
import com.example.poly_technic.university.R.id;
import com.example.poly_technic.university.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Jiadeweidao extends Activity
{
@Override
protected void onCreate(Bundle savedInstanceState)
{
	
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.jiadeweidao);
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
	 Button button2=(Button) this.findViewById(R.id.button2);
	 button2.setOnClickListener(new OnClickListener()
	{
		
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			Uri uri2=Uri.parse("tel:13384196299");
			Intent intent2=new Intent(Intent.ACTION_CALL,uri2);
			startActivity(intent2);
		}
	});
}
}
