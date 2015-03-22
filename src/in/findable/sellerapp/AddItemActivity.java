package in.findable.sellerapp;

import in.findable.sellerapp.test.MyAdapter;
import in.findable.sellerapp.utlis.Constant;
import in.findable.sellerapp.utlis.INetworkListener;
import in.findable.sellerapp.utlis.ItemTypeForAdd;
import in.findable.sellerapp.utlis.NetworkRequest;
import in.findable.sellerapp.utlis.ProductModel;
import in.findable.sellerapp.utlis.RowAdapter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class AddItemActivity extends AbstractActivity implements
		INetworkListener {
	String search = "";
	private ListView listView;
	private RowAdapter rowAdapter = null;
//	MyAdapter myAdapter;

	private ArrayList<ProductModel> arrayList = new ArrayList<>();

	EditText serachEditText;
	ImageView seachImageView;
	AlertDialog.Builder builder;
	AlertDialog dialog;

	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.additem);
		ItemTypeForAdd itemTypeForAdd = (ItemTypeForAdd) getIntent()
				.getSerializableExtra(Constant.KEY_ADD_BY);
		if (itemTypeForAdd == ItemTypeForAdd.ADD_BY_BRAND) {
			search = "brand_name";
		} else if (itemTypeForAdd == ItemTypeForAdd.ADD_BY_CATEGORY) {
			search = "category_name";

		} else if (itemTypeForAdd == ItemTypeForAdd.ADD_BY_PRODUCT) {
			search = "text";

		}
		Log.i("Anil", "search=" + search);
		serachEditText = (EditText) findViewById(R.id.edt_search_additem);
		seachImageView = (ImageView) findViewById(R.id.img_search_additem);
		listView = (ListView) findViewById(R.id.lv_additem);
		rowAdapter=new RowAdapter(AddItemActivity.this, arrayList,false);
		listView.setAdapter(rowAdapter);

		seachImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				if (isOnline()) {
					if (serachEditText.getText().toString().length() == 0) {
						displayMessage("Please enter search item");

					} else {
						search = search + ":"
								+ serachEditText.getText().toString();
						NetworkRequest shoplistRequest = new NetworkRequest(
								AddItemActivity.this, Constant
										.searchItem(search),
								AddItemActivity.this, NetworkRequest.GET, true);
						shoplistRequest.execute();
					}
				} else {
					displayMessage(R.string.internet_connection_not_available);
				}

			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				System.out.println("on list click"+position);
                         confirmationDialogToAdd(position);
			}
		});

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

	public void confirmationDialogToAdd(final int position) {

		builder = new AlertDialog.Builder(AddItemActivity.this);
		builder.setTitle("Do you want to add this item?");

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent = new Intent();
				intent.putExtra("ITEM_ADDED", arrayList.get(position));
//				intent.putExtra("ITEM_NAME", arrayList.get(position)
//						.getProductName());
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				dialog.dismiss();
			}
		});
		dialog = builder.create();
		dialog.show();
	}
}
