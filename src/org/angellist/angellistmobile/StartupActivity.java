package org.angellist.angellistmobile;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class StartupActivity  extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);

		//Uri data = getIntent().getData();
		String startupid = getIntent().getDataString().substring(getIntent().getDataString().lastIndexOf("/")+1);
		
		
		new AsyncHttpGet().execute(startupid);

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
			JSONObject jsonObject = ApiCalls.GetStartupInfo(params[0]);
			return jsonObject;
		}

		/**
		 * on getting result
		 */
		@Override
		protected void onPostExecute(JSONObject result) {
			
			TextView tvName =(TextView) StartupActivity.this.findViewById(R.id.textViewStartupName);
			TextView tvBio =(TextView) StartupActivity.this.findViewById(R.id.textViewStartupConcept);
			
			try {
				tvName.setText(result.getString("name"));
				tvBio.setText(result.getString("high_concept"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}

	}
}
