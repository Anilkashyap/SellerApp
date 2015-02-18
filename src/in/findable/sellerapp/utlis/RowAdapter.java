package in.findable.sellerapp.utlis;

import in.findable.sellerapp.R;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RowAdapter extends BaseAdapter {

	Context context;

	ArrayList<ProductModel> eventsInfos;
	ControlHolder holder = null;

	public RowAdapter(Context context, ArrayList<ProductModel> arrayList) {
		this.context = context;
		eventsInfos = arrayList;

	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(context);
			row = vi.inflate(R.layout.row_for_add_content, null);
		}

		holder = new ControlHolder();
		holder.stockswitch = (Switch) row.findViewById(R.id.switch1);
		holder.titleTextView = (TextView) row.findViewById(R.id.txt_title);
		holder.attributeTextView = (TextView) row
				.findViewById(R.id.txt_attributes);
		holder.sellinPriceTextView = (TextView) row
				.findViewById(R.id.txt_slleing_price);
		holder.competitivePriceTextView = (TextView) row
				.findViewById(R.id.txt_competitive_price);
		holder.quantityTextView = (TextView) row
				.findViewById(R.id.txt_quantity);
		holder.skuTextView = (TextView) row.findViewById(R.id.txt_sku);
		holder.image = (ImageView) row.findViewById(R.id.imv_row_add_content);

		row.setTag(holder);

		String imageUrl = eventsInfos.get(position).getImageUrl();
		if (imageUrl != null) {
			Picasso.with(context).load(imageUrl).fit().into(holder.image);
		}
		if (!eventsInfos.get(position).getProductName().equals(""))
			holder.titleTextView.setText(eventsInfos.get(position)
					.getProductName());

		if (!eventsInfos.get(position).getAttribute().equals(""))
			holder.attributeTextView.setText(eventsInfos.get(position)
					.getAttribute());
		else
			holder.attributeTextView.setText("text attribute");
		if (!eventsInfos.get(position).getSeelingPrice().equals(""))
			holder.sellinPriceTextView.setText(eventsInfos.get(position)
					.getSeelingPrice());
		else
			holder.sellinPriceTextView.setText("499");
		if (!eventsInfos.get(position).getCompetitiivePrice().equals(""))
			holder.competitivePriceTextView.setText(eventsInfos.get(position)
					.getCompetitiivePrice());
		else
			holder.competitivePriceTextView.setText("599");
		if (!eventsInfos.get(position).getSku().equals(""))
			holder.skuTextView.setText(eventsInfos.get(position).getSku());
		else
			holder.skuTextView.setText("black -c33");
		if (!eventsInfos.get(position).getQuntity().equals(""))
			holder.quantityTextView.setText(eventsInfos.get(position)
					.getQuntity());
		else
			holder.quantityTextView.setText("1");

		return row;
	}

	public int getCount() {

		return eventsInfos.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	static class ControlHolder {
		TextView titleTextView;

		TextView attributeTextView;
		TextView skuTextView;
		TextView sellinPriceTextView;
		TextView competitivePriceTextView;
		TextView quantityTextView;
		Switch stockswitch;
		ImageView image;
	}

}
