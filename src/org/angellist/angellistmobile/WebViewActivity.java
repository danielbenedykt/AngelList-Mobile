package org.angellist.angellistmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {
	 
	private WebView webView;
 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
 
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new HelloWebViewClient());
		
		String client_id = getResources().getString(R.string.client_id);
		
		webView.loadUrl("https://angel.co/api/oauth/authorize?client_id="+client_id+"&response_type=code");
 
	}
 
	
	private class HelloWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }

		@Override
		public void onLoadResource(WebView view, String url) {
			// TODO Auto-generated method stub
			if(url.startsWith("http://localhost/?code="))
			{
				String code = url.substring(23);
				Intent returnIntent = new Intent();
				returnIntent.putExtra("result",code);
				setResult(RESULT_OK,returnIntent);     
				finish();
			}
			else
			{
				super.onLoadResource(view, url);
			}
		}
	    
	}
	
}
