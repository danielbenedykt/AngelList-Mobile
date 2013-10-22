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

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fedorvlasov.lazylist.ImageLoader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UserActivity extends Activity {

	ProgressDialog mDialog;
	public ImageLoader imageLoader;
	ImageView imageView;
	TextView textViewLocations, textViewRoles;
	Button buttonTw, buttonFb, buttonLI, buttonBlog;
	String tw, fb, li, blog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);

		//Uri data = getIntent().getData();
		String userid = getIntent().getDataString().substring(getIntent().getDataString().lastIndexOf("/")+1);
		imageLoader = new ImageLoader(this.getApplicationContext());
		imageView = (ImageView) this.findViewById(R.id.imageViewUser);
		textViewLocations = (TextView) this.findViewById(R.id.textViewLocations);
		textViewRoles = (TextView) this.findViewById(R.id.textViewRoles);
		
		buttonTw = (Button) this.findViewById(R.id.buttonTw);
		buttonFb = (Button) this.findViewById(R.id.buttonFb);
		buttonLI= (Button) this.findViewById(R.id.buttonLI);
		buttonBlog = (Button) this.findViewById(R.id.buttonBlog);
		
		new AsyncHttpGet().execute(userid);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void actionTw(View view) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(tw));
		startActivity(i);
	}
	
	public void actionFb(View view) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(fb));
		startActivity(i);
	}
	
	public void actionLI(View view) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(li));
		startActivity(i);
	}
	
	public void actionBlog(View view) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(blog));
		startActivity(i);
	}
	
	
	
	public class AsyncHttpGet extends AsyncTask<String, Void, JSONObject[]> {

		/**
		 * constructor
		 */
		public AsyncHttpGet() {

		}

		/**
		 * background
		 */
		@Override
		protected JSONObject[] doInBackground(String... params) {
			JSONObject jsonObject = ApiCalls.GetUserInfo(params[0]);
			JSONObject jsonObject2 = ApiCalls.GetUserRolesInfo(params[0]);
			return new JSONObject[] {jsonObject,jsonObject2};
		}

		/**
		 * on getting result
		 */
		@Override
		protected void onPostExecute(JSONObject[] result) {
			
			JSONObject userInfo = result[0];
			
			TextView tvName =(TextView) UserActivity.this.findViewById(R.id.textViewUserName);
			TextView tvBio =(TextView) UserActivity.this.findViewById(R.id.textViewUserBio);
			
			
			
			try {
				Log.d("User", userInfo.toString(4));
				
				tvName.setText(userInfo.getString("name"));
				tvBio.setText(userInfo.getString("bio"));
				imageLoader.DisplayImage(userInfo.getString("image"), imageView);
					
				tw =userInfo.getString("twitter_url");
				fb =userInfo.getString("facebook_url");
				li =userInfo.getString("linkedin_url");
				blog =userInfo.getString("blog_url");
				
				if(tw.isEmpty())
				{
					buttonTw.setVisibility(View.GONE);
				}
				if(fb.isEmpty())
				{
					buttonFb.setVisibility(View.GONE);
				}
				if(li.isEmpty())
				{
					buttonLI.setVisibility(View.GONE);
				}
				if(blog.isEmpty())
				{
					buttonBlog.setVisibility(View.GONE);
				}
				
				
				String allLocations = "";
				JSONArray locations = userInfo.getJSONArray("locations");
				for (int i = 0; i < locations.length(); i++) {
					JSONObject element = locations.getJSONObject(i);
					allLocations = allLocations + element.getString("display_name") + " - ";
					
				}
				if(!allLocations.isEmpty())
				{
					allLocations = allLocations.substring(0, allLocations.length()-2);
				}
				
				textViewLocations.setText(allLocations);
				
				
				
				
				String allRoles = "";
				JSONArray roles = userInfo.getJSONArray("roles");
				for (int i = 0; i < roles.length(); i++) {
					JSONObject element = roles.getJSONObject(i);
					allRoles = allRoles + element.getString("display_name") + " - ";
					
				}
				if(!allRoles.isEmpty())
				{
					allRoles = allRoles.substring(0, allRoles.length()-2);
				}
				
				textViewRoles.setText(allRoles);
				
				
				JSONObject rolesInfo = result[1];
				
				Log.d("roles", rolesInfo.toString(4));
				
				JSONArray startupRoles = rolesInfo.getJSONArray("startup_roles");
				
				ListView listView = (ListView) findViewById(R.id.listViewRoles);
				listView.setAdapter(new UserRolesJSONAdapter(UserActivity.this, startupRoles));
				listView.setTextFilterEnabled(true);
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			mDialog.dismiss();
			

		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			mDialog = ProgressDialog.show(UserActivity.this, "", "Loading...", false);
			imageView.setImageResource(R.drawable.ic_launcher);
		}

		
	}
	
}
