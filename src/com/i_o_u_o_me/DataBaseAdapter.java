package com.i_o_u_o_me;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.i_o_u_o_me.ExpenseTracking.ExpenseClass;

public class DataBaseAdapter {
	private DataBaseHelper dbHelper;
	private SQLiteDatabase sqliteDB;
	
	// Database Properties
	public static final String DATABASE_NAME = "ioume_database.db";
	public static final int DATABASE_VERSION = 1;

	public DataBaseAdapter(Context context) {
		dbHelper = new DataBaseHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public class DataBaseHelper extends SQLiteOpenHelper {

		public DataBaseHelper(Context context, String databaseName,
				CursorFactory factory, int databaseVersion) {
			super(context, databaseName, factory, databaseVersion);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS OTAApplications");
			db.execSQL("DROP TABLE IF EXISTS OTAInfo");
			db.execSQL("DROP TABLE IF EXISTS OTAApplicationsTemp");
			onCreate(db);
		}

	}

	// Open method for opening the Custom Database i.e. "DataBaseAdapter"
	public DataBaseAdapter open() throws SQLException {
		sqliteDB = dbHelper.getWritableDatabase();
		//Log.v("sql", "=" + sqliteDB);
		return this;
	}

	// Closes the DataBase with the help of the helper object
	public void close() {
		dbHelper.close();
	}
	
	public long insertMonthlyInfo(MonthlyInfoClass new_MonthlyInfoClass) {
		open();
		ContentValues values = new ContentValues();
		
		values.put("DebitName", new_MonthlyInfoClass.DebitName);
		values.put("DebitAmmount", new_MonthlyInfoClass.DebitAmmount);
		values.put("MonthName", new_MonthlyInfoClass.MonthName);
		values.put("Year", new_MonthlyInfoClass.Year);
		values.put("AmmountDollar", new_MonthlyInfoClass.AmmountDollar);
		values.put("AmmountPound", new_MonthlyInfoClass.AmmountPound);
		/*values.put("WageAmmount", new_MonthlyInfoClass.WageAmmount);
		values.put("OtherAmmount", new_MonthlyInfoClass.OtherAmmount);*/
		
		return sqliteDB.insert("MonthlyTable", null, values);
	}
	
	/*public Cursor getMonthlyInfo(String month, String year) {
		Cursor cursor = sqliteDB.query("MonthlyTable", new String[] {
				"DebitName", "DebitAmmount", "MonthName", "Year", "AmmountDollar", "AmmountPound", "WageAmmount", "OtherAmmount"},
				"MonthName = " + "\""+month+"\"and Year = " + "\""+year+"\"", null, null, null, null);
		return cursor;
	}*/
	
	public Cursor getMonthlyInfo() {
		Cursor cursor = sqliteDB.query("MonthlyTable", new String[] {
				"DebitName", "DebitAmmount", "MonthName", "Year", "AmmountDollar", "AmmountPound", "WageAmmount", "OtherAmmount"},
				null, null, null, null, null);
		return cursor;
	}

	public long DeleteMonthlyData(String MonthName, String Year) {
		return sqliteDB.delete("MonthlyTable", "MonthName = " + "\""+MonthName+"\"and Year = " + "\""+Year+"\"", null);
	}

	public void deleteMonthlyInfoTable() {
		if (sqliteDB != null)
			sqliteDB.execSQL("delete from MonthlyTable");
	}
	
	public long insertExpenseInfo(ExpenseClass new_ExpenseClass) {
		open();
		ContentValues values = new ContentValues();
		
		values.put("Name", new_ExpenseClass.Name);
		values.put("Ammount", new_ExpenseClass.Ammount);
		values.put("Day", new_ExpenseClass.Day);
		values.put("Month", new_ExpenseClass.Month);
		values.put("Year", new_ExpenseClass.Year);
		values.put("Currency", new_ExpenseClass.Currency);
		values.put("AmmountPound", new_ExpenseClass.AmmountPound);
		values.put("AmmountDollar", new_ExpenseClass.AmmountDollar);
		
		return sqliteDB.insert("ExpenseTracking", null, values);
	}
	
	public Cursor getExpenseInfo(String Day, String Month, String Year) {
		Cursor cursor = sqliteDB.query("ExpenseTracking", new String[] {
				"Name", "Ammount", "AmmountPound", "AmmountDollar", "Day", "Month", "Year", "Currency" }, 
				"Day = " + "\""+Day+"\"and Month = " + "\""+Month+"\"and Year = " + "\""+Year+"\"",
				null, null, null, null);
		return cursor;
	}

	public void deleteExpenseInfoTable() {
		if (sqliteDB != null)
			sqliteDB.execSQL("delete from ExpenseTracking");
	}

	public long DeleteSelectExpenseData(String Day, String Month, String Year) {
		return sqliteDB.delete("ExpenseTracking", "Day = " + "\""+Day+"\"and Month = " +
				"\""+Month+"\"and Year = " + "\""+Year+"\"", null);
	}
	
	public long insertAmmountInfo1(AmmountInfoClass new_AmmountInfoClass) {
		open();
		ContentValues values = new ContentValues();
		
		values.put("Name", new_AmmountInfoClass.Name);
		values.put("Ammount", new_AmmountInfoClass.Ammount);
		values.put("CurrencyDollar", new_AmmountInfoClass.CurrencyDollar);
		values.put("CurrencyPound", new_AmmountInfoClass.CurrencyPound);
		
		return sqliteDB.insert("DataEntryTable", null, values);
	}
	
	public Cursor getAmmountInfo1() {
		Cursor cursor = sqliteDB.query("DataEntryTable", new String[] {
				"Name", "Ammount", "CurrencyDollar", "AmmountId", "CurrencyPound" }, null, null, null, null, null);
		return cursor;
	}

	public void deleteAmmountInfoTable1() {
		if (sqliteDB != null)
			sqliteDB.execSQL("delete from DataEntryTable");
	}
	
	public long insertAmmountInfo2(AmmountInfoClass new_AmmountInfoClass) {
		open();
		ContentValues values = new ContentValues();
		
		values.put("Name", new_AmmountInfoClass.Name);
		values.put("Ammount", new_AmmountInfoClass.Ammount);
		values.put("CurrencyDollar", new_AmmountInfoClass.CurrencyDollar);
		values.put("CurrencyPound", new_AmmountInfoClass.CurrencyPound);
		
		return sqliteDB.insert("DataEntryTable2", null, values);
	}
	
	public Cursor getAmmountInfo2() {
		Cursor cursor = sqliteDB.query("DataEntryTable2", new String[] {
				"Name", "Ammount", "CurrencyDollar", "AmmountId", "CurrencyPound"}, null, null, null, null, null);
		return cursor;
	}

	public void deleteAmmountInfoTable2() {
		if (sqliteDB != null)
			sqliteDB.execSQL("delete from DataEntryTable2");
	}
	
	//WeeklyCalculationTable
	
	public long insertWeeklyCalculation(WeeklyCalculationClass new_WeeklyCalculationClass) {
		open();
		ContentValues values = new ContentValues();

		values.put("DayOfTheMonth", new_WeeklyCalculationClass.DayOfTheMonth);
		values.put("NameOfTheWeek", new_WeeklyCalculationClass.NameOfTheWeek);
		values.put("StarTime", new_WeeklyCalculationClass.StarTime);
		values.put("BreakTime", new_WeeklyCalculationClass.BreakTime);
		values.put("FinishTime", new_WeeklyCalculationClass.FinishTime);
		values.put("TotalWorkingHourOfDay", new_WeeklyCalculationClass.TotalWorkingHourOfDay);
		values.put("TotalEarnOfDay", new_WeeklyCalculationClass.TotalEarnOfDay);
		
		values.put("Year", new_WeeklyCalculationClass.Year);
		values.put("Month", new_WeeklyCalculationClass.Month);
		values.put("AmmountInDollar", new_WeeklyCalculationClass.AmmountInDollar);
		values.put("AmmountInPound", new_WeeklyCalculationClass.AmmountInPound);
		values.put("Timestamp", new_WeeklyCalculationClass.Timestamp);
		
		values.put("WeekNumber", new_WeeklyCalculationClass.WeekNumber);
		values.put("HourlyRate", new_WeeklyCalculationClass.HourlyRate);
		values.put("PremiumPersentage", new_WeeklyCalculationClass.PremiumPersentage);
		values.put("NightPremium", new_WeeklyCalculationClass.NightPremium);
		
		values.put("Currency", new_WeeklyCalculationClass.Currency);
		values.put("HolyDay", new_WeeklyCalculationClass.HolyDay);
		values.put("DayOff", new_WeeklyCalculationClass.DayOff);
		values.put("NightPremiumPersentage", new_WeeklyCalculationClass.NightPremiumPersentage);

		values.put("InsertId", new_WeeklyCalculationClass.InsertId);
		values.put("isPaidForBreaks", new_WeeklyCalculationClass.isPaidForBreaks);
		
		return sqliteDB.insert("WeeklyCalculationTable", null, values);
	}
	
	public Cursor getWeeklyCalculation(String DayOfTheMonth, String Year, String Month) {
		Cursor cursor = sqliteDB.query("WeeklyCalculationTable", new String[] {
				"ID", "StarTime", "BreakTime", "FinishTime", "TotalWorkingHourOfDay",
				"TotalEarnOfDay", "AmmountInDollar", "AmmountInPound", "HourlyRate", "NightPremium",
				"InsertId", "HolyDay", "DayOff", "isPaidForBreaks", "Currency"},
				"DayOfTheMonth = " + "\""+DayOfTheMonth+"\"and Year = " + "\""+Year+"\"and Month = " + "\""+Month+"\"", 
				null, null, null, null);
		return cursor;
	}
	
	public boolean IsPaidForBreaks(String WeekNumber, String Month, String Year) {
		
		Cursor cursor = sqliteDB.query("WeeklyCalculationTable", new String[] {
				"ID"}, "Year = " + "\""+Year+"\"and isPaidForBreaks = " + "\""+"true"+"\"and Month = " 
				+ "\""+Month+"\"and WeekNumber = " + "\""+WeekNumber+"\"", null, null, null, null);
		
		if (cursor.getCount() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public int updatePaidForBreaks(String isPaidForBreaks, String WeekNumber, String Month, String Year) {
		
		ContentValues values = new ContentValues();
		
		values.put("isPaidForBreaks", isPaidForBreaks);
		
		return sqliteDB.update("WeeklyCalculationTable", values, "Year = " + "\""+Year+"\"and Month = " 
				+ "\""+Month+"\"and WeekNumber = " + "\""+WeekNumber+"\"", null);
	}
	
	
	public boolean getDayTotalHour(String DayOfTheMonth, String Year, String Month, String InsertId) {
		Cursor cursor = sqliteDB.query("WeeklyCalculationTable", new String[] {
				"ID"},
				"InsertId = " + "\""+InsertId+"\"and DayOfTheMonth = " + "\""+DayOfTheMonth+"\"and Year = " 
				+ "\""+Year+"\"and Month = " + "\""+Month+"\"", 
				null, null, null, null);
		if (cursor.getCount() > 0) {
			return true;
		}
		return false;
	}
	
	public Cursor getPremimumCalculation(String Year, String Month, String WeekNumber) {
		Cursor cursor = sqliteDB.query("WeeklyCalculationTable", new String[] {
				"ID", "StarTime", "BreakTime", "FinishTime", "NameOfTheWeek", "TotalWorkingHourOfDay", "TotalEarnOfDay",
				"AmmountInDollar", "AmmountInPound", "isPaidForBreaks",
				"WeekNumber",  "HourlyRate", "PremiumPersentage", "Currency", "NightPremiumPersentage", "NightPremium"},
				"Year = " + "\""+Year+"\"and Month = " + "\""+Month+"\"and WeekNumber = " + "\""+WeekNumber+"\"", 
				null, null, null, null);
		return cursor;
	}
	
	/*public boolean isDateHoliday(String DayOfTheMonth, String Year, String Month) {
		boolean holiday = false;
		Cursor cursor = sqliteDB.query("WeeklyCalculationTable", new String[] {
				"ID"},
				"DayOfTheMonth = " + "\""+DayOfTheMonth+"\"and Year = " + "\""+Year+"\"and Month = " + "\""+Month+"\"and HolyDay = \"ture\"", 
				null, null, null, null);
		if (cursor.getCount() > 0) {
			holiday = true;
		}
		return holiday;
	}*/
	
	
	public Cursor getTotalMonthEarn(String Month, String Year) {
		Cursor cursor = sqliteDB.query("WeeklyCalculationTable", new String[] {
				"AmmountInDollar", "AmmountInPound"}, 
				"Year = " + "\""+Year+"\"and Month = " + "\""+Month+"\"", 
				null, null, null, null);
		return cursor;
	}
	
	public int updateWeeklyCalculation(WeeklyCalculationClass newWeeklyCalculationClass, 
			String InstId, String DayOfTheMonth, String Month, String Year) {
		ContentValues values = new ContentValues();
		
		values.put("StarTime", newWeeklyCalculationClass.StarTime);
		values.put("BreakTime", newWeeklyCalculationClass.BreakTime);
		values.put("FinishTime", newWeeklyCalculationClass.FinishTime);
		values.put("TotalWorkingHourOfDay", newWeeklyCalculationClass.TotalWorkingHourOfDay);
		values.put("TotalEarnOfDay", newWeeklyCalculationClass.TotalEarnOfDay);
		
		values.put("AmmountInDollar", newWeeklyCalculationClass.AmmountInDollar);
		values.put("AmmountInPound", newWeeklyCalculationClass.AmmountInPound);
		
		values.put("HourlyRate", newWeeklyCalculationClass.HourlyRate);
		values.put("PremiumPersentage", newWeeklyCalculationClass.PremiumPersentage);
		values.put("NightPremium", newWeeklyCalculationClass.NightPremium);
		
		values.put("Currency", newWeeklyCalculationClass.Currency);
		values.put("HolyDay", newWeeklyCalculationClass.HolyDay);
		values.put("DayOff", newWeeklyCalculationClass.DayOff);
		values.put("NightPremiumPersentage", newWeeklyCalculationClass.NightPremiumPersentage);
		
		return sqliteDB.update("WeeklyCalculationTable", values, "Year = " + "\""+Year+"\"and Month = " 
		+ "\""+Month+"\"and DayOfTheMonth = " + "\""+DayOfTheMonth+"\"and InsertId = " + "\""+InstId+"\"", null);
	}
	
	public int updateSatPremium(String Persentage, 
			String WeekNumber, String Month, String Year) {
		
		ContentValues values = new ContentValues();
		
		values.put("PremiumPersentage", Persentage);
		
		return sqliteDB.update("WeeklyCalculationTable", values, "Year = " + "\""+Year+"\"and Month = " 
		+ "\""+Month+"\"and WeekNumber = " + "\""+WeekNumber+"\"and NameOfTheWeek = " + "\"Sat\"", null);
	}
	
	public int updateSunPremium(String Persentage, 
			String WeekNumber, String Month, String Year) {
		
		ContentValues values = new ContentValues();
		
		values.put("PremiumPersentage", Persentage);
		
		return sqliteDB.update("WeeklyCalculationTable", values, "Year = " + "\""+Year+"\"and Month = " 
		+ "\""+Month+"\"and WeekNumber = " + "\""+WeekNumber+"\"and NameOfTheWeek = " + "\"Sun\"", null);
	}
	
	public int updateNightPremium(String Persentage, 
			String WeekNumber, String Month, String Year) {
		
		ContentValues values = new ContentValues();
		
		values.put("NightPremiumPersentage", Persentage);
		Log.v("", "Persentage "+Persentage);
		return sqliteDB.update("WeeklyCalculationTable", values, "Year = " + "\""+Year+"\"and Month = " 
		+ "\""+Month+"\"and WeekNumber = " + "\""+WeekNumber+"\"and NightPremium = " + "\"true\"", null);
	}

	public void deleteWeeklyCalculationTable() {
		if (sqliteDB != null)
			sqliteDB.execSQL("delete from WeeklyCalculationTable");
	}
	
	/*public long insertPassword(ContentValues values) {
		open();
		return sqliteDB.insert("ParentalControlPassword", null, values);
	}
	
	public Cursor getPassword() {
		Cursor cursor = sqliteDB.query("ParentalControlPassword", new String[] {"Password"}, null, null, null, null, null);
		return cursor;
	}

	public void deletePasswordTable() {
		if (sqliteDB != null)
			sqliteDB.execSQL("delete from ParentalControlPassword");
	}

	public long insertBookmarkList(ContentValues values) {
		open();
		return sqliteDB.insert("SafeBrowserSettings", null, values);
	}
	
	public long insertBookmarkList(GridViewObjectClass new_GridViewObjectClass) {
		open();
		ContentValues values = new ContentValues();
		
		values.put("id", new_GridViewObjectClass.bookmark_id);
		values.put("BackGroundImage", new_GridViewObjectClass.background_image);
		values.put("Favorite_id", new_GridViewObjectClass.favorite_id);
		values.put("Favorite_Image", new_GridViewObjectClass.favorite_image);
		values.put("Url_address", new_GridViewObjectClass.Url_address);
		values.put("Url_Image_path", new_GridViewObjectClass.Url_image);
		values.put("Url_Name", new_GridViewObjectClass.Url_Name);
		values.put("random_image_path", new_GridViewObjectClass.random_image_path);
		values.put("urla_path_id", new_GridViewObjectClass.urla_path_id);
		
		return sqliteDB.insert("SafeBrowserSettings", null, values);
	}
	
	public Cursor getAllBookmarkList() {
		Cursor cursor = sqliteDB.query("SafeBrowserSettings", new String[] {
				"id", "BackGroundImage", "Url_Image_path", "Url_Name",
				"Url_address", "Favorite_id", "Favorite_Image", "random_image_path" }, "urla_path_id <> " + "\"1\"", null,
				null, null, null);
		return cursor;
	}

	public Cursor getFavBookmarkList() {
		Cursor cursor = sqliteDB.query("SafeBrowserSettings", new String[] {
				"id", "BackGroundImage", "Url_Image_path", "Url_Name",
				"Url_address", "Favorite_id", "Favorite_Image", "random_image_path" }, "urla_path_id <> " + "\"1\"and Favorite_id = " + "\"1\"", null,
				null, null, null);
		return cursor;
	}

	public Cursor getPreLoadedBookmarkList() {
		Cursor cursor = sqliteDB.query("PreLoadedData", new String[] {
				"id", "BackGroundImage", "Url_Image_path", "Url_Name",
				"Url_address", "Favorite_id", "Favorite_Image", "random_image_path", "urla_path_id" }, null , null,
				null, null, null);
		return cursor;
	}
	
	public long updateBookmarkTableList(GridViewObjectClass newGridViewObjectClass, String GridViewObjectClassId) {
		ContentValues values = new ContentValues();
		
		values.put("BackGroundImage", newGridViewObjectClass.background_image);
		values.put("Url_Image_path", newGridViewObjectClass.Url_image);
		values.put("Url_Name", newGridViewObjectClass.Url_Name);
		values.put("Url_address", newGridViewObjectClass.Url_address);
		values.put("Favorite_id", newGridViewObjectClass.favorite_id);
		values.put("Favorite_Image", newGridViewObjectClass.favorite_image);
		values.put("random_image_path", newGridViewObjectClass.random_image_path);

		return sqliteDB.update("SafeBrowserSettings", values, "id = "
				+ GridViewObjectClassId, null);
	}

	public long DeleteBookMarkItem(String GridViewObjectClassId) {
		return sqliteDB.delete("SafeBrowserSettings", "id = "
				+ GridViewObjectClassId, null);
	}

	public long updateBookmarkTableFavoriteId( GridViewObjectClass newGridViewObjectClass, String GridViewObjectClassId) {
		ContentValues values = new ContentValues();
		
		values.put("Favorite_id", newGridViewObjectClass.favorite_id);
		
		return sqliteDB.update("SafeBrowserSettings", values, "id = " + GridViewObjectClassId, null);
	}

	public void deleteSafeBrowserSettings() {
		if (sqliteDB != null)
			sqliteDB.execSQL("delete from SafeBrowserSettings");
	}
	
	public Cursor GetUrlSafeBrowser() {
		Cursor cursor = sqliteDB.query("SafeBrowserSettings", new String[] {"Url_address"}, null, null, null, null, null);
		Log.v("cursor.getCount()", "cursor.getCount() = "+cursor.getCount());
		return cursor;
	}
	
	public boolean getUrlExists (String url) {
		open();
		Cursor c = sqliteDB.rawQuery("SELECT * FROM SafeBrowserSettings WHERE Url_address like '%"+url.trim()+"%'", null);
		if (c.getCount() != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean AddingUrlExists (String url) {
		open();
		Cursor c = sqliteDB.rawQuery("SELECT * FROM PreLoadedData WHERE Url_address like '%"+url.trim()+"%'", null);
		if (c.getCount() != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public Cursor AddingUrl (String url) {
		open();
		Cursor c = sqliteDB.rawQuery("SELECT * FROM PreLoadedData WHERE Url_address like '%"+url.trim()+"%'", null);
		return c;
	}
	
	public Cursor getAddingUrlExists (String url) {
		open();
		Log.v("url", "url = "+url);
		Cursor c = sqliteDB.rawQuery("SELECT * FROM SafeBrowserSettings WHERE Url_address like '%"+url.trim()+"%'", null);
		Log.v("c", "c = "+c.getCount());
		return c;
	}*/
}
