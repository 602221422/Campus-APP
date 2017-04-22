package com.example.poly_technic.university.study;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.poly_technic.university.R;
import com.example.poly_technic.university.login;
import com.example.poly_technic.university.service.SyncHttp;

public class schedule extends Activity  implements OnItemSelectedListener{
	Button backbutton;
	private Spinner spinner;
	private ArrayAdapter<String> arrayadapter;
	ArrayList<HashMap<String, Object>>  schedulelist=new ArrayList<HashMap<String,Object>>();
	private final int FINISH = 0;
	private final int SECOND = 1;
	String tuNumber;
	String sposition;
	WebView webView;
	private Handler mHandler = new Handler()
	{
		
		public void handleMessage(Message msg)
		{
			switch (msg.arg1) {
			case FINISH:
			   
				dataList.add("   ");
				for (int i = 0; i < schedulelist.size(); i++) {
					dataList.add((String) schedulelist.get(i).get("s_term"));
					
				}
		        arrayadapter=new ArrayAdapter<String>(schedule.this, android.R.layout.simple_dropdown_item_1line, dataList);
		        spinner.setAdapter(arrayadapter);
			
				break;
			

		
			}
		
			
		}
		
	};

	
	List<String>  dataList =new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule); 
        spinner=(Spinner)findViewById(R.id.schedule);
        backbutton=(Button) this.findViewById(R.id.newsdetails_titlebar_previous);
        backbutton.setOnClickListener(new View.OnClickListener()
		{
			
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}
		});
        new getListThread().start(); 	
        spinner.setOnItemSelectedListener(this);
         webView = (WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
     
    }
//下拉菜单的监听器
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		if (position==0) {
			Toast.makeText(schedule.this,"请选择有效地数据", Toast.LENGTH_SHORT).show();
		}
		else {
			// TODO Auto-generated method stub
			String temp= (String) schedulelist.get(position-1).get("s_id");
//			Toast.makeText(schedule.this,temp, Toast.LENGTH_SHORT).show();
			SharedPreferences  sharedPreferences = getSharedPreferences("login", 0);
			 tuNumber = sharedPreferences.getString("name",null);
			//当学号为空是弹出登陆界面
			if (tuNumber==null) {
				Intent intent=new Intent(schedule.this, login.class);
				startActivity(intent); 
			}
		else {
			String url="http://minfy.cn/interface/getSchedule.asp?tmid="+temp+"&sid="+tuNumber;
			webView.loadUrl(url);
		}
			
		}
		
		
	}
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	private void getList(ArrayList<HashMap<String, Object>> newsList)
	{
		
		//请求URL和字符串
		String url = "http://minfy.cn/interface/getList.asp";
		SyncHttp syncHttp = new SyncHttp();
		try
		{
			//以Get方式请求，并获得返回结果
			String retStr = syncHttp.httpGet(url, null);
			JSONObject jsonObject = new JSONObject(retStr);
			//获取返回码，0表示成功
			int retCode = jsonObject.getInt("ret");
			if (0==retCode)
			{
				JSONObject dataObject = jsonObject.getJSONObject("data");
				//获取返回数目
				int totalnum = dataObject.getInt("totalnum");
				if (totalnum>0)
				{
					//获取返回新闻集合
					JSONArray newslist = dataObject.getJSONArray("newslist");
					for(int i=0;i<newslist.length();i++)
					{
						JSONObject newsObject = (JSONObject)newslist.opt(i); 
						HashMap<String, Object> hashMap = new HashMap<String, Object>();
						hashMap.put("s_id", newsObject.getString("tm_id"));
						hashMap.put("s_term", newsObject.getString("tm_term"));
						newsList.add(hashMap);
					
					}
			
				}
			
		} 
		}catch (Exception e)
		{
			e.printStackTrace();
		
		}
	}
	private class getListThread extends Thread
	{
		@Override
		public void run()
		{
			// 从网络上获取新闻
		
			getList(schedulelist);
			Message msg = mHandler.obtainMessage();
			msg.arg1 = FINISH;
			mHandler.sendMessage(msg);
	}

	}
}

