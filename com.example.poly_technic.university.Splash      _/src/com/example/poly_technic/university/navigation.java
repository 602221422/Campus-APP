package com.example.poly_technic.university;

import java.util.Timer;
import java.util.TimerTask;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class navigation extends TabActivity {
	private TabHost host;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.navigation);
		//获取tabhost对象
		host =getTabHost();
		//活动的内容
		TabSpec t1=host.newTabSpec("Index").setIndicator("Index").setContent(new Intent(this,MainActivity.class));
		TabSpec t2=host.newTabSpec("Study").setIndicator("Study").setContent(new Intent(this,StudyActivity.class));
		TabSpec t3=host.newTabSpec("Life").setIndicator("Life").setContent(new Intent(this,LifeActivity.class));
		TabSpec t4=host.newTabSpec("Community").setIndicator("Community").setContent(new Intent(this,CommunityActivity.class));
		host.addTab(t1);
		host.addTab(t2);
		host.addTab(t3);
		host.addTab(t4);
		
		RadioGroup group=(RadioGroup)this.findViewById(R.id.main_radio);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				// TODO Auto-generated method stub
				
				switch (checkedId)
				{//首页选项卡被选中
				case R.id.button1:
				{
					host.setCurrentTabByTag("Index");
				}
					
					break;
				case R.id.button2:
				{
					host.setCurrentTabByTag("Study");
				}
					
					break;
				case R.id.button3:
				{
					host.setCurrentTabByTag("Life");
				}
					
					break;
				case R.id.button4:
				{
					host.setCurrentTabByTag("Community");
				}
					
					break;
				default:
					break;
				}
			}
		});
	    
	}
  protected static final int MENU_NAME=Menu.FIRST;
  protected static final int MENU_HELP=Menu.FIRST+1;
  protected static final int MENU_ABOUT=Menu.FIRST+2;

//点击手机menu键
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
  	menu.add(1,MENU_NAME, 1,"切换账号");
  	menu.add(2,MENU_ABOUT, 2,"关于");
  return super.onCreateOptionsMenu(menu);
  }
  
  //menu触发事件
  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
	  Intent intent;
  	// TODO Auto-generated method stub
  	switch (item.getItemId())
  	{
  	case MENU_NAME:
  		 intent=new Intent(navigation.this,login.class);
  		startActivity(intent);
  		break;
  	case MENU_ABOUT:
  		intent=new Intent(navigation.this,About.class);
  		startActivity(intent);
  		break;
  	default:
  		break;
  	}
  	return super.onOptionsItemSelected(item);
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
