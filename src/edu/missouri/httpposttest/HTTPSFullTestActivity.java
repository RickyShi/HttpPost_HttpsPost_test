package edu.missouri.httpposttest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HTTPSFullTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_https_full_test);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment())
					.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.httpsfull_test, menu);
		return true;
	}

	public void httpsPostFull(View view) {
		TestFileReader tfr = new TestFileReader();
		TransmitData transDataThread = new TransmitData();
		// TransmitData2 transDataThread = new TransmitData2();
		// transDataThread.execute(Utilities.SMALL_FILENAME,
		// tfr.rtnString(Utilities.SMALL_PATH));
		// transDataThread.execute(Utilities.LARGE_FILENAME,
		// tfr.rtnString(Utilities.LARGE_PATH));
		transDataThread.execute(Utilities.XL_FILENAME, tfr.rtnString(Utilities.XL_PATH));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_https_full_test, container, false);
			return rootView;
		}
	}

	private class TransmitData extends AsyncTask<String, Void, Long> {

		@Override
		protected void onPostExecute(Long result) {
			TextView tvHttpsFull = (TextView) findViewById(R.id.tvHttpsFull);
			tvHttpsFull.setText("Round Time: " + String.valueOf(result));
		}

		@Override
		protected Long doInBackground(String... strings) {

			String fileName = strings[0];
			String dataToSend = strings[1];

			DefaultHttpClient client = (DefaultHttpClient) WebClientDevWrapper.getSpecialKeyStoreClient(getApplicationContext());
			// DefaultHttpClient client = (DefaultHttpClient)
			// WebClientDevWrapper.getHttpsClient();

			HttpPost request = new HttpPost(Utilities.HTTPS_ADDRESS);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("file_name", fileName));
			params.add(new BasicNameValuePair("data", dataToSend));
			try {
				long t1 = Calendar.getInstance().getTimeInMillis();
				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				// HttpResponse response = new
				// DefaultHttpClient().execute(request);
				HttpResponse response = client.execute(request);
				// String result = EntityUtils.toString(response.getEntity());
				Log.d(Utilities.TAG_HTTPS_FULL, String.valueOf(response.getStatusLine().getStatusCode()));
				long t2 = Calendar.getInstance().getTimeInMillis();
				Log.d(Utilities.TAG_HTTPS_FULL, "Round Time: " + (t2 - t1));
				return t2 - t1;
			} catch (Exception e) {
				e.printStackTrace();
				return (long) -1;
			}
		}
	}

}
