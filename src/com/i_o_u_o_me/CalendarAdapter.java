/*
* Copyright 2011 Lauri Nevala.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.i_o_u_o_me;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CalendarAdapter extends BaseAdapter {
	static final int FIRST_DAY_OF_WEEK =1; // Sunday = 1, Monday = 2
	
	String[] monthNames;
	private int currentYear;
	private int currentMonth;
	private Context mContext;
	
    private java.util.Calendar month;
    private Calendar selectedDate;
    private ArrayList<String> items;
    
    public CalendarAdapter(Context c, Calendar monthCalendar) {
    	month = monthCalendar;
    	selectedDate = (Calendar)monthCalendar.clone();
    	mContext = c;
        month.set(Calendar.DAY_OF_MONTH, 1);
        this.items = new ArrayList<String>();
        
        this.currentYear = month.get(Calendar.YEAR);
    	this.currentMonth = month.get(Calendar.MONTH);
    
    	this.monthNames = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    	
        refreshDays();
    }
    
    public void setItems(ArrayList<String> items) {
    	for(int i = 0;i != items.size();i++){
    		if(items.get(i).length()==1) {
    		items.set(i, "0" + items.get(i));
    		}
    	}
    	this.items = items;
    }
    

    public int getCount() {
        return days.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new view for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
    	TextView dayView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.calendar_item, null);
        	
        }
        dayView = (TextView)v.findViewById(R.id.date);
        
        
        int dayNumber = 0;
        if(dayView.getText().toString().matches("\\d+")) //check if only digits. Could also be text.matches("[0-9]+")
        {
        	dayNumber = Integer.parseInt(dayView.getText().toString());
        }
        else
        {
           //System.out.println("not a valid number");
        }
       
        // disable empty days from the beginning
        if(days[position].equals("")) {
        	dayView.setClickable(false);
        	dayView.setFocusable(false);
        }
        else {
        	
        	if(dayNumber > 0) {
	        	//Log.d("#### dayNumber: ", ""+dayNumber);
        		boolean isDateToday = true;
	        	MainActivity.db.open();
	        	
	        	//Log.d("current date:", "day: "+dayNumber+ " #month: "+this.currentMonth+ " #year: "+ this.currentYear);
	        	
//	        	boolean isHoliday = MainActivity.db.isDateHoliday(Integer.toString(dayNumber), Integer.toString(this.currentYear), this.monthNames[this.currentMonth]);
//	        	if(isHoliday) {
//	        		isDateToday = false;
//	        		v.setBackgroundResource(R.drawable.item_background_focused_blue);
//	        	}
//	        	else {
//	        		v.setBackgroundResource(R.drawable.item_background);
//	        	}
//	        	
//	        	boolean isDayOff = MainActivity.db.isDateDayOff(Integer.toString(dayNumber), Integer.toString(this.currentYear), this.monthNames[this.currentMonth]);
//	        	if(isDayOff) {
//	        		isDateToday = false;
//	        		v.setBackgroundResource(R.drawable.item_background_focused_green);
//	        	}
//	        	else {
//	        		v.setBackgroundResource(R.drawable.item_background);
//	        	}
	        	
	        	//GregorianCalendar calendar = new GregorianCalendar(this.currentYear, this.currentMonth, dayNumber);
	        	//int i = calendar.get(Calendar.DAY_OF_WEEK);
	   
	        	Cursor getDataCursor = MainActivity.db.getWeeklyCalculation(Integer.toString(dayNumber), Integer.toString(this.currentYear), this.monthNames[this.currentMonth]);
	        	if (getDataCursor.moveToFirst()) {
	        		
	        		//Log.d("#### StarTime : ", " "+ getDataCursor.getString(getDataCursor.getColumnIndex("StarTime")));
	        		//Log.d("#### Holyday : ", " "+ getDataCursor.getString(getDataCursor.getColumnIndex("HolyDay")));
	        		//Log.d("#### DayOff : ", " "+ getDataCursor.getString(getDataCursor.getColumnIndex("DayOff")));
	        		
	        		isDateToday = false;
	        		if(getDataCursor.getString(getDataCursor.getColumnIndex("HolyDay")).equals("true")) {
	        			v.setBackgroundResource(R.drawable.item_background_focused_blue);
	        		}
	        		else if(getDataCursor.getString(getDataCursor.getColumnIndex("DayOff")).equals("true")) {
	        			v.setBackgroundResource(R.drawable.item_background_focused_green);
	        		}
	        		else {
	        			v.setBackgroundResource(R.drawable.item_background_focused_red);
	        		}
	        	
	        		// record exists
	        	} else {
	        		// mark current day as focused
		        	if(month.get(Calendar.YEAR)== selectedDate.get(Calendar.YEAR) && month.get(Calendar.MONTH)== selectedDate.get(Calendar.MONTH) && days[position].equals(""+selectedDate.get(Calendar.DAY_OF_MONTH))) {
		        		if(isDateToday == true)
		        			v.setBackgroundResource(R.drawable.item_background_focused_today);
		        	}
		        	else {
		        		v.setBackgroundResource(R.drawable.item_background);
		        	}	
	        	}
	        	getDataCursor.close();
	        	MainActivity.db.close();
	     
        	}
            
        }
        dayView.setText(days[position]);
        
        // create date string for comparison
        String date = days[position];
        //Log.d("day num : ", ""+date);
        
        if(date.length()==1) {
    		date = "0"+date;
    	}
    	String monthStr = ""+(month.get(Calendar.MONTH)+1);
    	if(monthStr.length()==1) {
    		monthStr = "0"+monthStr;
    	}
       
        // show icon if date is not empty and it exists in the items array
        ImageView iw = (ImageView)v.findViewById(R.id.date_icon);
        if(date.length()>0 && items!=null && items.contains(date)) {        	
        	iw.setVisibility(View.VISIBLE);
        }
        else {
        	iw.setVisibility(View.INVISIBLE);
        }
        return v;
    }
    
    public String getDayName(int next) {
        int dayIndex = getCurrentDayIndex();
        dayIndex += next;
        dayIndex = dayIndex % 7;

        String[] days = new String[] {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
        String day = days[dayIndex];
        return day;
    }


    public int getCurrentDayIndex() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());   
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        return dayIndex;
    }
    
    public void refreshDays()
    {
    	// clear items
    	items.clear();
    	
    	this.currentYear = month.get(Calendar.YEAR);
    	this.currentMonth = month.get(Calendar.MONTH);
    	
    	//Log.d("##### MONTH name: ", ""+this.currentMonth+ " YEAR: "+ this.currentYear);
    	
    	
    	int lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDay = (int)month.get(Calendar.DAY_OF_WEEK);
        
        // figure size of the array
        if(firstDay==1){
        	days = new String[lastDay+(FIRST_DAY_OF_WEEK*6)];
        }
        else {
        	days = new String[lastDay+firstDay-(FIRST_DAY_OF_WEEK+1)];
        }
        
        int j=FIRST_DAY_OF_WEEK;
        
        // populate empty days before first real day
        if(firstDay>1) {
	        for(j=0;j<firstDay-FIRST_DAY_OF_WEEK;j++) {
	        	days[j] = "";
	        }
        }
	    else {
	    	for(j=0;j<FIRST_DAY_OF_WEEK*6;j++) {
	        	days[j] = "";
	        }
	    	j=FIRST_DAY_OF_WEEK*6+1; // sunday => 1, monday => 7
	    }
        
        // populate days
        int dayNumber = 1;
        for(int i=j-1;i<days.length;i++) {
        	days[i] = ""+dayNumber;
        	dayNumber++;
        }
    }

    // references to our items
    public String[] days;
    
}


