package org.angellist.angellistmobile;

import org.json.JSONException;
import org.json.JSONObject;

public class Parser {

	//{"access_token":"xxxxxxxxxxxxxxxxxxxxxx","token_type":"bearer"}
	public static String parseAccessToken(String json)
	{
		try {
			JSONObject jObject = new JSONObject(json);
			String accessToken = jObject.getString("access_token");
			return accessToken;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
