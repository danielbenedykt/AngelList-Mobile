package org.angellist.angellistmobile;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiCalls {
	private static String ActivityFeedUrl = "https://api.angel.co/1/feed?personalized=1";
	
	public static JSONArray GetActivityFeed()
	{
		String result = ApiCalls.GetData(ActivityFeedUrl);
		try {
			JSONObject jObject = new JSONObject(result);
			return jObject.getJSONArray("feed");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static String GetData(String url) {
		byte[] result = null;
        String str = "";
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        try {

            HttpResponse response = client.execute(get);
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpURLConnection.HTTP_OK){
                result = EntityUtils.toByteArray(response.getEntity());
                str = new String(result, "UTF-8");
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
        return str;
		
	}
	
	
}
