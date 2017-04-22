package com.example.poly_technic.university.life;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poly_technic.university.R;

public class travel extends Activity
{

	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.travel);
		Button backbutton;
		backbutton=(Button)this.findViewById(R.id.newsdetails_titlebar_previous);
		backbutton.setOnClickListener(new View.OnClickListener()
		{
			
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}
		});

		final ExpandableListAdapter adapter = new BaseExpandableListAdapter()
		{
			// 设置组视图的图片
//			int[] logos = new int[]
//			{ R.drawable.ic_launcher, R.drawable.ic_launcher,
//					R.drawable.ic_launcher, R.drawable.ic_launcher,
//					R.drawable.ic_launcher };
			// 设置组视图的显示文字
			private String[] generalsTypes = new String[]
			{ "    周围KTV", "     诊所医院", "     常用公交路线",

			"      电影院", "     旅游景点" };
			// 子视图显示文字
			private String[][] generals = new String[][]
			{
			{ "① 夜思缘", "② 巴黎KTV", "③ 大地飞歌", "④ 大唐夜色", "⑤ 新会歌厅" },
			{ "① 辽化总医院", "② 辽阳市第二人民医院", "③ 辽阳市中心医院", "④ 辽阳市第三人民医院", "⑤ 辽阳市中医医院" },
			{ "① 7路公交车", "② 17路公交车", "③ 19路公交车", "④ 27路公交车", "⑤ 31路公交车" },
			{ "① 辽化新视觉3d影院", "② 辽阳东都影城", "③ 大地影院（辽阳贺店)", "④ 九洲影视娱乐城" },
			{ "① 龙鼎山风景区", "② 曹雪芹纪念馆", "③ 东京陵", "④ 广佑寺" }

			};
			


			// 自己定义一个获得文字信息的方法
			TextView getTextView()
			{
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
						ViewGroup.LayoutParams.FILL_PARENT, 64);
				TextView textView = new TextView(travel.this);
				textView.setLayoutParams(lp);
				textView.setGravity(Gravity.CENTER_VERTICAL);
				textView.setPadding(36, 0, 0, 0);
				textView.setTextSize(20);
				textView.setTextColor(Color.BLACK);
				return textView;
			}

			// 重写ExpandableListAdapter中的各个方法

			public int getGroupCount()
			{
				// TODO Auto-generated method stub
				return generalsTypes.length;
			}

			public Object getGroup(int groupPosition)
			{
				// TODO Auto-generated method stub
				return generalsTypes[groupPosition];
			}

			public long getGroupId(int groupPosition)
			{
				// TODO Auto-generated method stub
				return groupPosition;
			}

			public int getChildrenCount(int groupPosition)
			{
				// TODO Auto-generated method stub
				return generals[groupPosition].length;
			}

			public Object getChild(int groupPosition, int childPosition)
			{
				// TODO Auto-generated method stub
				return generals[groupPosition][childPosition];
			}

			public long getChildId(int groupPosition, int childPosition)
			{
				// TODO Auto-generated method stub
				return childPosition;
			}

			public boolean hasStableIds()
			{
				// TODO Auto-generated method stub
				return true;
			}

			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent)
			{
				// TODO Auto-generated method stub
				LinearLayout ll = new LinearLayout(travel.this);
				ll.setOrientation(0);
				TextView textView = getTextView();
				textView.setTextColor(Color.BLACK);
				textView.setText(getGroup(groupPosition).toString());
				ll.addView(textView);

				return ll;
			}

			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent)
			{
				// TODO Auto-generated method stub
				LinearLayout ll = new LinearLayout(travel.this);
				ll.setOrientation(0);
//				ImageView generallogo = new ImageView(travel.this);
//				generallogo
//						.setImageResource(generallogos[groupPosition][childPosition]);
//				ll.addView(generallogo);
				TextView textView = getTextView();
				textView.setText(getChild(groupPosition, childPosition)
						.toString());
				ll.addView(textView);
				return ll;
			}

			public boolean isChildSelectable(int groupPosition,
					int childPosition)
			{
				// TODO Auto-generated method stub
				return true;
			}

		};

		ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.list);
		expandableListView.setAdapter(adapter);

		// 设置item点击的监听器
		expandableListView.setOnChildClickListener(new OnChildClickListener()
		{

			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id)
			{
				AlertDialog.Builder builder;
				View view;
				
				switch (groupPosition)
				{
				case 0:
					switch (childPosition)
					{
					case 0:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("夜思缘");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog1, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					case 1:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("巴黎KTV");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
					 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog2, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					case 2:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("大地飞歌");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog3, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					case 3:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("大唐夜色量贩商务KTV");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog4, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					case 4:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("新会歌厅");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog5, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					}
					break;
				case 1:
					switch (childPosition)
					{
					case 0:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("辽化总医院");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog6, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					case 1:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("辽阳市第二人民医院");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
					 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog7, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					case 2:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("辽阳市中心医院");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog8, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					case 3:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("辽阳市第三人民医院");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog9, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					case 4:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("辽阳市中医医院");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog10, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					}
					break;
				case 2:
					switch (childPosition)
					{
					case 0:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("7路公交车");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog11, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					case 1:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("17路公交车");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
					 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog12, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					case 2:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("19路公交车");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog13, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					case 3:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("27路公交车");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog14, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					case 4:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("31路公交车");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog15, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					}
					break;
				case 3:
					switch (childPosition)
					{
					case 0:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("辽化新视觉3d影院");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog16, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					case 1:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("辽阳东都影城");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
					 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog17, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					case 2:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("大地影院");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog18, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					case 3:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("九洲影视娱乐城");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog19, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					}
					break;
				case 4:
					switch (childPosition)
					{
					case 0:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("龙鼎山风景区");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog20, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					case 1:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("曹雪芹纪念馆");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
					 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog21, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					case 2:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("东京陵");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog22, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					case 3:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("广佑寺");
						// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog23, null);
						// 设置我们自己定义的布局文件作为弹出框的Content
						builder.setView(view);
						builder.show();
						break;
					default:
						break;
					}
					break;
				}
				return false;
			}
		});
	}

}
