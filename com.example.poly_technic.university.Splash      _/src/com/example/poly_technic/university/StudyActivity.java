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
	String study[] = { "成绩查询", "等级考试报名", "课表查询", "图书馆查书","学习资料",
			"学习工具", "更多" };

	// 图标数组
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
		 ColorDrawable(Color.TRANSPARENT));//设置按下时无背景色
		 ArrayList<HashMap<String, Object>> meumList = new
		 ArrayList<HashMap<String, Object>>();
		 for (int i = 0; i < 7; i++)
		 {
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 map.put("ItemImage",image[i]);
		 map.put("ItemText", "" + study[i]);
		 meumList.add(map);
		 }
		 SimpleAdapter saItem = new SimpleAdapter(this, meumList, R.layout.item, new String[] { "ItemImage", "ItemText" },new int[]{ R.id.ItemImage, R.id.ItemText }); // 对应R的Id
		
		 // 添加Item到网格中
		 gridView1.setAdapter(saItem); 
		 // 添加点击事件
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
		 Toast.makeText(getApplicationContext(), "敬请期待。。。",
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
	                  isExit = false; // 取消退出  
	              }  
	          }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务  
	    
	      } else {  
	          finish();  
	          System.exit(0);  
	      }  

}
}
