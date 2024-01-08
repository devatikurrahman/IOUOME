package com.i_o_u_o_me;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.Service;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Page2 extends Fragment implements OnClickListener {
	public static String FRAGMENT_TAG = "Page2";
	View rootview;
	Button plus_button;
	//ScrollView m_scroll_view;
	LinearLayout page2_dyanamic_lay;
	ArrayList<EditText> ammount_array_list = new ArrayList<EditText>();
	ArrayList<EditText> name_array_list = new ArrayList<EditText>();
	ArrayList<Button> cross_button_array_list = new ArrayList<Button>();
	TextView total, sub_title;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState == null)
			rootview = inflater.inflate(R.layout.page_1, container, false);

		return rootview;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (savedInstanceState == null)
			initViewsAndUtils();
	}
	
	public void initViewsAndUtils() {
		MainActivity.top_bar_text.setText("U-O-ME");
		plus_button = (Button) rootview.findViewById(R.id.plus_button);
		plus_button.setOnClickListener(this);
		
		total_ammount = 0;
		total = (TextView) rootview.findViewById(R.id.total);
		
		sub_title = (TextView) rootview.findViewById(R.id.sub_title);
		sub_title.setText("Money owed in");
		
		/*if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
			total.setText("Total: "+"£ "+total_ammount);
		} else {
			total.setText("Total: "+"$ "+total_ammount);
		}*/

		page2_dyanamic_lay = (LinearLayout) rootview.findViewById(R.id.page1_dyanamic_lay);

		ammount_array_list.clear();
		name_array_list.clear();
		cross_button_array_list.clear();
		SetFromDataBase();
		AddDynamicField(page2_dyanamic_lay, "");
		SetTotalMessage();
	}
	
	public void AddDynamicField(LinearLayout mLayout, String data_field) {
		View extra_view = getActivity().getLayoutInflater().inflate(R.layout.page1_extra_lay, mLayout, false);
		
		final ArrayList<EditText> insertionArrayList = new ArrayList<EditText>();
		final EditText direct_debit_name, direct_debit_ammount;
		Button cross_button_red;
		
		direct_debit_name = (EditText) extra_view.findViewById(R.id.direct_debit_name);
		direct_debit_ammount = (EditText) extra_view.findViewById(R.id.direct_debit_ammount);
		cross_button_red = (Button) extra_view.findViewById(R.id.cross_button_red);
		
		ammount_array_list.add(direct_debit_ammount);
		name_array_list.add(direct_debit_name);
		cross_button_array_list.add(cross_button_red);
		
		
		//date.setText(""+((Page3.selected_date*7)+week_day_number));
		//week.setText(FindWeekOfDay(Page3.selected_year+"-"+Page3.selected_month+"-"+((Page3.selected_date*7)+week_day_number)));
		
		if (!data_field.equals("")) {
			String [] data_field_part = data_field.split(",");
			direct_debit_name.setText(data_field_part[0]);
			direct_debit_ammount.setText(data_field_part[1]);
			//ShowTotalLeft();
		} else {
			direct_debit_name.setHint("Name");
			direct_debit_ammount.setHint("Ammount");
			cross_button_red.setVisibility(View.INVISIBLE);
		}
		
		insertionArrayList.clear();
		
		insertionArrayList.add(direct_debit_name);
		insertionArrayList.add(direct_debit_ammount);

		cross_button_red.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				direct_debit_ammount.setText("");
				direct_debit_name.setText("");
				
				SaveToDataBase();
				page2_dyanamic_lay.removeAllViews();
				SetFromDataBase();
				AddDynamicField(page2_dyanamic_lay, "");
			}
		});
		
		//direct_debit_ammount.requestFocus();
		/*direct_debit_ammount.setOnEditorActionListener(new OnEditorActionListener() {

		    @Override

		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_NEXT) {
		        	ShowTotalLeft();
		        }
		        return false;
		    }
		});*/
		
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

	public void SetFromDataBase() {
		db = new DataBaseAdapter(getActivity());
		db.open();
		
		Cursor AmmountCursor = db.getAmmountInfo2();
		Log.v("AmmountCursor.getCount()", "AmmountCursor.getCount() = "+AmmountCursor.getCount());
		
		while (AmmountCursor.moveToNext()) {
			//SetNameEditTextField(AmmountCursor.getString(AmmountCursor.getColumnIndex("Name")));
			//AmmountCursor.getString(AmmountCursor.getColumnIndex("Currency"));
			/*if (AmmountCursor.getString(AmmountCursor.getColumnIndex("Currency")).equals("£")) {
				if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
					SetAmmountEditTextField(AmmountCursor.getString(AmmountCursor.getColumnIndex("Ammount")));
				} else {
					float f = (float) (1.67*(Float.parseFloat(AmmountCursor.getString(AmmountCursor.getColumnIndex("Ammount")))));
					SetAmmountEditTextField(""+f);
					Log.v("f", "f "+f);
				}
			} else if (AmmountCursor.getString(AmmountCursor.getColumnIndex("Currency")).equals("$")) {
				if (MainActivity.mPreferences.getString("currency", "").equals("currency $")) {
					SetAmmountEditTextField(AmmountCursor.getString(AmmountCursor.getColumnIndex("Ammount")));
				} else {
					float f = (float) (0.6*(Float.parseFloat(AmmountCursor.getString(AmmountCursor.getColumnIndex("Ammount")))));
					SetAmmountEditTextField(""+f);
					Log.v("f", "f "+f);
				}
			}*/
			
			String name = AmmountCursor.getString(AmmountCursor.getColumnIndex("Name"));
			String ammount;
			if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
				ammount = AmmountCursor.getString(AmmountCursor.getColumnIndex("CurrencyPound"));
			} else {
				ammount = AmmountCursor.getString(AmmountCursor.getColumnIndex("CurrencyDollar"));
			}
			
			double dAmmount = (double) Float.parseFloat(ammount);
			
			ammount = ""+ String.format("%.2f", dAmmount);
			
			String data_field = name +","+ammount;
			
			AddDynamicField(page2_dyanamic_lay, data_field);
			
			Log.v("Name", "Name = "+AmmountCursor.getString(AmmountCursor.getColumnIndex("Name")));
			Log.v("Ammount", "Ammount = "+AmmountCursor.getString(AmmountCursor.getColumnIndex("Ammount")));
			Log.v("CurrencyDollar", "CurrencyDollar = "+AmmountCursor.getString(AmmountCursor.getColumnIndex("CurrencyDollar")));
			Log.v("CurrencyPound", "CurrencyPound = "+AmmountCursor.getString(AmmountCursor.getColumnIndex("CurrencyPound")));
		}
		
		AmmountCursor.close();
		db.close();
	}
	
	double total_ammount = 0;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.plus_button:
			
			Log.v("", "ammount "+ammount_array_list.get(ammount_array_list.size() - 1).getText().toString());
			
			if ((ammount_array_list.get(ammount_array_list.size() - 1).getText().toString().equals(""))
					||(name_array_list.get(name_array_list.size() - 1).getText().toString().equals(""))) {
				
				Toast.makeText(getActivity(), "Please insert name and ammount.", Toast.LENGTH_SHORT).show();
			} else {
				SetTotalMessage();
				cross_button_array_list.get(cross_button_array_list.size() - 1).setVisibility(View.VISIBLE);
				AddDynamicField(page2_dyanamic_lay, "");
			}
			break;

		case R.id.main_lay:
			HideKeyboard();
			SetTotalMessage();
			break;
			
		case R.id.another_lay:
			HideKeyboard();
			SetTotalMessage();
			break;
			
		default:
			break;
		}
	}
	
	public void HideKeyboard() {
		@SuppressWarnings("unused")
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
		//imm.hideSoftInputFromWindow(name_ed.getWindowToken(), 0);
		//imm.hideSoftInputFromWindow(ammount.getWindowToken(), 0);
	}
	
	//"^[0-9]*$"
	
	public void SetTotalMessage() {
		total_ammount = 0;
		//try {
			for (int i = 0; i < ammount_array_list.size(); i++) {
				if (!ammount_array_list.get(i).getText().toString().equals("")) {
					if (ammount_array_list.get(i).getText().toString().trim().matches("^[0-9]*$")
							|| ammount_array_list.get(i).getText().toString().trim().contains(".")) {
						
						Log.v("Float", "Float = "+Float.parseFloat(ammount_array_list.get(i).getText().toString()));
						
						total_ammount = (double) (total_ammount + Float.parseFloat(ammount_array_list.get(i).getText().toString()));
						Log.v("TAG", "total = "+total_ammount);
					}
				}
				/*if (Integer.parseInt(ammount_array_list.get(i).getText().toString()) >= 0) {
					total_ammount = total_ammount + Integer.parseInt(ammount_array_list.get(i).getText().toString());
				}*/
			}
		/*} catch (Exception e) {
			e.printStackTrace();
		}*/
		if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
			total.setText("Total : "+"£"+String.format("%.2f", total_ammount));
		} else {
			total.setText("Total : "+"$"+String.format("%.2f", total_ammount));
		}
	}
	
	DataBaseAdapter db;
	
	public void SaveToDataBase() {
		db = new DataBaseAdapter(getActivity());
		db.open();
		db.deleteAmmountInfoTable2();
		Log.v("TAG", "msg");
		AmmountInfoClass new_AmmountInfoClass = new AmmountInfoClass();
		
		Log.v("ammount_array_list.size()", "page 2 ammount_array_list.size() = "+ammount_array_list.size());
		
		for (int i = 0; i < ammount_array_list.size(); i++) {
			if (!ammount_array_list.get(i).getText().toString().equals("")) {
				if (ammount_array_list.get(i).getText().toString().trim().matches("^[0-9]*$")
						|| ammount_array_list.get(i).getText().toString().trim().contains(".")) {
					new_AmmountInfoClass.Ammount = ammount_array_list.get(i).getText().toString();
				} else {
					new_AmmountInfoClass.Ammount = "0";
				}
			} else {
				new_AmmountInfoClass.Ammount = "0";
			}
			
			if (name_array_list.get(i).getText().toString().isEmpty()
					|| name_array_list.get(i).getText().toString() == null
					|| name_array_list.get(i).getText().toString().equals("") ) {
				
				new_AmmountInfoClass.Name = "No Name";
			} else {
				new_AmmountInfoClass.Name = name_array_list.get(i).getText().toString();
			}
			
			if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
				new_AmmountInfoClass.CurrencyDollar = ""+(double)(MainActivity.mPreferences.
						getFloat("pound_to_dollar", 0)*Float.parseFloat(new_AmmountInfoClass.Ammount));
				new_AmmountInfoClass.CurrencyPound = new_AmmountInfoClass.Ammount;
			} else {
				new_AmmountInfoClass.CurrencyDollar = new_AmmountInfoClass.Ammount;
				new_AmmountInfoClass.CurrencyPound = ""+(double)(MainActivity.mPreferences.
						getFloat("dollar_to_pound", 0)*Float.parseFloat(new_AmmountInfoClass.Ammount));
			}
			
			if ((name_array_list.get(i).getText().toString().isEmpty()
							|| name_array_list.get(i).getText().toString() == null
							|| name_array_list.get(i).getText().toString().equals(""))
					&& (ammount_array_list.get(i).getText().toString().isEmpty()
							|| ammount_array_list.get(i).getText().toString() == null
							|| ammount_array_list.get(i).getText().toString().equals(""))) {
				Log.v("Tag", "no data");
			} else {
				db.insertAmmountInfo2(new_AmmountInfoClass);
			}
		}

		ammount_array_list.clear();
		name_array_list.clear();
		cross_button_array_list.clear();
		
		db.close();
	}

	@Override
	public void onPause() {
		SaveToDataBase();
		super.onPause();
	}
}