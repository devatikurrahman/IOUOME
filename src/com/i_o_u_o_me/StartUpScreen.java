package com.i_o_u_o_me;

import android.app.Dialog;
import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class StartUpScreen extends Fragment implements OnClickListener {
	public static String FRAGMENT_TAG = "StartUpScreen";
	View rootview;
	Button page_1, page_2, page_3, page_4, page_5, settings;
	TextView name;
	Dialog dialog_settings;

	Button reset_button, currency_change, cross_button;
	ListView currency_list_view;
	String currency_array [] = {"currency $", "currency £"}; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState == null)
			rootview = inflater.inflate(R.layout.start_up_screen, container, false);

		return rootview;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (savedInstanceState == null)
			initViewsAndUtils();
	}
	
	public void initViewsAndUtils() {
		MainActivity.top_bar_text.setText("welcome "+MainActivity.mPreferences.getString("Name", "")+" to i-o-u-o-me");
		page_1 = (Button) rootview.findViewById(R.id.page_1);
		page_1.setOnClickListener(this);
		
		page_2 = (Button) rootview.findViewById(R.id.page_2);
		page_2.setOnClickListener(this);
		
		page_3 = (Button) rootview.findViewById(R.id.page_3);
		page_3.setOnClickListener(this);
		
		page_4 = (Button) rootview.findViewById(R.id.page_4);
		page_4.setOnClickListener(this);
		
		page_5 = (Button) rootview.findViewById(R.id.page_5);
		page_5.setOnClickListener(this);
		
		settings = (Button) rootview.findViewById(R.id.settings);
		settings.setOnClickListener(this);

		//name = (TextView) rootview.findViewById(R.id.name);
		//name.setText(MainActivity.mPreferences.getString("Name", ""));
	}
	
	DataBaseAdapter db;
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.page_1:
			MainActivity.mFragmentManager = getActivity().getFragmentManager();
			MainActivity.mFragmentManager.beginTransaction().replace(R.id.frame, new Page1(), 
					Page1.FRAGMENT_TAG).addToBackStack(null).commit();
			break;

		case R.id.page_2:
			MainActivity.mFragmentManager = getActivity().getFragmentManager();
			MainActivity.mFragmentManager.beginTransaction().replace(R.id.frame, new Page2(), 
					Page2.FRAGMENT_TAG).addToBackStack(null).commit();
			break;

		case R.id.page_3:
			MainActivity.mFragmentManager = getActivity().getFragmentManager();
			MainActivity.mFragmentManager.beginTransaction().replace(R.id.frame, new Page3(), 
					Page3.FRAGMENT_TAG).addToBackStack(null).commit();
			break;

		case R.id.page_4:
			MainActivity.mFragmentManager = getActivity().getFragmentManager();
			MainActivity.mFragmentManager.beginTransaction().replace(R.id.frame, new MonthlyFragement(), 
					MonthlyFragement.FRAGMENT_TAG).addToBackStack(null).commit();
			break;

		case R.id.page_5:
			MainActivity.mFragmentManager = getActivity().getFragmentManager();
			MainActivity.mFragmentManager.beginTransaction().replace(R.id.frame, new Page5(), 
					Page5.FRAGMENT_TAG).addToBackStack(null).commit();
			break;

		case R.id.settings:
			/*MainActivity.mFragmentManager = getActivity().getFragmentManager();
			MainActivity.mFragmentManager.beginTransaction().replace(R.id.frame, new SettingsScreen(), 
					SettingsScreen.FRAGMENT_TAG).addToBackStack(null).commit();*/
			
			dialog_settings = new Dialog(getActivity());
			dialog_settings.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog_settings.setContentView(R.layout.dialog_settings_screen);
			dialog_settings.getWindow().setBackgroundDrawable(new ColorDrawable(0));
			dialog_settings.getWindow().setGravity( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
			
			dialog_settings.show();

			cross_button = (Button) dialog_settings.findViewById(R.id.cross_button);
			cross_button.setOnClickListener(this);
			
			reset_button = (Button) dialog_settings.findViewById(R.id.reset_button);
			reset_button.setOnClickListener(this);
			
			currency_change = (Button) dialog_settings.findViewById(R.id.currency_change);
			currency_change.setOnClickListener(this);
			currency_change.setText(MainActivity.mPreferences.getString("currency", ""));
			
			currency_list_view = (ListView) dialog_settings.findViewById(R.id.currency_list_view);
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
			
			break;
			
		case R.id.reset_button:
			MainActivity.mPreferences.edit().putBoolean("welcome_screen_running", false).commit();
			dialog_settings.dismiss();
			
			db = new DataBaseAdapter(getActivity());
			db.open();
			db.deleteAmmountInfoTable1();
			db.deleteAmmountInfoTable2();
			db.deleteWeeklyCalculationTable();
			db.deleteMonthlyInfoTable();
			db.deleteExpenseInfoTable();
			db.close();
			
			MainActivity.mFragmentManager = getActivity().getFragmentManager();
			MainActivity.mFragmentManager.beginTransaction().replace(R.id.frame, new WelcomeScreen(), 
					WelcomeScreen.FRAGMENT_TAG).addToBackStack(null).commit();
			break;

			
		case R.id.cross_button:
			dialog_settings.dismiss();
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
