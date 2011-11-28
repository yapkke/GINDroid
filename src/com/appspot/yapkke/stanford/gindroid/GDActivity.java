package com.appspot.yapkke.stanford.gindroid;

import android.app.*;
import android.content.*;
import android.widget.*;
import android.preference.*;
import android.os.*;

import android.util.Log;

import net.sf.andhsli.hotspotlogin.*;

/** GINDroid's Activity
 * 
 * @author ykk
 * @date Nov 2011
 */
public abstract class GDActivity
    extends Activity
{
    /** WebGin instance
     */
    protected WebGin wg;
    /** Listing
     */
    WebGin.Listing listing;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

	//Create new WebGin
	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	wg = new WebGin();
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

    public abstract void refresh_listing();

    protected class UpdateListing 
	extends AsyncTask<WebGin, Void, Void>
    {
	public ProgressDialog dialog;
	public GDActivity gdActivity = GDActivity.this;

	@Override
	    protected void onPreExecute()
	{
	    dialog = new ProgressDialog(gdActivity);
	    dialog.setMessage("Fetching data from GIN...");
	    dialog.show();
	}

	@Override
	    protected Void doInBackground(WebGin... wg)
	{
	    gdActivity.listing = wg[0].briefListing();   
	    return null;
	}
	
	@Override
	    protected void onPostExecute(Void v)
	{
	    if (dialog.isShowing())
		dialog.dismiss();
	    if (listing.classrooms.size() == 0)
		Toast.makeText(gdActivity, "GIN access failed!", Toast.LENGTH_LONG).show();
	    gdActivity.refresh_listing();
	}
    };
}