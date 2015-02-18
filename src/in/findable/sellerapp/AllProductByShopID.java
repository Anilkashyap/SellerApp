package in.findable.sellerapp;

import in.findable.sellerapp.utlis.Constant;
import in.findable.sellerapp.utlis.INetworkListener;
import in.findable.sellerapp.utlis.NetworkRequest;
import in.findable.sellerapp.utlis.ProductModel;
import in.findable.sellerapp.utlis.RowAdapter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class AllProductByShopID extends AbstractActivity implements
		INetworkListener {
	private ListView listView;
	private MenuItem storeMenuItem;
	private ActionBar actionBar;
	private RowAdapter rowAdapter = null;
	private ArrayList<ProductModel> arrayList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_product);
		actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		int shopid=getIntent().getIntExtra("SHOP_ID", 0);
		listView = (ListView) findViewById(R.id.lv_allproduct);
//      ProductModel model=new ProductModel();
//      model.setAttribute("testattribute");
//      model.setSku("black-44");
//      model.setSeelingPrice("556");
//      arrayList.add(model);
		rowAdapter = new RowAdapter(AllProductByShopID.this, arrayList);
		listView.setAdapter(rowAdapter);

		if (isOnline()) {
			NetworkRequest shoplistRequest = new NetworkRequest(
					AllProductByShopID.this, Constant.allproductByShopId(shopid), this,
					NetworkRequest.GET, true);
			shoplistRequest.execute();
		} else {
			displayMessage(R.string.internet_connection_not_available);
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		 storeMenuItem = menu.findItem(R.id.action_all_store);
		 storeMenuItem.setTitle("All products");
		return true;
	}

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
				ProductModel productModel = null;
				for (int i = 0; i < docsjsonArray.length(); i++) {
					productModel = new ProductModel();
					JSONObject docsJsonObject = docsjsonArray.getJSONObject(i);
					String image = docsJsonObject.getString("thumbnail");
					productModel.setImageUrl(image);
					String name = docsJsonObject.getString("name");
					productModel.setProductName(name);
					arrayList.add(productModel);
				}
				rowAdapter.notifyDataSetChanged();

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
