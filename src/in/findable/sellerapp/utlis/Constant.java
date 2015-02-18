package in.findable.sellerapp.utlis;

public class Constant {
	public static String SOP_LIST_URL = "http://ec2-54-254-77-253.ap-southeast-1.compute.amazonaws.com:8983/solr/collection1/select?q=name:%22Mobile%22&wt=json&json.nl=flat&indent=true&d=5&rows=10&group=true&group.main=true&group.field=shop_id&fl=shop_id,shop_name,location_name";
    public static String ALL_PRODUCT_BY_SHOPID="http://ec2-54-254-77-253.ap-southeast-1.compute.amazonaws.com:8983/solr/collection1/select?q=shop_id:597&wt=json&json.nl=flat&indent=true&d=5&rows=10&group=true&group.main=true&group.field=shop_id&fl=*";
    public static String allproductByShopId(int shopid)
    {
    	return "http://ec2-54-254-77-253.ap-southeast-1.compute.amazonaws.com:8983/solr/collection1/select?q=shop_id:"+shopid+"&wt=json&json.nl=flat&indent=true&d=5&rows=10&group=true&group.main=true&group.field=shop_id&fl=*";
    }
}
