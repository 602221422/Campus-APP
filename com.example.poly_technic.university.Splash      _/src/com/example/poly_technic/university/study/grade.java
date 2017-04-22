package com.example.poly_technic.university.study;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.poly_technic.university.R;
import com.example.poly_technic.university.login;
import com.example.poly_technic.university.service.SyncHttp;
import com.example.poly_technic.university.service.gbkHttp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class grade extends Activity  implements OnItemSelectedListener{
	private Spinner spinner;
	Button back;
	private ArrayAdapter<String> arrayadapter;
	ArrayList<HashMap<String, Object>>  schedulelist=new ArrayList<HashMap<String,Object>>();
	private final int FINISH = 0;
	private final int TH = 1;
	String tuNumber;
	String sposition;
	WebView webView;
	ListView listView;
	String temp;
	private ProgressBar progressBar;
	private ArrayList<HashMap<String, Object>> gradeList=new ArrayList<HashMap<String,Object>>();
	SimpleAdapter ListAdapter;
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
		        arrayadapter=new ArrayAdapter<String>(grade.this, android.R.layout.simple_dropdown_item_1line, dataList);
		        spinner.setAdapter(arrayadapter);
			
				break;
			case TH:
				ListAdapter=new SimpleAdapter(grade.this, gradeList, R.layout.grade_item,
				                              new String[]{"kemu","chengji","xuefeng"} , 
				                              new int[]{R.id.kemu,R.id.chengji,R.id.xuefeng});

			     listView.setAdapter(ListAdapter);
			   //隐藏进度条
					progressBar.setVisibility(View.GONE); 
				
					break;
		
			}
		
			
		}
		
	};

	
	List<String>  dataList =new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade); 
        spinner=(Spinner)findViewById(R.id.schedule);
        listView=(ListView)findViewById(R.id.list1);
        new getListThread().start(); 
    	progressBar=(ProgressBar)findViewById(R.id.load_progress);
    
        spinner.setOnItemSelectedListener(this);
    	back=(Button) this.findViewById(R.id.newsdetails_titlebar_previous);
    	back.setOnClickListener(new View.OnClickListener()
    	{
    		
    		public void onClick(View v)
    		{
    			// TODO Auto-generated method stub
    			finish();
    		}
    	});
      
    }

	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		gradeList.clear();
		
		if (position==0) {
			Toast.makeText(grade.this,"请选择有效地数据", Toast.LENGTH_SHORT).show();
		}
		else {
			// TODO Auto-generated method stub
			temp= (String) schedulelist.get(position-1).get("s_id");
//			Toast.makeText(schedule.this,temp, Toast.LENGTH_SHORT).show();
			SharedPreferences  sharedPreferences = getSharedPreferences("login", 0);
			 tuNumber = sharedPreferences.getString("name",null);
			//当学号为空是弹出登陆界面
			if (tuNumber==null) {
				Intent intent=new Intent(grade.this, login.class);
				startActivity(intent); 
			}
		else {
			
		//启动获取成绩的线程
			//显示进度条
			progressBar.setVisibility(View.VISIBLE); 
			new getgradeThread().start();
			
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
	
		
			getList(schedulelist);
			Message msg = mHandler.obtainMessage();
			msg.arg1 = FINISH;
			mHandler.sendMessage(msg);
	}

	}
	private void getGrade(List<HashMap<String, Object>> newsList)
	{
		String url="http://minfy.cn/interface/getGrade.asp";
		String params = "sid=" + tuNumber+"&xq="+temp;
		System.out.println(temp);
	
		SyncHttp syncHttp = new SyncHttp();
		try
		{
			//以Get方式请求，并获得返回结果
			String retStr = syncHttp.httpGet(url, params);
			System.out.println(retStr);
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
						hashMap.put("kemu", newsObject.getString("c_name"));
						hashMap.put("chengji", newsObject.getString("c_grade"));
						hashMap.put("xuefeng", newsObject.getString("c_gpa"));
						newsList.add(hashMap);
					}
					
				} 
				
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
			
		}
	}

	private class getgradeThread extends Thread
	{
		@Override
		public void run()
		{
			
			getGrade(gradeList);
			
			Message msg = mHandler.obtainMessage();
			msg.arg1 = TH;
			mHandler.sendMessage(msg);
	}

}
}

