package in.findable.sellerapp;

import in.findable.sellerapp.test.MyAdapter;
import in.findable.sellerapp.utlis.Constant;
import in.findable.sellerapp.utlis.INetworkListener;
import in.findable.sellerapp.utlis.ItemTypeForAdd;
import in.findable.sellerapp.utlis.NetworkRequest;
import in.findable.sellerapp.utlis.ProductModel;
import in.findable.sellerapp.utlis.RowAdapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class AllProductByShopID extends AbstractActivity implements
		INetworkListener {
	private ListView listView;
	private MenuItem storeMenuItem;
	private MenuItem addMenuItem;
	private MenuItem syncMenuItem;

	private MenuItem filterMenuItem;
	int REQUEST_CODE_ITEM_ADD = 0;

	private ActionBar actionBar;
//	private RowAdapter rowAdapter = null;
	MyAdapter myAdapter;
	private List<ProductModel> arrayList = new ArrayList<>();
	AlertDialog.Builder builder;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_product);
		actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		int shopid = getIntent().getIntExtra("SHOP_ID", 0);
		listView = (ListView) findViewById(R.id.lv_allproduct);
		myAdapter=new MyAdapter(AllProductByShopID.this, arrayList,true);
		listView.setAdapter(myAdapter);

		if (isOnline()) {
			NetworkRequest shoplistRequest = new NetworkRequest(
					AllProductByShopID.this,
					Constant.allproductByShopId(shopid), this,
					NetworkRequest.GET, true);
			shoplistRequest.execute();
		} else {
			displayMessage(R.string.internet_connection_not_available);
		}
//		listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View view,
//					int position, long id) {
//                         editProductDialog(position);
//			}
//		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		storeMenuItem = menu.findItem(R.id.action_all_store);
		storeMenuItem.setTitle("All products");
		addMenuItem = menu.findItem(R.id.action_add_product);
		addMenuItem.setVisible(true);
		syncMenuItem = menu.findItem(R.id.action_sync);
		syncMenuItem.setVisible(true);

		filterMenuItem = menu.findItem(R.id.action_filter);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add_product:
			// Toast.makeText(AllProductByShopID.this, "add product",
			// Toast.LENGTH_SHORT).show();
			dialogForAddItemtype();
			break;
		case R.id.action_sync:
		 syncItemOnServer();
			break;
		case R.id.action_filter:
			Toast.makeText(AllProductByShopID.this, "filter",
					Toast.LENGTH_SHORT).show();

		}

		return super.onOptionsItemSelected(item);
	}

	private void syncItemOnServer() {
		Toast.makeText(AllProductByShopID.this, "Syncing data on server completed",
				Toast.LENGTH_SHORT).show();
		
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
					String price = docsJsonObject.getString("price");
					productModel.setProductName(name);

					productModel.setSeelingPrice(price);
					productModel.setQuntity("1");
					arrayList.add(productModel);
				}
				myAdapter.notifyDataSetChanged();

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

	void dialogForAddItemtype() {
		final Dialog dialog = new Dialog(this);
		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setTitle("Select Your Choice");
		dialog.setContentView(R.layout.fav_option);
		Button addProductButton = (Button) dialog
				.findViewById(R.id.btn_add_by_product);
		Button addBrandButton = (Button) dialog
				.findViewById(R.id.btn_add_by_brand);
		Button addCategoryButton = (Button) dialog
				.findViewById(R.id.btn_add_by_category);
		dialog.show();
		addProductButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(AllProductByShopID.this,
						AddItemActivity.class);
				intent.putExtra(Constant.KEY_ADD_BY,
						ItemTypeForAdd.ADD_BY_PRODUCT);
				startActivityForResult(intent, REQUEST_CODE_ITEM_ADD);

				dialog.dismiss();

			}

		});

		addBrandButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent intent = new Intent(AllProductByShopID.this,
						AddItemActivity.class);
				intent.putExtra(Constant.KEY_ADD_BY,
						ItemTypeForAdd.ADD_BY_BRAND);
				startActivityForResult(intent, REQUEST_CODE_ITEM_ADD);

				dialog.dismiss();
			}
		});

		addCategoryButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(AllProductByShopID.this,
						AddItemActivity.class);
				intent.putExtra(Constant.KEY_ADD_BY,
						ItemTypeForAdd.ADD_BY_CATEGORY);
				startActivityForResult(intent, REQUEST_CODE_ITEM_ADD);

				dialog.dismiss();
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_ITEM_ADD && resultCode == RESULT_OK) {
			ProductModel productModel=data.getParcelableExtra("ITEM_ADDED");
			arrayList.add(productModel);
			myAdapter.notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
//	void editProductDialog(final int position) {
//
//		builder = new AlertDialog.Builder(AllProductByShopID.this);
//		builder.setTitle("Do you want to add this item?");
//		LayoutInflater vi;
//		vi = LayoutInflater.from(this);
//		View view = vi.inflate(R.layout.row_for_add_content, null);
//		builder.setView(view);
//		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface arg0, int arg1) {
//				Intent intent = new Intent();
//				intent.putExtra("ITEM_ADDED", arrayList.get(position));
////				intent.putExtra("ITEM_NAME", arrayList.get(position)
////						.getProductName());
//				setResult(Activity.RESULT_OK, intent);
//				finish();
//			}
//		});
//		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface arg0, int arg1) {
//				dialog.dismiss();
//			}
//		});
//		dialog = builder.create();
//		dialog.show();
//	}
}
