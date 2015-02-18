package in.findable.sellerapp.utlis;

public class ProductModel {
	private String attribute="";
	private String sku="";
	private String quntity="";
	private String seelingPrice="";
	private String competitiivePrice="";
	private String imageUrl="";
	private String productName="";
	private boolean isStockAvaible=false;
	
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
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
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
	public String getCompetitiivePrice() {
		return competitiivePrice;
	}
	public void setCompetitiivePrice(String competitiivePrice) {
		this.competitiivePrice = competitiivePrice;
	}



}
