/*Copyright 2013 Daniel Benedykt

This file is part of AngelList Mobile.

AngelList Mobile is free software: you can redistribute it and/or modify 
it under the terms of the GNU General Public License as published by 
the Free Software Foundation, either version 2 of the License, or 
(at your option) any later version.

AngelList Mobile is distributed in the hope that it will be useful, 
but WITHOUT ANY WARRANTY; without even the implied warranty of 
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
GNU General Public License for more details.

You should have received a copy of the GNU General Public License 
along with AngelList Mobile. If not, see http://www.gnu.org/licenses/.
 */

package org.angellist.angellistmobile;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class LoginActivity extends Activity {

	ProgressDialog mDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/** Called when the user touches the button */
	public void login(View view) {
		// Do something in response to button click
		Intent intent = new Intent(this, WebViewActivity.class);
		startActivityForResult(intent, 1);
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				String code = intent.getStringExtra("result");

				HashMap<String, String> data = new HashMap<String, String>();

				AsyncHttpPost asyncHttpPost = new AsyncHttpPost(data);
				String client_id = getResources().getString(R.string.client_id);
				String client_secret = getResources().getString(
						R.string.client_secret);

				asyncHttpPost
						.execute("https://angel.co/api/oauth/token?client_id="
								+ client_id + "&client_secret=" + client_secret
								+ "&code=" + code
								+ "&grant_type=authorization_code");

			}
			if (resultCode == RESULT_CANCELED) {
				// Write your code if there's no result
			}
		}
	}// onActivityResult

	public class AsyncHttpPost extends AsyncTask<String, String, String> {
		private HashMap<String, String> mData = null;// post data

		/**
		 * constructor
		 */
		public AsyncHttpPost(HashMap<String, String> data) {
			mData = data;
		}

		/**
		 * background
		 */
		@Override
		protected String doInBackground(String... params) {
			byte[] result = null;
			String str = "";
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(params[0]);// in this case, params[0]
													// is URL
			try {
				// set up post data
				ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
				Iterator<String> it = mData.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					nameValuePair.add(new BasicNameValuePair(key, mData
							.get(key)));
				}

				post.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
				HttpResponse response = client.execute(post);
				StatusLine statusLine = response.getStatusLine();
				if (statusLine.getStatusCode() == HttpURLConnection.HTTP_OK) {
					result = EntityUtils.toByteArray(response.getEntity());
					str = new String(result, "UTF-8");
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (Exception e) {
			}
			return str;
		}

		/**
		 * on getting result
		 */
		@Override
		protected void onPostExecute(String result) {
			// access_token
			String accessToken = Parser.parseAccessToken(result);

			Data.setAccessToken(accessToken);
			Data.save(LoginActivity.this);
			
			goToFeedActivity();
			
			mDialog.dismiss();
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			mDialog = ProgressDialog.show(LoginActivity.this, "", "Loading...", false);
			
		}
	}

	private void goToFeedActivity()
	{
		Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
		startActivity(intent);
	}
	
	@Override
	protected void onStart() {
		super.onStart();

		Data.load(this);
		if (!"".equals(Data.getAccessToken())) {
			goToFeedActivity();
		}

	}

}
