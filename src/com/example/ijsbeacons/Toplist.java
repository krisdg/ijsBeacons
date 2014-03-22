package com.example.ijsbeacons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Toplist extends Fragment implements OnClickListener {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
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
	public void onClick(View arg0) {


	}
}