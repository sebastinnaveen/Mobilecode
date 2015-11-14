package com.vz.hackathon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.support.v7.app.ActionBarActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {
	SharedPreferences prefs;
	GoogleCloudMessaging gcm;
	Context context;
	String regId;
	EditText txtRegId;
	Button showRegID, myJobs;
	ListView notiList;
	MyDBHandler db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txtRegId = (EditText) findViewById(R.id.etregid);
		showRegID = (Button) findViewById(R.id.btnshow);
		myJobs = (Button) findViewById(R.id.btnmyjobs);
		notiList = (ListView) findViewById(R.id.notificationlist);
		
		context = getApplicationContext();
		regId = getRegistrationId(context);
		if(regId == ""){
			registerInBackground();
		}else{
			txtRegId.setText(regId);
		}
		
		showRegID.setOnClickListener(new View.OnClickListener() {

			   @Override
			   public void onClick(View view) {
				   if(txtRegId.getVisibility() == View.VISIBLE){
					   txtRegId.setVisibility(View.INVISIBLE);
					   notiList.setVisibility(View.VISIBLE);
				   } else {
					   txtRegId.setVisibility(View.VISIBLE);
					   notiList.setVisibility(View.INVISIBLE);
				   }
			   }
		});
		myJobs.setOnClickListener(new View.OnClickListener() {

			   @Override
			   public void onClick(View view) {
				   Intent intent = new Intent(MainActivity.this, MyJobs.class);
					startActivity(intent);
					overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
			   }
		});
		
		db = new MyDBHandler(getApplicationContext());
		ArrayList<Map<String,Object>> notilist = db.getNotificationLogs();
		
		if(notilist != null && notilist.size() > 0){
			NotificationListAdapter adapter = new NotificationListAdapter(this, 
					notilist,
					  R.layout.row_notification, 
					  new String[] { 
								MyDBHandler.KEY_CUSTOMER_NAME,
								MyDBHandler.KEY_CUSTOMER_LOC,
								MyDBHandler.KEY_MSG_TECH,
								MyDBHandler.KEY_CUSTOMER_NUM
								},														
					  new int[] { R.id.customername,R.id.cusloc,R.id.msgtech,R.id.customernum});  
			
			
			notiList.setAdapter(adapter);
		}
		
	}
	
	
	private void registerInBackground() {
		 new AsyncTask<Void, Void, String>() {
	            @Override
	            protected String doInBackground(Void... params) {
	                String msg = "";
	                try {
	                    if (gcm == null) {
	                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
	                    }
	                    regId = gcm.register("438358394799");
	                    msg = "" + regId;
	                    

	                } catch (IOException ex) {
	                    msg = "Error :" + ex.getMessage();

	                }
	                return msg;
	            }

	            @Override
	            protected void onPostExecute(String msg) {
	            	txtRegId.setText(msg);
	            }
	        }.execute(null, null, null);
	}
	
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		String registrationId = prefs.getString("regId", "");
		if (registrationId == "") {
			return "";
		}
		
		return registrationId;
	}
	
	private void storeRegistrationId(Context context, String regId) {
		prefs = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("regId", regId);
		editor.commit();
	}
	
	/*@Override
	protected void onResume() {
	      super.onResume();
	      Utils.setActivityRunningState(getApplicationContext(), true);
	    }

	    @Override
	    protected void onPause() {
	      super.onPause();
	      Utils.setActivityRunningState(getApplicationContext(), false);
	    }
	    
	    @Override
	    protected void onStart() {
	     // TODO Auto-generated method stub
	     
	          //Register BroadcastReceiver
	          //to receive event from our service
	          myReceiver = new MyReceiver();
	          IntentFilter intentFilter = new IntentFilter();
	          intentFilter.addAction(GcmMessageHandler.MY_ACTION);
	          registerReceiver(myReceiver, intentFilter);
	         
	         
	     
	     super.onStart();
	    }
	     
	    @Override
	    protected void onStop() {
	     // TODO Auto-generated method stub
	     unregisterReceiver(myReceiver);
	     super.onStop();
	    }
	    
		private class MyReceiver extends BroadcastReceiver {

			@Override
			public void onReceive(Context arg0, Intent arg1) {
				// TODO Auto-generated method stub

				Toast.makeText(getApplicationContext(), "you have received new job!!!", Toast.LENGTH_LONG).show();
				db = new MyDBHandler(getApplicationContext());
				ArrayList<Map<String,Object>> notilist = db.getNotificationLogs();
				
				
				NotificationListAdapter adapter = new NotificationListAdapter(getApplicationContext(), 
						notilist,
						  R.layout.row_notification, 
						  new String[] { 
									MyDBHandler.KEY_CUSTOMER_NAME,
									MyDBHandler.KEY_CUSTOMER_LOC,
									MyDBHandler.KEY_MSG_TECH,
									MyDBHandler.KEY_CUSTOMER_NUM
									},														
						  new int[] { R.id.customername,R.id.cusloc,R.id.msgtech,R.id.customernum});  
				
				
				notiList.setAdapter(adapter);
			}

		}*/
}
