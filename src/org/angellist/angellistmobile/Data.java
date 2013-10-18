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

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Data {

	private static String AccessToken = "";

	public static String getAccessToken() {
		return AccessToken;
	}

	public static void setAccessToken(String accessToken) {
		Data.AccessToken = accessToken;
	}
	
	public static void save(Activity activity)
	{
		SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString("AccessToken", AccessToken);
		editor.commit();
	}
	
	public static void load(Activity activity)
	{
		SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
		//int defaultValue = getResources().getInteger(R.string.saved_high_score_default);
		AccessToken = sharedPref.getString("AccessToken", "");
	}
}
