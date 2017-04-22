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
	private ImageButton mNewsReplyImgBtn;// 发表新闻回复图片
	private LinearLayout mNewsReplyImgLayout;// 发表新闻回复图片Layout
	private LinearLayout mNewsReplyEditLayout;// 发表新闻回复回复Layout
	private TextView mNewsReplyContent;// 新闻回复内容
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
					//显示新闻回复信息
					// commentsAdapter = new SimpleAdapter(this, mCommsData, R.layout.comments_list_item, new String[]{ "commentator_from", "comment_ptime", "comment_content" },new int[]{ R.id.commentator_from, R.id.comment_ptime, R.id.comment_content });
 commentsAdapter=new SimpleAdapter(CommentsActivity.this,mCommsData,R.layout.comments_list_item, new String[]{ "commentator_from", "comment_ptime", "comment_content" }, new int[]{ R.id.commentator_from, R.id.comment_ptime, R.id.comment_content });
					 commentsList.setAdapter(commentsAdapter);
					break;
				case SECOND:
					Toast.makeText(CommentsActivity.this, (CharSequence) msg.obj, Toast.LENGTH_SHORT).show();
					mCommsData.clear();
					//获取新闻回复内容
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
	//获取新闻编号
			Intent intent = getIntent();
			 nid = intent.getIntExtra("nid", 0); 
			//获取新闻回复内容
			new GetComment().start();
		
			 commentsList = (ListView) findViewById(R.id.comments_list);
				// 查找新闻回复图片Layout
				mNewsReplyImgLayout = (LinearLayout) findViewById(R.id.news_reply_img_layout);
				// 查找新闻回复回复Layout
				mNewsReplyEditLayout = (LinearLayout) findViewById(R.id.news_reply_edit_layout);
				// 新闻回复内容
				mNewsReplyContent = (TextView) findViewById(R.id.news_reply_edittext);
				NewsDetailsOnClickListener newsDetailsOnClickListener = new NewsDetailsOnClickListener();
			// 发表新闻回复图片Button，就是写跟帖的小图片
				mNewsReplyImgBtn = (ImageButton) findViewById(R.id.news_reply_img_btn);
				mNewsReplyImgBtn.setOnClickListener(newsDetailsOnClickListener);
				// 发表回复，实际编辑框里的，发表按钮
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
	 * 处理NewsDetailsTitleBar点击事件
	 */
	class NewsDetailsOnClickListener implements OnClickListener
	{
		
		public void onClick(View v)
		{
			InputMethodManager m = (InputMethodManager) mNewsReplyContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			switch (v.getId())
			{
		
			// 新闻回复图片，跟帖的小图片，点击后图片布局隐藏，出现实际编辑框
			case R.id.news_reply_img_btn:
				mNewsReplyImgLayout.setVisibility(View.GONE);
				mNewsReplyEditLayout.setVisibility(View.VISIBLE);
				mNewsReplyContent.requestFocus();
				//对输入法的设置
				m.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
				break;
			// 发表新闻回复，点击实际编辑框中的发表按钮
			case R.id.news_reply_post:
				SharedPreferences  sharedPreferences = getSharedPreferences("login", 0);
				 tuNumber =sharedPreferences.getString("name",null);
					//当学号为空是弹出登陆界面
					if (tuNumber==null) {
						Intent intent1=new Intent(CommentsActivity.this, login.class);
						startActivity(intent1); 
					}
					else {
						//触发post线程
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
		 * 获取新闻回复内容
		 * @param nid 新闻编号
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
					// 获取返回数目
					int totalnum = dataObject.getInt("totalnum");
					if (totalnum > 0)
					{
						// 获取返回新闻集合
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
						
						return "本条新闻没有评论";
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				//Toast.makeText(CommentsActivity.this,"获取评论失败", Toast.LENGTH_LONG).show();
				return "获取评论失败";
			}
			return null;
}
		private class GetComment extends Thread
		{
			public void run( )
			{
				// 从网络上获取新闻
				String result = getComments(nid);
				Message msg = mHandler.obtainMessage();
				msg.arg1 = FIRST;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		}
		/**
		 * 发送评论
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
					//Toast.makeText(NewsDetailsActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
					mNewsReplyImgLayout.setVisibility(View.VISIBLE);
					mNewsReplyEditLayout.setVisibility(View.GONE);
					return   "发布成功";
				}
				else  return "发布失败";

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			//Toast.makeText(NewsDetailsActivity.this, "发表失败", Toast.LENGTH_SHORT).show();
			return "发布失败"; 
		}
		

		/**
		 * 发表回复
		 * 
		 */
		private class PostCommentThread extends Thread
		{
			@Override
			public void run()
			{
				// 从网络上获取新闻
				String message=sendComment();
				Message msg = mHandler.obtainMessage();
				msg.arg1 = SECOND;
				msg.obj = message;
				mHandler.sendMessage(msg);
		}

	}

}
