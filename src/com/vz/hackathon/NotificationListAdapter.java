package com.vz.hackathon;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class NotificationListAdapter extends SimpleAdapter {
	final Context appContext;
	final LayoutInflater inflater;
	private ArrayList<Map<String,Object>> data = null;
	
	public NotificationListAdapter(Context context, ArrayList<Map<String,Object>> _data, int _resource,
			String[] from, int[] to) {
		super(context, _data, _resource, from, to);
		data = _data;
		appContext = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	 @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
		 View row = convertView;
		    if(row == null)
		    	row = inflater.inflate(R.layout.row_notification, parent, false);
		    row = super.getView(position, row, parent);
		    
		    Map map = data.get(position);
		    
		   TextView customername = (TextView) row.findViewById(R.id.customername);
		   customername.setText(map.get(MyDBHandler.KEY_CUSTOMER_NAME).toString());
		   
		   TextView customerloc = (TextView) row.findViewById(R.id.cusloc);
		   customerloc.setText(map.get(MyDBHandler.KEY_CUSTOMER_LOC).toString());
		   
		   TextView msgTech = (TextView) row.findViewById(R.id.msgtech);
		   msgTech.setText(map.get(MyDBHandler.KEY_MSG_TECH).toString());
		   
		   TextView customernum = (TextView) row.findViewById(R.id.customernum);
		   customernum.setText(map.get(MyDBHandler.KEY_CUSTOMER_NUM).toString());
		   
		   final Button accept = (Button) row.findViewById(R.id.btn_approve);
		   accept.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(appContext, "You have acceptted this job!!!!", Toast.LENGTH_LONG).show();
					accept.setVisibility(View.GONE);
				}
		   });
		    
	        /*String mode = (String) map.get(DBAdapter.KEY_TRANSACTION_MODE);
	        ImageView modeIcon = (ImageView) row.findViewById(R.id.icon_transaction_mode);
	        System.out.println("MODE : "+mode + (String) map.get(DBAdapter.KEY_TRANSACTION_STATUS));
	        if(mode != null){		        
		        //int transModeIcon = (mode.equalsIgnoreCase("Mobile"))?R.drawable.icon_mobile:R.drawable.icon_card;
		        //modeIcon.setImageResource(transModeIcon);
		        //modeIcon.setBackgroundResource(transModeIcon);
	        	if(mode.equalsIgnoreCase("Mobile")){
	        		modeIcon.setImageResource(R.drawable.icon_mobile);
	        	}else{
	        		modeIcon.setImageResource(R.drawable.icon_card);
	        	}
	        }else{
	        	modeIcon.setVisibility(View.INVISIBLE);
	        }*/
	        return row;	   	       
	  }
}