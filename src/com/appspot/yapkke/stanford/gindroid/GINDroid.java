package com.appspot.yapkke.stanford.gindroid;

import android.app.*;
import android.view.*;
import android.content.*;
import android.preference.*;
import android.os.Bundle;

/** GINDroid main activity
 *
 * @author ykk
 * @date Nov 2011
 */
public class GINDroid extends TabActivity
{
    /** WebGin instance
     */
    WebGin wg;
    
    /** Starting GINDroid activity
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	wg = new WebGin();
	wg.auth(settings.getString("username", ""),
		settings.getString("password", ""));
    }

    /** Create menu from XML
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.main, menu);
	return true;
    }

    /** Process clicking of menu
     * 
     * (1) Bring up settings activity
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
	switch (item.getItemId()) 
	{
	case R.id.setting_menu:
	    Intent settingIntent = new Intent(this, Settings.class);
	    startActivity(settingIntent);
	    return true;
	default:
	    return super.onOptionsItemSelected(item);
	}
    }
}
