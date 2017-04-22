package com.example.poly_technic.university;

import java.util.ArrayList;

import com.example.poly_technic.university.model.Category;
import com.example.poly_technic.university.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleAdapter;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.example.poly_technic.university.service.SyncHttp;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
public class MainActivity extends Activity {
	private int mCid;
	private ArrayList<HashMap<String, Object>> mNewsData;
	private LayoutInflater mInflater;
	private SimpleAdapter mNewsListAdapter;
	private ListView mNewsList;
	private Button mTitlebarRefresh;
	private ProgressBar mLoadnewsProgress;//������
	private Button mLoadMoreBtn;
	private LoadNewsAsyncTask loadNewsAsyncTask;
	private final int NEWSCOUNT = 5; //����������Ŀ
	private final int SUCCESS = 0;//���سɹ�
	private final int NONEWS = 1;//����Ŀ��û������
	private final int NOMORENEWS = 2;//����Ŀ��û�и�������
	private final int LOADERROR = 3;//����ʧ��
	private String mCatName;//���ŷ���       
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNewsList=(ListView)findViewById(R.id.news_list);
        mInflater = getLayoutInflater();
		mNewsData = new ArrayList<HashMap<String,Object>>();
		mTitlebarRefresh = (Button)findViewById(R.id.titlebar_refresh);
		mLoadnewsProgress = (ProgressBar)findViewById(R.id.loadnews_progress);
		mTitlebarRefresh.setOnClickListener(loadMoreListener);
        WindowManager wm = this.getWindowManager(); 
        int width = wm.getDefaultDisplay().getWidth()/3;
      //��ȡ���ŷ���
      		String[] categoryArray = getResources().getStringArray(R.array.categories);
      		//�����ŷ��ౣ�浽List��
       		final List<HashMap<String, Category>> categories = new ArrayList<HashMap<String, Category>>();
      		//�ָ����������ַ���
      		for(int i=0;i<categoryArray.length;i++)
      		{
      			String[] temp = categoryArray[i].split("[|]");
      			if (temp.length==2)
      			{
      				int cid = StringUtil.String2Int(temp[0]);
      				String title = temp[1];
      				Category type = new Category(cid, title);
      				HashMap<String, Category> hashMap = new HashMap<String, Category>();
      				hashMap.put("category_title", type);
      				categories.add(hashMap);
      			}
      		}
      	//Ĭ��ѡ�е����ŷ���
    		mCid = 1;
    		mCatName ="��������";
       //�����ݷ�����������
         CustomSimpleAdapter categoryAdapter=new CustomSimpleAdapter(MainActivity.this, categories, R.layout.category_title, new String[]{"category_title"}, new int[]{R.id.category_title});
        GridView categoryView=new GridView(this);//gridview�Ķ��弰���������
        categoryView.setColumnWidth(width);
        categoryView.setNumColumns(GridView.AUTO_FIT); 
        categoryView.setGravity(Gravity.CENTER);
        categoryView.setAdapter(categoryAdapter); 
        LinearLayout caLinearLayout =(LinearLayout)findViewById(R.id.category_layout);
        caLinearLayout.addView(categoryView);
        categoryView.setSelector(new ColorDrawable(color.transparent)); 
        categoryView.setOnItemClickListener(new OnItemClickListener() {//������������ʱ��������ʾ

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView categoryTitle;
				for(int i=0;i<parent.getChildCount();i++){
					categoryTitle=(TextView)parent.getChildAt(i);
					categoryTitle.setTextColor(0XFF000000);
				}
				categoryTitle=(TextView)parent.getChildAt(position);
				categoryTitle.setTextColor(0XFF129AEE);
				//��ȡѡ�е����ŷ���id
				mCid = categories.get(position).get("category_title").getCid();
				mCatName = categories.get(position).get("category_title").getTitle();
				//��ȡ����Ŀ������
				//getSpeCateNews(mCid,mNewsData,0,true);
				//֪ͨListView���и���
				//mNewsListAdapter.notifyDataSetChanged();
				loadNewsAsyncTask = new LoadNewsAsyncTask();
				loadNewsAsyncTask.execute(mCid,1,true);
			}
		});
      //��ȡָ����Ŀ�������б�
  
//        mNewsListAdapter = new SimpleAdapter(this, mNewsData, R.layout.newslist_item, 
//      										new String[]{"newslist_item_title","newslist_item_digest","newslist_item_source","newslist_item_ptime"}, 
//      										new int[]{R.id.newslist_item_title,R.id.newslist_item_digest,R.id.newslist_item_source,R.id.newslist_item_ptime});
        mNewsListAdapter = new SimpleAdapter(this, mNewsData, R.layout.newslist_item, 
					new String[]{"newslist_item_title","newslist_item_source","newslist_item_ptime"}, 
					new int[]{R.id.newslist_item_title,R.id.newslist_item_source,R.id.newslist_item_ptime});
        //���ر�Ĳ��֡�����һ������
      		View loadMoreLayout = mInflater.inflate(R.layout.loadmore, null);
      		mNewsList.addFooterView(loadMoreLayout);
      		mNewsList.setAdapter(mNewsListAdapter);
      		mNewsList.setOnItemClickListener(new OnItemClickListener()
      		{
      			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
      			{
      				Intent intent = new Intent(MainActivity.this, NewsDetailsActivity.class);
      				//����Ҫ����Ϣ�ŵ�Intent��
    				intent.putExtra("newsDate", mNewsData);
    				intent.putExtra("position", position);
    				intent.putExtra("categoryName", mCatName);   
    				startActivity(intent);
      			}
      		});
      		
