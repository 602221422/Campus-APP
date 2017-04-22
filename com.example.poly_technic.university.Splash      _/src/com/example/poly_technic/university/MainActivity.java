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
	private ProgressBar mLoadnewsProgress;//进度条
	private Button mLoadMoreBtn;
	private LoadNewsAsyncTask loadNewsAsyncTask;
	private final int NEWSCOUNT = 5; //返回新闻数目
	private final int SUCCESS = 0;//加载成功
	private final int NONEWS = 1;//该栏目下没有新闻
	private final int NOMORENEWS = 2;//该栏目下没有更多新闻
	private final int LOADERROR = 3;//加载失败
	private String mCatName;//新闻分类       
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
      //获取新闻分类
      		String[] categoryArray = getResources().getStringArray(R.array.categories);
      		//把新闻分类保存到List中
       		final List<HashMap<String, Category>> categories = new ArrayList<HashMap<String, Category>>();
      		//分割新闻类型字符串
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
      	//默认选中的新闻分类
    		mCid = 1;
    		mCatName ="工大新闻";
       //把数据放适配器里面
         CustomSimpleAdapter categoryAdapter=new CustomSimpleAdapter(MainActivity.this, categories, R.layout.category_title, new String[]{"category_title"}, new int[]{R.id.category_title});
        GridView categoryView=new GridView(this);//gridview的定义及其行列设计
        categoryView.setColumnWidth(width);
        categoryView.setNumColumns(GridView.AUTO_FIT); 
        categoryView.setGravity(Gravity.CENTER);
        categoryView.setAdapter(categoryAdapter); 
        LinearLayout caLinearLayout =(LinearLayout)findViewById(R.id.category_layout);
        caLinearLayout.addView(categoryView);
        categoryView.setSelector(new ColorDrawable(color.transparent)); 
        categoryView.setOnItemClickListener(new OnItemClickListener() {//当点击新闻类别时，高亮显示

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView categoryTitle;
				for(int i=0;i<parent.getChildCount();i++){
					categoryTitle=(TextView)parent.getChildAt(i);
					categoryTitle.setTextColor(0XFF000000);
				}
				categoryTitle=(TextView)parent.getChildAt(position);
				categoryTitle.setTextColor(0XFF129AEE);
				//获取选中的新闻分类id
				mCid = categories.get(position).get("category_title").getCid();
				mCatName = categories.get(position).get("category_title").getTitle();
				//获取该栏目下新闻
				//getSpeCateNews(mCid,mNewsData,0,true);
				//通知ListView进行更新
				//mNewsListAdapter.notifyDataSetChanged();
				loadNewsAsyncTask = new LoadNewsAsyncTask();
				loadNewsAsyncTask.execute(mCid,1,true);
			}
		});
      //获取指定栏目的新闻列表
  
