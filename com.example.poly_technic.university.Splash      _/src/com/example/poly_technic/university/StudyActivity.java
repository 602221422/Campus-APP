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

import com.example.poly_technic.university.study.computer;
import com.example.poly_technic.university.study.grade;
import com.example.poly_technic.university.study.learn_tool;
import com.example.poly_technic.university.study.learning_materials;
import com.example.poly_technic.university.study.library;
import com.example.poly_technic.university.study.schedule;

public class StudyActivity extends Activity {
	private GridView gridView1;
	String study[] = { "�ɼ���ѯ", "�ȼ����Ա���", "�α��ѯ", "ͼ��ݲ���","ѧϰ����",
			"ѧϰ����", "����" };

	// ͼ������
	int image[] = { R.drawable.chengjichaxun, R.drawable.computer,
			R.drawable.kebiaochaxun, R.drawable.tushuguanchashu,
			R.drawable.xuexiziliao,
			R.drawable.xuexigongju, R.drawable.gengduo };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.study_layout);
		 gridView1 = (GridView) this.findViewById(R.id.gridView1);
		 gridView1.setColumnWidth(100);
		 gridView1.setHorizontalSpacing(10);
		 gridView1.setVerticalSpacing(10);
		 gridView1.setSelector(new
		 ColorDrawable(Color.TRANSPARENT));//���ð���ʱ�ޱ���ɫ
		 ArrayList<HashMap<String, Object>> meumList = new
		 ArrayList<HashMap<String, Object>>();
		 for (int i = 0; i < 7; i++)
		 {
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 map.put("ItemImage",image[i]);
		 map.put("ItemText", "" + study[i]);
		 meumList.add(map);
		 }
		 SimpleAdapter saItem = new SimpleAdapter(this, meumList, R.layout.item, new String[] { "ItemImage", "ItemText" },new int[]{ R.id.ItemImage, R.id.ItemText }); // ��ӦR��Id
		
		 // ���Item��������
		 gridView1.setAdapter(saItem); 
		 // ��ӵ���¼�
		 gridView1.setOnItemClickListener(new OnItemClickListener()
		 {
		 public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		 long arg3)
		 {
		 switch (arg2 + 1)
		 {
		 case 1:
		 Intent intent1 = new Intent();
		 intent1.setClass(getApplicationContext(), grade.class);
		 startActivity(intent1);
		 break;
		 case 2:
		 Intent intent2 = new Intent(getApplicationContext(),computer.class);
		 startActivity(intent2);
		 break;
		 case 3:
		 Intent intent3 = new Intent();
		 intent3.setClass(getApplicationContext(), schedule.class);
		 startActivity(intent3);
		 break;
		 case 4:
		 Intent intent4 = new Intent();
		 intent4.setClass(getApplicationContext(), library.class);
		 startActivity(intent4);
		 break;
		 case 5:
		 Intent intent6 = new Intent();
		 intent6.setClass(getApplicationContext(), learning_materials.class);
		 startActivity(intent6);
		 break;
		 case 6:
		 Intent intent7 = new Intent();
		 intent7.setClass(getApplicationContext(), learn_tool.class);
		 startActivity(intent7);
		 break;
		 case 7:
		 Toast.makeText(getApplicationContext(), "�����ڴ�������",
		 Toast.LENGTH_SHORT)
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
