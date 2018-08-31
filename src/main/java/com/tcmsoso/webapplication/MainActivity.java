package com.tcmsoso.webapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
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
    TextView begingLoading,endLoading,loading,mTitle;
    Button showHistory;
    String webTitle;
    Date webDate;
    String webUrl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //初始化
        init();
        mWebView.loadUrl("https://www.163.com/");

        showHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent historyIntent = new Intent(MainActivity.this,HistoryList.class);
                startActivity(historyIntent);

            }
        });

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
                Log.d("MainActivity","标题在这里");
                mTitle.setText(title);


            }

            @Override
            public void onProgressChanged(WebView view,int newProgress){
                if(newProgress < 100){
                    String progress = newProgress + "%";
                    loading.setText(progress);
                } else if(newProgress == 100){
                    String progress = newProgress + "%";
                    loading.setText(progress);
                }
            }
        });

        //设置WebViewClient类
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view,String url,Bitmap favicon){
                Log.d("MainActivity","开始加载了");
                begingLoading.setText("开始加载了");
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

                endLoading.setText("结束加载了");
            }
        });
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
        begingLoading = (TextView) findViewById(R.id.text_beginLoading);
        endLoading = (TextView) findViewById(R.id.text_endLoading);
        loading = (TextView) findViewById(R.id.text_Loading);
        mTitle = (TextView) findViewById(R.id.title);

        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        showHistory = findViewById(R.id.show_history);

    }
}
