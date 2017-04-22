package com.example.poly_technic.university.community;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.poly_technic.university.CommentsActivity;
import com.example.poly_technic.university.CommunityActivity;
import com.example.poly_technic.university.MainActivity;
import com.example.poly_technic.university.R;
import com.example.poly_technic.university.login;
import com.example.poly_technic.university.service.SyncHttp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Paint.Join;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.R.integer;
import android.app.Activity;
import android.os.Bundle;

public class communityDetails extends Activity {
	public String body;
	private final int FINISH = 0;
	private final int SECEND = 1;
	private final int THIRD = 2;
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
				TextView newsDetails = (TextView)BodyLayout.findViewById(R.id.community_body_details);
				newsDetails.setText(Html.fromHtml((String) msg.obj));			
				//��������ͼ��ӵ�Flipper��
				BodyFlipper = (ViewFlipper)findViewById(R.id.community_body_flipper);
				BodyFlipper.addView(BodyLayout);
				break;

			case SECEND:
				if ((boolean)msg.obj.equals("2")) {
					Toast.makeText(communityDetails.this, "���ѱ�Э���Ա�����ٴμ���", Toast.LENGTH_LONG).show(); 
				}else {
					Toast.makeText(communityDetails.this, "���ѳɹ����뱾Э��", Toast.LENGTH_LONG).show();
				}
				break;
			case THIRD:
				if ((boolean)msg.obj.equals("2")) {
					//�ǻ�Ա����ת
					Intent intent = new Intent(communityDetails.this, CommunityChat.class);
					intent.putExtra("id", mPosition);
					intent.putExtra("sno", tuNumber);
					startActivity(intent);
				}else {
					Toast.makeText(communityDetails.this, "�㲻�Ǳ�Э���Ա,���ȼ��뱾Э��", Toast.LENGTH_LONG).show();
				}
				
				break;
			}
		
			
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.communitydetails);
		// ��ȡ���ݵ�����
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		//��ȡ���λ��
		mPosition = bundle.getString("position");
		System.out.println(mPosition);
		//�ڹ�������л�ȡѧ��
		
		 s = bundle.getSerializable("data");
		//���ݹ�����mnewdata
//		mNewsData=new ArrayList<HashMap<String,Object>>(); 
		
//		mNewsData = (ArrayList<HashMap<String, Object>>) s;
		HashMap<String, Object> hashMap =(HashMap<String, Object>)s;
		String community_name =  (String) hashMap.get("community_item_title");
		String communityer_name =  (String) hashMap.get("community_item_digest");
		String community_phone =  (String) hashMap.get("community_item_source");
		TextView Detailstitlebar = (TextView)findViewById(R.id.communitydetails_titlebar_title);
		Detailstitlebar.setText(community_name);		
		//��̬����������ͼ
		 BodyInflater = getLayoutInflater();
		 BodyLayout = BodyInflater.inflate(R.layout.community_body, null);
		TextView newsTitle = (TextView)BodyLayout.findViewById(R.id.community_body_title);
		newsTitle.setText(community_name);
		TextView newsPtimeAndSource = (TextView)BodyLayout.findViewById(R.id.community_body_ptime_source);
		newsPtimeAndSource.setText(communityer_name+"         "+community_phone);
//		TextView newsDetails = (TextView)BodyLayout.findViewById(R.id.community_body_details);
//		newsDetails.setText(Html.fromHtml(NEWS));
//		
////		//��������ͼ��ӵ�Flipper��
////		BodyFlipper = (ViewFlipper)findViewById(R.id.community_body_flipper);
////		BodyFlipper.addView(BodyLayout);
		new getdetailsThread().start();
		//���찴ť�����ô����¼�
		Button chatbuttButton=(Button)BodyLayout.findViewById(R.id.chat_button);
		chatbuttButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				SharedPreferences  sharedPreferences = getSharedPreferences("login", 0);
				 tuNumber =sharedPreferences.getString("name",null);
				//��ѧ��Ϊ���ǵ�����½����
				if (tuNumber==null) {
					Intent intent=new Intent(communityDetails.this, login.class);
					startActivity(intent); 
				}
			else {
				new ischatThread(mPosition, tuNumber).start();
			}
				
				
			}
		});
		//�������Ű�ť�����ô����¼�
		Button   joinButton=(Button)findViewById(R.id.communitydetails_titlebar_comments);
		joinButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences  sharedPreferences = getSharedPreferences("login", 0);
				 tuNumber =sharedPreferences.getString("name",null);
				//��ѧ��Ϊ���ǵ�����½����
				if (tuNumber==null) {
					Intent intent=new Intent(communityDetails.this, login.class);
					startActivity(intent); 
					
				}
			else {
				new isjoinThread(mPosition, tuNumber).start();
			}
				
				
			}
		});
		//��ǰҳ��ķ��ذ�ť
		Button DetailsPref = (Button)findViewById(R.id.communitydetails_titlebar_previous);
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
		String url = "http://minfy.cn/interface/clubBrief.asp";
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
				 brief = newsObject.getString("cl_brief");
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
	
	//�ж��Ƿ��Ǳ�Э���Ա
	private String isJoin( String cid,String snum)
	{
		String url1 = "http://minfy.cn/interface/memTest.asp";
		String params = "id="+cid+"&sno="+snum;
		String is = "0";	
		SyncHttp syncHttp = new SyncHttp();
		try
		{
			//��Get��ʽ���󣬲���÷��ؽ��
			String retStr = syncHttp.httpGet(url1, params);
			JSONObject jsonObject = new JSONObject(retStr);
			 is = jsonObject.getString("ret");
			return is;	
		} catch (Exception e)
		{
			e.printStackTrace();
			
		}
		return is;
	}


	private class isjoinThread extends Thread
	{
		String cid;
		String snum;
		public isjoinThread(String cid,String snum) {
			// TODO Auto-generated constructor stub
			this.cid=cid;
			this.snum=snum;
		}
	
		@Override
		public void run()
		{
			// �������ϻ�ȡ����
		
			String yes=isJoin(cid, snum);
			Message msg = mHandler.obtainMessage();
			msg.arg1 = SECEND;
			msg.obj=yes;
			mHandler.sendMessage(msg);
	}

	

}
	private String ischat( String cid,String snum)
	{
		String url1 = "http://minfy.cn/interface/chatTest.asp";
		String params = "id="+cid+"&sno="+snum;
		String is = "0";	
		SyncHttp syncHttp = new SyncHttp();
		try
		{
			//��Get��ʽ���󣬲���÷��ؽ��
			String retStr = syncHttp.httpGet(url1, params);  
			JSONObject jsonObject = new JSONObject(retStr);
			 is = jsonObject.getString("ret");
			return is;	
		} catch (Exception e)
		{
			e.printStackTrace();
			
		}
		return is;
	}

	private class ischatThread extends Thread
	{
		String cid;
		String snum;
		public ischatThread(String cid,String snum) {
			// TODO Auto-generated constructor stub
			this.cid=cid;
			this.snum=snum;
		}
		@Override
		public void run()
		{
			// �������ϻ�ȡ����
		
			String yes=ischat(cid,snum);
			Message msg = mHandler.obtainMessage();
			msg.arg1 = THIRD;
			msg.obj=yes;
			mHandler.sendMessage(msg);
	}

}
}

