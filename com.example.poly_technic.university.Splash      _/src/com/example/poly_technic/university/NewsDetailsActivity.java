
package com.example.poly_technic.university;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.example.poly_technic.university.service.SyncHttp;
import com.example.poly_technic.university.community.communityDetails;
import com.example.poly_technic.university.model.Parameter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;


public class NewsDetailsActivity extends Activity {
	private final int FINISH = 0;
	private final int SECOND = 1;
	private ViewFlipper mNewsBodyFlipper;
	private LayoutInflater mNewsBodyInflater;
	private int count=0;
	private float mStartX;
	private ArrayList<HashMap<String, Object>> mNewsData;
	private int mPosition = 0;
	private int mCursor;
	private int mNid;//新闻编号
	private TextView mNewsDetails;//详细新闻的text
	private Button mNewsdetailsTitlebarComm;// 新闻回复按钮，给他上面加回复的数
	private ImageButton mNewsReplyImgBtn;// 发表新闻回复图片
	private LinearLayout mNewsReplyImgLayout;// 发表新闻回复图片Layout
	private LinearLayout mNewsReplyEditLayout;// 发表新闻回复回复Layout
	private TextView mNewsReplyContent;// 新闻回复内容
	  String tuNumber;
    //子线程处理方法，处理主线程的ui
    private Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.arg1)
			{
			case FINISH:
				// 把获取到的新闻显示到界面上
				mNewsDetails.setText(Html.fromHtml(msg.obj.toString()));
				break;
			case SECOND:
				Toast.makeText(NewsDetailsActivity.this, (CharSequence) msg.obj, Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsdetails);
    	// 查找新闻回复图片Layout
		mNewsReplyImgLayout = (LinearLayout) findViewById(R.id.news_reply_img_layout);
		// 查找新闻回复回复Layout
		mNewsReplyEditLayout = (LinearLayout) findViewById(R.id.news_reply_edit_layout);
		// 新闻回复内容
		mNewsReplyContent = (TextView) findViewById(R.id.news_reply_edittext);

		NewsDetailsOnClickListener newsDetailsOnClickListener = new NewsDetailsOnClickListener();
		// 上一篇新闻
		Button newsDetailsTitlebarPref = (Button) findViewById(R.id.newsdetails_titlebar_previous);
		newsDetailsTitlebarPref.setOnClickListener(newsDetailsOnClickListener);
		// 下一篇新闻，，，，无需下一条
//		Button newsDetailsTitlebarNext = (Button) findViewById(R.id.newsdetails_titlebar_next);
//		newsDetailsTitlebarNext.setOnClickListener(newsDetailsOnClickListener);
		//新闻回复Button给他添加回复的数量，点击查看回复内容
		mNewsdetailsTitlebarComm = (Button) findViewById(R.id.newsdetails_titlebar_comments);
		mNewsdetailsTitlebarComm.setOnClickListener(newsDetailsOnClickListener);
		// 发表新闻回复图片Button，就是写跟帖的小图片
		mNewsReplyImgBtn = (ImageButton) findViewById(R.id.news_reply_img_btn);
		mNewsReplyImgBtn.setOnClickListener(newsDetailsOnClickListener);
		// 发表回复，实际编辑框里的，发表按钮
		Button newsReplyPost = (Button) findViewById(R.id.news_reply_post);
		newsReplyPost.setOnClickListener(newsDetailsOnClickListener);

		// 获取传递的数据
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		// 设置标题栏名称
		String categoryName = bundle.getString("categoryName");
		TextView titleBarTitle = (TextView) findViewById(R.id.newsdetails_titlebar_title);
		titleBarTitle.setText(categoryName);
		// 获取新闻集合
		Serializable s = bundle.getSerializable("newsDate");
		mNewsData = (ArrayList<HashMap<String, Object>>) s;
		// 获取点击位置
		mCursor = mPosition = bundle.getInt("position");
		// 动态创建新闻视图，并赋值
		mNewsBodyInflater=getLayoutInflater();
		inflateView(0);
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
			// 上一条新闻
			case R.id.newsdetails_titlebar_previous:
				//showPrevious();
				finish();
				break;
			// 下一条新闻
				//不需要下一条的按钮
//			case R.id.newsdetails_titlebar_next:
//				showNext();
//				break;
			// 新闻回复Button给他添加回复的数量，点击查看回复内容
			case R.id.newsdetails_titlebar_comments:
				
				Intent intent = new Intent(NewsDetailsActivity.this, CommentsActivity.class);
				//传递新闻ID
				intent.putExtra("nid", mNid);
				startActivity(intent);
				break;
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
						Intent intent1=new Intent(NewsDetailsActivity.this, login.class);
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
	 * 处理新闻NewsBody触摸事件
	 */
	class NewsBodyOnTouchListener implements OnTouchListener
	{
		
		public boolean onTouch(View v, MotionEvent event)
		{
			switch (event.getAction())
			{
			// 手指按下
			case MotionEvent.ACTION_DOWN:
				// 设置新闻回复Layout是否可见
				mNewsReplyImgLayout.setVisibility(View.VISIBLE);
				mNewsReplyEditLayout.setVisibility(View.GONE);
				InputMethodManager m = (InputMethodManager) mNewsReplyContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				// 记录起始坐标
				mStartX = event.getX();
				break;
			// 手指抬起
			case MotionEvent.ACTION_UP:
				// 往左滑动
				if (event.getX() < mStartX)
				{
					showNext();
				}
				// 往右滑动
				else if (event.getX() > mStartX)
				{
					
					showPrevious();
				}
				break;
			}
			return true;
		}
	}
	/**
	 * 显示下一条新闻
	 */
	private void showNext()
	{
		//判断是否是最后一篇win问
		if (mPosition<mNewsData.size()-1)
		{
			// 设置下一屏动画
			mNewsBodyFlipper.setInAnimation(this, R.anim.push_left_in);
			mNewsBodyFlipper.setOutAnimation(this, R.anim.push_left_out);
			mPosition++;
			//判断下一屏是否已经创建
			if (mPosition >= mNewsBodyFlipper.getChildCount())
			{
				inflateView(mNewsBodyFlipper.getChildCount());
			}
			// 显示下一屏
			mNewsBodyFlipper.showNext();
		}
		else
		{
			Toast.makeText(this, "没有新闻了", Toast.LENGTH_SHORT).show();
		}
		System.out.println(mCursor +";"+mPosition);
	}

	private void showPrevious()
	{
		if (mPosition>0)
		{
			mPosition--;
			//记录当前新闻编号
			HashMap<String, Object> hashMap = mNewsData.get(mPosition);
			mNid = (Integer) hashMap.get("nid");
			if (mCursor>mPosition)
			{
				mCursor = mPosition;
				inflateView(0);
				System.out.println(mNewsBodyFlipper.getChildCount());
				mNewsBodyFlipper.showNext();// 显示下一页
			}
			mNewsBodyFlipper.setInAnimation(this, R.anim.push_right_in);// 定义下一页进来时的动画
			mNewsBodyFlipper.setOutAnimation(this, R.anim.push_right_out);// 定义当前页出去的动画
			mNewsBodyFlipper.showPrevious();// 显示上一页
		}
		else
		{
			Toast.makeText(this, "没有上一条新闻", Toast.LENGTH_SHORT).show();
		}
		System.out.println(mCursor +";"+mPosition);
	}
	//添加子滑动窗体
	private void inflateView(int index)
	{
		// 动态创建新闻视图，并赋值
		View newsBodyLayout = mNewsBodyInflater.inflate(R.layout.news_body, null);
		// 获取点击新闻基本信息
		HashMap<String, Object> hashMap = mNewsData.get(mPosition);
		// 新闻标题
		TextView newsTitle = (TextView) newsBodyLayout.findViewById(R.id.news_body_title);
		newsTitle.setText(hashMap.get("newslist_item_title").toString());
		// 发布时间和出处
		TextView newsPtimeAndSource = (TextView) newsBodyLayout.findViewById(R.id.news_body_ptime_source);
		newsPtimeAndSource.setText(hashMap.get("newslist_item_ptime").toString() + "    " + hashMap.get("newslist_item_source").toString());
		// 新闻编号
		mNid = (Integer) hashMap.get("nid");
		// 新闻回复数
		mNewsdetailsTitlebarComm.setText(hashMap.get("newslist_item_comments") + "跟帖");
	
		// 把新闻视图添加到Flipper中
		mNewsBodyFlipper = (ViewFlipper) findViewById(R.id.news_body_flipper);
		mNewsBodyFlipper.addView(newsBodyLayout,index);
	
		// 给新闻Body添加触摸事件
		mNewsDetails = (TextView) newsBodyLayout.findViewById(R.id.news_body_details);
		mNewsDetails.setOnTouchListener(new NewsBodyOnTouchListener());
		//以上完成了新闻的头部信息，
		// 启动线程，在handle中加载新闻的体
		new UpdateNewsThread().start();
	}
	//定义子线程，从网络获取数据，返回到handle类去处理
	private class UpdateNewsThread extends Thread
	{
		@Override
		public void run()
		{
			// 从网络上获取新闻
			String newsBody = getNewsBody();
			Message msg = mHandler.obtainMessage();
			msg.arg1 = FINISH;
			msg.obj = newsBody;
			mHandler.sendMessage(msg);
		}
	}

		/**
		 * 获取新闻详细信息
		 * 
		 * @return
		 */
		private String getNewsBody()
		{
			String retStr = "网络连接失败，请稍后再试";
			SyncHttp syncHttp = new SyncHttp();
			String url = "http://minfy.cn/interface/getnews.asp";
			String params = "nid=" + mNid;
			try
			{
				String retString = syncHttp.httpGet(url, params);
				JSONObject jsonObject = new JSONObject(retString);
				// 获取返回码，0表示成功
				int retCode = jsonObject.getInt("ret");
				if (0 == retCode)
				{
					JSONObject dataObject = jsonObject.getJSONObject("data");
					JSONObject newsObject = dataObject.getJSONObject("newslist");
					retStr = newsObject.getString("body");
				}

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			return retStr;
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
			params.add(new Parameter("nid", mNid + ""));
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




