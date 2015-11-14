package com.vz.hackathon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "vzCom.db";
	
	public static final String TABLE_CUSTOMER_INFO = "notification";
	public static final String KEY_ROWID = "id";
	public static final String KEY_MSG_TECH = "msg_tech";
	public static final String KEY_CUSTOMER_NAME = "customer_name";
	public static final String KEY_CUSTOMER_LOC = "customer_loc";
	public static final String KEY_CUSTOMER_NUM = "customer_num";
	private SQLiteDatabase db1;
	
	private static final String TABLE_CUSTOMER_INFO_CREATE_SQL = 
			"create table " + TABLE_CUSTOMER_INFO 
			+ " (" + KEY_ROWID + " integer primary key autoincrement, "
			+ KEY_MSG_TECH + " text, "
			+ KEY_CUSTOMER_NAME + " text, "
			+ KEY_CUSTOMER_LOC + " text,"
			+ KEY_CUSTOMER_NUM + " text"
			+ ");";
	
	public static final String[] TABLE_CUSTOMER_INFO_ALL_KEYS = new String[] {	KEY_ROWID, 
		KEY_MSG_TECH, 
		KEY_CUSTOMER_NAME, 
		KEY_CUSTOMER_LOC, 
		KEY_CUSTOMER_NUM};
	
	public MyDBHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(TABLE_CUSTOMER_INFO_CREATE_SQL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
		 db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER_INFO_CREATE_SQL);
		    // Create tables again
	        onCreate(db);
	}
	
	public void insertCustomer(CustomerInfo customer) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(KEY_MSG_TECH, customer.getMsgTech());
	    values.put(KEY_CUSTOMER_NAME, customer.getCustomerName());
	    values.put(KEY_CUSTOMER_LOC, customer.getCustomerLoc());
	    values.put(KEY_CUSTOMER_NUM, customer.customerNum);
	 
	    // Inserting Row
	    db.insert(TABLE_CUSTOMER_INFO, null, values);
	    db.close(); // Closing database connection
		
	}
	
	private Cursor getAllRows(String tableName) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		String where = null;
		Cursor c = 	db.query(true, tableName, getKeyList(tableName), 
							where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	public String[] getKeyList(String tableName){
		if(tableName.equalsIgnoreCase(TABLE_CUSTOMER_INFO))
			return TABLE_CUSTOMER_INFO_ALL_KEYS;
		
		return null;
		
	}
	
	/*public CustomerInfo getAllNotification(){
		CustomerInfo ci = new CustomerInfo();
		Cursor c = getAllRows(TABLE_CUSTOMER_INFO);
		
		
		return ci;
	}*/
	
	public ArrayList<Map<String,Object>> getNotificationLogs(){
		Cursor c = getAllRows(TABLE_CUSTOMER_INFO);
		if(c != null){		
				if (c.moveToFirst()) {
					ArrayList<Map<String,Object>> logs = new ArrayList<Map<String,Object>>();
					do {
						Map<String,Object> logMap = new HashMap<String,Object>(); 
						logMap.put(KEY_MSG_TECH,c.getString(1));				
						logMap.put(KEY_CUSTOMER_NAME,c.getString(2));
						logMap.put(KEY_CUSTOMER_LOC,c.getString(3));
						logMap.put(KEY_CUSTOMER_NUM,c.getString(4));
						logs.add(logMap);
					}while (c.moveToNext());
					return logs;
				}			
				return null;
		}
		return null;		
	}

} 