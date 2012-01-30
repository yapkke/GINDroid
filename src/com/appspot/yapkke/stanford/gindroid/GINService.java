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
    public int onStartCommand(Intent intent, int flags, int startId) 
    {
	Log.d(TAG, "Starting GIN service...");

	if (wg == null)
	{
	    wg = new WebGin();
	    new Authenticate().execute(wg);
	}

	return START_STICKY;
    }

    protected class Authenticate
	extends AsyncTask<WebGin, Void, Void>
    {
	@Override
	protected Void doInBackground(WebGin... wg)
	{	    
	    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	    try
	    {
		wg[0].auth(settings.getString("username", ""),
			   SimpleCrypto.decrypt(GINDroid.MASTER_PASS,
						settings.getString("password", "")));	
	    } catch (Exception e)
	    {
		;
	    }
	    
	    return null;
	}

	@Override
	protected void onPostExecute(Void v)
	{
	    Log.d(TAG, "GIN service authenticated");
	}
    }
    
    @Override
    public IBinder onBind(Intent intent)
    {
	return null;
    }   
}