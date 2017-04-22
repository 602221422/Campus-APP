package com.example.poly_technic.university.study;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.example.poly_technic.university.R;
import com.example.poly_technic.university.service.SyncHttp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class libraryDetail extends Activity {

	public String body;
	private final int FINISH = 0;
	private ViewFlipper BodyFlipper;
	private LayoutInflater BodyInflater;
	View BodyLayout;
	String mPosition;
	 String tuNumber=null;
	 Serializable s;
	private ArrayList<HashMap<String, Object>> detailData;
	private ArrayList<HashMap<String, Object>> mNewsData;
	private Handler mHandler = new Handler()
	{
		
		public void handleMessage(Message msg)
		{
			switch (msg.arg1) {
			case FINISH:
				TextView newsDetails = (TextView)BodyLayout.findViewById(R.id.library_body_details);
				newsDetails.setText(Html.fromHtml((String) msg.obj));			
				//��������ͼ��ӵ�Flipper��
				BodyFlipper = (ViewFlipper)findViewById(R.id.library_body_flipper);
				BodyFlipper.addView(BodyLayout);
				break;

		
			}
		
			
		}
		
	};
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.librarydetail);
		// ��ȡ���ݵ�����
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		//��ȡ���λ��
		mPosition = bundle.getString("position");	
		 s = bundle.getSerializable("data");
		HashMap<String, Object> hashMap =(HashMap<String, Object>)s;
		String library_name =  (String) hashMap.get("library_item_title");//����
		String libraryer_name =  (String) hashMap.get("library_item_digest");//����
		String library_phone =  (String) hashMap.get("library_item_source");//������
		TextView Detailstitlebar = (TextView)findViewById(R.id.librarydetails_titlebar_title);
		Detailstitlebar.setText(library_name);		
		//��̬����������ͼ
		 BodyInflater = getLayoutInflater();
		 BodyLayout = BodyInflater.inflate(R.layout.library_body, null);
		TextView newsTitle = (TextView)BodyLayout.findViewById(R.id.library_body_title);
		newsTitle.setText(library_name);
		TextView newsPtimeAndSource = (TextView)BodyLayout.findViewById(R.id.library_body_ptime_source);
		newsPtimeAndSource.setText(libraryer_name+"         "+library_phone);
		new getdetailsThread().start();
		//��ǰҳ��ķ��ذ�ť
		Button DetailsPref = (Button)findViewById(R.id.librarydetails_titlebar_previous);
		DetailsPref.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	/**
	 * ��ȡ���ŵ���ϸ��Ϣ
	 * 
	 * @return
	 */
	private String getComDetails()
	{
		String brief = null;
		SyncHttp syncHttp = new SyncHttp();
		String url = "http://minfy.cn/interface/bookBrief.asp";
		String params = "id=" + mPosition;
		 
		try
		{
			String retString = syncHttp.httpGet(url, params);
			JSONObject jsonObject = new JSONObject(retString);
			// ��ȡ�����룬0��ʾ�ɹ�
			int retCode = jsonObject.getInt("ret");
			if (0 == retCode)
			{
				JSONObject dataObject = jsonObject.getJSONObject("data");
				JSONObject newsObject = dataObject.getJSONObject("club");
				 brief = newsObject.getString("b_brief");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return brief;
	}
	private class getdetailsThread extends Thread
	{
		@Override
		public void run()
		{
			// �������ϻ�ȡ����
		
			String threadmsg=getComDetails();
			Message msg = mHandler.obtainMessage();
			msg.arg1 = FINISH;
			msg.obj=threadmsg;
			mHandler.sendMessage(msg);
	}
	}


}


