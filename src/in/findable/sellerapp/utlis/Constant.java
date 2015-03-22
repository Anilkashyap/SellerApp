package in.findable.sellerapp.utlis;

public class Constant {
	public static String SOP_LIST_URL = "http://ec2-54-254-77-253.ap-southeast-1.compute.amazonaws.com:8983/solr/collection1/select?q=name:%22Mobile%22&wt=json&json.nl=flat&indent=true&d=5&rows=10&group=true&group.main=true&group.field=shop_id&fl=shop_id,shop_name,location_name";
    public static String ALL_PRODUCT_BY_SHOPID="http://ec2-54-254-77-253.ap-southeast-1.compute.amazonaws.com:8983/solr/collection1/select?q=shop_id:597&wt=json&json.nl=flat&indent=true&d=5&rows=10&fl=*";
    public static String SEARCH_ITEM="http://ec2-54-254-77-253.ap-southeast-1.compute.amazonaws.com:8983/solr/collection1/select?q=brand_name:bebe&wt=json&json.nl=flat&indent=true&fl=*";

	public static String SIGN_IN_BY_EMAIL="http://api3.findable.in/v1/users/login/";

    public static String allproductByShopId(int shopid)
    {
    	return "http://ec2-54-254-77-253.ap-southeast-1.compute.amazonaws.com:8983/solr/collection1/select?q=shop_id:"+shopid+"&wt=json&json.nl=flat&indent=true&d=5&rows=10&fl=*"+shopid+"&wt=json&json.nl=flat&indent=true&d=5&rows=10&fl=*";
    }
    
    public static String searchItem(String searchQuery)
    {
    	return "http://ec2-54-254-77-253.ap-southeast-1.compute.amazonaws.com:8983/solr/collection1/select?q="+searchQuery+"&wt=json&json.nl=flat&indent=true&fl=*";
    }
    public static String KEY_ADD_BY="add_by";
    public static int ADD_ITEM=1;
    public static int UPDATE_ITEM=2;
    public static int DELETE_ITEM=3;


    
    
}
