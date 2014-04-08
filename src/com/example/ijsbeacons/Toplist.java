package com.example.ijsbeacons;

import java.text.DecimalFormat;
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
import android.widget.ImageView;
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

public class Toplist extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_toplist, container, false);

		try {
			SoapRequest_getToplist getToplist = new SoapRequest_getToplist(getArguments().getString("type"));
			getToplist.userAndroidId = ((IJsBeaconsApplication) getActivity().getApplication()).getUserAndroidId();

			SoapResult_getToplist result = (SoapResult_getToplist) new SendSoapRequest().execute(getToplist).get();
			
//			Toast.makeText(getActivity(), result.resultMessage, Toast.LENGTH_SHORT).show();
			
			//Set title
	        TableLayout table = (TableLayout) v.findViewById(R.id.TopListTable);
	        TableRow.LayoutParams params_title = new TableRow.LayoutParams();
	        params_title.span = 3;
	        TableRow.LayoutParams params_error = new TableRow.LayoutParams();
	        params_error.span = 2;

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
				
				if (result.dayStatisticsAvailable == false) {
			        TableRow row_day_na = new TableRow(this.getActivity());
			        
        			ImageView img_na = new ImageView(this.getActivity());
        			img_na.setImageDrawable(v.getResources().getDrawable(R.drawable.error));
        			img_na.setPadding(50, 50, 0, 0);
        			
        			row_day_na.addView(img_na);

			        TextView title_day_na = new TextView(this.getActivity());       
			        title_day_na.setText("Gegevens nog niet beschikbaar");
			        title_day_na.setTextSize(20);
			        title_day_na.setPadding(30, 35, 0, 0);

			        row_day_na.addView(title_day_na, params_error);

			        table.addView(row_day_na,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				} else {
			        for (String user : result.userDay) {
			        	if (user != null) {
			        		TableRow row = new TableRow(this.getActivity());
	
			        		if (counter < 3) {
			        			ImageView img_index = new ImageView(this.getActivity());
			        			if (counter == 0) {
				        			img_index.setImageDrawable(v.getResources().getDrawable(R.drawable.gold));
			        			}
			        			if (counter == 1) {
				        			img_index.setImageDrawable(v.getResources().getDrawable(R.drawable.silver));
			        			}
			        			if (counter == 2) {
				        			img_index.setImageDrawable(v.getResources().getDrawable(R.drawable.bronze));
			        			}
			        			img_index.setPadding(35, 20, 0, 0);
						        row.addView(img_index);
			        		} else {
			        			TextView txt_index = new TextView(this.getActivity());       
			        			txt_index.setText(String.valueOf(counter + 1));
			        			txt_index.setTextSize(20);
			        			txt_index.setGravity(Gravity.LEFT);
			        			txt_index.setPadding(50, 20, 0, 0);
						        row.addView(txt_index);
			        		}
					        
					        TextView txt_user = new TextView(this.getActivity());       
					        txt_user.setText(user);
					        txt_user.setTextSize(20);
					        txt_user.setPadding(30, 20, 0, 0);
					        txt_user.setGravity(Gravity.LEFT);
	
					        String unit = getArguments().getString("units");
					        if (unit.equals("") == false) {
					        	unit = " " + unit;
					        }
					        
					        TextView txt_score = new TextView(this.getActivity());
					        if (unit.equals(" km/u")) {
						        txt_score.setText(new DecimalFormat("#.#").format((float)result.dayStatistics[counter] / 1000) + unit);
					        } else {
						        txt_score.setText(result.dayStatistics[counter] + unit);
					        }
					        txt_score.setTextSize(20);
					        txt_score.setPadding(0, 20, 50, 0);
					        txt_score.setGravity(Gravity.RIGHT);
	
					        row.addView(txt_user);
					        row.addView(txt_score);
	
					        table.addView(row,new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
			        	
					        counter++;
			        	}
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

				if (result.monthStatisticsAvailable == false) {
			        TableRow row_month_na = new TableRow(this.getActivity());

        			ImageView img_na = new ImageView(this.getActivity());
        			img_na.setImageDrawable(v.getResources().getDrawable(R.drawable.error));
        			img_na.setPadding(50, 50, 0, 0);
        			
        			row_month_na.addView(img_na);

			        TextView title_month_na = new TextView(this.getActivity());       
			        title_month_na.setText("Gegevens nog niet beschikbaar");
			        title_month_na.setTextSize(20);
			        title_month_na.setPadding(30, 35, 0, 0);

			        row_month_na.addView(title_month_na, params_error);

			        table.addView(row_month_na,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				} else {
			        for (String user : result.userMonth) {
			        	if (user != null) {
			        		TableRow row = new TableRow(this.getActivity());
			        		
			        		if (counter < 3) {
			        			ImageView img_index = new ImageView(this.getActivity());
			        			if (counter == 0) {
				        			img_index.setImageDrawable(v.getResources().getDrawable(R.drawable.gold));
			        			}
			        			if (counter == 1) {
				        			img_index.setImageDrawable(v.getResources().getDrawable(R.drawable.silver));
			        			}
			        			if (counter == 2) {
				        			img_index.setImageDrawable(v.getResources().getDrawable(R.drawable.bronze));
			        			}
			        			img_index.setPadding(35, 20, 0, 0);
						        row.addView(img_index);
			        		} else {
			        			TextView txt_index = new TextView(this.getActivity());       
			        			txt_index.setText(String.valueOf(counter + 1));
			        			txt_index.setTextSize(20);
			        			txt_index.setGravity(Gravity.LEFT);
			        			txt_index.setPadding(50, 20, 0, 0);
						        row.addView(txt_index);
			        		}
					        
					        TextView txt_user = new TextView(this.getActivity());       
					        txt_user.setText(user);
					        txt_user.setTextSize(20);
					        txt_user.setPadding(30, 20, 0, 0);
					        txt_user.setGravity(Gravity.LEFT);
	
					        String unit = getArguments().getString("units");
					        if (unit.equals("") == false) {
					        	unit = " " + unit;
					        }
					        
					        TextView txt_score = new TextView(this.getActivity());
					        if (unit.equals(" km/u")) {
						        txt_score.setText(new DecimalFormat("#.#").format((float)result.monthStatistics[counter] / 1000) + unit);
					        } else {
						        txt_score.setText(result.monthStatistics[counter] + unit);
					        }
					        txt_score.setTextSize(20);
					        txt_score.setPadding(0, 20, 50, 0);
					        txt_score.setGravity(Gravity.RIGHT);
			        		
					        row.addView(txt_user);
					        row.addView(txt_score);
	
					        table.addView(row,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			        	
					        counter++;
			        	}
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
}






















