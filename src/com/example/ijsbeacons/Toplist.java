package com.example.ijsbeacons;

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

public class Toplist extends Fragment implements OnClickListener
{

    private static String SOAP_ACTION = "http://ijsbeacons.jcdegroot.eu:8080/FahrenheitToCelsius/";
    private static String NAMESPACE = "http://ijsbeacons.jcdegroot.eu:8080/";
    private static String METHOD_NAME = "FahrenheitToCelsius";
    private static String URL = "http://ijsbeacons.jcdegroot.eu:8080/";
	
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
		new SendSoapRequest().execute(50);
	}

	private class SendSoapRequest extends AsyncTask<Integer, String, Void>
	{
		@Override
		protected Void doInBackground(Integer... fahr)
		{
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("Fahrenheit", fahr[0]);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
	        envelope.dotNet = true;

	        publishProgress("Trying to send " + fahr[0] + " fahrenheit..");
			
			try{
				HttpTransportSE transport = new HttpTransportSE(URL);
				transport.call(SOAP_ACTION, envelope);
				

				System.out.println(envelope.bodyIn.toString());
				SoapObject result = (SoapObject)envelope.bodyIn;
				
//				String result = null;
				
				if( result != null )
				{
					publishProgress("Result: " + result.getProperty(0).toString());
				
				} else {

					publishProgress("Result was empty :(");
				}
			
			} catch(Exception e){
				e.printStackTrace();
				
				publishProgress("ERROR");
				
			}
			return null;
		}
		
		protected void onProgressUpdate(String... s)
		{
			Toast.makeText(getActivity(), s[0], Toast.LENGTH_SHORT).show();
		}
	
	}

}






















