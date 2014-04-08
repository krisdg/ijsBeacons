package com.example.ijsbeacons;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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

		TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
		tv.setText(getArguments().getString("msg"));

		try {
			SoapRequest_getToplist getToplist = new SoapRequest_getToplist(getArguments().getString("type"));
			getToplist.userAndroidId = ((IJsBeaconsApplication) getActivity().getApplication()).getUserAndroidId();

			SoapResult_getToplist result = (SoapResult_getToplist) new SendSoapRequest().execute(getToplist).get();
			
			Toast.makeText(getActivity(), result.resultMessage, Toast.LENGTH_SHORT).show();

			if (result.resultCode == 1)
			{
		        TextView tvUserDaily01 = (TextView) v.findViewById(R.id.userDaily01);
		        tvUserDaily01.setText(result.userDay[0] + "");

		        TextView tvStatisticsDaily01 = (TextView) v.findViewById(R.id.statisticsDaily01);
		        tvStatisticsDaily01.setText(result.dayStatistics[0] + "");


		        TextView tvUserMonthly01 = (TextView) v.findViewById(R.id.userMonthly01);
		        tvUserMonthly01.setText(result.userMonth[0] + "");

		        TextView tvStatisticsMonthly01 = (TextView) v.findViewById(R.id.statisticsMonthly01);
		        tvStatisticsMonthly01.setText(result.monthStatistics[0] + "");

		        
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
	

	public static Fragment newInstance(String text, String type) {

		Fragment f = new Toplist();
		Bundle b = new Bundle();
		b.putString("msg", text);
		b.putString("type", type);

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






















