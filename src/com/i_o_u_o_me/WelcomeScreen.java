package com.i_o_u_o_me;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeScreen extends Fragment implements OnClickListener {
	public static String FRAGMENT_TAG = "WelcomeScreen";
	View rootview;
	Button save_button, currency;
	String currency_array [] = {"currency $", "currency £"};
	ListView currency_list_view;
	int final_position;
	EditText name;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState == null)
			rootview = inflater.inflate(R.layout.welcome_screen, container, false);

		return rootview;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (savedInstanceState == null)
			initViewsAndUtils();
	}
	
	public void initViewsAndUtils() {
		save_button = (Button) rootview.findViewById(R.id.save_button);
		save_button.setOnClickListener(this);
		currency = (Button) rootview.findViewById(R.id.currency);
		currency.setOnClickListener(this);
		
		name = (EditText) rootview.findViewById(R.id.name);
		name.setOnEditorActionListener(new OnEditorActionListener() {
			
		    @Override

		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_DONE) {
		        	currency.performClick();
		        }
		        return false;
		    }
		});

		currency_list_view = (ListView) rootview.findViewById(R.id.currency_list_view);
		currency_list_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Log.v("position", "position = "+position);
				currency_list_view.setVisibility(View.INVISIBLE);
				currency.setText(currency_array[position]);
				final_position = position;
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save_button:
			if (final_position >= 0 && final_position < currency_array.length && name.getText().toString().length() != 0) {
				MainActivity.mPreferences.edit().putBoolean("welcome_screen_running", true).commit();
				MainActivity.mPreferences.edit().putString("currency", currency_array[final_position]).commit();
				MainActivity.mPreferences.edit().putString("Name", name.getText().toString()).commit();
				MainActivity.mFragmentManager = getActivity().getFragmentManager();
				MainActivity.mFragmentManager.beginTransaction().replace(R.id.frame, new StartUpScreen(), 
						StartUpScreen.FRAGMENT_TAG).addToBackStack(null).commit();
			} else {
				Toast.makeText(getActivity(), "Please insert your name and choose your currency.", Toast.LENGTH_LONG).show();
			}
			break;
			
		case R.id.currency:
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
