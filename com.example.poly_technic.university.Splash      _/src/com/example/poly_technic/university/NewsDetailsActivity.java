
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
	private int mNid;//���ű��
	private TextView mNewsDetails;//��ϸ���ŵ�text
	private Button mNewsdetailsTitlebarComm;// ���Żظ���ť����������ӻظ�����
	private ImageButton mNewsReplyImgBtn;// �������Żظ�ͼƬ
	private LinearLayout mNewsReplyImgLayout;// �������Żظ�ͼƬLayout
	private LinearLayout mNewsReplyEditLayout;// �������Żظ��ظ�Layout
	private TextView mNewsReplyContent;// ���Żظ�����
	  String tuNumber;
    //���̴߳��������������̵߳�ui
    private Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.arg1)
			{
			case FINISH:
				// �ѻ�ȡ����������ʾ��������
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
    	// �������Żظ�ͼƬLayout
		mNewsReplyImgLayout = (LinearLayout) findViewById(R.id.news_reply_img_layout);
		// �������Żظ��ظ�Layout
		mNewsReplyEditLayout = (LinearLayout) findViewById(R.id.news_reply_edit_layout);
		// ���Żظ�����
		mNewsReplyContent = (TextView) findViewById(R.id.news_reply_edittext);

		NewsDetailsOnClickListener newsDetailsOnClickListener = new NewsDetailsOnClickListener();
		// ��һƪ����
		Button newsDetailsTitlebarPref = (Button) findViewById(R.id.newsdetails_titlebar_previous);
		newsDetailsTitlebarPref.setOnClickListener(newsDetailsOnClickListener);
		// ��һƪ���ţ�������������һ��
//		Button newsDetailsTitlebarNext = (Button) findViewById(R.id.newsdetails_titlebar_next);
//		newsDetailsTitlebarNext.setOnClickListener(newsDetailsOnClickListener);
		//���Żظ�Button������ӻظ�������������鿴�ظ�����
		mNewsdetailsTitlebarComm = (Button) findViewById(R.id.newsdetails_titlebar_comments);
		mNewsdetailsTitlebarComm.setOnClickListener(newsDetailsOnClickListener);
		// �������Żظ�ͼƬButton������д������СͼƬ
		mNewsReplyImgBtn = (ImageButton) findViewById(R.id.news_reply_img_btn);
		mNewsReplyImgBtn.setOnClickListener(newsDetailsOnClickListener);
		// ����ظ���ʵ�ʱ༭����ģ�����ť
		Button newsReplyPost = (Button) findViewById(R.id.news_reply_post);
		newsReplyPost.setOnClickListener(newsDetailsOnClickListener);

		// ��ȡ���ݵ�����
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		// ���ñ���������
		String categoryName = bundle.getString("categoryName");
		TextView titleBarTitle = (TextView) findViewById(R.id.newsdetails_titlebar_title);
		titleBarTitle.setText(categoryName);
		// ��ȡ���ż���
		Serializable s = bundle.getSerializable("newsDate");
		mNewsData = (ArrayList<HashMap<String, Object>>) s;
		// ��ȡ���λ��
		mCursor = mPosition = bundle.getInt("position");
		// ��̬����������ͼ������ֵ
		mNewsBodyInflater=getLayoutInflater();
		inflateView(0);
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
			// ��һ������
			case R.id.newsdetails_titlebar_previous:
				//showPrevious();
				finish();
				break;
			// ��һ������
				//����Ҫ��һ���İ�ť
//			case R.id.newsdetails_titlebar_next:
//				showNext();
//				break;
			// ���Żظ�Button������ӻظ�������������鿴�ظ�����
			case R.id.newsdetails_titlebar_comments:
				
				Intent intent = new Intent(NewsDetailsActivity.this, CommentsActivity.class);
				//��������ID
				intent.putExtra("nid", mNid);
				startActivity(intent);
				break;
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
						Intent intent1=new Intent(NewsDetailsActivity.this, login.class);
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
	 * ��������NewsBody�����¼�
	 */
	class NewsBodyOnTouchListener implements OnTouchListener
	{
		
		public boolean onTouch(View v, MotionEvent event)
		{
			switch (event.getAction())
			{
			// ��ָ����
			case MotionEvent.ACTION_DOWN:
				// �������Żظ�Layout�Ƿ�ɼ�
				mNewsReplyImgLayout.setVisibility(View.VISIBLE);
				mNewsReplyEditLayout.setVisibility(View.GONE);
				InputMethodManager m = (InputMethodManager) mNewsReplyContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				// ��¼��ʼ����
				mStartX = event.getX();
				break;
			// ��ָ̧��
			case MotionEvent.ACTION_UP:
				// ���󻬶�
				if (event.getX() < mStartX)
				{
					showNext();
				}
				// ���һ���
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
	 * ��ʾ��һ������
	 */
	private void showNext()
	{
		//�ж��Ƿ������һƪwin��
		if (mPosition<mNewsData.size()-1)
		{
			// ������һ������
			mNewsBodyFlipper.setInAnimation(this, R.anim.push_left_in);
			mNewsBodyFlipper.setOutAnimation(this, R.anim.push_left_out);
			mPosition++;
			//�ж���һ���Ƿ��Ѿ�����
			if (mPosition >= mNewsBodyFlipper.getChildCount())
			{
				inflateView(mNewsBodyFlipper.getChildCount());
			}
			// ��ʾ��һ��
			mNewsBodyFlipper.showNext();
		}
		else
		{
			Toast.makeText(this, "û��������", Toast.LENGTH_SHORT).show();
		}
		System.out.println(mCursor +";"+mPosition);
	}

	private void showPrevious()
	{
		if (mPosition>0)
		{
			mPosition--;
			//��¼��ǰ���ű��
			HashMap<String, Object> hashMap = mNewsData.get(mPosition);
			mNid = (Integer) hashMap.get("nid");
			if (mCursor>mPosition)
			{
				mCursor = mPosition;
				inflateView(0);
				System.out.println(mNewsBodyFlipper.getChildCount());
				mNewsBodyFlipper.showNext();// ��ʾ��һҳ
			}
			mNewsBodyFlipper.setInAnimation(this, R.anim.push_right_in);// ������һҳ����ʱ�Ķ���
			mNewsBodyFlipper.setOutAnimation(this, R.anim.push_right_out);// ���嵱ǰҳ��ȥ�Ķ���
			mNewsBodyFlipper.showPrevious();// ��ʾ��һҳ
		}
		else
		{
			Toast.makeText(this, "û����һ������", Toast.LENGTH_SHORT).show();
		}
		System.out.println(mCursor +";"+mPosition);
	}
	//����ӻ�������
	private void inflateView(int index)
	{
		// ��̬����������ͼ������ֵ
		View newsBodyLayout = mNewsBodyInflater.inflate(R.layout.news_body, null);
		// ��ȡ������Ż�����Ϣ
		HashMap<String, Object> hashMap = mNewsData.get(mPosition);
		// ���ű���
		TextView newsTitle = (TextView) newsBodyLayout.findViewById(R.id.news_body_title);
		newsTitle.setText(hashMap.get("newslist_item_title").toString());
		// ����ʱ��ͳ���
		TextView newsPtimeAndSource = (TextView) newsBodyLayout.findViewById(R.id.news_body_ptime_source);
		newsPtimeAndSource.setText(hashMap.get("newslist_item_ptime").toString() + "    " + hashMap.get("newslist_item_source").toString());
		// ���ű��
		mNid = (Integer) hashMap.get("nid");
		// ���Żظ���
		mNewsdetailsTitlebarComm.setText(hashMap.get("newslist_item_comments") + "����");
	
		// ��������ͼ��ӵ�Flipper��
		mNewsBodyFlipper = (ViewFlipper) findViewById(R.id.news_body_flipper);
		mNewsBodyFlipper.addView(newsBodyLayout,index);
	
		// ������Body��Ӵ����¼�
		mNewsDetails = (TextView) newsBodyLayout.findViewById(R.id.news_body_details);
		mNewsDetails.setOnTouchListener(new NewsBodyOnTouchListener());
		//������������ŵ�ͷ����Ϣ��
		// �����̣߳���handle�м������ŵ���
		new UpdateNewsThread().start();
	}
	//�������̣߳��������ȡ���ݣ����ص�handle��ȥ����
	private class UpdateNewsThread extends Thread
	{
		@Override
		public void run()
		{
			// �������ϻ�ȡ����
			String newsBody = getNewsBody();
			Message msg = mHandler.obtainMessage();
			msg.arg1 = FINISH;
			msg.obj = newsBody;
			mHandler.sendMessage(msg);
		}
	}

		/**
		 * ��ȡ������ϸ��Ϣ
		 * 
		 * @return
		 */
		private String getNewsBody()
		{
			String retStr = "��������ʧ�ܣ����Ժ�����";
			SyncHttp syncHttp = new SyncHttp();
			String url = "http://minfy.cn/interface/getnews.asp";
			String params = "nid=" + mNid;
			try
			{
				String retString = syncHttp.httpGet(url, params);
				JSONObject jsonObject = new JSONObject(retString);
				// ��ȡ�����룬0��ʾ�ɹ�
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
		 * ��������
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




