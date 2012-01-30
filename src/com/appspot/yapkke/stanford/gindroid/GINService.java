package com.appspot.yapkke.stanford.gindroid;

import android.app.*;
import android.content.*;
import android.preference.*;
import android.os.*;

import android.util.Log;

import net.sf.andhsli.hotspotlogin.*;

/** GIN's Service
 *
 * @author ykk
 * @date Jan 2012
 */
public class GINService
    extends Service
{
    public static final String TAG = "GINService";

    /** WebGin instance
     */
    protected WebGin wg = null;

    @Override
    public void onCreate()
    {
	Log.d(TAG, "Creating GIN service...");

	if (wg == null)
	{
	    wg = new WebGin();
	    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	    try
	    {
		wg.auth(settings.getString("username", ""),
			SimpleCrypto.decrypt(GINDroid.MASTER_PASS,
					     settings.getString("password", "")));	
	    } catch (Exception e)
	    {
		;
	    }
	}
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) 
    {
	Log.d(TAG, "Starting GIN service...");

	return START_STICKY;
    }
    
    @Override
    public IBinder onBind(Intent intent)
    {
	return null;
    }   
}