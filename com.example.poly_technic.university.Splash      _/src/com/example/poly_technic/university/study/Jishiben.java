package com.example.poly_technic.university.study;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.example.poly_technic.university.R;
public class Jishiben extends Activity {
	Button xinzeng;
	private ListView listView;
	// 定义一个游标
	private Cursor cursor;
	// 定义DBHelper类
	private JishibenDBhelper helper;

	@SuppressWarnings("static-access")
	protected void onCreate(Bundle savedInstanceState) {
		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 设置布局标题
		super.onCreate(savedInstanceState);
		setContentView(R.layout.riji);
		// 获取自定义标题布局
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		// R.layout.biaoti1);

		// 获取数据库实列化对象
		helper = new JishibenDBhelper(this);
		// 获取所需要的控件
		listView = (ListView) findViewById(R.id.listview);
		// 调用查询方法
		cursor = helper.rijiselect();
		// 创建数据库适配器
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.riji, cursor, new String[] { helper.TITLE,
						helper.MARCH, helper.NEIRONG }, new int[] {
						R.id.biaoti, R.id.riqi, R.id.neirong });
		//绑定适配器
		listView.setAdapter(adapter);
		//绑定上下文菜单
		listView.setOnCreateContextMenuListener(contextMenuListener);
	}

	// 创建Menu菜单
	@SuppressWarnings("unused")
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu menua = menu.addSubMenu(Menu.NONE, 0, 1, "写日记");
		return super.onCreateOptionsMenu(menu);
	}

	// 点击Menu菜单事件
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 0:
			Intent intent = new Intent(Jishiben.this, XieRiJi.class);
			startActivity(intent);
			break;

		}

		return super.onOptionsItemSelected(item);
	}

	 //该方法用于显示上下文菜单
	OnCreateContextMenuListener contextMenuListener = new OnCreateContextMenuListener() {
		
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			//添加上下文菜单
			menu.add(Menu.NONE, 0, 2, "修改");
			menu.add(Menu.NONE, 1, 2, "删除");
			menu.add(Menu.NONE, 2, 2, "全部删除");
			//添加上下文菜单标题
			menu.setHeaderTitle("菜单选项");
			//添加上下文菜单标题图片
			menu.setHeaderIcon(android.R.drawable.ic_popup_sync);
			
		}
	};
	//点击上下文菜单所触发的事件
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			break;
		case 1:
//			rijidelete(i);
			break;
		case 2:
			
			break;
		case 3:
			break;
		}
		return super.onContextItemSelected(item);
	}
}
