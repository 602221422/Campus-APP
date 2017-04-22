package com.example.poly_technic.university.life;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.poly_technic.university.R;

public class Takeaway extends ListActivity
{
	private String fandian[] =
	{ "韩王府", "给力快餐", "家的味道", "六食堂胖仔", "中国好盒饭", "美好食光", "吉祥混沌", "无名缘米粉",
			"聚香沅米线", "食唯仙", "町田寿司", "大黄风"};
	private String fandian1[] =
	{ "免费配送 | 33分钟送达", "免费配送 | 37分钟送达", "免费配送 | 42分钟送达", "免费配送 | 28分钟送达",
			"免费配送 | 41分钟送达", "免费配送 | 31分钟送达", "免费配送 | 50分钟送达", "免费配送 | 34分钟送达",
			"免费配送 | 33分钟送达", "免费配送 | 51分钟送达", "免费配送 | 34分钟送达", "免费配送 | 33分钟送达"};
	private int images[] =
	{ R.drawable.hanwangfu, R.drawable.geili, R.drawable.jiadeweidao,
			R.drawable.liushitang, R.drawable.haohefan, R.drawable.meihaoshiguang,
			R.drawable.jixianghudun, R.drawable.wumingyuan,
			R.drawable.juxiangyuan, R.drawable.shiweixian,
			R.drawable.dingtian, R.drawable.dahuangfeng};
	private int xingxing[] =
	{ R.drawable.wuxing, R.drawable.sixing, R.drawable.sixing,
			R.drawable.wuxing, R.drawable.sanxing, R.drawable.sixing,
			R.drawable.sanxing, R.drawable.sanxing, R.drawable.sanxing,
			R.drawable.sanxing, R.drawable.sanxing, R.drawable.sanxing};

	private ArrayList<HashMap<String, Object>> listItems; // 存放文字、图片信息
	private SimpleAdapter listItemAdapter; // 适配器

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		setContentView(R.layout.takeaway);
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
		initListView();
		this.setListAdapter(listItemAdapter);
	}

	/**
	 * 设置适配器内容
	 */
	private void initListView()
	{
		listItems = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 12; i++)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemTitle", fandian[i]); // 餐厅名字
			map.put("ItemTitle1", fandian1[i]); // 文字
			map.put("ItemImage", images[i]);// 餐厅图片
			map.put("ItemTitle2", "推荐指数");// 图片
			map.put("ItemImage2", xingxing[i]);
			listItems.add(map);
		}
		// 生成适配器的Item和动态数组对应的元素
		listItemAdapter = new SimpleAdapter(this, listItems,// 数据源
				R.layout.takeaway_item,// ListItem的XML布局实现
				// 动态数组与ImageItem对应的子项
				new String[]
				{ "ItemTitle", "ItemTitle1", "ItemTitle2", "ItemImage",
						"ItemImage2" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[]
				{ R.id.ItemTitle, R.id.ItemTitle1, R.id.ItemTitle2,
						R.id.ItemImage, R.id.ItemImage2 });
	}

	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		// TODO Auto-generated method stub
		switch (position)
		{
		case 0:
			Intent intent0 = new Intent();
			intent0.setClass(getApplicationContext(), Hanwangfu.class);
			startActivity(intent0);
			break;
		case 1:
			Intent intent1 = new Intent();
			intent1.setClass(getApplicationContext(), Geili.class);
			startActivity(intent1);
			break;
		case 2:
			Intent intent2 = new Intent(getApplicationContext(),
					Jiadeweidao.class);
			startActivity(intent2);
			break;
		case 3:
			Intent intent3 = new Intent();
			intent3.setClass(getApplicationContext(), Liushitang.class);
			startActivity(intent3);
			break;
		case 4:
			Intent intent4 = new Intent();
			intent4.setClass(getApplicationContext(), Haohefan.class);
			startActivity(intent4);
			break;
		case 5:
			Intent intent5 = new Intent();
			intent5.setClass(getApplicationContext(), Meihaoshiguang.class);
			startActivity(intent5);
			break;
		case 6:
			Intent intent6 = new Intent();
			intent6.setClass(getApplicationContext(),Jixiang.class);
			startActivity(intent6);
			break;
		case 7:
			Intent intent7 = new Intent();
			intent7.setClass(getApplicationContext(), Wumingyuan.class);
			startActivity(intent7);
			break;
		case 8:
			Intent intent8 = new Intent();
			intent8.setClass(getApplicationContext(), Juxiangyuan.class);
			startActivity(intent8);
			break;
		case 9:
			Intent intent9 = new Intent();
			intent9.setClass(getApplicationContext(), Shiweixian.class);
			startActivity(intent9);
			break;
		case 10:
			Intent intent10= new Intent();
			intent10.setClass(getApplicationContext(), Dingtian.class);
			startActivity(intent10);
			break;
		case 11:
			Intent intent11 = new Intent();
			intent11.setClass(getApplicationContext(), Dahuangfeng.class);
			startActivity(intent11);
			break;
		}
	}

}