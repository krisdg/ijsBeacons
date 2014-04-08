package com.example.ijsbeacons;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import com.example.ijsbeacons.SOAP.*;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableLayout.LayoutParams;

public class UserStatistics extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_userstatistics, container, false);

//        TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
//        tv.setText(getArguments().getString("msg"));

		try {
			SoapRequest_getPersonalStatistics getPersonalStatisticsObject = new SoapRequest_getPersonalStatistics();
			getPersonalStatisticsObject.userAndroidId = ((IJsBeaconsApplication) getActivity().getApplication()).getUserAndroidId();

			SoapResult_getPersonalStatistics result = (SoapResult_getPersonalStatistics) new SendSoapRequest().execute(getPersonalStatisticsObject).get();
			
			Toast.makeText(getActivity(), result.resultMessage, Toast.LENGTH_SHORT).show();

	        TableLayout table = (TableLayout) v.findViewById(R.id.UserStatisticsTable);
	        TableRow.LayoutParams params_title = new TableRow.LayoutParams();
	        params_title.span = 3;

	        TableRow row_day = new TableRow(this.getActivity());

	        TextView title_day = new TextView(this.getActivity());       
	        title_day.setText(getArguments().getString("msg"));
	        title_day.setTextSize(26);
	        title_day.setPadding(50, 50, 0, 0);

	        row_day.addView(title_day, params_title);

	        table.addView(row_day,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	        
	        String[] prettyNames = {"Koffie gehaald", "Gelopen afstand", "% gezien", "Snelheidsrecord"};
	        String[] units = {"", " m", " %", " km/u"};

			if (result.resultCode == 1)
			{
		        TextView txt_empty = new TextView(this.getActivity());       
		        txt_empty.setText("");

		        TextView txt_month = new TextView(this.getActivity());       
		        txt_month.setText("Dag");
		        txt_month.setTextSize(20);
		        txt_month.setPadding(30, 50, 0, 0);
		        txt_month.setGravity(Gravity.LEFT);
		        
		        TextView txt_day = new TextView(this.getActivity());
		        txt_day.setText("Maand");
		        txt_day.setTextSize(20);
		        txt_day.setPadding(0, 50, 50, 0);
		        txt_day.setGravity(Gravity.LEFT);

        		TableRow firstRow = new TableRow(this.getActivity());
        		firstRow.addView(txt_empty);
        		firstRow.addView(txt_month);
        		firstRow.addView(txt_day);

		        table.addView(firstRow,new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
        	
		        for (int i = 0; i < 4; i++) {
	        		TableRow row = new TableRow(this.getActivity());

			        TextView txt_index = new TextView(this.getActivity());       
			        txt_index.setText(prettyNames[i]);
			        txt_index.setTextSize(20);
			        txt_index.setGravity(Gravity.LEFT);
			        txt_index.setPadding(50, 50, 0, 0);

			        String toShow = String.valueOf(result.dayStatistics[i]);
			        if(i == 3)
			        {
			        	toShow = new DecimalFormat("#.#").format((float)result.dayStatistics[i] / 1000);
			        }
			        TextView txt_dayscore = new TextView(this.getActivity());       
			        txt_dayscore.setText(toShow + units[i]);
			        txt_dayscore.setTextSize(20);
			        txt_dayscore.setPadding(30, 0, 0, 50);
			        txt_dayscore.setGravity(Gravity.LEFT);
			        
			        toShow = String.valueOf(result.monthStatistics[i]);
			        if(i == 3)
			        {
			        	toShow = new DecimalFormat("#.#").format((float)result.monthStatistics[i] / 1000);
			        }
			        TextView txt_monthscore = new TextView(this.getActivity());
			        txt_monthscore.setText(toShow + units[i]);
			        txt_monthscore.setTextSize(20);
			        txt_monthscore.setPadding(0, 50, 50, 0);
			        txt_monthscore.setGravity(Gravity.LEFT);

			        row.addView(txt_index);
			        row.addView(txt_dayscore);
			        row.addView(txt_monthscore);

			        table.addView(row,new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
	        	
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

    public static UserStatistics newInstance(String text) {

    	UserStatistics f = new UserStatistics();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}