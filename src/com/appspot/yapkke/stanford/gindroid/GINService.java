package com.appspot.yapkke.stanford.gindroid;

import android.app.*;
import android.content.*;
import android.preference.*;
import android.widget.*;
import android.os.*;

import java.util.concurrent.*;

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
	    Authenticate auth = new Authenticate();
	    auth.execute(wg);	    
	    new AuthCheck().execute(auth);
	}

	return START_STICKY;
    }

    protected class AuthCheck
	extends AsyncTask<Authenticate, Void, Void>
    {
	protected final String STATUS_SUCCESS = "Authenticated into GIN";
	protected String status = "";

	@Override
	protected Void doInBackground(Authenticate... auth)
	{    
	    try
	    {
		if (auth[0].getStatus() == AsyncTask.Status.RUNNING)
		    auth[0].get(20, TimeUnit.SECONDS);
	    } catch(TimeoutException e)
	    {
		auth[0].cancel(true);
		status = "GIN Authentication timed out!";
		return null;
	    } catch (Exception e)
	    {
		status = "GIN Authentication failed!";
		return null;
	    }
	    status = STATUS_SUCCESS;
	    
	    return null;
	}	

	@Override
	protected void onPostExecute(Void v)
	{
	    Log.d(TAG, status);
	    Toast.makeText(getBaseContext(), status, 
			   (status == STATUS_SUCCESS)? Toast.LENGTH_SHORT:Toast.LENGTH_LONG).show();
	}
    }

    /** Authentication task
     */
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