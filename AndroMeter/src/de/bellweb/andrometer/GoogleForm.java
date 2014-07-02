package de.bellweb.andrometer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

public class GoogleForm extends AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... params) {

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(
				"https://spreadsheets.google.com/spreadsheet/formResponse?hl=de_DE&formkey=dGZOODBIdmJZOWJxWGhvVUloWVNEUVE6MQ");

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			for (int i = 0; i < params.length; i++) {
				nameValuePairs.add(new BasicNameValuePair("entry." + i
						+ ".single", params[i]));
			}
			nameValuePairs.add(new BasicNameValuePair("submit", "Submit"));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			client.execute(post);
		} catch (ClientProtocolException e) {
			Log.e("sendGoolge", e.toString());
		} catch (IOException e) {
			Log.e("sendGoolge", e.toString());

		}
		return null;
	}
}
