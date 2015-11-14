package com.vz.hackathon;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Utils {
	static SharedPreferences prefs;
	
	private static final String PREF_ACTIVITY = "activityRunningState";
	
	
	public static void setActivityRunningState(Context context, boolean isActivityRunning)	{
		prefs = context.getSharedPreferences(MainActivity.class.getSimpleName(),
					Context.MODE_PRIVATE);
		  Editor editor = prefs.edit();
			editor.putBoolean(PREF_ACTIVITY, isActivityRunning);
			editor.commit();
		
	  }
	  
	  public static boolean getActivityRunningState(Context context)	{
		  prefs = context.getSharedPreferences("ActivityStatePref",
					Context.MODE_PRIVATE);
			return prefs.getBoolean(PREF_ACTIVITY, false);
	  }
}
