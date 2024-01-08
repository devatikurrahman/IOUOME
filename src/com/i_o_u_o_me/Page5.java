package com.i_o_u_o_me;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Page5 extends Fragment implements OnClickListener {
	public static String FRAGMENT_TAG = "Page5";
	View rootview;
	
	public Calendar month;
	public Page5_CalanderAdapter adapter;
	public Handler handler;
	public ArrayList<String> items;
	Button date_button;
	TextView title, today_text, workingday_test;
	ScrollView scroll_view;
	LinearLayout today, workingday, dayoff, holiday;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState == null)
			rootview = inflater.inflate(R.layout.calendar, container, false);

		return rootview;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (savedInstanceState == null)
			initViewsAndUtils();
	}

	public void initViewsAndUtils() {
		MainActivity.top_bar_text.setText("Expenses tracking");
		month = Calendar.getInstance();
		items = new ArrayList<String>();
		scroll_view = (ScrollView) rootview.findViewById(R.id.scroll_view);
		//scroll_view.setVisibility(View.GONE);

		today = (LinearLayout) rootview.findViewById(R.id.today);
		workingday = (LinearLayout) rootview.findViewById(R.id.workingday);
		dayoff = (LinearLayout) rootview.findViewById(R.id.dayoff);
		holiday = (LinearLayout) rootview.findViewById(R.id.holiday);

		dayoff.setVisibility(View.GONE);
		holiday.setVisibility(View.GONE);

		today_text = (TextView) rootview.findViewById(R.id.today_text);
		workingday_test = (TextView) rootview.findViewById(R.id.workingday_test);
		workingday_test.setText("Expense tracking");
		
		date_button = (Button) rootview.findViewById(R.id.date_button);
		//date_button.setOnClickListener(this);
	    date_button.setText("Today : "+android.text.format.DateFormat.format("EEEE, dd MMMM yyyy", month));
	    date_button.setEnabled(false);
		//onNewIntent(getIntent());
		
	    adapter = new Page5_CalanderAdapter(getActivity(), month);
	    
	    GridView gridview = (GridView) rootview.findViewById(R.id.gridview);
	    gridview.setAdapter(adapter);
	    
	    handler = new Handler();
	    handler.post(calendarUpdater);
	    
	    title = (TextView) rootview.findViewById(R.id.title);
	    title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	    title.setOnClickListener(this);
	    
	    TextView previous  = (TextView) rootview.findViewById(R.id.previous);
	    previous.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(month.get(Calendar.MONTH)== month.getActualMinimum(Calendar.MONTH)) {				
					month.set((month.get(Calendar.YEAR)-1),month.getActualMaximum(Calendar.MONTH),1);
				} else {
					month.set(Calendar.MONTH,month.get(Calendar.MONTH)-1);
				}
				refreshCalendar();
			}
		});
	    
	    TextView next  = (TextView) rootview.findViewById(R.id.next);
	    next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(month.get(Calendar.MONTH)== month.getActualMaximum(Calendar.MONTH)) {				
					month.set((month.get(Calendar.YEAR)+1),month.getActualMinimum(Calendar.MONTH),1);
				} else {
					month.set(Calendar.MONTH,month.get(Calendar.MONTH)+1);
				}
				refreshCalendar();
				
			}
		});
	    gridview.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		    	TextView date = (TextView)v.findViewById(R.id.date);
		        if(date instanceof TextView && !date.getText().equals("")) {
		        	
					MainActivity.mFragmentManager = getActivity().getFragmentManager();
					MainActivity.mFragmentManager.beginTransaction().replace(R.id.frame, new ExpenseTracking(), 
							ExpenseTracking.FRAGMENT_TAG).addToBackStack(null).commit();
					
		        	/*if(day.length()==1) {
		        		day = "0"+day;
		        	}*/

					selected_date = date.getText().toString().trim();
		        	selected_month = (String) android.text.format.DateFormat.format("MMMM", month);
		        	selected_year = (String) android.text.format.DateFormat.format("yyyy", month);
		        	
		        	/*Log.v("selected_month", "selected_month = "+ selected_month);
		        	Log.v("selected_year", "selected_year = "+ selected_year);
		        	Log.v("day", "day = "+ Integer.parseInt(day));
		        	
		        	selected_date = Integer.parseInt(day)-1;
		        	Log.v("selected_date", "b selected_date = "+ selected_date);
		        	if (selected_date != 0) {
		        		selected_date = selected_date/7;
					}
		        	Log.v("selected_date", "a selected_date = "+ selected_date);*/
		        	
		        	how_many_days_in_this_month = DaysInMonth(selected_year, selected_month);
		        	//Log.v("", "how_many_days_in_this_month = "+how_many_days_in_this_month);
		        	
		        }
		    }
		});
	}
	
	public int DaysInMonth(String Year, String Month) {
		int month = 0;
		if (Month.equalsIgnoreCase("January")) {
			 month = Calendar.JANUARY;
		} else if (Month.equalsIgnoreCase("February")) {
			 month = Calendar.FEBRUARY;
		} else if (Month.equalsIgnoreCase("March")) {
			 month = Calendar.MARCH;
		} else if (Month.equalsIgnoreCase("April")) {
			 month = Calendar.APRIL;
		} else if (Month.equalsIgnoreCase("May")) {
			 month = Calendar.MAY;
		} else if (Month.equalsIgnoreCase("June")) {
			 month = Calendar.JUNE;
		} else if (Month.equalsIgnoreCase("July")) {
			 month = Calendar.JULY;
		} else if (Month.equalsIgnoreCase("August")) {
			 month = Calendar.AUGUST;
		} else if (Month.equalsIgnoreCase("September")) {
			 month = Calendar.SEPTEMBER;
		} else if (Month.equalsIgnoreCase("October")) {
			 month = Calendar.OCTOBER;
		} else if (Month.equalsIgnoreCase("November")) {
			 month = Calendar.NOVEMBER;
		} else if (Month.equalsIgnoreCase("December")) {
			 month = Calendar.DECEMBER;
		}
		Calendar mycal = new GregorianCalendar(Integer.parseInt(Year), month, 1);
		return mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	//"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
	public static String selected_date;
	public static String selected_month, selected_year;
	public static int how_many_days_in_this_month;
	
	public void refreshCalendar() {
		adapter.refreshDays();
		adapter.notifyDataSetChanged();				
		handler.post(calendarUpdater); // generate some random calendar items				
		
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	}

	public void onNewIntent(Intent intent) {
		String date = intent.getStringExtra("date");
		String[] dateArr = date.split("-"); // date format is yyyy-mm-dd
		month.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2]));
	}

	public Runnable calendarUpdater = new Runnable() {
		
		@Override
		public void run() {
			items.clear();
			// format random values. You can implement a dedicated class to provide real values
			for(int i=0;i<31;i++) {
				Random r = new Random();
				
				if(r.nextInt(10)>6)
				{
					items.add(Integer.toString(i));
				}
			}

			adapter.setItems(items);
			adapter.notifyDataSetChanged();
		}
	};
	
	Dialog dialog_date_picker;
	Button done_button;
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.date_button:
			
			dialog_date_picker = new Dialog(getActivity());
			dialog_date_picker.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog_date_picker.setContentView(R.layout.dialog_date_picker);
			dialog_date_picker.getWindow().setBackgroundDrawable(new ColorDrawable(0));
			dialog_date_picker.getWindow().setGravity( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
			
			dialog_date_picker.show();

			done_button = (Button) dialog_date_picker.findViewById(R.id.done_button);
			done_button.setOnClickListener(this);
			break;
			
		case R.id.done_button:
			dialog_date_picker.dismiss();
			
			break;

			
		case R.id.title:
			
			/*month_year = title.getText().toString();
			
			MainActivity.mFragmentManager = getActivity().getFragmentManager();
			MainActivity.mFragmentManager.beginTransaction().replace(R.id.frame, new MonthlyFragement(), 
					MonthlyFragement.FRAGMENT_TAG).addToBackStack(null).commit();*/
			
			break;
		default:
			break;
		}
	}
	
	public static String month_year = "";

}