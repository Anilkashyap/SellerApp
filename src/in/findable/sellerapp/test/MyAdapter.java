package in.findable.sellerapp.test;

import in.findable.sellerapp.AddItemActivity;
import in.findable.sellerapp.R;
import in.findable.sellerapp.utlis.ProductModel;

import java.net.URI;
import java.util.List;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<ProductModel> {

	private final List<ProductModel> list;
	private final Activity context;
	boolean checkAll_flag = false;
	boolean checkItem_flag = false;
	ViewHolder viewHolder = null;
	boolean isStockAvailable = false;


	public MyAdapter(Activity context, List<ProductModel> list,boolean isStockAvailable) {
		super(context, R.layout.row, list);
		this.context = context;
		this.list = list;
		this.isStockAvailable = isStockAvailable;

	}

	static class ViewHolder {
		protected TextView text;
		protected CheckBox checkbox;
		protected ImageView image;
		protected TextView sellinPriceTextView;
		protected TextView quantityTextView;
		protected TextView skuTextView;
		protected Button incrButton;
		protected Button decrButton;
//		protected RelativeLayout relativeLayout;


	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			convertView = inflator.inflate(R.layout.row, null);
			viewHolder = new ViewHolder();
//			viewHolder.relativeLayout = (RelativeLayout) convertView
//					.findViewById(R.id.rel_parent_row);

			viewHolder.text = (TextView) convertView
					.findViewById(R.id.txt_title);
			viewHolder.sellinPriceTextView = (TextView) convertView
					.findViewById(R.id.txt_slleing_price);
			viewHolder.quantityTextView = (TextView) convertView
					.findViewById(R.id.txt_quanity);
			viewHolder.skuTextView = (TextView) convertView
					.findViewById(R.id.txt_sku);
			viewHolder.image = (ImageView) convertView
					.findViewById(R.id.imv_row_add_content);
			viewHolder.incrButton = (Button) convertView
					.findViewById(R.id.incrementbtn);
			viewHolder.decrButton = (Button) convertView
					.findViewById(R.id.decrementbtn);
			viewHolder.checkbox = (CheckBox) convertView
					.findViewById(R.id.check);
			viewHolder.checkbox
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							int getPosition = (Integer) buttonView.getTag(); // Here
																				// we
																				// get
																				// the
																				// position
																				// that
																				// we
																				// have
																				// set
																				// for
																				// the
																				// checkbox
																				// using
																				// setTag.
							list.get(getPosition).setStockAvaible(
									buttonView.isChecked()); // Set the value of
																// checkbox to
																// maintain its
																// state.
						}
					});
			viewHolder.decrButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					int getPosition = (Integer) v.getTag();
					// TextView textView=(TextView)v.getTag();
					if ((Integer.parseInt(list.get(getPosition).getQuntity()) > 0)) {
						list.get(getPosition)
								.setQuntity(
										""
												+ (Integer.parseInt(list.get(
														getPosition)
														.getQuntity()) - 1));
						// textView.setText(list.get(getPosition)
						// .getQuntity());
						notifyDataSetChanged();
					}

				}
			});
			viewHolder.incrButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int getPosition = (Integer) v.getTag();
					// TextView textView=(TextView)v.getTag();

					list.get(getPosition).setQuntity(
							""
									+ (Integer.parseInt(list.get(getPosition)
											.getQuntity()) + 1));
					// textView.setText(list.get(getPosition)
					// .getQuntity());
					// viewHolder.quantityTextView.setText(list.get(getPosition)
					// .getQuntity());
					notifyDataSetChanged();

				}
			});
//			viewHolder.relativeLayout.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					int getPosition = (Integer) v.getTag();
//					((AddItemActivity)context).confirmationDialogToAdd(getPosition);
//
//
//				}
//			});
			convertView.setTag(viewHolder);
			convertView.setTag(R.id.txt_title, viewHolder.text);
			convertView.setTag(R.id.check, viewHolder.checkbox);
			convertView.setTag(R.id.txt_quanity, viewHolder.quantityTextView);
			convertView.setTag(R.id.incrementbtn, viewHolder.incrButton);
			convertView.setTag(R.id.decrementbtn, viewHolder.decrButton);
//			convertView.setTag(R.id.rel_parent_row, viewHolder.relativeLayout);


		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.checkbox.setTag(position); // This line is important.
		viewHolder.incrButton.setTag(position);
		viewHolder.decrButton.setTag(position);
//		viewHolder.relativeLayout.setTag(position);

		viewHolder.quantityTextView.setTag(position);

		String imageUrl = list.get(position).getImageUrl();
		if (imageUrl != null) {
			Picasso.with(context).load(imageUrl).into(viewHolder.image);
		}
		// viewHolder.image.setImageURI(Uri.parse(imageUrl));
		if (!list.get(position).getSeelingPrice().equals(""))
			viewHolder.sellinPriceTextView.setText(list.get(position)
					.getSeelingPrice());
		else
			viewHolder.sellinPriceTextView.setText("0");

		if (!list.get(position).getSku().equals(""))
			viewHolder.skuTextView.setText(list.get(position).getSku());
		else
			viewHolder.skuTextView.setText("black -c33");
		viewHolder.text.setText(list.get(position).getProductName());
		viewHolder.checkbox.setChecked(list.get(position).isStockAvaible());
		viewHolder.quantityTextView.setText(list.get(position).getQuntity());
		if (isStockAvailable) {
			viewHolder.checkbox.setVisibility(Switch.VISIBLE);
		} else {
			viewHolder.checkbox.setVisibility(Switch.GONE);
			viewHolder.decrButton.setVisibility(View.GONE);
			viewHolder.incrButton.setVisibility(View.GONE);

		}

		Log.i("Row Adapter", "position=" + position);

		return convertView;
	}
}