//      		Button loadMoreBtn = (Button)loadMoreLayout.findViewById(R.id.loadmore_btn);
//      		loadMoreBtn.setOnClickListener(loadMoreListener); 
      	    mLoadMoreBtn = (Button)loadMoreLayout.findViewById(R.id.loadmore_btn);
    		mLoadMoreBtn.setOnClickListener(loadMoreListener);
      		loadNewsAsyncTask = new LoadNewsAsyncTask();
    		loadNewsAsyncTask.execute(mCid,1,true);
    }
    /**
	 * ��ȡָ�����͵������б�
	 * @param cid ����ID
	 * @param newsList ����������Ϣ�ļ���
	 * @param startnid ��ҳ
	 * @param firstTimes	�Ƿ��һ�μ���
	 */
	private int getSpeCateNews(int cid,List<HashMap<String, Object>> newsList,int startnid,Boolean firstTimes)
	{
		if (firstTimes)
		{
			//����ǵ�һ�Σ�����ռ���������
			newsList.clear();
		}
		//����URL���ַ���
		String url = "http://minfy.cn/interface/server1.asp";       
		String params = "startnid="+startnid+"&count="+NEWSCOUNT+"&cid="+cid;   
		SyncHttp syncHttp = new SyncHttp();
		try
		{
			//��Get��ʽ���󣬲���÷��ؽ��
			String retStr = syncHttp.httpGet(url, params);
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
					JSONArray newslist = dataObject.getJSONArray("newslist");
					for(int i=0;i<newslist.length();i++)
					{
						JSONObject newsObject = (JSONObject)newslist.opt(i); 
						HashMap<String, Object> hashMap = new HashMap<String, Object>();
						hashMap.put("nid", newsObject.getInt("t_new.nid"));
						hashMap.put("newslist_item_title", newsObject.getString("title"));
						//hashMap.put("newslist_item_digest", newsObject.getString("digest"));
						hashMap.put("newslist_item_source", newsObject.getString("source"));
						hashMap.put("newslist_item_ptime", newsObject.getString ("ptime"));
						hashMap.put("newslist_item_comments", newsObject.getString("Expr1001"));
						newsList.add(hashMap);
					}
					return SUCCESS;
				}
				else
				{
					if (firstTimes)
					{
						return NONEWS;
					}
					else
					{
						return NOMORENEWS;
					}
				}
			}
			else
			{//�ڶ��μ���ʱ���֣����ش���
				return LOADERROR;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return LOADERROR;
		}
	}
	private OnClickListener loadMoreListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			loadNewsAsyncTask = new LoadNewsAsyncTask();
			switch (v.getId())
			{
			case R.id.loadmore_btn:
				//��ȡ����Ŀ������
			//	getSpeCateNews(mCid,mNewsData,mNewsData.size(),false);
				//֪ͨListView���и���
			//	mNewsListAdapter.notifyDataSetChanged();
				loadNewsAsyncTask.execute(mCid,mNewsData.size()+1,false);
				break;
			case R.id.titlebar_refresh:
				loadNewsAsyncTask.execute(mCid,1,true);
				break;
			}
			
		}
	};
	//�����첽��������
	private class LoadNewsAsyncTask extends AsyncTask<Object, Integer, Integer>
	{
		
		@Override
		protected void onPreExecute()
		{
			//����ˢ�°�ť
			mTitlebarRefresh.setVisibility(View.GONE);
			//��ʾ������
			mLoadnewsProgress.setVisibility(View.VISIBLE); 
			//����LoadMore Button ��ʾ�ı�����ʾ���ظ���
			mLoadMoreBtn.setText(R.string.loadmore_txt);
		}

		@Override
		protected Integer doInBackground(Object... params)
		{
			//mNewsDate��ȫ�ֱ�����������޸������ط���ʹ��
			return getSpeCateNews((Integer)params[0],mNewsData,(Integer)params[1],(Boolean)params[2]);
		}

		@Override
		protected void onPostExecute(Integer result)
		{
			//���ݷ���ֵ��ʾ��ص�Toast
			switch (result)
			{
			case NONEWS:
				Toast.makeText(MainActivity.this, "����Ŀ��û������", Toast.LENGTH_LONG).show();
			break;
			case NOMORENEWS:
				Toast.makeText(MainActivity.this,"����Ŀ��û�и�������", Toast.LENGTH_LONG).show();
				break;
			case LOADERROR:
				Toast.makeText(MainActivity.this,"��ȡ����ʧ��", Toast.LENGTH_LONG).show();
				break;
			}
			mNewsListAdapter.notifyDataSetChanged();
			//��ʾˢ�°�ť
			mTitlebarRefresh.setVisibility(View.VISIBLE);
			//���ؽ�����
			mLoadnewsProgress.setVisibility(View.GONE); 
			//����LoadMore Button ��ʾ�ı�
			mLoadMoreBtn.setText(R.string.loadmore_btn);
		}
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
	                  isExit = false;  // ȡ���˳�  
	              }  
	          }, 2000); // ���2������û�а��·��ؼ�����������ʱ��ȡ�����ղ�ִ�е�����  
	    
	      } else {  
	          finish();  
	          System.exit(0);  
	      }  
	  } 
}

