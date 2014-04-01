package com.example.ijsbeacons;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import com.example.ijsbeacons.SOAP.*;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class UserStatistics extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_userstatistics, container, false);

        TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
        tv.setText(getArguments().getString("msg"));

		try {
			SoapRequest_getPersonalStatistics getPersonalStatisticsObject = new SoapRequest_getPersonalStatistics();
			getPersonalStatisticsObject.userAndroidId = ((IJsBeaconsApplication) getActivity().getApplication()).getUserAndroidId();

			SoapResult_getPersonalStatistics result = (SoapResult_getPersonalStatistics) new SendSoapRequest().execute(
					getPersonalStatisticsObject).get();
			
			Toast.makeText(getActivity(), result.resultMessage, Toast.LENGTH_SHORT).show();

			if (result.resultCode == 1)
			{
		        TextView tvCoffeeMachineDay = (TextView) v.findViewById(R.id.tvCoffeeMachineDay);
		        tvCoffeeMachineDay.setText(result.dayStatistics[0]);

		        TextView tvWalkedDistanceDay = (TextView) v.findViewById(R.id.tvWalkedDistanceDay);
		        tvWalkedDistanceDay.setText(result.dayStatistics[1]);

		        TextView tvSeenSurfaceDay = (TextView) v.findViewById(R.id.tvSeenSurfaceDay);
		        tvSeenSurfaceDay.setText(result.dayStatistics[2]);

		        TextView tvWalkingSpeedDay = (TextView) v.findViewById(R.id.tvWalkingSpeedDay);
		        tvWalkingSpeedDay.setText(result.dayStatistics[3]);

		        TextView tvCoffeeMachineMonth = (TextView) v.findViewById(R.id.tvCoffeeMachineMonth);
		        tvCoffeeMachineMonth.setText(result.monthStatistics[0]);

		        TextView tvWalkedDistanceMonth = (TextView) v.findViewById(R.id.tvWalkedDistanceMonth);
		        tvWalkedDistanceMonth.setText(result.monthStatistics[1]);

		        TextView tvSeenSurfaceMonth = (TextView) v.findViewById(R.id.tvSeenSurfaceMonth);
		        tvSeenSurfaceMonth.setText(result.monthStatistics[2]);

		        TextView tvWalkingSpeedMonth = (TextView) v.findViewById(R.id.tvWalkingSpeedMonth);
		        tvWalkingSpeedMonth.setText(result.monthStatistics[3]);

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