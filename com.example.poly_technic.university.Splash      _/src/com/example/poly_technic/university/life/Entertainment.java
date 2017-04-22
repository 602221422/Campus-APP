package com.example.poly_technic.university.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.poly_technic.university.R;

public class Entertainment extends Activity implements OnClickListener {
TextView textview1,textview2,textview3,textview4;
Button backbutton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entertainment);
        backbutton=(Button)this.findViewById(R.id.newsdetails_titlebar_previous);
        textview1=(TextView)this.findViewById(R.id.textqiushi);
        textview2=(TextView)this.findViewById(R.id.textbaozou);
        textview3=(TextView)this.findViewById(R.id.textfeixing);
        textview4=(TextView)this.findViewById(R.id.text2048);
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
						Qiushibaike.class);
				startActivity(intent1);
			}
		});
        textview2.setOnClickListener(new View.OnClickListener()
      		{
      			
      			public void onClick(View v)
      			{
      				// TODO Auto-generated method stub
      				Intent intent2 = new Intent(getApplicationContext(),
      						Baozoumanhua.class);
      				startActivity(intent2);
      			}
      		});
        textview3.setOnClickListener(new View.OnClickListener()
  		{
  			
  			public void onClick(View v)
  			{
  				// TODO Auto-generated method stub
  				Intent intent3 = new Intent(getApplicationContext(),
  						com.k.feiji.FeiJi_Menu.class);
  				startActivity(intent3);
  			}
  		});
        textview4.setOnClickListener(new View.OnClickListener()
      		{
      			
      			public void onClick(View v)
      			{
      				// TODO Auto-generated method stub
      				Intent intent4 = new Intent(getApplicationContext(),
      						com.example.c2048.Main2048.class);
      				startActivity(intent4);
      			}
      		});
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		
	}
		
	}