package com.example.poly_technic.university.study;

import com.example.poly_technic.university.R;
import com.example.poly_technic.university.life.Baozoumanhua;
import com.example.poly_technic.university.life.Qiushibaike;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Button;
import android.widget.TextView;

public class learning_materials extends Activity
{
	
	TextView textview1,textview2;
Button backbutton;
	
	
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.learning_materials);
    backbutton=(Button)this.findViewById(R.id.newsdetails_titlebar_previous);
    textview1=(TextView)this.findViewById(R.id.textbibei);
    textview2=(TextView)this.findViewById(R.id.texthao123);
    backbutton.setOnClickListener(new View.OnClickListener()
	{
		
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			finish();
		}
	});
    textview1.setOnClickListener(new View.OnClickListener()
	{
		
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			Intent intent1 = new Intent(getApplicationContext(),
					Lizhi.class);
			startActivity(intent1);
		}
	});
    textview2.setOnClickListener(new View.OnClickListener()
  		{
  			
  			public void onClick(View v)
  			{
  				// TODO Auto-generated method stub
  				Intent intent2 = new Intent(getApplicationContext(),
  						Keke.class);
  				startActivity(intent2);
  			}
  		});
    
}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
}
}
