package com.i_o_u_o_me;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends Activity {
	public static FragmentManager mFragmentManager;
	public static SharedPreferences mPreferences;
	private static final String AD_UNIT_ID = "ca-app-pub-9754975194862641/9110344016";
	private static final String LOG_TAG = "InterstitialSample";

	public static DataBaseAdapter db;
	
	WelcomeScreen mWelcomeScreen;
	StartUpScreen mStartUpScreen;
	/*Page1 mPage1;
	Page2 mPage2;
	Page3 mPage3;
	Page4 mPage4;
	Page5 mPage5;*/
	
	public static TextView top_bar_text;
	
	private AdView adView;
	private InterstitialAd interstitialAd;
	long database_version = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		db = new DataBaseAdapter(MainActivity.this);
		
		
		//
		// Create an ad.
		//
	    adView = new AdView(this);
	    adView.setAdSize(AdSize.BANNER);
	    adView.setAdUnitId(AD_UNIT_ID);

	    // Add the AdView to the view hierarchy. The view will have no size
	    // until the ad is loaded.
	    RelativeLayout layout = (RelativeLayout) findViewById(R.id.ad_bar);
	    layout.addView(adView);
	  
	    // Create an ad request. Check logcat output for the hashed device ID to
	    // get test ads on a physical device.
	    
	    AdRequest adRequest = new AdRequest.Builder()
	        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	        .addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE")
	        .build();
	    
	    adView.loadAd(adRequest);
	    // Start loading the ad in the background.
	    
	    // Create an InterstitialAd.
	    interstitialAd = new InterstitialAd(this);
	    interstitialAd.setAdUnitId(AD_UNIT_ID);
	    
	    AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        // Optionally populate the ad request builder.
        adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        interstitialAd.loadAd(adRequestBuilder.build());
        
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //Toast.makeText(MainActivity.this,
                //        "The interstitial is loaded", Toast.LENGTH_SHORT).show();
                
                if (interstitialAd.isLoaded()) {
                	interstitialAd.show();
                }
                else {
	            	Log.d(LOG_TAG, "Interstitial ad was not ready to be shown.");
	            }
                
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
            	//Toast.makeText(MainActivity.this,
                //                "The interstitial is closed", Toast.LENGTH_SHORT).show();
            }
            
            @Override
	        public void onAdFailedToLoad(int errorCode) {
	      
	        	String message = String.format("onAdFailedToLoad (%s)", getErrorReason(errorCode));
	          	Log.d(LOG_TAG, message);
	          	//Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
	         
	        }
        });
        
        //
		// End ad code.
		//
        
        
		
		mPreferences = getSharedPreferences("i_o_u_o_me", Context.MODE_PRIVATE);
		database_version = 140827;
		if (mPreferences.getLong("database_version", 0) != database_version) {
			copyDatabase();
			mPreferences.edit().putLong("database_version", database_version).commit();
		}
		
		mFragmentManager = getFragmentManager();
		
		top_bar_text = (TextView) findViewById(R.id.top_bar_text);
		
		if (mPreferences.getBoolean("welcome_screen_running", false)) {
			mFragmentManager.beginTransaction().replace(R.id.frame, new StartUpScreen(), 
					StartUpScreen.FRAGMENT_TAG).addToBackStack(null).commit();
		} else {
			mFragmentManager.beginTransaction().replace(R.id.frame, new WelcomeScreen(), 
					WelcomeScreen.FRAGMENT_TAG).addToBackStack(null).commit();
		}
		
		
        
        
