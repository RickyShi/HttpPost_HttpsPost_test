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

public class HTTPSTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_httpstest);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment())
					.commit();
		}
	}

	public void httpsPost(View view) {
		TestFileReader tfr = new TestFileReader();
		TransmitData transDataThread = new TransmitData();
		// TransmitData2 transDataThread = new TransmitData2();
		long t1 = Calendar.getInstance().getTimeInMillis();
		// transDataThread.execute(Utilities.SMALL_FILENAME,
		// tfr.rtnString(Utilities.SMALL_PATH));
		transDataThread.execute(Utilities.LARGE_FILENAME, tfr.rtnString(Utilities.LARGE_PATH));
		long t2 = Calendar.getInstance().getTimeInMillis();
		TextView tvHttps = (TextView) findViewById(R.id.tvHttps);
		tvHttps.setText(String.valueOf(t2 - t1));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.httpstest, menu);
		return true;
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
			View rootView = inflater.inflate(R.layout.fragment_httpstest, container, false);
			return rootView;
		}
	}

	private class TransmitData extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... strings) {

			String fileName = strings[0];
			String dataToSend = strings[1];

			DefaultHttpClient client = (DefaultHttpClient) WebClientDevWrapper.getNewHttpClient();

			HttpPost request = new HttpPost(Utilities.HTTPS_ADDRESS);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("file_name", fileName));
			params.add(new BasicNameValuePair("data", dataToSend));
			try {

				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				// HttpResponse response = new
				// DefaultHttpClient().execute(request);
				HttpResponse response = client.execute(request);
				// String result = EntityUtils.toString(response.getEntity());
				Log.d(Utilities.TAG_HTTPS,
						String.valueOf(response.getStatusLine().getStatusCode()));
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * 10-13 21:38:38.600: E/Your app(8333):
	 * javax.net.ssl.SSLHandshakeException:
	 * java.security.cert.CertPathValidatorException: Trust anchor for
	 * certification path not found.
	 */
	// private class TransmitData2 extends AsyncTask<String, Void, Boolean> {
	//
	// @Override
	// protected Boolean doInBackground(String... strings) {
	//
	// String fileName = strings[0];
	// String dataToSend = strings[1];
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	// params.add(new BasicNameValuePair("file_name", fileName));
	// params.add(new BasicNameValuePair("data", dataToSend));
	// try {
	// UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
	// URL url = new URL(Utilities.HTTPS_ADDRESS);
	// HttpURLConnection request = (HttpURLConnection) url.openConnection();
	//
	// request.setUseCaches(false);
	// request.setDoOutput(true);
	// request.setDoInput(true);
	//
	// request.setRequestMethod("POST");
	// OutputStream post = request.getOutputStream();
	// entity.writeTo(post);
	// post.flush();
	//
	// BufferedReader in = new BufferedReader(new
	// InputStreamReader(request.getInputStream()));
	// String inputLine, response = "";
	// while ((inputLine = in.readLine()) != null) {
	// response += inputLine;
	// }
	// post.close();
	// in.close();
	// } catch (Exception e) {
	// Log.e("Your app", "error", e);
	// }
	//
	// return true;
	// }
	// }

}