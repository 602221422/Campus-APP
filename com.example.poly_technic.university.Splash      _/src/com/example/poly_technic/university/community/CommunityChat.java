package com.example.poly_technic.university.community;

import com.example.poly_technic.university.R;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Button;
import android.widget.ProgressBar;

public class CommunityChat extends Activity {
	private WebView webview;
	private  String url ="http://minfy.cn/chat/index.asp";
	String cid;
	String sno;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.communitychat);
		  webview=(WebView)findViewById(R.id.webView1);
	        WebSettings setweb=webview.getSettings();
	        Button backButton=(Button)findViewById(R.id.chat_titlebar_previous);
	        backButton.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
					
				}
			});
	        Intent intent = getIntent();
			Bundle bundle = intent.getExtras();
			//获取点击位置    
			cid = bundle.getString("id");
			sno=bundle.getString("sno");
			url=url+"?"+"id="+cid+"&"+"sno="+sno;
	        setweb.setJavaScriptCanOpenWindowsAutomatically(true);
	        setweb.setBuiltInZoomControls(true);
	       // setweb.setLoadsImagesAutomatically(true);
	        setweb.setLoadWithOverviewMode(true);
	        setweb.setUseWideViewPort(true);
	        setweb.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
	        setweb.setJavaScriptEnabled(true);
	        webview.loadUrl(url);
	        webview.setWebViewClient(new WebViewClient(){//页面始终在webview中显示
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {
	                view.loadUrl(url);
	                return true;
	            }
	        });
	}

}