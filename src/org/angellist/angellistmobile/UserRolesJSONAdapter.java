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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.fedorvlasov.lazylist.ImageLoader;

public class UserRolesJSONAdapter extends BaseAdapter implements ListAdapter {

	private final Activity activity;
	private final JSONArray jsonArray;
	public ImageLoader imageLoader;

	UserRolesJSONAdapter(Activity activity, JSONArray jsonArray) {
		assert activity != null;
		assert jsonArray != null;

		this.jsonArray = jsonArray;
		this.activity = activity;
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	@Override
	public int getCount() {

		return jsonArray.length();
	}

	@Override
	public JSONObject getItem(int position) {

		return jsonArray.optJSONObject(position);
	}

	@Override
	public long getItemId(int position) {
		JSONObject jsonObject = getItem(position);

		return jsonObject.optLong("id");
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = activity.getLayoutInflater().inflate(
					R.layout.row_userrole, null);

		TextView textView = (TextView) convertView.findViewById(R.id.label);
		textView.setMovementMethod(LinkMovementMethod.getInstance());
		ImageView imageView = (ImageView) convertView.findViewById(R.id.logo);
		JSONObject jsonObject = getItem(position);

		try {

			textView.setText("-");
			imageView.setImageResource(R.drawable.ic_launcher);
			String image = jsonObject.getJSONObject("startup").getString("logo_url");
			imageLoader.DisplayImage(image, imageView);
			
			String role = jsonObject.getString("role");
			String name = jsonObject.getJSONObject("startup").getString("name");
			
			textView.setText(name + " - " + role);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return convertView;
	}
	
	
	public String replaceUserAndStartupLinks(String descriptionHtml)
	{
		// get the description and remove all tags
		
		String description = Html.fromHtml(descriptionHtml).toString();

		// for each link, replace it with a link
		Document doc = Jsoup.parse(descriptionHtml);
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			if ("User".equals(link.attr("data-type"))) {
				description = description.replace(
						link.text(),
						"<a href=\"org.angellist.angellistmobile.user://"
								+ link.attr("data-id") + "\">"
								+ link.text() + "</a>");
			} else if ("Startup".equals(link.attr("data-type"))) {
				description = description.replace(
						link.text(),
						"<a href=\"org.angellist.angellistmobile.startup://"
								+ link.attr("data-id") + "\">"
								+ link.text() + "</a>");
			}
		}
		
		return description;
	}
}
