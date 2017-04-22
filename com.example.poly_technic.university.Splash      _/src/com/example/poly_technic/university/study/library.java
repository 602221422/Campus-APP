package com.example.poly_technic.university.study;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.poly_technic.university.R;
import com.example.poly_technic.university.model.Parameter;
import com.example.poly_technic.university.service.SyncHttp;
import com.example.poly_technic.university.service.gbkHttp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

public class library extends Activity
{
	Button back;
	String url = "http://minfy.cn/interface/getbook.asp";    
	SimpleAdapter libraryListAdapter;
	ListView newslist1;
	private final int FINISH = 0;
	private final int TH = 1;
	String retCode = null;
	int i;
	private ArrayList<HashMap<String, Object>> libraryData;   
	private ArrayList<HashMap<String, Object>> data;
	
	ImageView imageView;
	private ProgressBar progressBar;
	private EditText  searchinputText;
	private Handler mHandler = new Handler()
	{
		
		public void handleMessage(Message msg)
		
		{
			switch (msg.arg1) {
		
			case FINISH:
			 ArrayList<HashMap<String, Object>> tempData=new ArrayList<HashMap<String,Object>>();
			ArrayList<HashMap<String, Object>> temp=(ArrayList<HashMap<String, Object>>) msg.obj;
			for (int i = 0; i <libraryData.size(); i++) {
				HashMap<String, Object> map = new HashMap();
				map.put("library_item_title", libraryData.get(i).get("library_item_title"));
				map.put("library_item_digest", libraryData.get(i).get("library_item_digest"));
				map.put("library_item_source", libraryData.get(i).get("library_item_source"));
				map.put("library_image", temp.get(i).get("library_image"));
				tempData.add(map);
			}
			
			libraryListAdapter=new SimpleAdapter(library.this, tempData, R.layout.library_list,
					new String[]{"library_item_title","library_item_digest","library_item_source","library_image"} , 
					new int[]{R.id.library_item_title,R.id.library_item_digest,R.id.library_item_source,R.id.library_image});

		     newslist1.setAdapter(libraryListAdapter);
		   libraryListAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
				
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
		 
		   newslist1.setAdapter(libraryListAdapter);
			//隐藏进度条
			progressBar.setVisibility(View.GONE); 
			break;
			case TH:
				for ( i=0;i<libraryData.size();i++){
					if(libraryData.get(i).get("library_id").equals(retCode))
						break;
				}
				if (retCode.equals("0")) {
					Toast.makeText(library.this, "没有这本书", 1).show();
					
				
					
				}else {
					Intent intent = new Intent(library.this, libraryDetail.class);
					//intent.putExtra("position",(String)libraryData.get(retCode).get("library_id"));
					intent.putExtra("position",retCode);
					intent.putExtra("data",libraryData.get(i));
					startActivity(intent);
					
				}
				

				break;
			
		}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
				super.onCreate(savedInstanceState);
				setContentView(R.layout.library);
				back=(Button) this.findViewById(R.id.library_titlebar_previous);
				back.setOnClickListener(new View.OnClickListener()
				{
					
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						finish();
					}
				});
				progressBar=(ProgressBar)findViewById(R.id.load_progress);
			    //设置listview的内容及其显示方式
					libraryData = new ArrayList<HashMap<String,Object>>();
					//显示进度条
					progressBar.setVisibility(View.VISIBLE); 
					newslist1=(ListView)findViewById(R.id.library_list);
					 searchinputText = (EditText) findViewById(R.id.search_input_text );
					new getlibraryThread().start();		
					newslist1.setOnItemClickListener(new OnItemClickListener() {

						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							Intent intent=new Intent(library.this, libraryDetail.class);
							intent.putExtra("position",(String)libraryData.get(position).get("library_id"));
							intent.putExtra("data",libraryData.get(position));
							startActivity(intent);
							
						}
					});
					 Button searchButton=(Button)findViewById(R.id.search_send);
					    searchButton.setOnClickListener(new View.OnClickListener() {
							
							public void onClick(View v) {
								// TODO Auto-generated method stub
								 String content = searchinputText.getText().toString();  
						            if(!"".equals(content)){  
						                
						            
						               
						                new searchThread().start();
						                 
								
							}
							}
						});
			}


			private void getlibraryData(List<HashMap<String, Object>> newsList)
				{
				
					gbkHttp syncHttp = new gbkHttp();
					try
					{
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
								JSONArray newslist = dataObject.getJSONArray("books");
								for(int i=0;i<newslist.length();i++)
								{
									JSONObject newsObject = (JSONObject)newslist.opt(i); 
									HashMap<String, Object> hashMap = new HashMap<String, Object>();
									hashMap.put("library_item_title", newsObject.getString("b_name"));
									hashMap.put("library_item_digest", newsObject.getString("b_author"));
									hashMap.put("library_item_source", newsObject.getString("b_press"));
									hashMap.put("library_image", newsObject.getString("b_picture"));
									//用来接收id
									hashMap.put("library_id", newsObject.getString("b_no"));
									newsList.add(hashMap);
								}
								
							} 
							
						}
						
					} catch (Exception e)
					{
						e.printStackTrace();
						
					}
				}

				private class getlibraryThread extends Thread
				{
					@Override
					public void run()
					{
						// 从网络上获取新闻
						getlibraryData(libraryData);
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
				//此函数通过调用returnBitMap（）函数，将libraryData全局数据中的图片路径，转换为图片
				public  ArrayList<HashMap<String, Object>>  getData( ) { 
					data = new  ArrayList<HashMap<String, Object>>();
					for (int i = 0; i <libraryData.size(); i++) {
						HashMap<String, Object> map = new HashMap();

						map.put("library_image", returnBitMap((String) libraryData.get(i).get("library_image")));
						data.add(map);
					}
					return data;

				}
				private void search()
				{
				
					SyncHttp syncHttp = new SyncHttp();
					String url = "http://minfy.cn/interface/searchBook.asp";
					List<Parameter> params = new ArrayList<Parameter>();
					params.add(new Parameter("search",  searchinputText.getText().toString()));
					try
					{
						String retStr = syncHttp.httpPost(url, params);
						JSONObject jsonObject = new JSONObject(retStr);
						 retCode = jsonObject.getString("ret");
						
					}
					 catch (Exception e)
					{
						e.printStackTrace();
					}
				
					
				}



				private class searchThread extends Thread
				{
					@Override
					public void run()
					{
						// 从网络上获取新闻
						search();
						Message msg = mHandler.obtainMessage();
						msg.arg1 = TH;
						mHandler.sendMessage(msg);
				}

				}

			}
