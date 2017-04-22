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
	// ����һ���α�
	private Cursor cursor;
	// ����DBHelper��
	private JishibenDBhelper helper;

	@SuppressWarnings("static-access")
	protected void onCreate(Bundle savedInstanceState) {
		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// ���ò��ֱ���
		super.onCreate(savedInstanceState);
		setContentView(R.layout.riji);
		// ��ȡ�Զ�����Ⲽ��
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		// R.layout.biaoti1);

		// ��ȡ���ݿ�ʵ�л�����
		helper = new JishibenDBhelper(this);
		// ��ȡ����Ҫ�Ŀؼ�
		listView = (ListView) findViewById(R.id.listview);
		// ���ò�ѯ����
		cursor = helper.rijiselect();
		// �������ݿ�������
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.riji, cursor, new String[] { helper.TITLE,
						helper.MARCH, helper.NEIRONG }, new int[] {
						R.id.biaoti, R.id.riqi, R.id.neirong });
		//��������
		listView.setAdapter(adapter);
		//�������Ĳ˵�
		listView.setOnCreateContextMenuListener(contextMenuListener);
	}

	// ����Menu�˵�
	@SuppressWarnings("unused")
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu menua = menu.addSubMenu(Menu.NONE, 0, 1, "д�ռ�");
		return super.onCreateOptionsMenu(menu);
	}

	// ���Menu�˵��¼�
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 0:
			Intent intent = new Intent(Jishiben.this, XieRiJi.class);
			startActivity(intent);
			break;

		}

		return super.onOptionsItemSelected(item);
	}

	 //�÷���������ʾ�����Ĳ˵�
	OnCreateContextMenuListener contextMenuListener = new OnCreateContextMenuListener() {
		
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			//��������Ĳ˵�
			menu.add(Menu.NONE, 0, 2, "�޸�");
			menu.add(Menu.NONE, 1, 2, "ɾ��");
			menu.add(Menu.NONE, 2, 2, "ȫ��ɾ��");
			//��������Ĳ˵�����
			menu.setHeaderTitle("�˵�ѡ��");
			//��������Ĳ˵�����ͼƬ
			menu.setHeaderIcon(android.R.drawable.ic_popup_sync);
			
		}
	};
	//��������Ĳ˵����������¼�
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