//	    AdRequest interstitialAdRequest = new AdRequest.Builder()
//		.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//		.addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE")
//		.build();
//	   
//		// Load the interstitial ad.
//		interstitialAd.loadAd(interstitialAdRequest);
//	    interstitialAd.setAdListener(new AdListener() {
//	        @Override
//	        public void onAdLoaded() {
//	        	Log.d(LOG_TAG, "onAdLoaded");
//	        	Toast.makeText(MainActivity.this, "onAdLoaded", Toast.LENGTH_SHORT).show();
//	        	
//	        	if (interstitialAd.isLoaded()) {
//	          		interstitialAd.show();
//	            } 
//	            else {
//	            	Log.d(LOG_TAG, "Interstitial ad was not ready to be shown.");
//	            }
//				
//	        }
//
//	        @Override
//	        public void onAdFailedToLoad(int errorCode) {
//	      
//	        	String message = String.format("onAdFailedToLoad (%s)", getErrorReason(errorCode));
//	          	Log.d(LOG_TAG, message);
//	          	//Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
//	         
//	        }
//	    });
	    
	}


	@Override
	public void onBackPressed() {
		mWelcomeScreen = (WelcomeScreen) mFragmentManager.findFragmentByTag(WelcomeScreen.FRAGMENT_TAG);
		mStartUpScreen = (StartUpScreen) mFragmentManager.findFragmentByTag(StartUpScreen.FRAGMENT_TAG);
		/*mPage1 = (Page1) mFragmentManager.findFragmentByTag(Page1.FRAGMENT_TAG);
		mPage2 = (Page2) mFragmentManager.findFragmentByTag(Page2.FRAGMENT_TAG);
		mPage3 = (Page3) mFragmentManager.findFragmentByTag(Page3.FRAGMENT_TAG);
		mPage4 = (Page4) mFragmentManager.findFragmentByTag(Page4.FRAGMENT_TAG);
		mPage5 = (Page5) mFragmentManager.findFragmentByTag(Page5.FRAGMENT_TAG);*/
		if((mWelcomeScreen != null && mWelcomeScreen.isVisible()) || (mStartUpScreen != null && mStartUpScreen.isVisible())) {
			finish();
		}/* else if(mPage1 != null && mPage1.isVisible()) {
			mPage1.SaveToDataBase();
			mFragmentManager = getFragmentManager();
			mFragmentManager.beginTransaction().replace(R.id.frame, new StartUpScreen(), 
					StartUpScreen.FRAGMENT_TAG).addToBackStack(null).commit();
		} else if(mPage2 != null && mPage2.isVisible()) {
			mPage2.SaveToDataBase();
			mFragmentManager = getFragmentManager();
			mFragmentManager.beginTransaction().replace(R.id.frame, new StartUpScreen(), 
					StartUpScreen.FRAGMENT_TAG).addToBackStack(null).commit();
		} else if(mPage3 != null && mPage3.isVisible()) {
			mFragmentManager = getFragmentManager();
			mFragmentManager.beginTransaction().replace(R.id.frame, new StartUpScreen(), 
					StartUpScreen.FRAGMENT_TAG).addToBackStack(null).commit();
		} else if(mPage4 != null && mPage4.isVisible()) {
			mFragmentManager = getFragmentManager();
			mFragmentManager.beginTransaction().replace(R.id.frame, new StartUpScreen(), 
					StartUpScreen.FRAGMENT_TAG).addToBackStack(null).commit();
		} else if(mPage5 != null && mPage5.isVisible()) {
			mFragmentManager = getFragmentManager();
			mFragmentManager.beginTransaction().replace(R.id.frame, new StartUpScreen(), 
					StartUpScreen.FRAGMENT_TAG).addToBackStack(null).commit();
		}*/
		else {
			super.onBackPressed();
		}
	}

	private void copyDatabase() {
		db = new DataBaseAdapter(MainActivity.this);
		db.open();
		db.close();
		try {
			InputStream dbIn = getAssets().open("ioume_database.db");
			OutputStream dbOut = new FileOutputStream(getDatabasePath("ioume_database.db").getAbsolutePath());
			Log.v("copy", "database");
			
			byte[] buffer = new byte[1024];
			int remainingData;
			while ((remainingData = dbIn.read(buffer)) > 0) {
				dbOut.write(buffer, 0, remainingData);
			}
			
			dbIn.close();
			dbOut.flush();
			dbOut.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Gets a string error reason from an error code. */
	private String getErrorReason(int errorCode) {
	    String errorReason = "";
	    switch(errorCode) {
	    	case AdRequest.ERROR_CODE_INTERNAL_ERROR:
	    		errorReason = "Internal error";
	    		break;
	    	case AdRequest.ERROR_CODE_INVALID_REQUEST:
	    		errorReason = "Invalid request";
	    		break;
	    	case AdRequest.ERROR_CODE_NETWORK_ERROR:
	    		errorReason = "Network Error";
	    		break;
	    	case AdRequest.ERROR_CODE_NO_FILL:
	    		errorReason = "No fill";
	    		break;
	    }
	    return errorReason;
	}

	@Override
	protected void onResume() {
		if (haveNetworkConnection()) {
			new GetCurrencyRate().execute("http://rate-exchange.appspot.com/currency?from=GBP&to=USD");
			new GetCurrencyRate().execute("http://rate-exchange.appspot.com/currency?from=USD&to=GBP");
		} else {
			Toast.makeText(this, "App needs net connection to get expected currency rate." +
					" Please configure your net connection.", Toast.LENGTH_LONG).show();
			
			if (mPreferences.getFloat("pound_to_dollar", 0) == 0) {
				mPreferences.edit().putFloat("pound_to_dollar", (float) 1.6666667).commit();
				Log.v("rate", "rate dollar_to_pound " + mPreferences.getFloat("pound_to_dollar", 0));
			}
			
			if (mPreferences.getFloat("dollar_to_pound", 0) == 0) {
				mPreferences.edit().putFloat("dollar_to_pound", (float) 0.6).commit();
				Log.v("rate", "rate dollar_to_pound " + mPreferences.getFloat("dollar_to_pound", 0));
			}
		}
		super.onResume();
	}
	
	private boolean haveNetworkConnection() {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}
	
	class GetCurrencyRate extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String responseString = null;
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;
			try {
				response = httpclient.execute(new HttpGet(params[0]));
				StatusLine statusLine = response.getStatusLine();
				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					out.close();
					responseString = out.toString();
				} else {
					// Closes the connection.
					response.getEntity().getContent().close();
					throw new IOException(statusLine.getReasonPhrase());
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (responseString == null) {
				return responseString;
			} else {
				responseString = responseString.replace("\"", "").trim();
				responseString = responseString.replace(" ", "").trim();
				responseString = responseString.replace("{", "").trim();
				responseString = responseString.replace("}", "").trim();
				String [] rate_part = responseString.split(",");
				
				@SuppressWarnings("unused")
				String to = rate_part[0].replace("to:", "");
				float rate = Float.parseFloat(rate_part[1].replace("rate:", ""));
				String from = rate_part[2].replace("from:", "");
				
				if (from.equalsIgnoreCase("GBP")) {
					Log.v("rate", "rate pound_to_dollar " + rate);
					mPreferences.edit().putFloat("pound_to_dollar", rate).commit();
				} else {
					Log.v("rate", "rate dollar_to_pound " + rate);
					mPreferences.edit().putFloat("dollar_to_pound", rate).commit();
				}
				return responseString;
			}
			//Log.v("to", "to " + to);
			//Log.v("from", "from " + from);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (mPreferences.getFloat("pound_to_dollar", 0) == 0) {
				mPreferences.edit().putFloat("pound_to_dollar", (float) 1.6666667).commit();
				Log.v("rate", "rate dollar_to_pound " + mPreferences.getFloat("pound_to_dollar", 0));
			}
			
			if (mPreferences.getFloat("dollar_to_pound", 0) == 0) {
				mPreferences.edit().putFloat("dollar_to_pound", (float) 0.6).commit();
				Log.v("rate", "rate dollar_to_pound " + mPreferences.getFloat("dollar_to_pound", 0));
			}
			super.onPostExecute(result);
		}
	}
}
