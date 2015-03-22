package in.findable.sellerapp.utlis;

import in.findable.sellerapp.R;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

public class RowAdapter extends BaseAdapter {

	Context context;

	ArrayList<ProductModel> eventsInfos;
	ControlHolder holder = null;
	boolean isStockAvailable = false;
	int currentPostion = -1;
//	HashMap<Integer, Integer> hashMap = new HashMap<>();

	public RowAdapter(Context context, ArrayList<ProductModel> arrayList,
			boolean isStockAvailable) {
		this.context = context;
		eventsInfos = arrayList;
		this.isStockAvailable = isStockAvailable;
//		fillProductQuanity(arrayList);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(context);
			convertView = vi.inflate(R.layout.row_for_add_content, null);
			holder = new ControlHolder();

			holder.chckeBox = (CheckBox) convertView
					.findViewById(R.id.checkbox_stcok_available);

//			holder.chckeBox.setTag(holder.productModel);
//
//			holder.chckeBox.setChecked(eventsInfos.get(position)
//					.isStockAvaible());

			holder.titleTextView = (TextView) convertView.findViewById(R.id.txt_title);

			holder.sellinPriceTextView = (TextView) convertView
					.findViewById(R.id.txt_slleing_price);
			holder.quantityTextView = (TextView) convertView
					.findViewById(R.id.txt_quanity);
			holder.quantityTextView.setText("44");
			holder.skuTextView = (TextView) convertView.findViewById(R.id.txt_sku);
			holder.image = (ImageView) convertView
					.findViewById(R.id.imv_row_add_content);
			holder.incrButton = (Button) convertView.findViewById(R.id.incrementbtn);
			holder.decrButton = (Button) convertView.findViewById(R.id.decrementbtn);
			
			// holder.decrButton.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			//
			// // hashMap.put(currentPostion, hashMap.get(currentPostion) - 1);
			// // holder.quantityTextView.setText("3" +
			// (hashMap.get(currentPostion)));
			// holder.quantityTextView.setText(""
			// +(Integer.parseInt(eventsInfos.get(currentPostion).getQuntity())-1));
			//
			// }
			// });
			// holder.incrButton.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // hashMap.put(currentPostion, hashMap.get(currentPostion) + 1);
			// holder.quantityTextView.setText(""
			// +(Integer.parseInt(eventsInfos.get(currentPostion).getQuntity())+1));
			// }
			// });
			convertView.setTag(holder);
			convertView.setTag(R.id.checkbox_stcok_available, holder.chckeBox);
			// convertView.setTag(R.id.check, viewHolder.checkbox);
		} 
		else {
			holder = (ControlHolder) convertView.getTag();
		}
//		holder.productModel = eventsInfos.get(position);
//		currentPostion = position;
		// holder = new ControlHolder();
		holder.chckeBox.setChecked(eventsInfos.get(position).isStockAvaible());

//        if(eventsInfos.get(position).isStockAvaible())
//		holder.chckeBox.setChecked(true);
//        else
//        {
//        	holder.chckeBox.setChecked(false);
//        }

		// row.setTag(holder);
		if (isStockAvailable) {
			holder.chckeBox.setVisibility(Switch.VISIBLE);
		} else {
			holder.chckeBox.setVisibility(Switch.GONE);

		}
		String imageUrl = eventsInfos.get(position).getImageUrl();
		if (imageUrl != null) {
			Picasso.with(context).load(imageUrl).fit().into(holder.image);
		}
		if (!eventsInfos.get(position).getProductName().equals(""))
			holder.titleTextView.setText(eventsInfos.get(position)
					.getProductName());

		if (!eventsInfos.get(position).getSeelingPrice().equals(""))
			holder.sellinPriceTextView.setText(eventsInfos.get(position)
					.getSeelingPrice());
		else
			holder.sellinPriceTextView.setText("0");

		if (!eventsInfos.get(position).getSku().equals(""))
			holder.skuTextView.setText(eventsInfos.get(position).getSku());
		else
			holder.skuTextView.setText("black -c33");
		holder.quantityTextView.setText(eventsInfos.get(position).getQuntity());

		// if (!eventsInfos.get(position).getQuntity().equals(""))
		// holder.quantityTextView.setText(eventsInfos.get(position)
		// .getQuntity());
		// else
		// holder.quantityTextView.setText("1");
		  Log.i("Row Adapter", "position="+position);
		holder.chckeBox.setTag(position); // This line is important.
		convertView.setTag(holder);
		holder.chckeBox
		.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				
				int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
				  Log.i("Row Adapter", "position="+getPosition+" ischecked="+isChecked);
				eventsInfos.get(getPosition).setStockAvaible(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
//				if (isChecked) {
//					holder.toggleButton
//							.setBackgroundResource(R.drawable.on);
////					eventsInfos.get(currentPostion)
////							.setStockAvaible(isChecked);
//				} else {
//					holder.toggleButton
//							.setBackgroundResource(R.drawable.circular_background_increment);
////					eventsInfos.get(currentPostion)
////							.setStockAvaible(isChecked);
//
//				}
			}
		});
		return convertView;
	}

	public int getCount() {

		return eventsInfos.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	static class ControlHolder {

//		ProductModel productModel;
		TextView titleTextView;

		TextView skuTextView;
		TextView sellinPriceTextView;
		CheckBox chckeBox;
		ImageView image;
		Button incrButton;
		Button decrButton;
		TextView quantityTextView;

	}

//	@Override
//	public void notifyDataSetChanged() {
//		super.notifyDataSetChanged();
////		fillProductQuanity(eventsInfos);
//	}

//	private void fillProductQuanity(ArrayList<ProductModel> arrayList) {
//		for (int i = 0; i < arrayList.size(); i++) {
//			hashMap.put(i, Integer.parseInt(arrayList.get(i).getQuntity()));
//		}
//	}
}
