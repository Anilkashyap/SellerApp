package in.findable.sellerapp.dao;

import in.findable.sellerapp.utlis.Constant;
import in.findable.sellerapp.utlis.ProductModel;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SellerDatabase extends SQLiteOpenHelper {
	ArrayList<ProductModel> arrayList = new ArrayList<ProductModel>();
	SQLiteDatabase sqLiteDatabase;
	static SellerDatabase helper = null;
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "seller_app";

	private static final String KEY_ID = "id";
	private static final String KEY_PRODUCT_NAME = "name";
	private static final String KEY_PRODUCT_PRICE = "price";
	private static final String KEY_PRODUCT_QUANTUTY = "quanity";
	private static final String KEY_IS_STOCK_AVAILABLE = "isavailable";
	private static final String KEY_TYPE = "add_update_del";

	private static final String KEY_PRODUCT_SKU = "sku";
	private static final String KEY_PRODUCT_IMEAGE_URL = "imageurl";


	private static final String TABLE_PRODUCT = "product";
	

	private static final String CREATE_EVENT_TABLE = "CREATE TABLE "
			+ TABLE_PRODUCT + " (" + KEY_ID
			+ " INTEGER  PRIMARY KEY AUTOINCREMENT, " + KEY_PRODUCT_NAME
			+ " TEXT NOT NULL," + KEY_PRODUCT_PRICE + " TEXT NOT NULL,"
			+ KEY_IS_STOCK_AVAILABLE + " INTEGER ," + KEY_PRODUCT_QUANTUTY + " INTEGER ,"
			 + KEY_PRODUCT_IMEAGE_URL + " TEXT , " + KEY_PRODUCT_SKU
			+ " TEXT ,"+ KEY_TYPE + " INTEGER );";
	

	public SellerDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		sqLiteDatabase = getWritableDatabase();
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_EVENT_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
	}

	

	
	//conflict when add and update same item after adding
	public long addItem(ProductModel productModel) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_PRODUCT_NAME, productModel.getProductName());
		contentValues.put(KEY_PRODUCT_PRICE, productModel.getSeelingPrice());
		contentValues.put(KEY_PRODUCT_QUANTUTY, productModel.getQuntity());
		contentValues.put(KEY_PRODUCT_SKU, productModel.getSku());
		contentValues.put(KEY_TYPE, Constant.ADD_ITEM);

		if(productModel.isStockAvaible())
		contentValues.put(KEY_IS_STOCK_AVAILABLE,1);
		else
			contentValues.put(KEY_IS_STOCK_AVAILABLE,0);

		return sqLiteDatabase.insert(TABLE_PRODUCT, null, contentValues);
	}

	public long updateItem(ProductModel productModel,int id,int ty) {
		ContentValues contentValues = new ContentValues();
//		contentValues.put(KEY_PRODUCT_NAME, productModel.getProductName());
		contentValues.put(KEY_PRODUCT_PRICE, productModel.getSeelingPrice());
		contentValues.put(KEY_PRODUCT_QUANTUTY, productModel.getQuntity());
//		contentValues.put(KEY_PRODUCT_SKU, productModel.getSku());
		contentValues.put(KEY_TYPE, Constant.UPDATE_ITEM);

		if(productModel.isStockAvaible())
		contentValues.put(KEY_IS_STOCK_AVAILABLE,1);
		else
			contentValues.put(KEY_IS_STOCK_AVAILABLE,0);

//		return sqLiteDatabase.update(table, values, whereClause, whereArgs)(TABLE_PRODUCT, null, contentValues);
		return sqLiteDatabase.update(TABLE_PRODUCT, contentValues, KEY_ID + "="
				+ id, null);
	}


	
	public void closeOpenDatabase() {
		sqLiteDatabase.close();
	}

	// ---deletes contacts--
	public int deleteContacts(long rowId) {
		int a = sqLiteDatabase
				.delete(TABLE_PRODUCT, KEY_ID + "=" + rowId, null);
		return a;
	}

	// ---deletes event--
	public int deleteEvent(long rowId) {
		int a = sqLiteDatabase.delete(TABLE_PRODUCT, KEY_ID + "=" + rowId, null);
		return a;
	}

//	// ---deletes event--
//	public int deleteFBEvent(long event_type) {
//		int a = sqLiteDatabase.delete(TABLE_PRODUCT, KEY_TYPE + "=" + event_type,
//				null);
//		return a;
//	}

	
	
	

//	public ArrayList<ProductModel> getEvents(String str)
//
//	{
//
//		String sql = "SELECT " + KEY_PRODUCT_NAME + "," + KEY_ID + " FROM "
//				+ TABLE_PRODUCT + " WHERE SUBSTR(" + KEY_PRODUCT_PRICE
//				+ ",6) like '" + str + "'";
//		Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
//		if (cursor.moveToFirst()) {
//			ProductModel info;
//			do {
//				info = new ProductModel();
//				info.event_name = cursor.getString(0);
//				info.row_id = cursor.getInt(1);
//				arrayList.add(info);
//			} while (cursor.moveToNext());
//
//			cursor.close();
//			cursor = null;
//		}
//		return arrayList;
//	}

	

//	public ArrayList<EventsInfo> getCustomEvents(int type) {
//		String curDate = null;
//		Calendar calendar = Calendar.getInstance();
//		curDate = calendar.get(Calendar.YEAR) + "-"
//				+ Constants.pad(calendar.get(Calendar.MONTH) + 1) + "-"
//				+ Constants.pad(calendar.get(Calendar.DAY_OF_MONTH));
//		String selection = KEY_TYPE + "=" + type + " AND " + KEY_PRODUCT_PRICE
//				+ " >=date('" + curDate + "')";
//		String sql = "SELECT " + KEY_PRODUCT_NAME + " ," + KEY_PRODUCT_PRICE + ","
//				+ KEY_IS_STOCK_AVAILABLE + ",SUBSTR(" + KEY_PRODUCT_PRICE + ",6,2),"
//				+ KEY_STATUS + "," + KEY_ON_OFF + "," + KEY_ID + ", "
//				+ KEY_PICTURE + " FROM " + TABLE_PRODUCT + " WHERE " + selection
//				+ " ORDER BY " + KEY_PRODUCT_PRICE;
//		Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
//		if (cursor.moveToFirst()) {
//			EventsInfo info;
//			do {
//				info = new EventsInfo();
//				info.event_name = cursor.getString(0);
//				info.event_date = cursor.getString(1);
//				info.date_when = cursor.getString(2);
//				String month_formater = cursor.getString(3);
//				info.event_date = info.event_date.substring(8)
//						+ "-"
//						+ Constants.getMonthInFormat(Integer
//								.parseInt(month_formater)) + "-"
//						+ info.event_date.substring(0, 4);
//				if (info.date_when != null)
//					info.date_when = info.date_when.substring(8)
//							+ "-"
//							+ Constants.getMonthInFormat(Integer
//									.parseInt(month_formater)) + "-"
//							+ info.date_when.substring(0, 4);
//				;
//				info.status = cursor.getInt(4);
//				info.on_off = cursor.getInt(5);
//				info.row_id = cursor.getInt(6);
//				info.picture = cursor.getString(7);
//
//				arrayList.add(info);
//			} while (cursor.moveToNext());
//
//		}
//		cursor.close();
//		cursor = null;
//
//		return arrayList;
//	}

	
	
	

		
}
