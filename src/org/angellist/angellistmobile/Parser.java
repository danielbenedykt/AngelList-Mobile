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
