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

import com.example.ijsbeacons.SOAP.SendSoapRequest;
import com.example.ijsbeacons.SOAP.SoapRequest_getUserByAndroidId;
import com.example.ijsbeacons.SOAP.SoapResult_getUser;

public class Toplist extends Fragment implements OnClickListener
{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_toplist, container, false);

		TextView tv = (TextView) v.findViewById(R.id.tvFragThird);
		tv.setText(getArguments().getString("msg"));

		v.findViewById(R.id.button1).setOnClickListener(this);
		return v;
	}

	public static Fragment newInstance(String text) {

		Fragment f = new Toplist();
		Bundle b = new Bundle();
		b.putString("msg", text);

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






















