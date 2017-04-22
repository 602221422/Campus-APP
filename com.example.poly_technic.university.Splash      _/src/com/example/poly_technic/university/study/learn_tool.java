package com.example.poly_technic.university.study;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.poly_technic.university.R;

public class learn_tool extends Activity implements OnClickListener {
TextView textview1,textview2,textview3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_tool);
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
        textview1=(TextView)this.findViewById(R.id.textjisuan);
        textview2=(TextView)this.findViewById(R.id.textcidian);
        textview3=(TextView)this.findViewById(R.id.jishiben);
        textview1.setOnClickListener(new View.OnClickListener()
		{
			
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(getApplicationContext(),
						Calculator.class);
				startActivity(intent1);
			}
		});
        textview2.setOnClickListener(new View.OnClickListener()
      		{
      			
      			public void onClick(View v)
      			{
      				// TODO Auto-generated method stub
      				Intent intent2 = new Intent(getApplicationContext(),
      						Dictionary.class);
      				startActivity(intent2);
      			}
      		});
        textview3.setOnClickListener(new View.OnClickListener()
  		{
  			
  			public void onClick(View v)
  			{
  				// TODO Auto-generated method stub
  				Intent intent2 = new Intent(getApplicationContext(),
  						Jishiben.class);
  				startActivity(intent2);
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