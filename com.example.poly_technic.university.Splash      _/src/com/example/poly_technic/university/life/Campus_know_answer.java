		package com.example.poly_technic.university.life;
		
		import java.util.ArrayList;
import java.util.List;
		















import org.json.JSONArray;
import org.json.JSONObject;

		import com.example.poly_technic.university.R;

		




import com.example.poly_technic.university.life.Campus_know.Msg;
import com.example.poly_technic.university.model.Parameter;
import com.example.poly_technic.university.service.SyncHttp;






import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


		
		public class Campus_know_answer extends Activity {
		
			private ListView msgListView;  
			
			private EditText inputText;  
			
			private Button send;  
			private final int SECOND = 1;
			  
			private MsgAdapter adapter; 
			ArrayList<String>  question=new ArrayList<String>();
			ArrayList<String>  answer=new ArrayList<String>();
			String mPosition;
			  
			private List<Msg> msgList = new ArrayList<Msg>(); 
			private final int FINISH = 0;
			private Handler mHandler = new Handler()
			{
				
				public void handleMessage(Message msg)
				{
					switch (msg.arg1) {
					case FINISH:
						//0位置为问题
						Msg msg1 = new Msg("问:"+question.get(0),Msg.RECEIVED);  
					    msgList.add(msg1); 
						for (int i = 0; i <answer.size(); i++) {
							Msg msg2 = new Msg("答:"+answer.get(i),Msg.SENT);  
						    msgList.add(msg2); 
						}
						  adapter = new MsgAdapter(Campus_know_answer.this, R.layout.msg_item, msgList);  
						    msgListView.setAdapter(adapter);
					
						break;
					case SECOND:
						Toast.makeText(Campus_know_answer.this,(CharSequence) msg.obj, Toast.LENGTH_SHORT).show();
						inputText.setText("");//清空输入框的内容  
						break;

					}
				}
			};

		
		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口没有标题栏  
		setContentView(R.layout.campus_know_answer);
		// 获取传递的数据
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		 mPosition = bundle.getString("position");
		//启动线程获取问题和答案
		 new quesidThread(mPosition).start();
	  
	    inputText = (EditText) findViewById(R.id.answer_input_text);  
	    send = (Button) findViewById(R.id.answer_send);  
	    msgListView = (ListView) findViewById(R.id.answer_msg_list_view);  
	      
	    send.setOnClickListener(new OnClickListener(){  

	        
	        public void onClick(View v) {  
	            String content = inputText.getText().toString();  
	            if(!"".equals(content)){  
	                Msg msg = new Msg(content, Msg.SENT);  
	                msgList.add(msg);  
	                adapter.notifyDataSetChanged();//有新消息时，刷新ListView中的显示  
	                msgListView.setSelection(msgList.size());//将ListView定位到最后一行  
	                //启动线程将回答上传至网络
	                new PostThread().start();
	                
	            }  
	        }  
	          
	    }); 
	    Button backButton1=(Button)findViewById(R.id.Campus_answer_titlebar_previous);
	    backButton1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				
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
	private void getquestionid(ArrayList<String> newsList,String id1)
	{
		
		//请求URL和字符串
		String url = "http://minfy.cn/interface/getQuestionid.asp";
		String params = "id=" + id1;
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
					//获取返回新闻集合
					JSONArray newslist = dataObject.getJSONArray("newslist");
					//只有一条
					for(int i=0;i<newslist.length();i++)
					{
						JSONObject newsObject = (JSONObject)newslist.opt(i); 
						newsList.add(newsObject.getString("q_name"));
					
					}
			
			
			
		} 
		}catch (Exception e)
		{
			e.printStackTrace();
		
		}
	}
	private void getanswer(ArrayList<String> newsList,String qid)
	{
		
		//请求URL和字符串
		String url = "http://minfy.cn/interface/getanswer.asp";
		String params = "id=" + qid;
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
						newsList.add(newsObject.getString("a_content"));
					
					}
			
				}
			
		} 
		}catch (Exception e)
		{
			e.printStackTrace();
		
		}
	}
	private class quesidThread extends Thread
	{
		String id;
		  
		public quesidThread(String id) {
			// TODO Auto-generated constructor stub
			this.id=id;
			
		}
		@Override
		public void run()
		{
			// 从网络上获取新闻
		
			getquestionid(question,id);
			getanswer(answer, id);
			Message msg = mHandler.obtainMessage();
			msg.arg1 = FINISH;
			mHandler.sendMessage(msg);
	}

	}
	private String sendAnswer()
	{
		
		SyncHttp syncHttp = new SyncHttp();
		String url = "http://minfy.cn/interface/postAnswer.asp";
		List<Parameter> params = new ArrayList<Parameter>();
		params.add(new Parameter("content",  inputText.getText().toString()));
		params.add(new Parameter("id",  mPosition));
		try
		{
			String retStr = syncHttp.httpPost(url, params);
			JSONObject jsonObject = new JSONObject(retStr);  
			int retCode = jsonObject.getInt("ret");
			if (0==retCode)
			{
				
				
				return "回答成功，感谢您的回答！";
			}
		}
		
		 catch (Exception e)
		{
			e.printStackTrace();
		}
		return "回答失败，请检查网络连接";
		
	}



	private class PostThread extends Thread
	{
		@Override
		public void run()
		{
			// 从网络上获取新闻
			String result=sendAnswer();
			Message msg = mHandler.obtainMessage();
			msg.arg1 = SECOND;
			msg.obj=result;
			mHandler.sendMessage(msg);
	}

	}
	  
	}  