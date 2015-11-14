package com.vz.hackathon;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class GcmMessageHandler extends IntentService {

	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	public final static String MY_ACTION = "MY_ACTION";
	String msg;

	public GcmMessageHandler() {
		super("GcmIntentService");
	}

	public static final String TAG = "GCMNotificationIntentService";

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);

		
	    msg = extras.getString("msgTech");
		CustomerInfo ci = getCustomerInfoModel(extras);
		MyDBHandler db = new MyDBHandler(getApplicationContext());
		db.insertCustomer(ci);
		/*if(Utils.getActivityRunningState(getApplicationContext())){
				Intent in = new Intent();
				in.setAction(MY_ACTION);
				sendBroadcast(in);
			
    	}else{*/
				sendNotification();
			//}
    	
	    GcmBroadcastReceiver.completeWakefulIntent(intent);
	}
	
	private CustomerInfo getCustomerInfoModel(Bundle extras){
		CustomerInfo ci = new CustomerInfo();
		ci.setCustomerLoc(extras.getString("customerLocation"));
		ci.setCustomerName(extras.getString("customerName"));
		ci.setCustomerNum(extras.getString("customerNumber"));
		ci.setMsgTech(extras.getString("msgTech"));
		
		return ci;
	}

	
	
	private void sendNotification() {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
        .setContentTitle("VZCommunicator")
        .setSmallIcon(R.drawable.ic_launcher)
        .setSound(soundUri)
        .setAutoCancel(true)
        .setContentText(msg);
        

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
