package org.angellist.angellistmobile;

import org.json.JSONArray;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class FeedActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed);
		
		new AsyncHttpGet().execute();
		//JSONArray jsonarray = ApiCalls.GetActivityFeed();
		/*ListView listView = (ListView) findViewById(R.id.listViewFeed);
		listView.setAdapter(new JSONAdapter(this, jsonarray));
		listView.setTextFilterEnabled(true);
*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	
	
	public class AsyncHttpGet extends AsyncTask<Void, Void, JSONArray> {
	    

	    /**
	     * constructor
	     */
	    public AsyncHttpGet() {
	        
	    }

	    /**
	     * background
	     */
	    @Override
	    protected JSONArray doInBackground(Void... params) {
	    	JSONArray jsonarray = ApiCalls.GetActivityFeed();
	        return jsonarray;
	    }

	    /**
	     * on getting result
	     */
	    @Override
	    protected void onPostExecute(JSONArray result) {
	        //access_token
	    	ListView listView = (ListView) findViewById(R.id.listViewFeed);
			listView.setAdapter(new JSONAdapter(FeedActivity.this, result));
			listView.setTextFilterEnabled(true);
	        
	    }

	}

}