//        mNewsListAdapter = new SimpleAdapter(this, mNewsData, R.layout.newslist_item, 
//      										new String[]{"newslist_item_title","newslist_item_digest","newslist_item_source","newslist_item_ptime"}, 
//      										new int[]{R.id.newslist_item_title,R.id.newslist_item_digest,R.id.newslist_item_source,R.id.newslist_item_ptime});
        mNewsListAdapter = new SimpleAdapter(this, mNewsData, R.layout.newslist_item, 
					new String[]{"newslist_item_title","newslist_item_source","newslist_item_ptime"}, 
					new int[]{R.id.newslist_item_title,R.id.newslist_item_source,R.id.newslist_item_ptime});
        //加载别的布局。做成一个窗体
      		View loadMoreLayout = mInflater.inflate(R.layout.loadmore, null);
      		mNewsList.addFooterView(loadMoreLayout);
      		mNewsList.setAdapter(mNewsListAdapter);
      		mNewsList.setOnItemClickListener(new OnItemClickListener()
      		{
      			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
      			{
      				Intent intent = new Intent(MainActivity.this, NewsDetailsActivity.class);
      				//把需要的信息放到Intent中
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
	 * 获取指定类型的新闻列表
	 * @param cid 类型ID
	 * @param newsList 保存新闻信息的集合
	 * @param startnid 分页
	 * @param firstTimes	是否第一次加载
	 */
	private int getSpeCateNews(int cid,List<HashMap<String, Object>> newsList,int startnid,Boolean firstTimes)
	{
		if (firstTimes)
		{
			//如果是第一次，则清空集合里数据
			newsList.clear();
		}
		//请求URL和字符串
		String url = "http://minfy.cn/interface/server1.asp";       
		String params = "startnid="+startnid+"&count="+NEWSCOUNT+"&cid="+cid;   
		SyncHttp syncHttp = new SyncHttp();
		try
		{
			//以Get方式请求，并获得返回结果
			String retStr = syncHttp.httpGet(url, params);
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
			{//第二次加载时出现，加载错误
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
				//获取该栏目下新闻
			//	getSpeCateNews(mCid,mNewsData,mNewsData.size(),false);
				//通知ListView进行更新
			//	mNewsListAdapter.notifyDataSetChanged();
				loadNewsAsyncTask.execute(mCid,mNewsData.size()+1,false);
				break;
			case R.id.titlebar_refresh:
				loadNewsAsyncTask.execute(mCid,1,true);
				break;
			}
			
		}
	};
	//定义异步加载新闻
	private class LoadNewsAsyncTask extends AsyncTask<Object, Integer, Integer>
	{
		
		@Override
		protected void onPreExecute()
		{
			//隐藏刷新按钮
			mTitlebarRefresh.setVisibility(View.GONE);
			//显示进度条
			mLoadnewsProgress.setVisibility(View.VISIBLE); 
			//设置LoadMore Button 显示文本，显示加载更多
			mLoadMoreBtn.setText(R.string.loadmore_txt);
		}

		@Override
		protected Integer doInBackground(Object... params)
		{
			//mNewsDate是全局变量，在这儿修改其他地方可使用
			return getSpeCateNews((Integer)params[0],mNewsData,(Integer)params[1],(Boolean)params[2]);
		}

		@Override
		protected void onPostExecute(Integer result)
		{
			//根据返回值显示相关的Toast
			switch (result)
			{
			case NONEWS:
				Toast.makeText(MainActivity.this, "该栏目下没有新闻", Toast.LENGTH_LONG).show();
			break;
			case NOMORENEWS:
				Toast.makeText(MainActivity.this,"该栏目下没有更多新闻", Toast.LENGTH_LONG).show();
				break;
			case LOADERROR:
				Toast.makeText(MainActivity.this,"获取新闻失败", Toast.LENGTH_LONG).show();
				break;
			}
			mNewsListAdapter.notifyDataSetChanged();
			//显示刷新按钮
			mTitlebarRefresh.setVisibility(View.VISIBLE);
			//隐藏进度条
			mLoadnewsProgress.setVisibility(View.GONE); 
			//设置LoadMore Button 显示文本
			mLoadMoreBtn.setText(R.string.loadmore_btn);
		}
	}
	/** 
	 * 菜单、返回键响应 
	 */  
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    // TODO Auto-generated method stub  
	    if(keyCode == KeyEvent.KEYCODE_BACK)  
	       {    
	           exitBy2Click();      //调用双击退出函数  
	       }  
	    return false;  
	}  
	  /** 
	   * 双击退出函数 
	   */  
	  private static Boolean isExit = false;  
	    
	  private void exitBy2Click() {  
	      Timer tExit = null;  
	      if (isExit == false) {  
	          isExit = true; // 准备退出  
	          Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();  
	          tExit = new Timer();  
	          tExit.schedule(new TimerTask() {  
	              @Override  
	              public void run() {  
	                  isExit = false;  // 取消退出  
	              }  
	          }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务  
	    
	      } else {  
	          finish();  
	          System.exit(0);  
	      }  
	  } 
}

