package com.i_o_u_o_me;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.widget.ToggleButton;

public class WeekFragement extends Fragment implements OnClickListener {
	public static String FRAGMENT_TAG = "WeekFragement";
	View rootview;
	
	Button satday_picker_button, sunday_picker_button;
	EditText night_premium_edittext;
	ToggleButton toggle_button;
	TextView paid_for_breaks_text;
	
	Button fst_day_week_np_button1, snd_day_week_np_button2, trd_day_week_np_button3,
			frt_day_week_np_button4, fft_day_week_np_button5, sxt_day_week_np_button6,
			svn_day_week_np_button7;
	
	RelativeLayout fst_day_week_np_lay1, snd_day_week_np_lay2, trd_day_week_np_lay3,
			frt_day_week_np_lay4, fft_day_week_np_lay5, sxt_day_week_np_lay6,
			svn_day_week_np_lay7;
	
	TextView fst_day_week_plus1, snd_day_week_plus2, trd_day_week_plus3,
			frt_day_week_plus4, fft_day_week_plus5, sxt_day_week_plus6,
			svn_day_week_plus7;
	
	TextView fst_day_week_date1, snd_day_week_date2, trd_day_week_date3,
			frt_day_week_date4, fft_day_week_date5, sxt_day_week_date6,
			svn_day_week_date7;
	
	TextView fst_day_week_name1, snd_day_week_name2, trd_day_week_name3,
			frt_day_week_name4, fft_day_week_name5, sxt_day_week_name6,
			svn_day_week_name7;
	
	TextView fst_day_week_str_tm1, snd_day_week_str_tm2, trd_day_week_str_tm3,
			frt_day_week_str_tm4, fft_day_week_str_tm5, sxt_day_week_str_tm6,
			svn_day_week_str_tm7;

	TextView fst_day_week_brk_tm1, snd_day_week_brk_tm2, trd_day_week_brk_tm3,
			frt_day_week_brk_tm4, fft_day_week_brk_tm5, sxt_day_week_brk_tm6,
			svn_day_week_brk_tm7;

	TextView fst_day_week_fns_tm1, snd_day_week_fns_tm2, trd_day_week_fns_tm3,
			frt_day_week_fns_tm4, fft_day_week_fns_tm5, sxt_day_week_fns_tm6,
			svn_day_week_fns_tm7;

	TextView fst_day_week_ttl_hr1, snd_day_week_ttl_hr2, trd_day_week_ttl_hr3,
			frt_day_week_ttl_hr4, fft_day_week_ttl_hr5, sxt_day_week_ttl_hr6,
			svn_day_week_ttl_hr7;

	TextView fst_day_week_dy_ttl1, snd_day_week_dy_ttl2, trd_day_week_dy_ttl3,
			frt_day_week_dy_ttl4, fft_day_week_dy_ttl5, sxt_day_week_dy_ttl6,
			svn_day_week_dy_ttl7;
	
	EditText hourly_rate_edit_text;
	TextView hourly_rate_text, satureday_rate_text, sunday_rate_text, night_rate_text;
	
	ArrayList<TextView> first_week_array_list_text_view = new ArrayList<TextView>();
	ArrayList<TextView> second_week_array_list_text_view = new ArrayList<TextView>();
	ArrayList<TextView> third_week_array_list_text_view = new ArrayList<TextView>();
	ArrayList<TextView> fourth_week_array_list_text_view = new ArrayList<TextView>();
	ArrayList<TextView> fifth_week_array_list_text_view = new ArrayList<TextView>();
	ArrayList<TextView> sixth_week_array_list_text_view = new ArrayList<TextView>();
	ArrayList<TextView> seventh_week_array_list_text_view = new ArrayList<TextView>();
	
	ArrayList<TextView> start_time_array_list_text_view = new ArrayList<TextView>();
	ArrayList<TextView> break_time_array_list_text_view = new ArrayList<TextView>();
	ArrayList<TextView> finish_time_array_list_text_view = new ArrayList<TextView>();
	ArrayList<TextView> total_hour_array_list_text_view = new ArrayList<TextView>();
	ArrayList<TextView> total_earn_array_list_text_view = new ArrayList<TextView>();
	
	//ArrayList<TextView> day_of_week_list_text_view = new ArrayList<TextView>();

	TextView weekly_total_working_time, weekly_total_earn;
	
	LinearLayout linear_lay_week1, linear_lay_week2, linear_lay_week3, linear_lay_week4,
			linear_lay_week5, linear_lay_week6, linear_lay_week7;
	
