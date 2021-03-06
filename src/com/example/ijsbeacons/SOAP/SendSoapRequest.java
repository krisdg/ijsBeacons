package com.example.ijsbeacons.SOAP;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;
import android.widget.Toast;

public class SendSoapRequest extends AsyncTask<SoapRequest, String, SoapResult> {
	private String SOAP_ACTION = "http://ijsbeacons.jcdegroot.eu:8080/";
	private String NAMESPACE = "http://ijsbeacons.jcdegroot.eu:8080/";
	private String METHOD_NAME = "";
	private String URL = "http://ijsbeacons.jcdegroot.eu:8080/";

	@Override
	protected SoapResult doInBackground(SoapRequest... soaprequest) {
		SoapObject request = null;

		METHOD_NAME = ((SoapRequest) soaprequest[0]).METHOD_NAME;
		SOAP_ACTION += METHOD_NAME;
		request = new SoapObject(NAMESPACE, METHOD_NAME);

		if (soaprequest[0] instanceof SoapRequest_getUserByAndroidId) {
			//Parameters
			request.addProperty("userAndroidId", ((SoapRequest_getUserByAndroidId) soaprequest[0]).userAndroidId);
		}
		if (soaprequest[0] instanceof SoapRequest_getPersonalStatistics) {
			//Parameters
			request.addProperty("userAndroidId", ((SoapRequest_getPersonalStatistics) soaprequest[0]).userAndroidId);
		}
		if (soaprequest[0] instanceof SoapRequest_registerUser) {
			//Paramaters
			request.addProperty("userAndroidId", ((SoapRequest_registerUser) soaprequest[0]).userAndroidId);
			request.addProperty("userName", ((SoapRequest_registerUser) soaprequest[0]).userName);
		}
		if (soaprequest[0] instanceof SoapRequest_updateStatistics) {
			//Paramaters
			request.addProperty("userAndroidId", ((SoapRequest_updateStatistics) soaprequest[0]).userAndroidId);
			request.addProperty("coffeeMachineCount", ((SoapRequest_updateStatistics) soaprequest[0]).coffeeMachineCount);
			request.addProperty("walkedDistance", ((SoapRequest_updateStatistics) soaprequest[0]).walkedDistance);
			request.addProperty("seenSurface", ((SoapRequest_updateStatistics) soaprequest[0]).seenSurface);
			request.addProperty("walkingSpeed", ((SoapRequest_updateStatistics) soaprequest[0]).walkingSpeed);
			request.addProperty("lunchRoomCount", ((SoapRequest_updateStatistics) soaprequest[0]).lunchRoomCount);
		}
		if (soaprequest[0] instanceof SoapRequest_getToplist) {
			//Paramaters
			request.addProperty("startDate", ((SoapRequest_getToplist) soaprequest[0]).startDate);
			request.addProperty("endDate", ((SoapRequest_getToplist) soaprequest[0]).endDate);
		}
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;

		while(true) {
			try {
				HttpTransportSE transport = new HttpTransportSE(URL);
				transport.call(SOAP_ACTION, envelope);
	
				System.out.println(envelope.bodyIn.toString());
				SoapObject result = (SoapObject) envelope.bodyIn;

				if (soaprequest[0] instanceof SoapRequest_getUserByAndroidId) {
					SoapResult_getUser resultObject = new SoapResult_getUser();
					
					if (result.getProperty(0) instanceof Vector) {
						Vector soapresult = (Vector)result.getProperty(0);
						
						resultObject.resultCode = Integer.parseInt(soapresult.get(0).toString());
						resultObject.resultMessage = soapresult.get(1).toString();
						
						if (resultObject.resultCode == 1) {
							resultObject.userId = Integer.parseInt(soapresult.get(2).toString());
							resultObject.userAndroidId = soapresult.get(3).toString();
							resultObject.userName = soapresult.get(4).toString();
							resultObject.userLastActivity = Integer.parseInt(soapresult.get(5).toString());
							resultObject.userFirstActivity = Integer.parseInt(soapresult.get(6).toString());
						}
					}

					return resultObject;
				}

				if (soaprequest[0] instanceof SoapRequest_getPersonalStatistics) {
					int counter = 0;

					SoapResult_getPersonalStatistics resultObject = new SoapResult_getPersonalStatistics();
					
					if (result.getProperty(0) instanceof Vector) {
						Vector soapresult = (Vector)result.getProperty(0);
						
						resultObject.resultCode = Integer.parseInt(soapresult.get(0).toString());
						resultObject.resultMessage = soapresult.get(1).toString();
						
						if (resultObject.resultCode == 1)
						{
							for (String value : soapresult.get(2).toString().split(";")) {
								resultObject.dayStatistics[counter] = Integer.parseInt(value);
								counter++;
							}
							counter = 0;
							for (String value : soapresult.get(3).toString().split(";")) {
								resultObject.monthStatistics[counter] = Integer.parseInt(value);
								counter++;
							}
						}
					}

					return resultObject;
				}

				if (soaprequest[0] instanceof SoapRequest_getToplist) {
					int counter = 0;
					
					// true is user, false is statistic number
					boolean user = true;

					SoapResult_getToplist resultObject = new SoapResult_getToplist();
					
					if (result.getProperty(0) instanceof Vector) {
						Vector soapresult = (Vector)result.getProperty(0);
						
						resultObject.resultCode = Integer.parseInt(soapresult.get(0).toString());
						resultObject.resultMessage = soapresult.get(1).toString();
						
						if (resultObject.resultCode == 1)
						{
							// Daily
							resultObject.dayStatisticsAvailable = Boolean.parseBoolean(soapresult.get(2).toString());
							for (String value : soapresult.get(3).toString().split(";"))
							{
								if( user )
								{
									resultObject.userDay[counter] = value;
									
								} else {

									resultObject.dayStatistics[counter] = Integer.parseInt(value);
									counter++;
								}
								user = !user;
							}
							
							// Monthly
							user = true;
							counter = 0;
							resultObject.monthStatisticsAvailable = Boolean.parseBoolean(soapresult.get(4).toString());
							for (String value : soapresult.get(5).toString().split(";"))
							{
								if( user )
								{
									resultObject.userMonth[counter] = value;
									
								} else {

									resultObject.monthStatistics[counter] = Integer.parseInt(value);
									counter++;
								}
								user = !user;
							}
							
						} // end if result code 1
					}

					return resultObject;
				}
				
				if (soaprequest[0] instanceof SoapRequest_registerUser || soaprequest[0] instanceof SoapRequest_updateStatistics) {
					SoapResult resultObject = new SoapResult();
					
					if (result.getProperty(0) instanceof Vector) {
						Vector soapresult = (Vector)result.getProperty(0);
						
						resultObject.resultCode = Integer.parseInt(soapresult.get(0).toString());
						resultObject.resultMessage = soapresult.get(1).toString();
					}

					return resultObject;
				}
				
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	protected void onProgressUpdate(String... s) {
		// Toast.makeText(getActivity(), s[0], Toast.LENGTH_SHORT).show();
	}

}
