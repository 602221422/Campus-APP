
package com.example.poly_technic.university.life;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.poly_technic.university.R;

public class Baozoumanhua extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baozoumanhua);
        WebView webView = (WebView)findViewById(R.id.webView);
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
        webView.getSettings().setJavaScriptEnabled(true);
        
      //加载一个网页

        webView.loadUrl("http://baozoumanhua.com/");
      //添加网页加载状况回调，这里使用了WebViewClient的匿名内部类方式，如果你不知道WebViewClient类有哪些可以响应的回调函数的话，你可以在代码中选中WebViewClient，右键选择源代码--->覆盖/实现方法，在弹出的对话框中包含了WebViewClient的所有可以覆盖的方法。如果不添加这个方法的话，当你点击网页中的链接时，会弹出一个框提示你使用哪种浏览器来打开这个链接。
        webView.setWebViewClient(new WebViewClient(){
        //网页加载开始时调用，显示加载提示旋转进度条
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        // TODO Auto-generated method stub
                        super.onPageStarted(view, url, favicon);
                        progressBar.setVisibility(android.view.View.VISIBLE);
//                        Toast.makeText(ElecHall.this, "onPageStarted", 2).show();
                    }

        //网页加载完成时调用，隐藏加载提示旋转进度条
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        // TODO Auto-generated method stub
                        super.onPageFinished(view, url);
                        progressBar.setVisibility(android.view.View.GONE);
//                        Toast.makeText(ElecHall.this, "onPageFinished", 2).show();
                    }
        //网页加载失败时调用，隐藏加载提示旋转进度条
                    @Override
                    public void onReceivedError(WebView view, int errorCode,
                            String description, String failingUrl) {
                        // TODO Auto-generated method stub
                        super.onReceivedError(view, errorCode, description, failingUrl);
                        progressBar.setVisibility(android.view.View.GONE);
//                        Toast.makeText(ElecHall.this, "onReceivedError", 2).show();
                    }
                    
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