	Dialog dialog_progress;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState == null)
			rootview = inflater.inflate(R.layout.weekly_view, container, false);

		return rootview;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (savedInstanceState == null)
			initViewsAndUtils();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		spref.edit().putString("hourly_pay_rate", hourly_rate_edit_text.getText().toString()).commit();
		if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
			spref.edit().putString("hourly_pay_rate_inputted_currency", "pound").commit();
		} else {
			spref.edit().putString("hourly_pay_rate_inputted_currency", "dollar").commit();
		}
		super.onPause();
	}

	SharedPreferences spref;
	
	public void UpdatePaidForBreaks(boolean isChecked) {
		
		dialog_progress = new Dialog(getActivity());
		dialog_progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_progress.setContentView(R.layout.dialog_progress);
		dialog_progress.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		dialog_progress.getWindow().setGravity( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		dialog_progress.setCanceledOnTouchOutside(false);
		dialog_progress.setCancelable(false);
		dialog_progress.show();
		
		db = new DataBaseAdapter(getActivity());
		db.open();
		db.updatePaidForBreaks(isChecked+"", Page3.selected_date+"", Page3.selected_month, Page3.selected_year);
		db.close();
		
		/*linear_lay_week1.removeAllViews();
		linear_lay_week2.removeAllViews();
		linear_lay_week3.removeAllViews();
		linear_lay_week4.removeAllViews();
		linear_lay_week5.removeAllViews();
		linear_lay_week6.removeAllViews();
		linear_lay_week7.removeAllViews();*/
		if (linear_lay_week1.getChildCount() > 1) {
			linear_lay_week1.removeViews(1, linear_lay_week1.getChildCount()-1);
		}
		if (linear_lay_week2.getChildCount() > 1) {
			linear_lay_week2.removeViews(1, linear_lay_week2.getChildCount()-1);
		}
		if (linear_lay_week3.getChildCount() > 1) {
			linear_lay_week3.removeViews(1, linear_lay_week3.getChildCount()-1);
		}
		if (linear_lay_week4.getChildCount() > 1) {
			linear_lay_week4.removeViews(1, linear_lay_week4.getChildCount()-1);
		}
		if (linear_lay_week5.getChildCount() > 1) {
			linear_lay_week5.removeViews(1, linear_lay_week5.getChildCount()-1);
		}
		if (linear_lay_week6.getChildCount() > 1) {
			linear_lay_week6.removeViews(1, linear_lay_week6.getChildCount()-1);
		}
		if (linear_lay_week7.getChildCount() > 1) {
			linear_lay_week7.removeViews(1, linear_lay_week7.getChildCount()-1);
		}
		
		week_1_insertionId = 0;
		week_2_insertionId = 0;
		week_3_insertionId = 0;
		week_4_insertionId = 0;
		week_5_insertionId = 0;
		week_6_insertionId = 0;
		week_7_insertionId = 0;
		
		AddingToArrayList();
		if (Page3.selected_date > 3) {
			if (Page3.how_many_days_in_this_month == 29) {
				SetFromDataBase(first_week_array_list_text_view, ""+((Page3.selected_date*7)+1), Page3.selected_year, Page3.selected_month);
			} else if (Page3.how_many_days_in_this_month == 30) {
				SetFromDataBase(first_week_array_list_text_view, ""+((Page3.selected_date*7)+1), Page3.selected_year, Page3.selected_month);
				SetFromDataBase(second_week_array_list_text_view, ""+((Page3.selected_date*7)+2), Page3.selected_year, Page3.selected_month);
			} else if (Page3.how_many_days_in_this_month == 31) {
				SetFromDataBase(first_week_array_list_text_view, ""+((Page3.selected_date*7)+1), Page3.selected_year, Page3.selected_month);
				SetFromDataBase(second_week_array_list_text_view, ""+((Page3.selected_date*7)+2), Page3.selected_year, Page3.selected_month);
				SetFromDataBase(third_week_array_list_text_view, ""+((Page3.selected_date*7)+3), Page3.selected_year, Page3.selected_month);
			}
		} else {
			SetFromDataBase(first_week_array_list_text_view, ""+((Page3.selected_date*7)+1), Page3.selected_year, Page3.selected_month);
			SetFromDataBase(second_week_array_list_text_view, ""+((Page3.selected_date*7)+2), Page3.selected_year, Page3.selected_month);
			SetFromDataBase(third_week_array_list_text_view, ""+((Page3.selected_date*7)+3), Page3.selected_year, Page3.selected_month);
			SetFromDataBase(fourth_week_array_list_text_view, ""+((Page3.selected_date*7)+4), Page3.selected_year, Page3.selected_month);
			SetFromDataBase(fifth_week_array_list_text_view, ""+((Page3.selected_date*7)+5), Page3.selected_year, Page3.selected_month);
			SetFromDataBase(sixth_week_array_list_text_view, ""+((Page3.selected_date*7)+6), Page3.selected_year, Page3.selected_month);
			SetFromDataBase(seventh_week_array_list_text_view, ""+((Page3.selected_date*7)+7), Page3.selected_year, Page3.selected_month);
		}
		ShowTotal();
		ShowPremium();
		dialog_progress.dismiss();
	}

	public void initViewsAndUtils() {
		MainActivity.top_bar_text.setText("Weekly rota");
		
		spref = getActivity().getSharedPreferences("IOUMEprefs", Context.MODE_PRIVATE);
		
		db = new DataBaseAdapter(getActivity());
		db.open();
		
		//dynamic_arraylistArray.clear();
		
		paid_for_breaks_text = (TextView) rootview.findViewById(R.id.paid_for_breaks_text);
		toggle_button = (ToggleButton) rootview.findViewById(R.id.toggle_button);
		
		if (db.IsPaidForBreaks(Page3.selected_date+"", Page3.selected_month, Page3.selected_year)) {
			toggle_button.setChecked(true);
		} else {
			toggle_button.setChecked(false);
		}
		
		db.close();
		
		toggle_button.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					toggle_button.setChecked(true);
				} else {
					toggle_button.setChecked(false);
				}
				
				UpdatePaidForBreaks(toggle_button.isChecked());
			}
		});
		
		paid_for_breaks_text.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (toggle_button.isChecked()) {
					toggle_button.setChecked(false);
				} else {
					toggle_button.setChecked(true);
				}
				UpdatePaidForBreaks(toggle_button.isChecked());
			}
		});
		
		satday_picker_button = (Button) rootview .findViewById(R.id.satday_picker_button);
		sunday_picker_button = (Button) rootview .findViewById(R.id.sunday_picker_button);
		night_premium_edittext = (EditText) rootview .findViewById(R.id.night_premium_edittext);
		
		satday_picker_button.setOnClickListener(this);
		sunday_picker_button.setOnClickListener(this);
		
		night_premium_edittext.setOnEditorActionListener(new OnEditorActionListener() {

		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_DONE) {
					if (night_premium_edittext.getText().toString().trim().matches("^[0-9]*$")
							|| hourly_rate_edit_text.getText().toString().trim().contains(".")) {
						
			        	Persentage(null, "night", night_premium_edittext);
					}
		        }
		        return false;
		    }
		});
		
		hourly_rate_edit_text = (EditText) rootview .findViewById(R.id.hourly_rate_edit_text);
		if (!spref.getString("hourly_pay_rate", "").equals("")) {
			hourly_rate_edit_text.setText(spref.getString("hourly_pay_rate", ""));
		}/*
		
		if (spref.getString("hourly_pay_rate_inputted_currency", "").equals("pound")
				&& !MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
			try {
				hourly_rate_edit_text.setText(""+String.format("%.2f", (double)(MainActivity.mPreferences.
						getFloat("pound_to_dollar", 0)*Float.
						parseFloat(spref.getString("hourly_pay_rate", "")))));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (!spref.getString("hourly_pay_rate_inputted_currency", "").equals("dollar")
				&& MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
			try {
				hourly_rate_edit_text.setText(""+String.format("%.2f", (double)(MainActivity.mPreferences.
						getFloat("dollar_to_pound", 0)*Float.
						parseFloat(spref.getString("hourly_pay_rate", "")))));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		
		hourly_rate_text = (TextView) rootview .findViewById(R.id.hourly_rate_text);
		satureday_rate_text = (TextView) rootview .findViewById(R.id.satureday_rate_text);
		sunday_rate_text = (TextView) rootview .findViewById(R.id.sunday_rate_text);
		night_rate_text = (TextView) rootview .findViewById(R.id.night_rate_text);
		
		if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
			hourly_rate_text.setText("£");
			satureday_rate_text.setText("£");
			sunday_rate_text.setText("£");
			night_rate_text.setText("£");
		} else {
			hourly_rate_text.setText("$");
			satureday_rate_text.setText("$");
			sunday_rate_text.setText("$");
			night_rate_text.setText("$");
		}

		fst_day_week_str_tm1 = (TextView) rootview .findViewById(R.id.fst_day_week_str_tm1);
		snd_day_week_str_tm2 = (TextView) rootview .findViewById(R.id.snd_day_week_str_tm2);
		trd_day_week_str_tm3 = (TextView) rootview .findViewById(R.id.trd_day_week_str_tm3);
		frt_day_week_str_tm4 = (TextView) rootview .findViewById(R.id.frt_day_week_str_tm4);
		fft_day_week_str_tm5 = (TextView) rootview .findViewById(R.id.fft_day_week_str_tm5);
		sxt_day_week_str_tm6 = (TextView) rootview .findViewById(R.id.sxt_day_week_str_tm6);
		svn_day_week_str_tm7 = (TextView) rootview .findViewById(R.id.svn_day_week_str_tm7);

		fst_day_week_brk_tm1 = (TextView) rootview .findViewById(R.id.fst_day_week_brk_tm1);
		snd_day_week_brk_tm2 = (TextView) rootview .findViewById(R.id.snd_day_week_brk_tm2);
		trd_day_week_brk_tm3 = (TextView) rootview .findViewById(R.id.trd_day_week_brk_tm3);
		frt_day_week_brk_tm4 = (TextView) rootview .findViewById(R.id.frt_day_week_brk_tm4);
		fft_day_week_brk_tm5 = (TextView) rootview .findViewById(R.id.fft_day_week_brk_tm5);
		sxt_day_week_brk_tm6 = (TextView) rootview .findViewById(R.id.sxt_day_week_brk_tm6);
		svn_day_week_brk_tm7 = (TextView) rootview .findViewById(R.id.svn_day_week_brk_tm7);

		fst_day_week_fns_tm1 = (TextView) rootview .findViewById(R.id.fst_day_week_fns_tm1);
		snd_day_week_fns_tm2 = (TextView) rootview .findViewById(R.id.snd_day_week_fns_tm2);
		trd_day_week_fns_tm3 = (TextView) rootview .findViewById(R.id.trd_day_week_fns_tm3);
		frt_day_week_fns_tm4 = (TextView) rootview .findViewById(R.id.frt_day_week_fns_tm4);
		fft_day_week_fns_tm5 = (TextView) rootview .findViewById(R.id.fft_day_week_fns_tm5);
		sxt_day_week_fns_tm6 = (TextView) rootview .findViewById(R.id.sxt_day_week_fns_tm6);
		svn_day_week_fns_tm7 = (TextView) rootview .findViewById(R.id.svn_day_week_fns_tm7);

		fst_day_week_ttl_hr1 = (TextView) rootview .findViewById(R.id.fst_day_week_ttl_hr1);
		snd_day_week_ttl_hr2 = (TextView) rootview .findViewById(R.id.snd_day_week_ttl_hr2);
		trd_day_week_ttl_hr3 = (TextView) rootview .findViewById(R.id.trd_day_week_ttl_hr3);
		frt_day_week_ttl_hr4 = (TextView) rootview .findViewById(R.id.frt_day_week_ttl_hr4);
		fft_day_week_ttl_hr5 = (TextView) rootview .findViewById(R.id.fft_day_week_ttl_hr5);
		sxt_day_week_ttl_hr6 = (TextView) rootview .findViewById(R.id.sxt_day_week_ttl_hr6);
		svn_day_week_ttl_hr7 = (TextView) rootview .findViewById(R.id.svn_day_week_ttl_hr7);

		fst_day_week_dy_ttl1 = (TextView) rootview .findViewById(R.id.fst_day_week_dy_ttl1);
		snd_day_week_dy_ttl2 = (TextView) rootview .findViewById(R.id.snd_day_week_dy_ttl2);
		trd_day_week_dy_ttl3 = (TextView) rootview .findViewById(R.id.trd_day_week_dy_ttl3);
		frt_day_week_dy_ttl4 = (TextView) rootview .findViewById(R.id.frt_day_week_dy_ttl4);
		fft_day_week_dy_ttl5 = (TextView) rootview .findViewById(R.id.fft_day_week_dy_ttl5);
		sxt_day_week_dy_ttl6 = (TextView) rootview .findViewById(R.id.sxt_day_week_dy_ttl6);
		svn_day_week_dy_ttl7 = (TextView) rootview .findViewById(R.id.svn_day_week_dy_ttl7);

		fst_day_week_date1 = (TextView) rootview .findViewById(R.id.fst_day_week_date1);
		snd_day_week_date2 = (TextView) rootview .findViewById(R.id.snd_day_week_date2);
		trd_day_week_date3 = (TextView) rootview .findViewById(R.id.trd_day_week_date3);
		frt_day_week_date4 = (TextView) rootview .findViewById(R.id.frt_day_week_date4);
		fft_day_week_date5 = (TextView) rootview .findViewById(R.id.fft_day_week_date5);
		sxt_day_week_date6 = (TextView) rootview .findViewById(R.id.sxt_day_week_date6);
		svn_day_week_date7 = (TextView) rootview .findViewById(R.id.svn_day_week_date7);

		fst_day_week_name1 = (TextView) rootview .findViewById(R.id.fst_day_week_name1);
		snd_day_week_name2 = (TextView) rootview .findViewById(R.id.snd_day_week_name2);
		trd_day_week_name3 = (TextView) rootview .findViewById(R.id.trd_day_week_name3);
		frt_day_week_name4 = (TextView) rootview .findViewById(R.id.frt_day_week_name4);
		fft_day_week_name5 = (TextView) rootview .findViewById(R.id.fft_day_week_name5);
		sxt_day_week_name6 = (TextView) rootview .findViewById(R.id.sxt_day_week_name6);
		svn_day_week_name7 = (TextView) rootview .findViewById(R.id.svn_day_week_name7);

		fst_day_week_plus1 = (TextView) rootview .findViewById(R.id.fst_day_week_plus1);
		snd_day_week_plus2 = (TextView) rootview .findViewById(R.id.snd_day_week_plus2);
		trd_day_week_plus3 = (TextView) rootview .findViewById(R.id.trd_day_week_plus3);
		frt_day_week_plus4 = (TextView) rootview .findViewById(R.id.frt_day_week_plus4);
		fft_day_week_plus5 = (TextView) rootview .findViewById(R.id.fft_day_week_plus5);
		sxt_day_week_plus6 = (TextView) rootview .findViewById(R.id.sxt_day_week_plus6);
		svn_day_week_plus7 = (TextView) rootview .findViewById(R.id.svn_day_week_plus7);

		fst_day_week_np_button1 = (Button) rootview .findViewById(R.id.fst_day_week_np_button1);
		snd_day_week_np_button2 = (Button) rootview .findViewById(R.id.snd_day_week_np_button2);
		trd_day_week_np_button3 = (Button) rootview .findViewById(R.id.trd_day_week_np_button3);
		frt_day_week_np_button4 = (Button) rootview .findViewById(R.id.frt_day_week_np_button4);
		fft_day_week_np_button5 = (Button) rootview .findViewById(R.id.fft_day_week_np_button5);
		sxt_day_week_np_button6 = (Button) rootview .findViewById(R.id.sxt_day_week_np_button6);
		svn_day_week_np_button7 = (Button) rootview .findViewById(R.id.svn_day_week_np_button7);
		
		fst_day_week_np_button1.setOnClickListener(this);
		snd_day_week_np_button2.setOnClickListener(this);
		trd_day_week_np_button3.setOnClickListener(this);
		frt_day_week_np_button4.setOnClickListener(this);
		fft_day_week_np_button5.setOnClickListener(this);
		sxt_day_week_np_button6.setOnClickListener(this);
		svn_day_week_np_button7.setOnClickListener(this);

		fst_day_week_np_lay1 = (RelativeLayout) rootview .findViewById(R.id.fst_day_week_np_lay1);
		snd_day_week_np_lay2 = (RelativeLayout) rootview .findViewById(R.id.snd_day_week_np_lay2);
		trd_day_week_np_lay3 = (RelativeLayout) rootview .findViewById(R.id.trd_day_week_np_lay3);
		frt_day_week_np_lay4 = (RelativeLayout) rootview .findViewById(R.id.frt_day_week_np_lay4);
		fft_day_week_np_lay5 = (RelativeLayout) rootview .findViewById(R.id.fft_day_week_np_lay5);
		sxt_day_week_np_lay6 = (RelativeLayout) rootview .findViewById(R.id.sxt_day_week_np_lay6);
		svn_day_week_np_lay7 = (RelativeLayout) rootview .findViewById(R.id.svn_day_week_np_lay7);
		
		fst_day_week_np_lay1.setOnClickListener(this);
		snd_day_week_np_lay2.setOnClickListener(this);
		trd_day_week_np_lay3.setOnClickListener(this);
		frt_day_week_np_lay4.setOnClickListener(this);
		fft_day_week_np_lay5.setOnClickListener(this);
		sxt_day_week_np_lay6.setOnClickListener(this);
		svn_day_week_np_lay7.setOnClickListener(this);

		linear_lay_week1 = (LinearLayout) rootview .findViewById(R.id.linear_lay_week1);
		linear_lay_week2 = (LinearLayout) rootview .findViewById(R.id.linear_lay_week2);
		linear_lay_week3 = (LinearLayout) rootview .findViewById(R.id.linear_lay_week3);
		linear_lay_week4 = (LinearLayout) rootview .findViewById(R.id.linear_lay_week4);
		linear_lay_week5 = (LinearLayout) rootview .findViewById(R.id.linear_lay_week5);
		linear_lay_week6 = (LinearLayout) rootview .findViewById(R.id.linear_lay_week6);
		linear_lay_week7 = (LinearLayout) rootview .findViewById(R.id.linear_lay_week7);
		
		fst_day_week_str_tm1.setOnClickListener(this);
		snd_day_week_str_tm2.setOnClickListener(this);
		trd_day_week_str_tm3.setOnClickListener(this);
		frt_day_week_str_tm4.setOnClickListener(this);
		fft_day_week_str_tm5.setOnClickListener(this);
		sxt_day_week_str_tm6.setOnClickListener(this);
		svn_day_week_str_tm7.setOnClickListener(this);
		
		fst_day_week_brk_tm1.setOnClickListener(this);
		snd_day_week_brk_tm2.setOnClickListener(this);
		trd_day_week_brk_tm3.setOnClickListener(this);
		frt_day_week_brk_tm4.setOnClickListener(this);
		fft_day_week_brk_tm5.setOnClickListener(this);
		sxt_day_week_brk_tm6.setOnClickListener(this);
		svn_day_week_brk_tm7.setOnClickListener(this);
		
		fst_day_week_fns_tm1.setOnClickListener(this);
		snd_day_week_fns_tm2.setOnClickListener(this);
		trd_day_week_fns_tm3.setOnClickListener(this);
		frt_day_week_fns_tm4.setOnClickListener(this);
		fft_day_week_fns_tm5.setOnClickListener(this);
		sxt_day_week_fns_tm6.setOnClickListener(this);
		svn_day_week_fns_tm7.setOnClickListener(this);
		
		fst_day_week_plus1.setOnClickListener(this);
		snd_day_week_plus2.setOnClickListener(this);
		trd_day_week_plus3.setOnClickListener(this);
		frt_day_week_plus4.setOnClickListener(this);
		fft_day_week_plus5.setOnClickListener(this);
		sxt_day_week_plus6.setOnClickListener(this);
		svn_day_week_plus7.setOnClickListener(this);
		
		//day_of_week_list_text_view.clear();
		//selected_date
		fst_day_week_date1.setText(""+((Page3.selected_date*7)+1));
		snd_day_week_date2.setText(""+((Page3.selected_date*7)+2));
		trd_day_week_date3.setText(""+((Page3.selected_date*7)+3));
		frt_day_week_date4.setText(""+((Page3.selected_date*7)+4));
		fft_day_week_date5.setText(""+((Page3.selected_date*7)+5));
		sxt_day_week_date6.setText(""+((Page3.selected_date*7)+6));
		svn_day_week_date7.setText(""+((Page3.selected_date*7)+7));
		
		fst_day_week_name1.setText(FindWeekOfDay(Page3.selected_year+"-"+Page3.selected_month+"-"+((Page3.selected_date*7)+1)));
		snd_day_week_name2.setText(FindWeekOfDay(Page3.selected_year+"-"+Page3.selected_month+"-"+((Page3.selected_date*7)+2)));
		trd_day_week_name3.setText(FindWeekOfDay(Page3.selected_year+"-"+Page3.selected_month+"-"+((Page3.selected_date*7)+3)));
		frt_day_week_name4.setText(FindWeekOfDay(Page3.selected_year+"-"+Page3.selected_month+"-"+((Page3.selected_date*7)+4)));
		fft_day_week_name5.setText(FindWeekOfDay(Page3.selected_year+"-"+Page3.selected_month+"-"+((Page3.selected_date*7)+5)));
		sxt_day_week_name6.setText(FindWeekOfDay(Page3.selected_year+"-"+Page3.selected_month+"-"+((Page3.selected_date*7)+6)));
		svn_day_week_name7.setText(FindWeekOfDay(Page3.selected_year+"-"+Page3.selected_month+"-"+((Page3.selected_date*7)+7)));
		
		weekly_total_working_time = (TextView) rootview .findViewById(R.id.weekly_total_working_hour);
		weekly_total_earn = (TextView) rootview .findViewById(R.id.weekly_total_earn);
		
		AddingToArrayList();
		
		if (Page3.selected_date > 3) {
			Log.v("", "");
			if (Page3.how_many_days_in_this_month == 29) {
				linear_lay_week1.setVisibility(View.VISIBLE);
				linear_lay_week2.setVisibility(View.GONE);
				linear_lay_week3.setVisibility(View.GONE);
				linear_lay_week4.setVisibility(View.GONE);
				linear_lay_week5.setVisibility(View.GONE);
				linear_lay_week6.setVisibility(View.GONE);
				linear_lay_week7.setVisibility(View.GONE);
				
				SetFromDataBase(first_week_array_list_text_view, ""+((Page3.selected_date*7)+1), Page3.selected_year, Page3.selected_month);
				//SetFromDataBase(second_week_array_list_text_view, ""+((Page3.selected_date*7)+2), Page3.selected_year, Page3.selected_month);
				//SetFromDataBase(third_week_array_list_text_view, ""+((Page3.selected_date*7)+3), Page3.selected_year, Page3.selected_month);
				//SetFromDataBase(fourth_week_array_list_text_view, ""+((Page3.selected_date*7)+4), Page3.selected_year, Page3.selected_month);
				//SetFromDataBase(fifth_week_array_list_text_view, ""+((Page3.selected_date*7)+5), Page3.selected_year, Page3.selected_month);
				//SetFromDataBase(sixth_week_array_list_text_view, ""+((Page3.selected_date*7)+6), Page3.selected_year, Page3.selected_month);
				//SetFromDataBase(seventh_week_array_list_text_view, ""+((Page3.selected_date*7)+7), Page3.selected_year, Page3.selected_month);
				
			} else if (Page3.how_many_days_in_this_month == 30) {
				linear_lay_week1.setVisibility(View.VISIBLE);
				linear_lay_week2.setVisibility(View.VISIBLE);
				linear_lay_week3.setVisibility(View.GONE);
				linear_lay_week4.setVisibility(View.GONE);
				linear_lay_week5.setVisibility(View.GONE);
				linear_lay_week6.setVisibility(View.GONE);
				linear_lay_week7.setVisibility(View.GONE);
				
				SetFromDataBase(first_week_array_list_text_view, ""+((Page3.selected_date*7)+1), Page3.selected_year, Page3.selected_month);
				SetFromDataBase(second_week_array_list_text_view, ""+((Page3.selected_date*7)+2), Page3.selected_year, Page3.selected_month);
				//SetFromDataBase(third_week_array_list_text_view, ""+((Page3.selected_date*7)+3), Page3.selected_year, Page3.selected_month);
				//SetFromDataBase(fourth_week_array_list_text_view, ""+((Page3.selected_date*7)+4), Page3.selected_year, Page3.selected_month);
				//SetFromDataBase(fifth_week_array_list_text_view, ""+((Page3.selected_date*7)+5), Page3.selected_year, Page3.selected_month);
				//SetFromDataBase(sixth_week_array_list_text_view, ""+((Page3.selected_date*7)+6), Page3.selected_year, Page3.selected_month);
				//SetFromDataBase(seventh_week_array_list_text_view, ""+((Page3.selected_date*7)+7), Page3.selected_year, Page3.selected_month);
				
			} else if (Page3.how_many_days_in_this_month == 31) {
				linear_lay_week1.setVisibility(View.VISIBLE);
				linear_lay_week2.setVisibility(View.VISIBLE);
				linear_lay_week3.setVisibility(View.VISIBLE);
				linear_lay_week4.setVisibility(View.GONE);
				linear_lay_week5.setVisibility(View.GONE);
				linear_lay_week6.setVisibility(View.GONE);
				linear_lay_week7.setVisibility(View.GONE);
				
				SetFromDataBase(first_week_array_list_text_view, ""+((Page3.selected_date*7)+1), Page3.selected_year, Page3.selected_month);
				SetFromDataBase(second_week_array_list_text_view, ""+((Page3.selected_date*7)+2), Page3.selected_year, Page3.selected_month);
				SetFromDataBase(third_week_array_list_text_view, ""+((Page3.selected_date*7)+3), Page3.selected_year, Page3.selected_month);
				//SetFromDataBase(fourth_week_array_list_text_view, ""+((Page3.selected_date*7)+4), Page3.selected_year, Page3.selected_month);
				//SetFromDataBase(fifth_week_array_list_text_view, ""+((Page3.selected_date*7)+5), Page3.selected_year, Page3.selected_month);
				//SetFromDataBase(sixth_week_array_list_text_view, ""+((Page3.selected_date*7)+6), Page3.selected_year, Page3.selected_month);
				//SetFromDataBase(seventh_week_array_list_text_view, ""+((Page3.selected_date*7)+7), Page3.selected_year, Page3.selected_month);
				
			}
		} else {
			SetFromDataBase(first_week_array_list_text_view, ""+((Page3.selected_date*7)+1), Page3.selected_year, Page3.selected_month);
			SetFromDataBase(second_week_array_list_text_view, ""+((Page3.selected_date*7)+2), Page3.selected_year, Page3.selected_month);
			SetFromDataBase(third_week_array_list_text_view, ""+((Page3.selected_date*7)+3), Page3.selected_year, Page3.selected_month);
			SetFromDataBase(fourth_week_array_list_text_view, ""+((Page3.selected_date*7)+4), Page3.selected_year, Page3.selected_month);
			SetFromDataBase(fifth_week_array_list_text_view, ""+((Page3.selected_date*7)+5), Page3.selected_year, Page3.selected_month);
			SetFromDataBase(sixth_week_array_list_text_view, ""+((Page3.selected_date*7)+6), Page3.selected_year, Page3.selected_month);
			SetFromDataBase(seventh_week_array_list_text_view, ""+((Page3.selected_date*7)+7), Page3.selected_year, Page3.selected_month);
		}
		
		ShowTotal();
		ShowPremium();
	}
	
	int counter = 0;
	
	public void SetFromDataBase(ArrayList<TextView> set_data_array_list, String DayOfTheMonth, String Year, String Month) {
		db.open();
		Cursor GetDataCursor = db.getWeeklyCalculation(DayOfTheMonth, Year, Month);
		if (GetDataCursor.getCount() == 0) {
			int temp_date = Integer.parseInt(DayOfTheMonth);
			temp_date = temp_date - 7;
			while (temp_date > 0) {
				Log.v("temp_date", "temp_date = "+temp_date);
				GetDataCursor = db.getWeeklyCalculation(temp_date+"", Year, Month);
				if (GetDataCursor.getCount() > 0) {
					break;
				}
				temp_date = temp_date - 7;
			}
		}
		counter = counter + GetDataCursor.getCount();
		Log.v("", ""+counter);
		int field_num = 0;
		while (GetDataCursor.moveToNext()) {
			
			String StarTime = GetDataCursor.getString(GetDataCursor.getColumnIndex("StarTime"));
			String BreakTime = GetDataCursor.getString(GetDataCursor.getColumnIndex("BreakTime"));
			String FinishTime = GetDataCursor.getString(GetDataCursor.getColumnIndex("FinishTime"));
			String TotalWorkingHourOfDay = GetDataCursor.getString(GetDataCursor.getColumnIndex("TotalWorkingHourOfDay"));
			String Ammount;
			
			if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
				Ammount = GetDataCursor.getString(GetDataCursor.getColumnIndex("AmmountInPound"));
			} else {
				Ammount = GetDataCursor.getString(GetDataCursor.getColumnIndex("AmmountInDollar"));
			}
			
			if (!(GetDataCursor.getString(GetDataCursor.getColumnIndex("FinishTime")).equalsIgnoreCase("H") 
					|| GetDataCursor.getString(GetDataCursor.getColumnIndex("FinishTime")).equalsIgnoreCase("D/O"))) {

				String [] start_time_part = GetDataCursor.getString(GetDataCursor.getColumnIndex("StarTime")).split(":");
				String [] break_time_part = GetDataCursor.getString(GetDataCursor.getColumnIndex("BreakTime")).split(":");
				String [] finish_time_part = GetDataCursor.getString(GetDataCursor.getColumnIndex("FinishTime")).split(":");
				
				int total_working_hour = 0;
				int total_working_min = 0;
				
				if (toggle_button.isChecked()) {
					total_working_hour = Integer.parseInt(finish_time_part[0]) -Integer.parseInt(start_time_part[0]);
					total_working_min = Integer.parseInt(finish_time_part[1]) -Integer.parseInt(start_time_part[1]);
				} else {
					total_working_hour = Integer.parseInt(finish_time_part[0])
							-(Integer.parseInt(start_time_part[0])+Integer.parseInt(break_time_part[0]));
					total_working_min = Integer.parseInt(finish_time_part[1])
							-(Integer.parseInt(start_time_part[1])+Integer.parseInt(break_time_part[1]));
				}
				
				if (total_working_hour < 0) {
					total_working_hour = total_working_hour+24;
				}
				
				if (total_working_min < 0) {
					total_working_min = 60 + total_working_min;
					total_working_hour = total_working_hour - 1;
				}
				
				if (total_working_hour < 10 && total_working_min == 0) {
					TotalWorkingHourOfDay = "0"+total_working_hour+":"+"0"+total_working_min;
				} else if (total_working_hour < 10) {
					TotalWorkingHourOfDay = "0"+total_working_hour+":"+total_working_min;
				} else if (total_working_min == 0) {
					TotalWorkingHourOfDay = total_working_hour+":"+"0"+total_working_min;
				} else {
					TotalWorkingHourOfDay = total_working_hour+":"+total_working_min;
				}
				
				float hourly_rate = Float.parseFloat(GetDataCursor.getString(GetDataCursor.getColumnIndex("HourlyRate")));
				double double_ammount = (double)(total_working_hour*hourly_rate)+
						(double)((total_working_min*hourly_rate)/60);
				
				if (GetDataCursor.getString(GetDataCursor.getColumnIndex("Currency")).equals("pound")
						&& !MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
					
					double_ammount = (double)((total_working_hour*hourly_rate)+
						(double)((total_working_min*hourly_rate)/60))*
						MainActivity.mPreferences.getFloat("pound_to_dollar", 0);
				
				} else if (GetDataCursor.getString(GetDataCursor.getColumnIndex("Currency")).equals("dollar")
						&& MainActivity.mPreferences.getString("currency", "").equals("currency £")) {

					double_ammount = (double)((total_working_hour*hourly_rate)+
						(double)((total_working_min*hourly_rate)/60))*
						MainActivity.mPreferences.getFloat("dollar_to_pound", 0);
				}
				
				Ammount = ""+String.format("%.2f", double_ammount);
				/*
				
				if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
					Ammount = GetDataCursor.getString(GetDataCursor.getColumnIndex("AmmountInPound"));
				} else {
					Ammount = GetDataCursor.getString(GetDataCursor.getColumnIndex("AmmountInDollar"));
				}
				
				Ammount = (double)(total_working_hour*Float.parseFloat((hourly_rate_edit_text.getText().toString())))+
						(double)((total_working_min*Float.parseFloat((hourly_rate_edit_text.getText().toString())))/60);
				Log.v("TotalWorkingHourOfDay", "TotalWorkingHourOfDay = "+TotalWorkingHourOfDay);*/
			}
			
			

			String NightPremium = GetDataCursor.getString(GetDataCursor.getColumnIndex("NightPremium"));
			//Log.v("NightPremium", "NightPremium = "+NightPremium);
			String data_field = StarTime+","+BreakTime+","+FinishTime+","+TotalWorkingHourOfDay+","+Ammount+","+NightPremium;
			
			Log.v("data_field", "data_field = "+data_field);
			
			field_num++;
			if (field_num > 1) {
				int week_day_number = Integer.parseInt(DayOfTheMonth) - 1;
				if (week_day_number != 0) {
					week_day_number = week_day_number%7;
				}
				week_day_number = week_day_number+1;
				if (week_day_number == 1) {
					AddDynamicField(linear_lay_week1, week_day_number, data_field, true, 
							GetDataCursor.getString(GetDataCursor.getColumnIndex("InsertId")));
				} else if (week_day_number == 2) {
					AddDynamicField(linear_lay_week2, week_day_number, data_field, true, 
							GetDataCursor.getString(GetDataCursor.getColumnIndex("InsertId")));
				}  else if (week_day_number == 3) {
					AddDynamicField(linear_lay_week3, week_day_number, data_field, true, 
							GetDataCursor.getString(GetDataCursor.getColumnIndex("InsertId")));
				}  else if (week_day_number == 4) {
					AddDynamicField(linear_lay_week4, week_day_number, data_field, true, 
							GetDataCursor.getString(GetDataCursor.getColumnIndex("InsertId")));
				}  else if (week_day_number == 5) {
					AddDynamicField(linear_lay_week5, week_day_number, data_field, true, 
							GetDataCursor.getString(GetDataCursor.getColumnIndex("InsertId")));
				}  else if (week_day_number == 6) {
					AddDynamicField(linear_lay_week6, week_day_number, data_field, true, 
							GetDataCursor.getString(GetDataCursor.getColumnIndex("InsertId")));
				}  else if (week_day_number == 7) {
					AddDynamicField(linear_lay_week7, week_day_number, data_field, true, 
							GetDataCursor.getString(GetDataCursor.getColumnIndex("InsertId")));
				}
			} else {
				set_data_array_list.get(0).setText(StarTime);
				set_data_array_list.get(1).setText(BreakTime);
				set_data_array_list.get(2).setText(FinishTime);
				set_data_array_list.get(3).setText(TotalWorkingHourOfDay);
				set_data_array_list.get(4).setText(Ammount);
				set_data_array_list.get(8).setText(GetDataCursor.getString(GetDataCursor.getColumnIndex("InsertId")));
				Log.v("InsertId", "InsertId = "+GetDataCursor.getString(GetDataCursor.getColumnIndex("InsertId")));
				int week_day_number = Integer.parseInt(DayOfTheMonth) - 1;
				if (week_day_number != 0) {
					week_day_number = week_day_number%7;
				}
				week_day_number = week_day_number+1;
				if (week_day_number == 1) {
					fst_day_week_np_button1.setSelected(Boolean.parseBoolean(NightPremium));
				} else if (week_day_number == 2) {
					snd_day_week_np_button2.setSelected(Boolean.parseBoolean(NightPremium));
				}  else if (week_day_number == 3) {
					trd_day_week_np_button3.setSelected(Boolean.parseBoolean(NightPremium));
				}  else if (week_day_number == 4) {
					frt_day_week_np_button4.setSelected(Boolean.parseBoolean(NightPremium));
				}  else if (week_day_number == 5) {
					fft_day_week_np_button5.setSelected(Boolean.parseBoolean(NightPremium));
				}  else if (week_day_number == 6) {
					sxt_day_week_np_button6.setSelected(Boolean.parseBoolean(NightPremium));
				}  else if (week_day_number == 7) {
					svn_day_week_np_button7.setSelected(Boolean.parseBoolean(NightPremium));
				}
			}
		}
		GetDataCursor.close();
		db.close();
	}
	
	@SuppressLint("SimpleDateFormat")
	public String FindWeekOfDay(String Date){
		String WeekOfDay = "";
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MMMM-dd");
		//SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
		Date d1 = new Date();
		try {
			d1 = sdf1.parse(Date);
			sdf1 = new SimpleDateFormat("EE");
			WeekOfDay = sdf1.format(d1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			WeekOfDay = "null";
		}
		
		return WeekOfDay;
	}
	
	public void AddingToArrayList() {
		first_week_array_list_text_view.clear();
		second_week_array_list_text_view.clear();
		third_week_array_list_text_view.clear();
		fourth_week_array_list_text_view.clear();
		fifth_week_array_list_text_view.clear();
		sixth_week_array_list_text_view.clear();
		seventh_week_array_list_text_view.clear();

		TextView NP = new TextView(getActivity());
		NP.setText("false");
		
		TextView InsertId = new TextView(getActivity());
		InsertId.setText("0");
		
		first_week_array_list_text_view.add(fst_day_week_str_tm1);
		first_week_array_list_text_view.add(fst_day_week_brk_tm1);
		first_week_array_list_text_view.add(fst_day_week_fns_tm1);
		first_week_array_list_text_view.add(fst_day_week_ttl_hr1);
		first_week_array_list_text_view.add(fst_day_week_dy_ttl1);
		first_week_array_list_text_view.add(fst_day_week_date1);
		first_week_array_list_text_view.add(fst_day_week_name1);
		first_week_array_list_text_view.add(NP);
		first_week_array_list_text_view.add(InsertId);

		second_week_array_list_text_view.add(snd_day_week_str_tm2);
		second_week_array_list_text_view.add(snd_day_week_brk_tm2);
		second_week_array_list_text_view.add(snd_day_week_fns_tm2);
		second_week_array_list_text_view.add(snd_day_week_ttl_hr2);
		second_week_array_list_text_view.add(snd_day_week_dy_ttl2);
		second_week_array_list_text_view.add(snd_day_week_date2);
		second_week_array_list_text_view.add(snd_day_week_name2);
		second_week_array_list_text_view.add(NP);
		second_week_array_list_text_view.add(InsertId);

		third_week_array_list_text_view.add(trd_day_week_str_tm3);
		third_week_array_list_text_view.add(trd_day_week_brk_tm3);
		third_week_array_list_text_view.add(trd_day_week_fns_tm3);
		third_week_array_list_text_view.add(trd_day_week_ttl_hr3);
		third_week_array_list_text_view.add(trd_day_week_dy_ttl3);
		third_week_array_list_text_view.add(trd_day_week_date3);
		third_week_array_list_text_view.add(trd_day_week_name3);
		third_week_array_list_text_view.add(NP);
		third_week_array_list_text_view.add(InsertId);
		
		fourth_week_array_list_text_view.add(frt_day_week_str_tm4);
		fourth_week_array_list_text_view.add(frt_day_week_brk_tm4);
		fourth_week_array_list_text_view.add(frt_day_week_fns_tm4);
		fourth_week_array_list_text_view.add(frt_day_week_ttl_hr4);
		fourth_week_array_list_text_view.add(frt_day_week_dy_ttl4);
		fourth_week_array_list_text_view.add(frt_day_week_date4);
		fourth_week_array_list_text_view.add(frt_day_week_name4);
		fourth_week_array_list_text_view.add(NP);
		fourth_week_array_list_text_view.add(InsertId);

		fifth_week_array_list_text_view.add(fft_day_week_str_tm5);
		fifth_week_array_list_text_view.add(fft_day_week_brk_tm5);
		fifth_week_array_list_text_view.add(fft_day_week_fns_tm5);
		fifth_week_array_list_text_view.add(fft_day_week_ttl_hr5);
		fifth_week_array_list_text_view.add(fft_day_week_dy_ttl5);
		fifth_week_array_list_text_view.add(fft_day_week_date5);
		fifth_week_array_list_text_view.add(fft_day_week_name5);
		fifth_week_array_list_text_view.add(NP);
		fifth_week_array_list_text_view.add(InsertId);

		sixth_week_array_list_text_view.add(sxt_day_week_str_tm6);
		sixth_week_array_list_text_view.add(sxt_day_week_brk_tm6);
		sixth_week_array_list_text_view.add(sxt_day_week_fns_tm6);
		sixth_week_array_list_text_view.add(sxt_day_week_ttl_hr6);
		sixth_week_array_list_text_view.add(sxt_day_week_dy_ttl6);
		sixth_week_array_list_text_view.add(sxt_day_week_date6);
		sixth_week_array_list_text_view.add(sxt_day_week_name6);
		sixth_week_array_list_text_view.add(NP);
		sixth_week_array_list_text_view.add(InsertId);

		seventh_week_array_list_text_view.add(svn_day_week_str_tm7);
		seventh_week_array_list_text_view.add(svn_day_week_brk_tm7);
		seventh_week_array_list_text_view.add(svn_day_week_fns_tm7);
		seventh_week_array_list_text_view.add(svn_day_week_ttl_hr7);
		seventh_week_array_list_text_view.add(svn_day_week_dy_ttl7);
		seventh_week_array_list_text_view.add(svn_day_week_date7);
		seventh_week_array_list_text_view.add(svn_day_week_name7);
		seventh_week_array_list_text_view.add(NP);
		seventh_week_array_list_text_view.add(InsertId);
		
		start_time_array_list_text_view.clear();
		break_time_array_list_text_view.clear();
		finish_time_array_list_text_view.clear();
		total_hour_array_list_text_view.clear();
		total_earn_array_list_text_view.clear();

		start_time_array_list_text_view.add(fst_day_week_str_tm1);
		start_time_array_list_text_view.add(snd_day_week_str_tm2);
		start_time_array_list_text_view.add(trd_day_week_str_tm3);
		start_time_array_list_text_view.add(frt_day_week_str_tm4);
		start_time_array_list_text_view.add(fft_day_week_str_tm5);
		start_time_array_list_text_view.add(sxt_day_week_str_tm6);
		start_time_array_list_text_view.add(svn_day_week_str_tm7);

		break_time_array_list_text_view.add(fst_day_week_brk_tm1);
		break_time_array_list_text_view.add(snd_day_week_brk_tm2);
		break_time_array_list_text_view.add(trd_day_week_brk_tm3);
		break_time_array_list_text_view.add(frt_day_week_brk_tm4);
		break_time_array_list_text_view.add(fft_day_week_brk_tm5);
		break_time_array_list_text_view.add(sxt_day_week_brk_tm6);
		break_time_array_list_text_view.add(svn_day_week_brk_tm7);

		finish_time_array_list_text_view.add(fst_day_week_fns_tm1);
		finish_time_array_list_text_view.add(snd_day_week_fns_tm2);
		finish_time_array_list_text_view.add(trd_day_week_fns_tm3);
		finish_time_array_list_text_view.add(frt_day_week_fns_tm4);
		finish_time_array_list_text_view.add(fft_day_week_fns_tm5);
		finish_time_array_list_text_view.add(sxt_day_week_fns_tm6);
		finish_time_array_list_text_view.add(svn_day_week_fns_tm7);

		total_hour_array_list_text_view.add(fst_day_week_ttl_hr1);
		total_hour_array_list_text_view.add(snd_day_week_ttl_hr2);
		total_hour_array_list_text_view.add(trd_day_week_ttl_hr3);
		total_hour_array_list_text_view.add(frt_day_week_ttl_hr4);
		total_hour_array_list_text_view.add(fft_day_week_ttl_hr5);
		total_hour_array_list_text_view.add(sxt_day_week_ttl_hr6);
		total_hour_array_list_text_view.add(svn_day_week_ttl_hr7);

		total_earn_array_list_text_view.add(fst_day_week_dy_ttl1);
		total_earn_array_list_text_view.add(snd_day_week_dy_ttl2);
		total_earn_array_list_text_view.add(trd_day_week_dy_ttl3);
		total_earn_array_list_text_view.add(frt_day_week_dy_ttl4);
		total_earn_array_list_text_view.add(fft_day_week_dy_ttl5);
		total_earn_array_list_text_view.add(sxt_day_week_dy_ttl6);
		total_earn_array_list_text_view.add(svn_day_week_dy_ttl7);
	}

	int week_1_insertionId = 0;
	int week_2_insertionId = 0;
	int week_3_insertionId = 0;
	int week_4_insertionId = 0;
	int week_5_insertionId = 0;
	int week_6_insertionId = 0;
	int week_7_insertionId = 0;

	@Override
	public void onClick(View v) {
		if (!hourly_rate_edit_text.getText().toString().equals("")) {
			switch (v.getId()) {
			case R.id.fst_day_week_str_tm1:
				ShowTimePicker(fst_day_week_str_tm1, 1, first_week_array_list_text_view);
				break;
	
			case R.id.snd_day_week_str_tm2:
				ShowTimePicker(snd_day_week_str_tm2, 2, second_week_array_list_text_view);
				break;
	 
			case R.id.trd_day_week_str_tm3:
				ShowTimePicker(trd_day_week_str_tm3, 3, third_week_array_list_text_view);
				break;
	
			case R.id.frt_day_week_str_tm4:
				ShowTimePicker(frt_day_week_str_tm4, 4, fourth_week_array_list_text_view);
				break;
	
			case R.id.fft_day_week_str_tm5:
				ShowTimePicker(fft_day_week_str_tm5, 5, fifth_week_array_list_text_view);
				break;
	
			case R.id.sxt_day_week_str_tm6:
				ShowTimePicker(sxt_day_week_str_tm6, 6, sixth_week_array_list_text_view);
				break;
	
			case R.id.svn_day_week_str_tm7:
				ShowTimePicker(svn_day_week_str_tm7, 7, seventh_week_array_list_text_view);
				break;
	
			case R.id.fst_day_week_brk_tm1:
				ShowTimePicker(fst_day_week_brk_tm1, 1, first_week_array_list_text_view);
				break;
	
			case R.id.snd_day_week_brk_tm2:
				ShowTimePicker(snd_day_week_brk_tm2, 2, second_week_array_list_text_view);
				break;
	 
			case R.id.trd_day_week_brk_tm3:
				ShowTimePicker(trd_day_week_brk_tm3, 3, third_week_array_list_text_view);
				break;
	
			case R.id.frt_day_week_brk_tm4:
				ShowTimePicker(frt_day_week_brk_tm4, 4, fourth_week_array_list_text_view);
				break;
	
			case R.id.fft_day_week_brk_tm5:
				ShowTimePicker(fft_day_week_brk_tm5, 5, fifth_week_array_list_text_view);
				break;
	
			case R.id.sxt_day_week_brk_tm6:
				ShowTimePicker(sxt_day_week_brk_tm6, 6, sixth_week_array_list_text_view);
				break;
	
			case R.id.svn_day_week_brk_tm7:
				ShowTimePicker(svn_day_week_brk_tm7, 7, seventh_week_array_list_text_view);
				break;
	
			case R.id.fst_day_week_fns_tm1:
				ShowTimePicker(fst_day_week_fns_tm1, 1, first_week_array_list_text_view);
				break;
	
			case R.id.snd_day_week_fns_tm2:
				ShowTimePicker(snd_day_week_fns_tm2, 2, second_week_array_list_text_view);
				break;
	 
			case R.id.trd_day_week_fns_tm3:
				ShowTimePicker(trd_day_week_fns_tm3, 3, third_week_array_list_text_view);
				break;
	
			case R.id.frt_day_week_fns_tm4:
				ShowTimePicker(frt_day_week_fns_tm4, 4, fourth_week_array_list_text_view);
				break;
	
			case R.id.fft_day_week_fns_tm5:
				ShowTimePicker(fft_day_week_fns_tm5, 5, fifth_week_array_list_text_view);
				break;
	
			case R.id.sxt_day_week_fns_tm6:
				ShowTimePicker(sxt_day_week_fns_tm6, 6, sixth_week_array_list_text_view);
				break;
	
			case R.id.svn_day_week_fns_tm7:
				ShowTimePicker(svn_day_week_fns_tm7, 7, seventh_week_array_list_text_view);
				break;

			case R.id.fst_day_week_plus1:
				if (first_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| first_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						|| !GetTotalFromDB(1, week_1_insertionId)) {
					if (!GetTotalFromDB(1, week_1_insertionId)) {
						Toast.makeText(getActivity(), "Please input first.", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), "Please choose working day.", Toast.LENGTH_SHORT).show();
					}
				} else {
					week_1_insertionId++;
					//first_week_array_list_text_view.get(8).setText(""+week_1_insertionId);
					AddDynamicField(linear_lay_week1, 1, "", false, week_1_insertionId+"");
				}
				break;
	
			case R.id.snd_day_week_plus2:
				if (second_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| second_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						|| !GetTotalFromDB(2, week_2_insertionId)) {
					if (!GetTotalFromDB(2, week_2_insertionId)) {
						Toast.makeText(getActivity(), "Please input first.", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), "Please choose working day.", Toast.LENGTH_SHORT).show();
					}
				} else {
					week_2_insertionId++;
					//second_week_array_list_text_view.get(8).setText(""+week_2_insertionId);
					AddDynamicField(linear_lay_week2, 2, "", false, week_2_insertionId+"");
				}
				break;
	 
			case R.id.trd_day_week_plus3:
				if (third_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| third_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						|| !GetTotalFromDB(3, week_3_insertionId)) {
					if (!GetTotalFromDB(3, week_3_insertionId)) {
						Toast.makeText(getActivity(), "Please input first.", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), "Please choose working day.", Toast.LENGTH_SHORT).show();
					}
				} else {
					week_3_insertionId++;
					//third_week_array_list_text_view.get(8).setText(""+week_3_insertionId);
					AddDynamicField(linear_lay_week3, 3, "", false, week_3_insertionId+"");
				}
				break;
	
			case R.id.frt_day_week_plus4:
				if (fourth_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| fourth_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						|| !GetTotalFromDB(4, week_4_insertionId)) {
					if (!GetTotalFromDB(4, week_4_insertionId)) {
						Toast.makeText(getActivity(), "Please input first.", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), "Please choose working day.", Toast.LENGTH_SHORT).show();
					}
				} else {
					week_4_insertionId++;
					//fourth_week_array_list_text_view.get(8).setText(""+week_4_insertionId);
					AddDynamicField(linear_lay_week4, 4, "", false, week_4_insertionId+"");
				}
				break;
	
			case R.id.fft_day_week_plus5:
				if (fifth_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| fifth_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						|| !GetTotalFromDB(5, week_5_insertionId)) {
					if (!GetTotalFromDB(5, week_5_insertionId)) {
						Toast.makeText(getActivity(), "Please input first.", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), "Please choose working day.", Toast.LENGTH_SHORT).show();
					}
				} else {
					week_5_insertionId++;
					//fifth_week_array_list_text_view.get(8).setText(""+week_5_insertionId);
					AddDynamicField(linear_lay_week5, 5, "", false, week_5_insertionId+"");
				}
				break;
	
			case R.id.sxt_day_week_plus6:
				if (sixth_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| sixth_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						|| !GetTotalFromDB(6, week_6_insertionId)) {
					if (!GetTotalFromDB(6, week_6_insertionId)) {
						Toast.makeText(getActivity(), "Please input first.", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), "Please choose working day.", Toast.LENGTH_SHORT).show();
					}
					week_6_insertionId++;
					//sixth_week_array_list_text_view.get(8).setText(""+week_6_insertionId);
					AddDynamicField(linear_lay_week6, 6, "", false, week_6_insertionId+"");
				}
				break;
	
			case R.id.svn_day_week_plus7:
				if (seventh_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| seventh_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						|| !GetTotalFromDB(7, week_7_insertionId)) {
					if (!GetTotalFromDB(7, week_7_insertionId)) {
						Toast.makeText(getActivity(), "Please input first.", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), "Please choose working day.", Toast.LENGTH_SHORT).show();
					}
				} else {
					week_7_insertionId++;
					//seventh_week_array_list_text_view.get(8).setText(""+week_7_insertionId);
					AddDynamicField(linear_lay_week7, 7, "", false, week_7_insertionId+"");
				}
				break;
				
			//****************************************************************np
			case R.id.fst_day_week_np_lay1:
				if (first_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| first_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						/*|| first_week_array_list_text_view.get(6).getText().toString().equals("Sat")
						|| first_week_array_list_text_view.get(6).getText().toString().equals("Sun")*/) {
					
					Toast.makeText(getActivity(), "Night premium is only applicable for working days.", Toast.LENGTH_LONG).show();
					
				} else {
					setNightPremium(fst_day_week_np_button1, 1);
				}
				break;
	
			case R.id.snd_day_week_np_lay2:
				if (second_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| second_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						/*|| second_week_array_list_text_view.get(6).getText().toString().equals("Sat")
						|| second_week_array_list_text_view.get(6).getText().toString().equals("Sun")*/) {
					
					Toast.makeText(getActivity(), "Night premium is only applicable for working days.", Toast.LENGTH_LONG).show();
					
				} else {
					setNightPremium(snd_day_week_np_button2, 2);
				}
				break;
	 
			case R.id.trd_day_week_np_lay3:
				if (third_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| third_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						/*|| third_week_array_list_text_view.get(6).getText().toString().equals("Sat")
						|| third_week_array_list_text_view.get(6).getText().toString().equals("Sun")*/) {
					
					Toast.makeText(getActivity(), "Night premium is only applicable for working days.", Toast.LENGTH_LONG).show();
					
				} else {
					setNightPremium(trd_day_week_np_button3, 3);
				}
				break;
	
			case R.id.frt_day_week_np_lay4:
				if (fourth_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| fourth_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						/*|| fourth_week_array_list_text_view.get(6).getText().toString().equals("Sat")
						|| fourth_week_array_list_text_view.get(6).getText().toString().equals("Sun")*/) {
					
					Toast.makeText(getActivity(), "Night premium is only applicable for working days.", Toast.LENGTH_LONG).show();
					
					
				} else {
					setNightPremium(frt_day_week_np_button4, 4);
				}
				break;
	
			case R.id.fft_day_week_np_lay5:
				if (fifth_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| fifth_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						/*|| fifth_week_array_list_text_view.get(6).getText().toString().equals("Sat")
						|| fifth_week_array_list_text_view.get(6).getText().toString().equals("Sun")*/) {
					
					Toast.makeText(getActivity(), "Night premium is only applicable for working days.", Toast.LENGTH_LONG).show();
					
					
				} else {
					setNightPremium(fft_day_week_np_button5, 5);
				}
				break;
	
			case R.id.sxt_day_week_np_lay6:
				if (sixth_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| sixth_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						/*|| sixth_week_array_list_text_view.get(6).getText().toString().equals("Sat")
						|| sixth_week_array_list_text_view.get(6).getText().toString().equals("Sun")*/) {
					
					Toast.makeText(getActivity(), "Night premium is only applicable for working days.", Toast.LENGTH_LONG).show();
					
					
				} else {
					setNightPremium(sxt_day_week_np_button6, 6);
				}
				break;
	
			case R.id.svn_day_week_np_lay7:
				if (seventh_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| seventh_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						/*|| seventh_week_array_list_text_view.get(6).getText().toString().equals("Sat")
						|| seventh_week_array_list_text_view.get(6).getText().toString().equals("Sun")*/) {
					
					Toast.makeText(getActivity(), "Night premium is only applicable for working days.", Toast.LENGTH_LONG).show();
					
					
				} else {
					setNightPremium(svn_day_week_np_button7, 7);
				}
				break;

			case R.id.fst_day_week_np_button1:
				if (first_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| first_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						/*|| first_week_array_list_text_view.get(6).getText().toString().equals("Sat")
						|| first_week_array_list_text_view.get(6).getText().toString().equals("Sun")*/) {
					
					Toast.makeText(getActivity(), "Night premium is only applicable for working days.", Toast.LENGTH_LONG).show();
					
					
				} else {
					setNightPremium(fst_day_week_np_button1, 1);
				}
				break;
	
			case R.id.snd_day_week_np_button2:
				if (second_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| second_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						/*|| second_week_array_list_text_view.get(6).getText().toString().equals("Sat")
						|| second_week_array_list_text_view.get(6).getText().toString().equals("Sun")*/) {
					
					Toast.makeText(getActivity(), "Night premium is only applicable for working days.", Toast.LENGTH_LONG).show();
					
					
				} else {
					setNightPremium(snd_day_week_np_button2, 2);
				}
				break;
	 
			case R.id.trd_day_week_np_button3:
				if (third_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| third_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						/*|| third_week_array_list_text_view.get(6).getText().toString().equals("Sat")
						|| third_week_array_list_text_view.get(6).getText().toString().equals("Sun")*/) {
					
					Toast.makeText(getActivity(), "Night premium is only applicable for working days.", Toast.LENGTH_LONG).show();
					
					
				} else {
					setNightPremium(trd_day_week_np_button3, 3);
				}
				break;
	
			case R.id.frt_day_week_np_button4:
				if (fourth_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| fourth_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						/*|| fourth_week_array_list_text_view.get(6).getText().toString().equals("Sat")
						|| fourth_week_array_list_text_view.get(6).getText().toString().equals("Sun")*/) {
					
					Toast.makeText(getActivity(), "Night premium is only applicable for working days.", Toast.LENGTH_LONG).show();
					
				} else {
					setNightPremium(frt_day_week_np_button4, 4);
				}
				break;
	
			case R.id.fft_day_week_np_button5:
				if (fifth_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| fifth_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						/*|| fifth_week_array_list_text_view.get(6).getText().toString().equals("Sat")
						|| fifth_week_array_list_text_view.get(6).getText().toString().equals("Sun")*/) {
					
					Toast.makeText(getActivity(), "Night premium is only applicable for working days.", Toast.LENGTH_LONG).show();
					
				} else {
					setNightPremium(fft_day_week_np_button5, 5);
				}
				break;
	
			case R.id.sxt_day_week_np_button6:
				if (sixth_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| sixth_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						/*|| sixth_week_array_list_text_view.get(6).getText().toString().equals("Sat")
						|| sixth_week_array_list_text_view.get(6).getText().toString().equals("Sun")*/) {
					
					Toast.makeText(getActivity(), "Night premium is only applicable for working days.", Toast.LENGTH_LONG).show();
					
				} else {
					setNightPremium(sxt_day_week_np_button6, 6);
				}
				break;
	
			case R.id.svn_day_week_np_button7:
				if (seventh_week_array_list_text_view.get(1).getText().toString().equals("H")
						|| seventh_week_array_list_text_view.get(1).getText().toString().equals("D/O")
						/*|| seventh_week_array_list_text_view.get(6).getText().toString().equals("Sat")
						|| seventh_week_array_list_text_view.get(6).getText().toString().equals("Sun")*/) {
					
					Toast.makeText(getActivity(), "Night premium is only applicable for working days.", Toast.LENGTH_LONG).show();
					
				} else {
					setNightPremium(svn_day_week_np_button7, 7);
				}
				break;
	
			default:
				break;
			}
		} else {
			Toast.makeText(getActivity(), "Please insert hour rate.", Toast.LENGTH_SHORT).show();
		}
		
		switch (v.getId()) {
		case R.id.satday_picker_button:
			Persentage(satday_picker_button, "sat", null);
			break;
			
		case R.id.sunday_picker_button:
			Persentage(sunday_picker_button, "sun", null);
			break;
			
		/*case R.id.night_picker_button:
			Persentage(night_picker_button, "night");
			break;*/

		default:
			break;
		}
	}
	
	public boolean GetTotalFromDB(int date, int insertId) {
		db = new DataBaseAdapter(getActivity());
		db.open();
		boolean getTotal = db.getDayTotalHour(((Page3.selected_date*7)+date)+"", Page3.selected_year, Page3.selected_month, ""+insertId);
		db.close();
		return getTotal;
	}
	
	public void setNightPremium(Button n_button, int num) {
		if (n_button.isSelected()) {
			n_button.setSelected(false);
		} else {
			n_button.setSelected(true);
		}
		
		Updateable = GetTotalFromDB(num, 0);
		
		if (num == 1) {
			first_week_array_list_text_view.get(7).setText(""+n_button.isSelected());
			SaveToDataBase(first_week_array_list_text_view, Updateable, false);
		} else if (num == 2) {
			second_week_array_list_text_view.get(7).setText(""+n_button.isSelected());
			SaveToDataBase(second_week_array_list_text_view, Updateable, false);
		} else if (num == 3) {
			third_week_array_list_text_view.get(7).setText(""+n_button.isSelected());
			SaveToDataBase(third_week_array_list_text_view, Updateable, false);
		} else if (num == 4) {
			fourth_week_array_list_text_view.get(7).setText(""+n_button.isSelected());
			SaveToDataBase(fourth_week_array_list_text_view, Updateable, false);
		} else if (num == 5) {
			fifth_week_array_list_text_view.get(7).setText(""+n_button.isSelected());
			SaveToDataBase(fifth_week_array_list_text_view, Updateable, false);
		} else if (num == 6) {
			sixth_week_array_list_text_view.get(7).setText(""+n_button.isSelected());
			SaveToDataBase(sixth_week_array_list_text_view, Updateable, false);
		} else if (num == 7) {
			seventh_week_array_list_text_view.get(7).setText(""+n_button.isSelected());
			SaveToDataBase(seventh_week_array_list_text_view, Updateable, false);
		}
		
	}
	
	Dialog dialog_persentage_picker;
	//String sPersentage = "0";
	public void Persentage(final Button b, final String name, EditText night_premium_ammount) {
		if (b != null || night_premium_ammount == null) {
			dialog_persentage_picker = new Dialog(getActivity());
			dialog_persentage_picker.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog_persentage_picker.setContentView(R.layout.dialog_persentage_picker);
			dialog_persentage_picker.getWindow().setBackgroundDrawable(new ColorDrawable(0));
			dialog_persentage_picker.getWindow().setGravity( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
			
			dialog_persentage_picker.show();

			final NumberPicker persentage_picker = (NumberPicker) dialog_persentage_picker.findViewById(R.id.persentage_picker);
			
			persentage_picker.setMaxValue(100);
			persentage_picker.setMinValue(0);
			
			
			dialog_persentage_picker.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss(DialogInterface dialog) {
					// TODO Auto-generated method stub
					b.setText(""+persentage_picker.getValue());
					db = new DataBaseAdapter(getActivity());
					db.open();
					if (name.equals("sat")) {
						db.updateSatPremium(""+persentage_picker.getValue(), Page3.selected_date+"",
								Page3.selected_month, Page3.selected_year);
					} else if (name.equals("sun")) {
						db.updateSunPremium(sunday_picker_button.getText().toString(), 
								Page3.selected_date+"", Page3.selected_month, Page3.selected_year);
					}/* else if (name.equals("night")) {
						db.updateNightPremium(night_picker_button.getText().toString(), 
								Page3.selected_date+"", Page3.selected_month, Page3.selected_year);
					} */
					db.close();
					ShowPremium();
				}
			});
		} else {
			db = new DataBaseAdapter(getActivity());
			db.open();
			
			db.updateNightPremium(night_premium_ammount.getText().toString(), 
					Page3.selected_date+"", Page3.selected_month, Page3.selected_year);
			
			db.close();
			ShowPremium();
		}
	}
	
	public void AddDynamicField(LinearLayout mLayout, final int week_day_number, String data_field, 
			boolean insert_field, final String InsId) {
		View extra_view = getActivity().getLayoutInflater().inflate(R.layout.xyz, mLayout, false);
		
		final ArrayList<TextView> dynamic_week_array_list = new ArrayList<TextView>();
		@SuppressWarnings("unused")
		final TextView plus, date, week, start_time, break_time, finish_time, total_hour, day_total, night_premium, insertId;
		final Button np_button;
		RelativeLayout np_lay;
		
		plus = (TextView) extra_view.findViewById(R.id.plus);
		date = (TextView) extra_view.findViewById(R.id.date);
		week = (TextView) extra_view.findViewById(R.id.week);
		start_time = (TextView) extra_view.findViewById(R.id.start_time);
		break_time = (TextView) extra_view.findViewById(R.id.break_time);
		finish_time = (TextView) extra_view.findViewById(R.id.finish_time);
		total_hour = (TextView) extra_view.findViewById(R.id.total_hour);
		day_total = (TextView) extra_view.findViewById(R.id.day_total);
		
		np_button = (Button) extra_view.findViewById(R.id.np_button);
		np_lay = (RelativeLayout) extra_view.findViewById(R.id.np_lay);
		
		night_premium = new TextView(getActivity());
		insertId =  new TextView(getActivity());
		insertId.setText(InsId);
		
		Log.v("week_day_number", "week_day_number = "+week_day_number);
		
		date.setText(""+((Page3.selected_date*7)+week_day_number));
		week.setText(FindWeekOfDay(Page3.selected_year+"-"+Page3.selected_month+"-"+((Page3.selected_date*7)+week_day_number)));
		
		if (!data_field.equals("")) {
			String [] data_field_part = data_field.split(",");
			start_time.setText(data_field_part[0]);
			break_time.setText(data_field_part[1]);
			finish_time.setText(data_field_part[2]);
			total_hour.setText(data_field_part[3]);
			day_total.setText(data_field_part[4]);
			if (data_field_part[5].equals("true")) {
				np_button.setSelected(true);
			} else {
				np_button.setSelected(false);
			}

			if (week_day_number == 1) {
				week_1_insertionId = Integer.parseInt(InsId);
			} else if (week_day_number == 2) {
				week_2_insertionId = Integer.parseInt(InsId);
			} else if (week_day_number == 3) {
				week_3_insertionId = Integer.parseInt(InsId);
			} else if (week_day_number == 4) {
				week_4_insertionId = Integer.parseInt(InsId);
			} else if (week_day_number == 5) {
				week_5_insertionId = Integer.parseInt(InsId);
			} else if (week_day_number == 6) {
				week_6_insertionId = Integer.parseInt(InsId);
			} else if (week_day_number == 7) {
				week_7_insertionId = Integer.parseInt(InsId);
			}
			
			Log.v("InsId", "InsId = "+InsId);
		}
		
		np_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (np_button.isSelected()) {
					np_button.setSelected(false);
					night_premium.setText("false");
				} else {
					np_button.setSelected(true);
					night_premium.setText("true");
				}
				dynamic_week_array_list.get(7).setText(night_premium.getText().toString());
				Updateable = GetTotalFromDB(week_day_number, Integer.parseInt(InsId));
				SaveToDataBase(dynamic_week_array_list, Updateable, false);
			}
		});
		np_lay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (np_button.isSelected()) {
					np_button.setSelected(false);
					night_premium.setText("false");
				} else {
					np_button.setSelected(true);
					night_premium.setText("true");
				}
				dynamic_week_array_list.get(7).setText(night_premium.getText().toString());
				Updateable = GetTotalFromDB(week_day_number, Integer.parseInt(InsId));
				SaveToDataBase(dynamic_week_array_list, Updateable, false);
			}
		});
		
		dynamic_week_array_list.clear();
		
		dynamic_week_array_list.add(start_time);
		dynamic_week_array_list.add(break_time);
		dynamic_week_array_list.add(finish_time);
		dynamic_week_array_list.add(total_hour);
		dynamic_week_array_list.add(day_total);
		dynamic_week_array_list.add(date);
		dynamic_week_array_list.add(week);
		dynamic_week_array_list.add(night_premium);
		dynamic_week_array_list.add(insertId);

		start_time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!hourly_rate_edit_text.getText().toString().equals("")) {
					DynamiShowTimePicker(start_time, week_day_number, dynamic_week_array_list);
				} else {
					Toast.makeText(getActivity(), "Please insert hour rate.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		break_time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!hourly_rate_edit_text.getText().toString().equals("")) {
					DynamiShowTimePicker(break_time, week_day_number, dynamic_week_array_list);
				} else {
					Toast.makeText(getActivity(), "Please insert hour rate.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		finish_time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!hourly_rate_edit_text.getText().toString().equals("")) {
					DynamiShowTimePicker(finish_time, week_day_number, dynamic_week_array_list);
				} else {
					Toast.makeText(getActivity(), "Please insert hour rate.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		/*Log.v("plus.getId()", "b plus.getId()"+plus.getId());
		plus.setId(1);
		Log.v("plus.getId()", "a plus.getId()"+plus.getId());*/
		if (insert_field) {
			total_hour_array_list_text_view.add(total_hour);
			total_earn_array_list_text_view.add(day_total);
		}
		DynamicShowTotal();
		mLayout.addView(extra_view);
	}
	
	//ArrayList<ArrayList> dynamic_arraylistArray = new ArrayList<ArrayList>();
	
	Dialog dialog_time_picker;
	String [] hour_array = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
			"12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
	
	String [] min_array = {"00", "15", "30", "45"};
	boolean Updateable;

	Button holiday_toggle_button, dayoff_toggle_button;
	LinearLayout holiday_selection_lay, dayoff_selection_lay;
	
	public void ShowTimePicker(final TextView textview, final int WeekNum, final ArrayList<TextView> array_list) {
		dialog_time_picker = new Dialog(getActivity());
		dialog_time_picker.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_time_picker.setContentView(R.layout.dialog_time_picker);
		dialog_time_picker.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		dialog_time_picker.getWindow().setGravity( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		
		dialog_time_picker.show();
		
		// = true;
		
		/*if (array_list.get(4).getText().toString().equals("0.00")
				|| array_list.get(4).getText().toString().equals("00.00")
				|| array_list.get(4).getText().toString().equals("0.0")
				|| array_list.get(4).getText().toString().equals("00.0")) {
			
			Updateable = false;
		}*/
		final ImageView invisible_view = (ImageView) dialog_time_picker.findViewById(R.id.invisible_view);
		
		Updateable = GetTotalFromDB(WeekNum, Integer.parseInt(array_list.get(8).getText().toString()));
		
		Button done_button;
		
		done_button = (Button) dialog_time_picker.findViewById(R.id.done_button);
		
		holiday_toggle_button = (Button) dialog_time_picker.findViewById(R.id.holiday_toggle_button);
		dayoff_toggle_button = (Button) dialog_time_picker.findViewById(R.id.dayoff_toggle_button);

		holiday_selection_lay = (LinearLayout) dialog_time_picker.findViewById(R.id.holiday_selection_lay);
		dayoff_selection_lay = (LinearLayout) dialog_time_picker.findViewById(R.id.dayoff_selection_lay);
		
		//Log.v("t.getText()", "t.getText() "+t.getText());
		final NumberPicker time_picker_hour, time_picker_min;

		time_picker_hour = (NumberPicker) dialog_time_picker.findViewById(R.id.time_picker_hour);
		time_picker_min = (NumberPicker) dialog_time_picker.findViewById(R.id.time_picker_min);

		time_picker_hour.setMaxValue(hour_array.length - 1);
		time_picker_hour.setMinValue(0);
		time_picker_hour.setDisplayedValues(hour_array);
		//time_picker_hour.setValue(hour_array);
		time_picker_hour.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				// TODO Auto-generated method stub
				
			}
		});
		
		time_picker_min.setMaxValue(min_array.length - 1);
		time_picker_min.setMinValue(0);
		time_picker_min.setDisplayedValues(min_array);
		//time_picker_hour.setValue(hour_array);
		time_picker_min.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				// TODO Auto-generated method stub
				
			}
		});
		
		holiday_toggle_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (holiday_toggle_button.isSelected()) {
					holiday_toggle_button.setSelected(false);
				} else {
					holiday_toggle_button.setSelected(true);
					dayoff_toggle_button.setSelected(false);
				}
				InactiveNumberPicker(holiday_toggle_button, dayoff_toggle_button, invisible_view);
			}
		});
		
		dayoff_toggle_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (dayoff_toggle_button.isSelected()) {
					dayoff_toggle_button.setSelected(false);
				} else {
					dayoff_toggle_button.setSelected(true);
					holiday_toggle_button.setSelected(false);
				}
				InactiveNumberPicker(holiday_toggle_button, dayoff_toggle_button, invisible_view);
			}
		});
		
		holiday_selection_lay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (holiday_toggle_button.isSelected()) {
					holiday_toggle_button.setSelected(false);
				} else {
					holiday_toggle_button.setSelected(true);
					dayoff_toggle_button.setSelected(false);
				}
				InactiveNumberPicker(holiday_toggle_button, dayoff_toggle_button, invisible_view);
			}
		});
		
		dayoff_selection_lay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (dayoff_toggle_button.isSelected()) {
					dayoff_toggle_button.setSelected(false);
				} else {
					dayoff_toggle_button.setSelected(true);
					holiday_toggle_button.setSelected(false);
				}
				InactiveNumberPicker(holiday_toggle_button, dayoff_toggle_button, invisible_view);
			}
		});
		
		invisible_view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		done_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Log.v("textview.getId()", "textview.getId() "+textview.getId());
				if (holiday_toggle_button.isSelected()) {
					array_list.get(0).setText("H");
					array_list.get(1).setText("H");
					array_list.get(2).setText("H");
				} else if (dayoff_toggle_button.isSelected()) {
					array_list.get(0).setText("D/O");
					array_list.get(1).setText("D/O");
					array_list.get(2).setText("D/O");
				} else {
					//for (int i = 1; i <= 7; i++) {
					//Log.v("array_list.size()", "array_list.size() " +array_list.size());
						//if (WeekNum == i) {
					if (textview.getText().toString().equals("H")
							|| textview.getText().toString().equals("D/O") ) {
						array_list.get(0).setText("00:00");
						array_list.get(1).setText("00:00");
						array_list.get(2).setText("00:00");
					}
					
					textview.setText(hour_array[time_picker_hour.getValue()]+":"+min_array[time_picker_min.getValue()]);
					
					String [] start_time_part = array_list.get(0).getText().toString().split(":");
					String [] break_time_part = array_list.get(1).getText().toString().split(":");
					String [] finish_time_part = array_list.get(2).getText().toString().split(":");
					
					//Log.v("finish_time_part[0]", "finish_time_part[0] = "+ Integer.parseInt(finish_time_part[0]));						
					//Log.v("finish_time_part[1]", "finish_time_part[1] = "+ Integer.parseInt(finish_time_part[1]));
					
					//start_time_part[0] 
					
					int total_working_hour = 0;
					int total_working_min = 0;
					
					if (toggle_button.isChecked()) {
						total_working_hour = Integer.parseInt(finish_time_part[0]) -Integer.parseInt(start_time_part[0]);
						total_working_min = Integer.parseInt(finish_time_part[1]) -Integer.parseInt(start_time_part[1]);
					} else {
						total_working_hour = Integer.parseInt(finish_time_part[0])
								-(Integer.parseInt(start_time_part[0])+Integer.parseInt(break_time_part[0]));
						total_working_min = Integer.parseInt(finish_time_part[1])
								-(Integer.parseInt(start_time_part[1])+Integer.parseInt(break_time_part[1]));
					}
					
					if (total_working_hour < 0) {
						total_working_hour = total_working_hour+24;
					}
					
					if (total_working_hour > -1 && (total_working_hour > 0 || total_working_min > 0)) {
						if (total_working_min < 0) {
							total_working_min = 60 + total_working_min;
							total_working_hour = total_working_hour - 1;
						}
						//total_working_hour = Math.abs(total_working_hour);
						//total_working_min = Math.abs(total_working_min);
						
						if (total_working_hour < 10 && total_working_min == 0) {
							array_list.get(3).setText("0"+total_working_hour+":"+"0"+total_working_min);
						} else if (total_working_hour < 10) {
							array_list.get(3).setText("0"+total_working_hour+":"+total_working_min);
						} else if (total_working_min == 0) {
							array_list.get(3).setText(total_working_hour+":"+"0"+total_working_min);
						} else {
							array_list.get(3).setText(total_working_hour+":"+total_working_min);
						}
						
						double day_total = 0;
						if (!hourly_rate_edit_text.getText().toString().equals("")) {
							if (hourly_rate_edit_text.getText().toString().trim().matches("^[0-9]*$")
									|| hourly_rate_edit_text.getText().toString().trim().contains(".")) {
								
								day_total = (double)(total_working_hour*Float.parseFloat((hourly_rate_edit_text.getText().toString())))+
										(double)((total_working_min*Float.parseFloat((hourly_rate_edit_text.getText().toString())))/60);
								array_list.get(4).setText(""+String.format("%.2f", day_total));
							}
						} else {
							array_list.get(4).setText(""+String.format("%.2f", 0f));
						}
					} else {
						array_list.get(3).setText("00:00");
						array_list.get(4).setText(""+String.format("%.2f", 0f));
					}
						//}
					//}
				}
				SaveToDataBase(array_list, Updateable, false);
				dialog_time_picker.dismiss();
			}
		});
	}
	
	public void InactiveNumberPicker(Button h, Button d, ImageView mView) {
		if (h.isSelected() || d.isSelected()) {
			mView.setVisibility(View.VISIBLE);
		} else {
			mView.setVisibility(View.INVISIBLE);
		}
	}
	
	DataBaseAdapter db;
	
	public void SaveToDataBase(ArrayList<TextView> array_list, boolean Updateable2, boolean dynamic) {
		if (array_list.get(2).getText().toString().equals("H")
				|| array_list.get(2).getText().toString().equals("D/O")) {
			
			//Log.v("H", "H");
			//Log.v("D/O", "D/O");
			
			db.open();
			WeeklyCalculationClass new_WeeklyCalculationClass = new WeeklyCalculationClass();
			
			if (array_list.get(2).getText().toString().equals("H")) {
				new_WeeklyCalculationClass.HolyDay = "true";
				new_WeeklyCalculationClass.DayOff = "false";
			} else {
				new_WeeklyCalculationClass.HolyDay = "false";
				new_WeeklyCalculationClass.DayOff = "true";
			}

			new_WeeklyCalculationClass.StarTime = array_list.get(0).getText().toString();
			new_WeeklyCalculationClass.BreakTime = array_list.get(1).getText().toString();
			new_WeeklyCalculationClass.FinishTime = array_list.get(2).getText().toString();
			array_list.get(3).setText("00:00");
			array_list.get(4).setText("0.00");
			new_WeeklyCalculationClass.TotalWorkingHourOfDay = array_list.get(3).getText().toString();
			new_WeeklyCalculationClass.TotalEarnOfDay = array_list.get(4).getText().toString();
			new_WeeklyCalculationClass.DayOfTheMonth = array_list.get(5).getText().toString();
			new_WeeklyCalculationClass.NameOfTheWeek = array_list.get(6).getText().toString();
			new_WeeklyCalculationClass.InsertId = array_list.get(8).getText().toString();
			new_WeeklyCalculationClass.Timestamp = System.currentTimeMillis()+"";
			new_WeeklyCalculationClass.Month = Page3.selected_month;
			new_WeeklyCalculationClass.Year = Page3.selected_year;

			new_WeeklyCalculationClass.WeekNumber = Page3.selected_date+"";
			new_WeeklyCalculationClass.HourlyRate = hourly_rate_edit_text.getText().toString();
			
			/*if (new_WeeklyCalculationClass.NameOfTheWeek.equalsIgnoreCase("Sat")) {
				new_WeeklyCalculationClass.PremiumPersentage = satday_picker_button.getText().toString();
			} else if (new_WeeklyCalculationClass.NameOfTheWeek.equalsIgnoreCase("Sun")) {
				new_WeeklyCalculationClass.PremiumPersentage = sunday_picker_button.getText().toString();
			} else {
				new_WeeklyCalculationClass.PremiumPersentage = "0";
			}*/
			
			new_WeeklyCalculationClass.PremiumPersentage = "0";
			new_WeeklyCalculationClass.NightPremium = "false";
			new_WeeklyCalculationClass.NightPremiumPersentage = "0";
			
			//Log.v("NightPremium", "NightPremium = "+new_WeeklyCalculationClass.NightPremium);
			
			if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
				/*new_WeeklyCalculationClass.AmmountInPound = array_list.get(4).getText().toString();
				new_WeeklyCalculationClass.AmmountInDollar = String.format("%.2f", 
						(double)(1.66666667*Float.parseFloat(array_list.get(4).getText().toString())));*/
				
				new_WeeklyCalculationClass.Currency = "pound";
			} else {
				/*new_WeeklyCalculationClass.AmmountInDollar = array_list.get(4).getText().toString();
				new_WeeklyCalculationClass.AmmountInPound = String.format("%.2f", 
						(double)(0.60*Float.parseFloat(array_list.get(4).getText().toString())));*/

				new_WeeklyCalculationClass.Currency = "dollar";
			}
			
			new_WeeklyCalculationClass.AmmountInPound = "0.00";
			new_WeeklyCalculationClass.AmmountInDollar = "0.00";
			
			if (toggle_button.isChecked()) {
				new_WeeklyCalculationClass.isPaidForBreaks = "true";
			} else {
				new_WeeklyCalculationClass.isPaidForBreaks = "false";
			}
			
			if(Updateable2) {
				db.updateWeeklyCalculation(new_WeeklyCalculationClass, new_WeeklyCalculationClass.InsertId,
						new_WeeklyCalculationClass.DayOfTheMonth, new_WeeklyCalculationClass.Month, new_WeeklyCalculationClass.Year);
				
			} else {
				db.insertWeeklyCalculation(new_WeeklyCalculationClass);
			}
			
			db.close();
			if (dynamic) {
				total_hour_array_list_text_view.add(array_list.get(3));
				total_earn_array_list_text_view.add(array_list.get(4));
			}
			ShowTotal();
			ShowPremium();
		} else if (array_list.get(4).getText().toString().equals("0.00")
				|| array_list.get(4).getText().toString().equals("00.00")
				|| array_list.get(4).getText().toString().equals("0.0")
				|| array_list.get(4).getText().toString().equals("00.0")) {
			
		} else {
			db.open();
			WeeklyCalculationClass new_WeeklyCalculationClass = new WeeklyCalculationClass();
			
			new_WeeklyCalculationClass.HolyDay = "false";
			new_WeeklyCalculationClass.DayOff = "false";
			
			new_WeeklyCalculationClass.StarTime = array_list.get(0).getText().toString();
			new_WeeklyCalculationClass.BreakTime = array_list.get(1).getText().toString();
			new_WeeklyCalculationClass.FinishTime = array_list.get(2).getText().toString();
			new_WeeklyCalculationClass.TotalWorkingHourOfDay = array_list.get(3).getText().toString();
			new_WeeklyCalculationClass.TotalEarnOfDay = array_list.get(4).getText().toString();
			new_WeeklyCalculationClass.DayOfTheMonth = array_list.get(5).getText().toString();
			new_WeeklyCalculationClass.NameOfTheWeek = array_list.get(6).getText().toString();
			new_WeeklyCalculationClass.InsertId = array_list.get(8).getText().toString();
			new_WeeklyCalculationClass.Timestamp = System.currentTimeMillis()+"";
			new_WeeklyCalculationClass.Month = Page3.selected_month;
			new_WeeklyCalculationClass.Year = Page3.selected_year;

			new_WeeklyCalculationClass.WeekNumber = Page3.selected_date+"";
			new_WeeklyCalculationClass.HourlyRate = hourly_rate_edit_text.getText().toString();
			
			if (new_WeeklyCalculationClass.NameOfTheWeek.equalsIgnoreCase("Sat")) {
				new_WeeklyCalculationClass.PremiumPersentage = satday_picker_button.getText().toString();
			} else if (new_WeeklyCalculationClass.NameOfTheWeek.equalsIgnoreCase("Sun")) {
				new_WeeklyCalculationClass.PremiumPersentage = sunday_picker_button.getText().toString();
			} else {
				new_WeeklyCalculationClass.PremiumPersentage = "0";
			}
			
			if (array_list.get(7).getText().toString().equals("true")) {
				new_WeeklyCalculationClass.NightPremium = "true";
				new_WeeklyCalculationClass.NightPremiumPersentage = night_premium_edittext.getText().toString();
			} else {
				new_WeeklyCalculationClass.NightPremium = "false";
				new_WeeklyCalculationClass.NightPremiumPersentage = night_premium_edittext.getText().toString();
			}
			
			Log.v("NightPremium", "NightPremium = "+new_WeeklyCalculationClass.NightPremium);
			
			if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
				new_WeeklyCalculationClass.AmmountInPound = array_list.get(4).getText().toString();
				new_WeeklyCalculationClass.AmmountInDollar = String.format("%.2f", 
						(double)(MainActivity.mPreferences.getFloat("pound_to_dollar", 
								0)*Float.parseFloat(array_list.get(4).getText().toString())));
				
				new_WeeklyCalculationClass.Currency = "pound";
			} else {
				new_WeeklyCalculationClass.AmmountInDollar = array_list.get(4).getText().toString();
				new_WeeklyCalculationClass.AmmountInPound = String.format("%.2f", 
						(double)(MainActivity.mPreferences.
								getFloat("dollar_to_pound", 0)*Float.parseFloat(array_list.get(4).getText().toString())));

				new_WeeklyCalculationClass.Currency = "dollar";
			}
			
			if (toggle_button.isChecked()) {
				new_WeeklyCalculationClass.isPaidForBreaks = "true";
			} else {
				new_WeeklyCalculationClass.isPaidForBreaks = "false";
			}
			
			Log.v("InsertId", "InsertId = "+new_WeeklyCalculationClass.InsertId);
			
			if(Updateable2) {
				db.updateWeeklyCalculation(new_WeeklyCalculationClass, new_WeeklyCalculationClass.InsertId,
						new_WeeklyCalculationClass.DayOfTheMonth, new_WeeklyCalculationClass.Month, new_WeeklyCalculationClass.Year);
				
			} else {
				db.insertWeeklyCalculation(new_WeeklyCalculationClass);
			}
			db.close();
			if (dynamic) {
				total_hour_array_list_text_view.add(array_list.get(3));
				total_earn_array_list_text_view.add(array_list.get(4));
			}
			ShowTotal();
			ShowPremium();
		}
	}
	
	public void ShowTotal() {
		double weekly_total_earn_ammount = 0;
		int weekly_total_working_hour = 0;
		int weekly_total_working_min = 0;
		
		Log.v("total_hour_array_list_text_view.size()", "total_hour_array_list_text_view.size()"
				+total_hour_array_list_text_view.size());
		
		for (int i = 0; i < total_hour_array_list_text_view.size(); i++) {
			//Log.v("i", ""+i);
			if (!total_hour_array_list_text_view.get(i).getText().toString().equals("00:00")){
				//String [] total_earn_part = total_earn_array_list_text_view.get(i).getText().toString().split(".");
				
				weekly_total_earn_ammount = weekly_total_earn_ammount +
						Float.parseFloat(total_earn_array_list_text_view.get(i).getText().toString());
				
				String [] weekly_total_working_hour_part = 
						total_hour_array_list_text_view.get(i).getText().toString().split(":");
				
				weekly_total_working_hour = weekly_total_working_hour + Integer.parseInt(weekly_total_working_hour_part[0]);
				weekly_total_working_min = weekly_total_working_min + Integer.parseInt(weekly_total_working_hour_part[1]);
			}
		}
		
		weekly_total_working_hour = weekly_total_working_hour + (weekly_total_working_min/60);
		weekly_total_working_min = weekly_total_working_min%60;
		
		if (weekly_total_working_hour < 10 && weekly_total_working_min == 0) {
			weekly_total_working_time.setText("0"+weekly_total_working_hour+":"+"0"+weekly_total_working_min);
		} else if (weekly_total_working_hour < 10) {
			weekly_total_working_time.setText("0"+weekly_total_working_hour+":"+weekly_total_working_min);
		} else if (weekly_total_working_min == 0) {
			weekly_total_working_time.setText(weekly_total_working_hour+":"+"0"+weekly_total_working_min);
		} else {
			weekly_total_working_time.setText(weekly_total_working_hour+":"+weekly_total_working_min);
		}
		
		if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
			weekly_total_earn.setText("£"+String.format("%.2f", weekly_total_earn_ammount));
		} else {
			weekly_total_earn.setText("$"+String.format("%.2f", weekly_total_earn_ammount));
		}
	}
	
	public void DynamicShowTotal() {
		double weekly_total_earn_ammount = 0;
		int weekly_total_working_hour = 0;
		int weekly_total_working_min = 0;
		
		Log.v("total_hour_array_list_text_view.size()", "total_hour_array_list_text_view.size()"
				+total_hour_array_list_text_view.size());
		
		for (int i = 0; i < total_hour_array_list_text_view.size(); i++) {
			Log.v("i", ""+i);
			if (!total_hour_array_list_text_view.get(i).getText().toString().equals("00:00")){
				//String [] total_earn_part = total_earn_array_list_text_view.get(i).getText().toString().split(".");
				
				weekly_total_earn_ammount = weekly_total_earn_ammount +
						Float.parseFloat(total_earn_array_list_text_view.get(i).getText().toString());
				
				String [] weekly_total_working_hour_part = 
						total_hour_array_list_text_view.get(i).getText().toString().split(":");
				
				weekly_total_working_hour = weekly_total_working_hour + Integer.parseInt(weekly_total_working_hour_part[0]);
				weekly_total_working_min = weekly_total_working_min + Integer.parseInt(weekly_total_working_hour_part[1]);
			}
		}
		
		weekly_total_working_hour = weekly_total_working_hour + (weekly_total_working_min/60);
		weekly_total_working_min = weekly_total_working_min%60;
		
		if (weekly_total_working_hour < 10 && weekly_total_working_min == 0) {
			weekly_total_working_time.setText("0"+weekly_total_working_hour+":"+"0"+weekly_total_working_min);
		} else if (weekly_total_working_hour < 10) {
			weekly_total_working_time.setText("0"+weekly_total_working_hour+":"+weekly_total_working_min);
		} else if (weekly_total_working_min == 0) {
			weekly_total_working_time.setText(weekly_total_working_hour+":"+"0"+weekly_total_working_min);
		} else {
			weekly_total_working_time.setText(weekly_total_working_hour+":"+weekly_total_working_min);
		}
		
		if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
			weekly_total_earn.setText("£"+String.format("%.2f", weekly_total_earn_ammount));
		} else {
			weekly_total_earn.setText("$"+String.format("%.2f", weekly_total_earn_ammount));
		}
	}

	public void DynamiShowTimePicker(final TextView textview, final int WeekNum, final ArrayList<TextView> array_list) {
		dialog_time_picker = new Dialog(getActivity());
		dialog_time_picker.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_time_picker.setContentView(R.layout.dialog_time_picker);
		dialog_time_picker.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		dialog_time_picker.getWindow().setGravity( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		
		dialog_time_picker.show();
		
		/*Updateable = true;
		
		if (array_list.get(4).getText().toString().equals("0.00")
				|| array_list.get(4).getText().toString().equals("00.00")
				|| array_list.get(4).getText().toString().equals("0.0")
				|| array_list.get(4).getText().toString().equals("00.0")) {
			
			Updateable = false;
		}*/

		Updateable = GetTotalFromDB(WeekNum, Integer.parseInt(array_list.get(8).getText().toString()));
		
		Button done_button;
		done_button = (Button) dialog_time_picker.findViewById(R.id.done_button);
		
		holiday_toggle_button = (Button) dialog_time_picker.findViewById(R.id.holiday_toggle_button);
		dayoff_toggle_button = (Button) dialog_time_picker.findViewById(R.id.dayoff_toggle_button);

		holiday_selection_lay = (LinearLayout) dialog_time_picker.findViewById(R.id.holiday_selection_lay);
		dayoff_selection_lay = (LinearLayout) dialog_time_picker.findViewById(R.id.dayoff_selection_lay);
		
		holiday_selection_lay.setVisibility(View.INVISIBLE);
		dayoff_selection_lay.setVisibility(View.INVISIBLE);
		
		//Log.v("t.getText()", "t.getText() "+t.getText());
		final NumberPicker time_picker_hour, time_picker_min;

		time_picker_hour = (NumberPicker) dialog_time_picker.findViewById(R.id.time_picker_hour);
		time_picker_min = (NumberPicker) dialog_time_picker.findViewById(R.id.time_picker_min);

		time_picker_hour.setMaxValue(hour_array.length - 1);
		time_picker_hour.setMinValue(0);
		time_picker_hour.setDisplayedValues(hour_array);
		//time_picker_hour.setValue(hour_array);
		time_picker_hour.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				// TODO Auto-generated method stub
				
			}
		});
		
		time_picker_min.setMaxValue(min_array.length - 1);
		time_picker_min.setMinValue(0);
		time_picker_min.setDisplayedValues(min_array);
		//time_picker_hour.setValue(hour_array);
		time_picker_min.setOnValueChangedListener(new OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				// TODO Auto-generated method stub
				
			}
		});
		
		done_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Log.v("textview.getId()", "textview.getId() "+textview.getId());
				textview.setText(hour_array[time_picker_hour.getValue()]+":"+min_array[time_picker_min.getValue()]);
				
				//for (int i = 1; i <= 7; i++) {
				//Log.v("array_list.size()", "array_list.size() " +array_list.size());
					//if (WeekNum == i) {
				String [] start_time_part = array_list.get(0).getText().toString().split(":");
				String [] break_time_part = array_list.get(1).getText().toString().split(":");
				String [] finish_time_part = array_list.get(2).getText().toString().split(":");
				
				//Log.v("finish_time_part[0]", "finish_time_part[0] = "+ Integer.parseInt(finish_time_part[0]));						
				//Log.v("finish_time_part[1]", "finish_time_part[1] = "+ Integer.parseInt(finish_time_part[1]));
				
				//start_time_part[0] 
				int total_working_hour = 0;
				int total_working_min = 0;
				
				if (toggle_button.isChecked()) {
					total_working_hour = Integer.parseInt(finish_time_part[0]) -Integer.parseInt(start_time_part[0]);
					total_working_min = Integer.parseInt(finish_time_part[1]) -Integer.parseInt(start_time_part[1]);
				} else {
					total_working_hour = Integer.parseInt(finish_time_part[0])
							-(Integer.parseInt(start_time_part[0])+Integer.parseInt(break_time_part[0]));
					total_working_min = Integer.parseInt(finish_time_part[1])
							-(Integer.parseInt(start_time_part[1])+Integer.parseInt(break_time_part[1]));
				}
				
				if (total_working_hour < 0) {
					total_working_hour = total_working_hour+24;
				}
				
				if (total_working_hour > -1 && (total_working_hour > 0 || total_working_min > 0)) {
					if (total_working_min < 0) {
						total_working_min = 60 + total_working_min;
						total_working_hour = total_working_hour - 1;
					}
					//total_working_hour = Math.abs(total_working_hour);
					//total_working_min = Math.abs(total_working_min);
					
					if (total_working_hour < 10 && total_working_min == 0) {
						array_list.get(3).setText("0"+total_working_hour+":"+"0"+total_working_min);
					} else if (total_working_hour < 10) {
						array_list.get(3).setText("0"+total_working_hour+":"+total_working_min);
					} else if (total_working_min == 0) {
						array_list.get(3).setText(total_working_hour+":"+"0"+total_working_min);
					} else {
						array_list.get(3).setText(total_working_hour+":"+total_working_min);
					}
					
					double day_total = 0;
					if (!hourly_rate_edit_text.getText().toString().equals("")) {
						if (hourly_rate_edit_text.getText().toString().trim().matches("^[0-9]*$")
								|| hourly_rate_edit_text.getText().toString().trim().contains(".")) {
							
							day_total = (double)(total_working_hour*Float.parseFloat((hourly_rate_edit_text.getText().toString())))+
									(double)((total_working_min*Float.parseFloat((hourly_rate_edit_text.getText().toString())))/60);
							array_list.get(4).setText(""+String.format("%.2f", day_total));
						}
					} else {
						array_list.get(4).setText(""+String.format("%.2f", 0f));
					}
				} else {
					array_list.get(3).setText("00:00");
					array_list.get(4).setText(""+String.format("%.2f", 0f));
				}
				
				Updateable = GetTotalFromDB(WeekNum, Integer.parseInt(array_list.get(8).getText().toString()));
				
				Log.v("3", "3 = "+array_list.get(3).getText().toString());
				Log.v("4", "4 = "+array_list.get(4).getText().toString());
				Log.v("8", "8 = "+array_list.get(8).getText().toString());
				Log.v("WeekNum", "WeekNum = "+WeekNum);
				
				Log.v("Updateable", "Updateable = "+Updateable);
				
				
				SaveToDataBase(array_list, Updateable, true);
				
				dialog_time_picker.dismiss();
			}
		});
	}
	
	TextView satday_premium_hour, satday_premium_ammount, sunday_premium_hour,
			sunday_premium_ammount, night_premium_hour, night_premium_ammount;
	
	public void ShowPremium() {
		satday_premium_hour = (TextView) rootview.findViewById(R.id.satday_premium_hour);
		satday_premium_ammount = (TextView) rootview.findViewById(R.id.satday_premium_ammount);
		sunday_premium_hour = (TextView) rootview.findViewById(R.id.sunday_premium_hour);
		sunday_premium_ammount = (TextView) rootview.findViewById(R.id.sunday_premium_ammount);
		night_premium_hour = (TextView) rootview.findViewById(R.id.night_premium_hour);
		night_premium_ammount = (TextView) rootview.findViewById(R.id.night_premium_ammount);
		
		if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
			satday_premium_ammount.setText("£0.00");
			sunday_premium_ammount.setText("£0.00");
			night_premium_ammount.setText("£0.00");
		} else {
			satday_premium_ammount.setText("$0.00");
			sunday_premium_ammount.setText("$0.00");
			night_premium_ammount.setText("$0.00");
		}
		
		int premium_total_working_hour = 0;
		int premium_total_working_min = 0;
		int night_premium_total_working_min = 0;
		//int night_premium_flag = 0;
		
		db = new DataBaseAdapter(getActivity());
		db.open();
		
		Cursor premimum_cursor = db.getPremimumCalculation(Page3.selected_year, Page3.selected_month, Page3.selected_date+"");
		
		while (premimum_cursor.moveToNext()) {
			if (!premimum_cursor.getString(premimum_cursor.getColumnIndex("PremiumPersentage")).equals("0")) {
				
				double per_hour_premium = (double)((Float.parseFloat(premimum_cursor.getString(premimum_cursor.getColumnIndex("HourlyRate")))*
					Float.parseFloat(premimum_cursor.getString(premimum_cursor.getColumnIndex("PremiumPersentage"))))/100);
				
				if (!(premimum_cursor.getString(premimum_cursor.getColumnIndex("FinishTime")).equalsIgnoreCase("H") 
						|| premimum_cursor.getString(premimum_cursor.getColumnIndex("FinishTime")).equalsIgnoreCase("D/O"))) {

					String [] start_time_part = premimum_cursor.getString(premimum_cursor.getColumnIndex("StarTime")).split(":");
					String [] break_time_part = premimum_cursor.getString(premimum_cursor.getColumnIndex("BreakTime")).split(":");
					String [] finish_time_part = premimum_cursor.getString(premimum_cursor.getColumnIndex("FinishTime")).split(":");
					
					if (toggle_button.isChecked()) {
						premium_total_working_hour = Integer.parseInt(finish_time_part[0]) -Integer.parseInt(start_time_part[0]);
						premium_total_working_min = Integer.parseInt(finish_time_part[1]) -Integer.parseInt(start_time_part[1]);
					} else {
						premium_total_working_hour = Integer.parseInt(finish_time_part[0])
								-(Integer.parseInt(start_time_part[0])+Integer.parseInt(break_time_part[0]));
						premium_total_working_min = Integer.parseInt(finish_time_part[1])
								-(Integer.parseInt(start_time_part[1])+Integer.parseInt(break_time_part[1]));
					}
					
					if (premium_total_working_min < 0) {
						premium_total_working_min = 60 + premium_total_working_min;
						premium_total_working_hour = premium_total_working_hour - 1;
					}
					
					//premium_total_working_min = (premium_total_working_hour*60)+premium_total_working_min;
					per_hour_premium = (premium_total_working_hour * per_hour_premium)+((premium_total_working_min * per_hour_premium)/60);
					
					if (premimum_cursor.getString(premimum_cursor.getColumnIndex("NameOfTheWeek")).equalsIgnoreCase("Sat")) {
						satday_premium_hour.setText("00:00");
						if (premium_total_working_hour < 10 && premium_total_working_min == 0) {
							satday_premium_hour.setText("0"+premium_total_working_hour+":"+"0"+premium_total_working_min);
						} else if (premium_total_working_hour < 10) {
							satday_premium_hour.setText("0"+premium_total_working_hour+":"+premium_total_working_min);
						} else if (premium_total_working_min == 0) {
							satday_premium_hour.setText(premium_total_working_hour+":"+"0"+premium_total_working_min);
						} else {
							satday_premium_hour.setText(premium_total_working_hour+":"+premium_total_working_min);
						}
						
						if (premimum_cursor.getString(premimum_cursor.getColumnIndex("Currency")).equals("pound")
								&& !MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
							
							satday_premium_ammount.setText("$"+String.format("%.2f", (double)(MainActivity.mPreferences.
									getFloat("pound_to_dollar", 0)*per_hour_premium)));
							
						} else if (premimum_cursor.getString(premimum_cursor.getColumnIndex("Currency")).equals("dollar")
								&& MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
							
							satday_premium_ammount.setText("£"+String.format("%.2f", (double)(MainActivity.mPreferences.
									getFloat("dollar_to_pound", 0)*per_hour_premium)));
						} else if (premimum_cursor.getString(premimum_cursor.getColumnIndex("Currency")).equals("pound")
								&& MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
							
							satday_premium_ammount.setText("£"+String.format("%.2f", per_hour_premium));
							
						} else if (premimum_cursor.getString(premimum_cursor.getColumnIndex("Currency")).equals("dollar")
								&& !MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
							
							satday_premium_ammount.setText("$"+String.format("%.2f", per_hour_premium));
						}
						satday_picker_button.setText(premimum_cursor.getString(premimum_cursor.getColumnIndex("PremiumPersentage")));
					
					} else if (premimum_cursor.getString(premimum_cursor.getColumnIndex("NameOfTheWeek")).equalsIgnoreCase("Sun")) {
						sunday_premium_hour.setText("00:00");
						if (premium_total_working_hour < 10 && premium_total_working_min == 0) {
							sunday_premium_hour.setText("0"+premium_total_working_hour+":"+"0"+premium_total_working_min);
						} else if (premium_total_working_hour < 10) {
							sunday_premium_hour.setText("0"+premium_total_working_hour+":"+premium_total_working_min);
						} else if (premium_total_working_min == 0) {
							sunday_premium_hour.setText(premium_total_working_hour+":"+"0"+premium_total_working_min);
						} else {
							sunday_premium_hour.setText(premium_total_working_hour+":"+premium_total_working_min);
						}
						if (premimum_cursor.getString(premimum_cursor.getColumnIndex("Currency")).equals("pound")
								&& !MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
							
							sunday_premium_ammount.setText("$"+String.format("$%.2f", (double)(MainActivity.mPreferences.
									getFloat("pound_to_dollar", 0)*per_hour_premium)));
							
						} else if (premimum_cursor.getString(premimum_cursor.getColumnIndex("Currency")).equals("dollar")
								&& MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
							
							sunday_premium_ammount.setText("£"+String.format("%.2f", (double)(MainActivity.mPreferences.
									getFloat("dollar_to_pound", 0)*per_hour_premium)));
						} else if (premimum_cursor.getString(premimum_cursor.getColumnIndex("Currency")).equals("pound")
								&& MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
							
							sunday_premium_ammount.setText("£"+String.format("%.2f", per_hour_premium));
							
						} else if (premimum_cursor.getString(premimum_cursor.getColumnIndex("Currency")).equals("dollar")
								&& !MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
							
							sunday_premium_ammount.setText("$"+String.format("%.2f", per_hour_premium));
						}
						sunday_picker_button.setText(premimum_cursor.getString(premimum_cursor.getColumnIndex("PremiumPersentage")));
					}
				}
			}
			
			if (!premimum_cursor.getString(premimum_cursor.getColumnIndex("NightPremiumPersentage")).equals("0")) {
				if (premimum_cursor.getString(premimum_cursor.getColumnIndex("NameOfTheWeek")).equalsIgnoreCase("Sat")
						|| premimum_cursor.getString(premimum_cursor.getColumnIndex("NameOfTheWeek")).equalsIgnoreCase("Sun")
						|| premimum_cursor.getString(premimum_cursor.getColumnIndex("NightPremium")).equals("true")) {
					
					/*String [] night_premium_working_hour_part = premimum_cursor.
							getString(premimum_cursor.getColumnIndex("TotalWorkingHourOfDay")).split(":");
					
					premium_total_working_hour = Integer.parseInt(night_premium_working_hour_part[0]);
					premium_total_working_min = Integer.parseInt(night_premium_working_hour_part[1]);
					
					premium_total_working_hour = premium_total_working_hour + (premium_total_working_min/60);
					premium_total_working_min = premium_total_working_min%60;*/
					String [] start_time_part = premimum_cursor.getString(premimum_cursor.getColumnIndex("StarTime")).split(":");
					String [] break_time_part = premimum_cursor.getString(premimum_cursor.getColumnIndex("BreakTime")).split(":");
					String [] finish_time_part = premimum_cursor.getString(premimum_cursor.getColumnIndex("FinishTime")).split(":");
					
					if (toggle_button.isChecked()) {
						premium_total_working_hour = Integer.parseInt(finish_time_part[0]) -Integer.parseInt(start_time_part[0]);
						premium_total_working_min = Integer.parseInt(finish_time_part[1]) -Integer.parseInt(start_time_part[1]);
					} else {
						premium_total_working_hour = Integer.parseInt(finish_time_part[0])
								-(Integer.parseInt(start_time_part[0])+Integer.parseInt(break_time_part[0]));
						premium_total_working_min = Integer.parseInt(finish_time_part[1])
								-(Integer.parseInt(start_time_part[1])+Integer.parseInt(break_time_part[1]));
					}
					
					if (premium_total_working_min < 0) {
						premium_total_working_min = 60 + premium_total_working_min;
						premium_total_working_hour = premium_total_working_hour - 1;
					}
					
					night_premium_total_working_min += (premium_total_working_hour*60) + premium_total_working_min;
					//Log.v("night_premium_total_working_min", "night_premium_total_working_min = "+night_premium_total_working_min);
					
					premium_total_working_hour = night_premium_total_working_min/60;
					premium_total_working_min = night_premium_total_working_min%60;
					
					/*night_premium_hour = (TextView) rootview.findViewById(R.id.night_premium_hour);
					night_premium_ammount*/
					
					if (premium_total_working_hour < 10 && premium_total_working_min == 0) {
						night_premium_hour.setText("0"+premium_total_working_hour+":"+"0"+premium_total_working_min);
					} else if (premium_total_working_hour < 10) {
						night_premium_hour.setText("0"+premium_total_working_hour+":"+premium_total_working_min);
					} else if (premium_total_working_min == 0) {
						night_premium_hour.setText(premium_total_working_hour+":"+"0"+premium_total_working_min);
					} else {
						night_premium_hour.setText(premium_total_working_hour+":"+premium_total_working_min);
					}
					
					night_premium_edittext.setText(premimum_cursor.getString(premimum_cursor.getColumnIndex("NightPremiumPersentage")));
					
					if (premimum_cursor.getString(premimum_cursor.getColumnIndex("Currency")).equals("pound")
							&& !MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
						
						night_premium_edittext.setText(""+String.format("%.2f", (double)(MainActivity.mPreferences.
								getFloat("pound_to_dollar", 0)*Float.
								parseFloat(night_premium_edittext.getText().toString()))));
						
					} else if (premimum_cursor.getString(premimum_cursor.getColumnIndex("Currency")).equals("dollar")
							&& MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
						
						night_premium_edittext.setText(""+String.format("%.2f", (double)(MainActivity.mPreferences.
								getFloat("dollar_to_pound", 0)*Float.
								parseFloat(night_premium_edittext.getText().toString()))));
					}
					
					double per_hour_premium = (double)Float.parseFloat(premimum_cursor.
							getString(premimum_cursor.getColumnIndex("NightPremiumPersentage")));
					
					per_hour_premium = (premium_total_working_hour*per_hour_premium)+
							((premium_total_working_min*per_hour_premium)/60);
					
					if (MainActivity.mPreferences.getString("currency", "").equals("currency £")) {
						night_premium_ammount.setText("£"+String.format("%.2f", per_hour_premium));
					} else {
						night_premium_ammount.setText("$"+String.format("%.2f", per_hour_premium));
					}
				}
			}
		}
		
		premimum_cursor.close();
		db.close();
	}
}