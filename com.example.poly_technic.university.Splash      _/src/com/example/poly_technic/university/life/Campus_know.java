package com.example.poly_technic.university.life;

import java.util.ArrayList;  
import java.util.HashMap;
import java.util.List;  

import org.json.JSONArray;
import org.json.JSONObject;














import com.example.poly_technic.university.R;
import com.example.poly_technic.university.community.CommunityChat;
import com.example.poly_technic.university.community.communityDetails;
import com.example.poly_technic.university.model.Parameter;
import com.example.poly_technic.university.service.SyncHttp;

import android.R.string;  
import android.os.Bundle;  
import android.os.Handler;
import android.os.Message;
import android.app.Activity;  
import android.content.Context;  
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;  
import android.view.Menu;  
import android.view.MotionEvent;
import android.view.View;  
import android.view.View.OnClickListener;  
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;  
import android.view.Window;  
import android.widget.Adapter;  
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;  
import android.widget.Button;  
import android.widget.EditText;  
import android.widget.LinearLayout;  
import android.widget.ListView;  
import android.widget.TextView;  
import android.widget.Toast;
import android.widget.ViewFlipper;

public class Campus_know extends Activity{
		private ListView msgListView;  
		
		private EditText inputText; 
		private EditText  searchinputText;
		
		private Button send;
	
	
		  
		private MsgAdapter adapter;  
		  
		private List<Msg> msgList = new ArrayList<Msg>(); 
		ArrayList<HashMap<String, Object>>  question=new ArrayList<HashMap<String,Object>>();
		private final int FINISH = 0;
		private final int SECOND = 1;
		private final int TH = 2;
		private Handler mHandler = new Handler()
		{
			
			public void handleMessage(Message msg)
			{
				switch (msg.arg1) {
				case FINISH:
					for (int i = 0; i <question.size(); i++) {
						Msg msg1 = new Msg("问:"+question.get(i).get("q_name"),Msg.RECEIVED);  
					    msgList.add(msg1); 
					}
					adapter = new MsgAdapter(Campus_know.this, R.layout.msg_item, msgList);  
				    msgListView.setAdapter(adapter); 
				
					break;
				case SECOND:
					Toast.makeText(Campus_know.this,(CharSequence) msg.obj, Toast.LENGTH_SHORT).show();
					inputText.setText("");//清空输入框的内容  
					break;
				case TH:
					inputText.setText("");//清空输入框的内容  
					if (msg.obj.equals("null")) {
						Toast.makeText(Campus_know.this, "没有该问题", 1).show();
					
						
					}else {
						Intent intent = new Intent(Campus_know.this, Campus_know_answer.class);
						intent.putExtra("position",  (String)msg.obj);
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
	setContentView(R.layout.campus_know);
	//initMsg();  
    //启动线程，加载问题
    new quesThread().start(); 
    
    inputText = (EditText) findViewById(R.id.input_text);  
    searchinputText = (EditText) findViewById(R.id.search_input_text );
    send = (Button) findViewById(R.id.send);  
    msgListView = (ListView) findViewById(R.id.msg_list_view);  
     

    //发表按钮，启动线程发送到网络
    send.setOnClickListener(new OnClickListener(){  

      
        public void onClick(View v) {  
            String content = inputText.getText().toString();  
            if(!"".equals(content)){   
                if (question.size()==0) {
                	Toast.makeText(Campus_know.this,"请链接网络", Toast.LENGTH_SHORT).show();
				}else { 
					//避免无网络情况，发送的内容显示在屏幕上
					   Msg msg = new Msg(content,Msg.RECEIVED);  
		                msgList.add(msg); 
					 
		                adapter.notifyDataSetChanged();//有新消息时，刷新ListView中的显示  
		                msgListView.setSelection(msgList.size());//将ListView定位到最后一行  
		                //启动线程将回答上传至网络
		                new PostThread().start();
		                
					
				}
              
            }  
        }  
          
    });  
    msgListView.setOnItemClickListener(new OnItemClickListener() {   

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			if (question.size()==position) {
				Toast.makeText(Campus_know.this,"你的问题还没有相关回答，请耐心等待", Toast.LENGTH_SHORT).show();
				
			}else {
				
				Intent intent = new Intent(Campus_know.this, Campus_know_answer.class);
				//把需要的信息放到Intent中
			intent.putExtra("position",(String) question.get(position).get("q_id"));
			startActivity(intent);
			}
		
			
		}
	});

    Button backButton=(Button)findViewById(R.id.Campus_titlebar_previous);
    backButton.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
			
		}
	});
    //点击搜索按钮时的。跳转到下一个页面。并且启动线程
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


  
  
public class Msg{  
      
    public static final int RECEIVED = 0;//收到一条消息     
      
    public static final int SENT = 1;//发出一条消息   
      
