package com.example.poly_technic.university.study;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poly_technic.university.NewsDetailsActivity;
import com.example.poly_technic.university.R;
import com.example.poly_technic.university.login;
import com.example.poly_technic.university.model.Parameter;
import com.example.poly_technic.university.service.SyncHttp;
public class computer extends Activity implements OnClickListener
{
	
	private TextView txtResult;
	private Button back, ysubmit, j2submit, j3submit;
	private EditText edtName;
	private Spinner yingyu, computer2b, computer3b;
	private final int SECOND = 1;
	String[] Balls = new String[]
	{ "四  级", "六 级" };
	String[] computer2 = new String[]
	{ "C语言  ", "java  ", "c++  ", "vb  " };
	String[] computer3 = new String[]
	{"java", "C语言", "c++", "vb" };
	String xuehao=null;
    private Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.arg1)
			{
		
			case SECOND:
				Toast.makeText(computer.this, (CharSequence) msg.obj, Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.computer);

		txtResult = (TextView) findViewById(R.id.txtResult);
		yingyu = (Spinner) findViewById(R.id.yingyu);
		computer2b = (Spinner) findViewById(R.id.computer2);
		computer3b = (Spinner) findViewById(R.id.computer3);

		back = (Button) this.findViewById(R.id.newsdetails_titlebar_previous);
		ysubmit = (Button) this.findViewById(R.id.ysubmit);
		j2submit = (Button) this.findViewById(R.id.j2submit);
		j3submit = (Button) this.findViewById(R.id.j3submit);

		ysubmit.setOnClickListener(this);
		j2submit.setOnClickListener(this);
		j3submit.setOnClickListener(this);
		back.setOnClickListener(this);

		yingyu.setPrompt("选择你要报名的项目");
		computer2b.setPrompt("选择你要报名的项目");
		computer3b.setPrompt("选择你要报名的项目");
		back = (Button) this.findViewById(R.id.newsdetails_titlebar_previous);
		ArrayAdapter<String> adapterBalls = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, Balls);
		// 设置下拉风格
		adapterBalls
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ArrayAdapter<String> adaptercomputer2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, computer2);
		adaptercomputer2
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ArrayAdapter<String> adaptercomputer3 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, computer3);
		adaptercomputer3
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		yingyu.setAdapter(adapterBalls);
		computer2b.setAdapter(adaptercomputer2);
		computer3b.setAdapter(adaptercomputer3);
		SharedPreferences  sharedPreferences = getSharedPreferences("login", 0);
		 xuehao =sharedPreferences.getString("name",null);

	}

	// 返回以及提交触发按钮事件	

	public void onClick(View v)
	{
		AlertDialog.Builder builder;
		View view;
	
	
		
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.newsdetails_titlebar_previous:
			finish();
			break;
		case R.id.ysubmit:
			
			
			//判断是否已经登录，若没有登录，则先进行登录
			if (xuehao==null) {
				Intent intent=new Intent(computer.this, login.class);
				startActivity(intent); 
			}else
			{
				
			builder = new AlertDialog.Builder(computer.this)
			.setTitle("确认信息").setPositiveButton("确定", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					// TODO Auto-generated method stub
					String kemu1=yingyu.getSelectedItem().toString();
					final String bianhao1=getbianhao(kemu1);
					//启动线程、、、、、、、、、、、、
					new baomingThread(xuehao, bianhao1).start();;
					
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					// TODO Auto-generated method stub
					
				}
			}).setIcon(R.drawable.jwc);
	// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
	view = LayoutInflater.from(computer.this).inflate(
			R.layout.computer_dialog1, null);
	

	TextView snoTextView=(TextView)view.findViewById(R.id.no);
	snoTextView.setText(xuehao);
	String kemu11=yingyu.getSelectedItem().toString();
	TextView kemutTextView=(TextView)view.findViewById(R.id.kemu);
	kemutTextView.setText(kemu11);
	// 设置我自己定义的布局文件作为弹出框的内容
	builder.setView(view);
	builder.show();
			}
	
			break;
			
			
			//第二個提交按鈕，同上
		case R.id.j2submit:
		
			
			//判断是否已经登录，若没有登录，则先进行登录
			if (xuehao==null) {
				Intent intent=new Intent(computer.this, login.class);
				startActivity(intent); 
			}else
			{
				
			builder = new AlertDialog.Builder(computer.this)
					.setTitle("确认信息").setPositiveButton("确定", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							// TODO Auto-generated method stub
							String kemu2=computer2b.getSelectedItem().toString();
							final String bianhao2=getbianhao(kemu2);
							//启动线程、、、、、、、、、、、、
							new baomingThread(xuehao, bianhao2).start();
							
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							// TODO Auto-generated method stub
							
						}
					}).setIcon(R.drawable.jwc);
			
			// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
			view = LayoutInflater.from(computer.this).inflate(
					R.layout.computer_dialog1, null);
			

			TextView snoTextView1=(TextView)view.findViewById(R.id.no);
			snoTextView1.setText(xuehao);
			TextView kemutTextView1=(TextView)view.findViewById(R.id.kemu);
			String kemu21=computer2b.getSelectedItem().toString();
			kemutTextView1.setText(kemu21);
			// 设置我们自己定义的布局文件作为弹出框的内容
			builder.setView(view);
			builder.show();
			}
			break;
			
			//第三个按钮，同上
		case R.id.j3submit:
			//判断是否已经登录，若没有登录，则先进行登录
			if (xuehao==null) {
				Intent intent=new Intent(computer.this, login.class);
				startActivity(intent); 
			}else
			{
			builder = new AlertDialog.Builder(computer.this)
			.setTitle("确认信息").setPositiveButton("确定", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					// TODO Auto-generated method stub
					 String kemu3=computer3b.getSelectedItem().toString();
						String bianhao3=getbianhao(kemu3);
					new baomingThread(xuehao, bianhao3).start();
					
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					// TODO Auto-generated method stub
					
				}
			}).setIcon(R.drawable.jwc);
			
	// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
	view = LayoutInflater.from(computer.this).inflate(
			R.layout.computer_dialog1, null);
	

	TextView snoTextView2=(TextView)view.findViewById(R.id.no);
	snoTextView2.setText(xuehao);
	 String kemu31=computer3b.getSelectedItem().toString();
	TextView kemutTextView2=(TextView)view.findViewById(R.id.kemu);
	kemutTextView2.setText(kemu31);
	// 设置我们自己定义的布局文件作为弹出框的内容
	builder.setView(view);
	builder.show();
			}
			break;
		}
	}
