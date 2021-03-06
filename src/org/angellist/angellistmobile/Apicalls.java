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

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author danielbenedykt
 *
 */
public class ApiCalls {
	//private static final String UserRolesUrl1 = null;
	private static final String UserRolesUrl2 = "/roles";
	private static String ActivityFeedUrl = "https://api.angel.co/1/feed?personalized=1";
	private static String UserUrl = "https://api.angel.co/1/users/";
	private static String StartupUrl = "https://api.angel.co/1/startups/";
	
	public static JSONArray GetActivityFeed()
	{
		String result = ApiCalls.GetData(ActivityFeedUrl + "&access_token=" + Data.getAccessToken());
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

	public static JSONObject GetUserInfo(String user) {
		String result = ApiCalls.GetData(UserUrl + user);
		try {
			JSONObject jObject = new JSONObject(result);
			return jObject;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONObject GetUserRolesInfo(String user) {
		String result = ApiCalls.GetData(UserUrl + user+ UserRolesUrl2);
		try {
			JSONObject jObject = new JSONObject(result);
			return jObject;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	 

	

	public static JSONObject GetStartupInfo(String startup) {
		String result = ApiCalls.GetData(StartupUrl + startup);
		try {
			JSONObject jObject = new JSONObject(result);
			return jObject;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
