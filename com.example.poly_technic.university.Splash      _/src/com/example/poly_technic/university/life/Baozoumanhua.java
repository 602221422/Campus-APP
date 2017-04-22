
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
        
      //����һ����ҳ

        webView.loadUrl("http://baozoumanhua.com/");
      //�����ҳ����״���ص�������ʹ����WebViewClient�������ڲ��෽ʽ������㲻֪��WebViewClient������Щ������Ӧ�Ļص������Ļ���������ڴ�����ѡ��WebViewClient���Ҽ�ѡ��Դ����--->����/ʵ�ַ������ڵ����ĶԻ����а�����WebViewClient�����п��Ը��ǵķ���������������������Ļ�����������ҳ�е�����ʱ���ᵯ��һ������ʾ��ʹ���������������������ӡ�
        webView.setWebViewClient(new WebViewClient(){
        //��ҳ���ؿ�ʼʱ���ã���ʾ������ʾ��ת������
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        // TODO Auto-generated method stub
                        super.onPageStarted(view, url, favicon);
                        progressBar.setVisibility(android.view.View.VISIBLE);
//                        Toast.makeText(ElecHall.this, "onPageStarted", 2).show();
                    }

        //��ҳ�������ʱ���ã����ؼ�����ʾ��ת������
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        // TODO Auto-generated method stub
                        super.onPageFinished(view, url);
                        progressBar.setVisibility(android.view.View.GONE);
//                        Toast.makeText(ElecHall.this, "onPageFinished", 2).show();
                    }
        //��ҳ����ʧ��ʱ���ã����ؼ�����ʾ��ת������
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
