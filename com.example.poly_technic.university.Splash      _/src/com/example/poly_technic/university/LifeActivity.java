package com.example.poly_technic.university;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.poly_technic.university.life.Campus_know;
import com.example.poly_technic.university.life.Entertainment;
import com.example.poly_technic.university.life.Found;
import com.example.poly_technic.university.life.Takeaway;
import com.example.poly_technic.university.life.energy_charge;
import com.example.poly_technic.university.life.travel;

public class LifeActivity extends Activity
{
	String life[] =
	{ "����", "Ѱ������", "��Ѳ�ѯ", "У԰����", "У԰֪��", "����", "У԰һ��", "����" };
	private GridView gridView2;
//ͼ��
	int l_image[] =
		{ R.drawable.l_waimai, R.drawable.l_xuunwu,
				R.drawable.l_dianfei, R.drawable.l_chuxing,
				R.drawable.l_zhidao, R.drawable.l_yule,R.drawable.l_xiaoyuyijiao,R.drawable.l_gengduo };
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.life_layout);
		gridView2 = (GridView) this.findViewById(R.id.gridView2);
		gridView2.setColumnWidth(100);
		gridView2.setVerticalSpacing(10);
		gridView2.setHorizontalSpacing(10);
		gridView2.setSelector(new ColorDrawable(Color.TRANSPARENT));//���ð���ʱ�ޱ���ɫ
		ArrayList<HashMap<String, Object>> meumList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 8; i++)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage",l_image[i]);
			map.put("ItemText", "" + life[i]);
			meumList.add(map);
		}
		SimpleAdapter saItem = new SimpleAdapter(this, meumList, // ����Դ
				R.layout.item, // xmlʵ��
				new String[]
				{ "ItemImage", "ItemText" }, // ��Ӧmap��Key
				new int[]
				{ R.id.ItemImage, R.id.ItemText }); // ��ӦR��Id

		// ���Item��������
		gridView2.setAdapter(saItem);
		// ��ӵ���¼�
		gridView2.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
			{
			switch (arg2 + 1)
				{
				case 1:
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(), Takeaway.class);
					startActivity(intent1);
					break;
				case 2:
					Intent intent2 = new Intent(getApplicationContext(),
							Found.class);
					startActivity(intent2);
					break;
				case 3:
					Intent intent3 = new Intent();
					intent3.setClass(getApplicationContext(), energy_charge.class);
					startActivity(intent3);
					break;
				case 4:
					Intent intent4 = new Intent();
					intent4.setClass(getApplicationContext(), travel.class);
					startActivity(intent4);
					break;
				case 5:
					Intent intent5 = new Intent();
					intent5.setClass(getApplicationContext(), Campus_know.class);
					startActivity(intent5);
					break;
				case 6:
					Intent intent6 = new Intent();
					intent6.setClass(getApplicationContext(),Entertainment.class);
					startActivity(intent6);
					break;
				case 7:
					Intent intent7 = new Intent();
					intent7.setClass(getApplicationContext(), com.example.poly_technic.university.life.CampusScenery.class);
					startActivity(intent7);
					break;
				case 8:
					 Toast.makeText(getApplicationContext(), "�����ڴ�������", Toast.LENGTH_SHORT)
					 .show();
					break;
				default:
					break;
				}
			}
		});

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
	                  isExit = false; // ȡ���˳�  
	              }  
	          }, 2000); // ���2������û�а��·��ؼ�����������ʱ��ȡ�����ղ�ִ�е�����  
	    
	      } else {  
	          finish();  
	          System.exit(0);  
	      }  
	  } 
	  
}
