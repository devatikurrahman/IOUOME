package com.i_o_u_o_me;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.Service;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class MonthlyFragement extends Fragment implements OnClickListener {
	public static String FRAGMENT_TAG = "MonthlyFragement";
	View rootview;
	//133
	Button plus_button;
	LinearLayout dynamic_lay;
	TextView total_left, total_paying_text;
	EditText wage_edit_text, other_income_edit_text;
	TextView wage_text, other_income_text;
	
	ArrayList<EditText> ammount_array_list = new ArrayList<EditText>();
	ArrayList<EditText> name_array_list = new ArrayList<EditText>();
	ArrayList<Button> cross_button_array_list = new ArrayList<Button>();
	//RelativeLayout relative_lay;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState == null)
			rootview = inflater.inflate(R.layout.monthly_view, container, false);
		
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
		
		plus_button = (Button) rootview.findViewById(R.id.plus_button);
		dynamic_lay = (LinearLayout) rootview.findViewById(R.id.dynamic_lay);
		total_left = (TextView) rootview.findViewById(R.id.total_left);
		total_paying_text = (TextView) rootview.findViewById(R.id.total_paying_text);
		
		wage_edit_text = (EditText) rootview.findViewById(R.id.wage_edit_text);
		other_income_edit_text = (EditText) rootview.findViewById(R.id.other_income_edit_text);
		
		wage_text = (TextView) rootview.findViewById(R.id.wage_text);
		other_income_text = (TextView) rootview.findViewById(R.id.other_income_text);
		
		if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
			wage_text.setText("£");
			other_income_text.setText("£");
		} else {
			wage_text.setText("$");
			other_income_text.setText("$");
		}
		
		/*if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
			MainActivity.mPreferences.edit().putString("currency_input", "pound").commit();
		} else {
			MainActivity.mPreferences.edit().putString("currency_input", "dollar").commit();
		}*/
		
		if (!MainActivity.mPreferences.getString("monthly_wage", "").equals("")) {
			wage_edit_text.setText(MainActivity.mPreferences.getString("monthly_wage", ""));
		} else {
			wage_edit_text.setText("0");
		}
		
		if (!MainActivity.mPreferences.getString("other_income", "").equals("")) {
			other_income_edit_text.setText(MainActivity.mPreferences.getString("other_income", ""));
		} else {
			other_income_edit_text.setText("0");
		}
		
		if (MainActivity.mPreferences.getString("currency_input", "").equals("pound")
				&& !MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
			try {
				wage_edit_text.setText(""+String.format("%.2f", (double)(MainActivity.mPreferences.
						getFloat("pound_to_dollar", 0)*Float.
						parseFloat(MainActivity.mPreferences.getString("monthly_wage", "")))));
				
				other_income_edit_text.setText(""+String.format("%.2f", (double)(MainActivity.mPreferences.
						getFloat("pound_to_dollar", 0)*Float.
						parseFloat(MainActivity.mPreferences.getString("other_income", "")))));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (MainActivity.mPreferences.getString("currency_input", "").equals("dollar")
				&& MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
			try {
				wage_edit_text.setText(""+String.format("%.2f", (double)(MainActivity.mPreferences.
						getFloat("dollar_to_pound", 0)*Float.
						parseFloat(MainActivity.mPreferences.getString("monthly_wage", "")))));
				
				other_income_edit_text.setText(""+String.format("%.2f", (double)(MainActivity.mPreferences.
						getFloat("dollar_to_pound", 0)*Float.
						parseFloat(MainActivity.mPreferences.getString("other_income", "")))));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		plus_button.setOnClickListener(this);
		wage_edit_text.setOnEditorActionListener(new OnEditorActionListener() {
			
		    @Override

		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_NEXT) {
		        	ShowTotalLeft();
		        }
		        return false;
		    }
		});
		
		other_income_edit_text.setOnEditorActionListener(new OnEditorActionListener() {
			
		    @Override

		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_NEXT) {
		        	ShowTotalLeft();
		        }
		        return false;
		    }
		});
		
		ammount_array_list.clear();
		name_array_list.clear();
		cross_button_array_list.clear();
		
		total = 0;
		/*relative_lay = (RelativeLayout) rootview.findViewById(R.id.relative_lay);
		relative_lay.setOnClickListener(this);*/
		SetFromDataBase();
		ShowTotalLeft();
		AddDynamicField(dynamic_lay, "");
	}

	public void HideKeyboard() {
		Log.v("hide", "called");
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
		//imm.hideSoftInputFromWindow(other_income_edit_text.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(wage_edit_text.getWindowToken(), 0);
	}
	
	double total = 0;
	double expence = 0;
	
	/*public void ShowTotalLeft(EditText wage, EditText o_income) {
		
		
		total_left.setText(""+String.format("%.2f", total));
	}*/
	
	public void ShowTotalLeft() {
		total = 0;
		expence = 0;
		
		if (!wage_edit_text.getText().toString().equals("")) {
			if (wage_edit_text.getText().toString().trim().matches("^[0-9]*$")
					|| wage_edit_text.getText().toString().trim().contains(".")) {
				total = (Float.parseFloat((wage_edit_text.getText().toString())));
			}
		}
		
		if (!other_income_edit_text.getText().toString().equals("")) {
			if (other_income_edit_text.getText().toString().trim().matches("^[0-9]*$")
					|| other_income_edit_text.getText().toString().trim().contains(".")) {
				
				total = total + (Float.parseFloat((other_income_edit_text.getText().toString())));
			}
		}
		
		Log.v("ammount_array_list.size()", "ammount_array_list.size() = "+ammount_array_list.size());
		
		for (int i = 0; i < ammount_array_list.size(); i++) {
			if (!ammount_array_list.get(i).getText().toString().equals("")) {
				if (ammount_array_list.get(i).getText().toString().trim().matches("^[0-9]*$")
						|| ammount_array_list.get(i).getText().toString().trim().contains(".")) {
					expence = expence + (Float.parseFloat((ammount_array_list.get(i).getText().toString())));
				}
			}
		}
		
		total = total - expence;

		if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
			total_paying_text.setText("£"+String.format("%.2f", expence));
			total_left.setText("£"+String.format("%.2f", total));
		} else {
			total_paying_text.setText("$"+String.format("%.2f", expence));
			total_left.setText("$"+String.format("%.2f", total));
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.plus_button:
			if ((ammount_array_list.get(ammount_array_list.size() - 1).getText().toString().equals(""))
					||(name_array_list.get(name_array_list.size() - 1).getText().toString().equals(""))) {
				
				Toast.makeText(getActivity(), "Please insert direct debit and ammount.", Toast.LENGTH_SHORT).show();
			} else {
				ShowTotalLeft();
				cross_button_array_list.get(cross_button_array_list.size() - 1).setVisibility(View.VISIBLE);
				AddDynamicField(dynamic_lay, "");
			}
			break;
			
		/*case R.id.relative_lay:
			 HideKeyboard();
			 break;*/
			 
		default:
			break;
		}
	}
	

	public void AddDynamicField(LinearLayout mLayout, String data_field) {
		View extra_view = getActivity().getLayoutInflater().inflate(R.layout.page1_extra_lay, mLayout, false);
		
		//final ArrayList<EditText> insertionArrayList = new ArrayList<EditText>();
		final EditText direct_debit_name, direct_debit_ammount;
		final Button cross_button_red;
		
		direct_debit_name = (EditText) extra_view.findViewById(R.id.direct_debit_name);
		direct_debit_ammount = (EditText) extra_view.findViewById(R.id.direct_debit_ammount);
		cross_button_red = (Button) extra_view.findViewById(R.id.cross_button_red);

		ammount_array_list.add(direct_debit_ammount);
		name_array_list.add(direct_debit_name);
		
		cross_button_array_list.add(cross_button_red);;
		
		//date.setText(""+((Page3.selected_date*7)+week_day_number));
		//week.setText(FindWeekOfDay(Page3.selected_year+"-"+Page3.selected_month+"-"+((Page3.selected_date*7)+week_day_number)));
		
		if (!data_field.equals("")) {
			String [] data_field_part = data_field.split(",");
			direct_debit_name.setText(data_field_part[0]);
			direct_debit_ammount.setText(data_field_part[1]);
		} else {
			cross_button_red.setVisibility(View.INVISIBLE);
		}
		
		//insertionArrayList.clear();
		
		//insertionArrayList.add(direct_debit_name);
		//insertionArrayList.add(direct_debit_ammount);

		cross_button_red.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*cross_button_red.setVisibility(View.GONE);
				direct_debit_ammount.setVisibility(View.GONE);
				direct_debit_name.setVisibility(View.GONE);*/

				direct_debit_ammount.setText("");
				direct_debit_name.setText("");
				
				SaveToDataBase();
				dynamic_lay.removeAllViews();
				SetFromDataBase();
				AddDynamicField(dynamic_lay, "");
				ShowTotalLeft();
			}
		});
		//direct_debit_ammount.requestFocus();
		direct_debit_ammount.setOnEditorActionListener(new OnEditorActionListener() {

		    @Override

		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_NEXT) {
		        	ShowTotalLeft();
		        }
		        return false;
		    }
		});
		
		/*Log.v("plus.getId()", "b plus.getId()"+plus.getId());
		plus.setId(1);
		Log.v("plus.getId()", "a plus.getId()"+plus.getId());*/
		/*if (insert_field) {
			total_hour_array_list_text_view.add(total_hour);
			total_earn_array_list_text_view.add(day_total);
		}
		DynamicShowTotal();*/
		mLayout.addView(extra_view);
	}
	
	@Override
	public void onPause() {
		if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
			MainActivity.mPreferences.edit().putString("currency_input", "pound").commit();
		} else {
			MainActivity.mPreferences.edit().putString("currency_input", "dollar").commit();
		}
		
		if (wage_edit_text.getText().toString().trim().matches("^[0-9]*$")
				|| wage_edit_text.getText().toString().trim().contains(".")) {
			Log.v("TAG", "onpause");
			MainActivity.mPreferences.edit().putString("monthly_wage", wage_edit_text.getText().toString()).commit();
		}
		if (other_income_edit_text.getText().toString().trim().matches("^[0-9]*$")
				|| other_income_edit_text.getText().toString().trim().contains(".")) {
			
			MainActivity.mPreferences.edit().putString("other_income", other_income_edit_text.getText().toString()).commit();
		}
		
		SaveToDataBase();
		super.onPause();
	}
	
	DataBaseAdapter db;
	
	public void SaveToDataBase() {
		db = new DataBaseAdapter(getActivity());
		db.open();
		
		/*String [] s = Page3.month_year.split(" ");
		db.DeleteMonthlyData(s[0], s[1]);*/
		db.deleteMonthlyInfoTable();
		Log.v("ammount_array_list.size()", "ammount_array_list.size() = "+ammount_array_list.size());
		
		MonthlyInfoClass new_MonthlyInfoClass = new MonthlyInfoClass();
		
		for (int i = 0; i < ammount_array_list.size(); i++) {
			if (!ammount_array_list.get(i).getText().toString().equals("")) {
				if (ammount_array_list.get(i).getText().toString().trim().matches("^[0-9]*$")
						|| ammount_array_list.get(i).getText().toString().trim().contains(".")) {
					new_MonthlyInfoClass.DebitAmmount = ammount_array_list.get(i).getText().toString();
				} else {
					new_MonthlyInfoClass.DebitAmmount = "0";
				}
			} else {
				new_MonthlyInfoClass.DebitAmmount = "0";
			}
			
			if (name_array_list.get(i).getText().toString().isEmpty()
					|| name_array_list.get(i).getText().toString() == null
					|| name_array_list.get(i).getText().toString().equals("") ) {
				
				new_MonthlyInfoClass.DebitName = "No Name";
			} else {
				new_MonthlyInfoClass.DebitName = name_array_list.get(i).getText().toString();
			}
			
			if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
				new_MonthlyInfoClass.AmmountDollar = ""+(double)(MainActivity.mPreferences.
						getFloat("pound_to_dollar", 0)*Float.parseFloat(new_MonthlyInfoClass.DebitAmmount));
				new_MonthlyInfoClass.AmmountPound = new_MonthlyInfoClass.DebitAmmount;
			} else {
				new_MonthlyInfoClass.AmmountPound = ""+(double)(MainActivity.mPreferences.
						getFloat("dollar_to_pound", 0)*Float.parseFloat(new_MonthlyInfoClass.DebitAmmount));
				new_MonthlyInfoClass.AmmountDollar = new_MonthlyInfoClass.DebitAmmount;
			}
			
			
			//Log.v("", "month "+s[0]);
			//Log.v("", "year "+s[1]);
			
			new_MonthlyInfoClass.MonthName = "";
			new_MonthlyInfoClass.Year = "";
			
			if (ammount_array_list.get(i).getText().toString().equals("") && 
					name_array_list.get(i).getText().toString().equals("")) {
				//Toast.makeText(getActivity(), "Please insert direct debit and ammount", Toast.LENGTH_SHORT).show();
			} else {
				db.insertMonthlyInfo(new_MonthlyInfoClass);
			}
			
			if ((name_array_list.get(i).getText().toString().isEmpty()
							|| name_array_list.get(i).getText().toString() == null
							|| name_array_list.get(i).getText().toString().equals(""))
					&& (name_array_list.get(i).getText().toString().isEmpty()
							|| name_array_list.get(i).getText().toString() == null
							|| name_array_list.get(i).getText().toString().equals(""))) {
				Log.v("Tag", "no data");
			} else {
				Log.v("insert", "data");
			}
		}
		db.close();
		ammount_array_list.clear();
		name_array_list.clear();
		cross_button_array_list.clear();
	}
	
	public void SetFromDataBase() {
		db = new DataBaseAdapter(getActivity());
		db.open();
		/*String [] s = Page3.month_year.split(" ");
		
		//Log.v("", "month "+);
		//Log.v("", "year "+);*/
		Cursor GetDataCursor = db.getMonthlyInfo();
		
		Log.v("getCount", "getCount = "+GetDataCursor.getCount());
		
		while (GetDataCursor.moveToNext()) {
			String name = GetDataCursor.getString(GetDataCursor.getColumnIndex("DebitName"));
			String ammount = GetDataCursor.getString(GetDataCursor.getColumnIndex("DebitAmmount"));
			double amnt;
			if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
				amnt = (double) (Float.parseFloat(GetDataCursor.getString(GetDataCursor.getColumnIndex("AmmountPound")))); 
			} else {
				amnt = (double) (Float.parseFloat(GetDataCursor.getString(GetDataCursor.getColumnIndex("AmmountDollar"))));	
			}
			ammount = ""+ String.format("%.2f", amnt);
			Log.v("DebitAmmount", "DebitAmmount "+GetDataCursor.getString(GetDataCursor.getColumnIndex("DebitAmmount")));
			//Log.v("AmmountInDollar", "AmmountInDollar"+GetDataCursor.getString(GetDataCursor.getColumnIndex("AmmountInDollar")));
			
			String data_field = name+","+ammount;
			
			AddDynamicField(dynamic_lay, data_field);
		}
		
		GetDataCursor.close();
		db.close();
	}
	
}