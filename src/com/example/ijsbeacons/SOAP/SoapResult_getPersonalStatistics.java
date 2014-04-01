package com.example.ijsbeacons.SOAP;

import java.util.HashMap;
import java.util.Map;

public class SoapResult_getPersonalStatistics extends SoapResult {
	public int[] dayStatistics = new int[4];
	public int[] monthStatistics = new int[4];

	public SoapResult_getPersonalStatistics() {
	}
	public SoapResult_getPersonalStatistics(int resultCode) {
		super.resultCode = resultCode;
	}
	public SoapResult_getPersonalStatistics(int resultCode, String resultMessage) {
		super(resultCode, resultMessage);
	}
}