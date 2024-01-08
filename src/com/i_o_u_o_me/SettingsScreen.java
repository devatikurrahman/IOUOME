package com.i_o_u_o_me;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SettingsScreen extends Fragment implements OnClickListener {
	public static String FRAGMENT_TAG = "SettingsScreen";
	View rootview;
	Button reset_button, currency_change;
	ListView currency_list_view;
	String currency_array [] = {"currency $", "currency &"}; 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState == null)
			rootview = inflater.inflate(R.layout.dialog_settings_screen, container, false);

		return rootview;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (savedInstanceState == null)
			initViewsAndUtils();
	}

	public void initViewsAndUtils() {
		reset_button = (Button) rootview.findViewById(R.id.reset_button);
		reset_button.setOnClickListener(this);
		
		currency_change = (Button) rootview.findViewById(R.id.currency_change);
		currency_change.setOnClickListener(this);
		currency_change.setText(MainActivity.mPreferences.getString("currency", ""));
		
		currency_list_view = (ListView) rootview.findViewById(R.id.currency_list_view);
		currency_list_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Log.v("position", "position = "+position);
				currency_list_view.setVisibility(View.INVISIBLE);
				currency_change.setText(currency_array[position]);
				MainActivity.mPreferences.edit().putString("currency", currency_array[position]).commit();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reset_button:
			MainActivity.mPreferences.edit().putBoolean("welcome_screen_running", false).commit();
			break;

		case R.id.currency_change:
			if (currency_list_view.getVisibility() == View.VISIBLE) {
				currency_list_view.setVisibility(View.INVISIBLE);
			} else {
				currency_list_view.setVisibility(View.VISIBLE);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spiner_lay, currency_array);
				currency_list_view.setAdapter(adapter);
			}
			break;
			
		default:
			break;
		}
	}
}