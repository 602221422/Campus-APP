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
	// 大图片对应的缩略图源数组
	private Integer[] mThumbIds =
	{ R.drawable.x, R.drawable.x1, R.drawable.x2, R.drawable.x3, R.drawable.x4,
			R.drawable.x5, R.drawable.x6, R.drawable.x7, R.drawable.x8,
			R.drawable.x9, R.drawable.x10, R.drawable.x11, R.drawable.x12,
			R.drawable.x13, R.drawable.x14, R.drawable.x15, R.drawable.x16,
			R.drawable.x17, R.drawable.x18, R.drawable.x19, R.drawable.x20,
			R.drawable.x21, R.drawable.x22, R.drawable.x23, R.drawable.x24,R.drawable.x25,R.drawable.x26
			 };
	// 大图片源数组
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
		// 设置窗口无标题
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
		// 注意在使用一个ImageSwitcher之前，
		// 一定要调用setFactory方法，要不setImageResource这个方法会报空指针异常。
		mSwitcher.setFactory(this);
		// 设置动画效果
		mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in));
		mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));

		Gallery g = (Gallery) findViewById(R.id.gallery);

		// 添加OnItemSelectedListener监听器
		g.setAdapter(new ImageAdapter(this));
		g.setOnItemSelectedListener(this);

	}

	// 创建内部类ImageAdapter
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
			// 设置边界对齐
			i.setAdjustViewBounds(true);
			// 设置布局参数
			i.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			// 设置背景资源
			i.setBackgroundResource(R.drawable.ic_action_search);
			return i;
		}

		private Context mContext;
	}

	// 实现onItemSelected()方法，更换图片
	public void onItemSelected(AdapterView<?> adapter, View v, int position,
			long id)
	{
		// 设置图片资源
		mSwitcher.setImageResource(mImageIds[position]);
		String str = null;
		switch (position)
		{

		case 0:
			str = "校区正门";
			jianjie.setText(str);
			break;
		case 1:
			str = "青荇湖";
			jianjie.setText(str);
			break;
		case 2:
			str = "南院工程学院办公楼";
			jianjie.setText(str);
			break;
		case 3:
			str = "会堂";
			jianjie.setText(str);
			break;
		case 4:
			str = "校园风景";
			jianjie.setText(str);
			break;
		case 5:
			str = "会堂";
			jianjie.setText(str);
			break;
		case 6:
			str = "第一教学楼";
			jianjie.setText(str);
			break;
		case 7:
			str = "第二教学楼";
			jianjie.setText(str);
			break;
		case 8:
			str = "第三教学楼";
			jianjie.setText(str);
			break;
		case 9:
			str = "体育场";
			jianjie.setText(str);
			break;
		case 10:
			str = "时钟";
			jianjie.setText(str);
			break;
		case 11:
			str = "白楼";
			jianjie.setText(str);
			break;
		case 12:
			str = "图书馆";
			jianjie.setText(str);
			break;
		case 13:
			str = "冬日的工大";
			jianjie.setText(str);
			break;case 14:
				str = "冬日的工大";
				jianjie.setText(str);
				break;
			case 15:
				str = "春天的工大";
				jianjie.setText(str);
				break;
			case 16:
				str = "春天的工大";
				jianjie.setText(str);
				break;
			case 17:
				str = "校园风景";
				jianjie.setText(str);
				break;
			case 18:
				str = "毕业生毕业典礼";
				jianjie.setText(str);
				break;
			case 19:
				str = "趣味运动会";
				jianjie.setText(str);
				break;
			case 20:
				str = "体育馆";
				jianjie.setText(str);
				break;
			case 21:
				str = "优美的舞姿";
				jianjie.setText(str);
				break;
			case 22:
				str = "新生军训";
				jianjie.setText(str);
				break;
			case 23:
				str = "藏宝山";
				jianjie.setText(str);
				break;
			case 24:
				str = "学校风景";
				jianjie.setText(str);
				break;
			case 25:
				str = "学生公寓";
				jianjie.setText(str);
				break;
			case 26:
				str = "学生公寓";
				jianjie.setText(str);
				break;
				
		default:
			break;
		}
	}

	public void onNothingSelected(AdapterView<?> arg0)
	{

	}

	// 实现makeView()方法，为ImageView设置布局格式
	public View makeView()
	{
		ImageView i = new ImageView(this);
		// 设置背景颜色
		i.setBackgroundColor(0xFF000000);
		// 设置比例类型
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		// 设置布局参数
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return i;
	}
}