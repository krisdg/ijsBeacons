package com.example.ijsbeacons.SOAP;

import java.util.HashMap;
import java.util.Map;

public class SoapResult_getToplist extends SoapResult
{
	public int size = 10;
	
	public String[] userDay = new String[size];
	public int[] dayStatistics = new int[size];
	
	public String[] userMonth = new String[size];
	public int[] monthStatistics = new int[size];

	
	public SoapResult_getToplist() {
	}
	public SoapResult_getToplist(int resultCode) {
		super.resultCode = resultCode;
	}
	public SoapResult_getToplist(int resultCode, String resultMessage) {
		super(resultCode, resultMessage);
	}
}