    private String  content;//消息的内容  
      
    private int type;//消息的类型  
      
    public  Msg(String content,int type){  
        this.content = content;  
        this.type = type;  
    }  
      
    public String getContent(){  
        return content;  
    }  
      
    public int getType(){  
        return type;  
    }  
}  
  
public class MsgAdapter extends ArrayAdapter<Msg>{  
    private int resourceId;  

    public MsgAdapter(Context context, int textViewresourceId, List<Msg> objects) {  
        super(context, textViewresourceId, objects);  
        resourceId = textViewresourceId;  
    }  
      
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {    
        Msg msg = getItem(position);  
        View view;  
        ViewHolder viewHolder;  
          
        if(convertView == null){  
        view = LayoutInflater.from(getContext()).inflate(resourceId, null);  
        viewHolder = new ViewHolder();  
        viewHolder.leftLayout = (LinearLayout)view.findViewById(R.id.left_layout);   
        viewHolder.rightLayout = (LinearLayout)view.findViewById(R.id.right_Layout);    
        viewHolder.leftMsg = (TextView)view.findViewById(R.id.left_msg);  
        viewHolder.rightMsg = (TextView)view.findViewById(R.id.right_msg);   
        view.setTag(viewHolder);  
        }else{  
            view = convertView;  
            viewHolder = (ViewHolder) view.getTag();  
        }  
          
        if(msg.getType()==Msg.RECEIVED){  
            //如果是收到的消息，则显示左边消息布局，将右边消息布局隐藏  
            viewHolder.leftLayout.setVisibility(View.VISIBLE);  
            viewHolder.rightLayout.setVisibility(View.GONE);  
            viewHolder.leftMsg.setText(msg.getContent());  
        }else if(msg.getType()==Msg.SENT){  
            //如果是发出去的消息，显示右边布局的消息布局，将左边的消息布局隐藏  
            viewHolder.rightLayout.setVisibility(View.VISIBLE);  
            viewHolder.leftLayout.setVisibility(View.GONE);  
            viewHolder.rightMsg.setText(msg.getContent());  
        }  
        return view;  
    }  
      
    class ViewHolder{  
        LinearLayout leftLayout;  
        LinearLayout rightLayout;  
        TextView leftMsg;  
        TextView rightMsg;  
    }  
} 
private void getquestion(ArrayList<HashMap<String, Object>> newsList)
{
	
	//请求URL和字符串
	String url = "http://minfy.cn/interface/getQuestion.asp";
	SyncHttp syncHttp = new SyncHttp();
	try
	{
		//以Get方式请求，并获得返回结果
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
				JSONArray newslist = dataObject.getJSONArray("newslist");
				for(int i=0;i<newslist.length();i++)
				{
					JSONObject newsObject = (JSONObject)newslist.opt(i); 
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					hashMap.put("q_id", newsObject.getString("q_no"));
					hashMap.put("q_name", newsObject.getString("q_name"));
					newsList.add(hashMap);
				
				}
		
			}
		
	} 
	}catch (Exception e)
	{
		e.printStackTrace();
	
	}
}
private class quesThread extends Thread
{
	@Override
	public void run()
	{
		// 从网络上获取新闻
	
		getquestion(question);
		Message msg = mHandler.obtainMessage();
		msg.arg1 = FINISH;
		mHandler.sendMessage(msg);
}

}

private String sendQuestion()
{
	
	SyncHttp syncHttp = new SyncHttp();
	String url = "http://minfy.cn/interface/postQuestion.asp";
	List<Parameter> params = new ArrayList<Parameter>();
	params.add(new Parameter("content",  inputText.getText().toString()));
	
	try
	{
		String retStr = syncHttp.httpPost(url, params);
		JSONObject jsonObject = new JSONObject(retStr);  
		int retCode = jsonObject.getInt("ret");
		if (0==retCode)
		{
			
			
			return "提问成功，请等待回答！";
		}
	}
	
	 catch (Exception e)
	{
		e.printStackTrace();
	}
	return "提问失败，请检查网络连接";
	
}



private class PostThread extends Thread
{
	@Override
	public void run()
	{
		// 从网络上获取新闻
		String result=sendQuestion();
		Message msg = mHandler.obtainMessage();
		
		msg.arg1 = SECOND;
		msg.obj=result;
		mHandler.sendMessage(msg);
}

}
private String search()
{
	String retCode = null ;
	SyncHttp syncHttp = new SyncHttp();
	String url = "http://minfy.cn/interface/search.asp";
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
	return retCode;
	
}



private class searchThread extends Thread
{
	@Override
	public void run()
	{
		// 从网络上获取新闻
		String result=search();
		Message msg = mHandler.obtainMessage();
		msg.arg1 = TH;
		msg.obj=result;
		mHandler.sendMessage(msg);
}

}
  
}  