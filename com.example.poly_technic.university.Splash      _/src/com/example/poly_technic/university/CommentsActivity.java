package com.example.poly_technic.university;

import java.util.ArrayList;

import com.example.poly_technic.university.model.Parameter;
import com.example.poly_technic.university.service.SyncHttp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CommentsActivity extends Activity {
	private List<HashMap<String, Object>> mCommsData=new ArrayList<HashMap<String, Object>>();;
	private final int FIRST=0;
	private final int SECOND = 1;
	SimpleAdapter commentsAdapter;
	private int nid; 
	ListView commentsList;
	private ImageButton mNewsReplyImgBtn;// �������Żظ�ͼƬ
	private LinearLayout mNewsReplyImgLayout;// �������Żظ�ͼƬLayout
	private LinearLayout mNewsReplyEditLayout;// �������Żظ��ظ�Layout
	private TextView mNewsReplyContent;// ���Żظ�����
	  String tuNumber;
	 private Handler mHandler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				switch (msg.arg1)
				{
				case FIRST:
					//Toast.makeText(CommentsActivity.this, (CharSequence) msg.obj, Toast.LENGTH_SHORT).show();
					//��ʾ���Żظ���Ϣ
					// commentsAdapter = new SimpleAdapter(this, mCommsData, R.layout.comments_list_item, new String[]{ "commentator_from", "comment_ptime", "comment_content" },new int[]{ R.id.commentator_from, R.id.comment_ptime, R.id.comment_content });
 commentsAdapter=new SimpleAdapter(CommentsActivity.this,mCommsData,R.layout.comments_list_item, new String[]{ "commentator_from", "comment_ptime", "comment_content" }, new int[]{ R.id.commentator_from, R.id.comment_ptime, R.id.comment_content });
					 commentsList.setAdapter(commentsAdapter);
					break;
				case SECOND:
					Toast.makeText(CommentsActivity.this, (CharSequence) msg.obj, Toast.LENGTH_SHORT).show();
					mCommsData.clear();
					//��ȡ���Żظ�����
					new GetComment().start();
					break;
				
				}
			}
		};

   @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	   
	super.onCreate(savedInstanceState);
	setContentView(R.layout.comments); 
	//��ȡ���ű��
			Intent intent = getIntent();
			 nid = intent.getIntExtra("nid", 0); 
			//��ȡ���Żظ�����
			new GetComment().start();
		
			 commentsList = (ListView) findViewById(R.id.comments_list);
				// �������Żظ�ͼƬLayout
				mNewsReplyImgLayout = (LinearLayout) findViewById(R.id.news_reply_img_layout);
				// �������Żظ��ظ�Layout
				mNewsReplyEditLayout = (LinearLayout) findViewById(R.id.news_reply_edit_layout);
				// ���Żظ�����
				mNewsReplyContent = (TextView) findViewById(R.id.news_reply_edittext);
				NewsDetailsOnClickListener newsDetailsOnClickListener = new NewsDetailsOnClickListener();
			// �������Żظ�ͼƬButton������д������СͼƬ
				mNewsReplyImgBtn = (ImageButton) findViewById(R.id.news_reply_img_btn);
				mNewsReplyImgBtn.setOnClickListener(newsDetailsOnClickListener);
				// ����ظ���ʵ�ʱ༭����ģ�����ť
				Button newsReplyPost = (Button) findViewById(R.id.news_reply_post);
				newsReplyPost.setOnClickListener(newsDetailsOnClickListener);
			
			
			Button commsTitlebarNews =(Button)findViewById(R.id.comments_titlebar_news);
			commsTitlebarNews.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					finish();
				}
			});
		} 
   /**
	 * ����NewsDetailsTitleBar����¼�
	 */
	class NewsDetailsOnClickListener implements OnClickListener
	{
		
		public void onClick(View v)
		{
			InputMethodManager m = (InputMethodManager) mNewsReplyContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			switch (v.getId())
			{
		
			// ���Żظ�ͼƬ��������СͼƬ�������ͼƬ�������أ�����ʵ�ʱ༭��
			case R.id.news_reply_img_btn:
				mNewsReplyImgLayout.setVisibility(View.GONE);
				mNewsReplyEditLayout.setVisibility(View.VISIBLE);
				mNewsReplyContent.requestFocus();
				//�����뷨������
				m.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
				break;
			// �������Żظ������ʵ�ʱ༭���еķ���ť
			case R.id.news_reply_post:
				SharedPreferences  sharedPreferences = getSharedPreferences("login", 0);
				 tuNumber =sharedPreferences.getString("name",null);
					//��ѧ��Ϊ���ǵ�����½����
					if (tuNumber==null) {
						Intent intent1=new Intent(CommentsActivity.this, login.class);
						startActivity(intent1); 
					}
					else {
						//����post�߳�
						new PostCommentThread().start();
						mNewsReplyImgLayout.setVisibility(View.VISIBLE);
						mNewsReplyEditLayout.setVisibility(View.GONE);
						m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
					}
				
			
				break;
			}
		}
	}

		/**
		 * ��ȡ���Żظ�����
		 * @param nid ���ű��
		 */
		private String getComments(int nid)
		{
			String url = "http://minfy.cn/interface/getComments.asp";
			String params = "nid=" + nid ;
			SyncHttp http = new SyncHttp();
			try
			{
				String retStr = http.httpGet(url, params);
				JSONObject jsonObject = new JSONObject(retStr);
				int retCode = jsonObject.getInt("ret");
				if (retCode == 0)
				{
					JSONObject dataObject = jsonObject.getJSONObject("data");
					// ��ȡ������Ŀ
					int totalnum = dataObject.getInt("totalnum");
					if (totalnum > 0)
					{
						// ��ȡ�������ż���
						JSONArray commsList = dataObject.getJSONArray("commentslist");
						for (int i = 0; i < commsList.length(); i++) 
						{
							JSONObject commsObject = (JSONObject) commsList.opt(i);
							HashMap<String, Object> hashMap = new HashMap<String, Object>();
							hashMap.put("commentator_from", commsObject.getString("region"));
							hashMap.put("comment_content", commsObject.getString("content"));
							hashMap.put("comment_ptime", commsObject.getString("ptime"));
							mCommsData.add(hashMap);
						}
					}
					else
					{
						
						return "��������û������";
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				//Toast.makeText(CommentsActivity.this,"��ȡ����ʧ��", Toast.LENGTH_LONG).show();
				return "��ȡ����ʧ��";
			}
			return null;
}
		private class GetComment extends Thread
		{
			public void run( )
			{
				// �������ϻ�ȡ����
				String result = getComments(nid);
				Message msg = mHandler.obtainMessage();
				msg.arg1 = FIRST;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		}
		/**
		 * ��������
		 * 
		 * @return
		 */
		private String sendComment()
		{
			
			SyncHttp syncHttp = new SyncHttp();
			String url = "http://minfy.cn/interface/postComment.asp";
			List<Parameter> params = new ArrayList<Parameter>();
			params.add(new Parameter("nid", nid + ""));
			params.add(new Parameter("region", tuNumber));
			params.add(new Parameter("content", mNewsReplyContent.getText().toString()));
			try
			{
				String retStr = syncHttp.httpPost(url, params);
				JSONObject jsonObject = new JSONObject(retStr);
				int retCode = jsonObject.getInt("ret");
				if (0 == retCode)
				{
					//Toast.makeText(NewsDetailsActivity.this, "����ɹ�", Toast.LENGTH_SHORT).show();
					mNewsReplyImgLayout.setVisibility(View.VISIBLE);
					mNewsReplyEditLayout.setVisibility(View.GONE);
					return   "�����ɹ�";
				}
				else  return "����ʧ��";

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			//Toast.makeText(NewsDetailsActivity.this, "����ʧ��", Toast.LENGTH_SHORT).show();
			return "����ʧ��"; 
		}
		

		/**
		 * ����ظ�
		 * 
		 */
		private class PostCommentThread extends Thread
		{
			@Override
			public void run()
			{
				// �������ϻ�ȡ����
				String message=sendComment();
				Message msg = mHandler.obtainMessage();
				msg.arg1 = SECOND;
				msg.obj = message;
				mHandler.sendMessage(msg);
		}

	}

}
