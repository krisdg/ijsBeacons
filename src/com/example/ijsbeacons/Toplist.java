package com.example.ijsbeacons;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.example.ijsbeacons.SOAP.*;

public class Toplist extends Fragment implements OnClickListener
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_toplist, container, false);

		try {
			SoapRequest_getToplist getToplist = new SoapRequest_getToplist(getArguments().getString("type"));
			getToplist.userAndroidId = ((IJsBeaconsApplication) getActivity().getApplication()).getUserAndroidId();

			SoapResult_getToplist result = (SoapResult_getToplist) new SendSoapRequest().execute(getToplist).get();
			
			Toast.makeText(getActivity(), result.resultMessage, Toast.LENGTH_SHORT).show();
			
			
			//Set title
	        TableLayout table = (TableLayout) v.findViewById(R.id.TopListTable);
	        TableRow.LayoutParams params_title = new TableRow.LayoutParams();
	        params_title.span = 3;

	        TableRow row_day = new TableRow(this.getActivity());

	        TextView title_day = new TextView(this.getActivity());       
	        title_day.setText(getArguments().getString("msg") + " per dag");
	        title_day.setTextSize(26);
	        title_day.setPadding(50, 50, 0, 0);

	        row_day.addView(title_day, params_title);

	        table.addView(row_day,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			if (result.resultCode == 1)
			{
				int counter = 0;

		        for (String user : result.userDay) {
		        	if (user != null) {
		        		TableRow row = new TableRow(this.getActivity());

				        TextView txt_index = new TextView(this.getActivity());       
				        txt_index.setText(String.valueOf(counter + 1));
				        txt_index.setTextSize(20);
				        txt_index.setGravity(Gravity.LEFT);
				        txt_index.setPadding(50, 20, 0, 0);
				        
				        TextView txt_user = new TextView(this.getActivity());       
				        txt_user.setText(user);
				        txt_user.setTextSize(20);
				        txt_user.setPadding(30, 0, 0, 0);
				        txt_user.setGravity(Gravity.LEFT);

				        String unit = getArguments().getString("units");
				        if (unit.equals("") == false) {
				        	unit = " " + unit;
				        }
				        
				        TextView txt_score = new TextView(this.getActivity());
				        txt_score.setText(result.dayStatistics[counter] + unit);
				        txt_score.setTextSize(20);
				        txt_score.setPadding(0, 0, 50, 0);
				        txt_score.setGravity(Gravity.RIGHT);

				        row.addView(txt_index);
				        row.addView(txt_user);
				        row.addView(txt_score);

				        table.addView(row,new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
		        	
				        counter++;
		        	}
		        }
			}
			
			//Set title MONTH
	        TableRow row_month = new TableRow(this.getActivity());
	        
	        TextView title_month = new TextView(this.getActivity());       
	        title_month.setText(getArguments().getString("msg") + " per maand");
	        title_month.setTextSize(26);
	        title_month.setPadding(50, 50, 0, 0);

	        row_month.addView(title_month, params_title);

	        table.addView(row_month,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	        
	        if (result.resultCode == 1)
			{
				int counter = 0;

		        for (String user : result.userMonth) {
		        	if (user != null) {
		        		TableRow row = new TableRow(this.getActivity());

				        TextView txt_index = new TextView(this.getActivity());       
				        txt_index.setText(String.valueOf(counter + 1));
				        txt_index.setTextSize(20);
				        txt_index.setGravity(Gravity.LEFT);
				        txt_index.setPadding(50, 20, 0, 0);
				        
				        TextView txt_user = new TextView(this.getActivity());       
				        txt_user.setText(user);
				        txt_user.setTextSize(20);
				        txt_user.setPadding(30, 0, 0, 0);
				        txt_user.setGravity(Gravity.LEFT);

				        String unit = getArguments().getString("units");
				        if (unit.equals("") == false) {
				        	unit = " " + unit;
				        }
				        
				        TextView txt_score = new TextView(this.getActivity());
				        txt_score.setText(result.monthStatistics[counter] + unit);
				        txt_score.setTextSize(20);
				        txt_score.setPadding(0, 0, 50, 0);
				        txt_score.setGravity(Gravity.RIGHT);
		        		
				        row.addView(txt_index);
				        row.addView(txt_user);
				        row.addView(txt_score);

				        table.addView(row,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		        	
				        counter++;
		        	}
		        }
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return v;
	}
	

	public static Fragment newInstance(String text, String type, String units) {

		Fragment f = new Toplist();
		Bundle b = new Bundle();
		b.putString("msg", text);
		b.putString("type", type);
		b.putString("units", units);

		f.setArguments(b);

		return f;
	}

	@Override
	public void onClick(View arg0)
	{
		try {
			SoapResult_getUser result = (SoapResult_getUser) new SendSoapRequest().execute(new SoapRequest_getUserByAndroidId("abc")).get();
		
			Toast.makeText(this.getActivity(), "Result: " + result.resultCode + " - " + result.userId, Toast.LENGTH_LONG).show();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}






















