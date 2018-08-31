package com.tcmsoso.webapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.tcmsoso.webapplication.db.History;

import org.litepal.LitePal;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    WebView mWebView;
    WebSettings mWebSettings;
    String webTitle;
    Date webDate;
    String webUrl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //初始化
        init();
        mWebView.loadUrl("https://www.163.com/");

//        mWebView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view,String url){
//                view.loadUrl(url);
//                return true;
//            }
//        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view,String title){
                toolbar.setTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view,int newProgress){
//                if(newProgress < 100){
//                    String progress = newProgress + "%";
//                    loading.setText(progress);
//                } else if(newProgress == 100){
//                    String progress = newProgress + "%";
//                    loading.setText(progress);
//                }
            }
        });

        //设置WebViewClient类
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view,String url,Bitmap favicon){
            }

            @Override
            public void onPageFinished(WebView view,String url){
                webTitle = view.getTitle();
                webUrl = view.getUrl();
                webDate = new Date();

                //在这里插入数据
                History history = new History();
                history.setTitle(webTitle);
                history.setWebUrl(webUrl);
                history.setHistoryDate(webDate);
                history.save();
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.find_history:
                Intent historyIntent = new Intent(MainActivity.this,HistoryList.class);
                startActivity(historyIntent);
                break;
            default:
                break;
        }
        return true;
    }
    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode,event);
    }

    //销毁WebView
    @Override
    protected void onDestroy(){
        if(mWebView != null) {
            mWebView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }



    private void init(){
        mWebView = (WebView) findViewById(R.id.webView1);

        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

    }
}
