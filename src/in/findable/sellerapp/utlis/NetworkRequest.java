package in.findable.sellerapp.utlis;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class NetworkRequest extends AsyncTask<Void, Void, Object> {
	private INetworkListener networkListener;
	private String url;
	private boolean isLoaderShown;
	private ProgressDialog _loader;
	private Context _context;
	static String response = null;
	public final static int GET = 1;
	public final static int POST = 2;
	List<NameValuePair> params;
	int method;
	private HttpEntity httpEntity;
	boolean isAmazonUploading = false;

	public NetworkRequest(Context context, String url,
			INetworkListener networkListener,
			int method, boolean isLoaderShown) {
		this.networkListener = networkListener;
		_context = context;
		this.url = url;
		this.method = method;
		this.isLoaderShown = isLoaderShown;
	}

	@Override
	protected void onPreExecute() {
		if (isLoaderShown) {
			_loader = loadingDialog("please wait..loading");
		}
		super.onPreExecute();
	}

	@Override
	protected Object doInBackground(Void... arg0) {
		return makeServiceCall(url, method, params, httpEntity);
	}

	@Override
	protected void onPostExecute(Object result) {
		if (isLoaderShown) {
			_loader.dismiss();
		}
		if (result != null) {
			if (result instanceof String) {
				String _data = (String) result;
				System.out.println(_data);

				networkListener.onSuccess(_data);

			} else {
				networkListener.onFailure("");
			}
		}
		super.onPostExecute(result);
	}

	public Object makeServiceCall(String url, int method) {
		return this.makeServiceCall(url, method, null, null);
	}

	public Object makeServiceCall(String url, int method,
			List<NameValuePair> params, HttpEntity filehttpEntity) {
		try {
			
				HttpParams httpParameters = new BasicHttpParams();
				// Set the timeout in milliseconds until a connection is
				// established.
				// The default value is zero, that means the timeout is not
				// used.
				int timeoutConnection = 5000;
				HttpConnectionParams.setConnectionTimeout(httpParameters,
						timeoutConnection);
				int timeoutSocket = 15000;
				HttpConnectionParams
						.setSoTimeout(httpParameters, timeoutSocket);
				DefaultHttpClient httpClient = new DefaultHttpClient(
						httpParameters);
				HttpEntity httpEntity = null;
				HttpResponse httpResponse = null;
				// Checking http request method type
				if (method == POST) {
					HttpPost httpPost = new HttpPost(url);
					// adding post params
					if (params != null) {
						httpPost.setEntity(new UrlEncodedFormEntity(params,
								"UTF8"));
					} else if (filehttpEntity != null) {
						httpPost.setEntity(filehttpEntity);
					}
					httpResponse = httpClient.execute(httpPost);
				} else if (method == GET) {
					// appending params to url
					if (params != null) {
						String paramString = URLEncodedUtils.format(params,
								"utf-8");
						url += "?" + paramString;
					}
					HttpGet httpGet = new HttpGet(url);

					httpResponse = httpClient.execute(httpGet);

				}
				httpEntity = httpResponse.getEntity();
				response = EntityUtils.toString(httpEntity);
				return response;
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "Error: " + e.getMessage();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return "Error: " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return "Error: " + e.getMessage();
		}

	}

	public List<NameValuePair> getNameValuePairsList(String[] key,
			String[] value) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (int i = 0; i < value.length; i++) {
			BasicNameValuePair basicNameValuePair = new BasicNameValuePair(
					key[i], value[i]);
			list.add(basicNameValuePair);
		}
		return list;
	}

	private ProgressDialog loadingDialog(String title) {
		ProgressDialog dialog = new ProgressDialog(_context);
		dialog.setTitle(title);
		dialog.setCancelable(false);
		dialog.show();
		return dialog;
	}
}
