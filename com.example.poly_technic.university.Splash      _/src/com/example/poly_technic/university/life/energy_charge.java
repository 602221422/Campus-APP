package com.example.poly_technic.university.life;


import android.app.Activity;

import com.example.poly_technic.university.R;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.example.poly_technic.university.community.CommunityChat;
import com.example.poly_technic.university.community.communityDetails;
import com.example.poly_technic.university.service.SyncHttp;
import com.example.poly_technic.university.util.PickerView;
import com.example.poly_technic.university.util.PickerView.onSelectListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.os.Bundle;

public class energy_charge extends Activity{
		PickerView minute_pv;
		PickerView second_pv1;
		PickerView second_pv2;
		PickerView second_pv3;
		String chargetextpv="D座";
		String chargetextpv1="5";
		String chargetextpv2="5";
		String chargetextpv3="5";
		String chargetextpv4="0";
		private final int FINISH = 0;
		private Handler mHandler = new Handler()
		{
			
			public void handleMessage(Message msg)
			{
				switch (msg.arg1) {
				case FINISH:
					TextView resultTextView=(TextView)findViewById(R.id.check_result);
					if (msg.obj.equals("x")) {
						
						 resultTextView.setText( "没有这个宿舍号");
					}else {
						 resultTextView.setText( "余额：        "+msg.obj+"￥");
					}
					 
					break;

			
				}
			
				
			}
			
		};

		  protected void onCreate(Bundle savedInstanceState)
		    {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.energy_charge);
		        minute_pv = (PickerView) findViewById(R.id.minute_pv);
		        second_pv1 = (PickerView) findViewById(R.id.second_pv1);
		        second_pv2 = (PickerView) findViewById(R.id.second_pv2);
		        second_pv3 = (PickerView) findViewById(R.id.second_pv3);
		        List<String> data = new ArrayList<String>();
		        List<String> seconds1 = new ArrayList<String>();
		        List<String> seconds2 = new ArrayList<String>();
		        List<String> seconds3 = new ArrayList<String>();
		      
		            data.add("A座" );
		            data.add("B座" );
		            data.add("D座" );
		            data.add("E座" );
		            data.add("一舍" );
		            data.add("二舍" );
		            data.add("三舍" );
		            data.add("四舍" );
		            data.add("五舍" );
		            data.add("六舍" );
		            data.add("七舍" );
		            data.add("八舍" );
		        
		        for (int i = 0; i < 10; i++)
		        {
		            seconds1.add(""+i);
		            seconds2.add(""+i);
		            seconds3.add(""+i);
		        }
		        minute_pv.setData(data);
		        second_pv1.setData(seconds1);
		        second_pv2.setData(seconds2);
		        second_pv3.setData(seconds3);
		        minute_pv.setOnSelectListener(new onSelectListener() 
		        {
		 
		            public void onSelect(String text)
		            {
		            
		                chargetextpv=text;
		                chargetextpv4=chargetextpv+chargetextpv1+chargetextpv2+chargetextpv3;
				        Toast.makeText(energy_charge.this, chargetextpv4, Toast.LENGTH_SHORT).show();
		            }
		        });
		       
		        second_pv1.setOnSelectListener(new onSelectListener()
		        {
		 
		            public void onSelect(String text)
		            {
		               
		                chargetextpv1=text;
		                chargetextpv4=chargetextpv+chargetextpv1+chargetextpv2+chargetextpv3;
				        Toast.makeText(energy_charge.this, chargetextpv4, Toast.LENGTH_SHORT).show();
		            }
		        });
		        second_pv2.setOnSelectListener(new onSelectListener()
		        {
		 
		            public void onSelect(String text)
		            {
		               
		                chargetextpv2=text;
		                chargetextpv4=chargetextpv+chargetextpv1+chargetextpv2+chargetextpv3;
				        Toast.makeText(energy_charge.this, chargetextpv4, Toast.LENGTH_SHORT).show();
		            }
		        });
		        second_pv3.setOnSelectListener(new onSelectListener()
		        {
		 
		            public void onSelect(String text)
		            {
		                
		                chargetextpv3=text;
		                chargetextpv4=chargetextpv+chargetextpv1+chargetextpv2+chargetextpv3;
				        Toast.makeText(energy_charge.this, chargetextpv4, Toast.LENGTH_SHORT).show();
		            }
		        });
		        //点击查询按钮时启动线程
		        Button checkButton=(Button)findViewById(R.id.energy_check);
		        checkButton.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						new checkThread(chargetextpv4).start();
						
					}
				});
		        //点击返回按钮
		        Button backbButton=(Button)findViewById(R.id.energy_titlebar_previous);
		        backbButton.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});
		     
		    }
		  private String checkEnergy( String homenum)
			{
				String url1 = "http://minfy.cn/interface/elecTest.asp";

				String params = "eno="+homenum;
				String money = "0";	
				SyncHttp syncHttp = new SyncHttp();
				try
				{
					//以Get方式请求，并获得返回结果
					String retStr = syncHttp.httpGet(url1, params);
					JSONObject jsonObject = new JSONObject(retStr);
					 money = jsonObject.getString("ret");
					return money;	
				} catch (Exception e)
				{
					e.printStackTrace();
					
				}
				return money;
			}

			private class checkThread extends Thread
			{
			
				String snum;
				public checkThread(String snum) {
					// TODO Auto-generated constructor stub
					this.snum=snum;
				}
				@Override
				public void run()
				{
					// 从网络上获取新闻
				
					String yes=checkEnergy(snum);
					Message msg = mHandler.obtainMessage();
					msg.arg1 = FINISH;
					msg.obj=yes;
					mHandler.sendMessage(msg);
			}

		 
			}
}

