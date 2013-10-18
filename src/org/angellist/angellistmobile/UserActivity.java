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

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class UserActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);

		//Uri data = getIntent().getData();
		String userid = getIntent().getDataString().substring(getIntent().getDataString().lastIndexOf("/")+1);
		
		
		new AsyncHttpGet().execute(userid);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public class AsyncHttpGet extends AsyncTask<String, Void, JSONObject> {

		/**
		 * constructor
		 */
		public AsyncHttpGet() {

		}

		/**
		 * background
		 */
		@Override
		protected JSONObject doInBackground(String... params) {
			JSONObject jsonObject = ApiCalls.GetUserInfo(params[0]);
			return jsonObject;
		}

		/**
		 * on getting result
		 */
		@Override
		protected void onPostExecute(JSONObject result) {
			
			TextView tvName =(TextView) UserActivity.this.findViewById(R.id.textViewUserName);
			TextView tvBio =(TextView) UserActivity.this.findViewById(R.id.textViewUserBio);
			
			try {
				tvName.setText(result.getString("name"));
				tvBio.setText(result.getString("bio"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}

	}
}
