package org.angellist.angellistmobile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fedorvlasov.lazylist.ImageLoader;

import android.app.Activity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class JSONAdapter extends BaseAdapter implements ListAdapter {

    private final Activity activity;
    private final JSONArray jsonArray;
    public ImageLoader imageLoader;
    JSONAdapter(Activity activity, JSONArray jsonArray) {
        assert activity != null;
        assert jsonArray != null;

        this.jsonArray = jsonArray;
        this.activity = activity;
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }


    @Override public int getCount() {

        return jsonArray.length();
    }

    @Override public JSONObject getItem(int position) {

        return jsonArray.optJSONObject(position);
    }

    @Override public long getItemId(int position) {
        JSONObject jsonObject = getItem(position);

        return jsonObject.optLong("id");
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = activity.getLayoutInflater().inflate(R.layout.row_feed, null);

        	TextView textView = (TextView) convertView.findViewById(R.id.label);
        	ImageView imageView = (ImageView) convertView.findViewById(R.id.logo);
        	JSONObject jsonObject = getItem(position);  
        	
        	try {
        		
        		String type = null;
			
				type = jsonObject.getJSONObject("item").getString("type");
			
				textView.setText("-");
				imageView.setImageResource(R.drawable.ic_launcher);
				Log.d(type,jsonObject.toString());
	        	if("Follow".equals(type))
	        	{
	        		//follow  {"timestamp":"2013-10-17T15:16:46Z","id":"UyroZ","text":null,"promoted_by":[],"extra":null,"description":"<a href=\"https:\/\/angel.co\/sbergel\" class=\"feed_link\" data-id=\"106927\" data-type=\"User\">Shanti Bergel<\/a> followed <a href=\"https:\/\/angel.co\/softbank-capital\" class=\"feed_link\" data-id=\"79702\" data-type=\"Startup\">SoftBank Capital<\/a>","likes":0,"item":{"type":"Follow","ids":[15308380]},"target":{"id":79702,"image":"https:\/\/s3.amazonaws.com\/photos.angel.co\/startups\/i\/79702-06c0b2126476861a1ae5cfb92da2a1aa-thumb_jpg.jpg?buster=1333126870","system_user?":null,"type":"Startup","angellist_url":"https:\/\/angel.co\/softbank-capital","tagline":"Multi-stage venture fund w\/ $600mm under management delivering strategic value to founders","name":"SoftBank Capital"},"actor":{"id":106927,"image":"https:\/\/s3.amazonaws.com\/photos.angel.co\/users\/106927-medium_jpg?1331913511","system_user?":false,"type":"User","angellist_url":"https:\/\/angel.co\/sbergel","tagline":"Business & Corporate Development Black Belt ¥ Founder @qbiquity ¥ Exec roles at @playfish, @gree","name":"Shanti Bergel"},"comments":0}
	            	
	        		//String actor = jsonObject.getJSONObject("actor").getString("name");
	        		//String target = jsonObject.getJSONObject("target").getString("name");
	        		String image = jsonObject.getJSONObject("actor").getString("image");
	        		imageLoader.DisplayImage(image, imageView);
	        		String description = jsonObject.getString("description");
	        		
	        		
	        		/*Pattern p1 = Pattern.compile("data-id=\\\"(\\d+)\\\"");
	        		Matcher m1 = p1.matcher(description);
	        		m1.find();
	        		String idOne = m1.group(1);
	        		m1.find();
	        		String idTwo = m1.group(1);
	        		//data-type=\\"(\w+)\\"
	        		Pattern p3 = Pattern.compile(">([^<]+)<\\/a>");
	        		Matcher m3 = p3.matcher(description);
	        		m3.find();
	        		String nameOne = m3.group(1);
	        		m3.find();
	        		String nameTwo = m3.group(1);
	        		*/
	        		
	        		description = Html.fromHtml(description).toString();
	        		//description = nameOne + " followed " + nameTwo;	        		
	        		textView.setText(description);
	        		
	        	}
	        	else if("Press".equals(type))
	        	{
	        		//press {"timestamp":"2013-10-17T15:35:03Z","id":"Uyshy","text":"<a href=\"http:\/\/philadelphia.cbslocal.com\/2013\/10\/14\/boppl-app\/\" target=\"_blank\">CBS Philadelphia features Boppl<\/a>","promoted_by":[],"extra":null,"description":"<a href=\"https:\/\/angel.co\/boppl\" class=\"feed_link\" data-id=\"154252\" data-type=\"Startup\">Boppl<\/a> added press from philadelphia.cbslocal.com","likes":0,"item":{"type":"Press","ids":[85539]},"target":null,"actor":{"id":154252,"image":"https:\/\/s3.amazonaws.com\/photos.angel.co\/startups\/i\/154252-6e332013f324434792202151e21d8435-thumb_jpg.jpg?buster=1375709358","system_user?":null,"type":"Startup","angellist_url":"https:\/\/angel.co\/boppl","tagline":"Order and pay for food and drinks","name":"Boppl"},"comments":0}
	        		String description = jsonObject.getString("description");
	        		description = Html.fromHtml(description).toString();
	        		textView.setText(description);
	        	}
	        	else if("StartupRole".equals(type))
	        	{
	        		//startuprole {"timestamp":"2013-10-17T15:30:29Z","id":"Uys8P","text":null,"promoted_by":[],"extra":{"comment":null},"description":"<a href=\"https:\/\/angel.co\/jim-huston\" class=\"feed_link\" data-id=\"104534\" data-type=\"User\">Jim Huston<\/a> invested in <a href=\"https:\/\/angel.co\/onthego-platforms\" class=\"feed_link\" data-id=\"123266\" data-type=\"Startup\">OnTheGo Platforms<\/a>'s previous round","likes":0,"item":{"type":"StartupRole","ids":[902050]},"target":{"id":123266,"image":"https:\/\/s3.amazonaws.com\/photos.angel.co\/startups\/i\/123266-001115ef9a121690432a5a43ae7515c5-thumb_jpg.jpg?buster=1364053321","system_user?":null,"type":"Startup","angellist_url":"https:\/\/angel.co\/onthego-platforms","tagline":"Our software makes smart glasses easier to use and the applications on them more powerful.","name":"OnTheGo Platforms"},"actor":{"id":104534,"image":"https:\/\/s3.amazonaws.com\/photos.angel.co\/users\/104534-medium_jpg?1331270245","system_user?":false,"type":"User","angellist_url":"https:\/\/angel.co\/jim-huston","tagline":"Early Stage Seed Investor at Portland Seed Fund","name":"Jim Huston"},"comments":0}
	        		String description = jsonObject.getString("description");
	        		
	        		/*Pattern p1 = Pattern.compile("data-id=\\\"(\\d+)\\\"");
	        		Matcher m1 = p1.matcher(description);
	        		m1.find();
	        		String idOne = m1.group(1);
	        		m1.find();
	        		String idTwo = m1.group(1);
	        		//data-type=\\"(\w+)\\"
	        		Pattern p3 = Pattern.compile(">([^<]+)<\\/a>");
	        		Matcher m3 = p3.matcher(description);
	        		m3.find();
	        		String nameOne = m3.group(1);
	        		m3.find();
	        		String nameTwo = m3.group(1);
	        		*/
	        		
	        		description = Html.fromHtml(description).toString();
	        		textView.setText(description);
	        	}
	        	else if("StatusUpdate".equals(type))
	        	{
	        		//statusupdate {"timestamp":"2013-10-17T15:11:37Z","id":"UyriB","text":"Haxlr8r Demo Day (III) is coming up on 21 November 2013 in San Fran... Contact Cyril or me to get on the RSVP list... Flying robots, Batman! See some of our breakout companies here: http:\/\/kickstarter\/haxlr8r","promoted_by":[],"extra":null,"description":"<a href=\"https:\/\/angel.co\/sosventures\" class=\"feed_link\" data-id=\"33924\" data-type=\"User\">Sean O&#39;Sullivan<\/a> updated their status","likes":0,"item":{"type":"StatusUpdate","ids":[112276]},"target":null,"actor":{"id":33924,"image":"https:\/\/s3.amazonaws.com\/photos.angel.co\/users\/33924-medium_jpg?1305992664","system_user?":false,"type":"User","angellist_url":"https:\/\/angel.co\/sosventures","tagline":"founder of @carma-3, JumpStart Int'l, @mapinfo; co-inventor of street mapping on PCs. co-coined term \"cloud computing\" in 1996. seed & venture capital investor.","name":"Sean O'Sullivan"},"comments":0}

	        		String description = jsonObject.getString("description");
	        		description = Html.fromHtml(description).toString();
	        		String status = jsonObject.getString("text");
	        		textView.setText(description + " - " + status);
	        	}
	        	else if("Reservation".equals(type))
	        	{
	        		String description = jsonObject.getString("description");
	        		description = Html.fromHtml(description).toString();
	        		textView.setText(description);
	        	}
	        	else if("Review".equals(type))
	        	{
	        		String description = jsonObject.getString("description");
	        		description = Html.fromHtml(description).toString();
	        		textView.setText(description);
	        	}
	        	else if("JobListing".equals(type))
	        	{
	        		String description = jsonObject.getString("description");
	        		description = Html.fromHtml(description).toString();
	        		textView.setText(description);
	        	}
	        	else
	        	{
	        		textView.setText(type);
	        		
	        	}
        	
        	} catch (JSONException e) {
				e.printStackTrace();
			}
        	
        return convertView;
    }
}
