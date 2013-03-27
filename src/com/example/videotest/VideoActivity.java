package com.example.videotest;

import java.net.URL;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.webkit.WebSettings;

public class VideoActivity extends Activity {

	
	private static final String LOG_TAG = "VideoActivity";
	private WebView webView;
	private VideoActivity instance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_view_screen);
		
		instance = this;
		webView = (WebView) findViewById(R.id.webView);
		if(savedInstanceState != null) {
			webView.restoreState(savedInstanceState); 
			}else{}
		Intent intent = getIntent();
		if (intent.hasExtra(VideoListActivity.BK_VIDEO_URL)){
			
		String videoURLString = intent.getExtras().getString(VideoListActivity.BK_VIDEO_URL);
//		Toast.makeText(this, "Got videoURL "+videoURLString, Toast.LENGTH_SHORT).show();
		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDomStorageEnabled(true);
		WebChromeClient webChromeClient = new WebChromeClient(){
			   public void onProgressChanged(WebView view, int progress) {
				   
				   instance.setProgress(progress * 1000);
				   }
				 };
				 
		webView.setWebChromeClient(webChromeClient);
		WebViewClient webViewClient = new WebViewClient() {
			   public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				     Toast.makeText(instance, "Error occures! " + description, Toast.LENGTH_SHORT).show();
				   }
				 };
		webView.setWebViewClient(webViewClient);
    	webView.getSettings().setPluginState(WebSettings.PluginState.ON);
    	webView.getSettings().getUserAgentString();
    	
//    	webView.getSettings().setUserAgentString("Android Mozilla/5.0 AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 Chrome/10.0.648.204");
    	Log.d(LOG_TAG,"getUserAgentString = "+ webView.getSettings().getUserAgentString());
    	if (intent.hasExtra(VideoListActivity.BK_VIDEO_IFRAME)){
//    		webView.loadData(videoURLString, "text/html", "utf-8");
    		final String mimeType = "text/html";
    	    final String encoding = "UTF-8";	
    		webView.loadDataWithBaseURL("", videoURLString, mimeType, encoding, "");
    	}else{
    		webView.loadUrl(videoURLString);
		}
		}
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		webView.saveState(outState);
	}
	
	@Override
	public void onBackPressed() {
		webView.stopLoading();
		webView.loadUrl("");
		webView.clearView();
		
		super.onBackPressed();
	}
	 /* HTML5WebView mWebView;
      
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        mWebView = new HTML5WebView(this);
	        if (savedInstanceState != null) {
	                mWebView.restoreState(savedInstanceState);
	        } else {
	        	if (getIntent().hasExtra(Constants.BK_VIDEOWIEV_URL)){
	    			
	        		String videoURLString = getIntent().getExtras().getString(Constants.BK_VIDEOWIEV_URL);
	                mWebView.loadUrl(videoURLString);
	                //mWebView.loadUrl("file:///data/bbench/index.html");
	        	}
	        }
	        
	        setContentView(mWebView.getLayout());
	    }
	    
	    @Override
	    public void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        mWebView.saveState(outState);
	    }
	    
	    @Override
	    public void onStop() {
	        super.onStop();
	        mWebView.stopLoading();
	    }
	    
	    @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	            if (mWebView.inCustomView()) {
	                mWebView.hideCustomView();
	                return true;
	            }
	        }
	        return super.onKeyDown(keyCode, event);
	    }
	    
	    @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	         super.onConfigurationChanged(newConfig);
	    }*/
}
