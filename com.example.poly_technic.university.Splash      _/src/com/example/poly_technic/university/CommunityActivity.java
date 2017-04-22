package com.example.poly_technic.university;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.poly_technic.university.community.communityDetails;
import com.example.poly_technic.university.service.gbkHttp;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class CommunityActivity extends Activity
{
	String url = "http://minfy.cn/interface/getClub.asp";    
	SimpleAdapter CommunityActivityListAdapter;
	ListView newslist1;
	private final int FINISH = 0;
	private ArrayList<HashMap<String, Object>> comData;   
	private ArrayList<HashMap<String, Object>> data;
	
	ImageView imageView;
	private ProgressBar progressBar;
	private Handler mHandler = new Handler()
	{
		
		public void handleMessage(Message msg)
		
		{
			 ArrayList<HashMap<String, Object>> tempData=new ArrayList<HashMap<String,Object>>();
			ArrayList<HashMap<String, Object>> temp=(ArrayList<HashMap<String, Object>>) msg.obj;
			for (int i = 0; i <comData.size(); i++) {
				HashMap<String, Object> map = new HashMap();
				map.put("community_item_title", comData.get(i).get("community_item_title"));
				map.put("community_item_digest", comData.get(i).get("community_item_digest"));
				map.put("community_item_source", comData.get(i).get("community_item_source"));
				map.put("community_image", temp.get(i).get("community_image"));
				tempData.add(map);
			}
			
			CommunityActivityListAdapter=new SimpleAdapter(CommunityActivity.this, tempData, R.layout.community_list_item,
					new String[]{"community_item_title","community_item_digest","community_item_source","community_image"} , 
					new int[]{R.id.community_item_title,R.id.community_item_digest,R.id.community_item_source,R.id.community_image});

		     newslist1.setAdapter(CommunityActivityListAdapter);
		   CommunityActivityListAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
				
				public boolean setViewValue(View view, Object data,
						String textRepresentation) {
					// TODO Auto-generated method stub
					if (view instanceof ImageView && data instanceof Bitmap) {
						ImageView iv = (ImageView) view;
						iv.setImageBitmap((Bitmap) data);
						return true;
					}
				
					return false;
				}
			});
		   //  newslist1.setAdapter(simpleAdapter);
		 
		   newslist1.setAdapter(CommunityActivityListAdapter);
			//���ؽ�����
			progressBar.setVisibility(View.GONE); 
			
		}
		
	};
@Override
protected void onCreate(Bundle savedInstanceState)
{
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.community_layout);
	progressBar=(ProgressBar)findViewById(R.id.load_progress);
    //����listview�����ݼ�����ʾ��ʽ
		comData = new ArrayList<HashMap<String,Object>>();
		//��ʾ������
		progressBar.setVisibility(View.VISIBLE); 
newslist1=(ListView)findViewById(R.id.community_list);
		new getcomThread().start();
    
   //  newslist1.setAdapter(CommunityActivityListAdapter);
     //ʵ����ҳ�������ת��������ϸҳ��
     newslist1.setOnItemClickListener(new OnItemClickListener() {


		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			//��������б�ʱ������Ϣ������ѧ�š�����id.
			Intent intent=new Intent(CommunityActivity.this, communityDetails.class);
			intent.putExtra("position",(String) comData.get(position).get("community_id"));
			intent.putExtra("data", comData.get(position));
			startActivity(intent);
			
		}
	});
}


private void getComData(List<HashMap<String, Object>> newsList)
	{
	
		gbkHttp syncHttp = new gbkHttp();
		try
		{
			//��Get��ʽ���󣬲���÷��ؽ��
			String retStr = syncHttp.httpGet(url, null);
			JSONObject jsonObject = new JSONObject(retStr);
			//��ȡ�����룬0��ʾ�ɹ�
			int retCode = jsonObject.getInt("ret");
			if (0==retCode)
			{
				JSONObject dataObject = jsonObject.getJSONObject("data");
				//��ȡ������Ŀ
				int totalnum = dataObject.getInt("totalnum");
				if (totalnum>0)
				{
					//��ȡ�������ż���
					JSONArray newslist = dataObject.getJSONArray("club");
					for(int i=0;i<newslist.length();i++)
					{
						JSONObject newsObject = (JSONObject)newslist.opt(i); 
						HashMap<String, Object> hashMap = new HashMap<String, Object>();
						hashMap.put("community_item_title", newsObject.getString("cl_name"));
						hashMap.put("community_item_digest", newsObject.getString("s_name"));
						hashMap.put("community_item_source", newsObject.getString("s_phone"));
						hashMap.put("community_image", newsObject.getString("cl_picture"));
						//��������id
						hashMap.put("community_id", newsObject.getString("cl_no"));
						newsList.add(hashMap);
					}
					
				} 
				
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
			
		}
	}

	private class getcomThread extends Thread
	{
		@Override
		public void run()
		{
			// �������ϻ�ȡ����
			getComData(comData);
			ArrayList<HashMap<String, Object>> rundata=getData();
			
			Message msg = mHandler.obtainMessage();
			msg.arg1 = FINISH;
			msg.obj=rundata;
			mHandler.sendMessage(msg);
	}

}
	public Bitmap returnBitMap(String url1) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			myFileUrl = new URL(url1);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		return bitmap;
	}
	public  ArrayList<HashMap<String, Object>>  getData( ) { 
		data = new  ArrayList<HashMap<String, Object>>();
		for (int i = 0; i <comData.size(); i++) {
			HashMap<String, Object> map = new HashMap();

			map.put("community_image", returnBitMap((String) comData.get(i).get("community_image")));
			data.add(map);
		}
		return data;

	}
	/** 
	 * �˵������ؼ���Ӧ 
	 */  
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    // TODO Auto-generated method stub  
	    if(keyCode == KeyEvent.KEYCODE_BACK)  
	       {    
	           exitBy2Click();      //����˫���˳�����  
	       }  
	    return false;  
	}  
	/** 
	   * ˫���˳����� 
	   */  
	  private static Boolean isExit = false;  
	    
	  private void exitBy2Click() {  
	      Timer tExit = null;  
	      if (isExit == false) {  
	          isExit = true; // ׼���˳�  
	          Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();  
	          tExit = new Timer();  
	          tExit.schedule(new TimerTask() {  
	              @Override  
	              public void run() {  
	                  isExit = false; // ȡ���˳�  
	              }  
	          }, 2000); // ���2������û�а��·��ؼ�����������ʱ��ȡ�����ղ�ִ�е�����  
	    
	      } else {  
	          finish();  
	          System.exit(0);  
	      }  

}

}
