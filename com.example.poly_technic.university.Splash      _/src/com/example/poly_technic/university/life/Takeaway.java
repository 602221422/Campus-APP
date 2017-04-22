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
	{ "������", "�������", "�ҵ�ζ��", "��ʳ������", "�й��úз�", "����ʳ��", "�������", "����Ե�׷�",
			"����������", "ʳΨ��", "�����˾", "��Ʒ�"};
	private String fandian1[] =
	{ "������� | 33�����ʹ�", "������� | 37�����ʹ�", "������� | 42�����ʹ�", "������� | 28�����ʹ�",
			"������� | 41�����ʹ�", "������� | 31�����ʹ�", "������� | 50�����ʹ�", "������� | 34�����ʹ�",
			"������� | 33�����ʹ�", "������� | 51�����ʹ�", "������� | 34�����ʹ�", "������� | 33�����ʹ�"};
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

	private ArrayList<HashMap<String, Object>> listItems; // ������֡�ͼƬ��Ϣ
	private SimpleAdapter listItemAdapter; // ������

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
	 * ��������������
	 */
	private void initListView()
	{
		listItems = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 12; i++)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemTitle", fandian[i]); // ��������
			map.put("ItemTitle1", fandian1[i]); // ����
			map.put("ItemImage", images[i]);// ����ͼƬ
			map.put("ItemTitle2", "�Ƽ�ָ��");// ͼƬ
			map.put("ItemImage2", xingxing[i]);
			listItems.add(map);
		}
		// ������������Item�Ͷ�̬�����Ӧ��Ԫ��
		listItemAdapter = new SimpleAdapter(this, listItems,// ����Դ
				R.layout.takeaway_item,// ListItem��XML����ʵ��
				// ��̬������ImageItem��Ӧ������
				new String[]
				{ "ItemTitle", "ItemTitle1", "ItemTitle2", "ItemImage",
						"ItemImage2" },
				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
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