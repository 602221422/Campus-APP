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
			// ��������ͼ��ͼƬ
//			int[] logos = new int[]
//			{ R.drawable.ic_launcher, R.drawable.ic_launcher,
//					R.drawable.ic_launcher, R.drawable.ic_launcher,
//					R.drawable.ic_launcher };
			// ��������ͼ����ʾ����
			private String[] generalsTypes = new String[]
			{ "    ��ΧKTV", "     ����ҽԺ", "     ���ù���·��",

			"      ��ӰԺ", "     ���ξ���" };
			// ����ͼ��ʾ����
			private String[][] generals = new String[][]
			{
			{ "�� ҹ˼Ե", "�� ����KTV", "�� ��طɸ�", "�� ����ҹɫ", "�� �»����" },
			{ "�� �ɻ���ҽԺ", "�� �����еڶ�����ҽԺ", "�� ����������ҽԺ", "�� �����е�������ҽԺ", "�� ��������ҽҽԺ" },
			{ "�� 7·������", "�� 17·������", "�� 19·������", "�� 27·������", "�� 31·������" },
			{ "�� �ɻ����Ӿ�3dӰԺ", "�� ��������Ӱ��", "�� ���ӰԺ�������ص�)", "�� ����Ӱ�����ֳ�" },
			{ "�� ����ɽ�羰��", "�� ��ѩ�ۼ����", "�� ������", "�� ������" }

			};
			


			// �Լ�����һ�����������Ϣ�ķ���
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

			// ��дExpandableListAdapter�еĸ�������

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

		// ����item����ļ�����
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
						builder.setTitle("ҹ˼Ե");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog1, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
						builder.setView(view);
						builder.show();
						break;
					case 1:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("����KTV");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
					 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog2, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
						builder.setView(view);
						builder.show();
						break;
					case 2:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("��طɸ�");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog3, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
						builder.setView(view);
						builder.show();
						break;
					case 3:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("����ҹɫ��������KTV");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog4, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
						builder.setView(view);
						builder.show();
						break;
					case 4:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("�»����");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog5, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
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
						builder.setTitle("�ɻ���ҽԺ");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog6, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
						builder.setView(view);
						builder.show();
						break;
					case 1:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("�����еڶ�����ҽԺ");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
					 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog7, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
						builder.setView(view);
						builder.show();
						break;
					case 2:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("����������ҽԺ");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog8, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
						builder.setView(view);
						builder.show();
						break;
					case 3:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("�����е�������ҽԺ");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog9, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
						builder.setView(view);
						builder.show();
						break;
					case 4:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("��������ҽҽԺ");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog10, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
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
						builder.setTitle("7·������");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog11, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
						builder.setView(view);
						builder.show();
						break;
					case 1:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("17·������");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
					 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog12, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
						builder.setView(view);
						builder.show();
						break;
					case 2:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("19·������");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog13, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
						builder.setView(view);
						builder.show();
						break;
					case 3:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("27·������");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog14, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
						builder.setView(view);
						builder.show();
						break;
					case 4:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("31·������");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog15, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
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
						builder.setTitle("�ɻ����Ӿ�3dӰԺ");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog16, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
						builder.setView(view);
						builder.show();
						break;
					case 1:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("��������Ӱ��");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
					 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog17, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
						builder.setView(view);
						builder.show();
						break;
					case 2:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("���ӰԺ");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog18, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
						builder.setView(view);
						builder.show();
						break;
					case 3:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("����Ӱ�����ֳ�");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog19, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
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
						builder.setTitle("����ɽ�羰��");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog20, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
						builder.setView(view);
						builder.show();
						break;
					case 1:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("��ѩ�ۼ����");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
					 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog21, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
						builder.setView(view);
						builder.show();
						break;
					case 2:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("������");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog22, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
						builder.setView(view);
						builder.show();
						break;
					case 3:
						 builder = new AlertDialog.Builder(
								travel.this);
						builder.setTitle("������");
						// ͨ��LayoutInflater������һ��xml�Ĳ����ļ���Ϊһ��View����
						 view = LayoutInflater.from(travel.this).inflate(
								R.layout.dialog23, null);
						// ���������Լ�����Ĳ����ļ���Ϊ�������Content
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
