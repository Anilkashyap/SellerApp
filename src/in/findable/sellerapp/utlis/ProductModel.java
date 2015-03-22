package in.findable.sellerapp.utlis;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductModel implements Parcelable {
//	private String attribute = "";
	private String sku = "";
	private String quntity = "";
	private String seelingPrice = "";
//	private String competitiivePrice = "";
	private String imageUrl = "";
	private String productName = "";
	private boolean isStockAvaible = false;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isStockAvaible() {
		return isStockAvaible;
	}

	public void setStockAvaible(boolean isStockAvaible) {
		this.isStockAvaible = isStockAvaible;
	}

//	public String getAttribute() {
//		return attribute;
//	}
//
//	public void setAttribute(String attribute) {
//		this.attribute = attribute;
//	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getQuntity() {
		return quntity;
	}

	public void setQuntity(String quntity) {
		this.quntity = quntity;
	}

	public String getSeelingPrice() {
		return seelingPrice;
	}

	public void setSeelingPrice(String seelingPrice) {
		this.seelingPrice = seelingPrice;
	}

//	public String getCompetitiivePrice() {
//		return competitiivePrice;
//	}
//
//	public void setCompetitiivePrice(String competitiivePrice) {
//		this.competitiivePrice = competitiivePrice;
//	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int arg1) {
		parcel.writeString(productName);
		parcel.writeString(imageUrl);
//		parcel.writeString(attribute);
		parcel.writeString(sku);
		parcel.writeString(seelingPrice);
		parcel.writeString(quntity);



	}

	public static final Parcelable.Creator<ProductModel> CREATOR = new Parcelable.Creator<ProductModel>() {
		public ProductModel createFromParcel(Parcel in) {
			return new ProductModel(in);
		}

		public ProductModel[] newArray(int size) {
			return new ProductModel[size];
		}
	};

	public ProductModel(Parcel in) {
		productName = in.readString();
		imageUrl = in.readString();
//		attribute = in.readString();
		sku = in.readString();
		seelingPrice = in.readString();
		quntity = in.readString();



	}

	public ProductModel() {

	}

}
