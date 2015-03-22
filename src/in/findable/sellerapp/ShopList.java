package in.findable.sellerapp;

import in.findable.sellerapp.utlis.Constant;
import in.findable.sellerapp.utlis.INetworkListener;
import in.findable.sellerapp.utlis.NetworkRequest;
import in.findable.sellerapp.utlis.Util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShopList extends AbstractActivity implements INetworkListener {
	ListView listView;
	ArrayAdapter<String> arrayAdapter;
	ArrayList<String> arrayList = new ArrayList<>();
	MenuItem storeMenuItem;
	private ActionBar actionBar;
	int shopid[];
	private static String TAG=ShopList.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shoplist);
		actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		Log.i(TAG, Util.getUserName(ShopList.this));
		// actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_background));
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		listView = (ListView) findViewById(R.id.lv_shoplist);
		arrayAdapter = new ArrayAdapter<String>(ShopList.this,
				android.R.layout.simple_list_item_1, arrayList);
		listView.setAdapter(arrayAdapter);
		if (isOnline()) {
			NetworkRequest shoplistRequest = new NetworkRequest(ShopList.this,
					Constant.SOP_LIST_URL, this, NetworkRequest.GET, true);
			shoplistRequest.execute();
		} else {
			displayMessage(R.string.internet_connection_not_available);
		}

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				Intent intent = new Intent(ShopList.this,
						AllProductByShopID.class);
				intent.putExtra("SHOP_ID", shopid[position]);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		storeMenuItem = menu.findItem(R.id.action_all_store);
		return true;
	}

	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle action bar item clicks here. The action bar will
	// // automatically handle clicks on the Home/Up button, so long
	// // as you specify a parent activity in AndroidManifest.xml.
	// int id = item.getItemId();
	// if (id == R.id.action_settings) {
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	// }

	@Override
	public void onSuccess(String message) {
		try {
			JSONObject _response = new JSONObject(message);
			int success = _response.getJSONObject("responseHeader").getInt(
					"status");
			if (success == 0) {
				JSONObject responseJsonObject = _response
						.getJSONObject("response");
				JSONArray docsjsonArray = responseJsonObject
						.getJSONArray("docs");
				String prepareText = "";
				shopid = new int[docsjsonArray.length()];

				for (int i = 0; i < docsjsonArray.length(); i++) {
					JSONObject docsJsonObject = docsjsonArray.getJSONObject(i);
					String shopName = docsJsonObject.getString("shop_name");
					String shopLocation = docsJsonObject
							.getString("location_name");
					shopid[i] = docsJsonObject.getInt("shop_id");
					prepareText = shopName + " - " + shopLocation;
					arrayList.add(prepareText);
				}
				arrayAdapter.notifyDataSetChanged();
				storeMenuItem.setTitle("All Store(" + arrayList.size() + ")");

			} else {
				// displayMessage(_response.getString("message"));
			}
		} catch (Exception e) {
			Log.d("Organisation And Grouop",
					"error during organisation parsing" + e.getMessage());
		}

	}

	@Override
	public void onFailure(String errorMessage) {
		System.out.println("error occured during class" + errorMessage);
	}

}
