package com.example.poly_technic.university.life;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.example.poly_technic.university.R;

public class CampusScenery extends Activity implements OnItemSelectedListener,
		ViewFactory
{
	Button backbutton;
	private TextView jianjie;
	private ImageSwitcher mSwitcher;
	// ��ͼƬ��Ӧ������ͼԴ����
	private Integer[] mThumbIds =
	{ R.drawable.x, R.drawable.x1, R.drawable.x2, R.drawable.x3, R.drawable.x4,
			R.drawable.x5, R.drawable.x6, R.drawable.x7, R.drawable.x8,
			R.drawable.x9, R.drawable.x10, R.drawable.x11, R.drawable.x12,
			R.drawable.x13, R.drawable.x14, R.drawable.x15, R.drawable.x16,
			R.drawable.x17, R.drawable.x18, R.drawable.x19, R.drawable.x20,
			R.drawable.x21, R.drawable.x22, R.drawable.x23, R.drawable.x24,R.drawable.x25,R.drawable.x26
			 };
	// ��ͼƬԴ����
	private Integer[] mImageIds =
		{ R.drawable.x, R.drawable.x1, R.drawable.x2, R.drawable.x3, R.drawable.x4,
			R.drawable.x5, R.drawable.x6, R.drawable.x7, R.drawable.x8,
			R.drawable.x9, R.drawable.x10, R.drawable.x11, R.drawable.x12,
			R.drawable.x13, R.drawable.x14, R.drawable.x15, R.drawable.x16,
			R.drawable.x17, R.drawable.x18, R.drawable.x19, R.drawable.x20,
			R.drawable.x21, R.drawable.x22, R.drawable.x23, R.drawable.x24,R.drawable.x25,R.drawable.x26
			 };

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// ���ô����ޱ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.campus_scenery);
		backbutton = (Button) this
				.findViewById(R.id.newsdetails_titlebar_previous);
		jianjie = (TextView) this.findViewById(R.id.jianjie);
		backbutton.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}
		});
		mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
		// ע����ʹ��һ��ImageSwitcher֮ǰ��
		// һ��Ҫ����setFactory������Ҫ��setImageResource��������ᱨ��ָ���쳣��
		mSwitcher.setFactory(this);
		// ���ö���Ч��
		mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in));
		mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));

		Gallery g = (Gallery) findViewById(R.id.gallery);

		// ���OnItemSelectedListener������
		g.setAdapter(new ImageAdapter(this));
		g.setOnItemSelectedListener(this);

	}

	// �����ڲ���ImageAdapter
	public class ImageAdapter extends BaseAdapter
	{
		public ImageAdapter(Context c)
		{
			mContext = c;
		}

		public int getCount()
		{
			return mThumbIds.length;
		}

		public Object getItem(int position)
		{
			return position;
		}

		public long getItemId(int position)
		{
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent)
		{
			ImageView i = new ImageView(mContext);

			i.setImageResource(mThumbIds[position]);
			// ���ñ߽����
			i.setAdjustViewBounds(true);
			// ���ò��ֲ���
			i.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			// ���ñ�����Դ
			i.setBackgroundResource(R.drawable.ic_action_search);
			return i;
		}

		private Context mContext;
	}

	// ʵ��onItemSelected()����������ͼƬ
	public void onItemSelected(AdapterView<?> adapter, View v, int position,
			long id)
	{
		// ����ͼƬ��Դ
		mSwitcher.setImageResource(mImageIds[position]);
		String str = null;
		switch (position)
		{

		case 0:
			str = "У������";
			jianjie.setText(str);
			break;
		case 1:
			str = "������";
			jianjie.setText(str);
			break;
		case 2:
			str = "��Ժ����ѧԺ�칫¥";
			jianjie.setText(str);
			break;
		case 3:
			str = "����";
			jianjie.setText(str);
			break;
		case 4:
			str = "У԰�羰";
			jianjie.setText(str);
			break;
		case 5:
			str = "����";
			jianjie.setText(str);
			break;
		case 6:
			str = "��һ��ѧ¥";
			jianjie.setText(str);
			break;
		case 7:
			str = "�ڶ���ѧ¥";
			jianjie.setText(str);
			break;
		case 8:
			str = "������ѧ¥";
			jianjie.setText(str);
			break;
		case 9:
			str = "������";
			jianjie.setText(str);
			break;
		case 10:
			str = "ʱ��";
			jianjie.setText(str);
			break;
		case 11:
			str = "��¥";
			jianjie.setText(str);
			break;
		case 12:
			str = "ͼ���";
			jianjie.setText(str);
			break;
		case 13:
			str = "���յĹ���";
			jianjie.setText(str);
			break;case 14:
				str = "���յĹ���";
				jianjie.setText(str);
				break;
			case 15:
				str = "����Ĺ���";
				jianjie.setText(str);
				break;
			case 16:
				str = "����Ĺ���";
				jianjie.setText(str);
				break;
			case 17:
				str = "У԰�羰";
				jianjie.setText(str);
				break;
			case 18:
				str = "��ҵ����ҵ����";
				jianjie.setText(str);
				break;
			case 19:
				str = "Ȥζ�˶���";
				jianjie.setText(str);
				break;
			case 20:
				str = "������";
				jianjie.setText(str);
				break;
			case 21:
				str = "����������";
				jianjie.setText(str);
				break;
			case 22:
				str = "������ѵ";
				jianjie.setText(str);
				break;
			case 23:
				str = "�ر�ɽ";
				jianjie.setText(str);
				break;
			case 24:
				str = "ѧУ�羰";
				jianjie.setText(str);
				break;
			case 25:
				str = "ѧ����Ԣ";
				jianjie.setText(str);
				break;
			case 26:
				str = "ѧ����Ԣ";
				jianjie.setText(str);
				break;
				
		default:
			break;
		}
	}

	public void onNothingSelected(AdapterView<?> arg0)
	{

	}

	// ʵ��makeView()������ΪImageView���ò��ָ�ʽ
	public View makeView()
	{
		ImageView i = new ImageView(this);
		// ���ñ�����ɫ
		i.setBackgroundColor(0xFF000000);
		// ���ñ�������
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		// ���ò��ֲ���
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return i;
	}
}