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

import com.tcmsoso.webapplication.db.BookMark;
import com.tcmsoso.webapplication.db.History;
import com.tcmsoso.webapplication.util.util;

import org.litepal.LitePal;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    WebView mWebView;
    WebSettings mWebSettings;
    String webTitle;
    Date webDate;
    String webUrl;

    View webViewBack;
    View webViewForward;
    View webViewHome;
    View webViewWindow;
    View webViewSettings;

    boolean bookMarkIs = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //初始化
        init();
        mWebView.loadUrl("file:///android_asset/homepage.html");

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

                checkBookMark();
                }
        });
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.webView_back:
                if(mWebView.canGoBack()){
                    mWebView.goBack();
                }
                break;
            case R.id.webView_forward:
                if(mWebView.canGoBack()){
                    mWebView.goForward();
                }
                break;
            case R.id.webView_home:
                break;
            case R.id.webView_window:
                break;
            case R.id.webView_setting:
                break;
            default:
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        if (bookMarkIs){
            menu.findItem(R.id.book_mark_false).setVisible(false);
            menu.findItem(R.id.book_mark_true).setVisible(true);
            menu.findItem(R.id.find_history).setVisible(true);
            menu.findItem(R.id.find_bookmark).setVisible(true);
        } else{
            menu.findItem(R.id.book_mark_false).setVisible(true);
            menu.findItem(R.id.book_mark_true).setVisible(false);
            menu.findItem(R.id.find_history).setVisible(true);
            menu.findItem(R.id.find_bookmark).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.find_history:
                Intent historyIntent = new Intent(MainActivity.this,HistoryList.class);
                startActivity(historyIntent);
                break;
            case R.id.book_mark_false:
                BookMark bookMark = new BookMark();
                bookMark.setTitle(webTitle);
                bookMark.setDate(webDate);
                bookMark.setMarkUrl(webUrl);
                bookMark.save();

                checkBookMark();
                break;
            case R.id.book_mark_true:
                util.deleteBookMark(webTitle,webUrl);
                checkBookMark();
                break;
            case R.id.find_bookmark:
                Intent bookMarkIntent = new Intent(MainActivity.this,BookMarkActivity.class);
                startActivity(bookMarkIntent);
                break;
            default:
                break;
        }
        return true;
    }

    //书签图标变化
    private void checkBookMark(){
        if(util.isExistBookMark(webTitle,webUrl)){
            bookMarkIs = true;
            invalidateOptionsMenu();
        } else {
            bookMarkIs = false;
            invalidateOptionsMenu();
        }
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

        webViewBack = findViewById(R.id.webView_back);
        webViewForward = findViewById(R.id.webView_forward);
        webViewHome = findViewById(R.id.webView_home);
        webViewWindow = findViewById(R.id.webView_window);
        webViewSettings = findViewById(R.id.webView_setting);

        webViewBack.setOnClickListener(this);
        webViewForward.setOnClickListener(this);
        webViewHome.setOnClickListener(this);
        webViewWindow.setOnClickListener(this);
        webViewSettings.setOnClickListener(this);

    }
}
