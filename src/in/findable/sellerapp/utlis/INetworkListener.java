package in.findable.sellerapp.utlis;

public interface INetworkListener {
	void onSuccess(String message);
	void onFailure(String errorMessage);

}