public String getbianhao( String select ){
		if (select.equals("四  级")) {
			return "11";
			
		}
		if (select.equals("六 级")) {
			return "12";
			
		}
		if (select.equals("C语言  ")) {
			return "21";
			
		}
		if (select.equals("java  ")) {
			return "22";
			
		}
		if (select.equals("c++  ")) {
			return "23";
			
		}
		if (select.equals("vb  ")) {
			return "24";
			
		}
		if (select.equals("vb")) {
			return "34";
			
		}
		if (select.equals("c++")) {
			return "33";
			
		}
		if (select.equals("java")) {
			return "32";
			
		}
		if (select.equals("C语言 ")) {
			return "31";
			
		}
		
		return "meo";

		
	}
private String sendbaoming(String xuehao,String kemu)
{
	
	SyncHttp syncHttp = new SyncHttp();
	//String url = "http://172.17.122.16/sunbin/postbaoming.asp";
	String url = "http://minfy.cn/interface/postbaoming.asp";
	List<Parameter> params = new ArrayList<Parameter>();
	params.add(new Parameter("xuehao", xuehao));
	params.add(new Parameter("kemu", kemu));
	try
	{
		String retStr = syncHttp.httpPost(url, params);
		JSONObject jsonObject = new JSONObject(retStr);
		int retCode = jsonObject.getInt("ret");
		if (0 == retCode)
		{
			return   "报名成功";
		}
		else  return "发布失败";

	} catch (Exception e)
	{
		e.printStackTrace();
		
	}
	return "已经报名无需再报"; 
}

private class baomingThread extends Thread
{
	String xuehao;
	String kemu;
	public baomingThread(String xuehao,String kemu) {
		// TODO Auto-generated constructor stub
		this.xuehao=xuehao;
		this.kemu=kemu;
	}
	
	public void run()
	{
		// 从网络上获取新闻
		String message=sendbaoming(xuehao,kemu);
		Message msg = mHandler.obtainMessage();
		msg.arg1 = SECOND;
		msg.obj = message;
		mHandler.sendMessage(msg);
}

}

}