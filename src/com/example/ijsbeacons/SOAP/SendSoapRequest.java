package com.example.ijsbeacons.SOAP;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;
import android.widget.Toast;

public class SendSoapRequest extends AsyncTask<Object, String, Object> {
	private String SOAP_ACTION = "http://ijsbeacons.jcdegroot.eu:8080/";
	private String NAMESPACE = "http://ijsbeacons.jcdegroot.eu:8080/";
	private String METHOD_NAME = "";
	private String URL = "http://ijsbeacons.jcdegroot.eu:8080/";

	@Override
	protected SoapResult_getUserByAndroidId doInBackground(Object... soaprequest) {
		SoapObject request = null;
		
		if (soaprequest[0] instanceof SoapRequest_getUserByAndroidId) {
			METHOD_NAME = "getUserByAndroidId";
			SOAP_ACTION += METHOD_NAME;
			
			request = new SoapObject(NAMESPACE, METHOD_NAME);
			
			request.addProperty("userId", ((SoapRequest_getUserByAndroidId) soaprequest[0]).userAndroidId);
		}
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;

		try {
			HttpTransportSE transport = new HttpTransportSE(URL);
			transport.call(SOAP_ACTION, envelope);

			System.out.println(envelope.bodyIn.toString());
			SoapObject result = (SoapObject) envelope.bodyIn;

			// String result = null;

			if (soaprequest[0] instanceof SoapRequest_getUserByAndroidId) {
				SoapResult_getUserByAndroidId resultObject = new SoapResult_getUserByAndroidId(Integer.parseInt(result.getProperty(0).toString()));
				
				return resultObject;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void onProgressUpdate(String... s) {
		// Toast.makeText(getActivity(), s[0], Toast.LENGTH_SHORT).show();
	}

}
