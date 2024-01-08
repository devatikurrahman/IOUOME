package com.i_o_u_o_me;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class Page4 extends Fragment implements OnClickListener {
	public static String FRAGMENT_TAG = "Page4";
	View rootview;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState == null)
			rootview = inflater.inflate(R.layout.demo_page, container, false);

		return rootview;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (savedInstanceState == null)
			initViewsAndUtils();
	}

	public void initViewsAndUtils() {
		MainActivity.top_bar_text.setText("Monthly outgoings");
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}
	}
}